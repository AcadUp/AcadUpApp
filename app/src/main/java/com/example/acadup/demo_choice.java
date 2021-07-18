package com.example.acadup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.acadup.LoadData.ApplicationClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.acadup.Models.SubjectsModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.acadup.LoadData.ApplicationClass.lowerClass;
import static com.example.acadup.LoadData.ApplicationClass.midClass;
import static com.example.acadup.LoadData.ApplicationClass.upperClass;

public class demo_choice extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText etName,etEmail,etPhone;
    Spinner spinnerClass;
    AppCompatButton scheduleBtn;
    int low=1,mid=0,up=0;
    TextView sub1,sub2,sub3,sub4,sub5,sub6,sub7,sub8;
    Button datePickerBtn,timePickerBtn;
    int date,month,year;
    int hour;
    Calendar calendar=Calendar.getInstance();
    int count1=0,count2=0,count3=0,count4=0,count5=0,count6=0,count7=0,count8=0;
    String dates,times,s;
    TextView slotTime;
    ArrayList<SubjectsModel> lowClass,middleClass,upClass;
    FirebaseFirestore fireStore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference,demoClassRef;
    String name,email,phone;
    int classSel;
    Map<String,Object> user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_demo_choice);
        user= new HashMap<>();
        firebaseAuth=FirebaseAuth.getInstance();
        fireStore=FirebaseFirestore.getInstance();
        demoClassRef = fireStore.collection("DemoClass").document(firebaseAuth.getCurrentUser().getUid());
        lowClass=lowerClass;
        middleClass=midClass;
        upClass=upperClass;

        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        date=calendar.get(Calendar.DAY_OF_MONTH);
        
        etName=findViewById(R.id.name);
        etEmail=findViewById(R.id.emailId);
        etPhone=findViewById(R.id.phoneNum);
        slotTime=findViewById(R.id.slotTime);

        etName.setEnabled(false);
        etPhone.setEnabled(false);
        etEmail.setEnabled(false);
        
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



        documentReference=fireStore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            name=documentSnapshot.getString("firstName")+" "+documentSnapshot.getString("lastName");
                            classSel=Integer.parseInt(documentSnapshot.getString("class"))-1;
                            phone=documentSnapshot.getString("phone");
                            email=documentSnapshot.getString("email");
                            etName.setText(name);
                            spinnerClass.setSelection(classSel);
                            etPhone.setText(phone);
                            etEmail.setText(email);

                            user.put("name",name);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("class",String.valueOf(classSel+1));
                            demoClassRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                        }
                        }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(demo_choice.this, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });


        ArrayAdapter adapter=ArrayAdapter.createFromResource(this, R.array.classes,
                R.layout.color_spinner_layout);
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
                Toast.makeText(demo_choice.this, s, Toast.LENGTH_SHORT).show();

                if(etName.getText().toString().trim().isEmpty()||etEmail.getText().toString().trim().isEmpty()||
                etPhone.getText().toString().trim().isEmpty()|| times == null || dates == null){
                    Toast.makeText(demo_choice.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(demo_choice.this, demo_slot.class);
                    intent.putExtra("slot_time",dates+", "+times);
                    intent.putExtra("count1", count1);
                    intent.putExtra("count2", count2);
                    intent.putExtra("count3", count3);
                    intent.putExtra("count4", count4);
                    intent.putExtra("count5", count5);
                    intent.putExtra("count6", count6);
                    intent.putExtra("count7", count7);
                    intent.putExtra("count8", count8);
                    if (count1 == 1) {
                        intent.putExtra("Sub1", sub1.getText().toString().trim());
                    } else if (count2 == 1) {
                        intent.putExtra("Sub2", sub2.getText().toString().trim());
                    } else if (count3 == 1) {
                        intent.putExtra("Sub3", sub3.getText().toString().trim());
                    } else if (count4 == 1) {
                        intent.putExtra("Sub4", sub4.getText().toString().trim());
                    } else if (count5 == 1) {
                        intent.putExtra("Sub5", sub5.getText().toString().trim());
                    } else if (count6 == 1) {
                        intent.putExtra("Sub6", sub6.getText().toString().trim());
                    } else if (count7 == 1) {
                        intent.putExtra("Sub7", sub7.getText().toString().trim());
                    } else if (count8 == 1) {
                        intent.putExtra("Sub8", sub8.getText().toString().trim());
                    } else {
                        intent.putExtra("Sub1", sub1.getText().toString().trim());
                    }
                    startActivity(intent);
                    finish();
                }
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
            user.put("subject",sub1.getText().toString());
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
            user.put("subject",sub2.getText().toString());
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
            user.put("subject",sub3.getText().toString());
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
            user.put("subject",sub4.getText().toString());
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
            user.put("subject",sub5.getText().toString());
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
            user.put("subject",sub6.getText().toString());
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
            user.put("subject",sub7.getText().toString());
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
            user.put("subject",sub8.getText().toString());
        }
        demoClassRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void spinnerSelectionEffect(int i) {
        user.put("class",String.valueOf(i+1));
        demoClassRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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
        TimePickerDialog timePickerDialog=new TimePickerDialog(demo_choice.this,kTimePickerListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),false);
        timePickerDialog.show();
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


//            Toast.makeText(demo_choice.this, date+"/"+month+"/"+year+"\n"+weekDay, Toast.LENGTH_SHORT).show();
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

//            month calculation
            if(month==1){
                dates=dates+" Jan";
            }
            else if(month==2){
                dates=dates+" Feb";
            }
            else if(month==3){
                dates=dates+" Mar";
            }
            else if(month==4){
                dates=dates+" Apr";
            }
            else if(month==5){
                dates=dates+" May";
            }
            else if(month==6){
                dates=dates+" June";
            }
            else if(month==7){
                dates=dates+" July";
            }
            else if(month==8){
                dates=dates+" Aug";
            }
            else if(month==9){
                dates=dates+" Sep";
            }
            else if(month==10){
                dates=dates+" Oct";
            }else if(month==11){
                dates=dates+" Nov";
            }else if(month==12){
                dates=dates+" Dec";
            }
            //year calculation
            dates=dates+","+year;
            slotTime.setText("Selected slot:\n"+dates+", "+times);
        }
    };
    protected TimePickerDialog.OnTimeSetListener kTimePickerListener=new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String start_time="AM",end_time="AM";
                    hour=hourOfDay;
                    minute=minute;
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
                }
            };
}