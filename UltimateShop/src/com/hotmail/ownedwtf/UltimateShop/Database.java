package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.plugin.java.JavaPlugin;
import java.sql.Connection;

public abstract class Database {

    protected JavaPlugin plugin;

    protected Database(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public abstract Connection openConnection();

    public abstract boolean checkConnection();

    public abstract Connection getConnection();

    public abstract void closeConnection();
}
