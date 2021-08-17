package com.example.acadup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RestSubjects extends AppCompatActivity implements PaymentResultListener {

    TextView codingSelect,codingDeselect,roboticsSelect,roboticsDeselect;
    TextView heading_fundamental,amount,classNumber,moneyClass;
    TextView beginnerSelected,beginnerDeselected,intermediateSelected,intermediateDeselected,advancedSelected,advancedDeselected;
    ConstraintLayout demoChoice;
    Button payBtn;
    String amnt="11999";
    int robotics=0;
    int coding=1;
    String selectedPlan="1 on 1";
    FirebaseFirestore fireStore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    Map<String,Object> user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_coding_robotics_purchase);
        Intent data = getIntent();
        String email=data.getStringExtra("email");
        String contact=data.getStringExtra("phone");
        String subject=data.getStringExtra("subject");
        demoChoice=findViewById(R.id.demo_choice);
        heading_fundamental=findViewById(R.id.heading_fundamental);
        heading_fundamental.setText(subject+" Beginner");
        amount=findViewById(R.id.amount);
        classNumber=findViewById(R.id.classNumber);
        moneyClass=findViewById(R.id.moneyClass);
        payBtn=findViewById(R.id.payBtn);

        demoChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestSubjects.this,demo_choice.class));
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


        codingSelect.setText("1 on 1");
        codingDeselect.setText("1 on 1");

        roboticsSelect.setText("1 on 5");
        roboticsDeselect.setText("1 on 5");

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
                    heading_fundamental.setText(subject+" Beginner");
                    amount.setText("Rs. 12,999");
                    amnt="12999";
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 700/- per session");
                    selectedPlan="1 on 5";

                }
                else
                {
                    heading_fundamental.setText(subject+" Beginner");
                    amount.setText("Rs. 11,999");
                    amnt="11999";
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 600/- per session");
                    selectedPlan="1 on 1";
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
                    heading_fundamental.setText(subject+" Beginner");
                    amount.setText("Rs. 12,999");
                    amnt="12999";
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 700/- per session");
                    selectedPlan="1 on 5";
                }
                else
                {
                    heading_fundamental.setText(subject+" Beginner");
                    amount.setText("Rs. 11,999");
                    amnt="11999";
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 600/- per session");
                    selectedPlan="1 on 1";
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
                    heading_fundamental.setText(subject+" Beginner");
                    amount.setText("Rs. 12,999");
                    amnt="12999";
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 700/- per session");
                }
                else
                {
                    heading_fundamental.setText(subject+" Beginner");
                    amount.setText("Rs. 11,999");
                    amnt="11999";
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
                    heading_fundamental.setText(subject+" Intermediate");
                    amount.setText("Rs. 15,999");
                    amnt="15999";
                    classNumber.setText("30");
                    moneyClass.setText("Rs. 800/- per session");
                }
                else
                {
                    heading_fundamental.setText(subject+" Intermediate");
                    amount.setText("Rs. 14,999");
                    amnt="14999";
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
                    heading_fundamental.setText(subject+" Advanced");
                    amount.setText("Rs. 20,999");
                    amnt="20999";
                    classNumber.setText("50");
                    moneyClass.setText("Rs. 1000/- per session");
                }
                else
                {
                    heading_fundamental.setText(subject+" Advanced");
                    amount.setText("Rs. 18,999");
                    amnt="18999";
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

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String samount=amnt;
                int amt=Math.round(Float.parseFloat(samount)*100);

                Checkout checkout=new Checkout();
                checkout.setKeyID("rzp_test_aeTrn6p7xVhaSm");
                checkout.setImage(R.drawable.logo_shield);
                JSONObject object=new JSONObject();
                try {
                    object.put("name",heading_fundamental.getText().toString());
                    object.put("description","Payment for buying "+heading_fundamental.getText().toString()+" Course.");
                    object.put("amount",amt);
                    object.put("Currency","INR");
                    object.put("contact of student",contact);
                    object.put("email of student",email);

                    checkout.open(RestSubjects.this,object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this,"Payment successful "+s,Toast.LENGTH_SHORT).show();
        user= new HashMap<>();
        firebaseAuth=FirebaseAuth.getInstance();
        fireStore=FirebaseFirestore.getInstance();
        documentReference = fireStore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("purchasedCourses").document(s);
        user.put("course_name",heading_fundamental.getText().toString()+" ("+selectedPlan+")");
        user.put("amount",amount.getText().toString());

        user.put("Date of Payment", Calendar.getInstance().getTime());
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RestSubjects.this,"added data: "+s,Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RestSubjects.this,"not added"+s,Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment failed due to error: "+s,Toast.LENGTH_SHORT).show();
    }
}