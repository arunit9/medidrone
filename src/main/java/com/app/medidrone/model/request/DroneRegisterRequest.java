package com.app.medidrone.model.request;

import javax.validation.constraints.NotNull;

import com.app.medidrone.model.Drone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneRegisterRequest {

	@NotNull
	private Drone drone;

	public DroneRegisterRequest(Drone drone) {
		super();
		this.drone = drone;
	}

}
