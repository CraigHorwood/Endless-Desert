package com.craighorwood.desert.entity;
import java.util.*;
import com.craighorwood.desert.graphics.Sprite;
import com.craighorwood.desert.level.*;
public class Entity
{
	protected static final Random random = new Random();
	public Sprite sprite;
	public double x, z, rot;
	public double xa, za, rota;
	public Level level;
	public int xTileOld = -1;
	public int zTileOld = -1;
	protected boolean removed = false;
	private int age = 0;
	public final void updatePosition()
	{
		int xTile = (int) (x + 0.5);
		int zTile = (int) (z + 0.5);
		if (xTile != xTileOld || zTile != zTileOld)
		{
			level.getTile(xTileOld, zTileOld, false).removeEntity(this);
			xTileOld = xTile;
			zTileOld = zTile;
			if (!removed)
			{
				Tile tile = level.getTile(xTileOld, zTileOld, false);
				if (tile.equals(level.defaultTile))
				{
					if (!equals(level.player)) remove();
					return;
				}
				tile.addEntity(this);
				for (int i = 0; i < tile.entities.size(); i++)
				{
					Entity e = tile.entities.get(i);
					if (!e.equals(this))
					{
						collideWith(e);
						e.collideWith(this);
					}
				}
			}
		}
	}
	public final void age()
	{
		age++;
		if (age == 600)
		{
			double xd = x - level.player.x;
			double zd = z - level.player.z;
			if (xd * xd + zd * zd >= 64) remove();
			age = 0;
		}
	}
	public boolean isRemoved()
	{
		return removed;
	}
	public void remove()
	{
		level.getTile(xTileOld, zTileOld, false).removeEntity(this);
		removed = true;
	}
	protected void collideWith(Entity e)
	{
	}
	public void tick()
	{
	}
}