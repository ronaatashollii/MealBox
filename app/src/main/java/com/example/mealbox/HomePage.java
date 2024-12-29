package com.example.mealbox;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class HomePage extends AppCompatActivity {

    private static final String CHANNEL_ID = "welcome_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Welcome Channel";
            String description = "Channel for welcome notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        showWelcomeNotification();
        showLoginMessage();
        setupNavigationButtons();
        setupLogoutButton(); // Kalloni funksionin për butonin e logout-it
    }

    private void showWelcomeNotification() {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // Zhurma e paracaktuar

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img_2)
                .setContentTitle("Welcome to MealBox!")
                .setContentText("Get ready for the ultimate burger experience!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSound(soundUri);  // Zhurma në notifikim

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    private void showLoginMessage() {
        // Shfaqim një mesazh Toast pas hapjes së HomePage
        Toast.makeText(HomePage.this, "Welcome to MealBox! Enjoy your experience.", Toast.LENGTH_LONG).show();
    }

    private void setupNavigationButtons() {
        Button homeButton = findViewById(R.id.homeButton);
        Button menuButton = findViewById(R.id.menuButton);
        Button contactButton = findViewById(R.id.contactButton);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, HomePage.class);
            startActivity(intent);
        });

        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, MenuActivity.class);
            startActivity(intent);
        });

        contactButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, ContactPage.class); // Sigurohu që ContactPage është deklaruar në manifest
            startActivity(intent);
        });
    }

    private void setupLogoutButton() {
        ImageView logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            // Kaloni në LoginActivity kur të klikoni logout
            Intent intent = new Intent(HomePage.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Mbyllim aktivitetin aktual (HomePage)
        });
    }
}
