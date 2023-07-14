package com.webshop.assignment.service;

import com.webshop.assignment.shopping.Order;
import com.webshop.assignment.shopping.ShoppingCart;

public interface ShoppingCartService {

    public ShoppingCart getShoppingCartById(String cartId);

    public void saveShoppingCart(ShoppingCart cart);

    public void updateShoppingCart(ShoppingCart cart);

    public void deleteShoppingCart(ShoppingCart cart);

    public void addItemToCart(String cartId, String productId, Integer quantity);

    public void removeItemFromCart(String cartId, String productId);

    public void updateItemQuantity(String cartId, String productId, Integer quantity);

    public Order checkout(String cartId);
}