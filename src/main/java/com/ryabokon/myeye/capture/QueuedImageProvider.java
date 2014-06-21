package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.net.*;
import java.util.*;

import com.ryabokon.myeye.*;
import com.ryabokon.myeye.image.*;

public class QueuedImageProvider extends AbstractImageProvider {

	private final URL camera;
	private final List<BufferedImage> list = Collections.synchronizedList(new LinkedList<BufferedImage>());
	private Thread fillerThread;

	public QueuedImageProvider(String pathToStoreImages, URL camera) throws Throwable {
		super(pathToStoreImages);
		this.camera = camera;
	}

	@Override
	public BufferedImage provideImage() throws Throwable {
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
		Thread.sleep(1000L);
		return null;
	}

	private class ImageQueueFiller implements Runnable {

		@Override
		public void run() {
			while (true) {
				long startTime = System.nanoTime();
				BufferedImage image = ImageTools.getBufferedImage(camera);

				if (image != null) {
					list.add(image);
				}
				long endTime = System.nanoTime();
				Statistics.addProviderTime(endTime - startTime);
			}

		}
	}

}
