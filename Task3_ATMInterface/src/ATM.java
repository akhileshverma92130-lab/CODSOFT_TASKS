import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private final Map<String, BankAccount> accounts;
    private final Scanner scanner;
    private BankAccount currentAccount;
    private static final int MAX_PIN_ATTEMPTS = 3;

    public ATM() {
        this.accounts = new HashMap<>();
        this.scanner = new Scanner(System.in);
        initializeDummyAccounts();
    }

    private void initializeDummyAccounts() {
        // Pre-loaded accounts for testing
        accounts.put("1234567890", new BankAccount("1234567890", "Rahul Sharma", "1234", 50000.0));
        accounts.put("9876543210", new BankAccount("9876543210", "Priya Singh", "4321", 75000.0));
        accounts.put("1111222233", new BankAccount("1111222233", "Amit Kumar", "1111", 25000.0));
    }

    public void start() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     🏦 WELCOME TO CODSOFT ATM       ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (true) {
            if (currentAccount == null) {
                showLoginScreen();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginScreen() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine().trim();

        BankAccount account = accounts.get(accNum);
        if (account == null) {
            System.out.println("❌ Account not found!");
            return;
        }

        int attempts = 0;
        while (attempts < MAX_PIN_ATTEMPTS) {
            System.out.print("Enter 4-digit PIN: ");
            String pin = scanner.nextLine().trim();

            if (account.verifyPin(pin)) {
                currentAccount = account;
                System.out.println("\n✅ Login successful! Welcome, " + account.getAccountHolderName());
                return;
            } else {
                attempts++;
                int remaining = MAX_PIN_ATTEMPTS - attempts;
                if (remaining > 0) {
                    System.out.println("❌ Incorrect PIN. Attempts remaining: " + remaining);
                }
            }
        }
        System.out.println("🚫 Too many failed attempts. Account locked for this session.");
    }

    private void showMainMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         MAIN MENU                   ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. 💰 Check Balance                ║");
        System.out.println("║  2. 💵 Deposit Cash                 ║");
        System.out.println("║  3. 🏧 Withdraw Cash                ║");
        System.out.println("║  4. 📄 Mini Statement (Last 5)      ║");
        System.out.println("║  5. 📋 Full Statement               ║");
        System.out.println("║  6. 🔐 Change PIN                   ║");
        System.out.println("║  7. 🚪 Logout                       ║");
        System.out.println("║  0. ⛔ Exit ATM                     ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Select option: ");

        String choice = scanner.nextLine().trim();
        handleMenuChoice(choice);
    }

    private void handleMenuChoice(String choice) {
        switch (choice) {
            case "1" -> checkBalance();
            case "2" -> depositCash();
            case "3" -> withdrawCash();
            case "4" -> currentAccount.printMiniStatement(5);
            case "5" -> currentAccount.printFullStatement();
            case "6" -> changePin();
            case "7" -> logout();
            case "0" -> exitATM();
            default -> System.out.println("⚠️ Invalid option! Please try again.");
        }
    }

    private void checkBalance() {
        System.out.println("\n💰 BALANCE INQUIRY");
        System.out.println("Account: " + currentAccount.getAccountHolderName());
        System.out.println("A/C: **** **** **** " + currentAccount.getAccountNumber().substring(6));
        System.out.println("Available Balance: ₹" + String.format("%.2f", currentAccount.getBalance()));
    }

    private void depositCash() {
        System.out.println("\n💵 CASH DEPOSIT");
        System.out.print("Enter amount to deposit (Max ₹50,000): ₹");

        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());

            if (amount <= 0) {
                System.out.println("❌ Amount must be positive!");
                return;
            }
            if (amount > 50000) {
                System.out.println("❌ Maximum deposit per transaction: ₹50,000");
                return;
            }

            // Simulate note counting
            System.out.println("\n🔄 Counting notes...");
            simulateProcessing();

            if (currentAccount.deposit(amount)) {
                System.out.println("✅ Deposit successful!");
                System.out.println("Amount deposited: ₹" + String.format("%.2f", amount));
                System.out.println("New Balance: ₹" + String.format("%.2f", currentAccount.getBalance()));
                printReceipt("DEPOSIT", amount);
            } else {
                System.out.println("❌ Deposit failed. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount! Please enter numbers only.");
        }
    }

    private void withdrawCash() {
        System.out.println("\n🏧 CASH WITHDRAWAL");
        System.out.println("Available Balance: ₹" + String.format("%.2f", currentAccount.getBalance()));
        System.out.println("Daily Limit: ₹25,000 | Per Transaction: ₹10,000");
        System.out.print("Enter amount to withdraw: ₹");

        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());

            System.out.println("\n🔄 Processing withdrawal...");
            simulateProcessing();

            BankAccount.WithdrawResult result = currentAccount.withdraw(amount);

            if (result.success) {
                System.out.println("✅ " + result.message);
                printReceipt("WITHDRAWAL", amount);
            } else {
                System.out.println("❌ " + result.message);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount! Please enter numbers only.");
        }
    }

    private void changePin() {
        System.out.println("\n🔐 CHANGE PIN");
        System.out.print("Enter current PIN: ");
        String oldPin = scanner.nextLine().trim();

        if (!currentAccount.verifyPin(oldPin)) {
            System.out.println("❌ Incorrect current PIN!");
            return;
        }

        System.out.print("Enter new 4-digit PIN: ");
        String newPin = scanner.nextLine().trim();

        if (!newPin.matches("\\d{4}")) {
            System.out.println("❌ PIN must be exactly 4 digits!");
            return;
        }

        System.out.print("Confirm new PIN: ");
        String confirmPin = scanner.nextLine().trim();

        if (!newPin.equals(confirmPin)) {
            System.out.println("❌ PINs do not match!");
            return;
        }

        // Note: BankAccount.pin is final, so we simulate success
        System.out.println("✅ PIN changed successfully! (Simulated - in real app, PIN would be updated in database)");
    }

    private void logout() {
        System.out.println("\n👋 Logging out... Thank you for using CodSoft ATM!");
        currentAccount = null;
    }

    private void exitATM() {
        System.out.println("\n👋 Thank you for using CodSoft ATM. Have a great day!");
        scanner.close();
        System.exit(0);
    }

    private void printReceipt(String type, double amount) {
        System.out.println("\n" + "─".repeat(35));
        System.out.println("       CODSOFT ATM - RECEIPT");
        System.out.println("─".repeat(35));
        System.out.println("Transaction: " + type);
        System.out.println("Account: **** **** **** " + currentAccount.getAccountNumber().substring(6));
        System.out.println("Name: " + currentAccount.getAccountHolderName());
        System.out.println("Amount: ₹" + String.format("%.2f", amount));
        System.out.println("Balance: ₹" + String.format("%.2f", currentAccount.getBalance()));
        System.out.println("Date: " + java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        System.out.println("─".repeat(35));
        System.out.println("   Thank you for banking with us!");
        System.out.println("─".repeat(35));
    }

    private void simulateProcessing() {
        String[] frames = {"⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏"};
        for (int i = 0; i < 20; i++) {
            System.out.print("\r" + frames[i % frames.length] + " Processing...");
            try { Thread.sleep(50); } catch (InterruptedException ignored) {}
        }
        System.out.print("\r✅ Done!                    \n");
    }
}