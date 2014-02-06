package com.ryabokon.myeye;

import java.awt.image.*;
import java.net.*;
import java.text.*;

import javax.imageio.*;

import org.junit.*;

public class ImageToolsTest
{

    @Test
    public void GetImageDifferenceAsArrayTest() throws Throwable
    {
	URL imageFileA = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
	URL imageFileB = ImageToolsTest.class.getClassLoader().getResource("2.jpg");
	BufferedImage imageA = ImageIO.read(imageFileA);
	BufferedImage imageB = ImageIO.read(imageFileB);

	int[] diffArray = ImageTools.getImageDifferenceAsArray(imageA, imageB);

	for (int val : diffArray)
	{
	    Assert.assertTrue(val >= 0 && val < 255);
	}
    }

    @Test
    public void GetImageDifferenceAsImageTest() throws Throwable
    {
	URL imageFileA = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
	URL imageFileB = ImageToolsTest.class.getClassLoader().getResource("2.jpg");
	BufferedImage imageA = ImageIO.read(imageFileA);
	BufferedImage imageB = ImageIO.read(imageFileB);

	BufferedImage diff = ImageTools.getImageDifferenceAsImage(imageA, imageB);
	// ImageIO.write(diff, "JPG", new File("diff.jpg"));
    }

    @Test
    public void GetDifferenceAmountTest() throws Throwable
    {
	URL imageFileA = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
	URL imageFileB = ImageToolsTest.class.getClassLoader().getResource("2.jpg");
	BufferedImage imageA = ImageIO.read(imageFileA);
	BufferedImage imageB = ImageIO.read(imageFileB);

	int diffAmount = ImageTools.getDifferenceAmount(imageA, imageB, 100);

	Assert.assertTrue(diffAmount != 0);
	System.out.println(diffAmount);
    }

    @Test
    public void GetLiveImageDifferenceTest() throws Throwable
    {
	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
	URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");
	for (int i = 0; i < 5; i++)
	{
	    BufferedImage imageA = ImageIO.read(camera);
	    BufferedImage imageB = ImageIO.read(camera);

	    BufferedImage diff = ImageTools.getImageDifferenceAsImage(imageA, imageB);
	    // ImageIO.write(diff, "JPG", new File("diff/diff_" +
	    // dateFormatter.format(new Date()) + ".jpg"));
	}
    }

    @Test
    public void scalingResizeTest() throws Throwable
    {
	URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
	BufferedImage image = ImageIO.read(imageFile);

	// for (int i = 0; i < 10000; i++)
	{
	    BufferedImage sized = ImageTools.scalingResize(image, 256, 320);
	    // ImageIO.write(sized, "JPG", new File("sized_scaled.jpg"));
	}

    }

    @Test
    public void blurTest() throws Throwable
    {
	URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
	BufferedImage image = ImageIO.read(imageFile);
	// for (int i = 0; i < 1000; i++)
	{
	    BufferedImage blurd = ImageTools.blur(image);
	    // ImageIO.write(blurd, "JPG", new File("blurd_1.jpg"));
	}

    }

    @Test
    public void medianTest() throws Throwable
    {
	URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
	BufferedImage image = ImageIO.read(imageFile);
	int[] img = ImageTools.getImageAsArray(image);
	final int height = image.getHeight();
	final int width = image.getWidth();

	// for (int i = 0; i < 1000; i++)
	{
	    int[] result = FastMedianFilter.filter(img, width, height);
	    // BufferedImage blurd =
	    // ImageTools.writeImageArrayToBufferedImage(height, width, img);
	    // ImageIO.write(blurd, "JPG", new File("blurd_4.jpg"));
	}

    }
}
