import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

// Represents a shopping manager implementing the ShoppingManager interface
public class WestminsterShoppingManager implements ShoppingManager {
    public static List<Product> productList; // List to store products

    // Constructor to initialize the productList as an ArrayList
    public WestminsterShoppingManager() {
        productList = new ArrayList<>();
    }

    // Displays the main menu for the shopping manager
    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean done = true;
        int option;

        // Displaying the welcome message and menu options
        System.out.println("\n ***WELCOME TO THE WESTMINSTER SHOPPING MANAGER***");
        String z = "-";
        System.out.println(z.repeat(50));
        while (done) {
            try {
                // Presenting menu options to the user
                System.out.println("1) Add new product");
                System.out.println("2) Delete product");
                System.out.println("3) Print product information");
                System.out.println("4) Save to file");
                System.out.println("5) Load from file");
                System.out.println("0) Quit");
                String y = "-";
                System.out.println(z.repeat(50));
                System.out.print("Enter option:");
                option = scanner.nextInt();

                // Switch case to handle user input
                switch (option) {
                    case 1 -> addProduct();
                    case 2 -> deleteProduct();
                    case 3 -> printList();
                    case 4 -> {
                        saveList();
                        System.out.println("Successfully wrote to the file!!!");
                        System.out.println();
                    }
                    case 5 -> loadList();
                    case 0 -> {
                        System.out.println("***********EXITING PROGRAM***********");
                        done = false;
                        System.out.println();
                    }
                    default -> System.out.println("Invalid option, Please try again!!!\n");
                }
            } catch (Exception e) {
                System.out.println("### Invalid Input ###");       // Catch any exception thrown by the code in the try block and print an error message
                System.out.println();
                scanner.nextLine();
            }
        }
    }

    // Adds a new product to the productList
        @Override
        public void addProduct() {
            // Check if the maximum product limit has been reached (50 products)
            if(productList.size()>=50){
                System.out.println("Maximum product limit reached. Cannot add more products.");
                return;
            }

            // Scanner to take user input
            Scanner scanner = new Scanner(System.in);
            // Initialize type as an empty string
            String type = "";
            // Loop to prompt the user until a valid product type (Electronics or Clothing) is entered
            while (!type.equalsIgnoreCase("Electronics") && !type.equalsIgnoreCase("Clothing")) {
                System.out.print("Enter product type (Electronics or Clothing): ");
                type = scanner.nextLine();
                // Check if the entered type is not valid, and display an error message
                if (!type.equalsIgnoreCase("Electronics") && !type.equalsIgnoreCase("Clothing")) {
                    System.out.println("Invalid type. Please enter a valid product type.");
                }
            }

            // Determine the product ID prefix based on the product type
            String productIdPrefix = type.equalsIgnoreCase("Electronics") ? "E" : "C";
            boolean validId = false;
            String productId = "";

            // Loop to prompt the user until a valid product ID is entered
            while (!validId) {
                System.out.print("Enter the product ID (maximum two numbers): " + productIdPrefix);
                String remainingID = scanner.nextLine();
                // Check if the remaining part of the ID consists of 1 or 2 digits
                if (remainingID.matches("\\d{1,2}")) {
                    productId = productIdPrefix + remainingID;
                    // Check if the product with the entered ID already exists
                    if (!productExists(productId)) {
                        validId = true;
                    } else {
                        System.out.println("Product with this ID already exists. Enter a new ID:");
                    }
                } else {
                    System.out.println("Invalid ID. You can only add up to two numbers for the ID.");
                }
            }

            String productName = "";
            boolean validName = false;

            // Loop to prompt the user until a valid product name containing only letters is entered
            while (!validName) {
                System.out.print("Enter product name: ");
                productName = scanner.nextLine();

                // Check if the product name contains only letters and spaces
                if (productName.matches("^[a-zA-Z\\s]+$")) {
                    validName = true;
                } else {
                    System.out.println("Invalid name. The name should contain only letters.");
                }
            }

            double price = 0;
            boolean validPrice = false;
            // Loop to prompt the user until a valid numeric product price is entered
            while (!validPrice) {
                System.out.print("Enter product price: ");
                try {
                    price = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline left by nextDouble()
                    validPrice = true;
                } catch (InputMismatchException e) {
                    // Catch an exception if the entered value is not a valid double
                    System.out.println("Invalid input for price. Please enter a numeric value.");
                    scanner.nextLine(); // Clear the buffer
                }
            }

            // Check if the product type is Electronics
            if (type.equalsIgnoreCase("Electronics")) {
                String brand = "";
                // Loop to prompt the user until a valid brand containing only letters is entered
                while (true) {
                    System.out.print("Enter brand:");
                    brand = scanner.nextLine();
                    // Check if the brand contains only letters and spaces
                    if (brand.matches("^[a-zA-Z\\s]+$")) {
                        break;
                    } else {
                        System.out.println("Invalid brand. The brand should contain only letters.");
                    }
                }

                int warrantyPeriod = 0;
                // Loop to prompt the user until a valid numeric warranty period is entered
                while (true) {
                    System.out.print("Enter warranty period (in months):");
                    if (scanner.hasNextInt()) {
                        warrantyPeriod = scanner.nextInt();
                        scanner.nextLine(); // Consume newline after number
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a number for warranty period.");
                        scanner.nextLine(); // Clear the incorrect input
                    }
                }

                // Create an instance of Electronics and add it to the productList
                Electronics electronics = new Electronics(productId, productName, warrantyPeriod, price, brand, warrantyPeriod);
                // Check if the product with the same ID already exists
                if (!productExists(productId)) {
                        productList.add(electronics);
                        System.out.println("Electronics product added successfully!!!");
                        System.out.println();
                    } else {
                        System.out.println("A product with this ID already exists.");
                        System.out.println();
                    }
            }else if (type.equalsIgnoreCase("Clothing")) {
                int availableItems = 0;
                // Loop to prompt the user until a valid numeric value for available items is entered
                while (true) {
                    System.out.print("Enter Clothing Available Items: ");
                    if (scanner.hasNextInt()) {
                        availableItems = scanner.nextInt();
                        scanner.nextLine(); // Consume newline after number
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a number for available items.");
                        scanner.nextLine(); // Clear the incorrect input
                    }
                }

                int size = 0;
                // Loop to prompt the user until a valid numeric value for size is entered
                while (true) {
                    System.out.print("Enter Clothing Size: ");
                    if (scanner.hasNextInt()) {
                        size = scanner.nextInt();
                        scanner.nextLine(); // Consume newline after number
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a number for size.");
                        scanner.nextLine(); // Clear the incorrect input
                    }
                }

                String color = "";
                // Loop to prompt the user until a valid color containing only letters is entered
                while (true) {
                    System.out.print("Enter Clothing Color: ");
                    color = scanner.nextLine();
                    // Check if the color contains only letters and spaces
                    if (color.matches("^[a-zA-Z\\s]+$")) {
                        break;
                    } else {
                        System.out.println("Invalid color. The color should contain only letters.");
                    }
                }
                // Create an instance of Clothing using the entered details and add it to the productList
                Clothing clothing = new Clothing(productId, productName, availableItems, price, size, color);
                // Add the clothing product to the productList if it does not already exist
                productList.add(clothing);
                System.out.println("Clothing product added successfully!!!");
                System.out.println();
            }
        }

    // Method to check if a product with the given product ID already exists in the productList
    private boolean productExists(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    // Override method to delete a product from the productList
        @Override
        public void deleteProduct() {
            Scanner scanner = new Scanner(System.in);
            // Prompt the user to enter the product type (Electronics or Clothing) to delete
            System.out.print("Enter product type which you are going to delete (Electronics or Clothing):");
            String type = scanner.nextLine();

            // Validate the entered product type until a valid one is provided
            while (!type.equalsIgnoreCase("Electronics") && !type.equalsIgnoreCase("Clothing")) {
                System.out.print("Invalid type. Please enter a valid product type (Electronics or Clothing):");
                type = scanner.nextLine();
            }

            // Determine the product ID prefix based on the product type
            String productIdPrefix = type.equalsIgnoreCase("Electronics") ? "E" : "C";
            boolean validId = false;
            String productId = "";
            // Validate the entered product ID until a valid one is provided
            while (!validId) {
                System.out.print("Enter the delete product ID: " + productIdPrefix);
                String remainingID = scanner.nextLine();
                // Check if the entered ID has 1 or 2 digits
                if (remainingID.matches("\\d{1,2}")) {
                    productId = productIdPrefix + remainingID;
                    // Find the product to remove from the productList
                    Product productToRemove = null;
                    for (Product product : productList) {
                        if (product.getProductId().equals(productId)) {
                            productToRemove = product;
                            break;
                        }
                    }

                    // Check if the product was found and removed from the productList
                    if (productToRemove != null) {
                        productList.remove(productToRemove);
                        System.out.println("Deleted product: " + productToRemove);
                        System.out.println("Total number of products left in the system: " + productList.size());
                        System.out.println();
                        break;
                    } else {
                        System.out.println("Product not found.");
                        System.out.println();
                        break;
                    }

                } else {
                    System.out.println("Invalid ID. You can only add up to two numbers for the ID.");
                }
            }
        }

        @Override
        public void printList() {
            // Check if the productList is empty
                if (productList.isEmpty()) {
                    System.out.println("The product list is currently empty.");
                    return;
                }
            // Sort the productList based on the product IDs
                productList.sort(Comparator.comparing(Product::getProductId));
            // Iterate over the sorted productList and print each product's information
                for (Product product : productList) {
                    // Determine the product type (Electronics or Clothing)
                    String productType = product instanceof Electronics ? "Electronics" : "Clothing";
                    // Print the product type and information
                    System.out.println("Type: " + productType + ", " + product);
                    System.out.println();
                }
            }


    public void saveList() {
        try (PrintWriter out = new PrintWriter("products.txt")) {
            // Iterate over each product in the productList
            for (Product product : productList) {
                // Write the CSV representation of each product to the file
                out.println(product.toCSVString());
            }
            // Notify the user that the product list was saved successfully
            System.out.println("Product list saved successfully.");
        } catch (IOException e) {
            // Handle IOException by notifying the user of the error
            System.out.println("An error occurred while saving the product list.");
        }
    }

    // Method to load the product list from a file
    public void loadList() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            // Iterate through each line in the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Product product = null;

                // Check if the line contains product information
                if (parts.length > 0) {
                    // Identify the product type and create an instance accordingly
                    switch (parts[0]) {
                        case "Electronics":
                            product = Electronics.fromCSVString(line);
                            break;
                        case "Clothing":
                            product = Clothing.fromCSVString(line);
                            break;
                        // Notify the user if an unknown product type is encountered
                        default:
                            System.out.println("Unknown product type: " + parts[0]);
                            break;
                    }

                    // Add the product to the productList if it was successfully created
                    if (product != null) {
                        productList.add(product);
                    }
                }
            }
            // Display a message indicating a successful load from the file
            String z = "-";
            System.out.println(z.repeat(50));
            System.out.println("        LOAD FROM FILE");
            System.out.println("   File loaded successfully");
            System.out.println(z.repeat(50));
            System.out.println();
        } catch (IOException e) {
            // Handle IOException by notifying the user of the error
            System.out.println("An error occurred while loading the product list: " + e.getMessage());
        }
    }

    // Method to retrieve the current product list
        public static List<Product> getProductList(){
            return productList;
        }

}
