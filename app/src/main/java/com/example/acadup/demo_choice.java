package com.example.acadup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.acadup.LoadData.ApplicationClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import com.example.acadup.Models.SubjectsModel;
import com.example.acadup.SendMail.JavaMailAPI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.hbb20.CountryCodePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.acadup.LoadData.ApplicationClass.lowerClass;
import static com.example.acadup.LoadData.ApplicationClass.midClass;
import static com.example.acadup.LoadData.ApplicationClass.upperClass;

public class demo_choice extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText etName,etEmail,etPhone;
    Spinner spinnerClass;
    AppCompatButton scheduleBtn;
    int low=1,mid=0,up=0;
    String spinSel;
    TextView sub1,sub2,sub3,sub4,sub5,sub6,sub7,sub8;
    TextView dateText,timeText;
    Button datePickerBtn,timePickerBtn;
    CountryCodePicker countryCodePicker;
    String selectedSubject;
    LinearLayout whatsApp;
    int date,month,year;
    int hour;
    String demoDate="";
    String minute;
    Calendar calendar=Calendar.getInstance();
    int count1=0,count2=0,count3=0,count4=0,count5=0,count6=0,count7=0,count8=0;
    String dates,times,s;
    TextView slotTime;
    ArrayList<SubjectsModel> lowClass,middleClass,upClass;
    FirebaseAuth auth;
    FirebaseFirestore fireStore;
    Map<String,Object> map1;
    Map<String,Object> map2;
    int classTxt;
    DocumentReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_demo_choice);
        map1=new HashMap<>();
        map2=new HashMap<>();

        auth=FirebaseAuth.getInstance();
        fireStore=FirebaseFirestore.getInstance();
        loadClassFromFireBase();
        dbRef=fireStore.collection("DemoClass").document(auth.getCurrentUser().getUid());

        lowClass=lowerClass;
        middleClass=midClass;
        upClass=upperClass;

        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        date=calendar.get(Calendar.DAY_OF_MONTH);

        whatsApp=findViewById(R.id.whatsapp);
        etName=findViewById(R.id.name);
        etEmail=findViewById(R.id.emailId);
        countryCodePicker=findViewById(R.id.ccp);
        etPhone=findViewById(R.id.phoneNum);
        slotTime=findViewById(R.id.slotTime);
        dateText=findViewById(R.id.dateText);
        timeText=findViewById(R.id.timeText);
        String text1="<font color=#C11515>*</font> <font color=#000000>Date :</font>";
        String text2="<font color=#C11515>*</font> <font color=#000000>Time :</font>";
        dateText.setText(Html.fromHtml(text1));
        timeText.setText(Html.fromHtml(text2));
        countryCodePicker.registerCarrierNumberEditText(etPhone);
        etName.setText("");
        etEmail.setText("");
        etPhone.setText("");

        sub1=findViewById(R.id.sub1);
        sub2=findViewById(R.id.sub2);
        sub3=findViewById(R.id.sub3);
        sub4=findViewById(R.id.sub4);
        sub5=findViewById(R.id.sub5);
        sub6=findViewById(R.id.sub6);
        sub7=findViewById(R.id.sub7);
        sub8=findViewById(R.id.sub8);

        datePickerBtn=findViewById(R.id.datePicker1);
        timePickerBtn=findViewById(R.id.timePicker1);

        scheduleBtn=findViewById(R.id.scheduleTrialBtn);
        spinnerClass=findViewById(R.id.spinnerClass);


        ArrayAdapter adapter=ArrayAdapter.createFromResource(this, R.array.classes, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout); //Spinner Dropdown Text
        spinnerClass.setAdapter(adapter);
        spinnerClass.setOnItemSelectedListener(this);


        sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1=1;count2=0;count3=0;count4=0;count5=0;count6=0;count7=0;count8=0;
                subChangeEffect(count1);
            }
        });
        sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2=1;count1=0;count3=0;count4=0;count5=0;count6=0;count7=0;count8=0;
                subChangeEffect(count2);

            }
        });
        sub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count3=1;count2=0;count1=0;count4=0;count5=0;count6=0;count7=0;count8=0;
                subChangeEffect(count3);

            }
        });
        sub4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count4=1;count2=0;count3=0;count1=0;count5=0;count6=0;count7=0;count8=0;
                subChangeEffect(count4);

            }
        });
        sub5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count5=1;count2=0;count3=0;count4=0;count1=0;count6=0;count7=0;count8=0;
                subChangeEffect(count5);

            }
        });
        sub6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count6=1;count2=0;count3=0;count4=0;count5=0;count1=0;count7=0;count8=0;
                subChangeEffect(count6);

            }
        });
        sub7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count7=1;count2=0;count3=0;count4=0;count5=0;count6=0;count1=0;count8=0;
                subChangeEffect(count7);

            }
        });
        sub8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count8=1;count2=0;count3=0;count4=0;count5=0;count6=0;count7=0;count1=0;
                subChangeEffect(count8);

            }
        });

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();

            }
        });
        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime();

            }
        });
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().trim().isEmpty()||etEmail.getText().toString().trim().isEmpty()||
                        etPhone.getText().toString().trim().isEmpty()|| times == null || dates == null){
                    Toast.makeText(demo_choice.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        int valAvailable=1;
                                        Map<String,Object> fetchedMap=documentSnapshot.getData();
                                        Object[] key= fetchedMap.keySet().toArray();
                                        for(int i=0;i<fetchedMap.size();i++){
                                            if(key[i].toString().equals(selectedSubject+spinSel)){
                                                valAvailable=0;
                                            }
                                        }
                                        if(valAvailable==0){
                                            Toast.makeText(demo_choice.this, "You are not available for this subject", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            createDemoClassCollection();
                                        }
                                    }
                                    else{
                                        createDemoClassCollection();
                                    }
                                    sendEmail();
                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(demo_choice.this, "Something wrong", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });
        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String number = "+918584074448";
                    number = number.replace(" ", "").replace("+", "");
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net");
                    Context context = demo_choice.this;
                    context.startActivity(sendIntent);
                }
                catch (Exception e){
                    Toast.makeText(demo_choice.this, "Something wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendEmail() {
        String showClass=classFormatForMail(spinSel);
        String showDate=demoDate;
        String showTime=timeFormatForMail(times);
        String showName="";
        if(etName.getText().toString().trim().contains(" ")){
            showName=etName.getText().toString().substring(0,etName.getText().toString().indexOf(" "));
        }
        else{
            showName=etName.getText().toString().trim();
        }

        JavaMailAPI javaMailAPI=new JavaMailAPI(this,etEmail.getText().toString(),
                "Dear "+showName
                        +",\nWelcome to AcadUp."
                        +"\n\n You have successfully registered for a demo class.Your demo class details are as:"
                        +"\n\nSubject - "+selectedSubject
                        +"\nClass - "+showClass
                        +"\nDate: "+showDate
                        +"\nTime: "+showTime
                        +"\n\nIf you need any help/assistance, kindly reach out to us at rishavju21@gmail.com or +918584074448"
                        +"\n\nThank you"
                        +"\nTeam AcadUp");
        javaMailAPI.execute();
    }

    private String timeFormatForMail(String times) {
        String timeShow="";
        if(times.length()==17){
            timeShow=times;
        }
        else if(times.length()==15){
            timeShow="0"+times.substring(0,8)+"0"+times.substring(8);
        }
        else if(times.length()==16){
            if(times.indexOf("-")==8){
                timeShow=times.substring(0,9)+"0"+times.substring(9);
            }
            else if(times.indexOf("-")==7){
                timeShow="0"+times;
            }
        }
        return timeShow;
    }

    private String classFormatForMail(String spinSel) {
        String classShow="";
        if(spinSel.equals("1")){
            classShow="I";
        }
        else if(spinSel.equals("2")){
            classShow="II";
        }
        else if(spinSel.equals("3")){
            classShow="III";
        }
        else if(spinSel.equals("4")){
            classShow="IV";
        }
        else if(spinSel.equals("5")){
            classShow="V";
        }
        else if(spinSel.equals("6")){
            classShow="VI";
        }
        else if(spinSel.equals("7")){
            classShow="VII";
        }
        else if(spinSel.equals("8")){
            classShow="VIII";
        }
        else if(spinSel.equals("9")){
            classShow="IX";
        }
        else if(spinSel.equals("10")){
            classShow="X";
        }
        else if(spinSel.equals("11")){
            classShow="XI";
        }
        else if(spinSel.equals("12")){
            classShow="XII";
        }
        return classShow;
    }

    private void createDemoClassCollection() {
        map2.put("name",etName.getText().toString());
        map2.put("phone",String.valueOf(countryCodePicker.getFullNumberWithPlus().replace(" ","")));
        map2.put("email",etEmail.getText().toString());
        map1.put(String.valueOf(selectedSubject+spinSel),map2);

        dbRef.set(map1, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(demo_choice.this, demo_slot.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(demo_choice.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void loadClassFromFireBase() {
        DocumentReference db=fireStore.collection("users").document(auth.getCurrentUser().getUid());
        db.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){

                            classTxt=Integer.parseInt(documentSnapshot.getString("class"))-1;
                            spinnerClass.setSelection(classTxt);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        subChangeEffect(count1);
        spinnerSelectionEffect(i);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void subChangeEffect(int count) {
        selectedSubject="Maths";
        if(count==count1){
            sub1.setBackground(getResources().getDrawable(R.drawable.sub_select));
            sub2.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub3.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub4.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub5.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub6.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub7.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub8.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub1.setTextColor(Color.WHITE);
            sub2.setTextColor(Color.BLACK);
            sub3.setTextColor(Color.BLACK);
            sub4.setTextColor(Color.BLACK);
            sub5.setTextColor(Color.BLACK);
            sub6.setTextColor(Color.BLACK);
            sub7.setTextColor(Color.BLACK);
            sub8.setTextColor(Color.BLACK);
            selectedSubject=sub1.getText().toString();
        }
        else if(count==count2){
            sub2.setBackground(getResources().getDrawable(R.drawable.sub_select));
            sub1.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub3.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub4.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub5.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub6.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub7.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub8.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub2.setTextColor(Color.WHITE);
            sub1.setTextColor(Color.BLACK);
            sub3.setTextColor(Color.BLACK);
            sub4.setTextColor(Color.BLACK);
            sub5.setTextColor(Color.BLACK);
            sub6.setTextColor(Color.BLACK);
            sub7.setTextColor(Color.BLACK);
            sub8.setTextColor(Color.BLACK);
            selectedSubject=sub2.getText().toString();
        }
        else if(count==count3){
            sub3.setBackground(getResources().getDrawable(R.drawable.sub_select));
            sub2.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub1.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub4.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub5.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub6.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub7.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub8.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub3.setTextColor(Color.WHITE);
            sub2.setTextColor(Color.BLACK);
            sub1.setTextColor(Color.BLACK);
            sub4.setTextColor(Color.BLACK);
            sub5.setTextColor(Color.BLACK);
            sub6.setTextColor(Color.BLACK);
            sub7.setTextColor(Color.BLACK);
            sub8.setTextColor(Color.BLACK);
            selectedSubject=sub3.getText().toString();
        }
        else if(count==count4){
            sub4.setBackground(getResources().getDrawable(R.drawable.sub_select));
            sub2.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub3.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub1.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub5.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub6.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub7.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub8.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub4.setTextColor(Color.WHITE);
            sub2.setTextColor(Color.BLACK);
            sub3.setTextColor(Color.BLACK);
            sub1.setTextColor(Color.BLACK);
            sub5.setTextColor(Color.BLACK);
            sub6.setTextColor(Color.BLACK);
            sub7.setTextColor(Color.BLACK);
            sub8.setTextColor(Color.BLACK);
            selectedSubject=sub4.getText().toString();
        }
        else if(count==count5){
            sub5.setBackground(getResources().getDrawable(R.drawable.sub_select));
            sub2.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub3.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub4.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub1.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub6.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub7.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub8.setBackground(getResources().getDrawable(R.drawable.subjects_background));

            sub5.setTextColor(Color.WHITE);
            sub2.setTextColor(Color.BLACK);
            sub3.setTextColor(Color.BLACK);
            sub4.setTextColor(Color.BLACK);
            sub1.setTextColor(Color.BLACK);
            sub6.setTextColor(Color.BLACK);
            sub7.setTextColor(Color.BLACK);
            sub8.setTextColor(Color.BLACK);
            selectedSubject=sub5.getText().toString();
        }
        else if(count==count6){
            sub6.setBackground(getResources().getDrawable(R.drawable.sub_select));
            sub2.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub3.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub4.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub5.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub1.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub7.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub8.setBackground(getResources().getDrawable(R.drawable.subjects_background));

            sub6.setTextColor(Color.WHITE);
            sub2.setTextColor(Color.BLACK);
            sub3.setTextColor(Color.BLACK);
            sub4.setTextColor(Color.BLACK);
            sub5.setTextColor(Color.BLACK);
            sub1.setTextColor(Color.BLACK);
            sub7.setTextColor(Color.BLACK);
            sub8.setTextColor(Color.BLACK);
            selectedSubject=sub6.getText().toString();
        }
        else if(count==count7){
            sub7.setBackground(getResources().getDrawable(R.drawable.sub_select));
            sub2.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub3.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub4.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub5.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub6.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub1.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub8.setBackground(getResources().getDrawable(R.drawable.subjects_background));

            sub7.setTextColor(Color.WHITE);
            sub2.setTextColor(Color.BLACK);
            sub3.setTextColor(Color.BLACK);
            sub4.setTextColor(Color.BLACK);
            sub5.setTextColor(Color.BLACK);
            sub6.setTextColor(Color.BLACK);
            sub1.setTextColor(Color.BLACK);
            sub8.setTextColor(Color.BLACK);
            selectedSubject=sub7.getText().toString();
        }
        else if(count==count8){
            sub8.setBackground(getResources().getDrawable(R.drawable.sub_select));
            sub2.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub3.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub4.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub5.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub6.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub7.setBackground(getResources().getDrawable(R.drawable.subjects_background));
            sub1.setBackground(getResources().getDrawable(R.drawable.subjects_background));

            sub8.setTextColor(Color.WHITE);
            sub2.setTextColor(Color.BLACK);
            sub3.setTextColor(Color.BLACK);
            sub4.setTextColor(Color.BLACK);
            sub5.setTextColor(Color.BLACK);
            sub6.setTextColor(Color.BLACK);
            sub7.setTextColor(Color.BLACK);
            sub1.setTextColor(Color.BLACK);
            selectedSubject=sub8.getText().toString();
        }
        map2.put("subject",selectedSubject);
    }
    private void spinnerSelectionEffect(int i) {
        spinSel=String.valueOf(i+1);
        map2.put("classes",String.valueOf(i+1));
        if(i>=0 && i<=4){
            low=1;mid=0;up=0;
            sub1.setVisibility(View.VISIBLE);
            sub2.setVisibility(View.VISIBLE);
            sub3.setVisibility(View.VISIBLE);
            sub4.setVisibility(View.VISIBLE);
            sub5.setVisibility(View.VISIBLE);
            sub6.setVisibility(View.VISIBLE);
            sub7.setVisibility(View.VISIBLE);
            sub8.setVisibility(View.VISIBLE);


            sub1.setText(lowClass.get(0).getName());
            sub2.setText(lowClass.get(1).getName());
            sub3.setText(lowClass.get(2).getName());
            sub4.setText(lowClass.get(3).getName());
            sub5.setText(lowClass.get(4).getName());
            sub6.setText(lowClass.get(5).getName());
            sub7.setText(lowClass.get(6).getName());
            sub8.setText(lowClass.get(7).getName());
        }
        else if(i>=5 && i<=9){
            mid=1;low=0;up=0;
            sub1.setVisibility(View.VISIBLE);
            sub2.setVisibility(View.VISIBLE);
            sub3.setVisibility(View.VISIBLE);
            sub4.setVisibility(View.VISIBLE);
            sub5.setVisibility(View.VISIBLE);
            sub6.setVisibility(View.VISIBLE);
            sub7.setVisibility(View.GONE);
            sub8.setVisibility(View.GONE);

            sub1.setText(middleClass.get(0).getName());
            sub2.setText(middleClass.get(1).getName());
            sub3.setText(middleClass.get(2).getName());
            sub4.setText(middleClass.get(3).getName());
            sub5.setText(middleClass.get(4).getName());
            sub6.setText(middleClass.get(5).getName());
        }
        else if(i>=10 && i<=11){
            up=1;low=0;up=0;
            sub1.setVisibility(View.VISIBLE);
            sub2.setVisibility(View.VISIBLE);
            sub3.setVisibility(View.VISIBLE);
            sub4.setVisibility(View.VISIBLE);
            sub5.setVisibility(View.VISIBLE);
            sub6.setVisibility(View.GONE);
            sub7.setVisibility(View.GONE);
            sub8.setVisibility(View.GONE);


            sub1.setText(upClass.get(0).getName());
            sub2.setText(upClass.get(1).getName());
            sub3.setText(upClass.get(2).getName());
            sub4.setText(upClass.get(3).getName());
            sub5.setText(upClass.get(4).getName());
        }
    }


    private void setTime() {

        FragmentManager fragmentManager=getSupportFragmentManager();
        MaterialTimePicker.Builder picker=new MaterialTimePicker.Builder();
        picker.setTitleText("schedule demo")
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build();
        MaterialTimePicker materialTimePicker=picker.build();
        materialTimePicker.show(fragmentManager,"TIME");
        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int hourOfDay=materialTimePicker.getHour();
                    int minutes=materialTimePicker.getMinute();
                    String start_time="AM",end_time="AM";
                    hour=hourOfDay;

                    if(minutes<10){
                        minute=("0"+String.valueOf(minutes));
                    }
                    else {
                        minute=String.valueOf(minutes);
                    }
                    if(hour>12){
                        hour=hour-12;
                        start_time="PM";
                    }
                    else if(hour==12){
                        start_time="PM";
                    }
                    int session_end=hour+1;
                    if(start_time.equals("AM")){
                        if(session_end==12){
                            end_time="PM";
                        }
                    }
                    else if(start_time.equals("PM")){
                        if(hour==12){
                            end_time="PM";
                            session_end=session_end-12;
                        }
                        else if(session_end==12){
                            end_time="AM";
                            session_end=0;
                        }
                        else{
                            end_time="PM";
                        }
                    }

                    times=hour+":"+minute+" "+start_time+"-"+session_end+":"+minute+" "+end_time;
                    slotTime.setText("Selected slot:\n"+dates+", "+times);
                    map2.put("time",hourOfDay+":"+minute);
                    map2.put("formatted_date",dates+", "+times);
                }

        });
           materialTimePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(demo_choice.this, "You have not selected time", Toast.LENGTH_SHORT).show();
               }
           });

    }
    private void setDate() {
        DatePickerDialog datePickerDialog=new DatePickerDialog(demo_choice.this,kDatePickerListener,year,month,date);
        datePickerDialog.show();
    }
    protected DatePickerDialog.OnDateSetListener kDatePickerListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year=year;
            month=month+1;
            date=dayOfMonth;
            s=String.valueOf(date)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
            Date d1=null;
            try {
                d1=format1.parse(s);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat format2=new SimpleDateFormat("EEEE");
            String weekDay=format2.format(d1);


            dates=weekDay+", ";
            if(date==1){
                dates=dates+date+"st";
            }
            else if(date==2){
                dates=dates+date+"nd";

            }
            else if(date==3){
                dates=dates+date+"rd";
            }
            else if(date>3){
                dates=dates+date+"th";
            }

            demoDate=demoDate+date+"-";
//            month calculation
            if(month==1){
                dates=dates+" Jan";
                demoDate=demoDate+"January-";
            }
            else if(month==2){
                dates=dates+" Feb";
                demoDate=demoDate+"February-";
            }
            else if(month==3){
                dates=dates+" Mar";
                demoDate=demoDate+"March-";
            }
            else if(month==4){
                dates=dates+" Apr";
                demoDate=demoDate+"April-";
            }
            else if(month==5){
                dates=dates+" May";
                demoDate=demoDate+"May-";
            }
            else if(month==6){
                dates=dates+" June";
                demoDate=demoDate+"June-";
            }
            else if(month==7){
                dates=dates+" July";
                demoDate=demoDate+"July-";
            }
            else if(month==8){
                dates=dates+" Aug";
                demoDate=demoDate+"August-";
            }
            else if(month==9){
                dates=dates+" Sep";
                demoDate=demoDate+"September-";
            }
            else if(month==10){
                dates=dates+" Oct";
                demoDate=demoDate+"October-";
            }else if(month==11){
                dates=dates+" Nov";
                demoDate=demoDate+"November-";
            }else if(month==12){
                dates=dates+" Dec";
                demoDate=demoDate+"December-";
            }
            //year calculation
            dates=dates+", "+year;
            demoDate=demoDate+year;

            //Date selection correct or incorrect check
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
            SimpleDateFormat mf = new SimpleDateFormat("MM", Locale.getDefault());
            SimpleDateFormat yf = new SimpleDateFormat("yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            String formattedMonth = mf.format(c);
            String formattedYear = yf.format(c);

            if(year<Integer.parseInt(formattedYear)){
                Toast.makeText(demo_choice.this, "Please select a valid date", Toast.LENGTH_SHORT).show();
            }
            else if(year==Integer.parseInt(formattedYear)){
                if(month<Integer.parseInt(formattedMonth)){
                    Toast.makeText(demo_choice.this, "Please select a valid date", Toast.LENGTH_SHORT).show();
                }
                else if(month==Integer.parseInt(formattedMonth)){
                    if(dayOfMonth<=Integer.parseInt(formattedDate)){
                        Toast.makeText(demo_choice.this, "Please select a valid date", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        slotTime.setText("Selected slot:\n"+dates+", "+times);
                        map2.put("date",String.valueOf(dayOfMonth));
                        map2.put("month",String.valueOf(month));
                        map2.put("year",String.valueOf(year));
                        map2.put("formatted_date",dates+", "+times);
                    }
                }
                else{
                    slotTime.setText("Selected slot:\n"+dates+", "+times);
                    map2.put("date",String.valueOf(dayOfMonth));
                    map2.put("month",String.valueOf(month));
                    map2.put("year",String.valueOf(year));
                    map2.put("formatted_date",dates+", "+times);
                }
            }
            else if(year>Integer.parseInt(formattedYear)){
                slotTime.setText("Selected slot:\n"+dates+", "+times);
                map2.put("date",String.valueOf(dayOfMonth));
                map2.put("month",String.valueOf(month));
                map2.put("year",String.valueOf(year));
                map2.put("formatted_date",dates+", "+times);
            }

        }
    };

}