package com.app.medidrone.model.response;

import com.app.medidrone.model.Medication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class MedicationProductResponse {

	private Medication medication;

	public MedicationProductResponse(Medication medication) {
		super();
		this.medication = medication;
	}

}
