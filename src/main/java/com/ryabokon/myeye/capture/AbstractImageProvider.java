package com.ryabokon.myeye.capture;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.imageio.*;

public abstract class AbstractImageProvider {

	protected final SimpleDateFormat fileDateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
	protected final SimpleDateFormat folderDateFormatter = new SimpleDateFormat("yyyy.MM.dd");

	protected final String pathToImagesFolder;

	public AbstractImageProvider(String pathToImagesFolder) {
		this.pathToImagesFolder = pathToImagesFolder;
	}

	public abstract BufferedImage getImage() throws Throwable;

	public void saveImage(BufferedImage image) throws Throwable {

		Date date = new Date();

		String dateFolder = pathToImagesFolder + "/diff/" + folderDateFormatter.format(date) + "/";
		File dateFolderFile = new File(dateFolder);
		if (!dateFolderFile.exists()) {
			dateFolderFile.mkdirs();
		}

		String targetPath = dateFolder + fileDateFormatter.format(date) + ".jpg";
		File targetFile = new File(targetPath);
		ImageIO.write(rotateImage(image), "JPG", targetFile);
	}

	private BufferedImage rotateImage(BufferedImage sourceImage) throws IOException {
		BufferedImage rotatedImage = new BufferedImage(sourceImage.getHeight(), sourceImage.getWidth(),
				sourceImage.getType());
		Graphics2D graphics = (Graphics2D) rotatedImage.getGraphics();
		graphics.rotate(Math.toRadians(90.0));
		graphics.drawImage(sourceImage, 0, -rotatedImage.getWidth(null), null);
		graphics.dispose();
		return rotatedImage;
	}

}
