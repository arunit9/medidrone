package com.app.medidrone.model.request;

import javax.validation.constraints.NotNull;

import com.app.medidrone.dao.entity.State;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneStateUpdateRequest {

	@NotNull
	private State state;

	public DroneStateUpdateRequest(State state) {
		this.state = state;
	}

}
