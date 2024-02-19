package com.example.japanesereviewer123;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class N4DashboardActivity extends AppCompatActivity {


    Button btnkanji,btngrammar,btnvocabulary;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n4dashboard_layout);


        btnkanji =  findViewById(R.id.idbtnkanji);
        btngrammar = findViewById(R.id.idbtngrammar);
        btnvocabulary = findViewById(R.id.idbtnvocab);



        btnkanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(N4DashboardActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });


        btnvocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(N4DashboardActivity.this, Vocab_Activity.class);
                startActivity(intent);
            }
        });


        btngrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(N4DashboardActivity.this,GrammarActivity.class);
                startActivity(intent);


            }
        });





    }
}
