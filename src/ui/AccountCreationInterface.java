package ui;

import java.util.Scanner;

import services.BankAccountService;
import utils.Validation;

/**
 * This class represents an interface for creating a bank account.
 * It interacts with the user to get information and
 * uses BankAccountService to create the account.
 */
public class AccountCreationInterface {

  private final BankAccountService bankAccountService;
  private final Scanner scanner;

  public AccountCreationInterface(BankAccountService bankAccountService) {
    this.bankAccountService = bankAccountService;
    this.scanner = UIHelper.getScanner();
  }

  /**
   * This method guides the user through the process of creating a bank account.
   * It collects the user's first name, last name, date of birth, address,
   * username, and password.
   * Performs validation on the inputs and ensures the username is unique.
   */
  public void createAccountInterface() {

    String firstName, lastName, street, city, dob;

    System.out.println("=== Create Account ===");

    do {
      System.out.print("> Enter your first name: ");
      firstName = scanner.nextLine();
      if (Validation.isNotValidName(firstName)) {
        System.out.println("Error: First name should contain only letters.");
      }
    } while (Validation.isNotValidName(firstName));

    do {
      System.out.print("> Enter your last name: ");
      lastName = scanner.nextLine();
      if (Validation.isNotValidName(lastName)) {
        System.out.println("Error: Last name should contain only letters.");
      }
    } while (Validation.isNotValidName(lastName));

    System.out.print("> Enter your date of birth in DD-MM-YYYY format: ");
    dob = scanner.nextLine();
    while (Validation.isnotvaliddob(dob)) {
      System.out.println("Error: Invalid format. Use DD-MM-YYYY.");
      System.out.print("> Enter your date of birth in DD-MM-YYYY format: ");
      dob = scanner.nextLine();
    }

    System.out.println("--- Address Information ---");
    System.out.print("> Enter your street: ");
    street = scanner.nextLine();

    System.out.print("> Enter your city: ");
    city = scanner.nextLine();
    while (Validation.isNotValidName(city)) {
      System.out.println("Error: City should contain only letters.");
      System.out.print("> Enter your city: ");
      city = scanner.nextLine();
    }
    // Checks if the entered username already exists using the
    // bankAccountService.usernameExists(username) method.
    // The ! operator negates the result, so validUsername will be true if the
    // username does not exist, and false if it does exist.
    String username;
    boolean validUsername;
    do {
      System.out.print("> Select a username: ");
      username = scanner.nextLine();
      validUsername = !bankAccountService.usernameExists(username);
      if (!validUsername) {
        System.out.println("Error: Username already exists.");
      }
    } while (!validUsername);

    System.out.print("> Enter your password: ");
    String password = scanner.nextLine();

    String address = String.format("%s-%s", street, city);

    bankAccountService.createAccount(firstName, lastName, address, username, password);
    System.out.println("Account created successfully");
  }
}
