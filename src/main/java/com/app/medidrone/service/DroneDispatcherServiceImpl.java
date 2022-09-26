package com.app.medidrone.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.medidrone.dao.entity.AuditHistory;
import com.app.medidrone.dao.entity.MedicationProduct;
import com.app.medidrone.dao.entity.State;
import com.app.medidrone.dao.repository.AuditHistoryJpaRepository;
import com.app.medidrone.dao.repository.DroneJpaRepository;
import com.app.medidrone.dao.repository.MedicationProductJpaRepository;
import com.app.medidrone.model.Drone;
import com.app.medidrone.model.Medication;
import com.app.medidrone.model.response.DroneMedicationLoadResponse;
import com.app.medidrone.util.DroneMedicationLoadCalculator;

import io.jsonwebtoken.lang.Collections;
import lombok.extern.log4j.Log4j2;

/**
 * Handles business logic related to drones
 * 
 * @author arunitillekeratne
 *
 */
@Service
@Log4j2
public class DroneDispatcherServiceImpl implements DroneDispatcherService {

	@Autowired
	private DroneJpaRepository droneJpaRepository;

	@Autowired
	private MedicationProductJpaRepository medicationProductJpaRepository;

	@Autowired
	private AuditHistoryJpaRepository auditHistoryJpaRepository;

	@Value("${medidrone.audit.db.enable}")
	private boolean enableDbAudit;

	@Override
	public Drone registerDrone(Drone drone) {
		boolean exists = droneJpaRepository.existsBySerialNumber(drone.getSerialNumber());
		if (exists) {
			throw new DroneRegistrationException(String.format("Drone with given serial number %s already registered", drone.getSerialNumber()));
		}
		com.app.medidrone.dao.entity.Drone registeredDrone = droneJpaRepository.save(new com.app.medidrone.dao.entity.Drone(
				drone.getSerialNumber(), drone.getModel(), drone.getWeightLimit(), 
				drone.getBatteryCapacity(), drone.getState() != null ? drone.getState() : State.IDLE));

		return convertFromEntity(registeredDrone);
	}

	@Override
	public List<Drone> getAvailableDrones() {
		List<com.app.medidrone.dao.entity.Drone> availableEntities =
				droneJpaRepository.findByStateIn(Arrays.asList(State.IDLE));

		return availableEntities.stream()
		        .map(e -> convertFromEntity(e))
		        .collect(Collectors.toList());
	}

	@Override
	public Integer getDroneBattery(String serialNumber) {
		com.app.medidrone.dao.entity.Drone entity = droneJpaRepository.findById(serialNumber).orElse(null);
		if (entity == null) {
			throw new DroneNotFoundException(String.format("Drone with serial number %s not registered", serialNumber));
		}

		return entity.getBatteryCapacity(); 
	}

	@Override
	public void auditDroneBattery() {
		List<com.app.medidrone.dao.entity.Drone> entities = droneJpaRepository.findAll();

		log.info(entities.stream().map(com.app.medidrone.dao.entity.Drone::getBatteryAuditInfo)
				.collect(Collectors.joining(", ")));

		if (enableDbAudit) {
			entities.forEach( (e) -> auditHistoryJpaRepository.save(new AuditHistory(e.getBatteryAuditInfo(), Timestamp.from(Instant.now()))));
		}
	}

