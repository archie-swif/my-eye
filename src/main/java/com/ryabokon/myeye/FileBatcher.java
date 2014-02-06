package com.ryabokon.myeye;

import java.io.*;

import org.slf4j.*;

public class FileBatcher
{
    private static final Logger log = LoggerFactory.getLogger(FileBatcher.class);

    public static File[] getImageFilesInFolder(String folder)
    {
	return getImageFilesInFolder(new File(folder));
    }

    public static File[] getImageFilesInFolder(File folder)
    {
	File[] listOfFiles = folder.listFiles(new FilenameFilter()
	{
	    @Override
	    public boolean accept(File dir, String name)
	    {
		return name.endsWith(".jpg");
	    }
	});
	return listOfFiles;
    }

}
