package com.craighorwood.desert.menu;
import com.craighorwood.desert.*;
import com.craighorwood.desert.graphics.Image;
public class MainMenu extends Menu
{
	private boolean loading;
	public MainMenu(boolean loading)
	{
		this.loading = loading;
	}
	public void render(Image target)
	{
		target.fill(0, 0, 160, 120, 0);
		if (time >= 150 || !loading)
		{
			target.draw("ENDLESS DESERT", 38, 34, 0xFFFF00);
			target.draw("BY CRAIG HORWOOD", 32, 40, 0xFFFFFF);
			if (System.currentTimeMillis() / 500 % 2 == 0) target.draw("PRESS SPACE TO START", 20, 58, 0xFFFFFF);
		}
		else target.draw("PLEASE WAIT", 47, 40, 0xFFFFFF);
	}
	private int time = 0;
	public void tick(Game game, boolean select)
	{
		time++;
		if (select && time >= 150)
		{
			Sound.start.play();
			game.setMenu(new InstructionsMenu());
		}
	}
}