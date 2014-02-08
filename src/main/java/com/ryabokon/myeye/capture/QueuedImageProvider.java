package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.imageio.*;

import com.ryabokon.myeye.*;

public class QueuedImageProvider extends AbstractImagePtovider {

	private final URL camera;
	private final List<BufferedImage> list = Collections.synchronizedList(new LinkedList<BufferedImage>());
	private Thread fillerThread;

	public QueuedImageProvider(String pathToImagesFolder, URL camera) throws Throwable {
		super(pathToImagesFolder);
		this.camera = camera;
	}

	@Override
	public BufferedImage getImage() throws Throwable {
		if (fillerThread == null) {
			ImageQueueFiller filler = new ImageQueueFiller();
			fillerThread = new Thread(filler);
			fillerThread.start();
		}

		long startTime = System.nanoTime();
		if (list.size() != 0) {

			BufferedImage image = list.remove(0);
			long endTime = System.nanoTime();
			Statistics.addConsumerTime(endTime - startTime);
			return image;
		}
		Thread.sleep(3000L);
		return null;
	}

	private class ImageQueueFiller implements Runnable {

		@Override
		public void run() {
			while (true) {
				long startTime = System.nanoTime();
				BufferedImage image = null;
				try {
					InputStream in = camera.openStream();
					image = ImageIO.read(in);
					in.close();
				} catch (IOException e) {

				}

				if (image != null) {
					list.add(image);
					long endTime = System.nanoTime();
					Statistics.addProviderTime(endTime - startTime);
				}
			}

		}
	}

}
