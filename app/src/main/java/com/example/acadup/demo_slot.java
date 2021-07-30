package com.example.acadup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.example.acadup.Models.DemoSlotModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class demo_slot extends AppCompatActivity {
    int heightScreen;
    LinearLayout dataAppLayout,cardShowLayout;
    Button rescheduleTxt,cancelTxt;

    String minute;
    TextView sub_heading,class_heading,time_heading,final_click_time,desc_demo;
    ArrayList<DemoSlotModel> slots;
    LinearLayout whatsApp;
    String dates,time,s;
    int date,month,year;
    int hour;
    int fromHome;
    String dateFinal,monthFinal,yearFinal;
    Calendar calendar=Calendar.getInstance();
    ViewGroup viewGroup;
    ScrollView.LayoutParams params;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    DocumentReference reference;
    String sub_node,class_node;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_demo_slot);
        firebaseFirestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        reference=firebaseFirestore.collection("DemoClass").document(auth.getCurrentUser().getUid());

        fromHome=getIntent().getIntExtra("FromHomeActivity",0);
        viewGroup=(ViewGroup) findViewById(android.R.id.content);
        slots=new ArrayList<>();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        heightScreen = displayMetrics.heightPixels;


        dataAppLayout=findViewById(R.id.holderLayout);
        cardShowLayout=findViewById(R.id.cardShow);
        desc_demo=findViewById(R.id.desc_demo);
        params = (ScrollView.LayoutParams) dataAppLayout.getLayoutParams();
        params.height = (heightScreen * 4) / 5;
        params.gravity= Gravity.BOTTOM;
        dataAppLayout.setLayoutParams(params);

        fetchData();

        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        date=calendar.get(Calendar.DAY_OF_MONTH);

        whatsApp=findViewById(R.id.whatsapp);

        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String number = "+918584074448";
                    number = number.replace(" ", "").replace("+", "");
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net");
                    Context context = demo_slot.this;
                    context.startActivity(sendIntent);
                }
                catch (Exception e){
                    Toast.makeText(demo_slot.this, "Something wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void fetchData() {

        reference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            Map<String, Object> fetchedMap = documentSnapshot.getData();
                            Object[] key = fetchedMap.keySet().toArray();
                            for (int i = 0; i < fetchedMap.size(); i++) {
                                DemoSlotModel demoSlotModel = documentSnapshot.get(key[i].toString(), DemoSlotModel.class);

                                String date_in = demoSlotModel.getDate();
                                String month_in = demoSlotModel.getMonth();
                                String year_in=demoSlotModel.getYear();
                                String time_in = demoSlotModel.getTime();
                                String hour_in = time_in.split(":")[0];
                                String minute_in = time_in.split(":")[1];

                                int hour_int = Integer.parseInt(hour_in);
                                hour_int++;
                                if (hour_int >= 24) {
                                    hour_int = hour_int - 24;
                                }
                                int miute_int = Integer.parseInt(minute_in);
                                int date_int = Integer.parseInt(date_in);
                                int month_int = Integer.parseInt(month_in);
                                int year_int=Integer.parseInt(year_in);

                                String Hour_sys = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
                                String Minute_sys = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());
                                String Date_sys = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                                String Month_sys = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
                                String Year_sys=new SimpleDateFormat("yyyy",Locale.getDefault()).format(new Date());

                                int hour_sys_int = Integer.parseInt(Hour_sys);
                                int minute_sys_int = Integer.parseInt(Minute_sys);
                                int date_sys_int = Integer.parseInt(Date_sys);
                                int month_sys_int = Integer.parseInt(Month_sys);
                                int year_sys_int = Integer.parseInt(Year_sys);

                                if(year_sys_int > year_int){
                                    reference.update(key[i].toString(), FieldValue.delete());
                                }
                                else if(year_sys_int == year_int){
                                    if (month_sys_int > month_int) {

                                        reference.update(key[i].toString(), FieldValue.delete());

                                    }
                                    else if (month_sys_int == month_int) {

                                        if (date_sys_int > date_int) {

                                            reference.update(key[i].toString(),FieldValue.delete());

                                        } else if (date_sys_int == date_int) {

                                            if (hour_sys_int > hour_int) {

                                                reference.update(key[i].toString(),FieldValue.delete());

                                            } else if (hour_sys_int == hour_int) {

                                                if (minute_sys_int > miute_int) {

                                                    reference.update(key[i].toString(),FieldValue.delete());

                                                } else if (minute_sys_int == miute_int) {
                                                    slots.add(demoSlotModel);
                                                }
                                            } else {

                                                slots.add(demoSlotModel);

                                            }
                                        } else {

                                            slots.add(demoSlotModel);

                                        }

                                    }
                                    else {
                                        slots.add(demoSlotModel);
                                    }
                                }
                                else{
                                    slots.add(demoSlotModel);
                                }

                            }
                            for (int j = 0; j < slots.size(); j++) {
                                View v = LayoutInflater.from(demo_slot.this).inflate(R.layout.card_holders, viewGroup, false);
                                cardShowLayout.addView(v);
                                rescheduleTxt = v.findViewById(R.id.rescheduleTxt);
                                cancelTxt = v.findViewById(R.id.cancelTxt);
                                sub_heading = v.findViewById(R.id.sub_heading);
                                class_heading = v.findViewById(R.id.class_heading);
                                time_heading = v.findViewById(R.id.times);
                                sub_heading.setText(slots.get(j).getSubject());
                                class_heading.setText("(Class-" + slots.get(j).getClasses() + ")");
                                time_heading.setText(slots.get(j).getFormatted_date());
                                final int finalJ = j;
                                cancelTxt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(demo_slot.this);
                                        builder.setMessage("Are you sure about cancel the demo class");
                                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                reference.update(slots.get(finalJ).getSubject()+slots.get(finalJ).getClasses(),FieldValue.delete())
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                v.setVisibility(View.GONE);
                                                                Toast.makeText(demo_slot.this, slots.get(finalJ).getSubject() + " cancel", Toast.LENGTH_SHORT).show();

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(demo_slot.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        })
                                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog mDialog=builder.create();
                                        mDialog.show();
                                    }
                                });
                                rescheduleTxt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final_click_time=v.findViewById(R.id.times);
                                        sub_node=slots.get(finalJ).getSubject();
                                        class_node=slots.get(finalJ).getClasses();
                                        setDate();
                                    }
                                });
                            }
                            if(slots.size()==0){
                                desc_demo.setText("No demo class found");
                                params.gravity= Gravity.BOTTOM;
                                dataAppLayout.setLayoutParams(params);
                            }
                            else {
                                desc_demo.setText(R.string.sample2);
                                params.gravity = Gravity.TOP;
                                dataAppLayout.setLayoutParams(params);
                            }
                        }
                        else{
                            desc_demo.setText("No demo class found");
                            params.gravity= Gravity.BOTTOM;
                            dataAppLayout.setLayoutParams(params);
                            Toast.makeText(demo_slot.this, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(demo_slot.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void setDate() {
        DatePickerDialog datePickerDialog=new DatePickerDialog(demo_slot.this,kDatePickerListener,year,month,date);
        datePickerDialog.show();
    }
    protected DatePickerDialog.OnDateSetListener kDatePickerListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            year=year;
            month=month+1;
            date=dayOfMonth;
            int ValMonth=month,ValDay=date,valYear=year;


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

            //Date selection correct or incorrect check
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
            SimpleDateFormat mf = new SimpleDateFormat("MM", Locale.getDefault());
            SimpleDateFormat yf = new SimpleDateFormat("yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            String formattedMonth = mf.format(c);
            String formattedYear = yf.format(c);

            if(year<Integer.parseInt(formattedYear)){
                Toast.makeText(demo_slot.this, "Please select a valid date", Toast.LENGTH_SHORT).show();
            }
            else if(year==Integer.parseInt(formattedYear)){
                if(month<Integer.parseInt(formattedMonth)){
                    Toast.makeText(demo_slot.this, "Please select a valid date", Toast.LENGTH_SHORT).show();
                }
                else if(month==Integer.parseInt(formattedMonth)){
                    if(dayOfMonth<=Integer.parseInt(formattedDate)){
                        Toast.makeText(demo_slot.this, "Please select a valid date", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        dateFinal=String.valueOf(ValDay);
                        monthFinal=String.valueOf(ValMonth);
                        yearFinal=String.valueOf(valYear);
                        setTime();
                    }
                }
                else{
                    dateFinal=String.valueOf(ValDay);
                    monthFinal=String.valueOf(ValMonth);
                    yearFinal=String.valueOf(valYear);
                    setTime();

                }
            }
            else{

                dateFinal=String.valueOf(ValDay);
                monthFinal=String.valueOf(ValMonth);
                yearFinal=String.valueOf(valYear);
                setTime();
            }

        }
    };
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

                    time=hour+":"+minute+" "+start_time+"-"+session_end+":"+minute+" "+end_time;
                    final_click_time.setText(dates+", "+time);
                    reference.update(sub_node+class_node+".formatted_time",dates+", "+time);
                    reference.update(sub_node+class_node+".date",dateFinal);
                    reference.update(sub_node+class_node+".month",monthFinal);
                    reference.update(sub_node+class_node+".year",yearFinal);
                    reference.update(sub_node+class_node+".time",hourOfDay+":"+minute);
                }

        });
        materialTimePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(demo_slot.this, "You have not selected time", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(fromHome==7){
            startActivity(new Intent(demo_slot.this,HomeActivity.class));
        }
        else{
            startActivity(new Intent(demo_slot.this,demo_choice.class));
        }
        finish();
    }
}