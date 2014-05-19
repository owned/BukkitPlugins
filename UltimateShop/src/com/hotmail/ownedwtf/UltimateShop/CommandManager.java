package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager {

    private MySQLManager mysql;

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if (!(sender instanceof Player)){
            MessageManager.getInstance().severe(sender, "You must be a player.");
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("votepoints")){
            if(sender.hasPermission("ultimateshop.viewpoints")) {
                MessageManager.getInstance().severe(sender, " You are not permitted to use this command.");
                return true;
            }

            if (args.length == 0){
                MessageManager.getInstance().severe(sender, " Please specify a player.");
                return true;
            }
            @Deprecated
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null){
                MessageManager.getInstance().severe(sender, "Cannot find specified player " + args[0] + ".");
                return true;

            }

        }
        return true;
    }
}
