package main;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class TerminalEmulator {
    private static LinkedList<String> logs = new LinkedList<>();
    private static InputStream terminalOutputReader;
    private static OutputStream terminalInputWriter;
    public static Process terminalProcess = null;
    
    public void runTerminal() {
        Scanner in = new Scanner(System.in);
        terminalProcess = null;

        try {
            terminalProcess = openTerminal();
            terminalOutputReader = terminalProcess.getInputStream();
            terminalInputWriter = terminalProcess.getOutputStream();

            // Read and display terminal output in a separate thread
            Thread outputThread = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = terminalOutputReader.read(buffer)) != -1) {
                        String terminalOutput = new String(buffer, 0, bytesRead);
                        logs.add(terminalOutput); // Log the terminal output
                        System.out.print(terminalOutput); // Display terminal output
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            outputThread.start();

            // Starting directory to C Drive
            sendInputToTerminal(terminalInputWriter, "cd /d C:/");

            while (true) {
                String command = in.nextLine();

                if (command.equalsIgnoreCase("/exit")) {
                    break;
                } else if (command.equalsIgnoreCase("/cdir")) {
                    sendInputToTerminal(terminalInputWriter, "cd /d C:/");
                } else if (command.equalsIgnoreCase("/ddir")) {
                    sendInputToTerminal(terminalInputWriter, "cd /d D:/");
                } else if (command.equalsIgnoreCase("/help")) {
                    System.err.println("Available commands:\n"
                            + "/exit   - Close the terminal\n"
                            + "/cdir   - Switch to the C drive\n"
                            + "/ddir   - Switch to the D drive\n"
                            + "/help   - Display this help message\n"
                            + "/log    - Display logs");
                    if (!logs.isEmpty()) {
                        System.out.print(logs.get(logs.size() - 1));
                    }
                } else if (command.equalsIgnoreCase("/log")) {
                    // Display output log
                    System.err.println("History Logs:");
                    for (int i = 0; i < logs.size(); i++) {
                        if (i == logs.size() - 1) {
                            // Print the last log entry without System.err
                            System.out.print(logs.get(i));
                        } else {
                            System.err.print(logs.get(i));
                        }
                    }
                } else {
                    sendInputToTerminal(terminalInputWriter, command);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (terminalProcess != null) {
                terminalProcess.destroy();
            }
        }
    }
    private static Process openTerminal() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe");
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }
    private static void sendInputToTerminal(OutputStream terminalInputWriter, String command) throws IOException {
        if (command.charAt(0) == '/') {
            if (command.equalsIgnoreCase("/exit")) {
                if (terminalProcess != null) {
                    terminalProcess.destroy();
                }
            } else if (command.equalsIgnoreCase("/cdir")) {
                sendInputToTerminal(terminalInputWriter, "cd /d C:/");
            } else if (command.equalsIgnoreCase("/ddir")) {
                sendInputToTerminal(terminalInputWriter, "cd /d D:/");
            } else if (command.equalsIgnoreCase("/help")) {
                System.err.println("Available commands:\n"
                        + "/exit   - Close the terminal\n"
                        + "/cdir   - Switch to the C drive\n"
                        + "/ddir   - Switch to the D drive\n"
                        + "/help   - Display this help message\n"
                        + "/log    - Display logs");
                if (!logs.isEmpty()) {
                    System.out.print(logs.get(logs.size() - 1));
                }
            } else if (command.equalsIgnoreCase("/log")) {
                // Display output log
                System.err.println("History Logs:");
                for (int i = 0; i < logs.size(); i++) {
                    if (i == logs.size() - 1) {
                        // Print the last log entry without System.err
                        System.out.print(logs.get(i));
                    } else {
                        System.err.print(logs.get(i));
                    }
                }
            } else {
                System.err.println("Invalid command");
                if (!logs.isEmpty()) {
                    System.out.print(logs.get(logs.size() - 1));
                }
            } 
        }else{
            terminalInputWriter.write(command.getBytes());
            terminalInputWriter.write('\n');
            terminalInputWriter.flush();
        }
    }
    
    //run command parts
    public void createTerminal() {
        try {
            terminalProcess = openTerminal();
            terminalOutputReader = terminalProcess.getInputStream();
            terminalInputWriter = terminalProcess.getOutputStream();

            // Read and display terminal output in a separate thread
            Thread outputThread = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = terminalOutputReader.read(buffer)) != -1) {
                        String terminalOutput = new String(buffer, 0, bytesRead);
                        logs.add(terminalOutput); // Log the terminal output
                        System.out.print(terminalOutput); // Display terminal output
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            outputThread.start();

            // Starting directory to C Drive
            sendCommand("cd /d C:/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendCommand(String command) {
        try {
            sendInputToTerminal(terminalInputWriter, command);
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }
    public void terminateTerminal() {
        if (terminalProcess != null) {
            terminalProcess.destroy();
        }
    }
}
