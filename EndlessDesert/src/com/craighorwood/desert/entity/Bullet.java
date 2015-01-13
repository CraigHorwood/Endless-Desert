package com.craighorwood.desert.entity;
import com.craighorwood.desert.graphics.Sprite;
public class Bullet extends Entity
{
	public boolean deflected = false;
	public Bullet(double x, double z, double angle)
	{
		this.x = x;
		this.z = z;
		xa = Math.sin(angle) * 0.1;
		za = Math.cos(angle) * 0.1;
		sprite = new Sprite(0, 0, 0, 2);
	}
	public void tick()
	{
		x += xa;
		z += za;
	}
}