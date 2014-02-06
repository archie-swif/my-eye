package com.ryabokon.myeye;

import java.io.*;

import org.junit.*;
import org.slf4j.*;

public class FileBatcherTest
{

    private static final Logger log = LoggerFactory.getLogger(FileBatcherTest.class);

    @Test
    public void getFilesListTest()
    {
	File imagesDir = new File("src/test/resources");
	FileBatcher fb = new FileBatcher();
	File[] images = fb.getImageFilesInFolder(imagesDir);
	Assert.assertNotNull("Could not get file list from folder", images);
	Assert.assertTrue("Files list from folder is empty", images.length > 0);
    }
}
