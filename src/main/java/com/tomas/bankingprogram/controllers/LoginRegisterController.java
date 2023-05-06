package com.tomas.bankingprogram.controllers;

import com.tomas.bankingprogram.MySQL;
import com.tomas.bankingprogram.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.tomas.bankingprogram.BankingProgram.auth;

public class LoginRegisterController {
    @FXML
    private TextField login_name;
    @FXML
    private TextField login_surname;
    @FXML
    private TextField login_password;
    @FXML
    private TextField register_name;
    @FXML
    private TextField register_surname;
    @FXML
    private TextField register_password;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;


    public void login(ActionEvent event) throws IOException {
        if (auth(new String[]{login_name.getText(), login_surname.getText(), login_password.getText()}).getId() != -1) {
            switchToUserPanel(event);
        } else System.out.println("fgyukfsdkfks");
    }

    public User register(ActionEvent event) {
        if (!MySQL.check(register_name.getText(), register_surname.getText())) {
            //Create user with given credentials
            return MySQL.newUser(register_name.getText(), register_surname.getText(), Integer.parseInt(register_password.getText()));
        } else {
            //User already exists
            return new User(-1);
        }
    }

    public void switchToUserPanel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/userpanel.fxml"));
        stage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
