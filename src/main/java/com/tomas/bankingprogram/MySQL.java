package com.tomas.bankingprogram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public final class MySQL {
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
            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM users WHERE first_name=? and surname=? and pin=?");
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

            //Get user id;
            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM users WHERE first_name=? AND surname=?");
            stmt.setString(1, name);
            stmt.setString(2, surname);
            ResultSet rsId = stmt.executeQuery();
            

            if(rsId.next()) return true; //User found
            connection.close();
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

            //Get accounts owned by that user
            PreparedStatement stmt = connection.prepareStatement("SELECT id, balance FROM accounts WHERE owner_id=?");
            stmt.setInt(1, id);
            ResultSet rsAcc = stmt.executeQuery();

            int accId;
            double balance;

            ArrayList<Account> userAccounts = new ArrayList<>();
            while(rsAcc.next()) {
                accId = rsAcc.getInt("id");
                balance = rsAcc.getDouble("balance");
                userAccounts.add(new Account(accId, id, balance));
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
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users(first_name, surname, pin) VALUES(?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setInt(3, pin);
            stmt.execute();

            //Get newly-created Users id
            stmt = connection.prepareStatement("SELECT id FROM users WHERE first_name=? and surname=? and pin=?");
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setInt(3, pin);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            connection.close();

            //Return newly created Users object
            return new User(id, name, surname, pin);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }

    public static final int findUserByAccount(int accId) {
        try {
            //Initiate connection to database
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //Get user id
            PreparedStatement stmt = connection.prepareStatement("SELECT owner_id FROM accounts WHERE id=?");
            stmt.setInt(1, accId);
            ResultSet rs = stmt.executeQuery(); 
            
            if(rs.next()) {
                //User id found
                int id = rs.getInt("owner_id");
                connection.close();
                return id;
            }
            //User id not found
            connection.close();
            return 1;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }

    public static final User getUserById(int id) {
        try {
            //Initiate connection to database
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //Get user id
            PreparedStatement stmt = connection.prepareStatement("SELECT first_name, surname, pin FROM users WHERE id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery(); 
            
            if(rs.next()) {
                //User found
                String name = rs.getString("first_name");
                String surname = rs.getString("surname");
                int pin = rs.getInt("pin");
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

    public static final void updateAccount(double amount, int id) {
        try {
            //Initiate connection to database
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //Get user id
            PreparedStatement stmt = connection.prepareStatement("UPDATE accounts SET balance=balance+? WHERE id=?");
            stmt.setDouble(1, amount);
            stmt.setInt(2, id);
            stmt.execute();
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }

    public static final void newAccount(int id) {
        try {
            //Initiate connection to database;
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            //Insert new account into database table
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO accounts(owner_id, balance, type) VALUES(?, 0.0, 0)");
            stmt.setInt(1, id);
            stmt.execute();
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }
}
