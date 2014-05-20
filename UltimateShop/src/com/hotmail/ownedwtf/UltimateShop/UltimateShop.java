package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;


public class UltimateShop  extends JavaPlugin{

    MySQLManager mysql = new MySQLManager(this, "127.0.0.1", "3306", "ushop", "root", "");
    Connection c = null;

    public void OnEnable(){
        c = mysql.openConnection();
        Bukkit.getServer().getLogger().info("UltimateShop v" + this.getDescription().getVersion() + "enabled!");

        CommandManager cm = new CommandManager();
        cm.setup();
        getCommand("ultimateshop").setExecutor(cm);
    }

    public void OnDisable(){
        Bukkit.getServer().getLogger().info("UltimateShop v " + this.getDescription().getVersion()  + " disabled!");
        mysql.closeConnection();
        c = null;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if (cmd.getName().equalsIgnoreCase("mysqlcheck")){
            MessageManager.getInstance().mysqlcheck(sender, "The Connection to your MySQL Server is " + mysql.checkConnection());
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("openmysql")){
            if(mysql.checkConnection() == false) {
                MessageManager.getInstance().mysqlcheck(sender, "MySQL is not open, openning connection now!");
                mysql.openConnection();
            }else{
                MessageManager.getInstance().success(sender, "MySQL is already Open!");
                CreateTable.createUltimateShopTable(c);
                return true;
                }
            }
        return true;
    }
}
