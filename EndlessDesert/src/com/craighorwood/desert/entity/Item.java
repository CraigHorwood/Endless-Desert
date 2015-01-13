package com.craighorwood.desert.entity;
import com.craighorwood.desert.Sound;
import com.craighorwood.desert.graphics.Sprite;
public class Item extends Entity
{
	private double ya = 0.025;
	public int bouncePhase = 0;
	public Item(double x, double z, int image)
	{
		this.x = x;
		this.z = z;
		sprite = new Sprite(0, 0, 0, image);
		sprite.y = 0.5;
	}
	public void tick()
	{
		if (bouncePhase < 2)
		{
			sprite.y += ya;
			ya -= 0.005;
			if (sprite.y < 0)
			{
				if (bouncePhase < 1) ya *= -0.5;
				else
				{
					sprite.y = 0;
					ya = 0;
				}
				bouncePhase++;
			}
		}
	}
	public void collected(Player player)
	{
		switch (sprite.image)
		{
		case 8:
			player.gold++;
			Sound.gold.play();
			break;
		case 9:
			player.gold += 5;
			Sound.gem.play();
			break;
		case 10:
			player.gold += 10;
			Sound.gem.play();
			break;
		case 11:
			player.gold += 20;
			Sound.gem.play();
			break;
		case 12:
			if ((player.water += 15) > 125)
			{
				player.water = 125;
				if (player.status < 3) player.status = 0;
			}
			player.thirstDelay = 90;
			Sound.water.play();
			break;
		case 13:
			player.water = 125;
			player.thirstDelay = 180;
			if (player.status < 3) player.status = 0;
			Sound.canteennormal.play();
			break;
		case 14:
			player.water = 125;
			player.status = 3;
			if (player.status < 3) player.status = 0;
			Sound.canteengolden.play();
			break;
		default:
			player.treasure |= (1 << (sprite.image - 15));
			if (player.treasure == 0x1FF) level.allTreasure();
			Sound.treasure.play();
			break;
		}
		remove();
	}
}