package com.tomas.bankingprogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.tomas.bankingprogram.BankingProgram.auth;

public class LoginRegister {
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField password;


    public void login(ActionEvent event) {
        auth(new String[]{name.getText(), surname.getText(), password.getText()});
    }

    public void register(ActionEvent event) {
        System.out.println("Register");
    }
}
