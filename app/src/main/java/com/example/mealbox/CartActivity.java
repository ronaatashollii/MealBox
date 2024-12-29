package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private CartAdapter adapter;
    private Button backToHomeButton, orderButton;
    private List<CartItem> cartItems;
    private TextView totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);
        backToHomeButton = findViewById(R.id.backToHomeButton);
        orderButton = findViewById(R.id.orderButton);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);

        cartItems = CartManager.getCartItems();

        adapter = new CartAdapter(this, cartItems, item -> {
            CartManager.removeCartItem(item);
            adapter.updateData(CartManager.getCartItems());
            updateTotalPrice();
        });

        cartListView.setAdapter(adapter);

        backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        orderButton.setOnClickListener(v -> navigateToOrderActivity());

        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getPrice();
        }
        totalPriceTextView.setText(String.format("Total Price: $%.2f", total));
    }

    private void navigateToOrderActivity() {
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Your cart is empty. Add items before ordering.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(CartActivity.this, OrderActivity.class);
            startActivity(intent);
        }
    }
}
