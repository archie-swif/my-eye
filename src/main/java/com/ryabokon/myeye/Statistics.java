package com.ryabokon.myeye;

import org.slf4j.*;

public class Statistics {
	private static final Logger log = LoggerFactory.getLogger(Statistics.class);
	private static long imageProviderTime;
	private static long imageConsumerTime;
	private static long imageDetectorTime;
	private static int p = 0;
	private static int c = 0;
	private static int d = 0;

	public static void addProviderTime(long delta, String... info) {
		imageProviderTime = getNewAvarageTime(delta, imageProviderTime, p);
		p++;
	}

	public static void addConsumerTime(long delta, String... info) {
		imageConsumerTime = getNewAvarageTime(delta, imageConsumerTime, c);
		c++;
	}

	public static void addDetectorTime(long delta, String... info) {
		imageDetectorTime = getNewAvarageTime(delta, imageDetectorTime, d);
		d++;
	}

	private static long getNewAvarageTime(long delta, long currentAvarageTime, int counter) {

		if (currentAvarageTime == 0) {
			currentAvarageTime = delta;
		} else {
			currentAvarageTime = (currentAvarageTime * counter + delta) / (counter + 1);
		}
		counter++;
		return currentAvarageTime;
	}

	public static void printStatistics() {
		StringBuffer sb = new StringBuffer();
		sb.append("P: [" + nsecsToTime(imageProviderTime) + "], C:[" + nsecsToTime(imageConsumerTime) + "], D:["
				+ nsecsToTime(imageDetectorTime) + "], ALL["
				+ nsecsToTime(imageProviderTime + imageConsumerTime + imageDetectorTime) + "]");
		log.info(sb.toString());
	}

	public static void printImageProcessingTime(long startTime, long endTime, String... otherImprotatntInfo) {

		long delta = endTime - startTime;
		StringBuffer sb = new StringBuffer();
		sb.append("Processed in [").append(nsecsToTime(delta)).append("]");
		for (String s : otherImprotatntInfo) {
			sb.append(", ").append(s);
		}
		log.info(sb.toString());
	}

	private static String nsecsToTime(long nanoSecs) {
		int minutes = (int) (nanoSecs / 60000000000.0);
		int seconds = (int) (nanoSecs / 1000000000.0) - minutes * 60;
		int millisecs = (int) ((nanoSecs / 1000000000.0 - (seconds + minutes * 60)) * 1000);

		if (minutes == 0 && seconds == 0)
			return millisecs + "." + (nanoSecs - (millisecs * 1000000)) / 1000 + "ms";
		else if (minutes == 0 && millisecs == 0)
			return seconds + "s";
		else if (seconds == 0 && millisecs == 0)
			return minutes + "min";
		else if (minutes == 0)
			return seconds + "s " + millisecs + "ms";
		else if (seconds == 0)
			return minutes + "min " + millisecs + "ms";
		else if (millisecs == 0)
			return minutes + "min " + seconds + "s";

		return minutes + "min " + seconds + "s " + millisecs + "ms";
	}
}
