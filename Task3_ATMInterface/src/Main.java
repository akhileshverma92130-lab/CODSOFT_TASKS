public class Main {
    public static void main(String[] args) {
        // Enable ANSI colors for Windows
        System.setProperty("jansi.passthrough", "true");

        ATM atm = new ATM();
        atm.start();
    }
}