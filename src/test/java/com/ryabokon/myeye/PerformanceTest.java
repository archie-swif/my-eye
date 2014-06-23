package com.ryabokon.myeye;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.junit.Ignore;
import org.junit.Test;

import com.ryabokon.myeye.image.BoxFilter;
import com.ryabokon.myeye.image.ImageTools;

@Ignore
public class PerformanceTest {

	@Test
	public void scalingResizePerformanceTest() throws Throwable {
		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		BufferedImage image = ImageIO.read(imageFile);

		for (int i = 0; i < 10000; i++) {
			BufferedImage sized = ImageTools.scalingResizeImage(image, 256, 320);
		}

	}
	
	@Test
	public void arrayedResizePerformanceTest() throws Throwable {
		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		Raster raster = ImageTools.getRaster(new File(imageFile.toURI()));

		for (int i = 0; i < 10000; i++) {				
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
