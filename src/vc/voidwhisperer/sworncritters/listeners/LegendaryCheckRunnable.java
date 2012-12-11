package vc.voidwhisperer.sworncritters.listeners;

import org.bukkit.entity.Entity;

import vc.voidwhisperer.sworncritters.SwornCritters;

public class LegendaryCheckRunnable implements Runnable {

	@Override
	public void run() {
		for(Entity e : SwornCritters.lEntities)
		{
			if(SwornCritters.p.getLegendaryEntityIds().contains(e.getUniqueId()))
			{
			if(!e.isValid())
			{
				SwornCritters.p.removeLegendary(e.getUniqueId());
				SwornCritters.timesincelastlegendary = SwornCritters.p.time();
				SwornCritters.p.setLegendaryAlive(false);
			}
		}
	}
	}
}
