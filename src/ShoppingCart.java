import java.util.ArrayList;
import java.util.List;

// Represents a shopping cart containing various products
public class ShoppingCart {
    private static ShoppingCart instance;
    private final List<CartItem> cartItems;

    // Private constructor to ensure a single instance of the shopping cart
    public ShoppingCart() {
        cartItems = new ArrayList<>();
    }

    // Singleton pattern: Ensures only one instance of ShoppingCart exists
    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }
    // Adds a product to the shopping cart or increments its quantity if already present
    public void addProduct(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId().equals(product.getProductId())) {
                item.incrementQuantity();
                return;
            }
        }
        cartItems.add(new CartItem(product, 1));
    }

    // Removes a product from the shopping cart or decrements its quantity
    public void removeProduct(Product product) {
        CartItem toRemove = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId().equals(product.getProductId())) {
                item.decrementQuantity();
                if (item.getQuantity() <= 0) {
                    toRemove = item;
                }
                break;
            }
        }
        if (toRemove != null) {
            cartItems.remove(toRemove);
        }
    }

    // Calculates the total cost of items in the shopping cart, applying discounts
    public double calculateTotalCost() {
        double totalCost = cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();

        double firstPurchaseDiscount = isFirstPurchase() ? totalCost * 0.1 : 0.0;
        double categoryDiscount = calculateCategoryDiscount();
        return totalCost - (firstPurchaseDiscount + categoryDiscount);
    }

    // Returns a copy of the list of cart items to prevent external modification
    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems); // Return a copy of the list to prevent external modification
    }

    // Calculates the discount based on the number of Electronics and Clothing items in the cart
    public double calculateCategoryDiscount() {
        long countElectronics = cartItems.stream()
                .filter(item -> item.getProduct() instanceof Electronics)
                .count();

        long countClothing = cartItems.stream()
                .filter(item -> item.getProduct() instanceof Clothing)
                .count();

        if (countElectronics >= 3 || countClothing >= 3) {
            return calculateSubtotal() * 0.2; // 20% discount
        }

        return 0.0;
    }

    // Calculates the subtotal of all items in the cart
    public double calculateSubtotal() {
        return cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    // Determines if this is the user's first purchase
    private boolean isFirstPurchase() {
        // Implement the logic to determine this is the first purchase
        return false;
    }

    // Calculates the discount for the user's first purchase (currently not implemented)
    public double calculateFirstPurchaseDiscount() {
        return 0;
    }

    // Represents an item in the shopping cart with a product and quantity
    public static class CartItem {
        private final Product product;
        private int quantity;

        // Constructor for creating a cart item with a product and initial quantity
        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        // Increments the quantity of the item
        public void incrementQuantity() {
            this.quantity++;
        }

        // Decrements the quantity of the item, ensuring it doesn't go below 0
        public void decrementQuantity() {
            if (this.quantity > 0) {
                this.quantity--;
            }
        }

        // Gets the current quantity of the item
        public int getQuantity() {
            return this.quantity;
        }

        // Gets the product associated with the item
        public Product getProduct() {
            return product;
        }

        // Calculates the total price of the item (quantity * price)
        public double getTotalPrice() {
            return this.quantity * product.getPrice(); // Assuming getPrice() method in Product
        }
    }
}
