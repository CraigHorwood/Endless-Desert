package com.craighorwood.desert.entity;
import com.craighorwood.desert.Sound;
import com.craighorwood.desert.graphics.Sprite;
public class Cobra extends Animal
{
	private boolean retreated = true;
	public Cobra(double x, double z)
	{
		super((int) x, (int) z);
		sprite = new Sprite(0, 0, 0, 26);
	}
	public void tick()
	{
		if (!retreated) sprite.image = 26 + ((++time >> 4) & 1);
		double xd = level.player.x - x;
		double zd = level.player.z - z;
		if (xd * xd + zd * zd < 4)
		{
			retreated = true;
			sprite.image = 28;
		}
		else retreated = false;
		if ((time & 127) == 0 && !retreated)
		{
			level.addEntity(new Bullet(x, z, Math.atan2(xd, zd)));
			Sound.spit.play();
		}
	}
	public void collideWith(Entity e)
	{
		if (e instanceof Bullet)
		{
			Bullet bullet = (Bullet) e;
			if (bullet.deflected)
			{
				die();
				bullet.remove();
			}
		}
	}
	public void whacked()
	{
	}
	public void die()
	{
		sprite.col = 0;
		super.die();
	}
}