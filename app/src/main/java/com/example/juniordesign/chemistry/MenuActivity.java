package com.example.juniordesign.chemistry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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

        Button friends= (Button) findViewById(R.id.friendsbutton);
        Button findfriend = (Button) findViewById(R.id.findfriendbutton);
        Button status = (Button) findViewById(R.id.status);
        Button interest= (Button) findViewById(R.id.interestsbutton);


        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just for checking the currentUser id
//                FirebaseUser user= mAuth.getCurrentUser();
//                String id= user.getUid();
//                Toast.makeText(MenuActivity.this, "User id is: "+id, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuActivity.this,FriendsActivity1.class));
            }
        });

        findfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, FindActivity.class));
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,StatusActivity.class));
            }
        });

        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,InterestMenuActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(home);
    }
}
