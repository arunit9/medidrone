package com.app.medidrone.model.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class DroneBatteryResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String serialNumber;

	private Integer battery;

	public DroneBatteryResponse(String serialNumber, Integer battery) {
		super();
		this.serialNumber = serialNumber;
		this.battery = battery;
	}

}
