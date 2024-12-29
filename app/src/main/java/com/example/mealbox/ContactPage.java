package com.example.mealbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ContactPage extends AppCompatActivity {

    private EditText nameEditText, emailEditText, messageEditText;
    private Button sendButton;
    private static final String EMAIL = "agnesamaniii@@gmail.com"; // Përdorni email-in tuaj Gmail
    private static final String PASSWORD = "vehisvlelydqwvwp"; // Përdorni fjalëkalimin tuaj ose "App Password"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        // Inicializimi i fushave të EditText dhe butonit
        nameEditText = findViewById(R.id.et_name);
        emailEditText = findViewById(R.id.et_email);
        messageEditText = findViewById(R.id.et_message);
        sendButton = findViewById(R.id.btn_send);

        // Vendosim funksionin për të dërguar mesazhin
        sendButton.setOnClickListener(view -> sendMessage());
    }

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

        // Krijoni një objekt UserMessage për të ruajtur të dhënat
        UserMessage userMessage = new UserMessage(name, email, message);

        // Dërgoni mesazhin nëpërmjet Gmail SMTP
        sendEmail(userMessage);

        // Tregoni mesazh suksesin dhe ridrejtoni përdoruesin në faqen kryesore
        Toast.makeText(this, "Mesazhi u dërgua me sukses!", Toast.LENGTH_SHORT).show();
        Intent homePageIntent = new Intent(ContactPage.this, HomePage.class);
        startActivity(homePageIntent);
        finish(); // Mbyllim aktivitetin aktual
    }

    private void sendEmail(UserMessage userMessage) {
        // Vendosni parametrat e Gmail SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        // Krijoni një seancë për autentifikim dhe dërgim
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD); // Vendosni email-in dhe fjalëkalimin tuaj
            }
        });

        try {
            // Krijoni mesazhin email
            Message messageObject = new MimeMessage(session);
            messageObject.setFrom(new InternetAddress(EMAIL));
            messageObject.setRecipients(Message.RecipientType.TO, InternetAddress.parse("agnesamaniii@gmail.com")); // Vendosni adresën tuaj të emailit të destinacionit
            messageObject.setSubject("New Contact Message from " + userMessage.getName());
            messageObject.setText("Name: " + userMessage.getName() + "\nEmail: " + userMessage.getEmail() + "\nMessage: " + userMessage.getMessage());

            // Dërgo emailin
            Transport.send(messageObject);
        } catch (MessagingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ka ndodhur një gabim gjatë dërgimit të mesazhit.", Toast.LENGTH_SHORT).show();
        }
    }
}
