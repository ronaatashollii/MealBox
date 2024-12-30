package com.example.mealbox;

public class CartItem {
    private String name;
    private double price;
    private int imageRes;
    private String description;
    private String ingredients;

    public CartItem(String name, double price, int imageRes, String description, String ingredients) {
        this.name = name;
        this.price = price;
        this.imageRes = imageRes;
        this.description = description;
        this.ingredients = ingredients;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
