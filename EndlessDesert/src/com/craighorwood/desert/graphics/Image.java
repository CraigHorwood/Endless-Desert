package com.craighorwood.desert.graphics;
import com.craighorwood.desert.Images;
public class Image
{
	public final int width;
	public final int height;
	public final int[] pixels;
	private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";
	public Image(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	public void draw(Image image, int xOffs, int yOffs)
	{
		for (int y = 0; y < image.height; y++)
		{
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;
			for (int x = 0; x < image.width; x++)
			{
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;
				int src = image.pixels[x + y * image.width];
				if (src >= 0) pixels[xPix + yPix * width] = src;
			}
		}
	}
	public void draw(Image image, int xOffs, int yOffs, int xo, int yo, int w, int h)
	{
		draw(image, xOffs, yOffs, xo, yo, w, h, 0xFFFFFF);
	}
	public void draw(Image image, int xOffs, int yOffs, int xo, int yo, int w, int h, int col)
	{
		for (int y = 0; y < h; y++)
		{
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;
			for (int x = 0; x < w; x++)
			{
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;
				int src = image.pixels[(x + xo) + (y + yo) * image.width];
				if (src >= 0) pixels[xPix + yPix * width] = src & col;
			}
		}
	}
	public void draw(String string, int x, int y, int col)
	{
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++)
		{
			int ch = chars.indexOf(string.charAt(i));
			if (ch < 0) continue;
			int xx = ch % 26;
			int yy = ch / 26;
			draw(Images.font, x + i * 6, y, xx * 6, yy * 6, 6, 6, col);
		}
	}
	public void goldenDraw(String string, int x, int y)
	{
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++)
		{
			int ch = chars.indexOf(string.charAt(i));
			if (ch < 0) continue;
			int xx = ch % 26;
			int yy = ch / 26;
			int col = 0xFFC000;
			if (Math.random() < 0.5) col = 0xFFF000;
			draw(Images.font, x + i * 6, y, xx * 6, yy * 6, 6, 6, col);
		}
	}
	public void fill(int x0, int y0, int x1, int y1, int col)
	{
		for (int y = y0; y < y1; y++)
		{
			for (int x = x0; x < x1; x++)
			{
				pixels[x + y * width] = col;
			}
		}
	}
}