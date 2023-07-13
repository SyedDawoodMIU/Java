package service;

import dao.ProductDAO;
import dao.ShoppingCartDAO;
import shopping.ShoppingCart;

public class ShoppingCartService {
    private ShoppingCartDAO shoppingCartDAO;
    private ProductDAO productDAO;

    public ShoppingCartService() {
        shoppingCartDAO = new ShoppingCartDAO();
        productDAO = new ProductDAO();
    }

    public ShoppingCart getShoppingCartById(String cartId) {
        return shoppingCartDAO.getById(cartId);
    }

    public void saveShoppingCart(ShoppingCart cart) {
        shoppingCartDAO.save(cart);
    }

    public void updateShoppingCart(ShoppingCart cart) {
        shoppingCartDAO.update(cart);
    }

    public void deleteShoppingCart(ShoppingCart cart) {
        shoppingCartDAO.delete(cart);
    }

    public void addItemToCart(String cartId, String productId, Integer quantity) {
        ShoppingCart cart = shoppingCartDAO.getById(cartId);
        if (cart != null) {
            cart.addItem(productId, quantity);
        }
    }

    public void removeItemFromCart(String cartId, String productId) {
        ShoppingCart cart = shoppingCartDAO.getById(cartId);
        if (cart != null) {
            cart.removeItem(productId);
        }
    }

    public void updateItemQuantity(String cartId, String productId, Integer quantity) {
        ShoppingCart cart = shoppingCartDAO.getById(cartId);
        if (cart != null) {
            cart.updateQuantity(productId, quantity);
        }
    }

    public void checkout(String cartId) {
        ShoppingCart cart = shoppingCartDAO.getById(cartId);
        if (cart != null) {
            // Call the necessary methods to add payment, customer info, addresses, and place the order
            // using the appropriate service classes
        }
    }
}