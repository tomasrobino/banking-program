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
        System.out.println("Welcome!");
        MySQL sql;
        User currentUser;
        do {
            System.out.println("Please identify yourself, if you have yet to open an account, leave the field blank");
            System.out.println("First and Middle names:");
            name = scanner.nextLine();
            //Go to user registration
            if (name == null) {
                //TODO: user registration
                sql=null;
                
            //Go to user authentication
            } else {
                System.out.println("Surname:");
                surname = scanner.nextLine();
                System.out.println("PIN:");
                int aux = 0;

                do {
                    try {
                        pin = scanner.nextInt();
                        aux=1;
                    } catch(InputMismatchException e) {
                        System.out.println("Error in PIN, please reintroduce:");
                        scanner.nextLine();
                    }
                } while(aux == 0);
                sql = new MySQL(name, surname, pin);
            }
            scanner.nextLine();
            currentUser = sql.authenticate();
            if (currentUser.getId()<0) System.out.println("Authentication error. Please try again");
        } while(currentUser.getId()<0);

        //From this point on user is authenticated
        System.out.println("Authentication successful.");

        if (currentUser.getAccountList().size()>0) {
            //User first has to select account on which to operate
            //TODO: Account selector

            //Generating console prompt for selecting account
            String consoleString = "Please";
            for(int i=0;i<currentUser.getAccountList().size();i++) {
                consoleString+=", write "+(i+1)+" for account number "+currentUser.getAccountList().get(i).getId();
            }
            System.out.println(consoleString);
            System.out.println("If you wish to open another account, write -1");
            Account accSel = currentUser.getAccountList().get(scanner.nextInt()-1);
            scanner.nextLine();
            System.out.println("You have selected account #"+accSel.getId());

            //Choosing operation to do on selected account
            System.out.println("Please write 1 if you wish to review your balance, 2 to add funds, 3 to withdraw");
            
            int op = scanner.nextInt();
            double amnt;
            if (op == 1) {
                System.out.println("Your balance is: "+accSel.getBalance());
            } else if(op == 2 || op == 3) {
                System.out.println("Please introduce amount:");
                amnt = scanner.nextDouble();
                accSel.changeBalance(amnt);
                System.out.println("Your new balance is $"+accSel.getBalance());
            }
        } else {
            //User has no account open
            //TODO: Open account system
        }
	}
}
