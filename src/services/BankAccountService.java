package services;

import java.util.List;

import utils.CsvFileManager;

/**
 * class for managing bank accounts.
 */
public class BankAccountService {
  private final CsvFileManager csvFileManager;

  /**
   * Constructs a new BankAccountService with the specified CsvFileManager.
   *
   * @param csvFileManager the CsvFileManager used for handling CSV file
   *                       operations
   */
  public BankAccountService(CsvFileManager csvFileManager) {
    this.csvFileManager = csvFileManager;
  }

  /**
   * Creates a new bank account with the given input and appends it to the CSV
   * file.
   *
   * @param firstName the first name
   * @param lastName  the last name
   * @param address   the address
   * @param username  the username for the account
   * @param password  the password for the account
   */
  public void createAccount(
      String firstName, String lastName, String address, String username, String password) {

    int accountNumber = generateAccountNumber();
    List<String> accountData = List.of(
        String.valueOf(accountNumber),
        firstName.trim(),
        lastName.trim(),
        address.trim(),
        username.trim(),
        password.trim(),
        generateIBAN().trim(),
        String.format("%.2f", 0.0));
    csvFileManager.appendToCsv(accountData);
  }

  /**
   * Checks if a username already exists in the CSV file.
   *
   * @param username the username
   * @return true if the username exists, false otherwise
   */
  public boolean usernameExists(String username) {
    List<List<String>> users = csvFileManager.loadCsv();
    return users.stream().anyMatch(user -> user.get(4).equals(username));
  }

  /**
   * Generates a new unique account number.
   *
   * @return the generated account number
   */
  private int generateAccountNumber() {
    return Integer.parseInt(csvFileManager.loadCsv().get(csvFileManager.loadCsv().size() - 1).get(0)) + 1;
  }

  /**
   * Generates a new IBAN for the account.
   *
   * @return the generated IBAN
   */
  private String generateIBAN() {
    return "DE"
        + Math.round(Math.random() * 97)
        + "0000"
        + String.format("%010d", generateAccountNumber());
  }
}
