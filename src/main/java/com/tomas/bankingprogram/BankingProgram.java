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
        MySQL authenticator;
        int userId;
        do {
            System.out.println("Please identify yourself, if you have yet to open an account, leave the field blank");
            System.out.println("First and Middle names:");
            name = scanner.nextLine();
            //Go to user registration
            if (name == null) {
                //TODO: user registration
                authenticator=null;
                
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
                authenticator = new MySQL(name, surname, pin);
            }
            scanner.nextLine();
            userId = authenticator.authenticate();
        } while(userId<0);

        //From this point on user is authenticated
        System.out.println("Authentication successful.");
	}
}
