package com.craighorwood.desert.menu;
import com.craighorwood.desert.*;
import com.craighorwood.desert.graphics.Image;
public class PauseMenu extends Menu
{
	public void render(Image target)
	{
		target.draw("PAUSED", 62, 43, 0xFFFFFF);
	}
	public void tick(Game game, boolean select)
	{
		if (select) game.setMenu(null);
	}
}