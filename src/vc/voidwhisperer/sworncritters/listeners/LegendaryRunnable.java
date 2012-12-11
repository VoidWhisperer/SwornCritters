package vc.voidwhisperer.sworncritters.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;

import vc.voidwhisperer.sworncritters.SwornCritters;

public class LegendaryRunnable implements Runnable {

	@Override
	public void run() {
		if(SwornCritters.config.getFile().getBoolean("Configuration.Legendaries-Enabled"))
		{
			if(!SwornCritters.p.isLegendaryAlive())
			{
				if(SwornCritters.timesincelastlegendary + 300000 < SwornCritters.p.time())
				{
					if(SwornCritters.config.getFile().getBoolean("Configuration.notify-legendary-spawn"))
					{
						sendMessage(ChatColor.GREEN + "[SwornCreatures] A Legendary as been spawned!");
						//Bukkit.getLogger().info(ChatColor.GREEN + "TEST1");
					}
					Player[] players = Bukkit.getServer().getOnlinePlayers();
					Random r = new Random(System.currentTimeMillis());
					Player theOne;
					if(players.length > 1)
					{
					theOne = players[r.nextInt(players.length-1)];
					}else if(players.length == 1){
						theOne = players[0];
					}else{
						return;
					}
					int type = r.nextInt(2);
					if(type == 0)
					{
					int ran = r.nextInt(2); // 80
					int poz = ran < 30 ? 30 : ran; 
					Location pos = new Location(theOne.getWorld(),poz+theOne.getLocation().getX(),
							90, /*This is to hopefully make sure it's above land.. */
							poz+theOne.getLocation().getZ());
					if(SwornCritters.usingFactions == true)
					{
						FLocation floc = new FLocation(pos);
						if(Board.getFactionAt(floc).isSafeZone() || Board.getFactionAt(floc).isWarZone() ||!Board.getFactionAt(floc).isNone())
						{
							return;
						}
					}
					Giant giant = (Giant)theOne.getWorld().spawn(pos, (Class<? extends LivingEntity>)Giant.class);
					SwornCritters.p.addLegendary(giant.getUniqueId());
					//slime.setHealth(20);
					SwornCritters.p.setLegendaryAlive(true);
					((CraftLivingEntity)giant).getHandle().setHealth(1000);
					/* See EntityListener for the drop setting */
					}else{
					int ran = r.nextInt(2);
					int poz = ran < 30 ? 30 : ran;  
	
					Location pos = new Location(theOne.getWorld(),poz+theOne.getLocation().getX(),
					90, /*This is to hopefully make sure it's above land.. */
					poz+theOne.getLocation().getZ());	
					if(SwornCritters.usingFactions == true)
					{
					FLocation floc = new FLocation(pos);
					if(Board.getFactionAt(floc).isSafeZone() || Board.getFactionAt(floc).isWarZone() ||!Board.getFactionAt(floc).isNone())
					{
						return;
					}
					}
					Slime slime = (Slime)theOne.getWorld().spawn(pos, (Class<? extends LivingEntity>)Slime.class);
					slime.setSize(25);
					((CraftLivingEntity)slime).getHandle().setHealth(1200);
					System.out.println(slime.getUniqueId());
					SwornCritters.p.addLegendary(slime.getUniqueId());
					System.out.println(slime.getUniqueId());
					SwornCritters.p.setLegendaryAlive(true);
					}
				}
			}
		}
	}
	public void sendMessage(String text)
	{
		for(Player p : Bukkit.getServer().getOnlinePlayers())
		{
			p.sendMessage(text);
		}
	}

}
