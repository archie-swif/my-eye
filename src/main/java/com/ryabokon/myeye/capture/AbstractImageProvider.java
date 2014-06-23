package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.imageio.*;

import org.slf4j.*;

import com.ryabokon.myeye.image.*;

public abstract class AbstractImageProvider {

	private static final Logger log = LoggerFactory.getLogger(AbstractImageProvider.class);
	protected final SimpleDateFormat fileDateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
	protected final SimpleDateFormat folderDateFormatter = new SimpleDateFormat("yyyy.MM.dd");

	private final String pathToSaveImages;

	public AbstractImageProvider(String pathToStoreImages) {
		this.pathToSaveImages = pathToStoreImages;
	}

	public abstract Raster provideImage() throws Throwable;

	public void saveImage(Raster raster) throws Throwable {

		Date date = new Date();

		String dateFolder = pathToSaveImages + folderDateFormatter.format(date) + "/";
		File dateFolderFile = new File(dateFolder);
		if (!dateFolderFile.exists()) {
			dateFolderFile.mkdirs();
		}

		String targetPath = dateFolder + fileDateFormatter.format(date) + ".jpg";
		File targetFile = new File(targetPath);

		//TODO too complex
		BufferedImage buffImage = ImageTools.writeImageArrayToBufferedImage(raster.getHeight(), raster.getWidth(), ImageTools.getRasterAsArray(raster));
		ImageIO.write(buffImage, "JPG", targetFile);
	}

}
