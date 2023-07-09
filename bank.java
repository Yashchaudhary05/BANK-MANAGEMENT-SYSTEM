import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Bank {
    private Map<String, Double> accounts;
    private Map<String, String> passwords;

    public Bank() {
        accounts = new HashMap<>();
        passwords = new HashMap<>();
    }

    public void createAccount(String accountNumber, String password, double initialBalance) {
        accounts.put(accountNumber, initialBalance);
        passwords.put(accountNumber, password);
    }

    public double getBalance(String accountNumber) {
        return accounts.getOrDefault(accountNumber, 0.0);
    }

    public boolean authenticate(String accountNumber, String password) {
        return passwords.getOrDefault(accountNumber, "").equals(password);
    }

    public void deposit(String accountNumber, double amount) {
        double balance = accounts.getOrDefault(accountNumber, 0.0);
        accounts.put(accountNumber, balance + amount);
    }

    public void withdraw(String accountNumber, double amount) {
        double balance = accounts.getOrDefault(accountNumber, 0.0);
        if (balance >= amount) {
            accounts.put(accountNumber, balance - amount);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
    }
}

public class BankingSystem {
    private static Bank bank;
    private static String currentAccount;
    private static Scanner scanner;

    public static void main(String args[]) 
    {
        bank = new Bank();
        currentAccount = "";
        scanner = new Scanner(System.in);

        showMenu();
    }

    private static void showMenu() {
        while (true) {
            System.out.println("**** Banking System Menu ****");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("*************");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void register() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        bank.createAccount(accountNumber, password, initialBalance);
        System.out.println("Account registered successfully!");
    }

    private static void login() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (bank.authenticate(accountNumber, password)) {
            currentAccount = accountNumber;
            showAccountMenu();
        } else {
            System.out.println("Invalid account number or password. Please try again.");
        }
    }

    private static void showAccountMenu() {
        while (true) {
            System.out.println("**** Account Menu ****");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Logout");
            System.out.println("************");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    currentAccount = "";
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void checkBalance() {
        double balance = bank.getBalance(currentAccount);
        System.out.println("Current balance: " + balance);
    }

    private static void deposit() {
        System.out.print("Enter the deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        bank.deposit(currentAccount, amount);
        System.out.println("Amount deposited successfully!");
    }

    private static void withdraw() {
        System.out.print("Enter the withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        bank.withdraw(currentAccount, amount);
        System.out.println("Amount withdrawn successfully!");
    }

    private static void transfer() {
        System.out.print("Enter the recipient's account number: ");
        String recipientAccount = scanner.nextLine();
        System.out.print("Enter the transfer amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        bank.transfer(currentAccount, recipientAccount, amount);
        System.out.println("Amount transferred successfully!");
    }
}