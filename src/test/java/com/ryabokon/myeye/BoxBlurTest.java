package com.ryabokon.myeye;

import org.junit.*;

import com.ryabokon.myeye.image.*;

public class BoxBlurTest {

	@Test
	public void avarageMatrixTest() throws Throwable {
		int[][] matrix = new int[3][3];

		matrix[0][0] = 1;
		matrix[0][1] = 1;
		matrix[0][2] = 1;

		matrix[1][0] = 1;
		matrix[1][1] = 1;
		matrix[1][2] = 1;

		matrix[2][0] = 4;
		matrix[2][1] = 4;
		matrix[2][2] = 4;

		int avarage = BoxFilter.getCellAvarage(matrix, 1, 1);
		Assert.assertTrue(avarage == 2);

	}

	@Test
	public void arrayToMatrixTest() throws Throwable {
		int[][] expected = new int[2][2];

		expected[0][0] = 1;
		expected[0][1] = 2;
		expected[1][0] = 3;
		expected[1][1] = 4;

		int[] array = new int[4];

		array[0] = 1;
		array[1] = 2;
		array[2] = 3;
		array[3] = 4;

		int[][] actual = BoxFilter.arrayToMatrix(array, 2, 2);

		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 2; x++) {
				Assert.assertTrue(actual[y][x] == expected[y][x]);
			}
		}

	}

	@Test
	public void avarageArrayTest() throws Throwable {

		int[] source = new int[9];
		source[0] = 1;
		source[1] = 1;
		source[2] = 1;
		source[3] = 1;
		source[4] = 1;
		source[5] = 1;
		source[6] = 7;
		source[7] = 7;
		source[8] = 7;

		int[] expected = new int[9];
		expected[0] = 0;
		expected[1] = 0;
		expected[2] = 0;
		expected[3] = 0;
		expected[4] = 3;
		expected[5] = 0;
		expected[6] = 0;
		expected[7] = 0;
		expected[8] = 0;

		int[] actual = BoxFilter.filter(source, 3, 3);

		Assert.assertArrayEquals(expected, actual);

	}
}
