package com.tomas.bankingprogram;

public class User {
    private final int id;
    private int pin;
    private String name, surname;

    User(int id, String name, String surname, int pin) {
        this.id = id;
        this.pin = pin;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }
    public int getPin() {
        return pin;
    }
    public void setPin(int pin) {
        this.pin = pin;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean deleteUser(String name, String surname, int pin) {
        return true;
    }

    public boolean openAccount(String name, String surname, int pin, int type) {
        return true;
    }

    public boolean deposit() {
        return true;
    }

    public boolean withdraw() {
        return true;
    }
}
