import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WestminsterShoppingGUI class represents the graphical user interface for the Westminster Shopping Centre.
 * It extends JFrame to create a window for the shopping application.
 */

public class WestminsterShoppingGUI extends JFrame {
    // Static table for displaying the shopping cart
    static JTable cartTable = new JTable();

    // Panels for organizing the GUI components
    JPanel topPanel = new JPanel();
    JPanel middlePanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel categoryPanel = new JPanel();

    // Table for displaying products
    JTable productTable = new JTable();

    // Dropdown menu for selecting product categories
    String[] category = {"All", "Electronics", "Clothing"};
    JComboBox<String> dropDown = new JComboBox<>(category);

    // List to store the product data obtained from the WestminsterShoppingManager
    static List<Product> productList = WestminsterShoppingManager.getProductList();

    // Labels for displaying product information
    JLabel productIDLabel;
    JLabel productCategoryLabel;
    JLabel nameLabel;
    JLabel sizeLabel;
    JLabel colorLabel;
    JLabel availableItemsLabel;
    JLabel brandLabel;
    JLabel warrantyPeriodLabel;

    /**
     * Constructor for WestminsterShoppingGUI.
     * Sets up the GUI components and initializes the window.
     */
    public WestminsterShoppingGUI() {
        setTitle("Westminster Shopping Centre");
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Adding panels to the main frame
        add(getBottom(), BorderLayout.SOUTH);
        add(getTop(), BorderLayout.NORTH);
        add(getCenter(), BorderLayout.CENTER);
        setVisible(true); // Make the GUI visible
    }

