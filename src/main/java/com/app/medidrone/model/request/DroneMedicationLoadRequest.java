package com.app.medidrone.model.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.app.medidrone.model.Medication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneMedicationLoadRequest {

	@NotNull
	private List<Medication> medication;

}
