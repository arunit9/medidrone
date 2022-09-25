package com.app.medidrone;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.app.medidrone.util.DroneMedicationLoadCalculator;

/**
 * Test class for the drone medication loading algorithm
 * 
 * @author arunitillekeratne
 *
 */
public class DroneMedicationLoadCalculatorTest {

	@Test
	public void equalsMaxWeight_Test1() {
		int weights[] = new int[]{2, 1, 1, 4, 3, 4};
		boolean[] expected = new boolean[] {true, true, true, true, true, true};

		int weightLimit = 15;
		boolean[] selected = DroneMedicationLoadCalculator.calculateOptimalLoad(weights, weightLimit);

		assertArrayEquals(expected, selected);
	}

	@Test
	public void exceedMaxWeight_Test1() {
		int weights[] = new int[]{2, 1, 1, 4, 3, 22, 14};
		boolean[] expected = new boolean[] {false, true, false, false, false, false, true};

		int weightLimit = 15;
		boolean[] selected = DroneMedicationLoadCalculator.calculateOptimalLoad(weights, weightLimit);

		assertArrayEquals(expected, selected);
	}

	@Test
	public void exceedMaxWeight_Test2() {
		int weights[] = new int[]{2, 1, 10, 4, 1, 3};
		boolean[] expected = new boolean[] {false, true, true, true, false, false};

		int weightLimit = 15;
		boolean[] selected = DroneMedicationLoadCalculator.calculateOptimalLoad(weights, weightLimit);

		assertArrayEquals(expected, selected);
	}

	@Test
	public void exceedMaxWeight_Test3() {
		int weights[] = new int[]{2, 3, 10, 3, 2};
		boolean[] expected = new boolean[] {true, true, true, false, false};

		int weightLimit = 15;
		boolean[] selected = DroneMedicationLoadCalculator.calculateOptimalLoad(weights, weightLimit);

		assertArrayEquals(expected, selected);
	}

	@Test
	public void notExceedMaxWeight_Test() {
		int weights[] = new int[]{2, 1, 1, 4, 1, 3};
		boolean[] expected = new boolean[] {true, true, true, true, true, true};

		int weightLimit = 15;
		boolean[] selected = DroneMedicationLoadCalculator.calculateOptimalLoad(weights, weightLimit);

		assertArrayEquals(expected, selected);
	}

	@Test
	public void singleItemNotExceedMaxWeight_Test() {
		int weights[] = new int[]{2};
		boolean[] expected = new boolean[] {true};

		int weightLimit = 15;
		boolean[] selected = DroneMedicationLoadCalculator.calculateOptimalLoad(weights, weightLimit);

		assertArrayEquals(expected, selected);
	}

	@Test
	public void singleItemExceedMaxWeight_Test() {
		int weights[] = new int[]{16};
		boolean[] expected = new boolean[] {false};

		int weightLimit = 15;
		boolean[] selected = DroneMedicationLoadCalculator.calculateOptimalLoad(weights, weightLimit);

		assertArrayEquals(expected, selected);
	}

	@Test
	public void noItemExceedMaxWeight_Test() {
		int weights[] = new int[]{};
		boolean[] expected = new boolean[] {};

		int weightLimit = 15;
		boolean[] selected = DroneMedicationLoadCalculator.calculateOptimalLoad(weights, weightLimit);

		assertArrayEquals(expected, selected);
	}
}
