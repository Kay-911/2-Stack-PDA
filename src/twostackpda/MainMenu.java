package twostackpda;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * The MainMenu class represents the main menu and user interface for the 2-Stack PDA Simulator.
 */
public class MainMenu {

  private static final String SEPARATOR = "---------------------------------------------------";
  private static final String TITLE = "2-STACK PDA SIMULATOR";
  private static final String VERSION = "VERSION 1.0";
  private static final String NAME = "© 2023";

  private static final Scanner scanner = new Scanner(System.in);

  /**
   * The main entry point of the application. Displays the main menu and allows users to interact
   * with the application.
   *
   * @param args The command-line arguments (not used in this application).
   */
  public static void main(String[] args) {

    /* String currentWorkingDirectory = System.getProperty("user.dir");
    System.out.println("Current Working Directory: " + currentWorkingDirectory);*/

    System.out.println(SEPARATOR);
    System.out.printf("%s%n%s%n%s%n", centerText(TITLE, SEPARATOR.length()),
        centerText(VERSION, SEPARATOR.length()), centerText(NAME, SEPARATOR.length()));

    // Display the menu and perform the corresponding actions based on user input
    while (true) {
      displayMenu();
      // Trim leading and trailing whitespace
      String choice = scanner.nextLine().trim();

      switch (choice) {
        // Create a new machine
        case "new":
          createNewPda();
          break;

        // Load a machine from a file
        case "load":
          loadPdaFromFile();
          break;

        // Exit the app
        case "exit":
          System.out.println("Exiting the 2-Stack-PDA Simulator. Goodbye!");
          scanner.close();
          return;

        default:
          System.err.println("Invalid choice. Please try again.\n");
          break;
      }
    }
  }

  /**
   * Displays the main menu options.
   */
  private static void displayMenu() {
    System.out.println(SEPARATOR);
    System.out.println("Menu:");
    System.out.println("Type 'new' to create a new machine.");
    System.out.println("Type 'load' to load a machine from a file.");
    System.out.println("Type 'exit' to close the app.");
    System.out.println(SEPARATOR);
    System.out.println("Enter your choice: ");
    System.out.print(">> ");
  }

  /**
   * Creates a new 2-Stack PDA machine.
   */
  private static void createNewPda() {
    // Configure the machine components by taking user input
    List<String> inputAlphabet = MachineConfigurator.configureInputAlphabet(MainMenu.scanner);
    List<Integer> states = MachineConfigurator.configureStates(MainMenu.scanner);
    int startState = MachineConfigurator.configureStartState(MainMenu.scanner, states);
    List<Integer> endStates = MachineConfigurator.configureEndStates(MainMenu.scanner, states);
    List<String[]> transitions = MachineConfigurator.configureTransitions(MainMenu.scanner);

    // After creating the machine, prompt the user to save it to a file
    System.out.println("Do you want to save this machine to a file? (yes/no)");
    String saveChoice = MainMenu.scanner.nextLine().trim();
    if ("yes".equalsIgnoreCase(saveChoice)) {
      // Ask the user to provide a filename for the machine configuration
      System.out.println("Enter the file name: ");
      System.out.print(">> ");
      String fileName = MainMenu.scanner.nextLine();
      // Save the machine configuration to the specified file
      MachineFileHandler.saveMachineToFile(fileName, inputAlphabet, states, startState,
          endStates, transitions);
    }

    // Run the machine simulator with the newly created machine configurations
    MachineSimulator machineSimulator = new MachineSimulator();
    machineSimulator.runMachine(MainMenu.scanner, inputAlphabet, states, startState, endStates,
        transitions);
  }

  /**
   * Loads a 2-Stack-PDA machine configuration from a file.
   */
  private static void loadPdaFromFile() {
    // Get the list of machine files with .txt extension from the current directory
    File currentDir = new File(System.getProperty("user.dir"));
    File[] machineFiles = currentDir.listFiles((dir, name1) -> name1.endsWith(".txt"));

    // If no machine files are found, notify the user and exit the case
    if (machineFiles == null || machineFiles.length == 0) {
      System.out.println("No machine files found in the current directory.");
    } else {

      // Display the available machine files to the user
      System.out.println("Select a machine to load:");
      for (int i = 0; i < machineFiles.length; i++) {
        System.out.println((i + 1) + ". " + machineFiles[i].getName());
      }

      int selection;
      do {
        // Prompt the user to enter the number of the machine to load or 0 to cancel
        System.out.println("Enter the number of the machine to load (or 0 to cancel): ");
        System.out.print(">> ");
        String input = MainMenu.scanner.nextLine();
        try {
          selection = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          selection = -1;
        }
      } while (selection < 0 || selection > machineFiles.length);

      // If the user chose a valid machine file, load its configurations and run the simulator
      if (selection > 0 && selection <= machineFiles.length) {
        File selectedFile = machineFiles[selection - 1];
        String filePath = selectedFile.getPath(); // Get the file path from the selected File

        // Load the machine configurations from the selected file
        MachineConfigurator loadedConfigurations =
            MachineFileHandler.loadMachineFromFile(new File(filePath));

        System.out.println("Machine: " + machineFiles[selection - 1].getName() + " loaded!");

        // Run the machine simulator with the loaded machine configurations
        MachineSimulator machineSimulator = new MachineSimulator();
        assert loadedConfigurations != null;
        machineSimulator.runMachine(MainMenu.scanner, loadedConfigurations.getInputAlphabet(),
            loadedConfigurations.getStates(), loadedConfigurations.getStartState(),
            loadedConfigurations.getEndStates(), loadedConfigurations.getTransitions());
      }
    }
  }

  /**
   * Centers the given text within the specified width.
   *
   * @param text  the text to center
   * @param width the width to center the text within
   * @return the centered text
   **/
  private static String centerText(String text, int width) {
    int padding = (width - text.length()) / 2;
    return " ".repeat(padding) + text + " ".repeat(padding);
  }
}
