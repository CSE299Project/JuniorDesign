package com.example.juniordesign.chemistry;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatusActivity extends AppCompatActivity {

    DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;
    Spinner currentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        Button setStatus = (Button) findViewById(R.id.setStatus);
        currentStatus = (Spinner) findViewById(R.id.currentStatus);

        setStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserStatus();
            }
        });

    }

    private void addUserStatus(){

        final String status= currentStatus.getSelectedItem().toString();
        //Toast.makeText(StatusActivity.this,status,Toast.LENGTH_LONG).show();
        final String id= mAuth.getCurrentUser().getUid();
        databaseUsers.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("currentMood").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(StatusActivity.this,"Updated Successfully",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StatusActivity.this,"Update unsuccessful",Toast.LENGTH_LONG).show();
            }
        });

//        FOR DEBUGGING THE CURRENT MOOD WITH RESPECT TO THE USER CLASS
//        databaseUsers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot user: dataSnapshot.getChildren()) {
//                    if (user.getKey().equals(id)) {
//                        User user1 = user.getValue(User.class);
//                        Log.e("Get Data:", user1.getCurrentMood());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

   }
}
