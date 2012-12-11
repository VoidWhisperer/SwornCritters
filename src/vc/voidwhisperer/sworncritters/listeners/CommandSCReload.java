package vc.voidwhisperer.sworncritters.listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import vc.voidwhisperer.sworncritters.SwornCritters;

public class CommandSCReload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] args) {
		if(arg0.isOp())
		{
			SwornCritters.config.reloadFile();
			arg0.sendMessage(ChatColor.GREEN + "Config reloaded");
			return true;
		}else{
			arg0.sendMessage(ChatColor.RED + "You don't have permission to use that command.");
			return true;
		}
	}

}
