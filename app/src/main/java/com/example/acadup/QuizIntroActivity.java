package com.example.acadup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizIntroActivity extends AppCompatActivity {
    int timeTxt;
    String subTxt,docName;
    TextView insTxt;
    Button btnStart;
    int ACTIVITY_CODE;
    String sub,classNum,chapterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Tests");
        setContentView(R.layout.activity_quiz_intro);

        ACTIVITY_CODE=getIntent().getIntExtra("from_which_activity",0);
        if(ACTIVITY_CODE==101){
            timeTxt=getIntent().getIntExtra("quiz_time",0);
            docName=getIntent().getStringExtra("doc_name");
            subTxt=getIntent().getStringExtra("quiz_name");
        }

        if(ACTIVITY_CODE==100){
            sub=getIntent().getStringExtra("subject");
            classNum=getIntent().getStringExtra("classNum");
            chapterName=getIntent().getStringExtra("chapterName");
            timeTxt=getIntent().getIntExtra("quiz_time",0);
            subTxt=getIntent().getStringExtra("quiz_name");
        }
        if(ACTIVITY_CODE==102){
            sub=getIntent().getStringExtra("subject");
            classNum=getIntent().getStringExtra("classNum");
            chapterName=getIntent().getStringExtra("chapterName");
            timeTxt=getIntent().getIntExtra("quiz_time",0);
        }
        insTxt=findViewById(R.id.insTxt);
        btnStart=findViewById(R.id.btnStart);

        insTxt.setText("A quiz is a form of game attempt to answer questions correctly about a certain or verity of subjects. Here we arrange a quiz where you get "+timeTxt+" min and "+timeTxt+" questions to answer. We also provide 4 options and 4 point to each question . There is a 'Next' and 'Previous' button and within the "+timeTxt+" min you can change your answers of any question. If you do not press finish button ,we automatically submitted your answer and show your score.\nStart the quiz , all the best :))) ");
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QuizIntroActivity.this,TestQuestionActivity.class);
                intent.putExtra("from_which_activity",ACTIVITY_CODE);
                if(ACTIVITY_CODE==100){
                    intent.putExtra("subject",sub);
                    intent.putExtra("classNum",classNum);
                    intent.putExtra("chapterName",chapterName);
                    intent.putExtra("quiz_name",subTxt);
                    intent.putExtra("time_txt",timeTxt);
                }
                if(ACTIVITY_CODE==101) {
                    intent.putExtra("quiz_name", subTxt);
                    intent.putExtra("doc_name", docName);
                    intent.putExtra("time_txt", timeTxt);
                }
                if(ACTIVITY_CODE==102){
                    intent.putExtra("subject",sub);
                    intent.putExtra("classNum",classNum);
                    intent.putExtra("chapterName",chapterName);
                    intent.putExtra("time_txt",timeTxt);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));

    }
}