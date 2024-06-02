import services.BankAccountService;
import ui.AccountCreationInterface;
import ui.BankAccountInterface;
import ui.UIHelper;
import utils.CsvFileManager;

/**
 * This is the main class for the application.
 * It manages the overall program flow and interacts with other program
 * components
 */
public class App {

  public static void main(String[] args) {
    try {
      CsvFileManager csvFileManager = new CsvFileManager("csv/accounts.csv");
      BankAccountService bankAccountService = new BankAccountService(csvFileManager);
      BankAccountInterface bankAccountInterface = new BankAccountInterface(csvFileManager);
      runStartMenu(bankAccountInterface, bankAccountService);
    } catch (Exception e) {
      System.err.println("An error occurred: " + e.getMessage());
    }
  }

  /**
   * This method runs the main menu loop of the application.
   * It presents options to the user and calls methods based on
   * their choice.
   *
   * @param bankAccountInterface Object used for user interactions with bank
   *                             accounts
   * @param bankAccountService   Object used to manage bank account logic
   */

  private static void runStartMenu(BankAccountInterface bankAccountInterface,
      BankAccountService bankAccountService) {
    boolean running = true;
    AccountCreationInterface accountCreationInterface = new AccountCreationInterface(bankAccountService);

    while (running) {
      System.out.println("\n Welcome to the");
      printAsciiArt();
      System.out.println("=======================================");
      System.out.println("             Start Menu              ");
      System.out.println("=======================================");
      System.out.println("1. Login");
      System.out.println("2. Create Account");
      System.out.println("3. Exit");
      System.out.println("=======================================");
      int choice = UIHelper.getUserChoice();

      switch (choice) {
        case 1:
          UIHelper.clearScreen();
          bankAccountInterface.tryLogin();
          break;
        case 2:
          UIHelper.clearScreen();
          accountCreationInterface.createAccountInterface();
          break;
        case 3:
          UIHelper.displayGoodbyeMessage();
          running = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
          break;
      }
    }
  }

  /**
   * Prints the ASCII art to the console.
   */
  public static void printAsciiArt() {
    String art = "  ___     _  __    ___      ___                                                            \n" +
        " | __|   | |/ /   |   \\    |_ _|                                                           \n" +
        " | _|    | ' <    | |) |    | |                                                            \n" +
        " |___|   |_|\\_\\   |___/    |___|                                                           \n" +
        "                                                                                           \n" +
        "  ___     _     _  _   _  __  ___   _  _    ___   ___  __   __  ___   _____   ___   __  __ \n" +
        " | _ )   /_\\   | \\| | | |/ / |_ _| | \\| |  / __| / __| \\ \\ / / / __| |_   _| | __| |  \\/  |\n" +
        " | _ \\  / _ \\  | .` | | ' <   | |  | .` | | (_ | \\__ \\  \\ V /  \\__ \\   | |   | _|  | |\\/| |\n" +
        " |___/ /_/ \\_\\ |_|\\_| |_|\\_\\ |___| |_|\\_|  \\___| |___/   |_|   |___/   |_|   |___| |_|  |_|\n";
    System.out.println(art);
  }

}
