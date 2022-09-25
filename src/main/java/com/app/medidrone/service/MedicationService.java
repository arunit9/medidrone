package com.app.medidrone.service;

import java.util.List;

import com.app.medidrone.model.Medication;

/**
 * Handles business logic related to medical products
 * 
 * @author arunitillekeratne
 *
 */
public interface MedicationService {

	/**
	 * register a new product
	 *
	 * @param code
	 * @param name
	 * @param image
	 * @return
	 */
	public Medication registerMedicationProduct(String code, String name, String image);

	/**
	 * get all registered medical products
	 * @return
	 */
	public List<Medication> getAllMedicationProducts();

	/**
	 * get medical product details by given code
	 * @param code
	 * @return
	 */
	public Medication getMedicationProduct(String code);

}
