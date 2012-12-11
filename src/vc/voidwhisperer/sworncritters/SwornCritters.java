package vc.voidwhisperer.sworncritters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.P;

import vc.voidwhisperer.sworncritters.listeners.ChunkListener;
import vc.voidwhisperer.sworncritters.listeners.CommandLS;
import vc.voidwhisperer.sworncritters.listeners.CommandSCReload;
import vc.voidwhisperer.sworncritters.listeners.CommandSetDist;
import vc.voidwhisperer.sworncritters.listeners.EntityListener;
import vc.voidwhisperer.sworncritters.listeners.LegendaryCheckRunnable;
import vc.voidwhisperer.sworncritters.listeners.LegendaryRunnable;
import vc.voidwhisperer.sworncritters.listeners.SpawnRunnable;
import vc.voidwhisperer.sworncritters.utils.FileAccessor;

public class SwornCritters extends JavaPlugin {
	public static FileAccessor config;
	public static Logger l = Bukkit.getLogger();
	public static SwornCritters p;
	public static Set<Integer> spawnedMobs = new HashSet<Integer>();
	public static Set<Entity> lEntities = new HashSet<Entity>();
	public static P Fact = null;
	public static boolean usingFactions = false;
	private boolean legendaryAlive = false;
	public static long timesincelastlegendary = 0;
	private Set<UUID>legenadryEntityIds = new HashSet<UUID>();
	static EntityType[] mobsarray =  { EntityType.ZOMBIE ,
			   EntityType.SKELETON ,
			   EntityType.CREEPER ,
			   EntityType.SPIDER ,
			   EntityType.CAVE_SPIDER ,
			   EntityType.CHICKEN ,
			   EntityType.PIG ,
			   EntityType.SHEEP ,
			   EntityType.SLIME ,
			   EntityType.MAGMA_CUBE ,
			   EntityType.SILVERFISH ,
			   EntityType.SNOWMAN ,
			   EntityType.MUSHROOM_COW ,
			   EntityType.COW ,
			   EntityType.BLAZE ,
			   EntityType.ENDERMAN ,
			   EntityType.GHAST ,
			   EntityType.OCELOT ,
			   EntityType.WOLF ,
			   EntityType.IRON_GOLEM };
	static List<EntityType> moblist = Arrays.asList(mobsarray);
	private static HashSet<EntityType> mobs = new HashSet<EntityType>(moblist);
	public void onEnable()
	{
		p = this;
		config = new FileAccessor(this, "config.yml");
		//l.info(String.valueOf(config.getFile().getLong("Configuration.spawn-ticks")));
		//TODO: set legendary spawn ticks
		long ticks = config.getFile().getLong("Configuration.spawn-ticks") > 60 ? config.getFile().getLong("Configuration.spawn-ticks") : 60;
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,new SpawnRunnable(), 0L, ticks);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,new LegendaryRunnable(), 0L, 400L);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,new LegendaryCheckRunnable(), 0L, 200L);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityListener(), this);
		//Bukkit.getServer().getPluginManager().registerEvents(new ChunkListener(), this);
		getCommand("SCReload").setExecutor(new CommandSCReload());
		getCommand("SetDist").setExecutor(new CommandSetDist());
		getCommand("LS").setExecutor(new CommandLS());
		if(this.getServer().getPluginManager().isPluginEnabled("Factions"))
		{
			Plugin fac = this.getServer().getPluginManager().getPlugin("Factions");
			Fact = (P)fac;
			usingFactions = true;
			l.info("Hooked into Factions.");
			//factapi = Factions.i;
		}
		if(this.getServer().getPluginManager().isPluginEnabled("SwornNations"))
		{
			usingFactions = true;
		}
		clearEntities();
	}
	public long time()
	{
		return System.currentTimeMillis();
	}
	private void clearEntities()
	{
		/*This is to clear all entities on startup. It's used incase of a server crash --- To prevent multiple legendaries being alive.*/
		for(World world : Bukkit.getServer().getWorlds())
		{
		List<Entity> entities = world.getEntities();
		for(Entity entity : entities)
		{
			if(containsMob(entity.getType()))
			{
				entity.remove();
			}
		}
		}
	}
	public boolean isLegendaryAlive()
	{
		return legendaryAlive;
	}
	public Set<UUID> getLegendaryEntityIds()
	{
		return legenadryEntityIds;
	}
	public void addLegendary(UUID uuid)
	{
		legenadryEntityIds.add(uuid);
	}
	public void clearLegendaries()
	{
		legenadryEntityIds.clear();
	}
	public void removeLegendary(UUID uuid)
	{
		legenadryEntityIds.remove(uuid);
	}
	public void setLegendaryAlive(boolean setter)
	{
		legendaryAlive = setter;
	}
	public static boolean containsMob(EntityType type)
	{
		if(mobs.contains(type))
		{
			return true;
		}else{
			return false;
		}
	}
}
