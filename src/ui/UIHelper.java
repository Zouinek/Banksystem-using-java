package ui;

import java.util.Scanner;

/**
 * The UIHelper class provides utility methods for UI.
 * It includes methods for clearing the screen, getting user input, and
 * displaying messages.
 */
public class UIHelper {

  /**
   * A shared Scanner instance for reading user input from the console.
   */
  private static final Scanner scanner = new Scanner(System.in);

  /**
   * Clears the console screen.
   */
  public static void clearScreen() {
    // ANSI escape code to clear screen
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /**
   * Prompts the user to enter a choice and returns the input as an integer.
   * Continues to prompt the user until a valid integer is entered.
   *
   * @return the user's choice as an integer.
   */
  public static int getUserChoice() {
    while (true) {
      System.out.print("\u001B[34m> Enter your choice: \u001B[0m");
      String input = scanner.nextLine().trim();
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Enter a number.");
      }
    }
  }

  /**
   * Prompts the user to enter an amount for a specified operation and returns the
   * input as a double.
   * Continues to prompt the user until a valid double is entered.
   *
   * @param operation the operation for which the amount is being entered (e.g.,
   *                  "deposit" or "withdraw").
   * @return the amount entered by the user as a double.
   */
  public static double promptForAmount(String operation) {
    System.out.print("\u001B[34m> Enter the amount to " + operation + ": \u001B[0m");
    while (!scanner.hasNextDouble()) {
      scanner.next();
      System.out.print("Invalid input. Enter a valid amount to " + operation + ": ");
    }
    double amount = scanner.nextDouble();
    scanner.nextLine();
    return amount;
  }

  /**
   * Returns the shared Scanner instance used for reading user input from the
   * console.
   *
   * @return the shared Scanner instance.
   */
  public static Scanner getScanner() {
    return scanner;
  }

  /**
   * Displays a welcome message to the user
   */
  public static void displayWelcomeMessage() {
    System.out.println("\n Welcome to the");

  }

  /**
   * Displays a goodbye message to the user.
   */
  public static void displayGoodbyeMessage() {
    System.out.println("Exiting Banking System...");
  }
}
