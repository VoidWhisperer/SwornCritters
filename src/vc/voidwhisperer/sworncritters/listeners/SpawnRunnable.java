package vc.voidwhisperer.sworncritters.listeners;
import com.massivecraft.factions.Board;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.LazyMetadataValue.CacheStrategy;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;

import vc.voidwhisperer.sworncritters.SwornCritters;
import vc.voidwhisperer.sworncritters.utils.MobTyping;

public class SpawnRunnable implements Runnable {
	public static int ranint = 50;
	@Override
	public void run() {
		int commonchance = SwornCritters.config.getFile().getInt("Configuration.commonchance");
		boolean old_version = SwornCritters.config.getFile().getBoolean("Configuration.old-version");
		int uncommonchance = SwornCritters.config.getFile().getInt("Configuration.uncommonchance");
		int rarechance = SwornCritters.config.getFile().getInt("Configuration.rarechance");
		int mobcapperplayer = SwornCritters.config.getFile().getInt("Configuration.player-mob-cap");
		boolean debugmode = SwornCritters.config.getFile().getBoolean("Configuration.debug-mode");
		int total = SwornCritters.config.getFile().getInt("Configuration.spawnchance");
		Player[] playerz = Bukkit.getServer().getOnlinePlayers();
		Random r = new Random();
		for(Player p : playerz)
		{
			if(SwornCritters.usingFactions == true)
			{
				FLocation floc = new FLocation(p.getLocation());
				if(Board.getFactionAt(floc).isSafeZone() || Board.getFactionAt(floc).isWarZone() ||!Board.getFactionAt(floc).isNone())
				{
					continue;
				}
			}
			boolean spawn = r.nextInt(100) < total ? true : false;
			if(spawn)
			{
				if(old_version == true)
				{
				int r2 = r.nextInt(100);
				int type = 0 ; // common = 0, uncommon = 1, rare = 2;
				/* Example:
				 * commonchance = 50
				 * uncommon chance = 30
				 * rare chance = 20
				 */
				if(r2 < commonchance)
				{

					type = 0;
				}else if(r2 > commonchance && r2 < commonchance+uncommonchance)
				{

					type = 1;
				}else if(r2 > uncommonchance+commonchance && r2 <= rarechance+uncommonchance+commonchance)
				{
					type = 2;
				}
				if(type == 0)
				{
						EntityType z2 = vc.voidwhisperer.sworncritters.utils.MobTyping.getCommonMob();
						r.setSeed(r.nextLong()*r.nextLong());
						int rxnop = r.nextInt(2); //r x negative or positive
						boolean negativex = rxnop == 0 ? true : false;
						int rznop = r.nextInt(2);
						boolean negativez = rznop == 0 ? true : false;
						int x = r.nextInt(ranint);
						int zt = r.nextInt(ranint);
						int ranx = negativex ? (x-(x*2)) : x;
						int ranz = negativez ? (zt-(zt*2)) : zt;
						
						int entities = getNearbyEntities(p,32, 100, 32);
						//SwornCritters.l.info(String.valueOf(entities));
						if(entities <= mobcapperplayer)
						{
							if(z2 != EntityType.WOLF)
							{
								if(debugmode)
								{
								SwornCritters.l.info("Spawned " + z2.getName());
								SwornCritters.l.info(String.valueOf(p.getLocation().getZ()));
								}
							Block b = getYBlock(p.getLocation().add(ranx,2,ranz).getBlock());
							if(b != null)
							{
							Location pos = b.getLocation();		
							Entity asdf = (Entity)p.getWorld().spawn(pos, (Class<? extends LivingEntity>)z2.getEntityClass());
							SwornCritters.spawnedMobs.add(asdf.getEntityId());
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							}
							}else{
								//SwornCritters.l.info("Spawned " + z2.getName());
								/*Location pos = new Location(p.getWorld()
										,p.getLocation().getX() + ranx
										,p.getLocation().getY()+2
										,p.getLocation().getZ() + ranz);*/
							Block b = getYBlock(p.getLocation().add(ranx,2,ranz).getBlock());
							if(b != null)
							{
							Location pos = b.getLocation();		
							Wolf asdf = (Wolf)p.getWorld().spawn(pos, (Class<? extends LivingEntity>)z2.getEntityClass());
							SwornCritters.spawnedMobs.add(asdf.getEntityId());
							//asdf.setMetadata("pspawned", new LazyMetadataValue(SwornCritters.p, CacheStrategy.NEVER_CACHE,new MetadataCallable()));
							asdf.setAngry(true);
							asdf.setTarget(p);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							}
							}
						}
					}else if(type == 1)
					{
						r.setSeed(r.nextLong()*r.nextLong());
						EntityType z3 = vc.voidwhisperer.sworncritters.utils.MobTyping.getUncommonMob();
						int rxnop1 = r.nextInt(2); //r x negative or positive
						boolean negativex1 = rxnop1 == 0 ? true : false;
						int rznop1 = r.nextInt(2);
						boolean negativez1 = rznop1 == 0 ? true : false;
						int x1 = r.nextInt(ranint);
						int zt1 = r.nextInt(ranint);
						int ranx1 = negativex1 ? (x1-(x1*2)) : x1;
						int ranz1 = negativez1 ? (zt1-(zt1*2)) : zt1;
						int entities1 = getNearbyEntities(p,32, 100, 32);
						//SwornCritters.l.info(String.valueOf(entities1));
						if(entities1 <= mobcapperplayer)
						{
							if(z3 != EntityType.SPIDER)
							{
								if(debugmode)
								{
							SwornCritters.l.info("Spawned " + z3.getName());
								}
							Block b1 = getYBlock(p.getLocation().add(ranx1,2,ranz1).getBlock());
							if(b1 != null)
							{
							Location pos = b1.getLocation();
							Entity asdf = (Entity)p.getWorld().spawn(pos, (Class<? extends LivingEntity>)z3.getEntityClass());
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							SwornCritters.spawnedMobs.add(asdf.getEntityId());
							}
							}else{
								if(debugmode)
								{
								SwornCritters.l.info("Spawned " + z3.getName());
								}
							Block b1 = getYBlock(p.getLocation().add(ranx1,2,ranz1).getBlock());
							if(b1 != null)
							{
							Location pos = b1.getLocation();
							Entity asdf1 = p.getWorld().spawn(pos, (Class<? extends LivingEntity>)z3.getEntityClass());
							SwornCritters.spawnedMobs.add(asdf1.getEntityId());
							//asdf1.setMetadata("pspawned", new LazyMetadataValue(SwornCritters.p, CacheStrategy.NEVER_CACHE,new MetadataCallable()));
							Entity asdf2 = p.getWorld().spawn(pos, (Class<? extends LivingEntity>)Skeleton.class);
							SwornCritters.spawnedMobs.add(asdf2.getEntityId());
							//asdf2.setMetadata("pspawned", new LazyMetadataValue(SwornCritters.p, CacheStrategy.NEVER_CACHE,new MetadataCallable()));
							asdf1.setPassenger(asdf2);
							asdf1.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf1.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf1.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							}
							}
						}
					}else if(type == 2)
					{
						r.setSeed(r.nextLong()*r.nextLong());
						EntityType z5 = vc.voidwhisperer.sworncritters.utils.MobTyping.getRareMob();
						int rxnop2 = r.nextInt(2); //r x negative or positive
						boolean negativex2 = rxnop2 == 0 ? true : false;
						int rznop2 = r.nextInt(2);
						boolean negativez2 = rznop2 == 0 ? true : false;
						int x2 = r.nextInt(ranint);
						int zt2 = r.nextInt(ranint);
						int ranx2 = negativex2 ? (x2-(x2*2)) : x2;
						int ranz2 = negativez2 ? (zt2-(zt2*2)) : zt2;
						
						int entities2 = getNearbyEntities(p,32, 100, 32);
						//SwornCritters.l.info(String.valueOf(entities2));
						if(entities2 <= mobcapperplayer)
						{
							if(z5 != EntityType.CREEPER)
							{
								if(debugmode)
								{
								SwornCritters.l.info("Spawned " + z5.getName());
								}
							Block b2 = getYBlock(p.getLocation().add(ranx2,2,ranz2).getBlock());
							if(b2 != null)
							{
							Location pos = b2.getLocation();
							//SwornCritters.l.info(String.valueOf(p.getLocation().getX() + ranx2) + String.valueOf(p.getLocation().getY() + 2) + String.valueOf(p.getLocation().getZ() + ranz2));
							Entity asdf = (Entity)p.getWorld().spawn(pos, (Class<? extends LivingEntity>)z5.getEntityClass());
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							SwornCritters.spawnedMobs.add(asdf.getEntityId());
							}
							}else{
								if(debugmode)
								{
								SwornCritters.l.info("Spawned " + z5.getName());
								}
							Block b2 = getYBlock(p.getLocation().add(ranx2,2,ranz2).getBlock());
							if(b2 != null)
							{
							Location pos = b2.getLocation();
							Creeper asdf = (Creeper)p.getWorld().spawn(pos, (Class<? extends LivingEntity>)z5.getEntityClass());
							asdf.setMetadata("pspawned", new LazyMetadataValue(SwornCritters.p, CacheStrategy.NEVER_CACHE,new MetadataCallable()));
							asdf.setPowered(true);
							SwornCritters.spawnedMobs.add(asdf.getEntityId());
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							asdf.getWorld().playEffect(pos, Effect.MOBSPAWNER_FLAMES, 0);
							}
							}
						}
					}
				}else{
					if(p.getWorld().getTime() <= 13500)
					{
						int entities = getNearbyEntities(p,32, 100, 32);
						if(entities <= mobcapperplayer)
						{
							EntityType z5 = MobTyping.getPassiveMob();
							int rxnop2 = r.nextInt(2); //r x negative or positive
							boolean negativex2 = rxnop2 == 0 ? true : false;
							int rznop2 = r.nextInt(2);
							boolean negativez2 = rznop2 == 0 ? true : false;
							int x2 = r.nextInt(ranint);
							int zt2 = r.nextInt(ranint);
							int ranx2 = negativex2 ? (x2-(x2*2)) : x2;
							int ranz2 = negativez2 ? (zt2-(zt2*2)) : zt2;
							Block b2 = getYBlock(p.getLocation().add(ranx2,2,ranz2).getBlock());
							if(b2 != null)
							{
								Location pos = b2.getLocation();
								//SwornCritters.l.info(String.valueOf(p.getLocation().getX() + ranx2) + String.valueOf(p.getLocation().getY() + 2) + String.valueOf(p.getLocation().getZ() + ranz2));
								Entity asdf = (Entity)p.getWorld().spawn(pos, (Class<? extends LivingEntity>)z5.getEntityClass());
							}
						}
					}else{
						int entities = getNearbyEntities(p,32, 100, 32);
						if(entities <= 15)
						{
						int rantest = r.nextInt(300);
						if(rantest <= 100)
						{
							EntityType z5 = MobTyping.getHostileMob();
							int rxnop2 = r.nextInt(2); //r x negative or positive
							boolean negativex2 = rxnop2 == 0 ? true : false;
							int rznop2 = r.nextInt(2);
							boolean negativez2 = rznop2 == 0 ? true : false;
							int x2 = r.nextInt(ranint);
							int zt2 = r.nextInt(ranint);
							int ranx2 = negativex2 ? (x2-(x2*2)) : x2;
							int ranz2 = negativez2 ? (zt2-(zt2*2)) : zt2;
							Block b2 = getYBlock(p.getLocation().add(ranx2,2,ranz2).getBlock());
							if(b2 != null)
							{
								Location pos = b2.getLocation();
								//SwornCritters.l.info(String.valueOf(p.getLocation().getX() + ranx2) + String.valueOf(p.getLocation().getY() + 2) + String.valueOf(p.getLocation().getZ() + ranz2));
								Entity asdf = (Entity)p.getWorld().spawn(pos, (Class<? extends LivingEntity>)z5.getEntityClass());
							}
						}
						}
					}
				}
			}
			if(SwornCritters.config.getFile().getBoolean("zombie-hell-version"))
			{
				if(getNearbyEntities(p,5,5,5,EntityType.ZOMBIE) < 10)
				{
					int tospawn = 10 - getNearbyEntities(p,5,5,5,EntityType.ZOMBIE);
					for(int i = 0; i < tospawn;i++)
					{
						Entity asdf = (Entity)p.getWorld().spawn(p.getLocation().add(r.nextInt(3),0,r.nextInt(3)), (Class<? extends LivingEntity>)Zombie.class);
					}
				
				}
			}
			}
		}
	public int getNearbyEntities(Player p,int xdist,int ydist, int zdist)
	{
		List<Entity> entitylist = p.getNearbyEntities(xdist, ydist, zdist);
		int amnt = 0;
		for(Entity e : entitylist)
		{
			if(SwornCritters.containsMob(e.getType()))
			{
				amnt = amnt+1;
			}
		}
		return amnt;
	}
	public int getNearbyEntities(Player p,int xdist,int ydist, int zdist,EntityType a)
	{
		List<Entity> entitylist = p.getNearbyEntities(xdist, ydist, zdist);
		int amnt = 0;
		for(Entity e : entitylist)
		{
			if(e.getType() == a)
			{
				amnt = amnt+1;
			}
		}
		return amnt;
	}
	public Block getYBlock(Block b)
	{
		Block blz = checkAirBlocks(b);
		if(blz != null)
		{
		Block c = b.getRelative(0, 1, 0); 
		boolean spacefound = false;
		Block lastblock = null;
		if(!(c.getType() == Material.AIR || c.getType() == Material.WATER) 
		&& !(c.getRelative(0, 1, 0).getType() == Material.AIR || c.getRelative(0, 1, 0).getType() == Material.WATER))
		{
			return null;
			//if the open space isn't found immediatly, loop until it does
		/*	lastblock = b.getWorld().getBlockAt(c.getX(),c.getY() + 2,c.getZ());
			while(spacefound == false)
			{
				boolean space1unfilled = (lastblock.getType() == Material.AIR ||  lastblock.getType() == Material.WATER) ? true : false;
				boolean space2unfilled = (lastblock.getRelative(BlockFace.UP).getType() == Material.AIR ||  lastblock.getRelative(BlockFace.UP).getType() == Material.WATER) ? true : false;
				if(space1unfilled && space2unfilled)
				{
					//space found..
					spacefound = true;
					return lastblock.getRelative(BlockFace.UP);
				}else{
					//if a space is not found, loop through again.
				lastblock = lastblock.getRelative(BlockFace.UP);
				}
			}*/
		}else{
			return c;
		}
		}else{
		return null;
		}
	}
	public Block checkAirBlocks(Block bl)
	{
		int air = 0;
		for(int i = 1; i<= 5; i++)
		{
			if(bl.getRelative(bl.getX(),bl.getY()-i,bl.getZ()).getType() == Material.AIR)
			{
				air+=1;
			}
		}
		if(air != 5)
		{
			return null;
		}else{
			return bl;
		}
	}
}