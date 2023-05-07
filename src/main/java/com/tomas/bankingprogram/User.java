package com.tomas.bankingprogram;

import java.util.ArrayList;

class User {
    private final int id;
    private int pin;
    private String name, surname;
    private ArrayList<Account> accountList = new ArrayList<>();

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
    public ArrayList<Account> getAccountList() {
        return accountList;
    }
    public void setAccountList(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }



    public boolean deleteUser(String name, String surname, int pin) {
        return true;
    }

    public boolean openAccount(String name, String surname, int pin, int type) {
        return true;
    }
}
