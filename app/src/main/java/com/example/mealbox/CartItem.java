package com.example.mealbox;

public class CartItem {
    private String name;
    private double price;
    private int imageRes;

    public CartItem(String name, double price, int imageRes) {
        this.name = name;
        this.price = price;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImageRes() {
        return imageRes;
    }
}
