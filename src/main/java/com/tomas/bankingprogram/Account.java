package com.tomas.bankingprogram;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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
            HashMap<Integer, Account> receiverAccs = MySQL.getUserAccounts(receiverUserId);
            AtomicBoolean aux = new AtomicBoolean(false);
            receiverAccs.forEach((key, value) -> {
                if (key == second_id) {
                    changeBalance(-amount);
                    value.changeBalance(amount);
                    aux.set(true);
                }
            });
            return aux.get();
        } catch (Exception e) {
            return false;
        }
        
    }
    
}
