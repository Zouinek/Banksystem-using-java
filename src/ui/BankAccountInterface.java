package ui;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import models.BankAccount;
import services.LoginService;
import utils.CsvFileManager;

/**
 * The BankAccountInterface class handles user interactions for
 * logging in and managing bank accounts. It provides functionality
 * to check balances, deposit and withdraw money, and transfer funds.
 */
public class BankAccountInterface {

  /** Scanner instance for reading user input. */
  private final Scanner scanner;

  /** Service for handling login operations. */
  private final LoginService loginService;

  /** Interface for handling transactions. */
  private TransactionInterface transactionInterface;

  /**
   * Constructs a BankAccountInterface with the given CSV file manager.
   *
   * @param csvFileManager the CSV file manager to be used for account data
   *                       operations.
   */
  public BankAccountInterface(CsvFileManager csvFileManager) {
    this.scanner = UIHelper.getScanner();
    this.loginService = new LoginService(csvFileManager);
  }

  /**
   * Prompts the user to enter their username and password to attempt login.
   * If login is successful, initializes the user's session.
   */
  public void tryLogin() {
    System.out.print("> Enter your username: ");
    String username = scanner.nextLine().trim();
    System.out.print("> Enter your password: ");
    String password = scanner.nextLine().trim();
    UIHelper.clearScreen();
    Optional<List<String>> accountData = loginService.login(username, password);
    accountData.ifPresentOrElse(
        this::initializeSession,
        () -> System.out.println("Invalid username or password."));
  }

  /**
   * Initializes a session for the user by retrieving their account and
   * displaying the main menu.
   *
   * @param currentUserAccountData the data of the logged-in user.
   */
  private void initializeSession(List<String> currentUserAccountData) {
    double balance = Double.parseDouble(currentUserAccountData.get(7));
    BankAccount account = new BankAccount(
        // accountNumber
        currentUserAccountData.get(1), // firstName
        // lastName
        // address
        currentUserAccountData.get(4), // username
        // password
        currentUserAccountData.get(6), // IBAN
        balance);

    transactionInterface = new TransactionInterface(
        account.getIBAN());

    mainMenu(account);
  }

  /**
   * Displays the main menu and processes user choices in a loop.
   *
   * @param account the logged-in user's account.
   */
  private void mainMenu(BankAccount account) {
    boolean running = true;
    while (running) {
      displayMainMenu(account.getFirstName());

      int choice = UIHelper.getUserChoice();

      switch (choice) {
        case 1:
          showBalance(account);
          break;
        case 2:
          promptDeposit(account);
          break;
        case 3:
          promptWithdraw(account);
          break;
        case 4:
          transactionInterface.createTransaction();
          break;
        case 5:
          UIHelper.clearScreen();
          running = false;
          break;
        case 6:
          UIHelper.displayGoodbyeMessage();
          System.exit(0);
          break;
        default:
          System.out.println("Invalid choice.");
          break;
      }
    }
  }

  /**
   * Displays the main menu options to the user.
   *
   * @param firstName the first name of the logged-in user.
   */

  private void displayMainMenu(String firstName) {
    System.out.println("\n\u001B[36m============================");
    System.out.println("     Banking System Menu     ");
    System.out.println("============================\u001B[0m");
    System.out.println("\u001B[32mWelcome, " + firstName + "\u001B[0m");
    System.out.println("1. Check Balance");
    System.out.println("2. Deposit");
    System.out.println("3. Withdraw");
    System.out.println("4. Transfer");
    System.out.println("5. Logout");
    System.out.println("6. Exit");
    System.out.println("\u001B[36m============================\u001B[0m");
  }

  /**
   * Prompts the user to enter an amount to deposit and processes the deposit.
   *
   * @param account the logged-in user's account.
   */
  private void promptDeposit(BankAccount account) {
    double amount = UIHelper.promptForAmount("deposit");
    try {
      account.deposit(amount);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Prompts the user to enter an amount to withdraw and processes the withdrawal.
   *
   * @param account the logged-in user's account.
   */
  private void promptWithdraw(BankAccount account) {
    double amount = UIHelper.promptForAmount("withdraw");
    try {
      account.withdraw(amount);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Displays the current balance of the user's account.
   *
   * @param account the logged-in user's account.
   */
  private void showBalance(BankAccount account) {
    UIHelper.clearScreen();
    System.out.println("Your current balance is: " + account.getBalance());
  }
}
