package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView signup, forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializimi i komponentëve të UI
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signUpText);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        // Animimi i logos
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        findViewById(R.id.logoImage).startAnimation(fadeIn);

        // Përdorimi i listener për butonin e login
        loginButton.setOnClickListener(view -> loginUser());

        signup.setOnClickListener(view -> {
            // Kur përdoruesi klikoni regjistrohu
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_in, R.anim.fade_in);
        });

        forgotPasswordButton.setOnClickListener(view -> {
            // Kur përdoruesi klikoni harroi fjalëkalimin
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up, R.anim.fade_out);
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
        } else {
            // Nëse login është i suksesshëm
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

            // Kaloni në HomePage
            Intent intent = new Intent(LoginActivity.this, HomePage.class);
            startActivity(intent);

            // Mbyllni LoginActivity për të mos lejuar kthimin
            finish();
        }
    }
}
