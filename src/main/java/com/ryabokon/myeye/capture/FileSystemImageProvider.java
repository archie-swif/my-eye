package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

import javax.imageio.*;

import org.apache.commons.io.*;
import org.slf4j.*;

import com.ryabokon.myeye.*;

public class FileSystemImageProvider extends AbstractImageProvider {

	private static final Logger log = LoggerFactory.getLogger(FileSystemImageProvider.class);

	private final URL camera;
	private File[] listOfFiles;
	private int currentFileId = 0;

	private Thread fillerThread;

	public FileSystemImageProvider(String pathToImagesFolder, URL camera) throws Throwable {
		super(pathToImagesFolder);
		this.camera = camera;
	}

	@Override
	public BufferedImage getImage() throws Throwable {

		if (fillerThread == null) {
			FileSystemFiller filler = new FileSystemFiller();
			fillerThread = new Thread(filler);
			fillerThread.start();
		}

		long startTime = System.nanoTime();
		if (listOfFiles == null || listOfFiles.length < 2 || currentFileId == listOfFiles.length) {
			long sleepTime = System.nanoTime();
			Thread.sleep(5000L);
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

	private class FileSystemFiller implements Runnable {

		@Override
		public void run() {
			while (true) {
				long startTime = System.nanoTime();
				String filename = pathToImagesFolder + fileDateFormatter.format(new Date()) + ".jpg";
				File file = new File(filename);
				try {
					FileUtils.copyURLToFile(camera, file, 2000, 2000);
				} catch (IOException e) {
					log.error("Could not get a file");
				}
				long endTime = System.nanoTime();
				Statistics.addProviderTime(endTime - startTime);
			}

		}
	}
}
