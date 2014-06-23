package com.ryabokon.myeye;

import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.text.*;

import javax.imageio.*;

import org.junit.*;

import com.ryabokon.myeye.image.*;

@Ignore
public class PerformanceTest {

	@Test
	public void scalingResizePerformanceTest() throws Throwable {
		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		BufferedImage image = ImageIO.read(imageFile);

		for (int i = 0; i < 100000; i++) {
			BufferedImage sized = ImageTools.scalingResizeImage(image, 256, 320);
		}

	}

	@Test
	public void arrayResizePerformanceTest() throws Throwable {
		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		Raster raster = ImageTools.getRaster(new File(imageFile.toURI()));

		for (int i = 0; i < 100000; i++) {

			int[] result = ImageTools.getRasterAsScaledArray(raster, 4);
		}

	}

	@Test
	public void blurPerformanceTest() throws Throwable {
		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		BufferedImage image = ImageIO.read(imageFile);
		for (int i = 0; i < 1000; i++) {
			BufferedImage blurd = ImageTools.blurImage(image);
			ImageIO.write(blurd, "JPG", new File("blurd_1.jpg"));
		}

	}

	@Test
	public void boxFilterPerformanceTest() throws Throwable {
		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		BufferedImage image = ImageIO.read(imageFile);
		int[] img = ImageTools.getImageAsArray(image);
		final int height = image.getHeight();
		final int width = image.getWidth();

		for (int i = 0; i < 1000; i++) {
			int[] result = BoxFilter.filter(img, width, height);
		}

	}

	@Test
	public void DownloadImageAndGetDifferenceTest() throws Throwable {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");
		for (int i = 0; i < 5; i++) {
			BufferedImage imageA = ImageIO.read(camera);
			BufferedImage imageB = ImageIO.read(camera);

			BufferedImage diff = ImageTools.getImageDifferenceAsImage(imageA, imageB);
			// ImageIO.write(diff, "JPG", new File("diff/diff_" +
			// dateFormatter.format(new Date()) + ".jpg"));
		}
	}

}
