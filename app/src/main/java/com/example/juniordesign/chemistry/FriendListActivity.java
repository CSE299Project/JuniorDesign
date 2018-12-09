package com.example.juniordesign.chemistry;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendListActivity extends AppCompatActivity {

    private RecyclerView myFriends;
    private DatabaseReference FriendsRef, UsersRef;
    private FirebaseAuth mAuth;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        mAuth=FirebaseAuth.getInstance();
        current_user_id= mAuth.getCurrentUser().getUid();

        FriendsRef= FirebaseDatabase.getInstance().getReference().child("friends").child(current_user_id);
        UsersRef= FirebaseDatabase.getInstance().getReference().child("users");

        myFriends= (RecyclerView) findViewById(R.id.friend_list);
        myFriends.setHasFixedSize(true);
        myFriends.setLayoutManager(new LinearLayoutManager(this));

        DisplayFriends();

    }

    private void DisplayFriends() {

        FirebaseRecyclerOptions<Friend> options = new FirebaseRecyclerOptions.Builder<Friend>().setQuery(FriendsRef, Friend.class).build();

        FirebaseRecyclerAdapter<Friend, FriendsViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Friend, FriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FriendsViewHolder holder, int position, @NonNull Friend model) {
                final String userIDs= getRef(position).getKey();

                UsersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            final String userName = dataSnapshot.child("firstName").getValue().toString();
                            final String email = dataSnapshot.child("email").getValue().toString();

                            holder.setName(userName);
                            holder.setEmail(email);
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

        myFriends.setAdapter(firebaseRecyclerAdapter);
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

//        public void setDate(String date){
//            TextView friendsDate= (TextView) itemView.findViewById(R.id.date);
//            friendsDate.setText(date);
//        }
    }

}
