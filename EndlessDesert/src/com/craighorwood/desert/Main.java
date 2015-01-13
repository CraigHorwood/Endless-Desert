package com.craighorwood.desert;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Main
{
	public Main(Image icon, Image splashImage)
	{
		final DesertMain game = new DesertMain();
		game.splashImage = splashImage;
		game.frame = new JFrame("Endless Desert");
		game.frame.setIconImage(icon);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(game, BorderLayout.CENTER);
		game.frame.setContentPane(panel);
		game.frame.setResizable(false);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setVisible(true);
		game.frame.addComponentListener(new ComponentListener() {
			public void componentHidden(ComponentEvent ce)
			{
			}
			public void componentMoved(ComponentEvent ce)
			{
				Point framePosition = ce.getComponent().getLocationOnScreen();
				game.input.recenterX = framePosition.x + 320;
				game.input.recenterY = framePosition.y + 240;
			}
			public void componentResized(ComponentEvent ce)
			{
			}
			public void componentShown(ComponentEvent ce)
			{
			}
		});
		game.start();
	}
}