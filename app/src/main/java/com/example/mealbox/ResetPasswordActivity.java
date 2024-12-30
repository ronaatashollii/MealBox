package com.example.mealbox;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText newPassword;
    private EditText confirmPassword;
    private Button resetPasswordButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password2);

        // Lidhja me elementët në UI
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);


        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Log.d("Database", "Database path: " + db.getPath());


        String email = getIntent().getStringExtra("USER_EMAIL");

        resetPasswordButton.setOnClickListener(v -> resetPassword(email));
    }


    private void resetPassword(String email) {
        String newPass = newPassword.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();


        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
        } else if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {

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
}
