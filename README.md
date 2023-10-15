# 2-Stack-PDA Simulator

This is a Java-based simulator for 2-Stack Pushdown Automatons (2-Stack-PDA) with a command-line interface (CLI).

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Examples](#examples)

## Introduction

A 2-Stack-PDA is a theoretical computing device used in formal languages and automata theory. It extends the capabilities of a traditional pushdown automaton (PDA) by having two stacks, allowing for more complex language recognition and even being able to simulate Turing machines.

This package includes a set of classes to simulate 2-Stack-PDA behavior, allowing configuration of its input alphabet, states, start state, end states, and transitions. Additionally, it provides a simulator class to run simulations on input strings and determine their acceptance or rejection based on the 2-Stack-PDA's configurations.

**Note**: This project was developed as part of a Bachelor Thesis.

### Using 2-Stack-PDA and Translator Together

The "2PDA" and "Translator" projects are designed to work seamlessly together. After translating a Turing machine using the [Translator Project](https://github.com/Ysmnydgd/Translator), you can load the output .txt file into this 2-Stack-PDA Simulator.

## Features

- **MachineConfigurator**: Allows the user to configure the input alphabet, states, start state, end states, and transitions of the 2-Stack-PDA.
- **MachineSimulator**: Simulates the behavior of the 2-Stack-PDA and checks the acceptance status of input strings.
- **File Handling**: Save and load the machine configuration to/from a file.
- **Error Handling**: Provides informative error messages for invalid configurations or input strings.

## Getting Started

### Prerequisites

- Java SE Development Kit (JDK) 11

### Installation

1. Install a Java SDK which is Java 11 compatible (later versions should also be fine).
2. Make sure that the commands java and javac call the respective Java 11 binaries.
3. Install Git.
4. Checkout 2-Stack-PDA from git repository ```https://github.com/Ysmnydgd/2-Stack-PDA.git```
5. After that change into the dirctory 2-Stack-PDA.
6. To compile the source file execute the command ```javac -sourcepath ./src/ -d ./out/ .\src\twostackpda\MainMenu.java```
7. To create a jar file execute the command ```jar cvf 2-Stack-PDA.jar -C .\out\ .```
8. To execute the application run ```java -cp .\2-stack-pda.jar twostackpda/MainMenu```

## Usage
The 2PDA package can be used to define and simulate 2-Stack-PDAs for specific languages. It provides classes and methods to configure the machine, load/save configurations from/to files, and run simulations on input strings.

The initial stack symbol for both stacks is "#". 

### Configuring the 2-Stack-PDA

To configure the 2-Stack-PDA, follow these steps in the main menu:

1. Type `new` to create a new machine.
   
   You will be prompted to enter the following parameters:

   - **Input Alphabet**: Define all symbols that can be used in the input string. # and ε are NOT allowed.

   - **Set of States**: Specify all the states that the 2-Stack-PDA will have.

   - **Start State**: Choose one state from the set of states as the initial state.

   - **End State(s)**: Define one or more states from the set of states. If the machine reaches any of these states, it will stop.

2. Next, you need to define all the transitions for the 2-Stack-PDA. Each transition should have the following format:

   **current_state, input_symbol, pop_stack1, pop_stack2, push_stack1, push_stack2, next_state**

   - **current_state**: The state from which the machine should execute the transition.

   - **input_symbol**: The symbol to be read.

   - **pop_stack1**: The character to be popped (deleted) from stack 1.

   - **pop_stack2**: The character to be popped from stack 2.

   - **push_stack1**: The character to be pushed (written) into stack 1.

   - **push_stack2**: The character to be pushed into stack 2.

   - **next_state**: The state to which the 2-Stack-PDA will transition after this operation.
  
   ε as pop/push_stack1/2 character represents doing nothing, neither popping nor pushing, and ε as the input_symbol means that nothing will be read.

3. After entering all the transitions, type `end` to complete the transition configuration.

You can also save all entered configurations if you wish to a .txt file. Now the 2-Stack-PDA is ready for simulation.

You can enter input strings for simulation, view the current configuration by typing `show`, or return to the main menu using `menu`.

**Note**: The Simulator also functions as 1-Stack-PDAs by always passing **ε** as pop_stack2 and push_stack2 character.

### Loading a 2-Stack-PDA

If you have saved configurations, you can load them by following these steps:

1. In the main menu, type `load`. This will display a menu with all the machines in the directory. By default, the directory is set to `user.dir`.

2. You can select the machine you want to load by typing its index (seen on the right).
   
3. Once the machine is loaded, you can now run it by entering different input strings and observe how the 2-Stack-PDA processes them.
   

### Limitations

This project has some limitations that you should be aware of before usage:

- **Restriction to Deterministic Models:** The simulator is exclusively designed to support deterministic PDAs.

- **Limitation to Writing a Single Symbol:** You can push only one character at a time in a transition.

- **No Blanks Within Words:** Within the 2-Stack-PDA, the blank symbol **#** is defined as both the beginning and end of a word. Consequently, inserting blanks within a word is not possible and would result in an error. Instead, you can use any other symbols. For example, you can use the symbol □ instead of # to achieve a similar language.


## Examples

You'll find several pre-configured examples in the Examples folder above.
Please note that you'll need to change their directory to use them.
