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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddFriendActivity extends AppCompatActivity {

    private RecyclerView UserList;
    private DatabaseReference mDatabase;
    private DatabaseReference databaseFriends;
    private FirebaseAuth mAuth;
    private String saveCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("users");
        databaseFriends = FirebaseDatabase.getInstance().getReference("friends");
        mAuth = FirebaseAuth.getInstance();

        UserList=(RecyclerView) findViewById(R.id.user_list);
        UserList.setHasFixedSize(true);
        UserList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user= mAuth.getCurrentUser();
        final String currentUser_id= user.getUid();

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().setQuery(mDatabase, User.class).build();

        FirebaseRecyclerAdapter<User, UserViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, final int position, @NonNull User model) {
                holder.setName(model.getFirstName());
                holder.setEmail(model.getEmail());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user_id = getRef(position).getKey();
                        FirebaseUser user= mAuth.getCurrentUser();
                        final String currentUser_id= user.getUid();

                        // Friends since DD-MM-YYYY

                        Calendar calForDate= Calendar.getInstance();
                        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                        saveCurrentDate = currentDate.format(calForDate.getTime());


                       // Toast.makeText(AddFriendActivity.this, "This user's id: "+ user_id, Toast.LENGTH_SHORT).show();

                        databaseFriends.child(currentUser_id).child(user_id).child("date").setValue(saveCurrentDate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AddFriendActivity.this, "User added to Friends", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(AddFriendActivity.this, "Failed to add", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_view,viewGroup,false);
                UserViewHolder viewHolder = new UserViewHolder(view);
                return viewHolder;
            }
        };
        UserList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        View userView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userView= itemView;

        }

        public void setName(String name){
            TextView user_name=(TextView) userView.findViewById(R.id.user_name);
            user_name.setText(name);
        }

        public void setEmail(String email){
            TextView user_email= (TextView) userView.findViewById(R.id.user_email);
            user_email.setText(email);
        }
    }


}
