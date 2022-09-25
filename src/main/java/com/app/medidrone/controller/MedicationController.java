package com.app.medidrone.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.medidrone.model.Medication;
import com.app.medidrone.model.response.MedicationProductRegisterResponse;
import com.app.medidrone.model.response.MedicationProductResponse;
import com.app.medidrone.model.response.MedicationProductsResponse;
import com.app.medidrone.service.MedicationProductRegistrationException;
import com.app.medidrone.service.MedicationService;
import com.app.medidrone.storage.StorageFileNotFoundException;
import com.app.medidrone.storage.StorageService;

/**
 * REST controller for registering and viewing medical products
 * 
 * @author arunitillekeratne
 *
 */

@RestController
@Validated
@RequestMapping("/medication")
public class MedicationController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private MedicationService medicationService;

	@PostMapping("/register")
	public ResponseEntity<MedicationProductRegisterResponse> registerMedicationProduct(@RequestParam("name") @NotBlank @Size(min = 5, max = 20) String name,
			@RequestParam("code") @NotBlank @Size(min = 5, max = 20) String code, @RequestParam("image") MultipartFile image) {
		storageService.store(image);
		Medication registeredMedication = medicationService.registerMedicationProduct(code, name, image.getOriginalFilename());

	    return ResponseEntity.status(HttpStatus.OK).body(new MedicationProductRegisterResponse(registeredMedication, "Success"));
	}

	@GetMapping
	public ResponseEntity<MedicationProductsResponse> getAllMedicationProducts() throws IOException {
		List<Medication> all = medicationService.getAllMedicationProducts();
		return ResponseEntity.status(HttpStatus.OK).body(new MedicationProductsResponse(all));
	}

	@GetMapping("/{code}")
	public ResponseEntity<MedicationProductResponse> getMedicationProduct(@PathVariable @NotBlank @Size(min = 5, max = 20) String code) throws IOException {
		Medication medication = medicationService.getMedicationProduct(code);
		return ResponseEntity.status(HttpStatus.OK).body(new MedicationProductResponse(medication));
	}

	@GetMapping("/image/{id}")
	public ResponseEntity<Resource> getMedicationProductImage(@PathVariable String id) throws IOException {
		Resource image = storageService.loadAsResource(id);
		return ResponseEntity.status(HttpStatus.OK).body(image);
	}

	@ExceptionHandler(MedicationProductRegistrationException.class)
	public ResponseEntity<?> handleProductRegistrationError(MedicationProductRegistrationException exc) {
		return ResponseEntity.unprocessableEntity().body(exc.getMessage());
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
