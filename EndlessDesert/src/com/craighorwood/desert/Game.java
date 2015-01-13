package com.craighorwood.desert;
import java.awt.event.KeyEvent;
import com.craighorwood.desert.entity.*;
import com.craighorwood.desert.level.*;
import com.craighorwood.desert.menu.*;
public class Game
{
	public Level level;
	public Menu menu;
	public boolean justPaused = false, justDied = false;
	public int digCount = 0;
	private boolean wasDigging = false;
	private Tile digTileOld = new Tile();
	public int whackCount = 0;
	public Game()
	{
		setMenu(new MainMenu(true));
	}
	public void startGame()
	{
		level = new Level(this);
	}
	public void tick(boolean[] keys, int delta, boolean click)
	{
		boolean up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_NUMPAD8];
		boolean down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_NUMPAD2];
		boolean left = keys[KeyEvent.VK_A];
		boolean right = keys[KeyEvent.VK_D];
		boolean dig = keys[KeyEvent.VK_SPACE];
		if (keys[KeyEvent.VK_ESCAPE])
		{
			keys[KeyEvent.VK_ESCAPE] = false;
			if (menu == null)
			{
				justPaused = true;
				setMenu(new PauseMenu());
			}
			else if (menu instanceof PauseMenu) setMenu(null);
		}
		if (menu != null)
		{
			keys[KeyEvent.VK_W] = keys[KeyEvent.VK_UP] = keys[KeyEvent.VK_NUMPAD8] = false;
			keys[KeyEvent.VK_S] = keys[KeyEvent.VK_DOWN] = keys[KeyEvent.VK_NUMPAD2] = false;
			keys[KeyEvent.VK_A] = false;
			keys[KeyEvent.VK_D] = false;
			keys[KeyEvent.VK_SPACE] = false;
			menu.tick(this, dig);
		}
		else
		{
			double xTile = level.player.x + Math.sin(level.player.rot) + 0.5;
			double zTile = level.player.z + Math.cos(level.player.rot) + 0.5;
			int xt = (int) xTile;
			int zt = (int) zTile;
			if (xTile < 0) xt--;
			if (zTile < 0) zt--;
			Tile digTile = level.getTile(xt, zt, false);
			if (digTile != digTileOld)
			{
				digTileOld.setSelected(false);
				digTile.setSelected(true);
			}
			digTileOld = digTile;
			if (dig)
			{
					if (!wasDigging) wasDigging = true;
					else if (digCount == 29) Sound.shovelin.play();
					if (digCount < 30) digCount++;
			}
			else if (wasDigging)
			{
				if (!digTileOld.dug && digCount == 30)
				{
					digTileOld.dig(level, xt, zt);
					Sound.shovelout.play();
				}
				wasDigging = false;
				digCount = 0;
			}
			if (whackCount > 0) whackCount--;
			else if (click && !dig)
			{
				Tile[] tracedTiles = new Tile[2];
				double sin = Math.sin(level.player.rot);
				double cos = Math.cos(level.player.rot);
				xTile = level.player.x + sin + 0.5;
				zTile = level.player.z + cos + 0.5;
				tracedTiles[0] = level.getTile((int) xTile, (int) zTile, false);
				tracedTiles[1] = level.getTile((int) (xTile + sin + 0.5), (int) (zTile + cos + 0.5), false);
				for (int i = 0; i < tracedTiles.length; i++)
				{
					Tile tile = tracedTiles[i];
					for (int j = 0; j < tile.entities.size(); j++)
					{
					  	Entity e = tile.entities.get(j);
						if (e instanceof Animal) ((Animal)e).whacked();
						else if (e instanceof Bullet)
						{
							Bullet b = (Bullet) e;
							b.xa = -b.xa;
							b.za = -b.za;
							b.deflected = true;
						}
					}
				}
				whackCount = 10;
				Sound.thwack.play();
			}
			level.player.tick(up, down, left, right, dig, delta);
			level.tick();
		}
	}
	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}
}