package week7.debug;

import java.util.Map;
import java.util.Set;

public class Shop {
    private Map<Product, Integer> shoppingCart = new java.util.HashMap<>();

    private Set<Product> products = Set.of(
            new Product("Smartphone Pro", 1299.00, "Telefoon"),
            new Product("Toetsenbord Mechanisch", 35.50, "Accessoires"),
            new Product("Webcam HD", 45.99, "Accessoires"),
            new Product("Monitor 27-inch", 249.00, "Elektronica"),
            new Product("Muis Ergonomisch", 35.50, "Accessoires"),
            new Product("USB-Stick 64GB", 15.00, "Opslag"),
            new Product("Printer Laser", 199.99, "Kantoor")
    );

    public Product getProduct(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public void addToShoppingCart(Product product, int quantity) {
        shoppingCart.put(product, quantity);
    }

    public double getTotalPriceOfShoppingCart() {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry: shoppingCart.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            totalPrice += product.getPrice() * quantity;
        }
        return totalPrice;
    }

    public void clearShoppingCart() {
        shoppingCart.clear();
    }

    public int getNumberOfProductsInShoppingCart() {
        return shoppingCart.size();
    }
}
