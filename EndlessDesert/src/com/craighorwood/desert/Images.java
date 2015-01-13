package com.craighorwood.desert;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import com.craighorwood.desert.graphics.Image;
public class Images
{
	public static Image dig = loadImage("/img/dig.png");
	public static Image font = loadImage("/img/font.png");
	public static Image panel = loadImage("/img/panel.png");
	public static Image shovel = loadImage("/img/shovel.png");
	public static Image sky = loadImage("/img/sky.png");
	public static Image sprites = loadImage("/img/sprites.png");
	public static Image treasure = loadImage("/img/treasure.png");
	public static Image loadImage(String name)
	{
		try
		{
			BufferedImage image = ImageIO.read(Images.class.getResource(name));
			int w = image.getWidth();
			int h = image.getHeight();
			Image result = new Image(w, h);
			image.getRGB(0, 0, w, h, result.pixels, 0, w);
			for (int i = 0; i < result.pixels.length; i++)
			{
				int in = result.pixels[i];
				int col = in & 0xFFFFFF;
				if (in == 0xFFFF00FF) col = -1;
				result.pixels[i] = col;
			}
			return result;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}