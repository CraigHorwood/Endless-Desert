package com.craighorwood.desert;
import javax.sound.sampled.*;
public class Sound
{
	public static Sound bighit = loadSound("/snd/bighit.wav");
	public static Sound canteengolden = loadSound("/snd/canteengolden.wav");
	public static Sound canteennormal = loadSound("/snd/canteennormal.wav");
	public static Sound death = loadSound("/snd/death.wav");
	public static Sound explosion = loadSound("/snd/explosion.wav");
	public static Sound gem = loadSound("/snd/gem.wav");
	public static Sound gold = loadSound("/snd/gold.wav");
	public static Sound hiss = loadSound("/snd/hiss.wav");
	public static Sound hurt = loadSound("/snd/hurt.wav");
	public static Sound mummy = loadSound("/snd/mummy.wav");
	public static Sound mummykill = loadSound("/snd/mummykill.wav");
	public static Sound poisoned = loadSound("/snd/poisoned.wav");
	public static Sound shovelin = loadSound("/snd/shovelin.wav");
	public static Sound shovelout = loadSound("/snd/shovelout.wav");
	public static Sound spit = loadSound("/snd/spit.wav");
	public static Sound start = loadSound("/snd/start.wav");
	public static Sound thwack = loadSound("/snd/thwack.wav");
	public static Sound treasure = loadSound("/snd/treasure.wav");
	public static Sound water = loadSound("/snd/water.wav");
	public static Sound loadSound(String name)
	{
		Sound sound = new Sound();
		try
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(name));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			sound.clip = clip;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sound;
	}
	private Clip clip;
	public void play()
	{
		try
		{
			if (clip != null)
			{
				new Thread()
				{
					public void run()
					{
						synchronized (clip)
						{
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}