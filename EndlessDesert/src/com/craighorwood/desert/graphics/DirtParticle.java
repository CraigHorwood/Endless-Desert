package com.craighorwood.desert.graphics;
public class DirtParticle extends Particle
{
	public DirtParticle(int xTile, int zTile)
	{
		super(xTile, zTile, Math.random() - 0.5, Math.random() * 0.8, Math.random() - 0.5, (int) (Math.floor(Math.random() * 3)) + 4, 0xB97A57);
		xa = Math.random() - 0.5;
		ya = Math.random();
		za = Math.random() - 0.5;
	}
	public void tick()
	{
		x += xa * 0.03;
		y += ya * 0.03;
		z += za * 0.03;
		ya -= 0.1;
		if (y < 0)
		{
			y = 0;
			xa *= 0.8;
			za *= 0.8;
			if (Math.random() < 0.04) removed = true;
		}
	}
}