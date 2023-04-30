package com.tomas.bankingprogram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


final class MySQL {
    private final String URL = "jdbc:mysql://localhost:3306/bank";
    private final String USER = "tomas";
    private final String PASSWORD = "password";

    private String name, surname;
    private int pin;

    MySQL(String name, String surname, int pin) {
        this.name = name;
        this.surname = surname;
        this.pin = pin;
    }

    //This method checks user authentication
    //Returns user id if user is found or -1 if not
    public final User authenticate() {
        try {
            //Initiate connection to database;
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            //Get user id;
            ResultSet rsId = stmt.executeQuery(
                "SELECT id FROM users WHERE first_name = '"+name+"' AND surname = '"+surname+"' AND pin = '"+pin+"'"
            );
            //Get accounts owned by that user
            ResultSet rsAcc = stmt.executeQuery(
                "SELECT 'id','balance','type' FROM accounts WHERE owner_id = '"+rsId+"' "
            );
            if(rsId.next()) {
                //User found
                int id = rsId.getInt("id");
                int accId, type;
                double balance;

                //Getting user accounts
                ArrayList<Account> userAccounts = new ArrayList<>();
                while(rsAcc.next()) {
                    accId = rsAcc.getInt("id");
                    type = rsAcc.getInt("type");
                    balance = rsAcc.getDouble("balance");
                    userAccounts.add(new Account(accId, type, id, balance));
                }
                connection.close();
                return new User(id, name, surname, pin, userAccounts);
            }
            //User not found
            connection.close();
            return new User(-1);

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }
}
