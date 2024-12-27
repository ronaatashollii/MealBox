package com.example.mealbox;
import static android.content.ContentValues.TAG;

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


    public String code = generateCode();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        otp = findViewById(R.id.codeEditText);
        checkCode = findViewById(R.id.verifyButton);
        resendCode = findViewById(R.id.resentCode);
        emailRecipient = getIntent().getStringExtra("USER_EMAIL");

        Log.d(TAG, "Received email: " + emailRecipient);


        new Thread(() -> {
            try {
                com.example.mealbox.EmailSender.sendCode(emailRecipient, code);
                runOnUiThread(() -> Toast.makeText(this, "6 Digit Code sent to " + emailRecipient, Toast.LENGTH_SHORT).show());
            } catch (MessagingException e) {
                runOnUiThread(() -> Toast.makeText(this, "Failed to send 6 Digit Code", Toast.LENGTH_SHORT).show());
                Log.e(TAG, "Error sending OTP code", e);
                e.printStackTrace();
            }
        }).start();

        checkCode.setOnClickListener(v -> {
            checkCode();
        });

        resendCode.setOnClickListener(view ->{
            resendCode();

        });


    }

    public void checkCode() {
        String inputString = otp.getText().toString().trim();
        if (inputString.equals(code)) {
            Toast.makeText(this, "Code is correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect code, try again.", Toast.LENGTH_SHORT).show();
        }


    }

    public void resendCode(){
        String randomCode = generateCode();
        code = randomCode;
        new Thread(() -> {
            try {
                com.example.mealbox.EmailSender.sendCode(emailRecipient, randomCode);
                runOnUiThread(() -> Toast.makeText(this, "6 Digit Code sent to " + emailRecipient, Toast.LENGTH_SHORT).show());
            } catch (MessagingException e) {
                runOnUiThread(() -> Toast.makeText(this, "Failed to send 6 Digit Code", Toast.LENGTH_SHORT).show());
                Log.e(TAG, "Error sending OTP code", e);
                e.printStackTrace();
            }
        }).start();

    }
    public String generateCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        int length = 6; // Gjatësia e kodit

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }

}
