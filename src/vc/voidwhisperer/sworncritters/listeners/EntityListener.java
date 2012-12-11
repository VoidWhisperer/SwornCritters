package vc.voidwhisperer.sworncritters.listeners;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityVillager;
import net.minecraft.server.EntityZombie;
import net.minecraft.server.PathfinderGoalBreakDoor;
import net.minecraft.server.PathfinderGoalFloat;
import net.minecraft.server.PathfinderGoalLookAtPlayer;
import net.minecraft.server.PathfinderGoalMeleeAttack;
import net.minecraft.server.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.PathfinderGoalRandomLookaround;
import net.minecraft.server.PathfinderGoalRandomStroll;
import net.minecraft.server.PathfinderGoalSelector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftZombie;

import vc.voidwhisperer.sworncritters.SwornCritters;

public class EntityListener implements Listener {
	@EventHandler
	public void EntityCombust(EntityCombustEvent e)
	{
		if(e.getEntityType() == EntityType.SKELETON || e.getEntityType() == EntityType.ZOMBIE)
		{
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void EntitySpawnEvent(CreatureSpawnEvent e)
	{
		if(SwornCritters.config.getFile().getBoolean("Configuration.old-version") == true)
		{
		if(e.getEntityType() != EntityType.SQUID)
		{
			if(e.getSpawnReason() == SpawnReason.NATURAL || e.getSpawnReason() == SpawnReason.SPAWNER || e.getSpawnReason() == SpawnReason.CHUNK_GEN || e.getSpawnReason() == SpawnReason.DEFAULT)
			{
			e.setCancelled(true);
			}
		}
		}
		if(e.getEntityType() == EntityType.SLIME || e.getEntityType() == EntityType.MAGMA_CUBE || e.getEntityType() == EntityType.GIANT)
		{
			//SwornCritters.p.setLegendaryAlive(false);
			//SwornCritters.timesincelastlegendary = SwornCritters.p.time();
		}
		if(SwornCritters.config.getFile().getBoolean("Configuration.zombie-hell-version") == true)
		{
			if(e.getEntityType() == EntityType.ZOMBIE)
			{
				try {
					speedUpZombie((Zombie)e.getEntity());
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	@EventHandler
	public void EntitydamageByEntity(EntityDamageByEntityEvent event)
	{
		if(event.getEntityType() == EntityType.MAGMA_CUBE)
		{	
		//p.sendMessage(ChatColor.GREEN +  " --- " + ((LivingEntity)event.getEntity()).getHealth() + "---");
		//	((CraftLivingEntity)event.getEntity()).setHealth(((LivingEntity)event.getEntity()).getHealth() - (event.getDamage()));
		}
	}
	@EventHandler
	public void CreatureSplitEvent(SlimeSplitEvent e)
	{
		Entity dead = e.getEntity();
		//System.out.println(dead.getEntityId());
		for(UUID a : SwornCritters.p.getLegendaryEntityIds())
		{
		//	System.out.println(a);
		}
		if(SwornCritters.p.getLegendaryEntityIds().contains(dead.getUniqueId()))
		{
			if(e.getEntity().getType() == EntityType.MAGMA_CUBE)
			{
			MagmaCube magma = (MagmaCube) e.getEntity();
			//e.setCancelled(true);
			e.setCount(0);
			if(e.getEntity().getKiller() != null)
			{
			//e.getEntity().getKiller().getInventory().addItem(new ItemStack(41,10));
			e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(226,10));
			e.getEntity().getKiller().sendMessage(ChatColor.GREEN + "You have killed a legendary mob!");
			}
			//Event event = new EntityDeathEvent(e.getEntity(), Arrays.asList(items),300);
			//Bukkit.getServer().getPluginManager().callEvent(event);
			magma.remove();
			}else{
				//System.out.println("HAX");
				e.setCount(0);
				Slime magma = (Slime) e.getEntity();
				//ItemStack[] items = { new ItemStack(41,10) };
				if(e.getEntity().getKiller() != null)
				{
					e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(266,10));
				e.getEntity().getKiller().sendMessage(ChatColor.GREEN + "You have killed a legendary mob!");
				}
				//	Event event = new EntityDeathEvent(e.getEntity(), Arrays.asList(items),300);
				//Bukkit.getServer().getPluginManager().callEvent(event);
				magma.remove();
			}
			if(SwornCritters.p.getLegendaryEntityIds().size() == 0)
			{
			SwornCritters.p.setLegendaryAlive(false);
			SwornCritters.timesincelastlegendary = SwornCritters.p.time();
			}
		}
	}
	@EventHandler
	public void CreatureDeathEvent(EntityDeathEvent e)
	{
		Entity dead = e.getEntity();
		if(SwornCritters.p.getLegendaryEntityIds().contains(dead.getUniqueId()))
		{
			if(e.getEntity().getType() == EntityType.MAGMA_CUBE)
			{
			MagmaCube magma = (MagmaCube) e.getEntity();
			SwornCritters.p.removeLegendary(dead.getUniqueId());
			e.setDroppedExp(300);
			e.getDrops().clear();
			e.getDrops().add(new ItemStack(41,10));
			}
			if(e.getEntity().getType() == EntityType.GIANT)
			{
				SwornCritters.p.removeLegendary(dead.getUniqueId());
				e.setDroppedExp(100);
				e.getDrops().clear();
				//e.getDrops().add(new ItemStack(41,3));
			}
			if(e.getEntity().getType() == EntityType.SLIME)
			{
				Slime magma = (Slime) e.getEntity();
				SwornCritters.p.removeLegendary(dead.getUniqueId());
				e.setDroppedExp(300);
				e.getDrops().clear();
				//e.getDrops().add(new ItemStack(41,10));
			}
			if(SwornCritters.p.getLegendaryEntityIds().size() == 0)
			{
			SwornCritters.p.setLegendaryAlive(false);
			SwornCritters.timesincelastlegendary = SwornCritters.p.time();
			}
		}
	}
    public static void speedUpZombie(Zombie z) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Location loc = z.getLocation();
        EntityZombie zombie = ((CraftZombie) z).getHandle();
        Field fGoalSelector = EntityLiving.class.getDeclaredField("goalSelector");
        fGoalSelector.setAccessible(true);
        Float speed = 0.5F;
        PathfinderGoalSelector gs = new PathfinderGoalSelector(((CraftWorld) loc.getWorld()).getHandle() != null && ((CraftWorld) loc.getWorld()).getHandle().methodProfiler != null ? ((CraftWorld) loc.getWorld()).getHandle().methodProfiler : null);
        gs.a(0, new PathfinderGoalFloat(zombie));
        gs.a(1, new PathfinderGoalBreakDoor(zombie));
        gs.a(2, new PathfinderGoalMeleeAttack(zombie, EntityHuman.class, speed, false));
        gs.a(3, new PathfinderGoalMeleeAttack(zombie, EntityVillager.class, speed, true));
        gs.a(4, new PathfinderGoalMoveTowardsRestriction(zombie, speed));
        gs.a(5, new PathfinderGoalMoveThroughVillage(zombie, speed, false));
        gs.a(6, new PathfinderGoalRandomStroll(zombie, speed));
        gs.a(7, new PathfinderGoalLookAtPlayer(zombie, EntityHuman.class, 15.0F));
        gs.a(7, new PathfinderGoalRandomLookaround(zombie));
        fGoalSelector.set(zombie, gs);
    }

}
