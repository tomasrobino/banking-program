package com.tomas.bankingprogram;

import java.util.HashMap;

class User {
    private final int id;
    private int pin;
    private String name, surname;
    private HashMap<Integer, Account> accountList = new HashMap<>();

    User(int id, String name, String surname, int pin) {
        this.id = id;
        this.pin = pin;
        this.name = name;
        this.surname = surname;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public int getPin() {
        return pin;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public HashMap<Integer, Account> getAccountList() {
        return accountList;
    }
    public void setAccountList(HashMap<Integer, Account> accountList) {
        this.accountList = accountList;
    }
}
