package com.app.medidrone.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.medidrone.model.response.MedicationProductRegisterResponse;
import com.app.medidrone.model.response.MedicationProductResponse;
import com.app.medidrone.model.response.MedicationProductsResponse;

/**
 * REST controller for registering and viewing medical products
 * 
 * @author arunitillekeratne
 *
 */

@Controller
@RequestMapping("/medication")
public class MedicationController {

	@PostMapping("/register")
	public ResponseEntity<MedicationProductRegisterResponse> registerMedicationProduct(@RequestParam("name") String name,
			@RequestParam("code") String code, @RequestParam("image") MultipartFile image) {

		// TODO
	    return ResponseEntity.status(HttpStatus.OK).body(new MedicationProductRegisterResponse());
	}

	@GetMapping
	public ResponseEntity<MedicationProductsResponse> getAllMedicationProducts() throws IOException {
		// TODO
		return ResponseEntity.status(HttpStatus.OK).body(new MedicationProductsResponse());
	}

	@GetMapping("/{code}")
	public ResponseEntity<MedicationProductResponse> getMedicationProduct(@PathVariable String code) throws IOException {
		// TODO
		return ResponseEntity.status(HttpStatus.OK).body(new MedicationProductResponse());
	}

	@GetMapping("/image/{id}")
	public ResponseEntity<MultipartFile> getMedicationProductImage(@PathVariable String id) throws IOException {
		// TODO
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleFileNotFound(RuntimeException exc) {
		return ResponseEntity.notFound().build();
	}

}
