import java.util.Scanner;

public class SmartBankingApp{
    
    private static final String DASHBOARD = "Welcome to Smart Banking App";
    private static final String OPEN_ACCOUNT = "Open New Account";
    private static final String DEPOSIT = "Deposit";
    private static final String WITHDRAW = "Withdraw";
    private static final String TRANSFER = "Transfer";
    private static final String CHECK_BALANCE = "Check Account Balance";
    private static final String DELETE_ACCOUNT = "Delete Account";

    private static String[][] accountInfo = new String[0][0];

    public static void main(String[] args) {
        runSmartBankingApp();
    }

    //Implement the core logic of the Smart Banking App, including user interactions, menu navigation, and handling various banking operations.
    private static void runSmartBankingApp() {
        
        String screen = DASHBOARD;
        Scanner scanner = new Scanner(System.in);

        int accountCount = 0; // Counter for generating account number
        
        do {
            clearScreen();
            printHeader(screen);

            switch (screen) {
                case DASHBOARD:
                    int option = getMenuChoice(scanner);
                    switch (option) {
                        case 1:
                            screen = OPEN_ACCOUNT;
                            break;
                        case 2:
                            screen = DEPOSIT;
                            break;
                        case 3:
                            screen = WITHDRAW;
                            break;

                        case 4:
                            screen = TRANSFER;
                            break;
                        case 5:
                            screen = CHECK_BALANCE;
                            break;

                        case 6:
                            screen = DELETE_ACCOUNT;
                            break;

                        case 7:
                            scanner.close();
                            clearScreen();
                            System.out.println("Exiting the Smart Banking App...");
                            System.exit(0);
                            break;
                        default:
                            continue;
                    }
                    break;

                case OPEN_ACCOUNT:
                    openAccountProcess(scanner,  accountCount);
                    screen = DASHBOARD;
                    
                    break;

                case DEPOSIT:
                    depositProcess(scanner);
                    screen = DASHBOARD;
                    break;

                case WITHDRAW:
                    withdrawProcess(scanner);
                    screen = DASHBOARD;
                    break;

                case TRANSFER:
                    transferProcess(scanner);
                    screen = DASHBOARD;
                    break;

                case CHECK_BALANCE:
                    //checkAccountBalance(scanner);
                    screen = DASHBOARD;
                    break;

                case DELETE_ACCOUNT:
                    //deleteAccount(scanner);
                    screen = DASHBOARD;
                    break;
                
                default:
                    scanner.close();
                    System.exit(0);
            }

        } while (true);


    }
    // Clear the terminal
    private static void clearScreen() {
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }

    // Format and print the provided title as a header
    private static void printHeader(String title) {
        String COLOR_BLUE_BOLD = "\033[34;1m";
        String RESET = "\033[0m";

        String header = "-".repeat(50) + "\n" +
                " ".repeat((40 - title.length() + 7) / 2) + COLOR_BLUE_BOLD + title + RESET + "\n" +
                "-".repeat(50);
        System.out.println(header);
    }

    // Print the provided error message to inform the user of an issue.
    private static void printErrorMsg(String message) {
        String COLOR_RED_BOLD = "\033[31;1m";
        String RESET = "\033[0m";
        System.out.println(COLOR_RED_BOLD + message + RESET + "\n");
    }

    // Print the provided success messages to inform the user.
    private static void printSuccessMsg(String message) {
        String COLOR_GREEN_BOLD = "\033[32;1m";
        String RESET = "\033[0m";
        System.out.println(COLOR_GREEN_BOLD + message + RESET + "\n");
    }


    //Retrieves and returns the user's choice from a menu.
    private static int getMenuChoice(Scanner scanner) {
        System.out.println("[1]. Open New Account");
        System.out.println("[2]. Deposit");
        System.out.println("[3]. Withdraw");
        System.out.println("[4]. Transfer");
        System.out.println("[5]. Check Account Balance");
        System.out.println("[6]. Delete Account");
        System.out.println("[7]. Exit");
        System.out.print("Enter an option to continue > ");
        return scanner.nextInt();
    }


    //Generates a new account number based on the current account count.
    private static String generateAccountNumber(int accountCount) {
        return String.format("SDB-S%05d", accountCount + 1);
    }

