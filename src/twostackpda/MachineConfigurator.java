package twostackpda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * The MachineConfigurator class is responsible for configuring the 2-Stack PDA.
 */
public class MachineConfigurator {
  private final List<String> inputAlphabet;
  private final List<Integer> states;
  private final int startState;
  private final List<Integer> endStates;
  private final List<String[]> transitions;

  /**
   * Constructs a MachineConfigurator with the provided parameters.
   *
   * @param inputAlphabet The input alphabet of the 2-Stack PDA.
   * @param states        The states of the 2-Stack PDA.
   * @param startState    The starting state of the 2-Stack PDA.
   * @param endStates     The list of end states of the 2-Stack PDA.
   * @param transitions   The transitions functions of the 2-Stack PDA.
   */
  public MachineConfigurator(List<String> inputAlphabet, List<Integer> states, int startState,
      List<Integer> endStates, List<String[]> transitions) {
    this.inputAlphabet = inputAlphabet;
    this.states = states;
    this.startState = startState;
    this.endStates = endStates;
    this.transitions = transitions;
  }

  /**
   * Get the input alphabet of the 2-Stack PDA.
   *
   * @return A list containing the symbols of the input alphabet.
   */
  public List<String> getInputAlphabet() {
    return inputAlphabet;
  }

  /**
   * Get the set of states for the 2-Stack PDA.
   *
   * @return A list containing the integer values of the states.
   */
  public List<Integer> getStates() {
    return states;
  }

  /**
   * Get the starting state of the 2-Stack PDA .
   *
   * @return The starting state.
   */
  public int getStartState() {
    return startState;
  }

  /**
   * Get the end states of the 2-Stack PDA.
   *
   * @return A list containing the integer values of the end states.
   */
  public List<Integer> getEndStates() {
    return endStates;
  }

  /**
   * Gets the transitions of the 2-Stack PDA.
   *
   * @return A list of string arrays representing the transitions between states.
   */
  public List<String[]> getTransitions() {
    return transitions;
  }

  /**
   * Configure the input alphabet based on user input.
   *
   * @param scanner The scanner to read user input.
   * @return A list containing the symbols of the input alphabet.
   */
  public static List<String> configureInputAlphabet(Scanner scanner) {
    List<String> inputAlphabet;

    while (true) {
      System.out.println("Enter the input alphabet (separate symbols with commas, blanks/spaces "
          + "not allowed): ");
      System.out.print(">> ");
      String inputAlphabetStr = scanner.nextLine().trim();

      if (inputAlphabetStr.isEmpty()) {
        System.err.println("Invalid input alphabet. Please enter at least one character.");
      } else {
        inputAlphabet = Arrays.asList(inputAlphabetStr.split(","));
        break;
      }
    }
    return inputAlphabet;
  }

  /**
   * Configure the set of states based on user input.
   *
   * @param scanner The scanner to read user input.
   * @return A list containing the integer values of the states.
   */
  public static List<Integer> configureStates(Scanner scanner) {
    List<Integer> states = new ArrayList<>();

    while (true) {
      try {
        System.out.println("Enter the set of states (separate symbols with commas, blanks/spaces "
            + "not allowed): ");
        System.out.print(">> ");
        String statesStr = scanner.nextLine();
        states.clear();
        for (String state : statesStr.split(",")) {
          int stateValue = Integer.parseInt(state.trim());
          if (states.contains(stateValue)) {
            throw new IllegalArgumentException("Invalid states. Duplicate states are not allowed.");
          }
          states.add(stateValue);
        }
        break;
      } catch (NumberFormatException e) {
        // Catch and handle exceptions related to invalid state format (non-integer)
        System.err.println("Invalid state(s). Only numbers are allowed.");
      } catch (IllegalArgumentException e) {
        // Catch and handle exceptions related to duplicate states
        System.err.println(e.getMessage());
      }
    }
    return states;
  }

