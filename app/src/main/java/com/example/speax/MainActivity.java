package com.example.speax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String email;
    private String name;
    private String surname;
    private RecyclerView messagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messagesRecyclerView = findViewById(R.id.messages_recycler_view);

        // Pobierz dane wyslane z reigsterActivity
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        surname = getIntent().getStringExtra("surname");

        messagesRecyclerView.setHasFixedSize(true);

    }
}