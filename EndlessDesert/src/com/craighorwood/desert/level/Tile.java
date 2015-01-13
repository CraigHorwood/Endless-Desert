package com.craighorwood.desert.level;
import java.util.*;
import com.craighorwood.desert.Images;
import com.craighorwood.desert.entity.*;
import com.craighorwood.desert.graphics.*;
public class Tile
{
	protected static final Random random = new Random();
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Particle> particles = new ArrayList<Particle>();
	public Sprite sprite;
	public boolean dug = false;
	private boolean hasBuriedItem = false;
	public boolean hasSpawnedAnimal = false;
	public int canSpawnAnimal = 0;
	public Image image;
	private Image imageOrig;
	public Tile()
	{
		image = new Image(16, 16);
		imageOrig = new Image(16, 16);
		for (int i = 0; i < image.pixels.length; i++)
		{
			int col = 0xEDC9AF;
			if (random.nextDouble() < 0.1) col = 0xD88B52;
			image.pixels[i] = col;
			imageOrig.pixels[i] = col;
		}
		if (random.nextDouble() < 0.03) sprite = new Sprite(0, 0, 0, random.nextInt(2));
		else hasBuriedItem = (random.nextBoolean() ^ ((random.nextBoolean() || random.nextBoolean()) && random.nextBoolean())) && random.nextBoolean();
	}
	public void addEntity(Entity e)
	{
		entities.add(e);
	}
	public void removeEntity(Entity e)
	{
		entities.remove(e);
	}
	public void setSelected(boolean selected)
	{
		if (selected)
		{
			int col = 0xDD8B52;
			if (hasBuriedItem) col = 0xFF0000;
			for (int i = 0; i < image.pixels.length; i++)
			{
				int x = i & 0xF;
				int y = i >> 4;
				if (x == 0 || y == 0 || x == 15 || y == 15)
				{
					image.pixels[i] = col;
				}
			}
		}
		else
		{
			for (int i = 0; i < image.pixels.length; i++)
			{
				image.pixels[i] = imageOrig.pixels[i];
			}
		}
	}
	public void dig(Level level, int x, int z)
	{
		if (!dug)
		{
			boolean isGrass = isGrass();
			if (isGrass) sprite = null;
			for (int i = 0; i < 8; i++)
			{
				DirtParticle dp = new DirtParticle(x, z);
				particles.add(dp);
				level.particles.add(dp);
			}
			for (int i = 0; i < image.pixels.length; i++)
			{
				int col = Images.dig.pixels[i];
				if (col >= 0)
				{
					image.pixels[i] = col;
					imageOrig.pixels[i] = col;
				}
			}
			Item item = getBuriedItem(level, x, z, isGrass);
			if (item != null) level.addEntity(item);
			dug = true;
			hasBuriedItem = false;
		}
	}
	private Item getBuriedItem(Level level, int x, int z, boolean isGrass)
	{
		if (hasBuriedItem)
		{
			int kind = 8;
			if (random.nextDouble() < 0.3)
			{
				kind += random.nextInt(3) + 1;
				if ((kind - 8) / (random.nextInt(4) + 1) < random.nextDouble() * random.nextDouble());
				{
					int k = random.nextInt(9);
					if ((level.player.treasure & (1 << k)) == 0) kind = k + 15;
				}
			}
			return new Item(x, z, kind);
		}
		else if (isGrass)
		{
			int kind = 12;
			boolean exception = random.nextDouble() < 0.1;
			if ((!level.containsCanteen() || exception) && random.nextDouble() < 0.2)
			{
				kind = 13;
				if (random.nextDouble() < 0.05 && !exception) kind = 14;
			}
			Item item = new Item(x, z, kind);
			for (int i = 0; i < 3; i++)
			{
				if (i < 2) level.lastDugItems[i] = level.lastDugItems[i + 1];
				else level.lastDugItems[2] = item;
			}
			return item;
		}
		return null;
	}
	private boolean isGrass()
	{
		if (sprite != null) return sprite.image == 0 || sprite.image == 1;
		return false;
	}
}