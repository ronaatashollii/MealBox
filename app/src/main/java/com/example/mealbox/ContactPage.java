package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ContactPage extends AppCompatActivity {

    private EditText nameEditText, emailEditText, messageEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        // Inicializimi i fusha EditText dhe butonit
        nameEditText = findViewById(R.id.et_name);
        emailEditText = findViewById(R.id.et_email);
        messageEditText = findViewById(R.id.et_message);
        sendButton = findViewById(R.id.btn_send);

        // Vendosim funksionin për të dërguar mesazhin
        sendButton.setOnClickListener(view -> sendMessage());
    }

    // Funksioni që ekzekutohet kur përdoruesi klikon 'Send'
    private void sendMessage() {
        // Marrim të dhënat nga fusha të ndryshme
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        // Verifikimi i fusha
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Emri nuk mund të jetë i zbrazët");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Emaili nuk mund të jetë i zbrazët");
            return;
        }

        if (TextUtils.isEmpty(message)) {
            messageEditText.setError("Mesazhi nuk mund të jetë i zbrazët");
            return;
        }

        // Verifikimi i email-it
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Ju lutem shkruani një email të vlefshëm");
            return;
        }

        // Krijimi i instancës së UserMessage
        UserMessage userMessage = new UserMessage(name, email, message);

        // Mund të përdorim këtë objekt për të dërguar mesazhin në server ose për ruajtje lokale
        // Për momentin, do ta logojmë mesazhin
        Log.d("UserMessage", "Emri: " + userMessage.getName() + ", Email: " + userMessage.getEmail() + ", Mesazhi: " + userMessage.getMessage());

        // Mund të bëjmë një Toast për t'i treguar përdoruesit që mesazhi u dërgua
        Toast.makeText(this, "Mesazhi u dërgua me sukses!", Toast.LENGTH_SHORT).show();

        // Ridrejtoni përdoruesin në faqen kryesore
        Intent homePageIntent = new Intent(ContactPage.this, HomePage.class);
        startActivity(homePageIntent);
        finish();  // Mbyllim aktivitetin aktual
    }
}
