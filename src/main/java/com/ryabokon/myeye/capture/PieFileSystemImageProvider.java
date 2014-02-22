package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;
import java.nio.file.*;

import javax.imageio.*;

import org.slf4j.*;

import com.ryabokon.myeye.*;

public class PieFileSystemImageProvider extends AbstractImageProvider {

	private static final Logger log = LoggerFactory.getLogger(PieFileSystemImageProvider.class);

	private final String command;

	private File[] listOfFiles;
	private int currentFileId = 0;

	public PieFileSystemImageProvider(String pathToImagesFolder, String command) throws Throwable {
		super(pathToImagesFolder);
		this.command = command;
	}

	@Override
	public BufferedImage getImage() throws Throwable {

		long startTime = System.nanoTime();
		if (listOfFiles == null || listOfFiles.length < 2 || currentFileId == listOfFiles.length) {
			long sleepTime = System.nanoTime();
			Thread.sleep(2000L);
			startTime = sleepTime;
			listOfFiles = getImageFilesInFolder(pathToImagesFolder);
			currentFileId = 0;
			if (listOfFiles == null || listOfFiles.length < 2)
				return null;
		}

		File file = listOfFiles[currentFileId];
		BufferedImage image = ImageIO.read(file);
		Files.deleteIfExists(file.toPath());

		currentFileId++;

		long endTime = System.nanoTime();
		Statistics.addConsumerTime(endTime - startTime);
		return image;
	}

	private File[] getImageFilesInFolder(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg");
			}
		});
		return listOfFiles;
	}

}
