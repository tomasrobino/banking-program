package com.tomas.bankingprogram;

import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;

public class BankingProgram extends Application{
    @Override
    public void start(Stage primaryStage) {
        // TODO Auto-generated method stub
    }

	public static void main(String[] args) {
        launch(args);
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        //Start of console dialog
        System.out.println("Welcome!");
        System.out.println("If you are already registered, write 1, if you are not, write 0");
        //Checking answer, if not 1 or 0, tries again
        int aux;
        do {
            try {
                aux=scanner.nextInt();
                scanner.nextLine();
                if (aux==1) {
                    //Asking for credentials and authenticating them
                    currentUser = auth(credentials(scanner));
                    if(currentUser.getId()>-1) {
                        currentUser.setAccountList(MySQL.getUserAccounts(currentUser.getId()));
                    } else {
                        System.out.println("User does not exist");
                        aux=-1;
                    }
                } else if(aux == 0) {
                    boolean aux1 = true;
                    while(aux1) {
                        currentUser = register(scanner);
                        if (currentUser.getId() != -1) {
                            aux1=false;
                        } else {
                            System.out.println("There's already a user with that name, try again:");
                        }
                    }
                } else aux = -1;
            } catch (InputMismatchException e) {
                aux=-1;
                scanner.nextLine();
            }

            if (!(aux==1 || aux==0)) {
                System.out.println("Please try again.");
                System.out.println("If you already have an account, write 1, if you do not, write 0");
            }
            
        } while (aux == -1);

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

    private static String[] credentials(Scanner scanner) {
        String name=null;
        String surname=null;
        int pin=0;
        
        int aux = 0;

        System.out.println("First and Middle names:");
        do {
            try {
                name = scanner.nextLine();
                aux=1;
            } catch(InputMismatchException e) {
                System.out.println("Error in name, please reintroduce:");
            }
        } while(aux == 0);
        aux=0;

        System.out.println("Surname:");
        do {
            try {
                surname = scanner.nextLine();
                aux=1;
            } catch(InputMismatchException e) {
                System.out.println("Error in surname, please reintroduce:");
            }
        } while(aux == 0);
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

        return new String[]{name, surname, Integer.toString(pin)};
    }

    //Asking for and checking user credentials
    private static User auth(String[] arr) {
        //Checks credentials with SQL server
        User currentUser = MySQL.authenticate(
            arr[0], arr[1], Integer.parseInt(arr[2])
        );
        if (currentUser.getId()<0) {
            System.out.println("Authentication error");
            return new User(-1);
        } else {
            return currentUser;
        }
    }

    private static User register(Scanner scanner) {
        String[] arr = credentials(scanner); //Asks for credentials
        if (!MySQL.check(arr[0], arr[1])) {
            //Create user with given credentials
            return MySQL.newUser(arr[0], arr[1], Integer.parseInt(arr[2]));
        } else {
            //User already exists
            return new User(-1);
        }
    }
}
