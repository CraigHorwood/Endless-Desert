package com.craighorwood.desert;
import java.awt.*;
import java.awt.event.*;
public class Input implements KeyListener, FocusListener, MouseListener, MouseMotionListener
{
	public boolean[] keys = new boolean[0x10000];
	public int delta = 0;
	public int recenterX = 640;
	public int recenterY = 480;
	public boolean click = false;
	private boolean grabMouse = false;
	private Robot robot;
	public Input()
	{
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
	}
	public void tick(boolean grabMouse)
	{
		this.grabMouse = grabMouse;
		delta = 0;
		click = false;
	}
	public void mouseDragged(MouseEvent me)
	{
		mouseMoved(me);
	}
	private boolean isRobot = false;
	public void mouseMoved(MouseEvent me)
	{
		if (!isRobot)
		{
			int mouseX = me.getXOnScreen();
			delta = mouseX - recenterX;
			if (grabMouse)
			{
				robot.mouseMove(recenterX, recenterY);
				isRobot = true;
			}
		}
		else isRobot = false;
	}
	public void focusGained(FocusEvent fe)
	{
	}
	public void focusLost(FocusEvent fe)
	{
		for (int i = 0; i < keys.length; i++)
		{
			keys[i] = false;
		}
	}
	public void keyPressed(KeyEvent ke)
	{
		int code = ke.getKeyCode();
		if (code > 0 && code < keys.length)
		{
			keys[code] = true;
		}
	}
	public void keyReleased(KeyEvent ke)
	{
		int code = ke.getKeyCode();
		if (code > 0 && code < keys.length)
		{
			keys[code] = false;
		}
	}
	public void keyTyped(KeyEvent ke)
	{
	}
	public void mouseClicked(MouseEvent me)
	{
	}
	public void mouseEntered(MouseEvent me)
	{
	}
	public void mouseExited(MouseEvent me)
	{
	}
	public void mousePressed(MouseEvent me)
	{
		click = true;
	}
	public void mouseReleased(MouseEvent me)
	{
	}
}