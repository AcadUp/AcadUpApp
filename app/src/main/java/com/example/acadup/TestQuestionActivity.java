package com.example.acadup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acadup.Models.QuestionModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestQuestionActivity extends AppCompatActivity {
    String subTxt,docName;
    int timeTxt;
    String sendTime;
    int ACTIVITY_CODE;

    AppCompatButton nextBtn,prevBtn;
    ArrayList<QuestionModel> list;
    int index=0;
    int correctNumber=0,unAttempted=0;
    int click1=0,click2=0,click3=0,click4=0;
    LinearLayout op1Lay,op2Lay,op3Lay,op4Lay;
    int countNum[];
    String txt;
    CountDownTimer timer;
    FirebaseFirestore fireStore;
    ProgressBar progressBar;
    TextView timerTxt;
    ConstraintLayout constraintLayout;
    TextView question,option1,option2,option3,option4,countingTxt;

    String subject,classNum,chapterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_question);

        ACTIVITY_CODE=getIntent().getIntExtra("from_which_activity",0);
        if(ACTIVITY_CODE==100){
            subject=getIntent().getStringExtra("subject");
            classNum=getIntent().getStringExtra("classNum");
            chapterName=getIntent().getStringExtra("chapterName");
            subTxt=getIntent().getStringExtra("quiz_name");
            timeTxt=getIntent().getIntExtra("time_txt",0);
            getSupportActionBar().setTitle(subTxt+" Test");
        }
        if(ACTIVITY_CODE==101){
            docName=getIntent().getStringExtra("doc_name");
            subTxt=getIntent().getStringExtra("quiz_name");
            timeTxt=getIntent().getIntExtra("time_txt",0);
            getSupportActionBar().setTitle(subTxt+" Test");
        }
        if(ACTIVITY_CODE==102){
            subject=getIntent().getStringExtra("subject");
            classNum=getIntent().getStringExtra("classNum");
            chapterName=getIntent().getStringExtra("chapterName");
            timeTxt=getIntent().getIntExtra("time_txt",0);
            getSupportActionBar().setTitle(chapterName+" Test");
        }

        progressBar=findViewById(R.id.progress);
        constraintLayout=findViewById(R.id.constraint);
        nextBtn=findViewById(R.id.nextBtn);
        prevBtn=findViewById(R.id.prevBtn);
        question=findViewById(R.id.question);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        op1Lay=findViewById(R.id.op1Lay);
        op2Lay=findViewById(R.id.op2Lay);
        op3Lay=findViewById(R.id.op3Lay);
        op4Lay=findViewById(R.id.op4Lay);
        countingTxt=findViewById(R.id.countingTxt);
        timerTxt=findViewById(R.id.timerTxt);

        progressBar.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.GONE);
        list=new ArrayList<>();

        fireStore=FirebaseFirestore.getInstance();

        if(ACTIVITY_CODE==102){
            DocumentReference db;
            db = fireStore.collection("NCERT").document(subject)
                    .collection(classNum).document(chapterName).collection("test").document("quiz_questions");
            db.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Map<String, Object> map1 = documentSnapshot.getData();
                                Object[] key = map1.keySet().toArray();
                                for (int i = 0; i < map1.size(); i++) {
                                    QuestionModel quesModel = documentSnapshot.get(key[i].toString(), QuestionModel.class);
                                    list.add(quesModel);
                                }
//                        Toast.makeText(QuestionActivity.this, String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                                if (index == 0) {
                                    prevBtn.setVisibility(View.INVISIBLE);
                                } else {
                                    prevBtn.setVisibility(View.VISIBLE);
                                }
                                if (index >= list.size() - 1) {
                                    nextBtn.setText("FINISH");
                                } else {
                                    nextBtn.setText("NEXT");
                                }
                                countNum = new int[list.size()];
                                for (int i = 0; i < list.size(); i++) {
                                    countNum[i] = 0;
                                }
                                nextQuestion();
                                progressBar.setVisibility(View.GONE);
                                constraintLayout.setVisibility(View.VISIBLE);
                                timerFunc();
                                timer.start();
                            } else {
//                            startActivity(new Intent(TestQuestionActivity.this,HomeActivity.class));
                                Toast.makeText(TestQuestionActivity.this, "No question found", Toast.LENGTH_SHORT).show();
//                            finish();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }

        if(ACTIVITY_CODE==101) {
            DocumentReference db;
            db = fireStore.collection("PracticeTest").document("Practice")
                    .collection("Quiz").document(docName);
            db.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Map<String, Object> map1 = documentSnapshot.getData();
                                Object[] key = map1.keySet().toArray();
                                for (int i = 0; i < map1.size(); i++) {
                                    QuestionModel quesModel = documentSnapshot.get(key[i].toString(), QuestionModel.class);
                                    list.add(quesModel);
                                }
//                        Toast.makeText(QuestionActivity.this, String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                                if (index == 0) {
                                    prevBtn.setVisibility(View.INVISIBLE);
                                } else {
                                    prevBtn.setVisibility(View.VISIBLE);
                                }
                                if (index >= list.size() - 1) {
                                    nextBtn.setText("FINISH");
                                } else {
                                    nextBtn.setText("NEXT");
                                }
                                countNum = new int[list.size()];
                                for (int i = 0; i < list.size(); i++) {
                                    countNum[i] = 0;
                                }
                                nextQuestion();
                                progressBar.setVisibility(View.GONE);
                                constraintLayout.setVisibility(View.VISIBLE);
                                timerFunc();
                                timer.start();
                            } else {
//                            startActivity(new Intent(TestQuestionActivity.this,HomeActivity.class));
                                Toast.makeText(TestQuestionActivity.this, "No question found", Toast.LENGTH_SHORT).show();
//                            finish();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        if(ACTIVITY_CODE==100){
            DocumentReference db;
            db = fireStore.collection("NCERT").document(subject)
                    .collection(classNum).document(chapterName).collection("Topics")
                    .document(subTxt)
                    .collection("Quiz").document("quiz_questions");
            db.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Map<String, Object> map1 = documentSnapshot.getData();
                                Object[] key = map1.keySet().toArray();
                                for (int i = 0; i < map1.size(); i++) {
                                    QuestionModel quesModel = documentSnapshot.get(key[i].toString(), QuestionModel.class);
                                    list.add(quesModel);
                                }
//                        Toast.makeText(QuestionActivity.this, String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                                if (index == 0) {
                                    prevBtn.setVisibility(View.INVISIBLE);
                                } else {
                                    prevBtn.setVisibility(View.VISIBLE);
                                }
                                if (index >= list.size() - 1) {
                                    nextBtn.setText("FINISH");
                                } else {
                                    nextBtn.setText("NEXT");
                                }
                                countNum = new int[list.size()];
                                for (int i = 0; i < list.size(); i++) {
                                    countNum[i] = 0;
                                }
                                nextQuestion();
                                progressBar.setVisibility(View.GONE);
                                constraintLayout.setVisibility(View.VISIBLE);
                                timerFunc();
                                timer.start();
                            } else {
//                            startActivity(new Intent(TestQuestionActivity.this,HomeActivity.class));
                                Toast.makeText(TestQuestionActivity.this, "No question found", Toast.LENGTH_SHORT).show();
//                            finish();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        countingTxt.setText(index+1+"/"+list.size());
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index<list.size()){
                    if(click1==1){
                        countNum[index]=1;
                    }
                    else if(click2==1){
                        countNum[index]=2;
                    }
                    else if(click3==1){
                        countNum[index]=3;
                    }
                    else if(click4==1){
                        countNum[index]=4;
                    }
                    click1=0;click2=0;click3=0;click4=0;
                    index++;
                }
                if(index<list.size()) {
                    if (countNum[index] == 1) {
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option1.setTextColor(Color.WHITE);
                        option2.setTextColor(Color.BLACK);
                        option3.setTextColor(Color.BLACK);
                        option4.setTextColor(Color.BLACK);
                    } else if (countNum[index] == 2) {
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option2.setTextColor(Color.WHITE);
                        option1.setTextColor(Color.BLACK);
                        option3.setTextColor(Color.BLACK);
                        option4.setTextColor(Color.BLACK);
                    } else if (countNum[index] == 3) {
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option3.setTextColor(Color.WHITE);
                        option2.setTextColor(Color.BLACK);
                        option1.setTextColor(Color.BLACK);
                        option4.setTextColor(Color.BLACK);
                    } else if (countNum[index] == 4) {
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option4.setTextColor(Color.WHITE);
                        option2.setTextColor(Color.BLACK);
                        option3.setTextColor(Color.BLACK);
                        option1.setTextColor(Color.BLACK);
                    } else if (countNum[index] == 0) {
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option4.setTextColor(Color.BLACK);
                        option2.setTextColor(Color.BLACK);
                        option3.setTextColor(Color.BLACK);
                        option1.setTextColor(Color.BLACK);
                    }
                }
                if(index==0){
                    prevBtn.setVisibility(View.INVISIBLE);
                }
                else{
                    prevBtn.setVisibility(View.VISIBLE);
                }
                if(index>=list.size()-1){
                    nextBtn.setText("Finish");
                }
                else{
                    nextBtn.setText("Next");
                }
                nextQuestion();
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(index>0){
                    if(click1==1){
                        countNum[index]=1;
                    }
                    else if(click2==1){
                        countNum[index]=2;
                    }
                    else if(click3==1){
                        countNum[index]=3;
                    }
                    else if(click4==1){
                        countNum[index]=4;
                    }
                    index--;
                    click1=0;click2=0;click3=0;click4=0;
                }
                if(index>=0) {
                    if (countNum[index] == 1) {
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option1.setTextColor(Color.WHITE);
                        option2.setTextColor(Color.BLACK);
                        option3.setTextColor(Color.BLACK);
                        option4.setTextColor(Color.BLACK);
                    } else if (countNum[index] == 2) {
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option2.setTextColor(Color.WHITE);
                        option1.setTextColor(Color.BLACK);
                        option3.setTextColor(Color.BLACK);
                        option4.setTextColor(Color.BLACK);
                    } else if (countNum[index] == 3) {
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option3.setTextColor(Color.WHITE);
                        option2.setTextColor(Color.BLACK);
                        option1.setTextColor(Color.BLACK);
                        option4.setTextColor(Color.BLACK);
                    } else if (countNum[index] == 4) {
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option4.setTextColor(Color.WHITE);
                        option2.setTextColor(Color.BLACK);
                        option3.setTextColor(Color.BLACK);
                        option1.setTextColor(Color.BLACK);
                    } else if (countNum[index] == 0) {
                        op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
                        option4.setTextColor(Color.BLACK);
                        option2.setTextColor(Color.BLACK);
                        option3.setTextColor(Color.BLACK);
                        option1.setTextColor(Color.BLACK);
                    }
                }
                if(index==0){
                    prevBtn.setVisibility(View.INVISIBLE);
                }
                else{
                    prevBtn.setVisibility(View.VISIBLE);
                }
                if(index==list.size()){
                    nextBtn.setText("FINISH");
                }
                else{
                    nextBtn.setText("NEXT");
                }
                previousQuestion();
            }
        });
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                click1=1;click2=0;click3=0;click4=0;
                colorChange(1);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click1=0;click2=1;click3=0;click4=0;
                colorChange(2);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click1=0;click2=0;click3=1;click4=0;
                colorChange(3);
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click1=0;click2=0;click3=0;click4=1;
                colorChange(4);
            }
        });

    }
    private void colorChange(int i) {
        if(i==1){
            op1Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
            op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            option1.setTextColor(Color.WHITE);
            option2.setTextColor(Color.BLACK);
            option3.setTextColor(Color.BLACK);
            option4.setTextColor(Color.BLACK);
        }
        else if(i==2){
            op2Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
            op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            option2.setTextColor(Color.WHITE);
            option1.setTextColor(Color.BLACK);
            option3.setTextColor(Color.BLACK);
            option4.setTextColor(Color.BLACK);
        }
        else if(i==3){
            op3Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
            op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            op4Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            option3.setTextColor(Color.WHITE);
            option2.setTextColor(Color.BLACK);
            option1.setTextColor(Color.BLACK);
            option4.setTextColor(Color.BLACK);
        }
        else if(i==4){
            op4Lay.setBackground(getResources().getDrawable(R.drawable.select_bg));
            op3Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            op2Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            op1Lay.setBackground(getResources().getDrawable(R.drawable.option_bg));
            option4.setTextColor(Color.WHITE);
            option2.setTextColor(Color.BLACK);
            option3.setTextColor(Color.BLACK);
            option1.setTextColor(Color.BLACK);
        }
    }


    private void previousQuestion() {
        if(index>=0){
            question.setText(list.get(index).getQuestion());
            option1.setText(list.get(index).getOption1());
            option2.setText(list.get(index).getOption2());
            option3.setText(list.get(index).getOption3());
            option4.setText(list.get(index).getOption4());
            countingTxt.setText(index+1+"/"+list.size());
            StringBuilder va= new StringBuilder();
        }
    }

    void nextQuestion(){
        if(index<list.size()){
            question.setText(list.get(index).getQuestion());
            option1.setText(list.get(index).getOption1());
            option2.setText(list.get(index).getOption2());
            option3.setText(list.get(index).getOption3());
            option4.setText(list.get(index).getOption4());
            countingTxt.setText(index+1+"/"+list.size());

        }
        else{

            for(int i=0;i<list.size();i++){
                if(countNum[i]==list.get(i).getAnswer()){
                    correctNumber++;
                }
                if(countNum[i]==0){
                    unAttempted++;
                }
            }

            Intent intent=new Intent(TestQuestionActivity.this,ScoreActivity.class);
            intent.putExtra("number",correctNumber);
            intent.putExtra("total",list.size());
            intent.putExtra("from_which_activity",ACTIVITY_CODE);
            intent.putExtra("unAttempted",unAttempted);
            intent.putExtra("times",sendTime);
            intent.putExtra("TotalTimes",timeTxt);
            if(ACTIVITY_CODE==100){
                intent.putExtra("subject",subject);
                intent.putExtra("classNum",classNum);
                intent.putExtra("chapterName",chapterName);
                intent.putExtra("quiz_name",subTxt);

                intent.putExtra("heading",subTxt);
            }
            if(ACTIVITY_CODE==101){
                intent.putExtra("quiz_name", subTxt);
                intent.putExtra("doc_name", docName);

                intent.putExtra("heading",subTxt);
            }
            if(ACTIVITY_CODE==102){
                intent.putExtra("subject",subject);
                intent.putExtra("classNum",classNum);
                intent.putExtra("chapterName",chapterName);

                intent.putExtra("heading",chapterName);
            }

            startActivity(intent);
            if(timer!=null){
                timer.cancel();
                timer=null;
            }

            finish();

        }
    }
    private void timerFunc(){
        timer=new CountDownTimer((timeTxt*60*1000),(1000)) {
            @Override
            public void onTick(long l) {
                long minTime=TimeUnit.MILLISECONDS.toMinutes(l);
                String min;
                if(minTime<10){
                    min="0"+minTime;
                }
                else{
                    min=String.valueOf(minTime);
                }
                long secTime=TimeUnit.MILLISECONDS.toSeconds(l) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l));
                String sec;
                if(secTime<10){
                    sec="0"+secTime;
                }
                else{
                    sec=String.valueOf(secTime);
                }
                sendTime=min+":"+sec;
                timerTxt.setText(sendTime);
            }
            
            @Override
            public void onFinish() {
                for(int i=0;i<list.size();i++){
                    if(countNum[i]==list.get(i).getAnswer()){
                        correctNumber++;
                    }
                    if(countNum[i]==0){
                        unAttempted++;
                    }
                }
//            Toast.makeText(this, String.valueOf(correctNumber), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(TestQuestionActivity.this,ScoreActivity.class);
                intent.putExtra("number",correctNumber);
                intent.putExtra("total",list.size());
                intent.putExtra("from_which_activity",ACTIVITY_CODE);
                intent.putExtra("unAttempted",unAttempted);
                intent.putExtra("times",sendTime);
                intent.putExtra("TotalTimes",timeTxt);
                if(ACTIVITY_CODE==100){
                    intent.putExtra("subject",subject);
                    intent.putExtra("classNum",classNum);
                    intent.putExtra("chapterName",chapterName);
                    intent.putExtra("quiz_name",subTxt);

                    intent.putExtra("heading",subTxt);
                }
                if(ACTIVITY_CODE==101){
                    intent.putExtra("quiz_name", subTxt);
                    intent.putExtra("doc_name", docName);

                    intent.putExtra("heading",subTxt);
                }
                if(ACTIVITY_CODE==102){
                    intent.putExtra("subject",subject);
                    intent.putExtra("classNum",classNum);
                    intent.putExtra("chapterName",chapterName);

                    intent.putExtra("heading",chapterName);
                }
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
        finish();
    }
}