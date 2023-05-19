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

public final class FundPanelController {
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    private TextField fundField;

    private Account account;

    public void initialize(Account account) {
        this.account = account;
    }

    public void confirmListener(ActionEvent event) throws IOException {
        String fieldText = fundField.getText();
        double balance = -1;
        try {
            balance = Double.parseDouble(fieldText);
        } catch (NumberFormatException exception) {
            System.out.println("Invalid amount!");
        }
        if (balance > 0.0) {
            account.changeBalance(balance);
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