package com.app.medidrone.model.response;

import java.util.List;

import com.app.medidrone.model.Medication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneMedicationLoadResponse {

	private String serialNumber;

	private List<Medication> loadedMedication;

	private List<Medication> remainingMedication;

	private String status;

	public DroneMedicationLoadResponse(String serialNumber, List<Medication> loadedMedication,
			List<Medication> remainingMedication, String status) {
		super();
		this.serialNumber = serialNumber;
		this.loadedMedication = loadedMedication;
		this.remainingMedication = remainingMedication;
		this.status = status;
	}

}
