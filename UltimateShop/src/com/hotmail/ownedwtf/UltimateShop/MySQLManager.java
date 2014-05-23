package com.hotmail.ownedwtf.UltimateShop;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

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

    private static UltimateShop instance;

    public static UltimateShop getInstance() {
        return instance;
    }

    public Connection openConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not connect to MySQL server! because: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
        }
        return connection;
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

    public ResultSet querySQL(String query) {
        Connection c = null;

        if (checkConnection()) {
            c = getConnection();
        } else {
            c = openConnection();
        }

        Statement s = null;

        try {
            s = c.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        ResultSet ret = null;

        try {
            ret = s.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return ret;
    }

    public void updateSQL(String update) {

        Connection c = null;

        if (checkConnection()) {
            c = getConnection();
        } else {
            c = openConnection();
        }

        Statement s = null;

        try {
            s = c.createStatement();
            s.executeUpdate(update);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        closeConnection();

    }

    /*
    public int getPlayerVotePoints(Player p){
        try {
            PreparedStatement st = connection.prepareStatement("SELECT " + CreateTable.VOTE_COLUMN + " FROM " + CreateTable.TABLE_NAME + " WHERE " + CreateTable.PLAYER_COLUMN + " = '?';");
            st.setString(1, p.getName());
            ResultSet result = st.executeQuery();
            result.next();
            return result.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    */

}
