package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;


public class UltimateShop  extends JavaPlugin{

    private MySQLManager mysql;
    Connection con;

    public void OnEnable(){
        mysql = new MySQLManager(this, "localhost", "3306", "UShop", "root", "");
        mysql.openConnection();
        CreateTable.createUltimateShopTable(con);
        Bukkit.getServer().getLogger().info("UltimateShop v" + this.getDescription().getVersion() + "enabled!");
    }

    public void OnDisable(){
        Bukkit.getServer().getLogger().info("UltimateShop v " + this.getDescription().getVersion()  + " disabled!");
        mysql.closeConnection();
        con = null;
    }
}
