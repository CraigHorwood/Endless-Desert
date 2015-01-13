package com.craighorwood.desert.graphics;
import com.craighorwood.desert.*;
import com.craighorwood.desert.entity.Player;
public class Screen extends Image
{
	private static final int PANEL_HEIGHT = 29;
	private static final String[] statusStrings = { "OK", "POISONED", "THIRSTY" };
	private static final int[] statusColors = { 0xFFFF00, 0x7F00, 0xFF0000 };
	private Image3D viewport;
	public int currentFrames = 60;
	public Screen(int width, int height)
	{
		super(width, height);
		viewport = new Image3D(width, height - PANEL_HEIGHT);
	}
	public void render(Game game, boolean hasFocus)
	{
		if (game.menu == null)
		{
			fill(0, 0, width, height, 0);
			viewport.render(game);
			draw(viewport, 0, 0);
			Player player = game.level.player;
			if (game.whackCount == 0)
			{
				int xx = 120 + (int) (Math.cos(player.bobPhase * 0.2) * player.bob);
				int yy = 47 + (int) (Math.sin(player.bobPhase * 0.4) * player.bob + player.bob * 2);
				draw(Images.shovel, xx - game.digCount, yy + (game.digCount << 1));
			}
			else draw(Images.shovel, 80, 47);
			draw("" + currentFrames, 1, 1, 0);
			draw(Images.panel, 0, 91);
			draw("WATER", 2, 94, 0xFFFFFF);
			fill(33, 94, 33 + player.water, 100, player.recovery > 24 ? 0xFFFFFF : 0xC0FF);
			draw("STATUS", 9, 106, 0xFFFFFF);
			if (player.status < 3)
			{
				String status = statusStrings[player.status];
				draw(status, 27 - status.length() * 3, 113, statusColors[player.status]);
			}
			else goldenDraw("GOLDEN", 9, 113);
			draw("GOLD", 60, 106, 0xFFFFFF);
			String gold = player.gold + "";
			draw(gold, 72 - gold.length() * 3, 113, 0xFFFF00);
			for (int i = 0; i < 9; i++)
			{
				int xo = (i % 3) << 2;
				int yo = (i / 3) << 2;
				int col = 0x7F7F00;
				if ((player.treasure & (1 << i)) > 0) col = 0xFFFF00;
				draw(Images.treasure, 146 + xo, 106 + yo, xo, yo, 4, 4, col);
			}
		}
		else
		{
			if (game.justPaused || game.justDied)
			{
				for (int i = 0; i < pixels.length; i++)
				{
					pixels[i] = (pixels[i] & 0xFCFCFC) >> 2;
				}
				game.justPaused = false;
				game.justDied = false;
			}
			if (hasFocus) game.menu.render(this);
		}
	}
}