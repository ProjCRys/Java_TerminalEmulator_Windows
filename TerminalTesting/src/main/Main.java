package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        TerminalEmulator terminal = new TerminalEmulator();
        Scanner in = new Scanner(System.in);
        terminal.runTerminal();
        while (true) {
            String command = in.nextLine();
            if (command.equalsIgnoreCase("/exit")) {
                terminal.closeTerminal();
                terminal.closeAllPythonProcesses();
                System.out.println("Closing terminal...");
                break;
            }
            terminal.sendCommand(command);
        }
    }
}
