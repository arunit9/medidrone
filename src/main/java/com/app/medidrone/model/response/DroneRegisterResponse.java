package com.app.medidrone.model.response;

import com.app.medidrone.model.Drone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneRegisterResponse {

	private Drone drone;
	private String status;

	public DroneRegisterResponse(Drone drone, String status) {
		super();
		this.drone = drone;
		this.status = status;
	}
}
