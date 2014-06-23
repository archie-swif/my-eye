package com.ryabokon.myeye;

import java.net.*;

import com.ryabokon.myeye.capture.*;

public class Launch {

	public static void main(String[] args) throws Throwable {

		//TODO Try to use BifferedIamge with different image types, e.g. TYPE_BYTE_GRAY
		//TODO Use speed4j for performance measurements. Done in performance tests.
		//TODO Switch to using Raster instead of BufferedImage
		//TODO Try Jython\Commons Exec to grab images from python script output on RaspberryPi?
				
		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");
		String path = "images/";
		AbstractImageProvider provider;

		// String command =
		// "raspistill -t 10 -n -cfx 128:128 -q 50  -h 1024 -w 1280 -o -";
		// String command_py = "python out.py";
		//
		// if (args.length != 0 && args[0] != null) {
		// command = args[0];
		// }

		// provider = new SingleImageProvider(path,camera);
		//provider = new FileSystemImageProvider(path, path + "diff/");
		provider = new QueuedImageProvider(path, camera);
		// provider = new CommandLineImageProvider(path, command);
		// provider = new DownloadImageProvider(path, path + "diff/", camera);

		DifferenceFinder eye = new DifferenceFinder(provider);
		eye.startCapture();
	}
}
