package com.ryabokon.myeye;

import java.net.*;

import com.ryabokon.myeye.capture.*;

public class Launch {

	public static void main(String[] args) throws Throwable {
		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");
		String path = "d:/images/";

		String command = "raspistill -t 10 -n -cfx 128:128 -q 50  -h 1024 -w 1280";
		if (args.length != 0 && args[0] != null) {
			command = args[0];
		}

		AbstractImageProvider shot = new SingleImageProvider(path, camera);
		// AbstractImageProvider file = new FileSystemImageProvider(path,
		// camera);
		// AbstractImageProvider queue = new QueuedImageProvider(path, camera);
		// AbstractImageProvider pie = new PieImageProvider(path,
		// command);
		// AbstractImageProvider pieFile = new PieFileSystemImageProvider(path,
		// command);

		DifferenceFinder eye = new DifferenceFinder(shot);
		eye.startCapture();
	}
}
