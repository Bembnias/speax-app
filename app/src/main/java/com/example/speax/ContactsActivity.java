package com.example.speax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapters.UserAdapter;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView logoutBtn;

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> uUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        logoutBtn = (ImageView) findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.contacts_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    
        uUsers = new ArrayList<>();
        
        redUsers();
    }

    private void redUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uUsers.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if(!user.getEmail().equals(firebaseUser.getEmail())) {
                        uUsers.add(user);
                    }
                }

                userAdapter = new UserAdapter(getApplicationContext(), uUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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