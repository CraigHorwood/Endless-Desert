package com.craighorwood.desert.graphics;
import com.craighorwood.desert.*;
import com.craighorwood.desert.entity.*;
import com.craighorwood.desert.level.*;
public class Image3D extends Image
{
	private double[] depthBuffer;
	private double xCam, zCam, cos, sin, fov, xCenter, yCenter, rot;
	public Image3D(int width, int height)
	{
		super(width, height);
		depthBuffer = new double[width * height];
		xCenter = width / 2.0;
		yCenter = height / 4.0;
	}
	public void render(Game game)
	{
		for (int i = 0; i < width * height; i++)
		{
			depthBuffer[i] = 10000;
		}
		Player player = game.level.player;
		rot = player.rot;
		xCam = player.x - Math.sin(rot) * 0.3;
		zCam = player.z - Math.cos(rot) * 0.3;
		cos = Math.cos(rot);
		sin = Math.sin(rot);
		fov = height;
		Level level = game.level;
		int r = 6;
		int xCenter = (int) (Math.floor(xCam));
		int zCenter = (int) (Math.floor(zCam));
		for (int zt = zCenter - r; zt <= zCenter + r; zt++)
		{
			for (int xt = xCenter - r; xt <= xCenter + r; xt++)
			{
				Tile tile = level.getTile(xt, zt, true);
				if (tile.canSpawnAnimal > 0 && !tile.hasSpawnedAnimal) level.spawnAnimal(tile, xt, zt);
				for (int j = 0; j < tile.entities.size(); j++)
				{
					Entity e = tile.entities.get(j);
					if (e.sprite != null) renderSprite(e.x + e.sprite.x, -e.sprite.y, e.z + e.sprite.z, e.sprite.image, e.sprite.col);
				}
				for (int j = 0; j < tile.particles.size(); j++)
				{
					Particle p = tile.particles.get(j);
					renderSprite(xt + p.x, -p.y, zt + p.z, p.image, p.col);
				}
				Sprite sprite = tile.sprite;
				if (sprite != null) renderSprite(xt + sprite.x, -sprite.y, zt + sprite.z, sprite.image, sprite.col);
			}
		}
		renderSand(level);
		renderSky();
	}
	private void renderSand(Level level)
	{
		for (int y = 0; y < height; y++)
		{
			double yd = ((y + 0.5) - yCenter) / fov;
			double zd = 5.6 / yd;
			boolean sky = false;
			if (yd < 0)
			{
				sky = true;
				zd = 2.4 / -yd;
			}
			for (int x = 0; x < width; x++)
			{
				if (depthBuffer[x + y * width] <= zd) continue;
				double xd = ((xCenter - x) / fov) * zd;
				double xx = xd * cos + zd * sin + (xCam + 0.5) * 8;
				double zz = zd * cos - xd * sin + (zCam + 0.5) * 8;
				int xPix = (int) (xx * 2);
				int zPix = (int) (zz * 2);
				int xTile = xPix >> 4;
				int zTile = zPix >> 4;
				Tile tile = level.getTile(xTile, zTile, false);
				if (sky) depthBuffer[x + y * width] = -1;
				else
				{
					depthBuffer[x + y * width] = zd;
					pixels[x + y * width] = tile.image.pixels[(xPix & 0xF) + ((zPix & 0xF) << 4)];
				}
			}
		}
	}
	private void renderSprite(double x, double y, double z, int image, int color)
	{
		double xc = (x - xCam) * 2 - cos * 0.2;
		double yc = (y + 0.2) * 2;
		double zc = (z - zCam) * 2 - cos * 0.2;
		double xx = xc * cos - zc * sin;
		double yy = yc;
		double zz = zc * cos + xc * sin;
		if (zz < 0.1) return;
		double xPixel = xCenter - (xx / zz * fov);
		double yPixel = (yy / zz * fov + yCenter);
		double xPixel0 = xPixel - height / zz;
		double xPixel1 = xPixel + height / zz;
		double yPixel0 = yPixel - height / zz;
		double yPixel1 = yPixel + height / zz;
		int xp0 = (int) Math.ceil(xPixel0);
		int xp1 = (int) Math.ceil(xPixel1);
		int yp0 = (int) Math.ceil(yPixel0);
		int yp1 = (int) Math.ceil(yPixel1);
		if (xp0 < 0) xp0 = 0;
		if (xp1 > width) xp1 = width;
		if (yp0 < 0) yp0 = 0;
		if (yp1 > height) yp1 = height;
		zz *= 4;
		for (int yp = yp0; yp < yp1; yp++)
		{
			double ypr = (yp - yPixel0) / (yPixel1 - yPixel0);
			int yt = (int) (ypr * 16);
			for (int xp = xp0; xp < xp1; xp++)
			{
				double xpr = (xp - xPixel0) / (xPixel1 - xPixel0);
				int xt = (int) (xpr * 16);
				if (depthBuffer[xp + yp * width] > zz)
				{
					int col = Images.sprites.pixels[(xt + ((image & 3) << 4)) + ((yt + ((image >> 2) << 4)) << 6)];
					if (col >= 0)
					{
						pixels[xp + yp * width] = col & color;
						depthBuffer[xp + yp * width] = zz;
					}
				}
			}
		}
	}
	public void renderSky()
	{
		for (int i = 0; i < width * height; i++)
		{
			if (depthBuffer[i] < 0)
			{
				int xx = ((int) Math.floor((i % width) - rot * 512 / (Math.PI * 2))) & 0x1FF;
				int yy = i / width;
				pixels[i] = Images.sky.pixels[xx + (yy << 9)];
			}
		}
	}
}