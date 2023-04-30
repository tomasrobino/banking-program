package com.tomas.bankingprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class BankingProgram {
	public static void main(String[] args) {
		SpringApplication.run(BankingProgram.class, args);

		String name, surname;
        int pin=0;
        Scanner scanner = new Scanner(System.in);
        MySQL sql;
        User currentUser = null;

        //Start of console dialog
        System.out.println("Welcome!");
        do {
            System.out.println("If you already have an account, write 1, if you do not, write 0");
            int aux;
            //Checking answer, if not 0 or 1, tries again
            do {
                try {
                    aux=scanner.nextInt();
                    scanner.nextLine();
                    if (aux==1) {
                        //Asking for user credentials
                        System.out.println("Please identify yourself");
                        System.out.println("First and Middle names:");
                        name = scanner.nextLine();
                        System.out.println("Surname:");
                        surname = scanner.nextLine();
                        System.out.println("PIN:");
                        int aux2 = 0;
        
                        do {
                            try {
                                pin = scanner.nextInt();
                                aux2=1;
                            } catch(InputMismatchException e) {
                                System.out.println("Error in PIN, please reintroduce:");
                                scanner.nextLine();
                            }
                        } while(aux2 == 0);
                        
                        scanner.nextLine();
                        currentUser = MySQL.authenticate(name, surname, pin);
                        currentUser.setAccountList(MySQL.getUserAccounts(currentUser.getId()));
                        if (currentUser.getId()<0) System.out.println("Authentication error. Please try again");
                    } else {
                        currentUser=null;
                    }
                } catch (InputMismatchException e) {
                    aux=-1;
                }

                if (aux!=1 || aux!=0) {
                    System.out.println("Wrong input, please try again");
                }
                
            } while (aux!=1 || aux!=0);
        } while(currentUser.getId()<0);

        //From this point on user is authenticated
        System.out.println("Authentication successful.");

        if (currentUser.getAccountList().size()>0) {
            //User first has to select account on which to operate

            //Generating console prompt for selecting account
            String consoleString = "Please";
            for(int i=0;i<currentUser.getAccountList().size();i++) {
                consoleString+=", write "+(i+1)+" for account number "+currentUser.getAccountList().get(i).getId();
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
                System.out.println("Please write 1 if you wish to review your balance, 2 to add funds, 3 to withdraw");
                int op = scanner.nextInt();
                if (op == 1) {
                    System.out.println("Your balance is: "+accSel.getBalance());
                } else if(op == 2 || op == 3) {
                    System.out.println("Please introduce amount:");
                    accSel.changeBalance(scanner.nextDouble());
                    System.out.println("Your new balance is $"+accSel.getBalance());
                }          
            } else {
                //User wants to open new account
                //TODO: Open account system
            }
        } else {
            System.out.println("You do not have any accounts open. Write 1 to open one");
            //User has no account open
            //TODO: Open account system
        }
	}
}
