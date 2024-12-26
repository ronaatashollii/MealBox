package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private CartAdapter adapter;
    private Button backToHomeButton;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);
        backToHomeButton = findViewById(R.id.backToHomeButton);


        cartItems = CartManager.getCartItems();


        adapter = new CartAdapter(this, cartItems, new CartAdapter.OnRemoveClickListener() {
            @Override
            public void onRemoveClick(CartItem item) {
                CartManager.removeCartItem(item);
                adapter.updateData(CartManager.getCartItems());
            }
        });
        cartListView.setAdapter(adapter);


        backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}