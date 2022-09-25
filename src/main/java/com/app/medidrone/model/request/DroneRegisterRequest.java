package com.app.medidrone.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.app.medidrone.model.Drone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneRegisterRequest {

	@Valid
	@NotNull
	private Drone drone;

	public DroneRegisterRequest(Drone drone) {
		super();
		this.drone = drone;
	}

}
