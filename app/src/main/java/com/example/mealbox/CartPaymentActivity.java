package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CartPaymentActivity extends AppCompatActivity {

    private EditText cardNumberEditText, expiryDateEditText, cvvEditText;
    private Button confirmPaymentButton;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_payment);


        closeButton = findViewById(R.id.closeButton);
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        cvvEditText = findViewById(R.id.cvvEditText);
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);


        closeButton.setOnClickListener(v -> {

            Intent intent = new Intent(CartPaymentActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });


        confirmPaymentButton.setOnClickListener(v -> {
            String cardNumber = cardNumberEditText.getText().toString().trim();
            String expiryDate = expiryDateEditText.getText().toString().trim();
            String cvv = cvvEditText.getText().toString().trim();

            if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(CartPaymentActivity.this, "Please fill in all card details!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CartPaymentActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(CartPaymentActivity.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}