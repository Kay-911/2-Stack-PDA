package twostackpda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * The MachineSimulator class is responsible for simulating the 2-Stack PDA.
 */
public class MachineSimulator {

  private final Stack<String> stack1;
  private final Stack<String> stack2;
  //private static final String EMPTY_SYMBOL = "\u03B5"; //UTF-8 for ε
  private static final String EMPTY_SYMBOL = "E"; //represents "ε"
  private static final String STACK1_INITIAL_SYMBOL = "#";
  // To be uncommented, if word should start with $
  /*
    private static final String STACK1_INITIAL_SYMBOL = "$";
  */
  private static final String STACK2_INITIAL_SYMBOL = "#";

  public MachineSimulator() {
    stack1 = new Stack<>();
    stack2 = new Stack<>();
  }

  /**
   * Runs the simulation of the 2-Stack PDA based on user input and configurations.
   *
   * @param scanner       The scanner to read user input.
   * @param inputAlphabet The list of symbols in the input alphabet of the PDA.
   * @param states        The list of states in the PDA.
   * @param startState    The starting state of the PDA.
   * @param endStates     The list of accepting states of the PDA.
   * @param transitions   The list of transitions defined for the PDA.
   */
  public void runMachine(Scanner scanner, List<String> inputAlphabet, List<Integer> states,
      int startState, List<Integer> endStates, List<String[]> transitions) {

    while (true) {
      // Read input string
      System.out.println("Enter the input string (or 'menu' or 'show'): ");
      System.out.print(">> ");
      String inputString = scanner.nextLine();

      if (inputString.equals("menu")) {
        break;
      }

      // Print the configuration details for the user to review
      if (inputString.equals("show")) {
        printConfigurationDetails(inputAlphabet, states, startState, endStates, transitions);
      } else {
        // Simulate the PDA
        simulateTwoStackPda(inputString, inputAlphabet, startState, endStates, transitions);
      }
    }
  }

  /**
   * Simulates the 2-Stack PDA for a given input string and configurations.
   *
   * @param inputString    The input string to be processed by the PDA.
   * @param inputAlphabet  The input alphabet of the PDA.
   * @param startState     The starting state of the PDA.
   * @param endStates      The accepting (end) states for the PDA.
   * @param transitions    The transitions defined for the PDA.
   */
  private void simulateTwoStackPda(String inputString, List<String> inputAlphabet,
      int startState, List<Integer> endStates, List<String[]> transitions) {

    // Check if the input string is not empty
    if (!inputString.isEmpty()) {

      // Check if the input string contains valid characters from the inputAlphabet
      List<String> invalidCharacters = new ArrayList<>();
      for (char c : inputString.toCharArray()) {
        String inputChar = String.valueOf(c);
        if (!inputAlphabet.contains(inputChar)) {
          invalidCharacters.add(inputChar);
        }
      }
      if (!invalidCharacters.isEmpty()) {
        System.err.println("Invalid input characters: " + invalidCharacters);
        return;
      }
    }

    // Initialize the current state of the PDA to the starting state
    int currentState = startState;

    // Initialize the two stacks of the PDA and set them to contain the initial symbol '#'
    stack1.clear();
    stack2.clear();
    stack1.push(STACK1_INITIAL_SYMBOL);
    stack2.push(STACK2_INITIAL_SYMBOL);

    // Print the initial stacks
    System.out.println("Stack 1: " + stack1);
    System.out.println("Stack 2: " + stack2);
    System.out.println();

    int i = 0;
    while (true) {
      // The input string is processed via the variable symbol and as soon as the input string is
      // finished, symbol will be set to epsilon (only stack operations are being performed).
      String symbol;
      if (i < inputString.length()) {
        symbol = String.valueOf(inputString.charAt(i));
      } else {
        symbol = EMPTY_SYMBOL;
      }

      // Check if the current state is one of the end states, meaning the input string
      // is accepted
      if (endStates.contains(currentState)) {
        System.out.println("Word " + inputString + " accepted!\n");
        return;
      }

      // Check if there are any valid transitions for the current state and the current symbol
      List<String[]> validTransitions = getValidTransitions(currentState, symbol, transitions);

      // If there are no valid transitions, the input string has failed
      if (validTransitions.isEmpty()) {
        System.out.println("Word " + inputString + " failed!\n");
        return;
      }

      // Loop through the valid transitions and simulate the PDA for each one
      for (String[] transition : validTransitions) {
        currentState = Integer.parseInt(transition[6]);

        // Perform stack operations based on the transition
        performStackOperations(transition);

        // Check if stack1 is empty, push # into stack 1
        // Next 3 lines to be commented, if word should start with $
        if (stack1.isEmpty()) {
          stack1.push(STACK1_INITIAL_SYMBOL);
        }

        // Print the transition and the updated stacks
        System.out.println("Transition: " + String.join(",", transition));
        System.out.println("Stack 1: " + stack1);
        System.out.println("Stack 2: " + stack2);
        System.out.println();
      }
      // Move to the next character in the input string
      i++;
    }
  }

