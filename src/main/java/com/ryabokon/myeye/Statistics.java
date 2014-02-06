package com.ryabokon.myeye;

import org.slf4j.*;

public class Statistics
{
    private static final Logger log = LoggerFactory.getLogger(Statistics.class);
    private static long avarage;
    private static int i = 1;

    public static void printImageProcessingTimeWithAvarage(long startTime, long endTime, String... otherImprotatntInfo)
    {

	long delta = endTime - startTime;
	if (avarage == 0)
	{
	    avarage = delta;
	} else
	{
	    avarage = (avarage * i + delta) / (i + 1);
	    // avarage = avarage + ((delta - avarage) / (i + 1));
	}
	i++;
	if (i < 20)
	{
	    i++;
	}

	StringBuffer sb = new StringBuffer();
	sb.append("Processed in [").append(nsecsToTime(delta)).append("] ~[" + nsecsToTime(avarage) + "]");
	for (String s : otherImprotatntInfo)
	{
	    sb.append(", ").append(s);
	}
	log.info(sb.toString());
    }

    public static void printImageProcessingTime(long startTime, long endTime, String... otherImprotatntInfo)
    {

	long delta = endTime - startTime;
	StringBuffer sb = new StringBuffer();
	sb.append("Processed in [").append(nsecsToTime(delta)).append("]");
	for (String s : otherImprotatntInfo)
	{
	    sb.append(", ").append(s);
	}
	log.info(sb.toString());
    }

    private static String nsecsToTime(long nanoSecs)
    {
	int minutes = (int) (nanoSecs / 60000000000.0);
	int seconds = (int) (nanoSecs / 1000000000.0) - minutes * 60;
	int millisecs = (int) ((nanoSecs / 1000000000.0 - (seconds + minutes * 60)) * 1000);

	if (minutes == 0 && seconds == 0)
	    return millisecs + "ms";
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
