package com.craighorwood.desert.graphics;
public class Particle extends Sprite
{
	public int xTile, zTile;
	protected double xa, ya, za;
	public Particle(int xTile, int zTile, double x, double y, double z, int image, int col)
	{
		super(x, y, z, image, col);
		this.xTile = xTile;
		this.zTile = zTile;
	}
	public void tick()
	{
	}
}