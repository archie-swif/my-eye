package com.ryabokon.myeye;

import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.ecyrd.speed4j.StopWatch;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGDecodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

@Ignore
public class ImageLoadPerformanceTest {

	private static final int ITERATIONS = 100;
	public StopWatch sw = new StopWatch();

	@Rule
	public TestName name = new TestName();

	@Before
	public void startTimer() {
		sw.start();
	}

	@After
	public void stopTimer() {
		sw.stop(name.getMethodName());
		System.out.println(sw.toString(ITERATIONS));
	}

	@Test
	public void imageIOURLest() throws Throwable {
		URI uri = ImageToolsTest.class.getClassLoader().getResource("pie.jpg").toURI();
		for (int i = 0; i < ITERATIONS; i++) {
			BufferedImage image = ImageIO.read(Files.newInputStream(Paths.get(uri)));
		}
	}

	@Test
	public void imageIOBufferedISTest() throws Throwable {
		for (int i = 0; i < ITERATIONS; i++) {
			InputStream is = ImageToolsTest.class.getClassLoader().getResourceAsStream("pie.jpg");
			BufferedImage image = ImageIO.read(new BufferedInputStream(is));
		}
	}

	@Test
	public void imageIOISTest() throws Throwable {
		for (int i = 0; i < ITERATIONS; i++) {
			InputStream is = ImageToolsTest.class.getClassLoader().getResourceAsStream("pie.jpg");
			BufferedImage image = ImageIO.read(is);
		}
	}

	@Test
	public void cacheTest() throws Throwable {
		boolean isCache = ImageIO.getUseCache();
		ImageIO.setUseCache(false);

		URL imageFile = ImageToolsTest.class.getClassLoader().getResource("pie.jpg");
		for (int i = 0; i < ITERATIONS; i++) {
			BufferedImage image = ImageIO.read(imageFile);
		}
		ImageIO.setUseCache(isCache);
	}

	@Test
	public void decoderTest() throws Throwable {
		URL imageUrl = ImageToolsTest.class.getClassLoader().getResource("pie.jpg");

		for (int i = 0; i < ITERATIONS; i++) {
			File file = new File(imageUrl.toURI());
			ByteArrayInputStream imageBytes = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageBytes);
			BufferedImage image = decoder.decodeAsBufferedImage();
		}
	}
	
	@Test
	public void decoderRasterTest() throws Throwable {
		URL imageUrl = ImageToolsTest.class.getClassLoader().getResource("pie.jpg");

		for (int i = 0; i < ITERATIONS; i++) {
			File file = new File(imageUrl.toURI());
			ByteArrayInputStream imageBytes = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageBytes);
			Raster image = decoder.decodeAsRaster();
		}
	}
	
	@Test
	@Ignore
	public void decoderTypeTest() throws Throwable {
		InputStream is = new FileInputStream("D:/Language/GIT/my-eye/src/test/resources/pie.jpg");

		for (int i = 0; i < ITERATIONS; i++) {			
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(is);			
			BufferedImage image = decoder.decodeAsBufferedImage();
		}
	}

	@Test
	public void toolkitTest() throws Throwable {
		for (int i = 0; i < ITERATIONS; i++) {
			InputStream is = ImageToolsTest.class.getClassLoader().getResourceAsStream("pie.jpg");
			DataInputStream dis = new DataInputStream(is);
			byte abyte0[] = new byte[dis.available()];
			dis.readFully(abyte0);
			dis.close();
			Image image = Toolkit.getDefaultToolkit().createImage(abyte0);
			Image tmp = new ImageIcon(image).getImage();

			BufferedImage buffered = new BufferedImage(1280, 1024, BufferedImage.TYPE_INT_ARGB);
			buffered.getGraphics().drawImage(tmp, 0, 0, null);
		}
	}

}
