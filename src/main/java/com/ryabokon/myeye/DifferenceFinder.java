package com.ryabokon.myeye;

import java.awt.image.*;
import java.io.*;

import org.slf4j.*;

import com.ryabokon.myeye.capture.*;
import com.ryabokon.myeye.image.*;

public class DifferenceFinder {
	private static final Logger log = LoggerFactory.getLogger(DifferenceFinder.class);

	private final AbstractImageProvider imageProvider;
	private int[] previousImage;
	private int[] currentImage;

	public DifferenceFinder(AbstractImageProvider imageProvider) throws Throwable {
		this.imageProvider = imageProvider;
	}

	public void startCapture() throws IOException, Throwable {
		while (true) {

			Raster raster = imageProvider.provideImage();

			if (raster != null) {
				long startTime = System.nanoTime();

				if (previousImage == null) {
					this.previousImage = prepareImage(raster);
				} else {
					currentImage = prepareImage(raster);

					int diffAmmount = ImageTools.getDifferenceAmount(previousImage, currentImage, 15);
					if (diffAmmount > 200) {
						imageProvider.saveImage(raster);
					}
					previousImage = currentImage;

					long endTime = System.nanoTime();
					Statistics.addDetectorTime(endTime - startTime);
				}

			}

			else {
				Statistics.printStatistics();
			}

		}

	}

	private int[] prepareImage(Raster raster) {
		int[] image = ImageTools.getRasterAsScaledArray(raster, 4);
		image = BoxFilter.filter(image, raster.getWidth() / 4, raster.getHeight() / 4);
		return image;
	}

}
