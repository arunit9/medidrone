package com.app.medidrone.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.medidrone.model.Drone;
import com.app.medidrone.model.Medication;
import com.app.medidrone.model.request.DroneMedicationLoadRequest;
import com.app.medidrone.model.request.DroneRegisterRequest;
import com.app.medidrone.model.request.DroneStateUpdateRequest;
import com.app.medidrone.model.response.DroneBatteryResponse;
import com.app.medidrone.model.response.DroneMedicationLoadResponse;
import com.app.medidrone.model.response.DroneMedicationResponse;
import com.app.medidrone.model.response.DroneRegisterResponse;
import com.app.medidrone.model.response.DroneResponse;
import com.app.medidrone.model.response.DroneStateUpdateResponse;
import com.app.medidrone.service.DroneDispatcherService;
import com.app.medidrone.service.DroneLoadException;
import com.app.medidrone.service.DroneNotFoundException;
import com.app.medidrone.service.DroneRegistrationException;

/**
 * REST controller for handling drone operations
 * 
 * @author arunitillekeratne
 *
 */

@RestController
@Validated
@RequestMapping("/dispatcher/drone")
public class DroneDispatcherController {

	@Autowired
	private DroneDispatcherService droneDispatcherService;

	@PostMapping("/register")
	public ResponseEntity<DroneRegisterResponse> registerDrone(
			@Valid @RequestBody DroneRegisterRequest droneRequest) {
		Drone drone = droneDispatcherService.registerDrone(droneRequest.getDrone());
	    return ResponseEntity.status(HttpStatus.OK).body(new DroneRegisterResponse(drone, "Success"));
	}

	@PostMapping("/load/{serialNumber}")
	public ResponseEntity<DroneMedicationLoadResponse> loadMedication(
			@Valid @RequestBody DroneMedicationLoadRequest droneMedicationRequest, @PathVariable @NotBlank @Size(min = 10, max = 20) String serialNumber) {

		DroneMedicationLoadResponse response = droneDispatcherService.loadMedication(serialNumber, droneMedicationRequest.getMedication());

	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<DroneResponse> getAvailableDrones() throws IOException {
		List<Drone> drones = droneDispatcherService.getAvailableDrones();
		return ResponseEntity.status(HttpStatus.OK).body(new DroneResponse(drones));
	}

	@GetMapping("/medication/{serialNumber}")
	public ResponseEntity<DroneMedicationResponse> getLoadedMedication(@PathVariable @NotBlank @Size(min = 10, max = 20) String serialNumber) throws IOException {
		String imageDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/medication/image/").toUriString();

		List<Medication> loadedMedication = droneDispatcherService.getLoadedMedication(serialNumber, imageDownloadUri);

		return ResponseEntity.status(HttpStatus.OK).body(new DroneMedicationResponse(serialNumber, loadedMedication));
	}

	@GetMapping("/battery/{serialNumber}")
	public ResponseEntity<DroneBatteryResponse> getDroneBatteryCapacity(@PathVariable @NotBlank @Size(min = 10, max = 20) String serialNumber) throws IOException {
		Integer batteryCapacity = droneDispatcherService.getDroneBattery(serialNumber);
		return ResponseEntity.status(HttpStatus.OK).body(new DroneBatteryResponse(serialNumber, batteryCapacity));
	}

	@PatchMapping("/state/{serialNumber}")
	public ResponseEntity<DroneStateUpdateResponse> updateDroneState(@PathVariable @NotBlank @Size(min = 10, max = 20) String serialNumber,
			@Valid @RequestBody DroneStateUpdateRequest droneStateUpdateRequest) throws IOException {
		Drone drone = droneDispatcherService.updateDroneState(serialNumber, droneStateUpdateRequest.getState());
		return ResponseEntity.status(HttpStatus.OK).body(new DroneStateUpdateResponse(drone, "Success"));
	}

	@ExceptionHandler(DroneRegistrationException.class)
	public ResponseEntity<?> handleDroneRegistrationError(DroneRegistrationException exc) {
		return ResponseEntity.unprocessableEntity().body(exc.getMessage());
	}

	@ExceptionHandler(DroneNotFoundException.class)
	public ResponseEntity<?> handleDroneRegistrationError(DroneNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(DroneLoadException.class)
	public ResponseEntity<?> handleDroneLoadError(DroneLoadException exc) {
		return ResponseEntity.unprocessableEntity().body(exc.getMessage());
	}

}
