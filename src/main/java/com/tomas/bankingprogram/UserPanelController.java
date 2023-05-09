package com.tomas.bankingprogram;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class UserPanelController {
    @FXML
    private GridPane gridPane;

    public void initialize(User user) {
        //Gets all accounts corresponding to user
        ArrayList<Account> accountList = MySQL.getUserAccounts(user.getId());
        int counter = 0;
        //Displays them graphically in gridPane
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 3; i++) {
                if (counter != accountList.size()) {
                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(
                            new Text( String.valueOf(accountList.get(counter).getId()) ),
                            new Text( String.valueOf(accountList.get(counter).getBalance()) )
                    );
                    gridPane.add(vBox, i, k);
                    counter++;
                }
            }
        }
    }
}
