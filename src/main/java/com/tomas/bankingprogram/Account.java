package com.tomas.bankingprogram;

public class Account {
    private final int id, type, owner_id;
    private double balance;

    public Account(int id, int type, int owner_id, double balance) {
        this.id = id;
        this.type = type;
        this.owner_id = owner_id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }
    public int getType() {
        return type;
    }
    public int getOwner_id() {
        return owner_id;
    }


    public double getBalance() {
        return balance;
    }
    public void changeBalance(double balance) {
        this.balance += balance;
    }
    
}