    //Prompts the user to enter and validates a name.
    private static String getValidName(Scanner scanner) {
        boolean valid;
        String name;
    

        do {
            valid = true;
            System.out.print("Enter Account Holder Name: ");
            name = scanner.nextLine().strip();
            

            if (name.isBlank()) {
                printErrorMsg("Name can't be empty");
                valid = false;
                continue;
            }

            for (int i = 0; i < name.length(); i++) {
                if (!(Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i)))) {
                    printErrorMsg("Invalid Name");
                    valid = false;
                    break;
                }
            }
        } while (!valid);

        return name;
    }


    //Prompts the user to enter and validates an initial deposit amount.
    private static double getValidInitialDeposit(Scanner scanner) {
        boolean valid;
        double initialDeposit = 0.0;

        do {
            valid = true;
            System.out.print("Enter Initial Deposit (minimum 5000): ");
            try {
                initialDeposit = Double.parseDouble(scanner.nextLine());
                if (initialDeposit < 5000) {
                    printErrorMsg("Initial deposit must be at least 5000");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                printErrorMsg("Invalid input. Please enter a valid amount");
                valid = false;
            }
        } while (!valid);

        return initialDeposit;
    }


    //This method facilitates the process of opening a new account by gathering and storing account information.
    private static void openAccountProcess(Scanner scanner, int accountCount) {

        scanner.nextLine();
        do {

            String newAccountNumber = generateAccountNumber(accountCount);
            System.out.println("New Account Number: " + newAccountNumber);
            accountCount++;
            
            String name = getValidName(scanner);
            double initialDeposit = getValidInitialDeposit(scanner);

            String[][] newAccountInfo = new String[accountInfo.length + 1][3];

            // Copy existing accountInfo to newAccountInfo
            for (int i = 0; i < accountInfo.length; i++) {
                newAccountInfo[i][0] = accountInfo[i][0];
                newAccountInfo[i][1] = accountInfo[i][1];
                newAccountInfo[i][2] = accountInfo[i][2];
            }
            // Add new account information to the last row
            newAccountInfo[accountInfo.length][0] = name;
            newAccountInfo[accountInfo.length][1] = newAccountNumber;
            newAccountInfo[accountInfo.length][2] = String.valueOf(initialDeposit);

            // Update accountInfo to the new array
            accountInfo = newAccountInfo;

            printSuccessMsg( newAccountNumber + " Account number " + "for " + name + " added successfully.");
            System.out.print("Do you want to open another account (Y/n)? ");
            if (!scanner.nextLine().toUpperCase().strip().equals("Y")) {
                break;
            }else{
                clearScreen();
                printHeader(OPEN_ACCOUNT);
                            
            }
        } while (true);
    }


    // Prompts the user to enter and validates an account number, ensuring its uniqueness.
    private static String getValidAccountNumber(Scanner scanner) {
        String accountNumber;

        while (true) {
            System.out.print("Enter Account Number: ");
            accountNumber = scanner.nextLine().strip();

            // Validate the account number format "S-XXXXX"
            if (!accountNumber.startsWith("SDB-S") || accountNumber.length() != 10) {
                printErrorMsg("Invalid account number format. Please use SDB-SXXXXX format.");
                continue;
            }

            boolean accountFound = false;
            for (String[] account : accountInfo) {
                if (account[1].equals(accountNumber)) {
                    accountFound = true;
                    break;
                }
            }

            if (!accountFound) {
                printErrorMsg("Account not found");
            } else {
                break;
            }
        }
        return accountNumber;
    }

    //Prompts the user to enter and validates a deposit amount.    
    private static double getDepositAmount(Scanner scanner) {
        double depositAmount;
    
        while (true) {
            System.out.print("Enter Deposit Amount (minimum 500): ");
            try {
                depositAmount = Double.parseDouble(scanner.nextLine());
                if (depositAmount < 500) {
                    printErrorMsg("Deposit amount must be at least 500");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                printErrorMsg("Invalid input. Please enter a valid amount");
            }
        }
    
        return depositAmount;
    }

    private static String getNameForAccountNumber(String accountNumber) {
        for (String[] account : accountInfo) {
            if (account[1].equals(accountNumber)) {
                return account[0]; // Name is stored in the first column
            }
        }
        return null; // Account not found
    }

     //Retrieves the current balance of a specific account.
    private static double getAccountBalance(String accountNumber) {
        for (String[] account : accountInfo) {
            if (account[1].equals(accountNumber)) {
                return Double.parseDouble(account[2]);
            }
        }
        return 0.0; // Account not found (should handle this appropriately in your context)
    }


    //Initiates the process for depositing funds into an account.
    private static void depositProcess(Scanner scanner) {
        scanner.nextLine();
        do{
            String accountNumber = getValidAccountNumber(scanner);

            System.out.println("Account Holder Name: " + getNameForAccountNumber(accountNumber));

            double currentBalance = getAccountBalance(accountNumber);
            System.out.println("Current Balance: LKR " + currentBalance + "\n");
        
            double depositAmount = getDepositAmount(scanner);
        
            double newBalance = currentBalance + depositAmount;
            updateAccountBalance(accountNumber, newBalance);

            printSuccessMsg("Deposit Successful!\nNew Account Balance: LKR" + newBalance + "\n");
        
            System.out.println("Do you want to make another deposit (Y/n)? ");
            if (!scanner.nextLine().toUpperCase().strip().equals("Y")) {
                    break;
                }else{
                    clearScreen();
                    printHeader(DEPOSIT);
                                
            }

        }while(true);

        
    }

    private static void updateAccountBalance(String accountNumber, double newBalance) {
        for (String[] account : accountInfo) {
            if (account[1].equals(accountNumber)) {
                account[2] = String.valueOf(newBalance);
                break;
            }
        }
    }

    //Prompts the user to enter and validates a withdrawal amount, considering the current account balance.
    private static double getWithdrawAmount(Scanner scanner, double currentBalance) {
        double withdrawAmount;
    
        while (true) {
            System.out.print("Enter Withdraw Amount (minimum 100): ");
            try {
                withdrawAmount = Double.parseDouble(scanner.nextLine());
                if (withdrawAmount < 100) {
                    printErrorMsg("Withdraw amount must be at least 100");
                } else if (currentBalance - withdrawAmount < 500) {
                    printErrorMsg("Insufficient funds");
                    withdrawAmount = -1.0; // Signal for insufficient funds
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                printErrorMsg("Invalid input. Please enter a valid amount");
            }
        }
        return withdrawAmount;
    }



    //Initiates the process for withdrawing funds from an account.
    private static void withdrawProcess(Scanner scanner) {
        scanner.nextLine();
        do{
            String accountNumber = getValidAccountNumber(scanner);
            System.out.println("Account Holder Name :"+ getNameForAccountNumber(accountNumber));

            double currentBalance = getAccountBalance(accountNumber);
            System.out.println("Current Balance :" + currentBalance);
            System.out.println("Available Balance for Withdraw: LKR " + (currentBalance - 500) + "\n");
            //Ask for a withdrawal. If the user's input is 'N' then go to the DASHBOARD.
            System.out.println("Do you want to make a withdrawal (Y/n)? ");
            if (!scanner.nextLine().toUpperCase().strip().equals("Y")) {
                break;
            }

            double withdrawAmount = getWithdrawAmount(scanner, currentBalance);
            if (withdrawAmount == -1.0) {
                return; // Insufficient funds
            }
        
            double newBalance = currentBalance - withdrawAmount;

            updateAccountBalance(accountNumber, newBalance);

            printSuccessMsg("Withdrawal Successful!\nNew Balance: LKR " + newBalance + "\n");
        
            System.out.println("Do you want to make another withdrawal (Y/n)? ");
            if (!scanner.nextLine().toUpperCase().strip().equals("Y")) {
                break;
            }else{
                clearScreen();
                printHeader(WITHDRAW);                   
            }

        }while(true);
        
        
    }
    

    //Prompts the user to enter and validates a transfer amount, considering the balance of the source account.
    private static double getTransferAmount(Scanner scanner, double fromAccountBalance) {
        double transferAmount;
    
        while (true) {
            System.out.print("Enter Transfer Amount (minimum 100): ");
            try {
                transferAmount = Double.parseDouble(scanner.nextLine());
                if (transferAmount < 100) {
                    printErrorMsg("Transfer amount must be at least 100");
                } else if (fromAccountBalance - transferAmount < 500) {
                    printErrorMsg("Insufficient funds");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                printErrorMsg("Invalid input. Please enter a valid amount");
            }
        }
    
        return transferAmount;
    }


    //Initiates the process for transferring funds between accounts.
    private static void transferProcess(Scanner scanner) {
        scanner.nextLine();
        do{
            System.out.println("From Account:");
            String fromAccountNumber = getValidAccountNumber(scanner);
            System.out.println("From Account Holder Name :" + getNameForAccountNumber(fromAccountNumber));
            double fromAccountBalance = getAccountBalance(fromAccountNumber);
            System.out.println("From Account Number: " + fromAccountNumber);
            System.out.println("Current Balance: LKR " + fromAccountBalance + "\n");

            System.out.println("To Account:");
            String toAccountNumber = getValidAccountNumber(scanner);
            System.out.println("To Account Holder Name :" + getNameForAccountNumber(toAccountNumber));
            double toAccountBalance = getAccountBalance(toAccountNumber);
            System.out.println("To Account Number: " + toAccountNumber);
            System.out.println("Current Balance: LKR " + toAccountBalance + "\n");
        
            double transferAmount = getTransferAmount(scanner, fromAccountBalance);
        
            if (transferAmount == -1.0) {
                return; // Insufficient funds
            }
        
            fromAccountBalance -= transferAmount;
            updateAccountBalance(fromAccountNumber, fromAccountBalance);
            toAccountBalance += transferAmount;
            updateAccountBalance(toAccountNumber, toAccountBalance);
        
            printSuccessMsg("Transfer successful!");
            System.out.println("New Balance of " + fromAccountNumber + " (From Account): LKR " + fromAccountBalance );
            System.out.println("New Balance of "+ toAccountNumber+ " (To Account): LKR " + toAccountBalance + "\n");
        
            System.out.println("Do you want to make another transfer (Y/n)? ");
            if (!scanner.nextLine().toUpperCase().strip().equals("Y")) {
                        break;
                    }else{
                        clearScreen();
                        printHeader(TRANSFER);                 
                }

        }while(true);
    }
        
    

    
    
}






