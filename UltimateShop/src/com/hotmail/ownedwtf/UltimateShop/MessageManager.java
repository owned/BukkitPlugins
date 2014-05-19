package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {
	
	private MessageManager() { }
	
	private static MessageManager instance = new MessageManager();
	
	public static MessageManager getInstance() {
		return instance;
		
	}
	public void severe(CommandSender sender, String msg){
		msg(sender, ChatColor.YELLOW + "[UltimateShop] " + ChatColor.RED, msg);
	}
	
	public void success(CommandSender sender, String msg){
		msg(sender, ChatColor.YELLOW + "[UltimateShop] " + ChatColor.GREEN, msg);
	}
	
	public void info(CommandSender sender, String msg){
		msg(sender, ChatColor.YELLOW + "[UltimateShop] " + ChatColor.WHITE, msg);
	}
	
	private void msg(CommandSender sender, String string, String msg){
		sender.sendMessage(string + msg);
	}

}