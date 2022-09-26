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
	public Medication registerMedicationProduct(String code, String name, String image, String fileDownloadUri) {
		if (!code.equals(FilenameUtils.removeExtension(image))) {
			throw new MedicationProductRegistrationException(String.format("Image file name must match the code %s", code));
		}
		if (medicationProductJpaRepository.existsByCode(code)) {
			throw new MedicationProductRegistrationException(String.format("Medication Product with given code %s already registered", code));
		}
		MedicationProduct entity = medicationProductJpaRepository.save(new MedicationProduct(code, name, image));

		StringBuilder str = new StringBuilder();
		return convertFromEntity(entity, fileDownloadUri, str);
	}

	@Override
	public List<Medication> getAllMedicationProducts(String fileDownloadUri) {
		List<MedicationProduct> all = medicationProductJpaRepository.findAll();

		if (all != null) {
			StringBuilder str = new StringBuilder();
			return all.stream()
			        .map(e -> convertFromEntity(e, fileDownloadUri, str))
			        .collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public Medication getMedicationProduct(String code, String fileDownloadUri) {
		MedicationProduct entity = medicationProductJpaRepository.findById(code).orElse(null);

		if (entity != null) {
			StringBuilder str = new StringBuilder();
			return convertFromEntity(entity, fileDownloadUri, str);
		}
		return null;
	}

	private Medication convertFromEntity(MedicationProduct entity, String fileDownloadUri, StringBuilder str) {
		// init StringBuilder
		str.setLength(0);
		return new Medication(entity.getName(), null, entity.getCode(), str.append(fileDownloadUri).append(entity.getImage()).toString());
	}
}