  /**
   * Configure the starting state based on user input.
   * @param scanner The scanner to read user input.
   * @param states  The list of states that the PDA can have.
   * @return The starting state.
   */
  public static int configureStartState(Scanner scanner, List<Integer> states) {
    int startState;

    // Request input until a valid start state is provided
    while (true) {
      System.out.println("Enter the starting state (blanks/spaces not allowed): ");
      System.out.print(">> ");
      String input = scanner.nextLine().trim();

      try {
        startState = Integer.parseInt(input);
        if (states.contains(startState)) {
          break;
        } else {
          System.err.println("Invalid start state. The start state must be one of the defined "
              + "states.");
        }
      } catch (NumberFormatException e) {
        System.err.println("Invalid start state. Please enter a valid integer.");
      }
    }
    return startState;
  }

  /**
   * Configure the list of end states based on user input.
   * @param scanner The scanner to read user input.
   * @param states  The list of states that the PDA can have.
   * @return A list containing the integer values of the end states.
   */
  public static List<Integer> configureEndStates(Scanner scanner, List<Integer> states) {
    List<Integer> endStates = new ArrayList<>();
    // Request input until a valid set of end states is provided
    boolean validEndInput = false;

    while (!validEndInput) {
      System.out.println("Enter the end states (separate symbols with commas, blanks/spaces not "
          + "allowed): ");
      System.out.print(">> ");
      String endStatesStr = scanner.nextLine();
      String[] endStatesList = endStatesStr.split(",");

      Set<Integer> endStatesSet = new HashSet<>();
      boolean invalidEndState = false;

      for (String state : endStatesList) {
        try {
          int endState = Integer.parseInt(state.trim());
          if (!states.contains(endState)) {
            System.err.println("Invalid end state: " + endState
                + ". The end state must be one of the defined states.");
            invalidEndState = true;
            break;
          } else if (endStatesSet.contains(endState)) {
            System.err.println("Invalid end states. Duplicate end states are not allowed.");
            invalidEndState = true;
            break;
          } else {
            endStatesSet.add(endState);
          }
        } catch (NumberFormatException e) {
          // Catch and handle exceptions related to invalid end state format (non-integer)
          System.err.println("Invalid end states. Only integers are allowed.");
          invalidEndState = true;
          break;
        }
      }

      if (!invalidEndState) {
        endStates.addAll(endStatesSet);
        validEndInput = true;
      }
    }
    return endStates;
  }

  /**
   * Configure the list of transitions based on user input.
   *
   * @param scanner The scanner to read user input.
   * @return A list of string arrays representing the transitions between states.
   */
  public static List<String[]> configureTransitions(Scanner scanner) {
    List<String[]> transitions = new ArrayList<>();
    List<String> errorMessages = new ArrayList<>();

    System.out.println(
        "Enter the transitions in the following format:\n"
            + "current_state,input_symbol,pop_stack1,pop_stack2,push_stack1,push_stack2,"
            + "next_state\n"
            + "Separate the elements with commas. Blanks/spaces are not allowed.\n"
            + "When you're done, type 'end'.");
    System.out.print(">> ");

    while (true) {
      String transitionStr = scanner.nextLine();

      if (transitionStr.equalsIgnoreCase("end")) {
        if (transitions.isEmpty()) {
          System.err.println("No valid transitions entered. Please try again.");
        } else {
          boolean allValid = true;
          for (String[] transition : transitions) {
            // Check if the transition length is valid
            if (transition.length != 7) {
              errorMessages.add("The length of a transition needs to be 7: "
                  + Arrays.toString(transition));
              continue; // Skip further checks for this transition if the length is not 7
            }
            if (!isInteger(transition[0]) || !isInteger(transition[6])) {
              allValid = false;
              errorMessages.add("The 'current_state' and 'next_state' need to be Integers: "
                  + Arrays.toString(transition));
            }
          }
          if (allValid) {
            break;
          } else {
            // Print all error messages
            System.err.println("One or more transitions are invalid.");
            for (String errorMessage : errorMessages) {
              System.err.println(errorMessage);
            }
            errorMessages.clear();

            System.err.println("Please enter all transitions again:");
            transitions.clear();
          }
        }
      } else {
        String[] transition = transitionStr.trim().split(",");
        transitions.add(transition);
        }
      }
    return transitions;
  }

  /**
   * Check if a string can be converted to an integer.
   *
   * @param str The string to check.
   * @return True if the string can be converted to an integer, false otherwise.
   */
  private static boolean isInteger(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}