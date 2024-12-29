package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import javax.mail.MessagingException;

public class VerifyCodeActivity extends AppCompatActivity {

    private EditText otp;
    private Button checkCode;
    private String emailRecipient;
    private TextView resendCode;
    public String code = generateCode(); // Gjenero kodi i verifikimit kur hapet aktiviteti

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        // Lidhja me elementët në UI
        otp = findViewById(R.id.codeEditText);
        checkCode = findViewById(R.id.verifyButton);
        resendCode = findViewById(R.id.resentCode);
        emailRecipient = getIntent().getStringExtra("USER_EMAIL");

        Log.d("VerifyCodeActivity", "Received email: " + emailRecipient);

        // Dërgo kodin kur hapet aktiviteti
        new Thread(() -> {
            try {
                com.example.mealbox.EmailSender.sendCode(emailRecipient, code); // Dërgo kodin përmes emailit
                runOnUiThread(() -> Toast.makeText(this, "6 Digit Code sent to " + emailRecipient, Toast.LENGTH_SHORT).show());
            } catch (MessagingException e) {
                runOnUiThread(() -> Toast.makeText(this, "Failed to send 6 Digit Code", Toast.LENGTH_SHORT).show());
                Log.e("VerifyCodeActivity", "Error sending OTP code", e);
            }
        }).start();

        // Kontrollo kodin kur klikoni butonin
        checkCode.setOnClickListener(v -> checkCode());

        // Dërgo një kod të ri kur përdoruesi dëshiron ta rimbushë kodin
        resendCode.setOnClickListener(view -> resendCode());
    }

    // Funksioni për të kontrolluar kodin OTP
    public void checkCode() {
        String inputString = otp.getText().toString().trim(); // Merr tekstin e futur nga përdoruesi
        Log.d("VerifyCodeActivity", "User entered: " + inputString); // Log për debug

        // Kontrollo nëse kodi i futur është i saktë
        if (inputString.equals(code)) {
            Toast.makeText(this, "Code is correct!", Toast.LENGTH_SHORT).show();

            // Hapni aktivitetin për ndryshimin e fjalëkalimit pasi kodi është i saktë
            Intent intent = new Intent(VerifyCodeActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
            finish(); // Mbyll këtë aktivitet pas kalimit
        } else {
            Toast.makeText(this, "Incorrect code, try again.", Toast.LENGTH_SHORT).show(); // Kodi nuk është i saktë
        }
    }

    // Funksioni për të rimbushur kodin dhe dërguar një kod të ri
    public void resendCode() {
        String randomCode = generateCode();
        code = randomCode; // Vendosni kodin e ri
        new Thread(() -> {
            try {
                com.example.mealbox.EmailSender.sendCode(emailRecipient, randomCode); // Dërgo kodin e ri
                runOnUiThread(() -> Toast.makeText(this, "6 Digit Code sent to " + emailRecipient, Toast.LENGTH_SHORT).show());
            } catch (MessagingException e) {
                runOnUiThread(() -> Toast.makeText(this, "Failed to send 6 Digit Code", Toast.LENGTH_SHORT).show());
                Log.e("VerifyCodeActivity", "Error sending OTP code", e);
            }
        }).start();
    }

    // Funksioni për gjenerimin e një kodi të rastësishëm
    public String generateCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // Shkronjat dhe numrat për kodin
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        int length = 6; // Gjatësia e kodit

        // Gjeneroni kodin e rastësishëm
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString(); // Kthejeni kodin e gjeneruar
    }
}
