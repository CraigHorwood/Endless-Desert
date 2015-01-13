package com.craighorwood.desert.level;
import java.util.*;
import com.craighorwood.desert.Game;
import com.craighorwood.desert.entity.*;
import com.craighorwood.desert.graphics.Particle;
import com.craighorwood.desert.menu.*;
public class Level
{
	private static final int CHUNK_SIZE = 0x20;
	private Map<String, Tile[]> chunkCache = new HashMap<String, Tile[]>();
	public final DefaultTile defaultTile = new DefaultTile();
	private String chunkKey = "";
	private Game game;
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Particle> particles = new ArrayList<Particle>();
	private boolean mummySpawned = false;
	public int mummySpawnTime = 0, winTime = 0;
	public Mummy mummy;
	public Item[] lastDugItems = { null, null, null };
	public Player player;
	public Level(Game game)
	{
		this.game = game;
		chunkCache.clear();
		player = new Player((CHUNK_SIZE >> 1) + 0.5, (CHUNK_SIZE >> 1) + 0.5);
		addEntity(player);
	}
	public void addEntity(Entity e)
	{
		entities.add(e);
		e.level = this;
		e.updatePosition();
	}
	public void tick()
	{
		if (mummySpawned && mummySpawnTime > 0)
		{
			if (--mummySpawnTime == 0)
			{
				double x = (int) (player.x) + (Math.sin(player.rot) * 8);
				double z = (int) (player.z) + (Math.cos(player.rot) * 8);
				if (mummy == null) mummy = new Mummy(x, z);
				else
				{
					mummy.x = x;
					mummy.z = z;
				}
				mummy.restart();
				addEntity(mummy);
			}
		}
		if (winTime > 0)
		{
			if (--winTime == 0) game.setMenu(new WinMenu(player.gold));
		}
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.tick();
			e.updatePosition();
			e.age();
			if (e.isRemoved()) entities.remove(i--);
		}
		for (int i = 0; i < particles.size(); i++)
		{
			Particle p = particles.get(i);
			p.tick();
			if (p.removed)
			{
				getTile(p.xTile, p.zTile, false).particles.remove(p);
				particles.remove(i--);
			}
		}
	}
	private Tile[] getChunk(int xc, int zc, boolean generate)
	{
		chunkKey = xc + " " + zc;
		if (!chunkCache.containsKey(chunkKey) && generate)
		{
			return chunkCache.put(chunkKey, generateChunk(xc, zc));
		}
		return chunkCache.get(chunkKey);
	}
	private static final Random random = new Random();
	private Tile[] generateChunk(int xc, int zc)
	{
		Tile[] chunk = new Tile[1024];
		for (int i = 0; i < chunk.length; i++)
		{
			chunk[i] = new Tile();
		}
		if (xc != 0 || zc != 0)
		{
			int numAnimals = random.nextInt(12) + 1;
			double r = random.nextDouble();
			if (r < 0.5) numAnimals += (int) (r * 10);
			for (int i = 0; i < numAnimals; i++)
			{
				chunk[random.nextInt(1024)].canSpawnAnimal = random.nextInt(3) + 1;
			}
		}
		return chunk;
	}
	public Tile getTile(int x, int z, boolean generate)
	{
		int xc = x >> 5;
		int zc = z >> 5;
		x &= 0x1F;
		z &= 0x1F;
		Tile[] chunk = getChunk(xc, zc, generate);
		if (chunk == null) return defaultTile;
		return chunk[x + (z << 5)];
	}
	public boolean containsCanteen()
	{
		for (int i = 0; i < lastDugItems.length; i++)
		{
			Item item = lastDugItems[i];
			if (item != null)
			{
				int image =  item.sprite.image;
				if (image == 13 || image == 14) return true;
			}
		}
		return false;
	}
	public void allTreasure()
	{
		mummySpawned = true;
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			if (e instanceof Animal) ((Animal)e).die();
		}
		mummySpawnTime = 256;
	}
	public void die()
	{
		game.justDied = true;
		game.setMenu(new DeathMenu());
	}
	public void spawnAnimal(Tile tile, int xt, int zt)
	{
		if (mummySpawned) return;
		if (tile.canSpawnAnimal == 1) addEntity(new Scorpion(xt, zt));
		else if (tile.canSpawnAnimal == 2) addEntity(new BlackScorpion(xt, zt));
		else if (tile.canSpawnAnimal == 3) addEntity(new Cobra(xt, zt));
		tile.hasSpawnedAnimal = true;
	}
}