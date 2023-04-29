package com.tomas.bankingprogram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQL {
    public static void meth() {
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "tomas";
        String password = "password";

        try(Connection connection = DriverManager.getConnection(url, user, password);) {
            System.out.println("Success!");
        } catch (SQLException e) {
            System.out.println("Failure.");
        }
    }
    
    
}
