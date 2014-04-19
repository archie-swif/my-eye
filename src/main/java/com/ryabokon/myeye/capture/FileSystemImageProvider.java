package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;
import java.nio.file.*;

import org.slf4j.*;

import com.ryabokon.myeye.*;
import com.ryabokon.myeye.image.*;

public class FileSystemImageProvider extends AbstractImageProvider {

	private static final Logger log = LoggerFactory.getLogger(FileSystemImageProvider.class);

	private final String sourceImageFolder;

	private File[] listOfFiles;
	private int currentFileId = 0;

	public FileSystemImageProvider(String sourceImageFolder, String pathToImagesFolder) throws Throwable {
		super(pathToImagesFolder);
		this.sourceImageFolder = sourceImageFolder;
	}

	@Override
	public BufferedImage provideImage() throws Throwable {

		long startTime = System.nanoTime();
		if (listOfFiles == null || listOfFiles.length < 2 || currentFileId == -1) {
			long sleepTime = System.nanoTime();
			// Thread.sleep(1L);
			startTime = sleepTime;
			listOfFiles = getImageFilesInFolder(sourceImageFolder);
			currentFileId = listOfFiles.length - 1;
			if (listOfFiles == null || listOfFiles.length < 2)
				return null;
		}

		File file = listOfFiles[currentFileId];
		BufferedImage image = ImageTools.getBufferedImage(file);
		if (image == null) {
			Thread.sleep(100L);
			image = ImageTools.getBufferedImage(file);
		}
		Files.deleteIfExists(file.toPath());

		currentFileId--;

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
