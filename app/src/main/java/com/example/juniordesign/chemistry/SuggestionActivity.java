package com.example.juniordesign.chemistry;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SuggestionActivity extends AppCompatActivity {

    private RecyclerView SuggestionList;
    private DatabaseReference FriendsRef, UsersRef,NotificationsRef;
    private FirebaseAuth mAuth;
    private String current_user_id;
    private String currentMood;
    private String option;
    private String receiver_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        mAuth = FirebaseAuth.getInstance();
        current_user_id= mAuth.getCurrentUser().getUid();

        UsersRef= FirebaseDatabase.getInstance().getReference().child("users");
        FriendsRef = FirebaseDatabase.getInstance().getReference("friends").child(current_user_id);
        NotificationsRef= FirebaseDatabase.getInstance().getReference().child("notifications");


        Bundle bundle = getIntent().getExtras();
        currentMood = bundle.getString("mood");
        option = bundle.getString("searchOption");

        // DEBUG
//        Button testReceivedVal= (Button) findViewById(R.id.test);
//
//        testReceivedVal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SuggestionActivity.this, currentMood, Toast.LENGTH_SHORT).show();
//            }
//        });

        SuggestionList= (RecyclerView) findViewById(R.id.suggestionList);
        SuggestionList.setHasFixedSize(true);
        SuggestionList.setLayoutManager(new LinearLayoutManager(this));

        if(option.equals("Friends")) {
            DisplaySuggestedFriends();
        }
        else if(option.equals("Explore!")){
            DisplaySuggestedGlobalFriends();
        }
        else{

        }

    }

    private void DisplaySuggestedGlobalFriends() {
        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().setQuery(UsersRef, User.class).build();

        FirebaseRecyclerAdapter<User, FriendsViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<User, FriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FriendsViewHolder holder, int position, @NonNull User model) {
                final String userIDs= getRef(position).getKey();

                UsersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() & dataSnapshot.child("currentMood").getValue().toString().equals(currentMood) & !dataSnapshot.getKey().equals(current_user_id)){
                            Log.e("Get Data",currentMood);
                            final String userName = dataSnapshot.child("firstName").getValue().toString();
                            final String email = dataSnapshot.child("email").getValue().toString();

                            holder.setName(userName);
                            holder.setEmail(email);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap<String, String> notificationsData= new HashMap<>();
                                    notificationsData.put("from", current_user_id);
                                    notificationsData.put("type","request");
                                    receiver_user_id= dataSnapshot.getKey();
                                    NotificationsRef.child(receiver_user_id).push().setValue(notificationsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SuggestionActivity.this,"Added to firebase", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        else{
                            RecyclerView.LayoutParams param= (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                            holder.itemView.setVisibility(View.GONE);
                            param.height = 0;
                            param.width = 0;
                            param.bottomMargin=0;
                            param.topMargin=0;
                            holder.itemView.setLayoutParams(param);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_view,viewGroup,false);
                FriendsViewHolder viewHolder = new FriendsViewHolder(view);
                return viewHolder;
            }
        };

        SuggestionList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    private void DisplaySuggestedFriends() {
        FirebaseRecyclerOptions<Friend> options = new FirebaseRecyclerOptions.Builder<Friend>().setQuery(FriendsRef, Friend.class).build();

        FirebaseRecyclerAdapter<Friend, FriendsViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Friend, SuggestionActivity.FriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FriendsViewHolder holder, int position, @NonNull Friend model) {
                final String userIDs= getRef(position).getKey();

                UsersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() & dataSnapshot.child("currentMood").getValue().toString().equals(currentMood)){
                            Log.e("Get Data",currentMood);
                            final String userName = dataSnapshot.child("firstName").getValue().toString();
                            final String email = dataSnapshot.child("email").getValue().toString();

                            holder.setName(userName);
                            holder.setEmail(email);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap<String, String> notificationsData= new HashMap<>();
                                    notificationsData.put("from", current_user_id);
                                    notificationsData.put("type","request");
                                    receiver_user_id= dataSnapshot.getKey();
                                    NotificationsRef.child(receiver_user_id).push().setValue(notificationsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SuggestionActivity.this,"Added to firebase", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });

                        }
                        else{
                            RecyclerView.LayoutParams param= (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                            holder.itemView.setVisibility(View.GONE);
                            param.height = 0;
                            param.width = 0;
                            param.bottomMargin=0;
                            param.topMargin=0;
                            holder.itemView.setLayoutParams(param);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_view,viewGroup,false);
                FriendsViewHolder viewHolder = new FriendsViewHolder(view);
                return viewHolder;
            }
        };

        SuggestionList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setName(String name){
            TextView user_name=(TextView) itemView.findViewById(R.id.user_name);
            user_name.setText(name);
        }

        public void setEmail(String email){
            TextView user_email= (TextView) itemView.findViewById(R.id.user_email);
            user_email.setText(email);
        }
    }

}
