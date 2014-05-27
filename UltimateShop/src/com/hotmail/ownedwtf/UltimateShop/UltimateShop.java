package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;

import java.sql.*;


public class UltimateShop extends JavaPlugin implements Listener {

    private static UltimateShop instance;

    MySQLManager mysql = new MySQLManager(this, getConfig().getString("MySQL.HostName"), getConfig().getString("MySQL.Port"), getConfig().getString("MySQL.Database"), getConfig().getString("MySQL.User"), getConfig().getString("MySQL.Password"));
    Connection c = null;
    CommandManager cm = new CommandManager();

    public static UltimateShop getInstance() {
        return instance;
    }

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

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

    public void CreateMySQLTable() {
        mysql.updateSQL("CREATE TABLE IF NOT EXISTS ushop ( "
                + "   player VARCHAR(20), uuid VARCHAR(100), "
                + "   vote INT, donor INT, logins INT)");
    }

    public boolean playerDataCheck(Player p){
        try {
            PreparedStatement SQL = mysql.openConnection().prepareStatement("SELECT * FROM `ushop` WHERE player=?;");
            SQL.setString(1, p.getName());
            ResultSet resultSet = SQL.executeQuery();
            boolean playerisHere = resultSet.next();

            SQL.close();
            resultSet.close();
            return playerisHere;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        mysql.openConnection();
        try{
            int PastLogins = 0;

            if (playerDataCheck(event.getPlayer())){
                PreparedStatement SQL = mysql.openConnection().prepareStatement("SELECT * FROM `ushop` WHERE player=?;");
                SQL.setString(1, event.getPlayer().getName());
                ResultSet rs = SQL.executeQuery();
                rs.next();

                PastLogins = rs.getInt("logins");

                PreparedStatement loginUpdater = mysql.openConnection().prepareStatement("UPDATE `ushop` SET logins=? WHERE player=?;");
                loginUpdater.setInt(1, PastLogins + 1);
                loginUpdater.setString(2, event.getPlayer().getName());
                loginUpdater.executeUpdate();

                loginUpdater.close();
                SQL.close();
                rs.close();
            }else{
                PreparedStatement addNewPlayer = mysql.openConnection().prepareStatement("INSERT INTO `ushop` values(?,?,0,0,1);");
                addNewPlayer.setString(1, event.getPlayer().getName());
                addNewPlayer.setString(2, event.getPlayer().getUniqueId().toString());
                addNewPlayer.execute();
                addNewPlayer.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mysql.closeConnection();
        }
    }

}