    /**
     * Generates the top panel of the WestminsterShoppingGUI, containing a "Shopping Cart" button and a dropdown menu
     * for selecting product categories.
     *
     * @return JPanel representing the top section of the GUI.
     */
    private JPanel getTop() {
        // Button for viewing the shopping cart
        JButton cartButton = new JButton("Shopping Cart");
        cartButton.addActionListener(e -> viewShoppingCart());

        // Panel for organizing cart-related components
        JPanel cartPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));
        cartPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        cartPanel.add(cartButton);

        // Panel for selecting product categories
        categoryPanel.setLayout(new FlowLayout());
        categoryPanel.add(new JLabel("Select Product Category "));
        categoryPanel.add(dropDown);

        // Adding both cart and category panels to the top panel
        topPanel.add(cartPanel);
        topPanel.add(categoryPanel);
        return topPanel;
    }

    /**
     * Opens a new GUI2 window, populates the shopping cart table, updates totals, and sets the window as visible.
     */
    private void viewShoppingCart() {
        GUI2 gui2 = new GUI2();
        gui2.populateCartTable();
        GUI2.updateTotals();
        gui2.setVisible(true);
    }

    /**
     * Generates the bottom panel of the WestminsterShoppingGUI, containing a detail panel for selected product details
     * and a button for adding the selected product to the shopping cart.
     *
     * @return JPanel representing the bottom section of the GUI.
     */
    private JPanel getBottom() {
        // Main bottom panel layout and border settings
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Panel for displaying selected product details
        JPanel detailPanel = new JPanel();
        detailPanel.setBorder(BorderFactory.createTitledBorder("Selected Product - Details"));
        detailPanel.setLayout(new GridLayout(0,2,10,10));

        // Labels for displaying product details
        productIDLabel = new JLabel();
        productCategoryLabel = new JLabel();
        nameLabel = new JLabel();
        sizeLabel = new JLabel();
        colorLabel = new JLabel();
        availableItemsLabel = new JLabel();
        brandLabel = new JLabel();
        warrantyPeriodLabel = new JLabel();

        // Adding labels and corresponding information to the detail panel
        detailPanel.add(new JLabel("Product Id: "));
        detailPanel.add(productIDLabel);

        detailPanel.add(new JLabel("Category: "));
        detailPanel.add(productCategoryLabel);

        detailPanel.add(new JLabel("Name: "));
        detailPanel.add(nameLabel);

        detailPanel.add(new JLabel("Size: "));
        detailPanel.add(sizeLabel);

        detailPanel.add(new JLabel("Colour: "));
        detailPanel.add(colorLabel);

        detailPanel.add(new JLabel("Items Available: "));
        detailPanel.add(availableItemsLabel);

        detailPanel.add(new JLabel("Brand: "));
        detailPanel.add(brandLabel);

        detailPanel.add(new JLabel("Warranty: "));
        detailPanel.add(warrantyPeriodLabel);

        // Adding the detail panel to the bottom panel
        bottomPanel.add(detailPanel, BorderLayout.CENTER);

        // Button for adding the selected product to the shopping cart
        JButton addToCart = new JButton("Add to Shopping Cart");

        addToCart.addActionListener(e -> addProductToCart());

        // Panel for organizing the "Add to Shopping Cart" button
        JPanel cartButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cartButton.add(addToCart);
        // Adding the cart button to the bottom panel
        bottomPanel.add(cartButton, BorderLayout.SOUTH);
        return bottomPanel;
    }

    /**
     * Adds the selected product from the product table to the shopping cart.
     * Updates GUI2 on the Event Dispatch Thread to reflect the changes.
     */
    private void addProductToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String selectedProductId = productTable.getValueAt(selectedRow, 0).toString();
            Product selectedProduct = findProductById(selectedProductId);
            if (selectedProduct != null) {
                ShoppingCart sc = ShoppingCart.getInstance();
                sc.addProduct(selectedProduct);

                // Update GUI2 on the Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                    GUI2.addProductToCart(selectedProduct);
                    GUI2.updateTotals();
                });
            } else {
                System.out.println("Product with ID " + selectedProductId + " not found.");
            }
        }
    }

    // Helper method to find a product by ID in the productList
    private Product findProductById(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null; // Product not found
    }

    /**
     * Configures and returns the center panel containing the productTable.
     * Initializes the productTable with products based on the selected category.
     * Listens for changes in the productTable selection and updates product details accordingly.
     *
     * @return The configured center panel.
     */
    private JPanel getCenter() {
        middlePanel.removeAll();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Initialize the productTable with products from the "All" category
        initializeTable("All") ;
        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add ActionListener to the dropDown to update the productTable based on the selected category
        dropDown.addActionListener(e -> updateProductTableByCategory());
        JScrollPane scrollPane = new JScrollPane(productTable);
        middlePanel.add(scrollPane);

        // Add ListSelectionListener to productTable to update product details when selection changes
        productTable.getSelectionModel().addListSelectionListener(selection -> {
            if (!selection.getValueIsAdjusting()) {
                updateProductDetails();
            }
        });

        return middlePanel;
    }

    /**
     * Updates the productTable based on the selected category from the drop-down menu.
     * Invokes the SwingUtilities on the Event Dispatch Thread to ensure proper GUI updates.
     */
    private void updateProductTableByCategory() {
        String chooseCategory = (String) dropDown.getSelectedItem();
        // Reinitialize the productTable with products from the selected category
        initializeTable(chooseCategory);

        // Invoke SwingUtilities to revalidate and repaint the middlePanel for proper updates
        SwingUtilities.invokeLater(() ->{
            middlePanel.revalidate();
            middlePanel.repaint();
        });
    }

    /**
     * Updates the product details labels based on the selected row in the productTable.
     * Retrieves the selected product from the productList using its ID and updates the labels accordingly.
     * Handles different label values based on the product type (Electronics or Clothing).
     * Clears the labels if no row is selected.
     */
    private void updateProductDetails() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String selectedProductId = productTable.getValueAt(selectedRow, 0).toString();
            Product selectedProduct = findProductById(productList, selectedProductId);

            if (selectedProduct != null) {
                // Update labels with selected product information
                productIDLabel.setText(selectedProduct.getProductId());
                nameLabel.setText(selectedProduct.getName());
                availableItemsLabel.setText(String.valueOf(selectedProduct.getAvailableItems()));
                productCategoryLabel.setText(selectedProduct instanceof Electronics ? "Electronics" : "Clothing");

                if (selectedProduct instanceof Electronics) {
                    // Update labels specific to Electronics
                    Electronics electronics = (Electronics) selectedProduct;
                    brandLabel.setText(electronics.getBrand());
                    warrantyPeriodLabel.setText(String.valueOf(electronics.getWarrantyPeriod()));
                    sizeLabel.setText("N/A"); // Not applicable for Electronics
                    colorLabel.setText("N/A"); // Not applicable for Electronics
                } else if (selectedProduct instanceof Clothing) {
                    // Update labels specific to Clothing
                    Clothing clothing = (Clothing) selectedProduct;
                    sizeLabel.setText(String.valueOf(clothing.getSize()));
                    colorLabel.setText(clothing.getColor());
                    brandLabel.setText("N/A");
                    warrantyPeriodLabel.setText("N/A");
                }
            }
        } else {
            // Clear the labels if no row is selected
            productIDLabel.setText("");
            nameLabel.setText("");
            availableItemsLabel.setText("");
            productCategoryLabel.setText("");
            sizeLabel.setText("");
            colorLabel.setText("");
            brandLabel.setText("");
            warrantyPeriodLabel.setText("");
        }
    }

    // Method to find a product by ID in the list
    private Product findProductById(java.util.List<Product> productList, String productID) {
        for (Product product : productList) {
            if (product.getProductId().equals(productID)) {
                return product;
            }
        }
        return null; // Product not found
    }

    private void initializeTable(String type) {
        System.out.println("Initialising Table!!!");

        // Check if the productList is not empty
        if (productList.isEmpty()) {
            System.out.println("Warning: productList is empty!");
            return; // Exit the method early if there are no products
        }

        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : productList) {
            System.out.println("Processing product: " + product.getName()); // Debugging statement

            // Check if the product instances are correct
            if (type.equals("All")) {
                filteredProducts.add(product);
            } else if (type.equals("Clothing") && product instanceof Clothing) {
                filteredProducts.add(product);
                System.out.println("Added Clothing: " + product.getName()); // Debugging statement
            } else if (type.equals("Electronics") && product instanceof Electronics) {
                filteredProducts.add(product);
                System.out.println("Added Electronics: " + product.getName()); // Debugging statement
            }
        }

        // Check if filteredProducts has items in it after the loop
        if (filteredProducts.isEmpty()) {
            System.out.println("Warning: No products were added to filteredProducts!");
        } else {
            System.out.println("Filtered products count: " + filteredProducts.size());
        }


        // Extract column names
        String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"};

        // Convert ArrayList to a 2D Object array for JTable data
        Object[][] data = new Object[filteredProducts.size()][columnNames.length];

        for (int i = 0; i < filteredProducts.size(); i++) {
            Product product = filteredProducts.get(i);
            data[i][0] = product.getProductId();
            data[i][1] = product.getName();
            data[i][2] = getProductCategory(product); // Ensure this method is defined and returns String
            data[i][3] = String.valueOf(product.getPrice()); // Convert price to String if necessary
            data[i][4] = getInfo(product); // Ensure this method is defined and returns String
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            // This method will make the cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        SwingUtilities.invokeLater(() -> {
            productTable.setModel(tableModel);

            // Apply the custom renderer to each column
            AvailabilityCellRenderer renderer = new AvailabilityCellRenderer(productList);
            for (int i = 0; i < productTable.getColumnCount(); i++) {
                productTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
            middlePanel.revalidate();
            middlePanel.repaint();
        });
    }

    private String getInfo(Product product) {
        // Your logic to get the product info as a String
        if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;
            return "Brand: " + electronics.getBrand() + ", Warranty: " + electronics.getWarrantyPeriod();
        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            return "Size: " + clothing.getSize() + ", Color: " + clothing.getColor();
        } else {
            return "No Information";
        }
    }
    private String getProductCategory(Product product) {
        // Your logic to get the product category as a String
        return product instanceof Electronics ? "Electronics" : "Clothing";
    }

    /**
     * Custom cell renderer to handle the display of availability in the table cells.
     * Changes the background color based on the availability of the product.
     */
    static class AvailabilityCellRenderer extends DefaultTableCellRenderer {
        private List<Product> productList;

        public AvailabilityCellRenderer(List<Product> productList) {
            this.productList = productList;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String productId = (String) table.getValueAt(row, 0); // Get the product ID from the first column
            Product product = findProductById(productId);

            if (product != null && product.getAvailableItems() < 3) {
                c.setBackground(new Color(255, 102, 102)); // Red background for low availability
            } else {
                c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            }

            return c;
        }

        private Product findProductById(String productId) {
            for (Product p : productList) {
                if (p.getProductId().equals(productId)) {
                    return p;
                }
            }
            return null; // Return null if product not found
        }
    }


}