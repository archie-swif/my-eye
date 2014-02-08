package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import org.slf4j.*;

import com.ryabokon.myeye.*;

public class PieImageProvider extends AbstractImageProvider {
	private static final Logger log = LoggerFactory.getLogger(PieImageProvider.class);

	private final String command;

	public PieImageProvider(String pathToImagesFolder, String command) throws Throwable {
		super(pathToImagesFolder);
		this.command = command;
	}

	@Override
	public BufferedImage getImage() throws Throwable {
		long startTime = System.nanoTime();
		Process proc = Runtime.getRuntime().exec(command);
		InputStream stream = proc.getInputStream();
		BufferedImage image = ImageIO.read(stream);

		// Read errors if any appear
		StringBuilder error = new StringBuilder();
		BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		String s = "";
		while ((s = stdError.readLine()) != null) {
			error.append(s);
		}

		log.error(error.toString());

		proc.waitFor();
		long endTime = System.nanoTime();
		Statistics.addProviderTime(endTime - startTime);
		return image;
	}

}
