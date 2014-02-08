package com.ryabokon.myeye;

import org.junit.*;

import com.ryabokon.myeye.image.*;

public class BoxBlurTest
{

    @Test
    public void avarageMatrixTest() throws Throwable
    {
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
    public void arrayToMatrixTest() throws Throwable
    {
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

	for (int y = 0; y < 2; y++)
	{
	    for (int x = 0; x < 2; x++)
	    {
		Assert.assertTrue(actual[y][x] == expected[y][x]);
	    }
	}

    }

    @Test
    public void avarageImageTest() throws Throwable
    {
	int[][] matrix = new int[3][3];

	int[] array = new int[9];
	array[0] = 1;
	array[1] = 1;
	array[2] = 1;
	array[3] = 1;
	array[4] = 1;
	array[5] = 1;
	array[6] = 7;
	array[7] = 7;
	array[8] = 7;

	int[] result = BoxFilter.filter(array, 3, 3);

    }
}
