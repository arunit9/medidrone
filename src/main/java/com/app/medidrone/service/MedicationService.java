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
	 * @param fileDownloadUri
	 * 
	 * @return
	 */
	public Medication registerMedicationProduct(String code, String name, String image, String fileDownloadUri);

	/**
	 * get all registered medical products
	 * 
	 * @param fileDownloadUri
	 * 
	 * @return
	 */
	public List<Medication> getAllMedicationProducts(String fileDownloadUri);

	/**
	 * get medical product details by given code
	 * @param code
	 * @param fileDownloadUri
	 * 
	 * @return
	 */
	public Medication getMedicationProduct(String code, String fileDownloadUri);

}
