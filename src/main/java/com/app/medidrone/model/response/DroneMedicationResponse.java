package com.app.medidrone.model.response;

import java.util.List;

import com.app.medidrone.model.Medication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneMedicationResponse {

	private String serialNumber;

	private List<Medication> loadedMedication;

	public DroneMedicationResponse(String serialNumber, List<Medication> loadedMedication) {
		super();
		this.serialNumber = serialNumber;
		this.loadedMedication = loadedMedication;
	}

}
