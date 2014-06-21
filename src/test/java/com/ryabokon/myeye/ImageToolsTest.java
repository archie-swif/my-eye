package com.ryabokon.myeye;

import java.awt.image.*;
import java.net.*;
import java.text.*;

import javax.imageio.*;

import org.junit.*;

import com.ryabokon.myeye.image.*;

public class ImageToolsTest {

	@Test
	public void GetImageDifferenceAsArrayTest() throws Throwable {
		URL imageFileA = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		URL imageFileB = ImageToolsTest.class.getClassLoader().getResource("2.jpg");
		BufferedImage imageA = ImageIO.read(imageFileA);
		BufferedImage imageB = ImageIO.read(imageFileB);

		int[] diffArray = ImageTools.getImageDifferenceAsArray(imageA, imageB);
		Assert.assertNotNull(diffArray);
		Assert.assertEquals(imageA.getHeight() * imageB.getWidth(), diffArray.length);

		for (int val : diffArray) {
			Assert.assertTrue(val >= 0 && val < 255);
		}
	}

	@Test
	public void GetImageDifferenceAsImageTest() throws Throwable {
		URL imageFileA = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		URL imageFileB = ImageToolsTest.class.getClassLoader().getResource("2.jpg");
		BufferedImage imageA = ImageIO.read(imageFileA);
		BufferedImage imageB = ImageIO.read(imageFileB);

		BufferedImage diff = ImageTools.getImageDifferenceAsImage(imageA, imageB);

		Assert.assertNotNull(diff);
		Assert.assertEquals(imageA.getHeight(), diff.getHeight());
		Assert.assertEquals(imageA.getWidth(), diff.getWidth());

		// ImageIO.write(diff, "JPG", new File("diff.jpg"));
	}

	@Test
	public void GetDifferenceAmountTest() throws Throwable {
		URL imageFileA = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		URL imageFileB = ImageToolsTest.class.getClassLoader().getResource("2.jpg");
		BufferedImage imageA = ImageIO.read(imageFileA);
		BufferedImage imageB = ImageIO.read(imageFileB);

		int diffAmount = ImageTools.getDifferenceAmount(imageA, imageB, 100);

		Assert.assertTrue(diffAmount > 0);
		System.out.println(diffAmount);
	}

	@Test
	public void scalingResizeTest() throws Throwable {
		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		BufferedImage image = ImageIO.read(imageFile);

		BufferedImage resized = ImageTools.scalingResizeImage(image, 256, 320);
		Assert.assertNotNull(resized);
		Assert.assertEquals(256, resized.getWidth());
		Assert.assertEquals(320, resized.getHeight());
		// ImageIO.write(sized, "JPG", new File("sized_scaled.jpg"));

	}

	@Test
	public void blurTest() throws Throwable {
		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		BufferedImage image = ImageIO.read(imageFile);
		BufferedImage blurd = ImageTools.blurImage(image);
		// ImageIO.write(blurd, "JPG", new File("blurd_1.jpg"));

	}

	@Test
	public void boxFilterTest() throws Throwable {
		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
		BufferedImage image = ImageIO.read(imageFile);
		int[] img = ImageTools.getImageAsArray(image);
		final int height = image.getHeight();
		final int width = image.getWidth();

		int[] result = BoxFilter.filter(img, width, height);
		// BufferedImage blurd =
		// ImageTools.writeImageArrayToBufferedImage(height, width, img);
		// ImageIO.write(blurd, "JPG", new File("blurd_4.jpg"));

	}

}
