package com.example.juniordesign.chemistry;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText confirmPass;
    EditText password;
    Button buttonAdd;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        email= (EditText) findViewById(R.id.editText6);
        password= (EditText) findViewById(R.id.editText7);
        confirmPass= (EditText) findViewById(R.id.editText7);
        buttonAdd= (Button) findViewById(R.id.button9);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            startActivity(new Intent(RegisterActivity.this,MenuActivity.class));
        }
    }

    private void addUser(){

        String email1= email.getText().toString().trim();
        String password1= password.getText().toString().trim();
        String password2= confirmPass.getText().toString().trim();

        if(!TextUtils.isEmpty(email1) & !TextUtils.isEmpty(password1)){
            if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                email.setError("Please enter a valid email");
                email.requestFocus();
                return;
            }

            if(password1.length()<6){
                password.setError("Minimum length of password should be 6");
                password.requestFocus();
                return;
            }

            if(!password.equals(confirmPass)){
                confirmPass.setError("Password didn't match");
                confirmPass.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email1,password1);
            mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(RegisterActivity.this, SignUpActivity.class));
                    }
                }
            });



        }

    }
}
