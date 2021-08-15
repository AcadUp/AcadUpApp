package com.example.acadup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {
    TextView correctAns,wrongAns,attempt,retake,review,quit,progressAccuTxt,textProgressMarks,textProgressTime,remarkTxt;
    ImageView remarkImg;
    ProgressBar progressAcc,progressTime;

    int points,total,unAttempted,totalTimes,ACTIVITY_CODE;
    int accuracy;
    String doc,times;
    int minTime,secTime;
    String calTime;
    TextView heading;

    String top_text;
    String sub,classNum,chapterName,quizName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_score);
        points=getIntent().getIntExtra("number",0);
        total=getIntent().getIntExtra("total",0);

        ACTIVITY_CODE=getIntent().getIntExtra("from_which_activity",0);
        top_text=getIntent().getStringExtra("heading");
        times=getIntent().getStringExtra("times");
        unAttempted=getIntent().getIntExtra("unAttempted",0);
        totalTimes=getIntent().getIntExtra("TotalTimes",0);

        if(ACTIVITY_CODE==100){
            sub=getIntent().getStringExtra("subject");
            classNum=getIntent().getStringExtra("classNum");
            chapterName=getIntent().getStringExtra("chapterName");
            quizName=getIntent().getStringExtra("quiz_name");
        }
        else if(ACTIVITY_CODE==101){
            quizName=getIntent().getStringExtra("quiz_name");
            doc=getIntent().getStringExtra("doc_name");
        }
        else if(ACTIVITY_CODE==102){

            sub=getIntent().getStringExtra("subject");
            classNum=getIntent().getStringExtra("classNum");
            chapterName=getIntent().getStringExtra("chapterName");
        }

        heading=findViewById(R.id.heading);
        correctAns=findViewById(R.id.correctAns);
        wrongAns=findViewById(R.id.wrongAns);
        attempt=findViewById(R.id.attempted);
        retake=findViewById(R.id.retake);
        review=findViewById(R.id.review);
        quit=findViewById(R.id.quit);
        progressAcc=findViewById(R.id.progressAccuracy);
        progressAccuTxt=findViewById(R.id.textProgressAccuracy);
        textProgressMarks=findViewById(R.id.textProgressMarks);
        textProgressTime=findViewById(R.id.textProgressTime);
        progressTime=findViewById(R.id.progressTime);
        remarkTxt=findViewById(R.id.remarkTxt);
        remarkImg=findViewById(R.id.remarkImg);

        heading.setText(top_text+" Test");

        accuracy=((points*100)/total);
        correctAns.setText("Correct Answers:"+points);
        wrongAns.setText("Wrong Answers:"+((total-unAttempted)-points));
        attempt.setText("Attempted:"+(total-unAttempted));

        progressAcc.setProgress(accuracy);
        progressAccuTxt.setText(accuracy+"%");
        textProgressMarks.setText(points+" / "+total);


        minTime=(totalTimes-1)-(Integer.parseInt(times.substring(0,times.indexOf(':'))));
        secTime=60-(Integer.parseInt(times.substring(times.indexOf(':')+1)));
        if(minTime<10){
            calTime="0"+minTime+":";
        }
        else{
            calTime=minTime+":";
        }
        if(secTime<10){
            calTime=calTime+"0"+secTime;
        }
        else{
            calTime=calTime+secTime;
        }
        textProgressTime.setText(calTime);
        float t=Float.parseFloat(calTime.substring(0,calTime.indexOf(':'))+'.'+calTime.substring(calTime.indexOf(':')+1));
        progressTime.setMax(totalTimes*100);
        progressTime.setProgress((int) (t*100));

        if(accuracy>=0 && accuracy<=40){
            remarkImg.setImageResource(R.drawable.cry);
            remarkTxt.setText("Not so good , Keep hardworking");
        }
        else if(accuracy>40 && accuracy<=75){
            remarkImg.setImageResource(R.drawable.good);
            remarkTxt.setText("Good , Keep going");
        }
        else {
            remarkImg.setImageResource(R.drawable.medal);
            remarkTxt.setText("Brilliant , Keep going");
        }
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ScoreActivity.this,QuizIntroActivity.class);
                intent.putExtra("quiz_time",totalTimes);
                intent.putExtra("from_which_activity",ACTIVITY_CODE);
                if(ACTIVITY_CODE==100){
                    intent.putExtra("quiz_time",totalTimes);
                    intent.putExtra("subject",sub);
                    intent.putExtra("classNum",classNum);
                    intent.putExtra("chapterName",chapterName);
                    intent.putExtra("quiz_name",quizName);
                }
                if(ACTIVITY_CODE==101){
                    intent.putExtra("quiz_time",totalTimes);
                    intent.putExtra("quiz_name",quizName);
                    intent.putExtra("doc_name",doc);
                }
                if(ACTIVITY_CODE==102){
                    intent.putExtra("subject",sub);
                    intent.putExtra("classNum",classNum);
                    intent.putExtra("chapterName",chapterName);
                    intent.putExtra("quiz_time",totalTimes);
                }
                startActivity(intent);
                finish();
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScoreActivity.this, "review clicked", Toast.LENGTH_SHORT).show();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScoreActivity.this,HomeActivity.class));

            }
        });
    }


}