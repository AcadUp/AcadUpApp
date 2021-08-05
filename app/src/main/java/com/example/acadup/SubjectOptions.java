package com.example.acadup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubjectOptions extends AppCompatActivity {
    LinearLayout NCERT_SolutionLayout,pdfLayout,tutorialsLayout;
    TextView heading;
    String sub;
    int classNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_options);
        getSupportActionBar().hide();

        sub=getIntent().getStringExtra("subject");
        classNum=getIntent().getIntExtra("class",1);

        NCERT_SolutionLayout=findViewById(R.id.ncert_sol_layout);
        pdfLayout=findViewById(R.id.pdfLayout);
        tutorialsLayout=findViewById(R.id.tutorialsCard);
        heading=findViewById(R.id.heading);
        heading.setText(sub.toUpperCase()+" (Class-"+classNum+")");
        NCERT_SolutionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SubjectOptions.this,NcertSolution.class);
                intent.putExtra("subject",sub);
                intent.putExtra("class",classNum);
                startActivity(intent);
            }
        });
        pdfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SubjectOptions.this,ConceptPDFs.class);
                intent.putExtra("subject",sub);
                intent.putExtra("class",classNum);
                startActivity(intent);

            }
        });
        tutorialsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SubjectOptions.this,Tutorials.class);
                intent.putExtra("subject",sub);
                intent.putExtra("class",classNum);
                startActivity(intent);

            }
        });
    }
}