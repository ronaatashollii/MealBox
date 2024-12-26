package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private ImageView burgerImageView1, burgerImageView;
    private Button viewCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        burgerImageView1 = findViewById(R.id.burgerImageView1);
        burgerImageView = findViewById(R.id.burgerImageView);
        viewCartButton = findViewById(R.id.viewCartButton);


        findViewById(R.id.addBurgerToCartButton).setOnClickListener(v -> {
            addToCart("Burger", 5.99, R.drawable.whopper_with_cheese);
        });

        findViewById(R.id.addBurgerToCartButton).setOnClickListener(v -> {
            addToCart("Pizza", 8.99, R.drawable.whopper_with_cheese);
        });

        // Hapja e shportës manualisht
        viewCartButton.setOnClickListener(v -> openCart());
    }


    private void addToCart(String productName, double productPrice, int productImageRes) {
        CartManager.addCartItem(new CartItem(productName, productPrice, productImageRes));
        Toast.makeText(this, productName + " added to cart!", Toast.LENGTH_SHORT).show();
    }


    private void openCart() {
        Intent intent = new Intent(HomeActivity.this, CartActivity.class);
        startActivity(intent);
    }
}