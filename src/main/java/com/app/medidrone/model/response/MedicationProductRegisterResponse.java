package com.app.medidrone.model.response;

import com.app.medidrone.dao.entity.Medication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class MedicationProductRegisterResponse {

	private Medication medication;
	private String status;

	public MedicationProductRegisterResponse(Medication medication, String status) {
		super();
		this.medication = medication;
		this.status = status;
	}
}
