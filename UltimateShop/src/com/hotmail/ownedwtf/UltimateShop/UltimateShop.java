package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


import java.sql.Connection;
import java.sql.SQLException;


public class UltimateShop extends JavaPlugin {

    private static UltimateShop instance;

    MySQLManager mysql = new MySQLManager(this, getConfig().getString("MySQL.HostName"), getConfig().getString("MySQL.Port"), getConfig().getString("MySQL.Database"), getConfig().getString("MySQL.User"), getConfig().getString("MySQL.Password"));
    Connection c = null;
    CommandManager cm = new CommandManager();

    public static UltimateShop getInstance() {
        return instance;
    }
    public void onEnable() {

        FileConfiguration config = getConfig();

        config.addDefault("MySQL.Enabled", "true");
        config.addDefault("MySQL.HostName", "localhost");
        config.addDefault("MySQL.Port", "3306");
        config.addDefault("MySQL.Database", "");
        config.addDefault("MySQL.User", "");
        config.addDefault("MySQL.Password", "");

        config.options().copyDefaults(true);
        saveConfig();

        instance = this;
        mysql.openConnection();
        CreateMySQLTable();
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

    public void checkMySQL() throws SQLException, ClassNotFoundException{
        Connection con = null;
        con = mysql.getConnection();
        if(!con.isClosed()){
            mysql.openConnection();
        }

    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(cmd.getName().equalsIgnoreCase("test")){
            mysql.openConnection();
            MessageManager.getInstance().info(sender, "Your mysql is " + mysql.checkConnection());
            mysql.updateSQL("INSERT INTO `ushop`.`ushop` (`player`, `uuid`, `vote`, `donor`) VALUES ('test1', 'test', '12', '15');");
        }
        return true;
    }

  }