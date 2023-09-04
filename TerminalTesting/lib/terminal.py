import subprocess
import os

while True:
    command = input()
    
    if command == '/exit':
        break
    
    if command.startswith("cd "):
        # Handle directory change using os.chdir()
        try:
            new_directory = command[3:]  # Extract the path from the 'cd' command
            os.chdir(new_directory)
            print(f"Changed directory to: {new_directory}")
        except FileNotFoundError:
            print(f"Directory not found: {new_directory}")
        except Exception as e:
            print(f"An error occurred: {e}")
    else:
        try:
            result = subprocess.run(command, shell=True, text=True, capture_output=True)
            if result.returncode != 0:
                print(result.stderr)
            else:
                print(result.stdout)
        except Exception as e:
            print(f"An error occurred: {e}")
