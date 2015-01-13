package com.craighorwood.desert.menu;
import com.craighorwood.desert.*;
import com.craighorwood.desert.graphics.Image;
public class WinMenu extends Menu
{
	private int gold = 0;
	private String line1 = "AN ERROR OCCURRED";
	private String line2 = "";
	public WinMenu(int gold)
	{
		this.gold = gold;
		if (gold == 0) line1 = "YOU ARE POORER THAN DIRT";
		else if (gold < 20) line1 = "YOU ARE POOR AS DIRT";
		else if (gold < 100)
		{
			line1 = "YOU CAN AFFORD SOMEWHAT";
			line2 = "NICE THINGS";
		}
		else if (gold < 200)
		{
			line1 = "YOU ARE COMFORTABLY WELL";
			line2 = "OFF";
		}
		else if (gold < 300)
		{
			line1 = "YOU LOOK RICHER THAN";
			line2 = "YOU ACTUALLY ARE";
		}
		else if (gold < 400)
		{
			line1 = "YOU PROBABLY DONT HAVE";
			line2 = "TO WORK ANYMORE";
		}
		else if (gold < 500)
		{
			line1 = "YOU CAN AFFORD A MANSION";
			line2 = "AND A HALF";
		}
		else if (gold < 700)
		{
			line1 = "YOU CERTAINLY ARE A RICH";
			line2 = "MAN";
		}
		else
		{
			line1 = "YOUR PERSISTENCE IS ADMIRABLE";
		}
	}
	private int time = 0;
	public void render(Image target)
	{
		target.fill(0, 0, 160, 120, 0);
		target.draw("YOU WIN", 59, 32, 0xFFFF00);
		String goldString = "YOU GOT " + gold + " GOLD";
		target.draw(goldString, 80 - goldString.length() * 3, 44, 0xFFFFFFF);
		target.draw(line1, 80 - line1.length() * 3, 68, 0xFFFFC0);
		target.draw(line2, 80 - line2.length() * 3, 74, 0xFFFFC0);
		if (time > 60) target.draw("PRESS SPACE TO RETURN", 17, 112, 0xFFFFFF);
	}
	public void tick(Game game, boolean select)
	{
		time++;
		if (select && time > 60) game.setMenu(new MainMenu(false));
	}
}