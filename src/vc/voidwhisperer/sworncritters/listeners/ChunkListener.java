package vc.voidwhisperer.sworncritters.listeners;

import java.lang.reflect.Field;

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

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import vc.voidwhisperer.sworncritters.SwornCritters;

public class ChunkListener implements Listener {
/* Well I guess there is a first time for everything... */
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e)
	{
		for(Entity c : e.getChunk().getEntities())
		{
			if(c.getType() == EntityType.ZOMBIE)
			{
				if(SwornCritters.config.getFile().getBoolean("Configuration.zombie-hell-version") == true)
				{
					try {
					speedUpZombie((Zombie)c);
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
