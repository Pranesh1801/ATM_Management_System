import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

class BankAccount {
    private String accountNumber;
    private double balance;
    private String password;
    private String accountHolderName;
    private String bankName;
    private LocalDateTime creationDate;

    public BankAccount(String accountNumber, String accountHolderName, String password, String bankName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = 0.0;
        this.password = password;
        this.bankName = bankName; 
        this.creationDate = LocalDateTime.now();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getBankName() {
        return bankName;
    }

    public boolean authenticate(String inputPassword) {
        return password.equals(inputPassword);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited ₹" + amount + " into " + accountHolderName + "'s account successfully.");
            System.out.println("New balance: ₹" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn ₹" + amount + " from " + accountHolderName + "'s account successfully.");
            System.out.println("New balance: ₹" + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}

class Bank {
    private String bankName;
    private Map<String, BankAccount> accounts;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new HashMap<>();
    }

    public String getBankName() {
        return bankName;
    }

    public void createAccount(String accountNumber, String accountHolderName, String password) {
        accounts.put(accountNumber, new BankAccount(accountNumber, accountHolderName, password, bankName));
        System.out.println("Account created successfully in " + bankName + ".");
    }

    public void displayAccountDetails() {
        System.out.println("\nAccounts in " + bankName + ":");
        for (BankAccount account : getAccounts().values()) {
            System.out.println("Account Holder's Name: " + account.getAccountHolderName());
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Balance: ₹" + account.getBalance());
            System.out.println();
        }
    }

    public Map<String, BankAccount> getAccounts() {
        return accounts;
    }
}

public class BankingApp {
    private static Map<String, Bank> banks = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nBanking Management Application");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Bank");
            System.out.println("4. Exit");
            System.out.print("Select an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    loginAsAdmin();
                    break;
                case 2:
                    loginAsUser();
                    break;
                case 3:
                    loginAsBank();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting the application. Thank you!");
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }

        scanner.close();
    }

    private static void loginAsAdmin() {
        System.out.println("\nAdmin Dashboard");
        System.out.println("1. View All Accounts");
        System.out.print("Select an option (1): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewAllAccounts();
                break;
            default:
                System.out.println("Invalid option. Please select again.");
        }
    }

    private static void viewAllAccounts() {
        System.out.println("\nAll Accounts:");
        for (Bank bank : banks.values()) {
            bank.displayAccountDetails();
        }
    }

    private static void loginAsUser() {
        System.out.println("\nUser Dashboard");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Amount");
        System.out.println("3. Withdraw Amount");
        System.out.println("4. View Balance");
        System.out.println("5. Change Password");
        System.out.println("6. Transfer Amount");
        System.out.println("7. Back to Main Menu");
        System.out.print("Select an option (1-7): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                createNewAccount();
                break;
            case 2:
                depositAmount();
                break;
            case 3:
                withdrawAmount();
                break;
            case 4:
                viewBalance();
                break;
            case 5:
                changePassword();
                break;
            case 6:
                transferAmount();
                break;
            case 7:
                break;
            default:
                System.out.println("Invalid option. Please select again.");
        }
    }

    private static void createNewAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter account holder's name: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();
        displayRegisteredBanks(); 
        System.out.print("Select bank: ");
        String bankName = scanner.nextLine();

        if (banks.containsKey(bankName)) {
            banks.get(bankName).createAccount(accountNumber, accountHolderName, password);
        } else {
            System.out.println("Bank not registered yet.");
        }
    }

private static void depositAmount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        if (findAccount(accountNumber)) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            BankAccount account = findAccountAndGet(accountNumber);
            if (account.authenticate(password)) {
                System.out.print("Enter deposit amount: ₹");
                double depositAmount = scanner.nextDouble();
                account.deposit(depositAmount);
            } else {
                System.out.println("Authentication failed... Invalid password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawAmount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        if (findAccount(accountNumber)) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            BankAccount account = findAccountAndGet(accountNumber);
            if (account.authenticate(password)) {
                System.out.print("Enter withdrawal amount: ₹");
                double withdrawalAmount = scanner.nextDouble();
                account.withdraw(withdrawalAmount);
            } else {
                System.out.println("Authentication failed. Invalid password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        if (findAccount(accountNumber)) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            BankAccount account = findAccountAndGet(accountNumber);
            if (account.authenticate(password)) {
                double balance = account.getBalance();
                System.out.println("Account balance: ₹" + balance);
            } else {
                System.out.println("Authentication failed. Invalid password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void changePassword() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        if (findAccount(accountNumber)) {
            System.out.print("Enter current password: ");
            String currentPassword = scanner.nextLine();
            BankAccount account = findAccountAndGet(accountNumber);
            if (account.authenticate(currentPassword)) {
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                System.out.print("Confirm new password: ");
                String confirmNewPassword = scanner.nextLine();
                if (newPassword.equals(confirmNewPassword)) {
                    account.setPassword(newPassword);
                    System.out.println("Password changed successfully.");
                } else {
                    System.out.println("New password and confirm password do not match.");
                }
            } else {
                System.out.println("Authentication failed. Invalid password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transferAmount() {
        try {
            System.out.print("Enter sender account number: ");
            String senderAccountNumber = scanner.nextLine();
            if (!findAccount(senderAccountNumber)) {
                System.out.println("Sender account not found.");
                return;
            }

            System.out.print("Enter sender password: ");
            String senderPassword = scanner.nextLine();
            BankAccount senderAccount = findAccountAndGet(senderAccountNumber);
            if (!senderAccount.authenticate(senderPassword)) {
                System.out.println("Authentication failed for sender. Invalid password.");
                return;
            }

            System.out.print("Enter receiver account number: ");
            String receiverAccountNumber = scanner.nextLine();
            if (!findAccount(receiverAccountNumber)) {
                System.out.println("Receiver account not found.");
                return;
            }

            BankAccount receiverAccount = findAccountAndGet(receiverAccountNumber);

            System.out.print("Enter amount to transfer: ₹");
            double transferAmount = scanner.nextDouble();
            scanner.nextLine();

            if (transferAmount <= 0 || transferAmount > senderAccount.getBalance()) {
                System.out.println("Invalid transfer amount or insufficient balance.");
                return;
            }

            senderAccount.withdraw(transferAmount);
            receiverAccount.deposit(transferAmount);
            System.out.println("Amount transferred successfully from account " + senderAccountNumber + " to account " + receiverAccountNumber);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean findAccount(String accountNumber) {
        for (Bank bank : banks.values()) {
            for (BankAccount account : bank.getAccounts().values()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return true;
                }
            }
        }
        return false;
    }
        private static BankAccount findAccountAndGet(String accountNumber) {
        for (Bank bank : banks.values()) {
            for (BankAccount account : bank.getAccounts().values()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return account;
                }
            }
        }
        return null;
    }

    private static void loginAsBank() {
        System.out.println("\nBank Dashboard");
        System.out.println("1. Register Bank");
        System.out.println("2. View Accounts for Bank");
        System.out.print("Select an option (1-2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                registerBank();
                break;
            case 2:
                viewAccountsForBank();
                break;
            default:
                System.out.println("Invalid option. Please select again.");
        }
    }

    private static void registerBank() {
        System.out.print("Enter Bank Name: ");
        String bankName = scanner.nextLine();
        banks.put(bankName, new Bank(bankName));
        System.out.println("Bank registered successfully.");
    }

    private static void viewAccountsForBank() {
        System.out.print("Enter Bank Name: ");
        String bankName = scanner.nextLine();
        if (banks.containsKey(bankName)) {
            banks.get(bankName).displayAccountDetails();
        } else {
            System.out.println("Bank not found.");
        }
    }

    private static void displayRegisteredBanks() {
        System.out.println("Registered Banks:");
        for (String bankName : banks.keySet()) {
            System.out.println("- " + bankName);
        }
    }
}
