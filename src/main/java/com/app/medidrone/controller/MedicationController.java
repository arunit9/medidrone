package com.app.medidrone.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.medidrone.model.Medication;
import com.app.medidrone.model.response.MedicationProductRegisterResponse;
import com.app.medidrone.model.response.MedicationProductResponse;
import com.app.medidrone.model.response.MedicationProductsResponse;
import com.app.medidrone.service.MedicationProductRegistrationException;
import com.app.medidrone.service.MedicationService;
import com.app.medidrone.storage.StorageException;
import com.app.medidrone.storage.StorageFileNotFoundException;
import com.app.medidrone.storage.StorageService;

import lombok.extern.log4j.Log4j2;

/**
 * REST controller for registering and viewing medical products
 * 
 * @author arunitillekeratne
 *
 */

@RestController
@Validated
@RequestMapping("/medication")
@Log4j2
public class MedicationController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private MedicationService medicationService;

	@PostMapping("/register")
	public ResponseEntity<MedicationProductRegisterResponse> registerMedicationProduct(@RequestParam("name") @NotBlank @Size(min = 5, max = 20) @Pattern(regexp = "^[A-Za-z0-9-_]+$") String name,
			@RequestParam("code") @NotBlank @Size(min = 5, max = 20) @Pattern(regexp = "^[A-Z0-9_]+$") String code, @RequestParam("image") MultipartFile image) {
		storageService.store(image);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/medication/image/").toUriString();
		Medication registeredMedication = medicationService.registerMedicationProduct(code, name, image.getOriginalFilename(), fileDownloadUri);

	    return ResponseEntity.status(HttpStatus.OK).body(new MedicationProductRegisterResponse(registeredMedication, "Success"));
	}

	@GetMapping
	public ResponseEntity<MedicationProductsResponse> getAllMedicationProducts() throws IOException {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/medication/image/").toUriString();
		List<Medication> all = medicationService.getAllMedicationProducts(fileDownloadUri);
		return ResponseEntity.status(HttpStatus.OK).body(new MedicationProductsResponse(all));
	}

	@GetMapping("/{code}")
	public ResponseEntity<MedicationProductResponse> getMedicationProduct(@PathVariable @NotBlank @Size(min = 5, max = 20) String code) throws IOException {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/medication/image/").toUriString();
		Medication medication = medicationService.getMedicationProduct(code, fileDownloadUri);

		return ResponseEntity.status(HttpStatus.OK).body(new MedicationProductResponse(medication));
	}

	@GetMapping("/image/{id}")
	public ResponseEntity<Resource> getMedicationProductImage(@PathVariable String id, HttpServletRequest request) throws IOException {
		Resource image = storageService.loadAsResource(id);

		String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(image.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // set to default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                .body(image);
	}

	@ExceptionHandler(MedicationProductRegistrationException.class)
	public ResponseEntity<?> handleProductRegistrationError(MedicationProductRegistrationException exc) {
		return ResponseEntity.unprocessableEntity().body(exc.getMessage());
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(StorageException.class)
	public ResponseEntity<?> handleStorageError(StorageException exc) {
		return ResponseEntity.notFound().build();
	}

}
