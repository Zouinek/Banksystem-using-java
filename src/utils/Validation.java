package utils;

/**
 * class for performing validation checks.
 */
public class Validation {

  /**
   * Validates if the provided name is valid.
   * A valid name contains only alphabetic characters
   * 
   * @param str the name string to validate
   * @return true if the name is not valid, false otherwise
   */
  public static boolean isNotValidName(String str) {
    return !str.matches("[a-zA-Z\\säöüÄÖÜß]+");
  }

  /**
   * Validates if the provided date of birth is valid.
   * 
   * @param dob the date of birth string to validate
   * @return true if the date of birth is not valid, false otherwise
   */
  public static boolean isnotvaliddob(String dob) {
    return !dob.matches("\\d{2}-\\d{2}-\\d{4}");
  }
}
