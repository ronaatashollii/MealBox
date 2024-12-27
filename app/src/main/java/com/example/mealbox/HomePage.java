package com.example.mealbox;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Build;
import android.app.NotificationChannel;

public class HomePage extends AppCompatActivity implements OnMapReadyCallback {

    private static final String CHANNEL_ID = "welcome_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Set up the notification channel (required for Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Welcome Channel";
            String description = "Channel for welcome notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Show a welcome notification when the homepage is loaded
        showWelcomeNotification();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Set a marker on a specific location
        LatLng restaurantLocation = new LatLng(40.7128, -74.0060); // Example coordinates
        googleMap.addMarker(new MarkerOptions()
                .position(restaurantLocation)
                .title("Our Restaurant"));
        googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));
    }

    private void showWelcomeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img_2) // Add your notification icon here
                .setContentTitle("Welcome to MealBox!")
                .setContentText("Thank you for logging in.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Get the NotificationManager system service
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Show the notification
        notificationManager.notify(1, builder.build()); // Notification ID = 1
    }
}
