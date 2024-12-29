package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText newPassword;
    private EditText confirmPassword;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password2);

        // Lidhja me elementët në UI
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        resetPasswordButton.setOnClickListener(v -> resetPassword()); // Kur klikoni butonin, thirri funksionin për ndryshimin e fjalëkalimit
    }

    // Funksioni për të ndryshuar fjalëkalimin
    private void resetPassword() {
        String newPass = newPassword.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();

        // Kontrollo nëse fjalëkalimet janë të barabarta dhe të vlefshme
        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
        } else if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            // Dërgo fjalëkalimin e ri (kjo mund të lidhet me një API ose bazën e të dhënave)
            Toast.makeText(this, "Password has been reset successfully", Toast.LENGTH_SHORT).show();

            // Hapni aktivitetin e logimit pas resetimit të fjalëkalimit
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);  // Kaloni në aktivitetin e logimit
            finish();  // Mbyllni këtë aktivitet që të mos ktheheni prapa te resetimi
        }
    }
}
