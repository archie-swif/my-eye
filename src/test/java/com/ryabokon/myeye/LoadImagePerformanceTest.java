package com.ryabokon.myeye;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;

import javax.imageio.*;
import javax.swing.*;

import org.apache.commons.io.*;
import org.junit.*;
import org.junit.rules.*;

import com.ecyrd.speed4j.*;
import com.sun.image.codec.jpeg.*;

@Ignore
public class LoadImagePerformanceTest {

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
		InputStream is = new FileInputStream("src/test/resources/pie.jpg");

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

	@Test
	public void openAndCloseStreamTest() throws Throwable {
		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");

		for (int i = 0; i < 100; i++) {
			InputStream in = camera.openStream();
			BufferedImage image = ImageIO.read(in);
			Assert.assertTrue(image.getHeight() == 1024);
			in.close();
		}

	}

	@Test
	public void dontOpenStraemTest() throws Throwable {
		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");

		for (int i = 0; i < 100; i++) {
			BufferedImage image = ImageIO.read(camera);
			Assert.assertTrue(image.getHeight() == 1024);
		}

	}

	@Test
	public void decoderAndURLTest() throws Throwable {
		URL camera = new URL("http", "192.168.2.107", 80, "/image.jpg");

		for (int i = 0; i < 100; i++) {
			InputStream in = camera.openStream();
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
			BufferedImage image = decoder.decodeAsBufferedImage();
			Assert.assertTrue(image.getHeight() == 1024);
			in.close();
		}

	}

}
