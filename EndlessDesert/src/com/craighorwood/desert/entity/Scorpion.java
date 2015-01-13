package com.craighorwood.desert.entity;
import com.craighorwood.desert.graphics.Sprite;
public class Scorpion extends Animal
{
	private int wander = 1;
	public Scorpion(double x, double z)
	{
		super((int) x, (int) z);
		sprite = new Sprite(0, 0, 0, 24, 0xFF00000);
	}
	public void tick()
	{
		sprite.image = 24 + ((++time >> 3) & 1);
		if (--wander == 0)
		{
			double r = random.nextDouble() * Math.PI * 2;
			xa = Math.sin(r) * 0.03;
			za = Math.cos(r) * 0.03;
			wander = random.nextInt(100) + 30;
		}
		x += xa;
		z += za;
	}
}