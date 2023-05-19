package com.tomas.bankingprogram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

class BankingProgram extends Application{
    @Override
    public void start(Stage stage) {
        try {
            //Creates stage and loads first scene
            stage.setTitle("Banking Program");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginregister.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	public static void main(String[] args) {
        launch(args);
	}
}
