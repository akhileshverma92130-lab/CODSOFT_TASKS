import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    private int score = 0;
    private int highScore = 0;
    private int roundsPlayed = 0;
    private int roundsWon = 0;

    private int minRange;
    private int maxRange;
    private int maxAttempts;

    public static void main(String[] args) {
        NumberGame game = new NumberGame();
        game.start();
    }

    public void start() {

        System.out.println("======================================");
        System.out.println("   🎮 WELCOME TO NUMBER GUESSING GAME 🎮");
        System.out.println("======================================");

        boolean playAgain = true;

        while (playAgain) {
            playRound();
            playAgain = askPlayAgain();
        }

        displayFinalStats();
        scanner.close();
    }

    // Difficulty Selection
    private void selectDifficulty() {

        System.out.println("\nChoose Difficulty:");
        System.out.println("1. Easy   (1-50, 10 attempts)");
        System.out.println("2. Medium (1-100, 7 attempts)");
        System.out.println("3. Hard   (1-500, 5 attempts)");

        while (true) {

            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                minRange = 1;
                maxRange = 50;
                maxAttempts = 10;
                System.out.println("✅ Easy Mode Selected!");
                break;

            } else if (choice.equals("2")) {
                minRange = 1;
                maxRange = 100;
                maxAttempts = 7;
                System.out.println("✅ Medium Mode Selected!");
                break;

            } else if (choice.equals("3")) {
                minRange = 1;
                maxRange = 500;
                maxAttempts = 5;
                System.out.println("🔥 Hard Mode Selected!");
                break;

            } else {
                System.out.println("⚠️ Invalid choice! Please select 1, 2 or 3.");
            }
        }
    }

    // Play One Round
    private void playRound() {

        roundsPlayed++;

        selectDifficulty();

        int targetNumber =
                random.nextInt(maxRange - minRange + 1) + minRange;

        int attempts = 0;
        boolean guessed = false;

        System.out.println("\n--- Round " + roundsPlayed + " ---");
        System.out.println("Guess the number between "
                + minRange + " and " + maxRange);

        while (attempts < maxAttempts && !guessed) {

            attempts++;

            System.out.print("Attempt " + attempts + "/"
                    + maxAttempts + ": Enter your guess: ");

            int guess = getValidInput();

            // Hint after 3rd attempt
            if (attempts == 3) {

                if (targetNumber % 2 == 0) {
                    System.out.println("💡 Hint: The number is EVEN.");
                } else {
                    System.out.println("💡 Hint: The number is ODD.");
                }
            }

            if (guess == targetNumber) {

                System.out.println("✅ Correct! You guessed it in "
                        + attempts + " attempt(s)!");

                guessed = true;
                roundsWon++;

                calculateScore(attempts);

            } else if (guess < targetNumber) {

                System.out.println("📈 Too LOW! Try a higher number.");

            } else {

                System.out.println("📉 Too HIGH! Try a lower number.");
            }

            int remaining = maxAttempts - attempts;

            if (!guessed && remaining > 0) {
                System.out.println("Attempts remaining: " + remaining);
            }
        }

        if (!guessed) {

            System.out.println("❌ Out of attempts!");
            System.out.println("The number was: " + targetNumber);
        }

        System.out.println("Current Score: " + score);
        System.out.println("High Score: " + highScore);
    }

    // Input Validation
    private int getValidInput() {

        while (true) {

            try {

                String input = scanner.nextLine().trim();
                int guess = Integer.parseInt(input);

                if (guess < minRange || guess > maxRange) {

                    System.out.print("⚠️ Enter a number between "
                            + minRange + " and " + maxRange + ": ");

                    continue;
                }

                return guess;

            } catch (NumberFormatException e) {

                System.out.print(
                        "⚠️ Invalid input! Enter a valid number: ");
            }
        }
    }

    // Score Calculation
    private void calculateScore(int attempts) {

        // Fewer attempts = higher score
        int roundScore = (maxAttempts - attempts + 1) * 10;

        score += roundScore;

        System.out.println("🏆 Round Score: "
                + roundScore + " | Total Score: " + score);

        // High Score
        if (score > highScore) {

            highScore = score;

            System.out.println("🎉 NEW HIGH SCORE: " + highScore + "!");
        }
    }

    // Play Again
    private boolean askPlayAgain() {

        while (true) {

            System.out.print("\n🔄 Play another round? (y/n): ");

            String input = scanner.nextLine()
                    .trim()
                    .toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            }

            if (input.equals("n") || input.equals("no")) {
                return false;
            }

            System.out.println("⚠️ Please enter 'y' or 'n'");
        }
    }

    // Final Statistics
    private void displayFinalStats() {

        System.out.println("\n======================================");
        System.out.println("           🎯 GAME OVER 🎯");
        System.out.println("======================================");

        System.out.println("Rounds Played: " + roundsPlayed);
        System.out.println("Rounds Won:    " + roundsWon);
        System.out.println("Rounds Lost:   "
                + (roundsPlayed - roundsWon));

        System.out.println("Final Score:   " + score);
        System.out.println("High Score:    " + highScore);

        System.out.println("Win Rate:      "
                + (roundsPlayed > 0
                ? String.format("%.1f%%",
                (roundsWon * 100.0 / roundsPlayed))
                : "0%"));

        System.out.println("======================================");
        System.out.println("Thanks for playing! 👋");
    }
}