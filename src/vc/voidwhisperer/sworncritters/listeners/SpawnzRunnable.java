package vc.voidwhisperer.sworncritters.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import vc.voidwhisperer.sworncritters.SwornCritters;

public class SpawnzRunnable implements Runnable {
	CreatureSpawnEvent e;
	public SpawnzRunnable(CreatureSpawnEvent e2)
	{
		e2 = e;
	}
	@Override
	public void run() {
		if(e.getEntityType() != EntityType.SQUID)
		{
			if(SwornCritters.spawnedMobs.contains(e.getEntity().getEntityId()))
			{
			SwornCritters.spawnedMobs.remove(e.getEntity().getEntityId());
			}else{
			e.setCancelled(true);
			}
		}
	}

}
