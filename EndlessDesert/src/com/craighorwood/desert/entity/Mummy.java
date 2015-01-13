package com.craighorwood.desert.entity;
import com.craighorwood.desert.Sound;
import com.craighorwood.desert.graphics.Sprite;
public class Mummy extends Animal
{
	public boolean dying = false;
	private int running = 0;
	private int deathCounter = 128;
	private int hp = 32, recovery = 0, scorpionTime = 192;
	public Mummy(double x, double z)
	{
		super((int) x, (int) z);
		sprite = new Sprite(0, 0, 0, 29);
	}
	public void restart()
	{
		removed = false;
	}
	public void tick()
	{
		if (recovery > 0)
		{
			if (--recovery == 2) sprite.col = 0xFFFFFF;
		}
		int modifier = 3;
		if (dying)
		{
			modifier = 8 - (--deathCounter >> 4);
			sprite.col = (deathCounter << 17) | (deathCounter << 9) | (deathCounter << 1);
			if (deathCounter == 0)
			{
				level.winTime = 64;
				super.die();
			}
		}
		else if (running == 0 && --scorpionTime < 32)
		{
			modifier = 1;
			if (scorpionTime == 0)
			{
				if (random.nextDouble() < (32 - hp) / 40.0) level.addEntity(new BlackScorpion(xTileOld, zTileOld));
				else level.addEntity(new Scorpion(xTileOld, zTileOld));
				scorpionTime = 192;
			}
			if (scorpionTime == 31) Sound.mummy.play();
		}
		else if (running > 0)
		{
			x += xa;
			z += za;
			running--;
		}
		sprite.image = 29 + ((++time >> modifier) & 1);
	}
	public void remove()
	{
		super.remove();
		if (!dying) level.mummySpawnTime = 256;
	}
	public void whacked()
	{
		if (recovery == 0)
		{
			sprite.col = 0xFF0000;
			if ((--hp & 7) == 0)
			{
				if (hp > 0) run();
				else
				{
					sprite.col = 0xFFFFFF;
					die();
				}
			}
			recovery = 10;
			Sound.bighit.play();
		}
	}
	private void run()
	{
		running = 128 + (32 - hp);
		double r = level.player.rot + (random.nextDouble() - 0.5);
		xa = Math.sin(r) * 0.06;
		za = Math.cos(r) * 0.06;
	}
	public void die()
	{
		dying = true;
		Sound.mummykill.play();
	}
}