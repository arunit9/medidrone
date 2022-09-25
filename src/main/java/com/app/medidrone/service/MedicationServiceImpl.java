package com.app.medidrone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.medidrone.dao.entity.MedicationProduct;
import com.app.medidrone.dao.repository.MedicationProductJpaRepository;
import com.app.medidrone.model.Medication;

/**
 * Handles business logic related to medical products
 * 
 * @author arunitillekeratne
 *
 */
@Service
public class MedicationServiceImpl implements MedicationService {

	@Autowired
	private MedicationProductJpaRepository medicationProductJpaRepository;

	@Override
	public Medication registerMedicationProduct(String code, String name, String image) {
		if (!code.equals(FilenameUtils.removeExtension(image))) {
			throw new MedicationProductRegistrationException(String.format("Image file name must match the code %s", code));
		}
		if (medicationProductJpaRepository.existsByCode(code)) {
			throw new MedicationProductRegistrationException(String.format("Medication Product with given code %s already registered", code));
		}
		MedicationProduct entity = medicationProductJpaRepository.save(new MedicationProduct(code, name, image));

		return convertFromEntity(entity);
	}

	@Override
	public List<Medication> getAllMedicationProducts() {
		List<MedicationProduct> all = medicationProductJpaRepository.findAll();

		if (all != null) {
			return all.stream()
			        .map(e -> convertFromEntity(e))
			        .collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public Medication getMedicationProduct(String code) {
		MedicationProduct entity = medicationProductJpaRepository.findById(code).orElse(null);

		if (entity != null) {
			return convertFromEntity(entity);
		}
		return null;
	}

	private Medication convertFromEntity(MedicationProduct entity) {
		return new Medication(entity.getName(), null, entity.getCode(), entity.getImage());
	}
}
