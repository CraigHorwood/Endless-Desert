package com.craighorwood.desert.level;
import com.craighorwood.desert.graphics.Image;
public class DefaultTile extends Tile
{
	public DefaultTile()
	{
		image = new Image(16, 16);
		for (int i = 0; i < image.pixels.length; i++)
		{
			int col = 0xEDC9AF;
			if (random.nextDouble() < 0.1) col = 0xD88B52;
			image.pixels[i] = col;
		}
	}
}