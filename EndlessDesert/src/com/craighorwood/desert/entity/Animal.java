package com.craighorwood.desert.entity;
import com.craighorwood.desert.Sound;
import com.craighorwood.desert.level.Tile;
import com.craighorwood.desert.graphics.ShrapnelParticle;
public class Animal extends Entity
{
	private int xTile, zTile;
	protected boolean dead = false;
	protected int time = 0;
	public Animal(int xTile, int zTile)
	{
		this.xTile = xTile;
		this.zTile = zTile;
		x = xTile;
		z = zTile;
	}
	public void remove()
	{
		Tile tile = level.getTile(xTile, zTile, false);
		tile.hasSpawnedAnimal = false;
		if (dead) tile.canSpawnAnimal = 0;
		super.remove();
	}
	public void whacked()
	{
		die();
	}
	public void die()
	{
		dead = true;
		remove();
		for (int i = 0; i < 8 + (random.nextInt(3) - 1); i++)
		{
			int xTile = (int) x;
			int zTile = (int) z;
			ShrapnelParticle sp = new ShrapnelParticle(xTile, zTile, sprite.col);
			level.getTile(xTile, zTile, false).particles.add(sp);
			level.particles.add(sp);
		}
		Sound.explosion.play();
	}
}