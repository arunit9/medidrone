package com.app.medidrone.model.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.app.medidrone.model.Medication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneMedicationLoadRequest {

	@Valid
	@NotEmpty
	private List<Medication> medication;

}
