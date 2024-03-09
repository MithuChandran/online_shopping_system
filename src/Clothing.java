public class Clothing extends Product{
    private int size;
    private String color;

    // Constructor for creating a Clothing object
    public Clothing(String productId, String name, int availableItems, double price, int size, String color) {
        super(productId, name, availableItems, price); // Call the constructor of the superclass (Product)
        this.size = size;
        this.color = color;
    }

    // Getter method for retrieving the size of the clothing
    public int getSize() {
        return size;
    }

    // Setter method for updating the size of the clothing
    public void setSize(int size) {
        this.size = size;
    }

    // Getter method for retrieving the color of the clothing
    public String getColor() {
        return color;
    }

    // Setter method for updating the color of the clothing
    public void setColor(String color) {
        this.color = color;
    }

    // Override the toString method to provide a string representation of the Clothing object
    @Override
    public String toString() {
        return "Clothing{" +super.toString()+
                "Size:'" + size + '\'' +
                ", Color:'" + color + '\'' +
                '}';
    }

    // Custom method to get information specific to Clothing
    public String getInfo() {
        return "size: " + size +
                ", colour: '" + color;
    }

    // Convert Clothing object to a CSV-formatted string
    public String toCSVString() {
        return "Clothing," + getProductId() + "," + getName() + "," + getAvailableItems() +
                "," + getPrice() + "," + size + "," + getColor();
    }

    // Convert a CSV-formatted string to a Clothing object
    public static Product fromCSVString(String csvString) {
        String[] parts = csvString.split(",");
        if (parts.length != 7 || !parts[0].equals("Clothing")) {
            throw new IllegalArgumentException("Invalid Clothing CSV string");
        }

        // Extracting information from CSV string parts
        String productID = parts[1];
        String productName = parts[2];
        int noOfAvailableItems = Integer.parseInt(parts[3]);
        double productPrice = Double.parseDouble(parts[4]);
        int size = Integer.parseInt(parts[5]);
        String colour = parts[6];

        // Creating and returning a new Clothing object
        return new Clothing(productID, productName, noOfAvailableItems, productPrice, size, colour);
    }
}
