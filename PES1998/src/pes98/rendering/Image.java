package pes98.rendering;

import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Image {
	public int[] pixelsIm;
	public int width, height;
	
	public Image(int width, int height, int[] pixels) {
		this.width = width;
		this.height = height;
		this.pixelsIm = pixels;
	}
	
	public static Image loadImage(String name) {
		int width = 0, height = 0;
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(Image.class.getResource(name));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch(IOException e){
			e.printStackTrace();
		}
		return new Image(width, height, pixels);
	}
}
