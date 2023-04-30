package com.tomas.bankingprogram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
    public final int authenticate() {
        try {
            //Initiate connection to database;
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            //Get user id;
            ResultSet rs = stmt.executeQuery(
                "SELECT id FROM users WHERE first_name = '"+name+"' AND surname = '"+surname+"' AND pin = '"+pin+"'"
            );
            if(rs.next()) {
                //User found
                int id = rs.getInt("id");
                connection.close();
                return id;
            }
            //User not found
            connection.close();
            return -1;

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }
}
