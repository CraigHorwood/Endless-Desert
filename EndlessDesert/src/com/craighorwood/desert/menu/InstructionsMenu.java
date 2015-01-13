package com.craighorwood.desert.menu;
import com.craighorwood.desert.Game;
import com.craighorwood.desert.graphics.Image;
public class InstructionsMenu extends Menu
{
	private String[] lines = { "TILES HIGHLIGHTED IN RED", "ARE HIDING GOLD AND GEMS", "", "HOLD SPACE TO DIG", "", "CLICK MOUSE TO WHACK", "THINGS WITH THE SHOVEL", "", "STAY HYDRATED BY DIGGING", "IN PATCHES OF GRASS", "", "AVOID DANGEROUS DESERT", "CREATURES", "", "FIND ALL 9 TREASURE PIECES", "TO DEFEAT THE MUMMY" };
	public void render(Image target)
	{
		target.fill(0, 0, 160, 120, 0);
		for (int i = 0; i < lines.length; i++)
		{
			target.draw(lines[i], 2, 2 + i * 6, 0xFFFFFF);
		}
		if (time >= 60) target.draw("PRESS SPACE", 47, 108, 0xFFFF00);
	}
	private int time = 0;
	public void tick(Game game, boolean select)
	{
		time++;
		if (select && time >= 60)
		{
			game.setMenu(null);
			game.startGame();
		}
	}
}