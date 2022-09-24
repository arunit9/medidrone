package com.app.medidrone.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.medidrone.dao.entity.Drone;
import com.app.medidrone.model.request.DroneMedicationLoadRequest;
import com.app.medidrone.model.request.DroneRegisterRequest;
import com.app.medidrone.model.response.DroneMedicationLoadResponse;
import com.app.medidrone.model.response.DroneMedicationResponse;
import com.app.medidrone.service.DroneDispatcherService;

/**
 * REST controller for handling drone operations
 * 
 * @author arunitillekeratne
 *
 */

@Controller
@RequestMapping("/dispatcher/drone")
public class DroneDispatcherController {

	@Autowired
	private DroneDispatcherService droneDispatcherService;

	@PostMapping("/register")
	public ResponseEntity<Drone> registerDrone(
			@RequestBody DroneRegisterRequest droneRequest) {
		// TODO
	    return ResponseEntity.status(HttpStatus.OK).body(new Drone());
	}

	@PostMapping("/load/{serialNumber}")
	public ResponseEntity<DroneMedicationLoadResponse> loadMedication(
			@RequestBody DroneMedicationLoadRequest droneMedicationRequest, @PathVariable String serialNumber) {

		// TODO
	    return ResponseEntity.status(HttpStatus.OK).body(new DroneMedicationLoadResponse());
	}

	@GetMapping
	public ResponseEntity<List<Drone>> getAvailableDrones() throws IOException {
		// TODO
		return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<Drone>());
	}

	@GetMapping("/medication/{serialNumber}")
	public ResponseEntity<DroneMedicationResponse> getLoadedMedication(@PathVariable @Min(6) @Max(32) String serialNumber) throws IOException {
		// TODO
		return ResponseEntity.status(HttpStatus.OK).body(new DroneMedicationResponse());
	}

}
