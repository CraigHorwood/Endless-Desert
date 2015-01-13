package com.craighorwood.desert.entity;
import com.craighorwood.desert.Sound;
public class Player extends Entity
{
	public double bob = 0, bobPhase = 0;
	public int status = 0, gold = 0, treasure = 0;
	public int water = 125, thirstDelay = 0;
	public int recovery = 0;
	public Player(double x, double z)
	{
		this.x = x;
		this.z = z;
	}
	private int time = 0;
	public void tick(boolean up, boolean down, boolean left, boolean right, boolean dig, int delta)
	{
		time++;
		if (thirstDelay > 0) thirstDelay--;
		if (recovery > 0) recovery--;
		if (delta != 0) rota -= delta * 0.01;
		if (!dig)
		{
			double xm = 0;
			double zm = 0;
			if (up) zm--;
			if (down) zm++;
			if (left) xm--;
			if (right) xm++;
			double dd = xm * xm + zm * zm;
			double ddo = dd;
			if (dd > 0) dd = Math.sqrt(dd);
			else dd = 1;
			xm /= dd;
			zm /= dd;
			bob *= 0.6;
			dd = Math.sqrt(ddo);
			bob += dd;
			bobPhase += dd;
			double sin = Math.sin(rot);
			double cos = Math.cos(rot);
			xa -= (xm * cos + zm * sin) * 0.03;
			za -= (zm * cos - xm * sin) * 0.03;
			x += xa;
			z += za;
			xa *= 0.6;
			za *= 0.6;
		}
		rot += rota;
		rota *= 0.4;
		if (level.mummy != null)
		{
			if (level.mummy.dying) return;
		}
		if (thirstDelay == 0 && status != 3)
		{
			int thirstRate = 20;
			if (status == 1) thirstRate = 10;
			if (time % thirstRate == 0)
			{
				if (--water == 25) status = 2;
				else if (water == 0) die();
			}
		}
	}
	protected void collideWith(Entity e)
	{
		if (e instanceof Scorpion && recovery == 0)
		{
			xa += e.xa * 16;
			za += e.za * 16;
			recovery = 30;
			if ((water -= 15) <= 0) die();
			else
			{
				if (status == 3) status = 0;
				Sound.hurt.play();
			}
		}
		else if (e instanceof Bullet && recovery == 0)
		{
			xa += e.xa;
			za += e.za;
			e.remove();
			recovery = 30;
			if (status != 1)
			{
				status = 1;
				Sound.poisoned.play();
			}
			else Sound.hurt.play();
		}
		else if (e instanceof Item) ((Item)e).collected(this);
	}
	private void die()
	{
		Sound.death.play();
		level.die();
	}
}