package com.tomas.bankingprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class BankingProgram {
    private static String[] credentials() {
        String name=null;
        String surname=null;
        Integer pin=0;
        
        Scanner scanner = new Scanner(System.in);
        int aux = 0;

        System.out.println("First and Middle names:");
        do {
            try {
                name = scanner.nextLine();
                aux=1;
            } catch(InputMismatchException e) {
                System.out.println("Error in name, please reintroduce:");
                scanner.nextLine();
            }
        } while(aux == 0);
        scanner.nextLine();
        aux=0;

        System.out.println("Surname:");
        do {
            try {
                surname = scanner.nextLine();
                aux=1;
            } catch(InputMismatchException e) {
                System.out.println("Error in surname, please reintroduce:");
                scanner.nextLine();
            }
        } while(aux == 0);
        scanner.nextLine();
        aux=0;
        
        System.out.println("PIN:");
        do {
            try {
                pin = scanner.nextInt();
                aux=1;
            } catch(InputMismatchException e) {
                System.out.println("Error in PIN, please reintroduce:");
                scanner.nextLine();
            }
        } while(aux == 0);
        scanner.close();

        String[] arr = {name, surname, pin.toString()};
        return arr;
    }

    //Asking for and checking user credentials
    private static boolean auth(String[] arr) {
        //Checks credentials with SQL server
        User currentUser = MySQL.authenticate(
            arr[0], arr[1], Integer.parseInt(arr[2])
        );
        if (currentUser.getId()<0) {
            System.out.println("Authentication error");
            return false;
        } else {
            return true;
        }
    }

    private static User register() {
        String[] arr = credentials(); //Asks for credentials
        if (!MySQL.check(arr[0], arr[1])) {
            //Create user with given credentials
            return MySQL.newUser(arr[0], arr[1], Integer.parseInt(arr[2]));
        } else {
            //User already exists
            return new User(-1);
        }
    }

	public static void main(String[] args) {
		SpringApplication.run(BankingProgram.class, args);
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        //Start of console dialog
        System.out.println("Welcome!");
        int aux;
        System.out.println("If you already have an account, write 1, if you do not, write 0");
        //Checking answer, if not 1 or 0, tries again
        do {
            try {
                aux=scanner.nextInt();
                scanner.nextLine();
                if (aux==1) {
                    //Asking for credentials and authenticating them
                    if(auth(credentials())) {
                        currentUser.setAccountList(MySQL.getUserAccounts(currentUser.getId()));
                    } else {
                        System.out.println("User does not exist");
                        aux=-1;
                    }
                } else {
                    boolean aux1 = true;
                    while(aux1) {
                        currentUser = register();
                        if (currentUser.getId() != -1) {
                            aux1=false;
                        } else {
                            System.out.println("There's already a user with that name, try again:");
                        }
                    }
                }
            } catch (InputMismatchException e) {
                aux=-1;
            }

            if (aux!=1 || aux!=0) {
                System.out.println("Please try again.");
                System.out.println("If you already have an account, write 1, if you do not, write 0");
            }
            
        } while (currentUser.getId()<0);

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
                System.out.println("Please write 1 if you wish to review your balance, 2 to add funds, 3 to withdraw, 4 to transfer funds");
                int op = scanner.nextInt();
                if(op == 1) {
                    System.out.println("Your balance is: "+accSel.getBalance());
                } else if(op == 2 || op == 3) {
                    System.out.println("Please introduce amount:");
                    accSel.changeBalance(scanner.nextDouble());
                    System.out.println("Your new balance is $"+accSel.getBalance());
                } else if(op == 4) {
                    //TODO: Transfer funds system
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

        scanner.close();
	}
}
