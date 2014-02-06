package com.ryabokon.myeye;

public class FastMedianFilter {

	public static int[] filter(int[] image, final int width, final int height) {

		int[][] matrix = arrayToMatrix(image, height, width);

		int[] result = new int[width*height];

		int i = 0;
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				int avarageColor = getCellAvarage(matrix, x, y);
				result[(y * width + x)] = avarageColor;
				i++;
			}
		}

		return result;
	};

	public static int getCellAvarage(int[][] image, int x, int y) {
		int sum = 0;
		int i = 0;

		int a11 = image[y - 1][x - 1];
		int a12 = image[y - 1][x];
		int a13 = image[y - 1][x + 1];

		int a21 = image[y][x - 1];
		int a22 = image[y][x];
		int a23 = image[y][x + 1];

		int a31 = image[y + 1][x - 1];
		int a32 = image[y + 1][x];
		int a33 = image[y + 1][x + 1];

		for (int m = y - 1; m <= y + 1; m++) {
			for (int n = x - 1; n <= x + 1; n++) {
				i++;
				sum += image[m][n];
			}
		}

		int avarage = sum / i;

		return avarage;
	}

	public static int[][] arrayToMatrix(int[] array, final int height, final int width) {
		int[][] matrix = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				matrix[y][x] = array[(y * width + x)];
			}
		}
		return matrix;
	}

}
