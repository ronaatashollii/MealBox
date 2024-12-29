package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button sendCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.emailEditText);
        sendCodeButton = findViewById(R.id.sendCodeButton);

        sendCodeButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else {
                // Simulo dërgimin e kodit
                sendVerificationCode(email);

                // Hap faqen e konfirmimit
                Intent intent = new Intent(ForgotPasswordActivity.this, VerifyCodeActivity.class);
                intent.putExtra("USER_EMAIL", email); // Dërgo email-in në VerifyCodeActivity
                startActivity(intent);
            }
        });
    }

    // Funksioni për dërgimin e kodit të verifikimit (Simulim)
    private void sendVerificationCode(String email) {
        // Mund të përdorësh Firebase ose ndonjë API tjetër për dërgimin e kodit
        Toast.makeText(this, "Verification code sent to " + email, Toast.LENGTH_SHORT).show();
    }
}
