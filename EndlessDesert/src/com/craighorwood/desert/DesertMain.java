package com.craighorwood.desert;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import com.craighorwood.desert.graphics.Screen;
public class DesertMain extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 160;
	private static final int HEIGHT = 120;
	private static final int SCALE = 4;
	public Image splashImage;
	public JFrame frame;
	private boolean running;
	private Thread thread;
	private Game game;
	private Screen screen;
	private BufferedImage image;
	private int[] pixels;
	public Input input;
	private Cursor emptyCursor, defaultCursor;
	private boolean hadFocus = false;
	public DesertMain()
	{
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		game = new Game();
		screen = new Screen(WIDTH, HEIGHT);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		input = new Input();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "empty");
		defaultCursor = getCursor();
	}
	public synchronized void start()
	{
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.setName("Endless-Desert-Thread");
		thread.start();
	}
	public synchronized void stop()
	{
		if (!running) return;
		running = false;
		try
		{
			thread.join();
		}
		catch (InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}
	private boolean started = false;
	public void paint(Graphics g)
	{
		if (!started) g.drawImage(splashImage, 0, 0, null);
	}
	public void run()
	{
		int frames = 0;
		double unprocessed = 0;
		long then = System.nanoTime();
		double inverseFPS = 1 / 60.0;
		int ticks = 0;
		requestFocus();
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException ie)
		{
			ie.printStackTrace();
		}
		while (running)
		{
			long now = System.nanoTime();
			long passed = now - then;
			then = now;
			if (passed < 0) passed = 0;
			if (passed > 100000000) passed = 100000000;
			unprocessed += passed / 1000000000.0;
			boolean shouldRender = false;
			while (unprocessed > inverseFPS)
			{
				tick();
				unprocessed -= inverseFPS;
				shouldRender = true;
				ticks++;
				if (ticks % 60 == 0)
				{
					screen.currentFrames = frames;
					then += 1000;
					frames = 0;
				}
			}
			if (shouldRender)
			{
				render();
				frames++;
				started = true;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch (InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}
		}
	}
	private void tick()
	{
		if (hasFocus()) game.tick(input.keys, input.delta, input.click);
		input.tick(game.menu == null);
	}
	private void render()
	{
		if (hadFocus != hasFocus())
		{
			hadFocus = !hadFocus;
			setCursor(hadFocus ? emptyCursor : defaultCursor);
		}
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		screen.render(game, hasFocus());
		for (int i = 0; i < WIDTH * HEIGHT; i++)
		{
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
		bs.show();
	}
}