package com.ryabokon.myeye;

import java.net.*;

import com.ryabokon.myeye.capture.*;

public class Launch {

	public static void main(String[] args) throws Throwable {

		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");
		String path = "images/";
		AbstractImageProvider provider;

		// provider = new QueuedImageProvider(path, camera);
		provider = new FileSystemImageProvider(path, path + "diff/");

		DifferenceFinder eye = new DifferenceFinder(provider);
		eye.startCapture();
	}
}
