package shopping;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private String id;
    private Map<String, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    public void addItem(String productId, Integer quantity) {
        items.put(productId, quantity);
    }

    public void removeItem(String productId) {
        items.remove(productId);
    }

    public void updateQuantity(String productId, Integer quantity) {
        items.put(productId, quantity);
    }

    public Map<String, Integer> getContent() {
        return items;
    }

    public Order checkout() {
        // Implementation for checkout logic
        return new Order();
    }
}