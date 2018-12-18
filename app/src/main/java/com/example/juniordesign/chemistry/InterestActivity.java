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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InterestActivity extends AppCompatActivity {

    Spinner interests;
    private DatabaseReference currentUsersRef;
    private FirebaseAuth mAuth;
    String current_user_id;
    final ArrayList<String> previousInterests = new ArrayList();

//    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        mAuth = FirebaseAuth.getInstance();

        current_user_id=mAuth.getCurrentUser().getUid();
        currentUsersRef= FirebaseDatabase.getInstance().getReference().child("users").child(current_user_id);
//        databaseUsers=FirebaseDatabase.getInstance().getReference().child("users");

        interests=(Spinner) findViewById(R.id.interests);
        Button add=(Button) findViewById(R.id.addInterest);
//        Button test= (Button) findViewById(R.id.test);

//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //        FOR DEBUGGING THE CURRENT MOOD WITH RESPECT TO THE USER CLASS
//        databaseUsers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot user: dataSnapshot.getChildren()) {
//                    if (user.getKey().equals(current_user_id)) {
//                        User user1 = user.getValue(User.class);
//                        ArrayList<String> iList= user1.getInterestList();
//                        Log.e("Get Data YAY:", iList.get(0));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//            }
//        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUsersRef.child("interests").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot interestSnapshot : dataSnapshot.getChildren()) {
                                if (interestSnapshot.exists()) {
                                    String prevInterest = interestSnapshot.getValue().toString();
                                    Log.e("Get Interest: ", prevInterest);
                                    if(!previousInterests.contains(prevInterest)) {
                                        previousInterests.add(prevInterest);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                String interest= interests.getSelectedItem().toString();
                for(int i=0; i<previousInterests.size();i++){
                    Log.e("Array: ",previousInterests.get(i));
                }
                int index= previousInterests.size();
                String listIndex= Integer.toString(index);
                Log.e("Array size",listIndex);

                if(!previousInterests.contains(interest)) {
                    currentUsersRef.child("interests").child(listIndex).setValue(interest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(InterestActivity.this, "Added interest successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(InterestActivity.this, "You already have this interest added", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUsersRef= FirebaseDatabase.getInstance().getReference().child("users").child(current_user_id);

        currentUsersRef.child("interests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot interestSnapshot : dataSnapshot.getChildren()) {
                        if (interestSnapshot.exists()) {
                            String prevInterest = interestSnapshot.getValue().toString();
                            Log.e("Get Interest onStart: ", prevInterest);
                            if(!previousInterests.contains(prevInterest)) {
                                previousInterests.add(prevInterest);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
