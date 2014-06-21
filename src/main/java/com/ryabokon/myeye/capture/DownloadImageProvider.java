package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

import org.apache.commons.io.*;
import org.slf4j.*;

import com.ryabokon.myeye.*;
import com.ryabokon.myeye.image.*;

public class DownloadImageProvider extends AbstractImageProvider {

	private static final Logger log = LoggerFactory.getLogger(DownloadImageProvider.class);

	private final String temporaryImageStrage;

	private final URL camera;
	private File[] listOfFiles;
	private int currentFileId = 0;

	private Thread downloaderThread;

	public DownloadImageProvider(String temporaryImageStrage, String pathToSaveImages, URL camera)
			throws Throwable {
		super(pathToSaveImages);
		this.temporaryImageStrage = temporaryImageStrage;
		this.camera = camera;
	}

	@Override
	public BufferedImage provideImage() throws Throwable {

		if (downloaderThread == null) {
			ImageDownloader imageDownloader = new ImageDownloader();
			downloaderThread = new Thread(imageDownloader);
			downloaderThread.start();
		}

		long startTime = System.nanoTime();
		if (listOfFiles == null || listOfFiles.length < 2 || currentFileId == listOfFiles.length) {
			long sleepTime = System.nanoTime();
			Thread.sleep(5000L);
			startTime = sleepTime;
			listOfFiles = getImageFilesInFolder(temporaryImageStrage);
			currentFileId = 0;
			if (listOfFiles == null || listOfFiles.length < 2)
				return null;
		}

		File file = listOfFiles[currentFileId];
		BufferedImage image = ImageTools.getBufferedImage(file);
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

	private class ImageDownloader implements Runnable {

		@Override
		public void run() {
			while (true) {
				long startTime = System.nanoTime();
				String filename = temporaryImageStrage + fileDateFormatter.format(new Date()) + ".jpg";
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
