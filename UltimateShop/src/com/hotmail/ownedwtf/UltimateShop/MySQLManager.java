package com.hotmail.ownedwtf.UltimateShop;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.logging.Level;

public class MySQLManager extends Database {

    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;

    private Connection connection;

    public MySQLManager(JavaPlugin plugin, String hostname, String port, String database, String username, String password) {
        super(plugin);
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
        this.connection = null;
    }

    public Connection openConnection(){
        try{
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkConnection(){
        return connection != null;
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        if (connection != null){
            try {
                connection.close();
            }catch(SQLException e){
                Bukkit.getLogger().log(Level.SEVERE, "Error Closing the MySQL Connection!");
                e.printStackTrace();
            }
        }
    }

    public int getPlayerVotePoints(CommandSender sender, Player p, String[] args){
        try {
            PreparedStatement st = connection.prepareStatement("select vpoints from users where uuid'" + p.getUniqueId() + "'");
            st.setString(1, p.getName());
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return result.getInt("vpoints");
            } else {
                return result.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

}
