package main;

import java.io.*;

public class TerminalEmulator {
    private BufferedReader inputReader;
    private BufferedWriter outputWriter;
    private Thread outputThread;
    private Process pythonProcess;

    public void runTerminal() {
        try {
            // Start the Python script as a separate process
            pythonProcess = Runtime.getRuntime().exec("python lib/terminal.py");

            // Get input and output streams for communication with the Python process
            inputReader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()));
            outputWriter = new BufferedWriter(new OutputStreamWriter(pythonProcess.getOutputStream()));

            // Create a thread to read and display the Python process's output
            outputThread = new Thread(() -> {
                try {
                    String pythonOutput;
                    while ((pythonOutput = inputReader.readLine()) != null) {
                        System.out.println(pythonOutput);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            outputThread.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void sendCommand(String command) {
        try {
            // Send the user's input to the Python process
            outputWriter.write(command);
            outputWriter.newLine();
            outputWriter.flush();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void closeTerminal() {
        try {
            if (pythonProcess != null) {
                // Send an exit command to the Python process
                sendCommand("/exit");

                // Wait for the Python process to exit
                pythonProcess.waitFor();
            }

            if (outputThread != null) {
                // Wait for the output thread to finish
                outputThread.join();
            }

            // Close the inputReader and outputWriter
            if (inputReader != null) {
                inputReader.close();
            }

            if (outputWriter != null) {
                outputWriter.close();
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    // Helper method to close all Python processes
    public void closeAllPythonProcesses() {
        try {
            // Get the list of all running processes
            ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
            Process process = processBuilder.start();

            // Read the output of the tasklist command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the process is a Python process
                if (line.toLowerCase().contains("python.exe")) {
                    // Get the process ID (PID) of the Python process
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 2) {
                        String pid = parts[1];
                        // Terminate the Python process
                        terminateProcess(pid);
                    }
                }
            }

            // Close the reader
            reader.close();

        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    // Helper method to terminate a process by its PID
    private void terminateProcess(String pid) {
        try {
            // Use the taskkill command to terminate the process
            ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/F", "/PID", pid);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error terminating process with PID " + pid + ": " + e.toString());
        }
    }
}


//D: && cd D:\AI\ChatBots\llama-master-63d2046-bin-win-avx-x64 && run.bat
//echo "hi"
