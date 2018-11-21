package com.example.juniordesign.chemistry;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    Button buttonAdd;
    EditText age;
    EditText phone;

    DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        firstName= (EditText) findViewById(R.id.editText);
        lastName= (EditText) findViewById(R.id.editText2);
        buttonAdd= (Button) findViewById(R.id.button);
        age= (EditText) findViewById(R.id.editText3);
        phone= (EditText) findViewById(R.id.editText5);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserInfo();
            }
        });

    }


    private void addUserInfo(){
        String fname = firstName.getText().toString().trim();
        String lname= lastName.getText().toString().trim();
        String ageString= age.getText().toString().trim();
        int age= Integer.parseInt(ageString);
        String phone1= phone.getText().toString().trim();

        if(!TextUtils.isEmpty(fname) & !TextUtils.isEmpty(lname) & !TextUtils.isEmpty(ageString) & !TextUtils.isEmpty(phone1)){

            //String id=databaseUsers.push().getKey();
            String id = mAuth.getCurrentUser().getUid();
            String email = mAuth.getCurrentUser().getEmail();
            User user= new User(fname, lname,age,email,phone1);
            databaseUsers.child(id).setValue(user);

            Toast.makeText(this,"User added successfully",Toast.LENGTH_LONG).show();

            sendToMenu();

        }else{
            Toast.makeText(this, "You should enter all the required data",Toast.LENGTH_LONG).show();
        }
    }

    private void sendToMenu(){
        startActivity(new Intent(SignUpActivity.this, MenuActivity.class));
    }

}
