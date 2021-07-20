package com.example.acadup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class payment_inquiry extends AppCompatActivity {
    TextView codingSelect,codingDeselect,roboticsSelect,roboticsDeselect;
    TextView heading_fundamental,amount,classNumber,moneyClass;
    TextView beginnerSelected,beginnerDeselected,intermediateSelected,intermediateDeselected,advancedSelected,advancedDeselected;
    ConstraintLayout demoChoice;
    int robotics=0;
    int coding=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_payment_inquiry);
        demoChoice=findViewById(R.id.demo_choice);
        heading_fundamental=findViewById(R.id.heading_fundamental);
        amount=findViewById(R.id.amount);
        classNumber=findViewById(R.id.classNumber);
        moneyClass=findViewById(R.id.moneyClass);

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
                coding=0;
                robotics=1;
                codingSelect.setVisibility(View.GONE);
                codingDeselect.setVisibility(View.VISIBLE);
                if(robotics==1)
                {
                    heading_fundamental.setText("Robotics Beginner");
                    amount.setText("Rs. 12,999");
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 700/- per session");
                }
                else
                {
                    heading_fundamental.setText("Coding Beginner");
                    amount.setText("Rs. 11,999");
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 600/- per session");
                }
                beginnerDeselected.setVisibility(View.GONE);
                beginnerSelected.setVisibility(View.VISIBLE);
                intermediateDeselected.setVisibility(View.VISIBLE);
                advancedDeselected.setVisibility(View.VISIBLE);
                intermediateSelected.setVisibility(View.GONE);
                advancedSelected.setVisibility(View.GONE);
            }
        });
        codingDeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roboticsDeselect.setVisibility(View.VISIBLE);
                roboticsSelect.setVisibility(View.GONE);
                coding=1;
                robotics=0;
                codingSelect.setVisibility(View.VISIBLE);
                codingDeselect.setVisibility(View.GONE);
                if(robotics==1)
                {
                    heading_fundamental.setText("Robotics Beginner");
                    amount.setText("Rs. 12,999");
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 700/- per session");
                }
                else
                {
                    heading_fundamental.setText("Coding Beginner");
                    amount.setText("Rs. 11,999");
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 600/- per session");
                }
                beginnerDeselected.setVisibility(View.GONE);
                beginnerSelected.setVisibility(View.VISIBLE);
                intermediateDeselected.setVisibility(View.VISIBLE);
                advancedDeselected.setVisibility(View.VISIBLE);
                intermediateSelected.setVisibility(View.GONE);
                advancedSelected.setVisibility(View.GONE);
            }
        });

        beginnerDeselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(robotics==1)
                {
                    heading_fundamental.setText("Robotics Beginner");
                    amount.setText("Rs. 12,999");
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 700/- per session");
                }
                else
                {
                    heading_fundamental.setText("Coding Beginner");
                    amount.setText("Rs. 11,999");
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 600/- per session");
                }
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
                if(robotics==1)
                {
                    heading_fundamental.setText("Robotics Intermediate");
                    amount.setText("Rs. 15,999");
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 800/- per session");
                }
                else
                {
                    heading_fundamental.setText("Coding Intermediate");
                    amount.setText("Rs. 14,999");
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 800/- per session");
                }
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
                if(robotics==1)
                {
                    heading_fundamental.setText("Robotics Adavanced");
                    amount.setText("Rs. 20,999");
                    classNumber.setText("50");
                    moneyClass.setText("Rs. 1000/- per session");
                }
                else
                {
                    heading_fundamental.setText("Coding Adavanced");
                    amount.setText("Rs. 18,999");
                    classNumber.setText("50");
                    moneyClass.setText("Rs. 900/- per session");
                }
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