
package utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * class for managing CSV files.
 * Provides methods to load, retrieve, append, and update data in a CSV file.
 */
public class CsvFileManager {
  private final Path filePath;

  /**
   * Constructs a CsvFileManager with the specified file path.
   *
   * @param filePath the path of the CSV file
   */
  public CsvFileManager(String filePath) {
    this.filePath = Paths.get(filePath);
  }

  /**
   * Loads the CSV file and returns the data as a list of rows, where each row
   * is a list of strings.
   * Skips the header and only includes rows with exactly 8 columns.
   *
   * @return a list of rows from the CSV file
   */
  public List<List<String>> loadCsv() {
    List<List<String>> values = new ArrayList<>();
    try (Stream<String> lines = Files.lines(filePath)) {
      values = lines
          .skip(1)
          .map(line -> Stream.of(line.split(",")).map(String::trim).collect(Collectors.toList()))
          .filter(row -> row.size() == 8)
          .collect(Collectors.toList());
    } catch (IOException e) {
      System.err.println("Failed to load CSV: " + e.getMessage());
    }
    return values;
  }

  /**
   * Retrieves account data by username.
   *
   * @param username the username to search for
   * @return an List of String containing the account data if found, otherwise an
   *         empty
   */
  public Optional<List<String>> getAccountInfoByUsername(String username) {
    try (Stream<String> lines = Files.lines(filePath)) {
      return lines
          .skip(1)
          .map(line -> Stream.of(line.split(",")).map(String::trim).collect(Collectors.toList()))
          .filter(data -> data.get(4).equals(username))
          .findFirst();
    } catch (IOException e) {
      System.err.println("Failed to retrieve account info: " + e.getMessage());
      return Optional.empty();
    }
  }

  /**
   * Retrieves the balance by IBAN.
   *
   * @param IBAN the IBAN to search for
   * @return the balance if found, otherwise an empty
   */
  public Optional<String> getBalanceByIBAN(String IBAN) {
    try (Stream<String> lines = Files.lines(filePath)) {
      return lines
          .skip(1)
          .map(line -> Stream.of(line.split(",")).map(String::trim).collect(Collectors.toList()))
          .filter(data -> data.get(6).equals(IBAN))
          .map(data -> data.get(7))
          .findFirst();
    } catch (IOException e) {
      System.err.println("Failed to retrieve balance: " + e.getMessage());
      return Optional.empty();
    }
  }

  /**
   * Appends a new row of data to the CSV file.
   *
   * @param data the data to append
   */
  public void appendToCsv(List<String> data) {
    String joinedData = String.join(",", data) + "\n";
    try (BufferedWriter csvWriter = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND,
        StandardOpenOption.CREATE)) {
      csvWriter.write(joinedData);
    } catch (IOException e) {
      System.err.println("Failed to append to CSV: " + e.getMessage());
    }
  }

  /**
   * Updates the balance for username.
   *
   * @param username   the username to search for
   * @param newBalance the new balance to set
   */
  public void updateBalance(String username, double newBalance) {
    List<List<String>> values = loadCsv();
    boolean isUpdated = false;

    for (List<String> row : values) {
      if (row.get(4).equals(username)) {
        row.set(7, String.valueOf(newBalance));
        isUpdated = true;
        break;
      }
    }

    if (isUpdated) {
      try (BufferedWriter csvWriter = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING)) {
        csvWriter.write("account_number,first_name,last_name,address,username,password,IBAN,balance\n");
        for (List<String> row : values) {
          csvWriter.write(String.join(",", row) + "\n");
        }
      } catch (IOException e) {
        System.err.println("Failed to update CSV: " + e.getMessage());
      }
    }
  }

  /**
   * Updates the balance for the IBAN.
   *
   * @param IBAN       the IBAN
   * @param newBalance the new balance
   */
  public void updateBalanceByIBAN(String IBAN, double newBalance) {
    List<List<String>> values = loadCsv();
    boolean isUpdated = false;

    for (List<String> row : values) {
      if (row.get(6).equals(IBAN)) {
        row.set(7, String.valueOf(newBalance));
        isUpdated = true;
        break;
      }
    }

    if (isUpdated) {
      try (BufferedWriter csvWriter = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING)) {
        csvWriter.write("account_number,first_name,last_name,address,username,password,IBAN,balance\n");
        for (List<String> row : values) {
          csvWriter.write(String.join(",", row) + "\n");
        }
      } catch (IOException e) {
        System.err.println("Failed to update CSV: " + e.getMessage());
      }
    }
  }
}
