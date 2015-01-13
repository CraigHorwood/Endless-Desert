package com.craighorwood.desert.menu;
import com.craighorwood.desert.Game;
import com.craighorwood.desert.graphics.Image;
public class DeathMenu extends Menu
{
	public void render(Image target)
	{
		target.draw("YOU DEAD", 56, 45, 0xFFC0C0);
		if (time > 60) target.draw("PRESS SPACE TO RETURN", 17, 60, 0xFFFFFF);
	}
	private int time = 0;
	public void tick(Game game, boolean select)
	{
		if (++time > 60 && select) game.setMenu(new MainMenu(false));
	}
}