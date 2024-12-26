package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializimi i ConnectionClass


        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.img);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            overridePendingTransition(R.xml.slide_bottom, R.xml.fade_out);
            finish();
        }, 4000);

    }


}

