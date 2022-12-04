package com.example.speax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {
    TextView contact_name;
    ImageView backBtn;
    ImageButton send_btn;
    EditText msg_text;

    FirebaseUser fuser;
    DatabaseReference dbref;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = findViewById(R.id.contact_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(this);

        backBtn = (ImageView) findViewById(R.id.contact_back_button);
        backBtn.setOnClickListener(this);

        contact_name = findViewById(R.id.contact_name);
        send_btn = findViewById(R.id.contact_send_button);
        msg_text = findViewById(R.id.contact_input);
        intent = getIntent();

        String userId = intent.getStringExtra("user-id");
        send_btn.setOnClickListener(this);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getName() != null) {
                    contact_name.setText(user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    Wysylanie wiadomosci
    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        dbReference.child("Chats").push().setValue(hashMap);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.contact_back_button:
                backToContacts();
                break;
            case R.id.contact_toolbar:
                finish();
                break;
            case R.id.contact_send_button:
                sendMsgFunc();
                break;
        }
    }

    private void backToContacts() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ContactActivity.this, ContactsActivity.class));
    }

    public void sendMsgFunc() {
        String msg = msg_text.getText().toString();
        if (!msg.equals("")) {
            String userId;
            userId = intent.getStringExtra("user-id");
            sendMessage(fuser.getUid(), userId, msg);
        } else {
            Toast.makeText(ContactActivity.this, "Wpisz jakas tresc", Toast.LENGTH_SHORT).show();
        }
        msg_text.setText("");
    }
}