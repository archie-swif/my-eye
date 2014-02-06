package com.ryabokon.myeye;

import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

import javax.imageio.*;

import org.slf4j.*;

public class DifferenceFinder {
	private static final Logger log = LoggerFactory.getLogger(DifferenceFinder.class);
	private final SimpleDateFormat fileDateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
	private final SimpleDateFormat folderDateFormatter = new SimpleDateFormat("yyyy.MM.dd");

	private URL camera;
	private final String pathToImagesFolder;

	private BufferedImage previousImage;
	private BufferedImage currentImage;

	public DifferenceFinder(URL camera, String pathToImagesFolder) throws Throwable {
		this.camera = camera;
		this.pathToImagesFolder = pathToImagesFolder;
		startCameraCapture();
	}

	public DifferenceFinder(String pathToImagesFolder) throws Throwable {
		this.pathToImagesFolder = pathToImagesFolder;
		startFolderCapture();
	}

	public void startCameraCapture() throws IOException, Throwable {

		InputStream in = camera.openStream();
		BufferedImage previousImage = ImageIO.read(in);
		in.close();
		// previousImage = ImageIO.read(camera);

		previousImage = prepareImage(previousImage);

		while (true) {
			long startTime = System.nanoTime();

			BufferedImage imageToSaveIfAny = ImageIO.read(camera);
			currentImage = prepareImage(imageToSaveIfAny);

			int diffAmmount = ImageTools.getDifferenceAmount(previousImage, currentImage, 15);
			if (diffAmmount > 200) {
				saveUnusualImage(imageToSaveIfAny);
			}
			previousImage = currentImage;

			long endTime = System.nanoTime();
			Statistics.printImageProcessingTimeWithAvarage(startTime, endTime, String.valueOf(diffAmmount));
		}

	}

	public void startFolderCapture() throws IOException, Throwable {
		while (true) {
			Thread.sleep(5000L);

			File[] listOfFiles = FileBatcher.getImageFilesInFolder(pathToImagesFolder);
			File previousFile = null;

			if (listOfFiles != null && listOfFiles.length >= 2) {

				int i = 0;
				for (File file : listOfFiles) {
					i++;

					if (i == 1) {
						previousFile = file;
						previousImage = ImageIO.read(file);
						previousImage = prepareImage(previousImage);
						continue;
					}

					long startTime = System.nanoTime();

					currentImage = ImageIO.read(file);
					currentImage = prepareImage(currentImage);

					int diffAmmount = ImageTools.getDifferenceAmount(previousImage, currentImage, 15);
					if (diffAmmount > 200) {
						saveUnusualFile(previousFile);
					} else {
						Files.deleteIfExists(previousFile.toPath());
					}

					previousImage = currentImage;
					previousFile = file;

					long endTime = System.nanoTime();
					Statistics.printImageProcessingTime(startTime, endTime, String.valueOf(diffAmmount));
				}
			}
			log.info("Twix!");
		}

	}

	private BufferedImage prepareImage(BufferedImage image) {
		image = ImageTools.scalingResize(image, 128, 160);
		image = ImageTools.blur(image);
		return image;
	}

	private void saveImageDifference(BufferedImage previousImage, BufferedImage currentImage) throws IOException {
		BufferedImage diff = ImageTools.getImageDifferenceAsImage(previousImage, currentImage);
		ImageIO.write(diff, "JPG", new File("diff/image_" + fileDateFormatter.format(new Date()) + ".jpg"));
	}

	private void saveUnusualFile(File file) throws IOException {

		Date date = new Date(file.lastModified());
		String dateFolder = pathToImagesFolder + "/diff/" + folderDateFormatter.format(date) + "/";
		File dateFolderFile = new File(dateFolder);
		if (!dateFolderFile.exists()) {
			dateFolderFile.mkdirs();
		}
		String targetPath = dateFolder + file.getName();
		File targetFile = new File(targetPath);
		Files.deleteIfExists(targetFile.toPath());
		// rotateImage(sourceFile);
		Files.move(file.toPath(), targetFile.toPath());

	}

	private void saveUnusualImage(BufferedImage image) throws IOException {

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
