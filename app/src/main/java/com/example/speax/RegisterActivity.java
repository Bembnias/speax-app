package com.example.speax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    private TextView toLoginButton;
    private EditText editEmail, editName, editPassword, editConfirmPassword;
    private AppCompatButton submitBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        // Przycisk odsylania do ekranu logowania
        toLoginButton = (TextView) findViewById(R.id.register_summary_label);
        toLoginButton.setOnClickListener(this);

        // Przycisk do potwierdzenia rejestracji
        submitBtn = (AppCompatButton) findViewById(R.id.register_confirm_button);
        submitBtn.setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.register_email_field);
        editName = (EditText) findViewById(R.id.register_name_field);
        editPassword = (EditText) findViewById(R.id.register_password_field);
        editConfirmPassword = (EditText) findViewById(R.id.register_confirmPassword_field);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.register_summary_label:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.register_confirm_button:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String name = editName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();

        // Sprawdz czy pola nie s?? puste przed wys??aniem
        if(email.isEmpty()) {
            editEmail.setError("Wprowad?? adres e-mail");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Wprowad?? poprawny e-mail");
            editEmail.requestFocus();
            return;
        }

        if(name.isEmpty()) {
            editName.setError("Wprowad?? imie i nazwisko");
            editName.requestFocus();
            return;
        }

        if(name.isEmpty()) {
            editName.setError("Wprowad?? imie i nazwisko");
            editName.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editPassword.setError("Wprowad?? has??o dla konta");
            editPassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()) {
            editConfirmPassword.setError("Potwierd?? swoje has??o");
            editConfirmPassword.requestFocus();
            return;
        }

        if(password.length() < 8) {
            editPassword.setError("Has??o musi mie?? min. 8 znak??w");
            editPassword.requestFocus();
            return;
        }

        if(!confirmPassword.equals(password)) {
            editConfirmPassword.setError("Has??a musz?? si?? zgadza??");
            editConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Utworz nowego uzytkownika w bazie danych
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    String searchVal = name.toLowerCase();
                    User user = new User(name, email, userId, searchVal);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Utworzy??e?? nowe konto!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "B????d w trakcie rejestracji!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "Utworzy??e?? nowe konto!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, ContactsActivity.class));
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }
}