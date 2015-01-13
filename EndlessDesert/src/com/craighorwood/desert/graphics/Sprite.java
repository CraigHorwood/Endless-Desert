package com.craighorwood.desert.graphics;
public class Sprite
{
	public double x, y, z;
	public int image, col;
	public boolean removed = false;
	public Sprite(double x, double y, double z, int image)
	{
		this(x, y, z, image, 0xFFFFFF);
	}
	public Sprite(double x, double y, double z, int image, int col)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.image = image;
		this.col = col;
	}
}