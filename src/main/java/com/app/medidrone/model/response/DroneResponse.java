package com.app.medidrone.model.response;

import java.util.List;

import com.app.medidrone.model.Drone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneResponse {

	private List<Drone> drones;

	public DroneResponse(List<Drone> drones) {
		super();
		this.drones = drones;
	}
}
