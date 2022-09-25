package com.app.medidrone.service;

public class DroneRegistrationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DroneRegistrationException(String message) {
		super(message);
	}

	public DroneRegistrationException(String message, Throwable cause) {
		super(message, cause);
	}

}
