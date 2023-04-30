package com.tomas.bankingprogram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


final class MySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USER = "tomas";
    private static final String PASSWORD = "password";

    //This method checks user authentication
    //Returns user object if user is found or -1 if not
    public static final User authenticate(String name, String surname, int pin) {
        try {
            //Initiate connection to database;
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //Get user id;
            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM users WHERE ? and ? and ?");
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setInt(3, pin);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                //User found
                int id = rs.getInt("id");
                connection.close();
                return new User(id, name, surname, pin);
            }
            //User not found
            connection.close();
            return new User(-1);

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }

    //Check whether user exists
    public static final boolean check(String name, String surname) {
        try {
            //Initiate connection to database;
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            //Get user id;
            ResultSet rsId = stmt.executeQuery(
                "SELECT id FROM users WHERE first_name = '"+name+"' AND surname = '"+surname+"'"
            );
            connection.close();

            if(rsId.next()) return true; //User found
            return false; //User not found
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }

    //Getting user accounts
    public static final ArrayList<Account> getUserAccounts(int id) {
        try {
            //Initiate connection to database;
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();

            int accId, type;
            double balance;

            //Get accounts owned by that user
            ResultSet rsAcc = stmt.executeQuery(
                "SELECT 'id','balance','type' FROM accounts WHERE owner_id = '"+id+"' "
            );

            ArrayList<Account> userAccounts = new ArrayList<>();
            while(rsAcc.next()) {
                accId = rsAcc.getInt("id");
                type = rsAcc.getInt("type");
                balance = rsAcc.getDouble("balance");
                userAccounts.add(new Account(accId, type, id, balance));
            }
            connection.close();
            return userAccounts;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }

    }

    //Adding new user
    public static final User newUser(String name, String surname, int pin) {
        try {
            //Initiate connection to database;
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            //Insert new user into database table
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users(first_name, surname, pin) VALUES(?, ?, ?");
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setInt(3, pin);
            stmt.execute();

            //Get newly-created User's id
            stmt = connection.prepareStatement("SELECT id FROM users WHERE ? and ? and ?");
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setInt(3, pin);
            ResultSet rs = stmt.executeQuery();
            int id = rs.getInt("id");
            connection.close();

            //Return newly created User's object
            return new User(id, name, surname, pin);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }
}
