package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private Map<Integer, String> buttonToProductMap;
    private Map<String, ProductDetails> productDetailsMap;
    private Map<String, Integer> productImageMap;
    private ImageButton viewCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        viewCartButton = findViewById(R.id.viewButton);


        buttonToProductMap = new HashMap<>();
        productDetailsMap = new HashMap<>();
        productImageMap = new HashMap<>();


        initializeProductData();


        setupNavigationButtons();


        setupProductButtons();


        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void initializeProductData() {

        int[] buttonIds = {
                R.id.myButton1, R.id.myButton2, R.id.myButton3, R.id.myButton4,
                R.id.myButton5, R.id.myButton6, R.id.myButton7, R.id.myButton8,
                R.id.myButton9, R.id.myButton10
        };


        String[] productNames = {
                "Chicken Burger", "BBQ Burger", "Beef Burger", "Chicken Sandwich",
                "Chicken Spinner", "Crispy Onion Burger", "Double Crispy Onion", "Giant Beef",
                "Mega Cheese Burger", "Golden Burger"
        };


        double[] productPrices = {
                5.00, 3.50, 7.00, 4.50, 2.00, 2.50, 3.00, 4.00, 5.50, 6.00
        };

        // Harta për ruajtjen e pamjeve të produkteve
        productImageMap.put("Chicken Burger", R.drawable.chickenburger);
        productImageMap.put("BBQ Burger", R.drawable.bbq);
        productImageMap.put("Beef Burger", R.drawable.beef);
        productImageMap.put("Chicken Sandwich", R.drawable.chickensandwich);
        productImageMap.put("Chicken Spinner", R.drawable.chickenspinner);
        productImageMap.put("Crispy Onion Burger", R.drawable.crispyonionburger);
        productImageMap.put("Double Crispy Onion", R.drawable.doublecrispyonion);
        productImageMap.put("Giant Beef", R.drawable.giantbeef);
        productImageMap.put("Mega Cheese Burger", R.drawable.megacheeseburger);
        productImageMap.put("Golden Burger", R.drawable.goldenburger);


        for (int i = 0; i < buttonIds.length; i++) {
            String productName = productNames[i];
            double price = productPrices[i];
            buttonToProductMap.put(buttonIds[i], productName);
            productDetailsMap.put(productName, new ProductDetails(
                    productName + " is a delicious meal.",
                    "Ingredients: Custom recipe for " + productName,
                    price
            ));
        }
    }

    private void setupProductButtons() {
        for (Map.Entry<Integer, String> entry : buttonToProductMap.entrySet()) {
            int buttonId = entry.getKey();
            String productName = entry.getValue();

            Button button = findViewById(buttonId);
            if (button != null) {
                button.setOnClickListener(v -> {

                    ProductDetails details = productDetailsMap.get(productName);
                    Integer imageRes = productImageMap.get(productName);


                    if (details != null && imageRes != null) {

                        CartItem cartItem = new CartItem(
                                productName,
                                details.getPrice(),
                                imageRes,
                                details.getDescription(),
                                details.getIngredients()
                        );
                        CartManager.addCartItem(cartItem);
                        Log.d("MenuActivity", productName + " added to cart.");
                    } else {
                        Log.e("MenuActivity", "Error: Missing details or image for " + productName);
                        showErrorDialog("Error: Missing details for " + productName);
                    }
                });
            } else {
                Log.e("MenuActivity", "Button with ID " + buttonId + " not found in layout.");
                showErrorDialog("Button ID not found: " + buttonId);
            }
        }
    }

    private void setupNavigationButtons() {
        Button homeButton = findViewById(R.id.homeButton);
        Button buyButton = findViewById(R.id.menuButton);
        Button contactButton = findViewById(R.id.contactButton);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, HomePage.class);
            startActivity(intent);
        });

        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        contactButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ContactPage.class);
            startActivity(intent);
        });
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setMessage(message)
                .setPositiveButton("OK", null);
        builder.create().show();
    }


    private static class ProductDetails {
        private final String description;
        private final String ingredients;
        private final double price;

        public ProductDetails(String description, String ingredients, double price) {
            this.description = description;
            this.ingredients = ingredients;
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public String getIngredients() {
            return ingredients;
        }

        public double getPrice() {
            return price;
        }
    }
}
