package com.example.juniordesign.chemistry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class FindActivity extends AppCompatActivity {
    RadioGroup searchOptions;
    RadioButton selectedOption;
    Spinner mood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        searchOptions= (RadioGroup) findViewById(R.id.searchOptions);

        mood = (Spinner) findViewById(R.id.mood);
        Button find = (Button) findViewById(R.id.findfriendbutton);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentMood = mood.getSelectedItem().toString();
                int selectedId= searchOptions.getCheckedRadioButtonId();
                selectedOption=(RadioButton) findViewById(selectedId);
                String option= selectedOption.getText().toString();
                Intent intent = new Intent(FindActivity.this, SuggestionActivity.class);
                intent.putExtra("mood",currentMood);
                intent.putExtra("searchOption",option);
                startActivity(intent);
//                Toast.makeText(FindActivity.this, selectedOption.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
