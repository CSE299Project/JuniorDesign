package com.example.juniordesign.chemistry;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReceiverActivity extends AppCompatActivity {

    private String id;
    private DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        final TextView userName = (TextView) findViewById(R.id.userName);
        final TextView userPhone = (TextView) findViewById(R.id.userPhone);

        Bundle bundle = getIntent().getExtras();
        id= bundle.getString("visit_user_id");

        Log.e("Get Rec id:",id);

//        Button test = (Button) findViewById(R.id.button);

//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ReceiverActivity.this,id, Toast.LENGTH_SHORT).show();
//            }
//        });

        UsersRef= FirebaseDatabase.getInstance().getReference().child("users");


        UsersRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            final String user_name = dataSnapshot.child("firstName").getValue().toString();
            final String phone_num = dataSnapshot.child("phone").getValue().toString();

            userName.setText(user_name);
            userPhone.setText(phone_num);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
