package com.example.acadup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SubjectOptions extends AppCompatActivity {
    LinearLayout NCERT_SolutionLayout,pdfLayout,tutorialsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_options);
        getSupportActionBar().hide();
        NCERT_SolutionLayout=findViewById(R.id.ncert_sol_layout);
        pdfLayout=findViewById(R.id.pdfLayout);
        tutorialsLayout=findViewById(R.id.tutorialsCard);
        NCERT_SolutionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubjectOptions.this,NcertSolution.class));
            }
        });
        pdfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(SubjectOptions.this,  ConceptPDFs.class));

            }
        });
        tutorialsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubjectOptions.this,  Tutorials.class));

            }
        });
    }
}