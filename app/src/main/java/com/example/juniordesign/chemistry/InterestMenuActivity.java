package com.example.juniordesign.chemistry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InterestMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_menu);

        Button addInterest= (Button) findViewById(R.id.addInt);
        Button myInterest= (Button) findViewById(R.id.myInterest);

        addInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InterestMenuActivity.this,InterestActivity.class));
            }
        });

        myInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InterestMenuActivity.this,InterestListActivity.class));
            }
        });

    }
}
