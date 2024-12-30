package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText newPassword;
    private EditText confirmPassword;
    private EditText emailEditText;
    private Button resetPasswordButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password2);


        emailEditText = findViewById(R.id.emailEditText);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        dbHelper = new DBHelper(this);

        resetPasswordButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            resetPassword(email);
        });
    }

    private void resetPassword(String email) {
        String newPass = newPassword.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();


        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }


        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!dbHelper.isUserExist(email)) {
            Toast.makeText(this, "Email not found. Please check your email", Toast.LENGTH_SHORT).show();
            return;
        }


        boolean isUpdated = dbHelper.updatePassword(email, newPass);


        if (isUpdated) {
            Toast.makeText(this, "Password has been reset successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {

            Toast.makeText(this, "Error updating password", Toast.LENGTH_SHORT).show();
        }
    }
}
