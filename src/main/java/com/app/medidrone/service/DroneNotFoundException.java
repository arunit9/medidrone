package com.app.medidrone.service;

public class DroneNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DroneNotFoundException(String message) {
		super(message);
	}

	public DroneNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
