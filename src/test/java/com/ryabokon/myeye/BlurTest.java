package com.ryabokon.myeye;

import java.awt.image.*;
import java.net.*;

import javax.imageio.*;
import org.junit.*;
import com.ryabokon.myeye.image.*;

public class BlurTest
{

    @Test
    public void blurTest() throws Throwable
    {
	URL imageFile = ImageToolsTest.class.getClassLoader().getResource("1.jpg");
	BufferedImage image = ImageIO.read(imageFile);

	BufferedImage blurd = ImageTools.blurImage(image);
	// ImageIO.write(blurd, "JPG", new File("blurd_z.jpg"));

    }
}
