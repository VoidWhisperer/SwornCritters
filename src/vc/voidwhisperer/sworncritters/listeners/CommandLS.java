package vc.voidwhisperer.sworncritters.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import vc.voidwhisperer.sworncritters.SwornCritters;

public class CommandLS implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if(arg0 instanceof Player)
		{
			Player p = (Player)arg0;
		for(UUID uid : SwornCritters.p.getLegendaryEntityIds())
		{
			
			List<Entity> entities = p.getWorld().getEntities();
			for(Entity e : entities)
			{
				if(e.getUniqueId() == uid)
				{
					p.sendMessage(ChatColor.GOLD + "Legendary Type: " + e.getType().toString());
					p.sendMessage(" ");
					p.sendMessage(ChatColor.GREEN + "Legendary HP: " + ((LivingEntity)e).getHealth());
					p.sendMessage(" ");
					p.sendMessage(ChatColor.GREEN + "Legendary Location: " + (e.getLocation().getBlockX() + "," + e.getLocation().getBlockY() + "," + e.getLocation().getBlockZ() + " in world " + e.getLocation().getWorld().getEnvironment()));
					return true;
				}
			}
			if(SwornCritters.p.getLegendaryEntityIds().size() == 0)
			{
				p.sendMessage(ChatColor.GREEN + "No legenadaries are alive. Another will spawn in about "+ getTime(p));
			}
		}
		}
		return false;
	}
	public String getTime(Player p)
	{
		long hph = (SwornCritters.timesincelastlegendary/1000) + 30;
		// hour + half, which is 5400 seconds.
		long timeleft = hph - SwornCritters.p.time()/1000;
		if(timeleft < 0)
		{
			throw new IllegalStateException("Time left is negative");
		}
		long hours = timeleft / 3600;
		long remainder = timeleft % 3600;
		long minutes = remainder / 60;
		long seconds = remainder % 60;
		return (hours < 10 ? "0" : "") + hours
		+ ":" + (minutes < 10 ? "0" : "") + minutes
		+ ":" + (seconds < 10 ? "0" : "") + seconds + " left until an event!";
	}
}
