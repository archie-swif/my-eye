package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.net.*;

import com.ryabokon.myeye.*;
import com.ryabokon.myeye.image.*;

public class SingleImageProvider extends AbstractImageProvider {

	private final URL camera;

	public SingleImageProvider(String pathToStoreImages, URL camera) throws Throwable {
		super(pathToStoreImages);
		this.camera = camera;
	}

	@Override
	public BufferedImage provideImage() throws Throwable {
		long startTime = System.nanoTime();
		BufferedImage image = ImageTools.getBufferedImage(camera);
		long endTime = System.nanoTime();
		Statistics.addProviderTime(endTime - startTime);
		return image;
	}

}
