package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEditText, surnameEditText, emailEditText, passwordEditText, phoneEditText;
    private Button registerButton;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Inicializimi i elementeve të UI
        nameEditText = findViewById(R.id.signupName);
        surnameEditText = findViewById(R.id.signupSurname);
        emailEditText = findViewById(R.id.signupEmail);
        passwordEditText = findViewById(R.id.signupPassword);
        phoneEditText = findViewById(R.id.signupPhone);
        registerButton = findViewById(R.id.signup);
        closeButton = findViewById(R.id.closeButton);

        // Vendosim klikimin për regjistrim
        registerButton.setOnClickListener(view -> registerUser());

        // Vendosim klikimin për butonin X që kthen në LoginActivity
        closeButton.setOnClickListener(view -> {
            // Kaloni në LoginActivity
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();  // Mbyllim SignUpActivity për të mos u kthyer pas regjistrimit
        });
    }

    private void registerUser() {
        // Marrim të dhënat nga fushat e formularit
        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        // Validimi i të dhënave
        if (!validateFields(name, surname, email, phone, password)) return;

        // Hasimi i fjalëkalimit para se të dërgohet
        String hashedPassword = hashPasswordWithSalt(password);

        // Pasi regjistrimit, kaloni në LoginActivity
        Toast.makeText(this, "Registration successful!!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();  // Mbyllim SignUpActivity për të mos u kthyer pas regjistrimit
    }

    private boolean validateFields(String name, String surname, String email, String phone, String password) {
        // Validimi i emrit dhe mbiemrit
        if (TextUtils.isEmpty(name) || !name.matches("[a-zA-Z]+")) {
            nameEditText.setError("Name must contain only letters and not be empty.");
            return false;
        }

        if (TextUtils.isEmpty(surname) || !surname.matches("[a-zA-Z]+")) {
            surnameEditText.setError("Surname must contain only letters and not be empty.");
            return false;
        }

        // Validimi i email-it
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address.");
            return false;
        }

        // Validimi i numrit të telefonit
        if (TextUtils.isEmpty(phone) || !phone.matches("\\d{10,15}")) {
            phoneEditText.setError("Enter a valid phone number (10-15 digits).");
            return false;
        }

        // Validimi i fjalëkalimit
        if (TextUtils.isEmpty(password) || !isValidPassword(password)) {
            passwordEditText.setError("Password must contain at least 1 number, 1 special character, and be at least 6 characters long.");
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        // Kontrolloni nëse fjalëkalimi plotëson kriteret e kërkuara: të paktën një numër, një karakter special dhe 6+ karaktere
        return password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{6,}$");
    }

    private String hashPasswordWithSalt(String password) {
        try {
            // Gjeneroni një salt të rastësishëm
            byte[] salt = generateSalt();

            // Kombinoni fjalëkalimin me salt
            String passwordWithSalt = password + new String(salt);

            // Hashoni fjalëkalimin + salt duke përdorur SHA-256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(passwordWithSalt.getBytes());

            // Konvertoni bytes në një string hex
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
        // Përdorim SecureRandom për të gjeneruar një salt të fortë dhe të rastësishëm
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes për salt
        secureRandom.nextBytes(salt);
        return salt;
    }
}
