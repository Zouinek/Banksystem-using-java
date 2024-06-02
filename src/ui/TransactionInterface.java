package ui;

import java.util.Optional;
import java.util.Scanner;

import models.Transaction;
import utils.CsvFileManager;

/**
 * The TransactionInterface class handles the creation of transactions
 * between accounts, prompting users to input necessary details and
 * updating account balances accordingly.
 */
public class TransactionInterface {

  /**
   * Manages the reading and writing of account data from a CSV file.
   */
  private final CsvFileManager csvFileManager = new CsvFileManager("csv/accounts.csv");
  /**
   * Scanner instance for reading user input from the console.
   */
  private final Scanner scanner;
  /**
   * The IBAN of the sender's account.
   */
  private final String senderIBAN;

  /**
   * Constructs a TransactionInterface for a specific sender's account.
   *
   * @param senderIBAN the IBAN of the sender's account.
   */
  public TransactionInterface(String senderIBAN) {
    this.scanner = UIHelper.getScanner();
    this.senderIBAN = senderIBAN;
  }

  /**
   * Creates a transaction by prompting the user to enter the receiver's IBAN
   * and the amount to be transferred. Validates the inputs and updates the
   * account balances if the transaction is successful.
   */
  public void createTransaction() {
    String receiverIBAN;
    double amount;

    System.out.println("=== Create Transaction ===");

    // Prompt user for the receiver's IBAN and validate it
    do {
      System.out.print("> Enter the receiver's IBAN: ");
      receiverIBAN = scanner.nextLine();
      if (csvFileManager.getBalanceByIBAN(receiverIBAN).isEmpty()) {
        System.out.println("Error: IBAN does not exist.");
      }
    } while (csvFileManager.getBalanceByIBAN(receiverIBAN).isEmpty());

    // Prompt user for the transaction amount and validate it
    do {
      System.out.print("> Enter the amount to transfer:");
      while (!scanner.hasNextDouble()) {
        System.out.println("Error: Invalid amount.");
        scanner.next();
      }
      amount = scanner.nextDouble();
      scanner.nextLine();
      if (amount <= 0) {
        System.out.println("Error: Amount must be greater than zero.");
      }
    } while (amount <= 0);

    // Retrieve optional balances for sender and receiver
    Optional<String> senderBalanceOpt = csvFileManager.getBalanceByIBAN(senderIBAN);
    Optional<String> receiverBalanceOpt = csvFileManager.getBalanceByIBAN(receiverIBAN);

    if (senderBalanceOpt.isEmpty()) {
      System.out.println("Error: Sender account not found.");
      return;
    }

    double senderBalance = Double.parseDouble(senderBalanceOpt.get());
    double receiverBalance = Double.parseDouble(receiverBalanceOpt.get());

    if (senderBalance < amount) {
      System.out.println("Error: Insufficient funds.");
      return;
    }

    // Update balances and save the transaction
    senderBalance -= amount;
    receiverBalance += amount;

    csvFileManager.updateBalanceByIBAN(senderIBAN, senderBalance);
    csvFileManager.updateBalanceByIBAN(receiverIBAN, receiverBalance);

    Transaction transaction = new Transaction(senderIBAN, receiverIBAN, amount);
    transaction.saveTransaction(transaction);
    UIHelper.clearScreen();
    System.out.println("You have transferred $" + amount + " to " + receiverIBAN + ".");
  }
}
