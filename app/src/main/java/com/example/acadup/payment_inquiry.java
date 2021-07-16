package com.example.acadup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class payment_inquiry extends AppCompatActivity {
    TextView codingSelect,codingDeselect,roboticsSelect,roboticsDeselect;
    TextView beginnerSelected,beginnerDeselected,intermediateSelected,intermediateDeselected,advancedSelected,advancedDeselected;
    ConstraintLayout demoChoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_payment_inquiry);
        demoChoice=findViewById(R.id.demo_choice);

        demoChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(payment_inquiry.this,demo_choice.class));
            }
        });

        codingSelect=findViewById(R.id.codingColored);
        codingDeselect=findViewById(R.id.codingUnColored);
        roboticsSelect=findViewById(R.id.roboticsColored);
        roboticsDeselect=findViewById(R.id.roboticsUnColored);
        beginnerSelected=findViewById(R.id.beginnerColored);
        beginnerDeselected=findViewById(R.id.beginnerUnColored);
        intermediateSelected=findViewById(R.id.intermediateColored);
        intermediateDeselected=findViewById(R.id.intermediateUnColored);
        advancedSelected=findViewById(R.id.advancedColored);
        advancedDeselected=findViewById(R.id.advancedUnColored);
        codingSelect.setVisibility(View.VISIBLE);
        codingDeselect.setVisibility(View.GONE);
        roboticsSelect.setVisibility(View.GONE);
        roboticsDeselect.setVisibility(View.VISIBLE);

        beginnerSelected.setVisibility(View.VISIBLE);
        beginnerDeselected.setVisibility(View.GONE);
        intermediateDeselected.setVisibility(View.VISIBLE);
        intermediateSelected.setVisibility(View.GONE);
        advancedDeselected.setVisibility(View.VISIBLE);
        advancedSelected.setVisibility(View.GONE);

        roboticsDeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roboticsDeselect.setVisibility(View.GONE);
                roboticsSelect.setVisibility(View.VISIBLE);
                codingSelect.setVisibility(View.GONE);
                codingDeselect.setVisibility(View.VISIBLE);
            }
        });
        codingDeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roboticsDeselect.setVisibility(View.VISIBLE);
                roboticsSelect.setVisibility(View.GONE);
                codingSelect.setVisibility(View.VISIBLE);
                codingDeselect.setVisibility(View.GONE);
            }
        });

        beginnerDeselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginnerDeselected.setVisibility(View.GONE);
                beginnerSelected.setVisibility(View.VISIBLE);
                intermediateDeselected.setVisibility(View.VISIBLE);
                advancedDeselected.setVisibility(View.VISIBLE);
                intermediateSelected.setVisibility(View.GONE);
                advancedSelected.setVisibility(View.GONE);
            }
        });
        intermediateDeselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginnerDeselected.setVisibility(View.VISIBLE);
                beginnerSelected.setVisibility(View.GONE);
                intermediateDeselected.setVisibility(View.GONE);
                advancedDeselected.setVisibility(View.VISIBLE);
                intermediateSelected.setVisibility(View.VISIBLE);
                advancedSelected.setVisibility(View.GONE);
            }
        });
        advancedDeselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginnerDeselected.setVisibility(View.VISIBLE);
                beginnerSelected.setVisibility(View.GONE);
                intermediateDeselected.setVisibility(View.VISIBLE);
                advancedDeselected.setVisibility(View.GONE);
                intermediateSelected.setVisibility(View.GONE);
                advancedSelected.setVisibility(View.VISIBLE);
            }
        });
    }
}