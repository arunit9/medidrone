package com.app.medidrone.service;

public class MedicationProductRegistrationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MedicationProductRegistrationException(String message) {
		super(message);
	}

	public MedicationProductRegistrationException(String message, Throwable cause) {
		super(message, cause);
	}

}
