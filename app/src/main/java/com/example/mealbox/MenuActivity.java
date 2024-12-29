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
    private Map<Integer, String> buttonToProductMap; // Map buttons to product names
    private Map<String, ProductDetails> productDetailsMap; // Store product details
    private ImageButton viewCartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        viewCartButton = findViewById(R.id.viewButton);

        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CartActivity.class);
            startActivity(intent);
        });
        buttonToProductMap = new HashMap<>();
        productDetailsMap = new HashMap<>();

        // Button IDs and product names mapping
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



        // Set listeners for product buttons
        for (int id : buttonIds) {
            Button button = findViewById(id);

            if (button != null) {
                button.setOnClickListener(new ButtonClickListener());
            } else {
                Log.e("MenuActivity", "Button with ID " + id + " not found in layout.");
            }
        }

        // Navigation buttons setup
        setupNavigationButtons();
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

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int buttonId = view.getId();
            String productName = buttonToProductMap.get(buttonId);

            if (productName != null) {
                ProductDetails details = productDetailsMap.get(productName);

                if (details != null) {
                    // Show a dialog with product details
                    new AlertDialog.Builder(MenuActivity.this)
                            .setTitle(productName)
                            .setMessage("Description: " + details.getDescription() +
                                    "\n\nIngredients: " + details.getIngredients())
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    // If no details are found, show an error
                    new AlertDialog.Builder(MenuActivity.this)
                            .setTitle("Error")
                            .setMessage("Details for " + productName + " not found.")
                            .setPositiveButton("OK", null)
                            .show();
                }
            } else {
                Log.e("MenuActivity", "No product mapped for button ID: " + buttonId);
            }
        }
    }

    // Inner class for product details
    private static class ProductDetails {
        private final String description;
        private final String ingredients;

        public ProductDetails(String description, String ingredients) {
            this.description = description;
            this.ingredients = ingredients;
        }

        public String getDescription() {
            return description;
        }

        public String getIngredients() {
            return ingredients;
        }
    }
}
