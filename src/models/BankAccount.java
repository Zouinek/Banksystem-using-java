package models;

import ui.UIHelper;
import utils.CsvFileManager;

/**
 * Represents a bank account with basic functionalities such as deposit and
 * withdraw.
 */
public class BankAccount {
  private String firstName;
  private String username;
  private String IBAN;
  private double balance;

  CsvFileManager csvFileManager = new CsvFileManager("csv/accounts.csv");

  /**
   * Constructs a BankAccount with the specified details.
   *
   * @param firstName the first name of the account holder
   * @param username  the username of the account holder
   * @param IBAN      the International Bank Account Number
   * @param balance   the initial balance of the account
   */
  public BankAccount(String firstName, String username, String IBAN, double balance) {
    this.firstName = firstName;
    this.username = username;
    this.IBAN = IBAN;
    this.balance = balance;
  }

  /**
   * Gets the first name of the account holder.
   *
   * @return the first name of the account holder
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Gets the International Bank Account Number (IBAN) of the account.
   *
   * @return the IBAN of the account
   */
  public String getIBAN() {
    return IBAN;
  }

  /**
   * Gets the current balance of the account.
   *
   * @return the current balance of the account
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Deposits a specified amount into the account.
   *
   * @param amount the amount to deposit
   * @throws IllegalArgumentException if the deposit amount is not positive
   */
  public void deposit(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Deposit amount must be positive.");
    }
    balance += amount;
    UIHelper.clearScreen();
    System.out.println("Depositing amount: $" + amount);
    System.out.println("New balance: $" + balance);
    csvFileManager.updateBalance(username, balance);
  }

  /**
   * Withdraws a specified amount from the account.
   *
   * @param amount the amount to withdraw
   * @throws IllegalArgumentException if the withdrawal amount is not positive or
   *                                  if there are insufficient funds
   */
  public void withdraw(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be positive.");
    }
    if (amount > balance) {
      throw new IllegalArgumentException("Insufficient funds. Balance: $" + balance);
    }
    balance -= amount;
    UIHelper.clearScreen();
    System.out.println("Withdrawing amount: $" + amount);
    System.out.println("New balance: $" + balance);
    csvFileManager.updateBalance(username, balance);
  }
}
