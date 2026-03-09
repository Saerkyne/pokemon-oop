# create Java classes based on input from user

move = input("Enter the name of the move: ")

type = input("Enter the type of the move: ")
category = input("Enter the category of the move (Physical/Special/Status): ")
power = input("Enter the power of the move (if applicable): ")
accuracy = input("Enter the accuracy of the move (if applicable): ")
pp = input("Enter the PP of the move: ")

# Create the Java class for the move
class_name = move.replace(" ", "")  # Remove spaces for class name
java_class = f"""package pokemonGame.moves;
import pokemonGame.Move;

public class {class_name} extends Move {{
    public {class_name}() {{
        super("{move}", {power}, "{type}",
        "{category}", {accuracy}, {pp});
    }}
}}
"""

# Create .java file for the move
file_name = f"{class_name}.java"

with open(f"c:\\Users\\jthubbard\\OneDrive - Randolph Community College\\Documents\\Coding\\OOP-GameTesting\\Pokemon-OOP\\src\\pokemonGame\\moves\\{file_name}", "w") as file:
    file.write(java_class)
print(f"{file_name} has been created.")