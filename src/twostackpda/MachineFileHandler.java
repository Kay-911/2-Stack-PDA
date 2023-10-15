package twostackpda;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The MachineFileHandler class provides methods to save and load the 2-Stack PDA
 * configuration to/from a file.
 */
public class MachineFileHandler {

  private MachineFileHandler() {
    // Private constructor to prevent instantiation, as all methods are static.
  }

  /**
   * Load a 2-Stack PDA configuration from a file.
   *
   * @param file The file to load the configuration from.
   * @return A MachineConfigurator object object representing the loaded configuration, or
   *         null if an error occurs.
   */
  public static MachineConfigurator loadMachineFromFile(File file) {
    List<String> inputAlphabet = null;
    List<Integer> states = null;
    int startState = -1;
    List<Integer> endStates = null;
    List<String[]> transitions = null;

    try {
      try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          String[] parts = line.split("=", 2);
          if (parts.length == 2) {
            String key = parts[0].trim();
            String value = parts[1].trim();
            switch (key) {
              case "inputAlphabet":
                // Parse the inputAlphabet from the value and store it in the list.
                inputAlphabet = Arrays.asList(value.split(","));
                break;
              case "states":
                // Parse the states from the value and store them in the list.
                states = Arrays.stream(value.split(",")).map(Integer::parseInt)
                    .collect(Collectors.toList());
                break;
              case "startState":
                // Parse the startState from the value and store it.
                startState = Integer.parseInt(value);
                break;
              case "endStates":
                // Parse the endStates from the value and store them in the list.
                endStates = Arrays.stream(value.split(",")).map(Integer::parseInt)
                    .collect(Collectors.toList());
                break;
              case "transition":
                // Parse the transition from the value and add it to the list of transitions.
                if (transitions == null) {
                  transitions = new ArrayList<>();
                }
                transitions.add(value.split(","));
                break;
              default:
                // Unexpected key in the file, throw an exception.
                throw new IllegalStateException("Unexpected key in the file: " + key);
            }
          }
        }
      }
    } catch (Exception e) {
      System.err.println("Error reading from file: " + e.getMessage());
      return null;
    }
    // Return a new MachineConfigurator object with the loaded configuration.
    return new MachineConfigurator(inputAlphabet, states, startState, endStates, transitions);
  }


  /**
   * Save a 2-Stack PDA configuration to a file.
   *
   * @param fileName      The name of the file to save the machine configuration.
   * @param inputAlphabet The list of symbols in the input alphabet of the PDA.
   * @param states        The list of states in the PDA.
   * @param startState    The starting state of the PDA.
   * @param endStates     The list of accepting states of the PDA.
   * @param transitions   The list of transitions defined for the PDA.
   */
  public static void saveMachineToFile(String fileName, List<String> inputAlphabet,
      List<Integer> states, int startState,
      List<Integer> endStates, List<String[]> transitions) {

    fileName = addFileExtension(fileName);

    try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
      writer.println("inputAlphabet=" + String.join(",", inputAlphabet));
      writer.println("states="
          + String.join(",", states.stream().map(Object::toString).toArray(String[]::new)));
      writer.println("startState=" + startState);
      writer.println("endStates="
          + String.join(",", endStates.stream().map(Object::toString).toArray(String[]::new)));
      for (String[] transition : transitions) {
        writer.println("transition=" + String.join(",", transition));
      }
      System.out.println("Machine saved to file: " + fileName);
    } catch (IOException e) {
      System.err.println("Error saving machine to file: " + e.getMessage());
    }
  }

  /**
   * Add a ".txt" file extension to a given fileName if it doesn't already have one.
   *
   * @param fileName The name of the file.
   * @return The fileName with ".txt" extension.
   */
  private static String addFileExtension(String fileName) {
    if (!fileName.endsWith(".txt")) {
      fileName += ".txt";
    }
    return fileName;
  }
}