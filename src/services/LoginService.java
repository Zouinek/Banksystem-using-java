package services;

import java.util.List;
import java.util.Optional;

import utils.CsvFileManager;

/**
 * class responsible for handling user login operations.
 */
public class LoginService {
  private final CsvFileManager csvFileManager;

  /**
   * Constructs a LoginService with the specified CsvFileManager.
   *
   * @param csvFileManager the CsvFileManager used to manage CSV file
   *                       operations
   */
  public LoginService(CsvFileManager csvFileManager) {
    this.csvFileManager = csvFileManager;
  }

  /**
   * Attempts to log in with the provided username and password.
   *
   * @param username the username
   * @param password the password
   * @return an Optional containing list of stings of the user's account data if
   *         the login is successful, otherwise an empty Optional
   */
  public Optional<List<String>> login(String username, String password) {
    if (validateCredentials(username, password)) {
      return csvFileManager.getAccountInfoByUsername(username);
    } else {
      return Optional.empty();
    }
  }

  /**
   * Validates the provided username and password.
   *
   * @param username the username
   * @param password the password
   * @return true if the credentials are valid, false otherwise
   */
  public boolean validateCredentials(String username, String password) {
    return csvFileManager.loadCsv().stream()
        .filter(user -> user.size() > 5)
        .anyMatch(user -> isValidUser(user, username, password));
  }

  /**
   * Checks if the provided username and password match the stored credentials
   * 
   * @param user     the list of user details from the CSV
   * @param username the username
   * @param password the password
   * @return true if the username and password match, false otherwise
   */
  private boolean isValidUser(List<String> user, String username, String password) {
    String storedUsername = user.get(4).trim();
    String storedPassword = user.get(5).trim();
    return storedUsername.equals(username) && storedPassword.equals(password);
  }
}
