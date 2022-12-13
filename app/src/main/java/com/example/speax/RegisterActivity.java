package com.example.speax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private TextView toLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Przycisk odsylania do ekranu logowania
        toLoginButton = (TextView) findViewById(R.id.register_summary_label);
        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        // Tworzenie nowego konta użytkownika
        final EditText email = findViewById(R.id.register_email_field);
        final EditText name = findViewById(R.id.register_name_field);
        final EditText surname = findViewById(R.id.register_surname_field);
        final EditText password = findViewById(R.id.register_password_field);
        final AppCompatButton registerConfirmBtn = findViewById(R.id.register_confirm_button);

        registerConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameTxt = name.getText().toString();
                final String emailTxt = name.getText().toString();
                final String surnameTxt = name.getText().toString();
                final String passwordTxt = name.getText().toString();

                if(nameTxt.isEmpty() || emailTxt.isEmpty() || surnameTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(Register.this, "Wszystkie pole muszą zostać uzupełnione!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}