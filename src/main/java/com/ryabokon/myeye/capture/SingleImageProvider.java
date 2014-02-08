package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;

import com.ryabokon.myeye.*;

public class SingleImageProvider extends AbstractImagePtovider {

	private final URL camera;

	public SingleImageProvider(String pathToImagesFolder, URL camera) throws Throwable {
		super(pathToImagesFolder);
		this.camera = camera;
	}

	@Override
	public BufferedImage getImage() throws Throwable {
		long startTime = System.nanoTime();
		InputStream in = camera.openStream();
		BufferedImage image = ImageIO.read(in);
		in.close();
		long endTime = System.nanoTime();
		Statistics.addProviderTime(endTime - startTime);
		return image;
	}

}
