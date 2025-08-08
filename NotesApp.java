import java.io.*;
import java.util.Scanner;

public class NotesApp {
    private static final String NOTES_FILE = "notes.txt";
    private static Scanner sc = new Scanner(System.in); //Creating new Scanner object to take input fro0m user

    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("=== Simple Notes App ===");
        
        while (true) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    viewNotes();
                    break;
                case 2:
                    addNote();
                    break;
                case 3:
                    deleteNotes();
                    break;
                case 4:
                    System.out.println("Exiting the Notes App. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("------------------------------");
        System.out.println("Operation Menu:");
        System.out.println("------------------------------");
        System.out.println("1. View Notes");
        System.out.println("2. Add Note");
        System.out.println("3. Delete All Notes");
        System.out.println("4. Exit");
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a number.");
            sc.next(); // consume the invalid input
            System.out.print(prompt);
        }
        return sc.nextInt();
    }

    private static void viewNotes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTES_FILE))) {
            System.out.println("\n=== Your Notes ===");
            String line;
            int noteNumber = 1;
            
            while ((line = reader.readLine()) != null) {
                System.out.println(noteNumber++ + ". " + line);
            }
            
            if (noteNumber == 1) {
                System.out.println("No notes found.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No notes found. File doesn't exist yet.");
        } catch (IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
        }
    }

    private static void addNote() {
        System.out.println("\nEnter your note (press Enter twice to finish):");
        sc.nextLine(); //It will consume the newline from previous input
        
        StringBuilder noteContent = new StringBuilder();
        String line;
        while (!(line = sc.nextLine()).isEmpty()) {
            noteContent.append(line).append("\n");
        }
        
        if (noteContent.length() == 0) {
            System.out.println("Note cannot be added because content was empty.");
            return;
        }
        
        try (FileWriter writer = new FileWriter(NOTES_FILE, true)) {
            writer.write(noteContent.toString().trim() + System.lineSeparator());
            System.out.println("Note added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing note: " + e.getMessage());
        }
    }

    private static void deleteNotes() {
        System.out.print("\nAre you sure you want to delete ALL notes? (y/n): ");
        sc.nextLine(); //It will consume the newline from previous input
        String confirmation = sc.nextLine();
        
        if (confirmation.equalsIgnoreCase("y")) {
            try (FileWriter writer = new FileWriter(NOTES_FILE, false)) {
                
                //Opening in overwrite mode will clear the file
                System.out.println("All notes have been deleted.");
            } catch (IOException e) {
                System.out.println("Error deleting notes: " + e.getMessage());
            }
        } else {
            System.out.println("Operation cancelled. No notes were deleted.");
        }
    }
}