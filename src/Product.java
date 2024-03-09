public abstract class Product {
    private String productId;
    private String name;
    private int availableItems;
    private double price;

    // Constructor for creating a Product object
    public Product(String productId, String name, int availableItems, double price) {
        this.productId = productId;
        this.name = name;
        this.availableItems = availableItems;
        this.price = price;
    }

    // Getter method for retrieving the product ID
    public String getProductId() {
        return productId;
    }

    // Setter method for updating the product ID
    public void setProductId(String productId) {
        this.productId = productId;
    }

    // Getter method for retrieving the product name
    public String getName() {
        return name;
    }

    // Setter method for updating the product name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for retrieving the number of available items
    public int getAvailableItems() {
        return availableItems;
    }

    // Setter method for updating the number of available items
    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    // Getter method for retrieving the price of the product
    public double getPrice() {
        return price;
    }

    // Setter method for updating the price of the product
    public void setPrice(double price) {
        this.price = price;
    }

    // Override the toString method to provide a string representation of the Product object
    @Override
    public String toString() {
        return "Product{" +
                "ProductId:'" + productId + '\'' +
                ", Product Name:'" + name + '\'' +
                ", No of Available Items:" + availableItems +
                ", Price:" + price +
                '}';
    }

    // Abstract method to convert the Product object to a CSV-formatted string
    public abstract String toCSVString();


}
