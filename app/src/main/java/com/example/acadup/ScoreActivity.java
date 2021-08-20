package com.example.acadup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acadup.SendMail.JavaMailAPI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    String top_text,nameTxt,userId;
    FirebaseUser users;
    int marks=0;
    String sub,classNum,chapterName,quizName;


    FirebaseFirestore fireStore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    Map<String,Object> user ;
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

//        Toast.makeText(this, unAttempted+" "+total, Toast.LENGTH_LONG).show();

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();
        users = firebaseAuth.getCurrentUser();
        DocumentReference ref=fireStore.collection("users").document(userId);
        ref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        nameTxt=documentSnapshot.getString("firstName");
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

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

        if((total-unAttempted)!=0) {
            accuracy = ((points * 100) / (total - unAttempted));
        }
        else{
            accuracy = 0;
        }
        correctAns.setText("Correct Answers:"+points);
        wrongAns.setText("Wrong Answers:"+((total-unAttempted)-points));
        attempt.setText("Attempted:"+(total-unAttempted));

        marks=points*4;

        progressAcc.setProgress(accuracy);
        progressAccuTxt.setText(accuracy+"%");
        textProgressMarks.setText(marks+" / "+(total*4));


        user= new HashMap<>();
        firebaseAuth= FirebaseAuth.getInstance();
        fireStore= FirebaseFirestore.getInstance();
        documentReference = fireStore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("Attempted Tests").document(top_text);
        user.put("name",heading.getText().toString());
        user.put("score",textProgressMarks.getText().toString());
        user.put("accuracy",accuracy);
        user.put("correct answers",correctAns.getText().toString());
        user.put("wrong answers",wrongAns.getText().toString());
        user.put("attempted questions",total-unAttempted+"/"+total);
        user.put("date", Calendar.getInstance().getTime());
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sendEmail();
                Toast.makeText(ScoreActivity.this,"added data: ",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ScoreActivity.this,"not added",Toast.LENGTH_SHORT).show();

            }
        });


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

        int remarkCal=(points*100)/total;
        if(remarkCal>=0 && remarkCal<=40){
            remarkImg.setImageResource(R.drawable.cry);
            remarkTxt.setText("Not so good , Keep hardworking");
        }
        else if(remarkCal>40 && remarkCal<=75){
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

    private void sendEmail() {

        String showName=firebaseAuth.getInstance().getCurrentUser().getDisplayName();


        JavaMailAPI javaMailAPI=new JavaMailAPI(this,firebaseAuth.getInstance().getCurrentUser().getEmail(),"Test Result",
                "Dear "+nameTxt
                        +",\nYou have successfully completed an assessment test on the topic-"
                        +"\n"+heading.getText().toString()
                        +"\n\nHere is a quick report on the performance breakup: "
                        +"\nTotal Questions: "+total
                        +"\nTotal Attempted: "+(total-unAttempted)
                        +"\nTotal Correct: "+points
                        +"\nTotal Incorrect: "+((total-unAttempted)-points)
                        + "\nObtained Marks: "+marks+"/"+(total*4)
                        +"\nAccuracy: "+accuracy+"%"
                        +"\n\nTo view complete details and answer key,please login to your account."
                        +"\n\nFor any concern, Please reach us to our team members at 08584074448 or write to us at assessment@acadup.in"
                        +"\nHappy learning! :)"
                        +"\nRegards,"
                        +"\nTeam AcadUp");
        javaMailAPI.execute();
    }




}