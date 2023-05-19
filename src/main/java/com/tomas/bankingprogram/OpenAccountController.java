package com.tomas.bankingprogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class OpenAccountController {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    User user;

    public void initialize(User user) {
        this.user = user;
    }

    @FXML
    public void openAccount(ActionEvent event) throws IOException {
        MySQL.newAccount(user.getId());
        user.setAccountList(MySQL.getUserAccounts(user.getId()));
        System.out.println("New account created");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userpanel.fxml"));
        //Switches to userpanel fxml file
        Parent root = loader.load();
        UserPanelController userPanelController = loader.getController();
        userPanelController.initialize(user);
        stage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
