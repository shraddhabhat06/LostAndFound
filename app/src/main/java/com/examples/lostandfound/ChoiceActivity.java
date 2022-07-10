package com.examples.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceActivity extends AppCompatActivity {
    Button btnlost,btnfound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
         btnlost= findViewById(R.id.idBtnLost);
         btnfound=findViewById(R.id.idBtnFound);
        btnlost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiceActivity.this,LostActivity.class);
                startActivity(intent);
            }
        });
        btnfound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiceActivity.this,FoundActivity.class);
                startActivity(intent);

            }
        });

        }
    }
