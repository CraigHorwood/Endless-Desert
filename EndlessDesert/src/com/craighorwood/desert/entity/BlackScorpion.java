package com.craighorwood.desert.entity;
import com.craighorwood.desert.Sound;
public class BlackScorpion extends Scorpion
{
	private boolean chase = false;
	public BlackScorpion(double x, double z)
	{
		super(x, z);
		sprite.col = 0;
	}
	public void tick()
	{
		double xd = level.player.x - x;
		double zd = level.player.z - z;
		double dd = xd * xd + zd * zd;
		if (chase)
		{
			sprite.image = 24 + ((++time >> 2) & 1);
			double r = Math.atan2(xd, zd);
			x += Math.sin(r) * 0.06;
			z += Math.cos(r) * 0.06;
			if (dd > 25) chase = false;
		}
		else
		{
			super.tick();
			if (dd <= 25)
			{
				chase = true;
				Sound.hiss.play();
			}
		}
	}
}