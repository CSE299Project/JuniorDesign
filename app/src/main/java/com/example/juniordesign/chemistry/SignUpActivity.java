package com.example.juniordesign.chemistry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    Button buttonAdd;
    EditText age;
    EditText email;
    EditText phone;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        firstName= (EditText) findViewById(R.id.editText);
        lastName= (EditText) findViewById(R.id.editText2);
        buttonAdd= (Button) findViewById(R.id.button);
        age= (EditText) findViewById(R.id.editText3);
        email= (EditText) findViewById(R.id.editText4);
        phone= (EditText) findViewById(R.id.editText5);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

    }

    private void addUser(){
        String fname = firstName.getText().toString().trim();
        String lname= lastName.getText().toString().trim();
        String ageString= age.getText().toString().trim();
        String email1= email.getText().toString().trim();
        int age= Integer.parseInt(ageString);
        String phone1= phone.getText().toString().trim();

        if(!TextUtils.isEmpty(fname) & !TextUtils.isEmpty(lname) & !TextUtils.isEmpty(ageString) & !TextUtils.isEmpty(email1)){

            String id=databaseUsers.push().getKey();
            User user= new User(id, fname, lname,age,email1,phone1);
            databaseUsers.child(id).setValue(user);

            Toast.makeText(this,"User added successfully",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "You should enter all the required data",Toast.LENGTH_LONG).show();
        }
    }
}
