package com.tomas.bankingprogram;

public class Account {
    private final int id;
    private int type, owner_id;
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
    public void setType(int type) {
        this.type = type;
    }
    public int getOwner_id() {
        return owner_id;
    }
    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(float balance) {
        this.balance = balance;
    }
    
}
