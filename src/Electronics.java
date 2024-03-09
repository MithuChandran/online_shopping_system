public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String name, int availableItems, double price, String brand, int warrentyPeriod) {
        super(productId, name, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Electronics{" +super.toString()+
                "Brand:'" + brand + '\'' +
                ", Warranty Period:" + warrantyPeriod +
                '}';
    }
    public String getInfo() {
        return "Brand: '" + brand + '\'' +
                ", Warranty Period:" + warrantyPeriod;
    }

    public String toCSVString() {
        return "Electronics," + getProductId() + "," + getName() + "," + getAvailableItems() +
                "," + getPrice() + "," + brand + "," + warrantyPeriod;
    }

    public static Product fromCSVString(String csvString) {
        String[] parts = csvString.split(",");
        if (parts.length != 7 || !parts[0].equals("Electronics")) {
            throw new IllegalArgumentException("Invalid Electronics CSV string");
        }

        String productID = parts[1];
        String productName = parts[2];
        int noOfAvailableItems = Integer.parseInt(parts[3]);
        double productPrice = Double.parseDouble(parts[4]);
        String brand = parts[5];
        int warrantyPeriod = Integer.parseInt(parts[6]);

        return new Electronics(productID, productName, noOfAvailableItems, productPrice, brand, warrantyPeriod);
    }
}
