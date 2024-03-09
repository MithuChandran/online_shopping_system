// Name: Mithuna Chandrasekaran
// Student ID: w1956134
// Date:13/01/2024
// References : https://www.w3schools.com/java/default.asp, videos and lecture slides.

import java.util.Scanner;

public class Main {
    static WestminsterShoppingManager manager = new WestminsterShoppingManager();
    static WestminsterShoppingGUI shoppingGUI;

    public static void main(String[] args) {
        manager.loadList(); // Load the product list from a file
        mainMenu(); // Display the main menu
    }

    // Main menu function allowing the user to choose between Client, Manager, or Exit
    public static void mainMenu(){
        boolean done = true;
        Scanner scanner = new Scanner(System.in);
        while (done) {
            try {
                System.out.println("1.Client");
                System.out.println("2.Manager");
                System.out.println("0.Exit");
                System.out.print("Select your type:");
                int type = scanner.nextInt();
                switch (type) {
                    case (1) -> {
                        shoppingGUI = new WestminsterShoppingGUI(); // Launch the GUI for the client
                        done = false;
                    }
                    case (2) -> {
                        manager.displayMenu(); // Launch the console menu for the manager
                    }
                    case(0)-> {
                        System.out.println("***********EXITING PROGRAM***********");
                        done = false; // Exit the loop
                    }
                    default -> {
                        System.out.println("Invalid option, Please try again!!!\n");
                        System.out.println();
                    }
                }
            } catch (Exception e) {
                System.out.println("### Invalid Option ###");       // Catch any exception thrown by the code in the try block and print an error message
                System.out.println();
                scanner.nextLine(); // Consume the remaining newline character
            }
        }
    }
}

