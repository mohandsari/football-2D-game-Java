package pes98.rendering;

public class Screen {
	public int[] pixelsSc;
	public int width, height;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixelsSc = new int[width * height];
	}
	
	public void colorPixel(int x, int y, int color) {
		if(x < 0 || y < 0 || x >= width || y >= height) return;
		pixelsSc[x + y * width] = color;
	}
	
	public void drawImage(int x, int y, int width, int height, Image image) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int color = image.pixelsIm[(int) ((float) i / (float) width * image.width) + (int) ((float) j / (float) height * image.width) * image.width];
				if(color != 0xffff00ff) colorPixel(i + x, j + y, color);//pink
			}
		}
	}
	
	public void drawBall(int x, int y, int radius, int color) {
		for(int i = 0; i < radius * 2; i++) {
			for(int j = 0; j < radius * 2; j++) {
				if((int) Math.sqrt(Math.pow(i - radius, 2) + Math.pow(j - radius, 2)) + 1 <= radius)
					colorPixel(i + x - radius, j + y - radius, color);
			}
		}
	}
}