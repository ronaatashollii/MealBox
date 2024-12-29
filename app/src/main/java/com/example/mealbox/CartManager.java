package com.example.mealbox;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<CartItem> cartItems = new ArrayList<>();

    public static void addCartItem(CartItem item) {
        cartItems.add(item);
    }

    public static void removeCartItem(CartItem item) {
        cartItems.remove(item);
    }

    public static List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }
}

