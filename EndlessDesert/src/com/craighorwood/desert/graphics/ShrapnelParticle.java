package com.craighorwood.desert.graphics;
public class ShrapnelParticle extends Particle
{
	public ShrapnelParticle(int xTile, int zTile, int col)
	{
		super(xTile, zTile, Math.random() - 0.5, Math.random() + Math.random() * 0.4, Math.random() - 0.5, 6, col);
		xa = Math.random();
		ya = Math.random() - 0.5;
		za = Math.random();
	}
	public void tick()
	{
		x += xa * 0.03;
		y += ya * 0.03;
		z += za * 0.03;
		ya -= 0.2;
		if (y < 0)
		{
			y = 0;
			xa *= 0.8;
			za *= 0.8;
			if (Math.random() < 0.04) removed = true;
		}
	}
}