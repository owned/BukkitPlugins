package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;


public class CommandManager implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("us") || cmd.getName().equalsIgnoreCase("ultimateshop")) {
            Player p = (Player) sender;

            if (p.hasPermission("ultimateshop.user")) {
                if (args.length == 0) {

                    PluginDescriptionFile pdf = UltimateShop.getInstance().getDescription();

                    p.sendMessage(ChatColor.GOLD + "----------" + ChatColor.GREEN + "UltimateShop " + ChatColor.GOLD + "----------");
                    p.sendMessage(ChatColor.BLUE + "Author: " + ChatColor.GOLD + "ownedwtf");
                    p.sendMessage(ChatColor.BLUE + "Version: " + ChatColor.GOLD + pdf.getVersion());
                    p.sendMessage(ChatColor.BLUE + "/us help - " + ChatColor.GOLD + "Show's the user command help.");
                    p.sendMessage(ChatColor.BLUE + "/us help admin -  " + ChatColor.GOLD + "Show's the admin command help.");

                    return true;
                }

                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage(ChatColor.GOLD + "----------" + ChatColor.GREEN + "UltimateShop Help" + ChatColor.GOLD + "----------");
                        p.sendMessage(ChatColor.GOLD + "/us votepoints - " + ChatColor.BLUE + "Shows you your available vote points");
                        return true;
                    }
                }
                //MySQLManager.getInstance().mysql.getConnection();
                if (args[0].equalsIgnoreCase("votepoints")) {
                    MessageManager.getInstance().mysqlcheck(p, String.format("Your MySQL is %s", MySQLManager.getInstance().mysql.checkConnection()));
                }
                return true;
            }
            return false;
        }
        return false;
    }
}
