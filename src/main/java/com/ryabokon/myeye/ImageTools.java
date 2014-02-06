package com.ryabokon.myeye;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import org.slf4j.*;

public class ImageTools {
	private static final Logger log = LoggerFactory.getLogger(ImageTools.class);

	public static BufferedImage scalingResize(BufferedImage src, int dstWidth, int dstHeight) {
		final int srcHeight = src.getHeight();
		final int srcWidth = src.getWidth();

		double scaleY = (double) dstHeight / srcHeight;
		double scaleX = (double) dstWidth / srcWidth;

		BufferedImage dst = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = dst.createGraphics();
		AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
		g.drawRenderedImage(src, at);

		return dst;
	}

	public static BufferedImage blur(BufferedImage src) {
		float[] elements = { .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f };

		final int srcHeight = src.getHeight();
		final int srcWidth = src.getWidth();
		BufferedImage dst = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_BYTE_GRAY);

		Kernel kernel = new Kernel(3, 3, elements);
		ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		cop.filter(src, dst);

		return dst;
	}

	public static int getDifferenceAmount(BufferedImage imageA, BufferedImage imageB, int binarizationThreshold) throws Throwable {
		int length = imageA.getWidth() * imageA.getHeight();
		int differenceAmount = 0;

		int[] pixelsA = getImageAsArray(imageA);
		int[] pixelsB = getImageAsArray(imageB);

		for (int i = 0; i < length; i++) {
			int colorA = pixelsA[i];
			int colorB = pixelsB[i];

			int diffColor = Math.abs(colorB - colorA);
			if (diffColor > binarizationThreshold) {
				differenceAmount++;
			}
		}

		return differenceAmount;
	}

	public static int getDifferenceAmount2(BufferedImage imageA, BufferedImage imageB, int binarizationThreshold) throws Throwable {
		final int height = imageA.getHeight();
		final int width = imageA.getWidth();

		int[] diffArray = ImageTools.getImageDifferenceAsArray(imageA, imageB);
		FastMedianFilter.filter(diffArray, height, width);
		return ImageTools.getDifferenceAmount(diffArray, binarizationThreshold);
	}

	public static int getDifferenceAmount(BufferedImage differenceImage, int binarizationThreshold) throws Throwable {
		int[] pixels = getImageAsArray(differenceImage);
		return getDifferenceAmount(pixels, binarizationThreshold);
	}

	public static int getDifferenceAmount(int[] differenceArray, int binarizationThreshold) throws Throwable {
		int length = differenceArray.length;
		int differenceAmount = 0;

		for (int i = 0; i < length; i++) {
			int diff = differenceArray[i];
			if (diff > binarizationThreshold) {
				differenceAmount++;
			}
		}

		return differenceAmount;
	}

	public static int[] getImageAsArray(BufferedImage image) {
		final int height = image.getHeight();
		final int width = image.getWidth();
		int[] result = new int[height * width];

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		final int pixelLength = 3;
		for (int pixel = 0, i = 0; pixel < pixels.length; pixel += pixelLength, i++) {
			int blue = pixels[pixel] & 0xff;
			result[i] = blue;
		}
		return result;
	}

	public static int[] getImageDifferenceAsArray(BufferedImage imageA, BufferedImage imageB) throws IOException {
		final int height = imageA.getHeight();
		final int width = imageA.getWidth();
		int[] result = new int[height * width];

		int[] pixelsA = getImageAsArray(imageA);
		int[] pixelsB = getImageAsArray(imageB);

		for (int i = 0; i < result.length; i++) {
			int colorA = pixelsA[i];
			int colorB = pixelsB[i];

			int diffColor = Math.abs(colorB - colorA);
			result[i] = diffColor;
		}

		return result;
	}

	public static BufferedImage getImageDifferenceAsImage(BufferedImage imageA, BufferedImage imageB) throws IOException {
		final int height = imageA.getHeight();
		final int width = imageA.getWidth();
		int[] diffArray = getImageDifferenceAsArray(imageA, imageB);

		BufferedImage result = writeImageArrayToBufferedImage(height, width, diffArray);

		return result;
	}

	public static BufferedImage writeImageArrayToBufferedImage(final int height, final int width, int[] diffArray) {
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int diffColor = diffArray[(y * width + x)];
				result.setRGB(x, y, new Color(diffColor, diffColor, diffColor).getRGB());
			}

		}
		return result;
	}

}
