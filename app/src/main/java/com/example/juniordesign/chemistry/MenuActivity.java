package com.example.juniordesign.chemistry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();

        Button logOutButton = (Button) findViewById(R.id.button8);
        Button friends= (Button) findViewById(R.id.button7);


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just for checking the currentUser id
                FirebaseUser user= mAuth.getCurrentUser();
                String id= user.getUid();
                Toast.makeText(MenuActivity.this, "User id is: "+id, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
