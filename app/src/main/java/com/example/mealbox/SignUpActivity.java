package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEditText = findViewById(R.id.signupEmail);
        passwordEditText = findViewById(R.id.signupPassword);
        confirmPasswordEditText = findViewById(R.id.signupPassword);
        registerButton = findViewById(R.id.signup);

        registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must contain at least 1 number and 1 special character", Toast.LENGTH_SHORT).show();
        } else {
            // Firebase Registration Logic
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{6,}$");
    }
}