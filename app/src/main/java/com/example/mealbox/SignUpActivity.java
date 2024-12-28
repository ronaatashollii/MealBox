package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEditText, surnameEditText, emailEditText, passwordEditText, phoneEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEditText = findViewById(R.id.signupName);
        surnameEditText = findViewById(R.id.signupSurname);
        emailEditText = findViewById(R.id.signupEmail);
        passwordEditText = findViewById(R.id.signupPassword);
        phoneEditText = findViewById(R.id.signupPhone);
        registerButton = findViewById(R.id.signup);

        registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        // Validate fields before proceeding
        if (!validateFields(name, surname, email, phone, password)) return;

        // Hash the password before sending it
        String hashedPassword = hashPasswordWithSalt(password);

        // Proceed with registration using the hashed password
        Toast.makeText(this, "Registration successful!!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

    }

    private boolean validateFields(String name, String surname, String email, String phone, String password) {
        // Validate name and surname
        if (TextUtils.isEmpty(name) || !name.matches("[a-zA-Z]+")) {
            nameEditText.setError("Name must contain only letters and not be empty.");
            return false;
        }

        if (TextUtils.isEmpty(surname) || !surname.matches("[a-zA-Z]+")) {
            surnameEditText.setError("Surname must contain only letters and not be empty.");
            return false;
        }

        // Validate email
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address.");
            return false;
        }

        // Validate phone number
        if (TextUtils.isEmpty(phone) || !phone.matches("\\d{10,15}")) {
            phoneEditText.setError("Enter a valid phone number (10-15 digits).");
            return false;
        }

        // Validate password
        if (TextUtils.isEmpty(password) || !isValidPassword(password)) {
            passwordEditText.setError("Password must contain at least 1 number, 1 special character, and be at least 6 characters long.");
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        // Check if the password meets the required criteria: at least one number, one special character, and 6+ characters
        return password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{6,}$");
    }

    private String hashPasswordWithSalt(String password) {
        try {
            // Generate a random salt
            byte[] salt = generateSalt();

            // Combine the password with the salt
            String passwordWithSalt = password + new String(salt);

            // Hash the combined password + salt using SHA-256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(passwordWithSalt.getBytes());

            // Convert bytes to hex string
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] generateSalt() {
        // Use SecureRandom to generate a strong, random salt
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes for salt
        secureRandom.nextBytes(salt);
        return salt;
    }
}
