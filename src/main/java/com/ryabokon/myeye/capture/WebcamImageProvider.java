package com.ryabokon.myeye.capture;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;
import com.ryabokon.myeye.Statistics;

public class WebcamImageProvider extends AbstractImageProvider {

	Dimension customDimension = new Dimension(1280, 720);
	Dimension[] nonStandardResolutions = new Dimension[] { customDimension };
	Webcam webcam;

	public WebcamImageProvider(String pathToStoreImages) throws Throwable {
		super(pathToStoreImages);

		webcam = Webcam.getDefault();
		webcam.setCustomViewSizes(nonStandardResolutions);
		webcam.setViewSize(customDimension);
		webcam.open();
	}

	@Override
	public BufferedImage provideImage() throws Throwable {

		long startTime = System.nanoTime();
		BufferedImage image = webcam.getImage();
		long endTime = System.nanoTime();

		Statistics.addProviderTime(endTime - startTime);

		return image;
	}

}
