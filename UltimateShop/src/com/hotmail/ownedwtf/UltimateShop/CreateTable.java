package com.hotmail.ownedwtf.UltimateShop;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

        public static final String TABLE_NAME = "UShop";
        public static final String PLAYER_COLUMN = "Player";
        public static final String UUID_COLUMN = "uuid";
        public static final String VOTE_COLUMN = "VotePoints";
        public static final String DONOR_COLUMN = "DonorPoints";

        public static void createUltimateShopTable(Connection c){
            try{
                Statement statement = c.createStatement();
                statement.executeQuery("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + PLAYER_COLUMN + " VARCHAR(15), " + UUID_COLUMN + " INT, " + VOTE_COLUMN + " INT, " + DONOR_COLUMN + " INT);");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
