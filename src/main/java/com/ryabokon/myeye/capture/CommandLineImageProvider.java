package com.ryabokon.myeye.capture;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import org.slf4j.*;

import com.ryabokon.myeye.*;

public class CommandLineImageProvider extends AbstractImageProvider {
	private static final Logger log = LoggerFactory.getLogger(CommandLineImageProvider.class);

	private final String command;

	public CommandLineImageProvider(String pathToImagesFolder, String command) throws Throwable {
		super(pathToImagesFolder);
		this.command = command;
	}

	@Override
	public BufferedImage provideImage() throws Throwable {
		long startTime = System.nanoTime();
		Process proc = Runtime.getRuntime().exec(command);
		InputStream stream = proc.getInputStream();

		StringBuilder ok = new StringBuilder();
		BufferedReader stdOk = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		String s = "";
		while ((s = stdOk.readLine()) != null) {
			ok.append(s);
		}
		System.out.println(ok.toString());

		BufferedImage image = ImageIO.read(stream);

		// Read errors if any appear
		StringBuilder error = new StringBuilder();
		BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		String e = "";
		while ((e = stdError.readLine()) != null) {
			error.append(e);
		}
		log.error(error.toString());
		proc.waitFor();

		long endTime = System.nanoTime();
		Statistics.addProviderTime(endTime - startTime);
		return image;
	}
}
