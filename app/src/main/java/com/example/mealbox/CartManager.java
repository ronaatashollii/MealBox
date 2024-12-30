package com.example.mealbox;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<CartItem> cartItems = new ArrayList<>();

    public static synchronized void addCartItem(CartItem item) {
        if (!cartItems.contains(item)) {
            cartItems.add(item);
        }
    }

    public static synchronized void removeCartItem(CartItem item) {
        cartItems.remove(item);
    }


    public static synchronized List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }


    public static synchronized void clearCart() {
        cartItems.clear();
    }


    public static synchronized int getTotalItems() {
        return cartItems.size();
    }


    public static synchronized double getTotalCost() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getPrice();
        }
        return total;
    }
}

