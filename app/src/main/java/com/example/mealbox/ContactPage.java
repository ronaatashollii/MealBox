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
    private static final String EMAIL = "detyra2fa@gmail.com"; // Vendosni email-in tuaj
    private static final String PASSWORD = "fxzweisoojlfmbhc"; // App Password i llogarisë suaj Gmail

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

        // Validimi i të dhënave të futur nga përdoruesi
        if (!validateInputs(name, email, message)) return;

        // Dërgimi i mesazhit me një fije të veçantë
        new Thread(() -> {
            boolean isSent = sendEmail(name, email, message);
            runOnUiThread(() -> {
                if (isSent) {
                    Toast.makeText(this, "Mesazhi u dërgua me sukses!", Toast.LENGTH_SHORT).show();
                    Intent homePageIntent = new Intent(ContactPage.this, HomePage.class);
                    startActivity(homePageIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Dështoi dërgimi i mesazhit. Ju lutem provoni përsëri.", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private boolean validateInputs(String name, String email, String message) {
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Emri nuk mund të jetë i zbrazët");
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Emaili nuk mund të jetë i zbrazët");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Ju lutem shkruani një email të vlefshëm");
            return false;
        }

        if (TextUtils.isEmpty(message)) {
            messageEditText.setError("Mesazhi nuk mund të jetë i zbrazët");
            return false;
        }

        return true;
    }

    private boolean sendEmail(String name, String email, String messageContent) {
        try {
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
                    return new PasswordAuthentication(EMAIL, PASSWORD);
                }
            });

            // Krijoni mesazhin email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("detyra2fa@gmail.com")); // Ndryshoni adresën sipas nevojës
            message.setSubject("New Contact Message from " + name);
            message.setText("Name: " + name + "\nEmail: " + email + "\nMessage: " + messageContent);

            // Aktivizoni debug për SMTP
            session.setDebug(true);

            // Dërgoni email-in
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
