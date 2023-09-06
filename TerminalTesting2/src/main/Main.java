package main;

import java.util.Scanner;

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
