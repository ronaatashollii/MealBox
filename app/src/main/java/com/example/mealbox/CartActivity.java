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
    private Button backToHomeButton;
    private List<CartItem> cartItems;
    private TextView totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);
        backToHomeButton = findViewById(R.id.backToHomeButton);
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
            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getPrice();
        }
        totalPriceTextView.setText(String.format("Total Price: $%.2f", total));
    }

    private void showCardPaymentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.cart_payment, null);
        builder.setView(dialogView);

        final EditText cardNumberEditText = dialogView.findViewById(R.id.cardNumberEditText);
        final EditText expiryDateEditText = dialogView.findViewById(R.id.expiryDateEditText);
        final EditText cvvEditText = dialogView.findViewById(R.id.cvvEditText);
        final Button confirmPaymentButton = dialogView.findViewById(R.id.confirmPaymentButton);

        AlertDialog dialog = builder.create();

        confirmPaymentButton.setOnClickListener(v -> {
            String cardNumber = cardNumberEditText.getText().toString().trim();
            String expiryDate = expiryDateEditText.getText().toString().trim();
            String cvv = cvvEditText.getText().toString().trim();

            if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(this, "Please fill all card details!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}