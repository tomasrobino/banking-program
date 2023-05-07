package com.tomas.bankingprogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginRegisterController {
    @FXML
    private TextField login_name, login_surname, login_PIN, register_name, register_surname, register_PIN;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;


    @FXML
    private void login(ActionEvent event) throws IOException {
        if (auth(new String[]{
                login_name.getText(), login_surname.getText(), login_PIN.getText()
        }).getId() != -1) {
            switchToUserPanel(event);
        } else System.out.println("fgyukfsdkfks");
    }

    @FXML
    private User register(ActionEvent event) throws IOException {
        if (!MySQL.check(register_name.getText(), register_surname.getText())) {
            //Create user with given credentials
            switchToUserPanel(event);
            return MySQL.newUser(register_name.getText(), register_surname.getText(), Integer.parseInt(register_PIN.getText()));
        } else {
            //User already exists
            return new User(-1);
        }
    }

    private void switchToUserPanel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/userpanel.fxml"));
        stage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static User auth(String[] arr) {
        //Checks credentials with SQL server
        User user = MySQL.authenticate(
                arr[0], arr[1], Integer.parseInt(arr[2])
        );
        if (user.getId()<0) {
            System.out.println("Authentication error");
            return new User(-1);
        } else {
            System.out.println("Authentication success");
            return user;
        }
    }
}
