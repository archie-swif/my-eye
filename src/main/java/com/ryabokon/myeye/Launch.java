package com.ryabokon.myeye;

import com.ryabokon.myeye.capture.*;

public class Launch {

	public static void main(String[] args) throws Throwable {

		//TODO Try to use BifferedIamge with different image types, e.g. TYPE_BYTE_GRAY
		//TODO Use speed4j for performance measurements. Done in performance tests.
		//TODO Switch to using Raster instead of BufferedImage
		//TODO Try Jython\Commons Exec to grab images from python script output on RaspberryPi?
				
		String path = "images/";
		AbstractImageProvider provider;

		provider = new FileSystemImageProvider(path, path + "diff/");

		DifferenceFinder eye = new DifferenceFinder(provider);
		eye.startCapture();
	}
}
