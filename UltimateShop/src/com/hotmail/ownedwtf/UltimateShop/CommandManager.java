package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.sql.Connection;
import java.sql.SQLException;


public class CommandManager implements CommandExecutor {

    MySQLManager mysql;

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
                        p.sendMessage(ChatColor.GOLD + "/us donorpoints - " + ChatColor.BLUE + "Shows you your available donor points");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("votepoints")){
                    MessageManager.getInstance().success(p, "Vote Points: " + UltimateShop.getInstance().mysql.getPlayerVotePoints(p));
                }

                if (args[0].equalsIgnoreCase("donorpoints")){
                    MessageManager.getInstance().success(p, "Donor Points: " + UltimateShop.getInstance().mysql.getPlayerDonorPoints(p));
                }
                return true;
            }
            return false;
        }
        return false;
    }
}
