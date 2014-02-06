package com.ryabokon.myeye;

import java.net.*;

public class Launch
{

    public static void main(String[] args) throws Throwable
    {
	URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");
	String path = "d:/images/";
	if (args.length != 0 && args[0] != null)
	{
	    path = args[0];
	}
	DifferenceFinder df = new DifferenceFinder(camera, path);

	// DifferenceFinder df = new DifferenceFinder(path);

    }
}