  /**
   * Performs stack operations based on the given transition.
   *
   * @param transition The transition from which to perform stack operations.
   */
  private void performStackOperations(String[] transition) {
    performStackOperation(stack1, transition[2], transition[4]);
    performStackOperation(stack2, transition[3], transition[5]);
  }

  /**
   * Performs stack operation for a given stack with specified pop and push symbols.
   *
   * @param stack         The stack on which the operation is to be performed.
   * @param popSymbols    The symbols to pop from the stack.
   * @param pushSymbols   The symbols to push onto the stack.
   */
  private void performStackOperation(Stack<String> stack, String popSymbols, String pushSymbols) {
    if (!popSymbols.equals(EMPTY_SYMBOL)) {
      String[] popSymbolsArray = popSymbols.split(",");
      for (int j = popSymbolsArray.length - 1; j >= 0; j--) {
        if (!stack.empty() && stack.peek().equals(popSymbolsArray[j])) {
          stack.pop();
        } else {
          System.out.println("Word failed - transition[" + popSymbols + "]=E");
          return;
        }
      }
    }

    if (!pushSymbols.equals(EMPTY_SYMBOL)) {
      String[] pushSymbolsArray = pushSymbols.split(",");
      for (String symbol : pushSymbolsArray) {
        stack.push(symbol);
      }
    }
  }

  /**
   * Retrieves the list of valid transitions for a given current state and input symbol.
   *
   * @param currentState The current state of the PDA during the simulation.
   * @param symbol       The input symbol being processed during the simulation.
   * @param transitions  The list of transitions defined for the PDA.
   * @return A list of valid transitions that match the current state and input symbol.
   */
  private List<String[]> getValidTransitions(int currentState, String symbol,
      List<String[]> transitions) {
    // Initialize an empty list to store the valid transitions
    List<String[]> validTransitions = new ArrayList<>();

    // Loop through each transition in the machine's transition list
    for (String[] transition : transitions) {
      // Extract the actualState of the current transition
      int actualState = Integer.parseInt(transition[0]);

      // Check if the actualState and input symbol of the transition match the given currentState
      // and symbol.
      // Also, check if the top symbols of stack1 and stack2 match the pop symbols defined in the
      // transition, or if the pop symbols in the transition are empty (represented by "E").
      if (actualState == currentState && transition[1].equals(symbol)) {
        if ((transition[2].equals(EMPTY_SYMBOL) || stack1.empty() || transition[2].equals(stack1
            .peek()))
            && (transition[3].equals(EMPTY_SYMBOL) || stack2.empty() || transition[3].equals(stack2
            .peek()))) {

          // If the conditions are met, it means that the transition is valid for the given current
          // state and symbol. Hence, add the transition to the list of valid transitions.
          validTransitions.add(transition);
        }
      }
    }
    // Return the list of valid transitions found for the given current state and symbol.
    return validTransitions;
  }


  /**
   * Prints the configuration details for the user to review.
   *
   * @param inputAlphabet The list of symbols in the input alphabet of the PDA.
   * @param states        The list of states in the PDA.
   * @param startState    The starting state of the PDA.
   * @param endStates     The list of accepting states of the PDA.
   * @param transitions   The list of transitions defined for the PDA.
   */
  private void printConfigurationDetails(List<String> inputAlphabet, List<Integer> states,
      int startState, List<Integer> endStates,
      List<String[]> transitions) {
    System.out.println("Input Alphabet: " + inputAlphabet);
    System.out.println("States: " + states);
    System.out.println("Start State: " + "[" + startState + "]");
    System.out.println("End State(s): " + endStates);
    System.out.println("Transitions:");
    for (String[] transition : transitions) {
      System.out.println(Arrays.toString(transition));
    }
  }
}