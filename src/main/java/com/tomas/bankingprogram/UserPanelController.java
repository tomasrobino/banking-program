package com.tomas.bankingprogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class UserPanelController {
    private int selected;
    @FXML
    private GridPane gridPane;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Button fund, withdraw, transfer;

    private HashMap<Integer, Account> accountList;

    public void initialize(User user) {
        //Gets all accounts corresponding to user
         accountList = MySQL.getUserAccounts(user.getId());
        //Displays them graphically in gridPane
        AtomicInteger i = new AtomicInteger(1);
        AtomicInteger k = new AtomicInteger(1);
        accountList.forEach((key, value) -> {
            VBox vBox = new VBox();
            vBox.setOnMouseClicked(this::accountClickListener);
            vBox.getChildren().addAll(
                    new Text( String.valueOf(key) ),
                    new Text( String.valueOf(value.getBalance()) )
            );
            gridPane.add(vBox, k.get() -1, i.get() -1);
            k.getAndIncrement();
            if (k.get() == 4) {
                k.set(1);
                i.getAndIncrement();
            }
        });
        Button open = new Button("Open new account");
        open.setOnAction(event -> {
            try {
                switchToOpenAccountPanel(event, user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        gridPane.add(open, accountList.size()-1, accountList.size()-1);
    }

    public void initialize(Account account) {
        initialize( MySQL.getUserById( account.getOwner_id() ) );
    }

    private void switchToOpenAccountPanel(ActionEvent event ,User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/openaccount.fxml"));
        //Switches to openaccount fxml file
        Parent root = loader.load();
        OpenAccountController openAccountController = loader.getController();
        openAccountController.initialize(user);
        stage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void accountClickListener(MouseEvent event) {
        VBox vBox = (VBox) event.getSource();
        selected = Integer.parseInt( ( (Text) vBox.getChildren().get(0) ).getText() );
        fund.setDisable(false);
        withdraw.setDisable(false);
        transfer.setDisable(false);
    }

    public void addFundsListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fundpanel.fxml"));
        //Switches to fundpanel fxml file
        Parent root = loader.load();
        FundPanelController fundPanelController = loader.getController();
        fundPanelController.initialize(accountList.get(selected));
        stage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void withdrawListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/withdrawpanel.fxml"));
        //Switches to withdrawpanel fxml file
        Parent root = loader.load();
        WithdrawPanelController withdrawPanelController = loader.getController();
        withdrawPanelController.initialize(accountList.get(selected));
        stage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void transferListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/transferpanel.fxml"));
        //Switches to transferpanel fxml file
        Parent root = loader.load();
        TransferPanelController transferPanelController = loader.getController();
        transferPanelController.initialize(accountList.get(selected));
        stage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}