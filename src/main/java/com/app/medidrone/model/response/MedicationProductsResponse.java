package com.app.medidrone.model.response;

import java.util.List;

import com.app.medidrone.model.Medication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class MedicationProductsResponse {

	private List<Medication> medication;

	public MedicationProductsResponse(List<Medication> medication) {
		super();
		this.medication = medication;
	}

}
