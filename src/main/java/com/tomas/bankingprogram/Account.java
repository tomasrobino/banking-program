package com.tomas.bankingprogram;

import java.util.ArrayList;

public class Account {
    private final int id, owner_id;
    private double balance;

    public Account(int id, int owner_id, double balance) {
        this.id = id;
        this.owner_id = owner_id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }
    public int getOwner_id() {
        return owner_id;
    }


    public double getBalance() {
        return balance;
    }
    public void changeBalance(double balance) {
        this.balance += balance;
        MySQL.updateAccount(balance, this.id);
    }

    public boolean transfer(int second_id, double amount) {
        try {
            int receiverUserId = MySQL.findUserByAccount(second_id);
            ArrayList<Account> receiverAccs = MySQL.getUserAccounts(receiverUserId);
            for (Account i : receiverAccs) {
                if (i.getId() == second_id) {
                    this.balance -= amount;
                    i.changeBalance(amount);
                    MySQL.updateAccount(amount, i.getId());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
        
    }
    
}
