package vc.voidwhisperer.sworncritters.listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import vc.voidwhisperer.sworncritters.SwornCritters;

public class CommandSetDist implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] args) {
		if(arg0.isOp())
		{
			SpawnRunnable.ranint = Integer.parseInt(args[0]);
			arg0.sendMessage(ChatColor.GREEN + "Set distance");
			return true;
		}else{
			arg0.sendMessage(ChatColor.RED + "You don't have permission to use that command.");
			return true;
		}
	}

}
