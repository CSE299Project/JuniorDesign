package com.example.juniordesign.chemistry;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InterestListActivity extends AppCompatActivity {

    private DatabaseReference UsersRef;
    private FirebaseAuth mAuth;
    private RecyclerView InterestList;
    private String currentUser_id;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_list);
        lv = (ListView) findViewById(R.id.interest_list);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        currentUser_id = user.getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user: dataSnapshot.getChildren()) {
                    if (user.getKey().equals(currentUser_id)) {
                        User user1 = user.getValue(User.class);
                        ArrayList<String> iList= user1.getInterestList();
                        Log.e("Get Data: ",iList.get(0));
                        ArrayAdapter<String> adapter= new ArrayAdapter<String>(InterestListActivity.this,R.layout.interestlist_view,iList);
                        lv.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.interestlist_view,iList);

        //lv.setAdapter(adapter);

    }
}
