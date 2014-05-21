package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


import java.sql.Connection;


public class UltimateShop  extends JavaPlugin {

    MySQLManager mysql = new MySQLManager(this, "localhost", "3306", "ushop", "root", "");
    Connection c = null;
    CommandManager cm = new CommandManager();

    public void onEnable() {
        mysql.openConnection();
        CreateMySQLTable();
        cm.setup();
        this.getCommand("ultimateshop").setExecutor(cm);
    }

    public void onDisable() {
        Bukkit.getServer().getLogger().info("UltimateShop v " + this.getDescription().getVersion() + " disabled!");
        mysql.closeConnection();
        c = null;
    }
    public void CreateMySQLTable(){
            mysql.updateSQL("CREATE TABLE IF NOT EXISTS ushop ( "
                    + "   player VARCHAR(20), uuid VARCHAR(100), "
                    + "   vote INT, donor INT )");
        }
    }