package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private RadioGroup paymentMethodGroup;
    private RadioButton cardPaymentButton, cashPaymentButton;
    private EditText nameEditText, addressEditText, phoneEditText;
    private Button confirmOrderButton;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        closeButton = findViewById(R.id.closeButton); // Butoni X
        paymentMethodGroup = findViewById(R.id.paymentMethodGroup);
        cardPaymentButton = findViewById(R.id.cardPaymentButton);
        cashPaymentButton = findViewById(R.id.cashPaymentButton);
        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);


        closeButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });


        paymentMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.cardPaymentButton) {
                Intent intent = new Intent(OrderActivity.this, CartPaymentActivity.class);
                startActivity(intent);
            }
        });


        confirmOrderButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();


            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(OrderActivity.this, "Please fill all details to confirm the order!", Toast.LENGTH_SHORT).show();
                return;
            }


            int selectedPaymentId = paymentMethodGroup.getCheckedRadioButtonId();
            if (selectedPaymentId == -1) {
                Toast.makeText(OrderActivity.this, "Please select a payment method!", Toast.LENGTH_SHORT).show();
                return;
            }


            if (selectedPaymentId == R.id.cashPaymentButton) {
                Toast.makeText(OrderActivity.this, "Order confirmed with Cash Payment!", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(OrderActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}