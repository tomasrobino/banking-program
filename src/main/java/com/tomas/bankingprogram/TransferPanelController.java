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

public class TransferPanelController {
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    private TextField transferField, accountField;

    private Account account;

    public void initialize(Account account) {
        this.account = account;
    }

    public void confirmListener(ActionEvent event) throws IOException {
        String fieldText = transferField.getText();
        double balance = -1;
        try {
            balance = Double.parseDouble(fieldText);
        } catch (NumberFormatException exception) {
            System.out.println("Invalid amount!");
        }

        String accountText = accountField.getText();
        int accountNumber = -1;
        try {
            accountNumber = Integer.parseInt(accountText);
        } catch (NumberFormatException exception) {
            System.out.println("Invalid amount!");
        }

        if (balance > 0.0 && accountNumber > 0) {
            account.transfer(accountNumber, balance);
            switchToUserPanel(event, account);
        } else System.out.println("Invalid amount!");
    }

    public void cancelListener(ActionEvent event) throws IOException {
        switchToUserPanel(event, account);
    }

    public void switchToUserPanel(ActionEvent event, Account account) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userpanel.fxml"));
        //Switches to userpanel fxml file
        Parent root = loader.load();
        UserPanelController userPanelController = loader.getController();
        userPanelController.initialize(account);
        stage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}