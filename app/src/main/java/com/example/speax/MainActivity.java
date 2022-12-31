package com.example.speax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.List;

import Notifications.Token;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    private TextView toRegisterButton;
    private EditText editEmail, editPassword;
    private AppCompatButton submitBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // Przycisk odsylania do ekranu rejestracji
        toRegisterButton = (TextView) findViewById(R.id.login_summary_label);
        toRegisterButton.setOnClickListener(this);

        // Przycisk do potwierdzenia logowania
        submitBtn = (AppCompatButton) findViewById(R.id.login_confirm_button);
        submitBtn.setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.login_email_field);
        editPassword = (EditText) findViewById(R.id.login_password_field);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        System.out.println(token);
                        Toast.makeText(MainActivity.this, "Token rejestracyjny dla twojego urządzenia to:" + token, Toast.LENGTH_SHORT).show();

                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Tokens");
                        Token token1 = new Token(token);
                        dbRef.child(firebaseUser.getUid()).setValue(token);
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.login_summary_label:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_confirm_button:
                loginUser();
                break;
        }
    }

    private void loginUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // Sprawdz czy pola nie są puste przed wysłaniem
        if(email.isEmpty()) {
            editEmail.setError("Wprowadź adres e-mail");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Wprowadź poprawny e-mail");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editPassword.setError("Wprowadź hasło dla konta");
            editPassword.requestFocus();
            return;
        }

        if(password.length() < 8) {
            editPassword.setError("Hasło musi mieć min. 8 znaków");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, ContactsActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Błąd w trakcie logowania!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}