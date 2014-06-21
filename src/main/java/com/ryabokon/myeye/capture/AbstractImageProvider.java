package com.ryabokon.myeye.capture;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.imageio.*;

import org.slf4j.*;

public abstract class AbstractImageProvider {

	private static final Logger log = LoggerFactory.getLogger(AbstractImageProvider.class);
	protected final SimpleDateFormat fileDateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
	protected final SimpleDateFormat folderDateFormatter = new SimpleDateFormat("yyyy.MM.dd");

	private final String pathToSaveImages;

	public AbstractImageProvider(String pathToStoreImages) {
		this.pathToSaveImages = pathToStoreImages;
	}

	public abstract BufferedImage provideImage() throws Throwable;

	public void saveImage(BufferedImage image) throws Throwable {

		Date date = new Date();

		String dateFolder = pathToSaveImages + folderDateFormatter.format(date) + "/";
		File dateFolderFile = new File(dateFolder);
		if (!dateFolderFile.exists()) {
			dateFolderFile.mkdirs();
		}

		String targetPath = dateFolder + fileDateFormatter.format(date) + ".jpg";
		File targetFile = new File(targetPath);
		//ImageIO.write(rotateImage(image), "JPG", targetFile);
		ImageIO.write(image, "JPG", targetFile);
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
