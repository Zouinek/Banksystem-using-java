package models;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a transaction between two bank accounts.
 */
public class Transaction {
  private final String senderIBAN;
  private final String receiverIBAN;
  private final double amount;
  private final String timestamp;

  public Transaction(String senderIBAN, String receiverIBAN, double amount) {
    this.senderIBAN = senderIBAN;
    this.receiverIBAN = receiverIBAN;
    this.amount = amount;
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
  }

  /**
   * Constructs a Transaction with the specified sender IBAN, receiver IBAN, and
   * amount.
   *
   * @param senderIBAN   the International Bank Account Number (IBAN) of the
   *                     sender
   * @param receiverIBAN the IBAN of the receiver
   * @param amount       the amount of money to be transferred
   */
  public void saveTransaction(Transaction transaction) {
    String csvFilePath = "csv/transactions.csv";
    try (FileWriter fileWriter = new FileWriter(csvFilePath, true)) {
      fileWriter.write(transaction.toString() + "\n");
    } catch (IOException e) {
      System.err.println("Error writing to CSV file: " + e.getMessage());
    }
  }

  /**
   * Returns a string representation of the transaction.
   * The format is "senderIBAN,receiverIBAN,amount,timestamp".
   *
   * @return a string representation of the transaction
   */
  @Override
  public String toString() {
    return senderIBAN + "," + receiverIBAN + "," + amount + "," + timestamp;
  }
}
