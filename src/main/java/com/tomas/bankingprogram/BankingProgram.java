package com.tomas.bankingprogram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingProgram extends Application{
    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Banking Program");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginregister.fxml"));
            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	public static void main(String[] args) {
        launch(args);
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        //From this point on user is authenticated
        System.out.println("Authentication successful.");

        boolean st;
        do {
            if (currentUser.getAccountList().size()>0) {
                //User first has to select account on which to operate

                //Generating console prompt for selecting account
                StringBuilder consoleString = new StringBuilder("Please");
                for(int i=0;i<currentUser.getAccountList().size();i++) {
                    consoleString.append(", write ").append(i + 1).append(" for account #").append(currentUser.getAccountList().get(i).getId());
                }
                System.out.println(consoleString);
                System.out.println("If you wish to open another account, write 0");

                int sel = scanner.nextInt()-1;
                scanner.nextLine();
                //Check action user wants to do
                if(sel > -1){
                    //Selected account
                    Account accSel = currentUser.getAccountList().get(sel);
                    System.out.println("You have selected account #"+accSel.getId());

                    //Choosing operation to do on selected account
                    System.out.println("Please write 1 if you wish to review your balance, 2 to add funds, 3 to withdraw, 4 to transfer funds");
                    int op = scanner.nextInt();
                    if(op == 1) {
                        System.out.println("Your balance is: "+accSel.getBalance());
                    } else if(op == 2 || op == 3) {
                        System.out.println("Please introduce amount:");
                        if (op == 2) {
                            accSel.changeBalance(scanner.nextDouble());
                        } else {
                            double wd = scanner.nextDouble();
                            if(accSel.getBalance() >= wd) {
                                accSel.changeBalance(-wd);
                            } else {
                                System.out.println("Error. Insufficient funds");
                            }
                        }
                        System.out.println("Your balance is $"+accSel.getBalance());
                    } else if(op == 4) {
                        boolean status1;
                        do {
                            System.out.println("Please write the number of the account you wish to transfer funds to:");
                            int aux1 = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Please write the amount:");
                            int aux2 = scanner.nextInt();
                            scanner.nextLine();
                            if (accSel.getBalance() >= aux2) {
                                status1 = accSel.transfer(aux1, aux2);
                                if(!status1) {
                                    System.out.println("Error. Please try again");
                                }
                            } else {
                                System.out.println("Error. Insufficient funds");
                                status1 = true;
                            }
                        } while(!status1);
                    }
                } else {
                    //Open new account
                    MySQL.newAccount(currentUser.getId());
                    currentUser.setAccountList(MySQL.getUserAccounts(currentUser.getId()));
                    System.out.println("New account created");
                }
            } else {
                //User has no account open
                MySQL.newAccount(currentUser.getId());
                System.out.println("You had no accounts open. New account created");
            }

            System.out.println("Do you wish to do something else?");
            System.out.println("Write 1 for yes and 0 for no");
            try {
                st = scanner.nextInt() != 1;
            } catch (InputMismatchException e) {
                st = true;
            }
        } while (!st);

        scanner.close();
	}
}