	@Override
	public DroneMedicationLoadResponse loadMedication(String serialNumber, List<Medication> medication) {
		com.app.medidrone.dao.entity.Drone entity = droneJpaRepository.findById(serialNumber).orElse(null);

		if (entity == null) {
			throw new DroneNotFoundException(String.format("Drone with serial number %s not registered", serialNumber));
		}

		if (!State.IDLE.equals(entity.getState())) {
			throw new DroneLoadException(String.format("Drone with serial number %s is not available for loading", serialNumber));
		}

		if (entity.getBatteryCapacity() < 25) {
			throw new DroneLoadException(String.format("Drone cannot be loaded due to low battery %s", entity.getBatteryCapacity()));
		}

		// init and validate
		int[] weights = new int[medication.size()];
		for (int i = 0; i < weights.length; i++) {
			if (!medicationProductJpaRepository.existsByCode(medication.get(i).getCode())) {
				throw new DroneLoadException(String.format("Medication with code %s not registered", medication.get(i).getCode()));
			}
			weights[i] = medication.get(i).getWeight();
		}

		// validation passed, update state to LOADING
		entity.setState(State.LOADING);
		droneJpaRepository.save(entity);

		int sum = medication.stream().collect(Collectors.summingInt(o -> o.getWeight()));
		// if sum of all medication to be loaded is less than or equal to the drones weight limit
		// load all medication, no further processing required
		// update drone state to loaded
		if (sum <= entity.getWeightLimit()) {
			entity.setMedications(medication.stream()
			        .map(dto -> convertToEntity(dto))
			        .collect(Collectors.toList()));
			entity.setState(State.LOADED);
			droneJpaRepository.save(entity);
			return new DroneMedicationLoadResponse(serialNumber, medication, null, "Success");
		}

		// else find the optimal combination of medication to load
		boolean[] selected =  DroneMedicationLoadCalculator.calculateOptimalLoad(weights, entity.getWeightLimit());

		List<Medication> loadedMedication = new ArrayList<Medication>();
		List<Medication> remainingMedication = new ArrayList<Medication>();
		List<com.app.medidrone.dao.entity.Medication> medEntities = new ArrayList<com.app.medidrone.dao.entity.Medication>();
		boolean isLoaded = false;

		for (int i = 0; i < selected.length; i++) {
			if (selected[i]) {
				isLoaded = true;
				loadedMedication.add(medication.get(i));
				medEntities.add(convertToEntity(medication.get(i)));
			} else {
				remainingMedication.add(medication.get(i));
			}
		}
		// if at least one medication item is loaded, update drone state to LOADED, else reset to IDLE
		if(isLoaded) {
			entity.setState(State.LOADED);
		} else {
			entity.setState(State.IDLE);
		}
		entity.setMedications(medEntities);
		droneJpaRepository.save(entity);
		return new DroneMedicationLoadResponse(serialNumber, loadedMedication, remainingMedication, "Success");
	}

	@Override
	public Drone updateDroneState(String serialNumber, State state) {
		com.app.medidrone.dao.entity.Drone entity = droneJpaRepository.findById(serialNumber).orElse(null);

		if (entity == null) {
			throw new DroneNotFoundException(String.format("Drone with serial number %s not registered", serialNumber));
		}

		if (State.LOADED.equals(state) || State.LOADING.equals(state)) {
			throw new DroneLoadException(String.format("Drone state cannot be manually set to %s", state));
		}

		entity.setState(state);
		return convertFromEntity(entity);
	}

	@Override
	public List<Medication> getLoadedMedication(String serialNumber) {
		com.app.medidrone.dao.entity.Drone entity = droneJpaRepository.findById(serialNumber).orElse(null);
		if (entity == null) {
			throw new DroneNotFoundException(String.format("Drone with serial number %s not registered", serialNumber));
		}
		if (!Collections.isEmpty(entity.getMedications())) {
			return entity.getMedications().stream()
			        .map(e -> convertFromEntity(e))
			        .collect(Collectors.toList());
		}
		return null;
	}

	private Drone convertFromEntity(com.app.medidrone.dao.entity.Drone entity) {
		return new Drone(entity.getSerialNumber(),entity.getModel(),
				entity.getWeightLimit(), entity.getBatteryCapacity(),
				entity.getState());
	}

	private Medication convertFromEntity(com.app.medidrone.dao.entity.Medication entity) {
		return new Medication(entity.getMedicationProduct().getName(), entity.getWeight(), entity.getMedicationProduct().getCode(), entity.getMedicationProduct().getImage());
	}

	private com.app.medidrone.dao.entity.Medication convertToEntity(Medication dto) {
		MedicationProduct product = medicationProductJpaRepository.findById(dto.getCode()).get();
		return new com.app.medidrone.dao.entity.Medication(dto.getWeight(), product);
	}

}
