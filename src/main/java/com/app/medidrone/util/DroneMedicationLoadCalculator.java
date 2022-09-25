package com.app.medidrone.util;

import lombok.extern.log4j.Log4j2;

/**
 * Utility class for the optimal loading algorithms
 * 
 * @author arunitillekeratne
 *
 */

@Log4j2
public class DroneMedicationLoadCalculator {

	/**
	 * Finds the optimal combination of medication to load, not exceeding
	 * the weight limit of the drone
	 */
	public static boolean[] calculateOptimalLoad(int weights[], int maxWeight) {
		int n = weights.length;
		boolean selected[] = new boolean[n];
		int matrix[][] = new int[n + 1][maxWeight + 1];

		StringBuilder str = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			str.setLength(0);
			for (int j = 0; j <= maxWeight; j++) {
				matrix[i][j] = matrix[i - 1][j];
				if ((j >= weights[i-1]) && (matrix[i][j] < matrix[i - 1][j - weights[i - 1]] + weights[i - 1])) {
					matrix[i][j] = matrix[i - 1][j - weights[i - 1]] + weights[i - 1]; 
				}

				str.append(matrix[i][j] + " ");
			}
			log.debug(str);
		}

		log.debug("Optimal weight: " + matrix[n][maxWeight]);
		log.debug("Selected Packs: ");

		while (n != 0) {
			if (matrix[n][maxWeight] != matrix[n - 1][maxWeight]) {
				log.debug("Package " + n + " with W = " + weights[n - 1]);
				selected[n-1] = true;

				maxWeight = maxWeight - weights[n-1];
			}

			n--;
		}

		return selected;
	}
}
