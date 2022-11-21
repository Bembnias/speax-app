package com.example.speax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private TextView toLoginButton;

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://speax-app-default-rtdb.firebaseio.com")

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
                    Toast.makeText(RegisterActivity.this, "Wszystkie pole muszą zostać uzupełnione!", Toast.LENGTH_SHORT).show();
                } else {
                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.child("users").hasChild(emailTxt)) {
                                Toast.makeText(RegisterActivity.this, "Taki email już istnieje!", Toast.LENGTH_SHORT).show();
                            } else {
                                dbRef.child("users").child(emailTxt).child("email").setValue(emailTxt);
                                dbRef.child("users").child(emailTxt).child("name").setValue(nameTxt);
                                dbRef.child("users").child(emailTxt).child("surname").setValue(surnameTxt);
                                dbRef.child("users").child(emailTxt).child("password").setValue(passwordTxt);

                                Toast.makeText(RegisterActivity.this, "Poprawnie zarejestrowano!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra("email", emailTxt);
                                intent.putExtra("name", nameTxt);
                                intent.putExtra("surname", surnameTxt);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}