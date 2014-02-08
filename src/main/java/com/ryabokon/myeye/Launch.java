package com.ryabokon.myeye;

import java.net.*;

import com.ryabokon.myeye.capture.*;

public class Launch {

	public static void main(String[] args) throws Throwable {
		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");
		String path = "d:/images/";

		AbstractImagePtovider shot = new SingleImageProvider(path, camera);
		// ImageProvider file = new FileSystemImageProvider(path, camera);
		//ImageProvider queue = new QueueImageProvider(path, camera);

		DifferenceFinder eye = new DifferenceFinder(shot);
		eye.startCapture();
	}
}
