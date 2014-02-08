package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.imageio.*;

public abstract class AbstractImagePtovider {

	protected final SimpleDateFormat fileDateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
	protected final SimpleDateFormat folderDateFormatter = new SimpleDateFormat("yyyy.MM.dd");

	protected final String pathToImagesFolder;

	public AbstractImagePtovider(String pathToImagesFolder) {
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
		ImageIO.write(image, "JPG", targetFile);

	}

}
