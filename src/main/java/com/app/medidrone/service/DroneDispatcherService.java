package com.app.medidrone.service;

import java.util.List;

import com.app.medidrone.dao.entity.State;
import com.app.medidrone.model.Drone;
import com.app.medidrone.model.Medication;
import com.app.medidrone.model.response.DroneMedicationLoadResponse;

/**
 * Handles business logic related to drones
 * 
 * @author arunitillekeratne
 *
 */
public interface DroneDispatcherService {

	/**
	 * Register a new drone
	 * 
	 * @param drone
	 * @return
	 */
	public Drone registerDrone(Drone drone);

	/**
	 * Get available drones
	 * 
	 * @return
	 */
	public List<Drone> getAvailableDrones();

	/**
	 * Return batter capacity for the given drone serial number
	 * 
	 * @param serialNumber
	 */
	public Integer getDroneBattery(String serialNumber);

	/**
	 * Audit drone battery levels
	 * 
	 * @return
	 */
	public void auditDroneBattery();

	/**
	 * Load the given medication to the drone with given serial number in an optimal way
	 *
	 * @param serialNumber
	 * @param medication
	 * @return
	 */
	public DroneMedicationLoadResponse loadMedication(String serialNumber, List<Medication> medication);

	/**
	 * Returns the medication loaded onto a given drone
	 * @param serialNumber
	 * @return
	 */
	public List<Medication> getLoadedMedication(String serialNumber);

	/**
	 * Updates the state of the drone
	 * 
	 * @param serialNumber
	 * @param state
	 * @return
	 */
	public Drone updateDroneState(String serialNumber, State state);

}
