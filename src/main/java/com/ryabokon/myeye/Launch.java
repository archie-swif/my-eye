package com.ryabokon.myeye;

import java.net.*;

import com.ryabokon.myeye.capture.*;

public class Launch {

	public static void main(String[] args) throws Throwable {
		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");
		String path = "d:/images/";

		AbstractImageProvider shot = new SingleImageProvider(path, camera);
		// AbstractImageProvider file = new FileSystemImageProvider(path,
		// camera);
		// AbstractImageProvider queue = new QueuedImageProvider(path, camera);
		// AbstractImageProvider pie = new PieImageProvider(path,
		// "cmd.exe /C type src\\test\\resources\\1.jpg");

		DifferenceFinder eye = new DifferenceFinder(shot);
		eye.startCapture();
	}
}
