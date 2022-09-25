package com.app.medidrone.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.app.medidrone.dao.entity.Model;
import com.app.medidrone.dao.entity.State;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class Drone {

	@NotBlank
	@Size(min = 10, max = 20, message 
		      = "Serial number must be between 10 and 20 characters")
	private String serialNumber;

	@NotNull(message = "Model cannot be null")
	private Model model;

	@NotNull
	@Min(value =1, message 
		      = "Weight limit must be between 1 and 500mgr")
	@Max(value = 500, message 
		      = "Weight limit must be between 1 and 500mgr")
	private Integer weightLimit;

	@Min(value = 0, message 
			= "battery capacity must be between 0 and 100 percent")
	@Max(value = 100, message 
			= "battery capacity must be between 0 and 100 percent")
	private Integer batteryCapacity;

	private State state;

	public Drone(String serialNumber, Model model, Integer weightLimit, Integer batteryCapacity, State state) {
		super();
		this.serialNumber = serialNumber;
		this.model = model;
		this.weightLimit = weightLimit;
		this.batteryCapacity = batteryCapacity;
		this.state = state;
	}

}
