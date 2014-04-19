package com.ryabokon.myeye;

import java.net.*;

import com.ryabokon.myeye.capture.*;

public class Launch {

	public static void main(String[] args) throws Throwable {
		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");
		String path = "images/";

		// String command =
		// "raspistill -t 10 -n -cfx 128:128 -q 50  -h 1024 -w 1280 -o -";
		// String command = "python out.py";
		// if (args.length != 0 && args[0] != null) {
		// command = args[0];
		// }

		// AbstractImageProvider provider = new SingleImageProvider(path,
		// camera);
		// AbstractImageProvider provider = new FileSystemImageProvider(path,
		// path + "diff/");
		AbstractImageProvider provider = new QueuedImageProvider(path, camera);
		// AbstractImageProvider provider = new CommandLineImageProvider(path,
		// command);
		// AbstractImageProvider provider = new
		// UrlToFileSystemImageProvider(path, path + "diff/", camera);

		DifferenceFinder eye = new DifferenceFinder(provider);
		eye.startCapture();
	}
}
