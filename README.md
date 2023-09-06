# Terminal Emulator

This is a simple Java program that emulates a terminal window. It allows you to interact with the command prompt (CMD) from within your Java application. You can run commands, switch directories, display logs, and more.

## Features

- Run CMD commands from within your Java application.
- Navigate between different drives (C:/, D:/) using shortcuts.
- Display command logs for reference.
- Exit the terminal emulator gracefully.

## Getting Started

To use this terminal emulator in your Java project, follow these steps:

1. Clone or download the repository containing the code.

2. Include the `TerminalEmulator.java` file in your project.

3. Create an instance of the `TerminalEmulator` class in your Java code:

   ```java
   TerminalEmulator terminal = new TerminalEmulator();
   ```

4. To start the terminal emulator, call the `createTerminal` method:

   ```java
   terminal.createTerminal();
   ```

5. You can send commands to the terminal using the `sendCommand` method:

   ```java
   terminal.sendCommand("your-command-here");
   ```

6. To terminate the terminal emulator, call the `terminateTerminal` method:

   ```java
   terminal.terminateTerminal();
   ```

## Usage

- Type `/exit` to close the terminal emulator.
- Type `/cdir` to switch to the C drive (C:/).
- Type `/ddir` to switch to the D drive (D:/).
- Type `/help` to display a list of available commands.
- Type `/log` to display the command history logs.

You can also enter any other valid CMD command, and it will be executed within the terminal emulator.

## Example

Here's an example of how to use the terminal emulator in your Java application:

```java
public class Main {
    public static void main(String[] args) {
        TerminalEmulator terminal = new TerminalEmulator();
        Scanner in = new Scanner(System.in);

        terminal.createTerminal();
        while (true) {
            String cmd = in.nextLine();
            terminal.sendCommand(cmd);
            if (!terminal.terminalProcess.isAlive()) {
                break;
            }
        }
    }
}
```

## Note

- Make sure to handle exceptions appropriately when using this terminal emulator to ensure graceful termination.

- This terminal emulator is intended for educational purposes and may not be suitable for all use cases. Use it responsibly and securely.

Enjoy experimenting with the terminal emulator in your Java projects!
