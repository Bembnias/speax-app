package com.example.speax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        logoutBtn = (ImageView) findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.logout_button:
                logoutUser();
                break;
        }
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ContactsActivity.this, MainActivity.class));
    }
}