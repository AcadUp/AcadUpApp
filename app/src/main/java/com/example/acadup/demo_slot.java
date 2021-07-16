package com.example.acadup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class demo_slot extends AppCompatActivity {
    String sub,slot_times;
    int c1,c2,c3,c4,c5,c6,c7,c8;
    TextView subHeading,times,desc_demo;
    CardView demoCard;
    Button cancelTxt,rescheduleTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_demo_slot);
        subHeading=findViewById(R.id.sub_heading);
        times=findViewById(R.id.times);
        demoCard=findViewById(R.id.card_demo);
        cancelTxt=findViewById(R.id.cancelTxt);
        rescheduleTxt=findViewById(R.id.rescheduleTxt);
        desc_demo=findViewById(R.id.desc_demo);

        c1=getIntent().getIntExtra("count1",0);
        c2=getIntent().getIntExtra("count2",0);
        c3=getIntent().getIntExtra("count3",0);
        c4=getIntent().getIntExtra("count4",0);
        c5=getIntent().getIntExtra("count5",0);
        c6=getIntent().getIntExtra("count6",0);
        c7=getIntent().getIntExtra("count7",0);
        c8=getIntent().getIntExtra("count8",0);
        slot_times=getIntent().getStringExtra("slot_time");

        if(c1==0 && c2==0 && c3==0 && c4==0&& c5==0&&c6==0&&c7==0&&c8==0) {
            sub = getIntent().getStringExtra("Sub1");
        }
        else{
            if(c1==1){
                sub = getIntent().getStringExtra("Sub1");
            }
            else if(c2==1){
                sub = getIntent().getStringExtra("Sub2");
            }
            else if(c3==1){
                sub = getIntent().getStringExtra("Sub3");
            }
            else if(c4==1){
                sub = getIntent().getStringExtra("Sub4");
            }
            else if(c5==1){
                sub = getIntent().getStringExtra("Sub5");
            }
            else if(c6==1){
                sub = getIntent().getStringExtra("Sub6");
            }
            else if(c7==1){
                sub = getIntent().getStringExtra("Sub7");
            }
            else if(c8==1){
                sub = getIntent().getStringExtra("Sub8");
            }
        }
        subHeading.setText(sub);
        times.setText(slot_times);
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demoCard.setVisibility(View.GONE);
                desc_demo.setVisibility(View.GONE);
            }
        });
        rescheduleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(demo_slot.this,demo_choice.class));
                finish();
            }
        });
    }
}