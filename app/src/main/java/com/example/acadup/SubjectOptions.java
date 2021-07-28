package com.example.acadup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SubjectOptions extends AppCompatActivity {
    LinearLayout NCERT_SolutionLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_options);
        getSupportActionBar().hide();
        NCERT_SolutionLayout=findViewById(R.id.ncert_sol_layout);
        NCERT_SolutionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubjectOptions.this,TopicVideoLists.class));
            }
        });
    }
}