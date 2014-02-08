package com.ryabokon.myeye;

import java.awt.image.*;
import java.io.*;

import org.slf4j.*;

import com.ryabokon.myeye.capture.*;
import com.ryabokon.myeye.image.*;

public class DifferenceFinder {
	private static final Logger log = LoggerFactory.getLogger(DifferenceFinder.class);

	private final AbstractImagePtovider imageProvider;

	private BufferedImage previousImage;
	private BufferedImage currentImage;

	public DifferenceFinder(AbstractImagePtovider imageProvider) throws Throwable {
		this.imageProvider = imageProvider;
	}

	public void startCapture() throws IOException, Throwable {
		while (true) {

			BufferedImage image = imageProvider.getImage();

			if (image != null) {
				long startTime = System.nanoTime();
				if (previousImage == null) {
					this.previousImage = prepareImage(image);
				}

				else {
					currentImage = prepareImage(image);

					int diffAmmount = ImageTools.getDifferenceAmount(previousImage, currentImage, 15);
					if (diffAmmount > 200) {
						imageProvider.saveImage(image);
					}
					previousImage = currentImage;

					long endTime = System.nanoTime();
					Statistics.addDetectorTime(endTime - startTime, String.valueOf(diffAmmount));
				}
				Statistics.printStatistics();
			}

			else {
				log.info("Twix!");

			}

		}

	}

	private BufferedImage prepareImage(BufferedImage image) {
		image = ImageTools.scalingResize(image, 160, 128);
		image = ImageTools.blur(image);
		return image;
	}

}
