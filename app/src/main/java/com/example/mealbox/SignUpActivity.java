package com.example.mealbox;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

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
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Log.d("Database", "Database path: " + db.getPath());


        nameEditText = findViewById(R.id.signupName);
        surnameEditText = findViewById(R.id.signupSurname);
        emailEditText = findViewById(R.id.signupEmail);
        passwordEditText = findViewById(R.id.signupPassword);
        phoneEditText = findViewById(R.id.signupPhone);
        registerButton = findViewById(R.id.signup);
        closeButton = findViewById(R.id.closeButton);


        registerButton.setOnClickListener(view -> registerUser());


        closeButton.setOnClickListener(view -> {

            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (!validateFields(name, surname, email, phone, password)) return;


        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());


        DBHelper dbHelper = new DBHelper(this);
        boolean isAdded = dbHelper.addUser(name, surname, email, phone, hashedPassword);

        if (isAdded) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Email already exists!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFields(String name, String surname, String email, String phone, String password) {

        if (TextUtils.isEmpty(name) || !name.matches("[a-zA-Z]+")) {
            nameEditText.setError("Name must contain only letters and not be empty.");
            return false;
        }

        if (TextUtils.isEmpty(surname) || !surname.matches("[a-zA-Z]+")) {
            surnameEditText.setError("Surname must contain only letters and not be empty.");
            return false;
        }


        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address.");
            return false;
        }


        if (TextUtils.isEmpty(phone) || !phone.matches("\\d{10,15}")) {
            phoneEditText.setError("Enter a valid phone number (10-15 digits).");
            return false;
        }


        if (TextUtils.isEmpty(password) || !isValidPassword(password)) {
            passwordEditText.setError("Password must contain at least 1 number, 1 special character, and be at least 6 characters long.");
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {

        return password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{6,}$");
    }


}
