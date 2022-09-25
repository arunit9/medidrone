package com.app.medidrone.service;

public class DroneLoadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DroneLoadException(String message) {
		super(message);
	}

	public DroneLoadException(String message, Throwable cause) {
		super(message, cause);
	}

}
