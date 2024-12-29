package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

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

        adapter = new CartAdapter(this, cartItems, new CartAdapter.OnRemoveClickListener() {
            @Override
            public void onRemoveClick(CartItem item) {
                CartManager.removeCartItem(item);
                adapter.updateData(CartManager.getCartItems());
                updateTotalPrice();
            }
        });
        cartListView.setAdapter(adapter);

        backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        orderButton.setOnClickListener(v -> showOrderDialog());

        updateTotalPrice();
    }

    private void updateCartList() {
        cartItems = CartManager.getCartItems();
        adapter.updateData(cartItems);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getPrice();
        }
        totalPriceTextView.setText(String.format("Total Price: $%.2f", total));
    }



    private void showOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_order, null);
        builder.setView(dialogView);

        final EditText nameEditText = dialogView.findViewById(R.id.nameEditText);
        final EditText addressEditText = dialogView.findViewById(R.id.addressEditText);
        final EditText phoneEditText = dialogView.findViewById(R.id.phoneEditText);
        final Button confirmOrderButton = dialogView.findViewById(R.id.confirmOrderButton);

        final AlertDialog dialog = builder.create();

        confirmOrderButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(CartActivity.this, "Please fill all details!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CartActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
