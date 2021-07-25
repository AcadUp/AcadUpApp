package com.example.acadup.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.acadup.Adapters.HotCourseAdapter;
import com.example.acadup.Adapters.PracticeTestAdapter;
import com.example.acadup.Adapters.SliderAdapter;
import com.example.acadup.HomeActivity;
import com.example.acadup.LoadData.ApplicationClass;
import com.example.acadup.MainActivity;
import com.example.acadup.Models.HotCourseModel;
import com.example.acadup.Models.PracticeTestModel;
import com.example.acadup.Models.SliderModel;
import com.example.acadup.Models.SubjectsModel;
import com.example.acadup.R;
import com.example.acadup.SubjectOptions;
import com.example.acadup.SubjectView;
import com.example.acadup.demo_choice;
import com.example.acadup.payment_inquiry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;


public class HomeFragment extends Fragment implements SubjectView,View.OnClickListener {
    ConstraintLayout demoActivity;
    int classSelected;
    AppCompatButton moreSubjectBtn;
    RecyclerView hotCourseRecyclerView,practiceTestRecyclerView,whyAcadupRecyclerView,subjectRecyclerView;
    ArrayList<HotCourseModel> hotCourseModelArrayList;
    ArrayList<PracticeTestModel> practiceTestModelArrayList;
    RecyclerView.LayoutManager layoutManagerHotCourse,layoutManagerPracticeTest,layoutManagerWhyAcadup,layoutManagerSubject;
    HotCourseAdapter hotCourseAdapter;
    PracticeTestAdapter practiceTestAdapter;
    HotCourseAdapter.ItemClicked hotCourseItem;
    PracticeTestAdapter.ItemClicked practiceTestItem;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    ViewPager2 pager2;
    ArrayList<SliderModel> sliderModelArrayList;
    TextView[] dots;
    TextView welcomeMsg;
    ImageView subEightImg1,subEightImg2,subEightImg3,subEightImg4,subEightImg5,subEightImg6,subEightImg7,subEightImg8;
    ImageView subFourImg1,subFourImg2,subFourImg3,subFourImg4;
    ImageView subSixImg1,subSixImg2,subSixImg3,subSixImg4,subSixImg5,subSixImg6;
    ConstraintLayout consEight,consFour,consSix;
    int showMoreCount=0;
    int consSelect5=0,consSelect6=0,consSelect8=0;
    CardView cardView6;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userId;
    FirebaseUser user;
//    final int[] classDefault = new int[1];
    String emailId;
    String phone;

    FirebaseFirestore db;
    DocumentReference lowClassRef,midClassRef,upperClassRef;
    public static ArrayList<SubjectsModel> lowerClass_1,midClass_1,upperClass_1;

    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        root= inflater.inflate(R.layout.fragment_home, container, false);
        lowerClass_1=new ArrayList<>();
        midClass_1=new ArrayList<>();
        upperClass_1=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        lowClassRef=db.document("Class/lowerClass");
        midClassRef=db.document("Class/midClass");
        upperClassRef=db.document("Class/upperClass");
        loadData();

        moreSubjectBtn=root.findViewById(R.id.moreSubButton);

        demoActivity=root.findViewById(R.id.demo);
        welcomeMsg=root.findViewById(R.id.welcomeMsg);
        hotCourseRecyclerView=root.findViewById(R.id.hotCourseRecyclerView);
        practiceTestRecyclerView=root.findViewById(R.id.practiceTestRecyclerView);
        hotCourseModelArrayList=new ArrayList<>();
        practiceTestModelArrayList=new ArrayList<>();
        pager2=root.findViewById(R.id.view_pager2);
        dotsLayout=root.findViewById(R.id.dots_container);
//        Eight Img
        subEightImg1=root.findViewById(R.id.subEightImg1);
        subEightImg2=root.findViewById(R.id.subEightImg2);
        subEightImg3=root.findViewById(R.id.subEightImg3);
        subEightImg4=root.findViewById(R.id.subEightImg4);
        subEightImg5=root.findViewById(R.id.subEightImg5);
        subEightImg6=root.findViewById(R.id.subEightImg6);
        subEightImg7=root.findViewById(R.id.subEightImg7);
        subEightImg8=root.findViewById(R.id.subEightImg8);
//        Four Img
        subFourImg1=root.findViewById(R.id.subFourImg1);
        subFourImg2=root.findViewById(R.id.subFourImg2);
        subFourImg3=root.findViewById(R.id.subFourImg3);
        subFourImg4=root.findViewById(R.id.subFourImg4);
//      Six Img
        subSixImg1=root.findViewById(R.id.subSixImg1);
        subSixImg2=root.findViewById(R.id.subSixImg2);
        subSixImg3=root.findViewById(R.id.subSixImg3);
        subSixImg4=root.findViewById(R.id.subSixImg4);
        subSixImg5=root.findViewById(R.id.subSixImg5);
        subSixImg6=root.findViewById(R.id.subSixImg6);

        subEightImg1.setOnClickListener(this);
        subEightImg2.setOnClickListener(this);
        subEightImg3.setOnClickListener(this);
        subEightImg4.setOnClickListener(this);
        subEightImg5.setOnClickListener(this);
        subEightImg6.setOnClickListener(this);
        subEightImg7.setOnClickListener(this);
        subEightImg8.setOnClickListener(this);
        subSixImg1.setOnClickListener(this);
        subSixImg2.setOnClickListener(this);
        subSixImg3.setOnClickListener(this);
        subSixImg4.setOnClickListener(this);
        subSixImg5.setOnClickListener(this);
        subSixImg6.setOnClickListener(this);
        subFourImg1.setOnClickListener(this);
        subFourImg2.setOnClickListener(this);
        subFourImg3.setOnClickListener(this);
        subFourImg4.setOnClickListener(this);




        cardView6=root.findViewById(R.id.cardViewSix8);
        sliderModelArrayList=new ArrayList<>();

        consEight=root.findViewById(R.id.consEight);
        consFour=root.findViewById(R.id.consFour);
        consSix=root.findViewById(R.id.consSix);

        consEight.setVisibility(View.GONE);



        demoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), demo_choice.class));
            }
        });

        moreSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              // Toast.makeText(getContext(),consSelect5 +" /"+consSelect6+"/"+consSelect8,Toast.LENGTH_LONG).show();
                if(showMoreCount==0) {
                    showMoreCount=1;
                    moreSubjectBtn.setText("See less subjects");
                    if(consSelect5==1){
                        consSix.setVisibility(View.VISIBLE);
                        consFour.setVisibility(View.GONE);
                        consEight.setVisibility(View.GONE);
                        cardView6.setVisibility(View.GONE);
                        //consSix.setOnClickListener();
                        if(getContext()!=null && upperClass_1.size()>0) {
                            Glide.with(getContext()).load(upperClass_1.get(0).getImage()).into(subSixImg1);
                            Glide.with(getContext()).load(upperClass_1.get(1).getImage()).into(subSixImg2);
                            Glide.with(getContext()).load(upperClass_1.get(2).getImage()).into(subSixImg3);
                            Glide.with(getContext()).load(upperClass_1.get(3).getImage()).into(subSixImg4);
                            Glide.with(getContext()).load(upperClass_1.get(4).getImage()).into(subSixImg5);
                        }
                    }
                    else if(consSelect6==1){
                        consSix.setVisibility(View.VISIBLE);
                        consFour.setVisibility(View.GONE);
                        consEight.setVisibility(View.GONE);
                        cardView6.setVisibility(View.VISIBLE);
                        if(getContext()!=null && midClass_1.size()>0) {
                            Glide.with(getContext()).load(midClass_1.get(0).getImage()).into(subSixImg1);
                            Glide.with(getContext()).load(midClass_1.get(1).getImage()).into(subSixImg2);
                            Glide.with(getContext()).load(midClass_1.get(2).getImage()).into(subSixImg3);
                            Glide.with(getContext()).load(midClass_1.get(3).getImage()).into(subSixImg4);
                            Glide.with(getContext()).load(midClass_1.get(4).getImage()).into(subSixImg5);
                            Glide.with(getContext()).load(midClass_1.get(5).getImage()).into(subSixImg6);
                        }
                    }
                    else if(consSelect8==1){
                        consEight.setVisibility(View.VISIBLE);
                        consFour.setVisibility(View.GONE);
                        consSix.setVisibility(View.GONE);
                        cardView6.setVisibility(View.VISIBLE);
                        if(getContext()!=null && lowerClass_1.size()>0) {
                            Glide.with(getContext()).load(lowerClass_1.get(0).getImage()).into(subEightImg1);
                            Glide.with(getContext()).load(lowerClass_1.get(1).getImage()).into(subEightImg2);
                            Glide.with(getContext()).load(lowerClass_1.get(2).getImage()).into(subEightImg3);
                            Glide.with(getContext()).load(lowerClass_1.get(3).getImage()).into(subEightImg4);
                            Glide.with(getContext()).load(lowerClass_1.get(4).getImage()).into(subEightImg5);
                            Glide.with(getContext()).load(lowerClass_1.get(5).getImage()).into(subEightImg6);
                            Glide.with(getContext()).load(lowerClass_1.get(6).getImage()).into(subEightImg7);
                            Glide.with(getContext()).load(lowerClass_1.get(7).getImage()).into(subEightImg8);
                        }
                    }

                }
                else{
                    showMoreCount=0;
                    moreSubjectBtn.setText("See more subjects");

                    if(consSelect8==1){
                        if(getContext()!=null && lowerClass_1.size()>0) {
                            Glide.with(getContext()).load(lowerClass_1.get(0).getImage()).into(subFourImg1);
                            Glide.with(getContext()).load(lowerClass_1.get(1).getImage()).into(subFourImg2);
                            Glide.with(getContext()).load(lowerClass_1.get(2).getImage()).into(subFourImg3);
                            Glide.with(getContext()).load(lowerClass_1.get(3).getImage()).into(subFourImg4);
                        }
                        consFour.setVisibility(View.VISIBLE);
                        consEight.setVisibility(View.GONE);
                        consSix.setVisibility(View.GONE);
                    }
                    if(consSelect6==1){
                        if(getContext()!=null && midClass_1.size()>0) {
                            Glide.with(getContext()).load(midClass_1.get(0).getImage()).into(subFourImg1);
                            Glide.with(getContext()).load(midClass_1.get(1).getImage()).into(subFourImg2);
                            Glide.with(getContext()).load(midClass_1.get(2).getImage()).into(subFourImg3);
                            Glide.with(getContext()).load(midClass_1.get(3).getImage()).into(subFourImg4);
                        }
                        consFour.setVisibility(View.VISIBLE);
                        consEight.setVisibility(View.GONE);
                        consSix.setVisibility(View.GONE);
                    }
                    if(consSelect5==1){
                        if(getContext()!=null && upperClass_1.size()>0) {
                            Glide.with(getContext()).load(upperClass_1.get(0).getImage()).into(subFourImg1);
                            Glide.with(getContext()).load(upperClass_1.get(1).getImage()).into(subFourImg2);
                            Glide.with(getContext()).load(upperClass_1.get(2).getImage()).into(subFourImg3);
                            Glide.with(getContext()).load(upperClass_1.get(3).getImage()).into(subFourImg4);
                        }
                        consFour.setVisibility(View.VISIBLE);
                        consEight.setVisibility(View.GONE);
                        consSix.setVisibility(View.GONE);
                    }
                }
            }
        });

        //hot course some dummy element added
        hotCourseModelArrayList.add(new HotCourseModel("Hot Course 1"));
        hotCourseModelArrayList.add(new HotCourseModel("Hot Course 2"));
        hotCourseModelArrayList.add(new HotCourseModel("Hot Course 3"));
        hotCourseModelArrayList.add(new HotCourseModel("Hot Course 4"));
        hotCourseModelArrayList.add(new HotCourseModel("Hot Course 5"));
        hotCourseModelArrayList.add(new HotCourseModel("Hot Course 6"));
        //in practice test some dummy data added
        practiceTestModelArrayList.add(new PracticeTestModel(R.drawable.maths));
        practiceTestModelArrayList.add(new PracticeTestModel(R.drawable.maths));
        practiceTestModelArrayList.add(new PracticeTestModel(R.drawable.maths));
        practiceTestModelArrayList.add(new PracticeTestModel(R.drawable.maths));
        practiceTestModelArrayList.add(new PracticeTestModel(R.drawable.maths));
        practiceTestModelArrayList.add(new PracticeTestModel(R.drawable.maths));

        //slide recyclerView dummy data added
        sliderModelArrayList.add(new SliderModel(R.drawable.sample1));
        sliderModelArrayList.add(new SliderModel(R.drawable.sample2));
        sliderModelArrayList.add(new SliderModel(R.drawable.sample3));
        sliderModelArrayList.add(new SliderModel(R.drawable.sample4));


        //hot course adapter, layout Manager set and itemClick of hot course
        hotCourseItem=new HotCourseAdapter.ItemClicked() {
            @Override
            public void onItemClicked(int index) {
                Toast.makeText(getContext(), "Hot Course "+(index+1), Toast.LENGTH_SHORT).show();
            }
        };
        hotCourseAdapter=new HotCourseAdapter(getContext(),hotCourseModelArrayList,hotCourseItem);
        layoutManagerHotCourse=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        hotCourseRecyclerView.setLayoutManager(layoutManagerHotCourse);
        hotCourseRecyclerView.setAdapter(hotCourseAdapter);

        //practice test adapter,layout Manager set and itemClick of practice test

        practiceTestItem=new PracticeTestAdapter.ItemClicked() {
            @Override
            public void onItemClicked(int index) {
                startActivity(new Intent(getActivity().getApplicationContext(), SubjectOptions.class));
                Toast.makeText(getActivity().getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
            }
        };
        practiceTestAdapter=new PracticeTestAdapter(getContext(),practiceTestModelArrayList,practiceTestItem);
        layoutManagerPracticeTest=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        practiceTestRecyclerView.setLayoutManager(layoutManagerPracticeTest);
        practiceTestRecyclerView.setAdapter(practiceTestAdapter);


        //slide recyclerview adapter set and also dots are set
        sliderAdapter=new SliderAdapter(sliderModelArrayList);
        pager2.setAdapter(sliderAdapter);

        dots=new TextView[sliderModelArrayList.size()];
        dotsIndicator();

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectedIndicator(position);
                super.onPageSelected(position);
            }
        });


        return root;
    }

    public void loadData(){
        lowClassRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Map<String,Object> map= (Map<String, Object>) documentSnapshot.get("Subjects");
                            Object[] key= map.keySet().toArray();

                            for(int i=0;i<map.size();i++){
                                SubjectsModel subjectsModel1=documentSnapshot.get("Subjects."+key[i].toString(),SubjectsModel.class);
                                lowerClass_1.add(subjectsModel1);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ApplicationClass.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        midClassRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Map<String,Object> map= (Map<String, Object>) documentSnapshot.get("Subjects");
                            Object[] key= map.keySet().toArray();

                            for(int i=0;i<map.size();i++){
                                SubjectsModel subjectsModel1=documentSnapshot.get("Subjects."+key[i].toString(),SubjectsModel.class);
                                midClass_1.add(subjectsModel1);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ApplicationClass.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        upperClassRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Map<String,Object> map= (Map<String, Object>) documentSnapshot.get("Subjects");
                            Object[] key= map.keySet().toArray();
                            String[] names=new String[map.size()];
                            for(int i=0;i<map.size();i++){
                                SubjectsModel subjectsModel1=documentSnapshot.get("Subjects."+key[i].toString(),SubjectsModel.class);
                                upperClass_1.add(subjectsModel1);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ApplicationClass.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void selectedIndicator(int position) {
            for(int i=0;i<dots.length;i++){
                if(i==position){
                    dots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                else{
                    dots[i].setTextColor(getResources().getColor(R.color.colorGray));
                }
            }
    }

    private void dotsIndicator() {
        for(int i=0;i<dots.length;i++){
            dots[i]=new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(20);
            dotsLayout.addView(dots[i]);
        }
    }

    @Override
    public void spinnerClicked(int index) {
        classSelected=index+1;
        if(index>=0&& index<=4){
            consSelect8=1;consSelect5=0;consSelect6=0;
            consEight.setVisibility(View.GONE);
            consFour.setVisibility(View.VISIBLE);
            consSix.setVisibility(View.GONE);
            showMoreCount=0;
            moreSubjectBtn.setText("See more subjects");


            if(getContext()!=null && lowerClass_1.size()>0) {
                Glide.with(getContext()).load(lowerClass_1.get(0).getImage()).into(subFourImg1);
                Glide.with(getContext()).load(lowerClass_1.get(1).getImage()).into(subFourImg2);
                Glide.with(getContext()).load(lowerClass_1.get(2).getImage()).into(subFourImg3);
                Glide.with(getContext()).load(lowerClass_1.get(3).getImage()).into(subFourImg4);
            }
        }
        else if(index>=5 && index<=9){
            consSelect6=1;consSelect8=0;consSelect5=0;
            consEight.setVisibility(View.GONE);
            consFour.setVisibility(View.VISIBLE);
            consSix.setVisibility(View.GONE);
            showMoreCount=0;
            moreSubjectBtn.setText("See more subjects");

            if(getContext()!=null && midClass_1.size()>0) {
                Glide.with(getContext()).load(midClass_1.get(0).getImage()).into(subFourImg1);
                Glide.with(getContext()).load(midClass_1.get(1).getImage()).into(subFourImg2);
                Glide.with(getContext()).load(midClass_1.get(2).getImage()).into(subFourImg3);
                Glide.with(getContext()).load(midClass_1.get(3).getImage()).into(subFourImg4);
            }
        }
        else if(index>=10 && index<=11){
            consSelect5=1;consSelect8=0;consSelect6=0;
            consEight.setVisibility(View.GONE);
            consFour.setVisibility(View.VISIBLE);
            consSix.setVisibility(View.GONE);
            showMoreCount=0;
            moreSubjectBtn.setText("See more subjects");

            if(getContext()!=null && upperClass_1.size()>0) {
                Glide.with(getContext()).load(upperClass_1.get(0).getImage()).into(subFourImg1);
                Glide.with(getContext()).load(upperClass_1.get(1).getImage()).into(subFourImg2);
                Glide.with(getContext()).load(upperClass_1.get(2).getImage()).into(subFourImg3);
                Glide.with(getContext()).load(upperClass_1.get(3).getImage()).into(subFourImg4);
            }
        }
    }

    @Override
    public void onCreate(@androidx.annotation.Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if( classSelected>=0 && classSelected<=5)
        {
            consSelect8=1;consSelect5=0;consSelect6=0;
        }
        else if(classSelected>=6 && classSelected<=9){
            consSelect8=0;consSelect5=0;consSelect6=1;
        }
        else if(classSelected>=10 && classSelected<=11)
        {
            consSelect8=0;consSelect5=1;consSelect6=0;
        }


        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        DocumentReference ref=fStore.collection("users").document(userId);
        ref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String nameTxt=documentSnapshot.getString("firstName");
                        welcomeMsg.setText("Hello "+nameTxt+",");
                        emailId=documentSnapshot.getString("email");
                        phone=documentSnapshot.getString("phone");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.subEightImg1 :
            case R.id.subSixImg6:
            case R.id.subFourImg1:
            case R.id.subSixImg2:
            case R.id.subEightImg8 :
            case R.id.subSixImg1 :
            case R.id.subEightImg5 :
            case R.id.subEightImg3 :
            case R.id.subEightImg4 :
            case R.id.subEightImg2:
            case R.id.subFourImg2:
                startActivity(new Intent(getContext(),SubjectOptions.class));
                break;
            case R.id.subEightImg6 :
            case R.id.subEightImg7 :
            case R.id.subSixImg3:
            case R.id.subSixImg4:
                Intent intent=new Intent(getContext(),payment_inquiry.class);
                intent.putExtra("email",emailId);
                intent.putExtra("phone",phone);
                startActivity(intent);
                break;
            case R.id.subFourImg3:
                if(classSelected>=7 && classSelected<=10)
                {
                    intent=new Intent(getContext(),payment_inquiry.class);
                    intent.putExtra("email",emailId);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }
                else {
                    startActivity(new Intent(getContext(),SubjectOptions.class));
                }
                break;
            case R.id.subFourImg4:
                if(classSelected>=7 && classSelected<=10)
                {
                    intent=new Intent(getContext(),payment_inquiry.class);
                    intent.putExtra("email",emailId);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }
                else if(classSelected>=11 && classSelected<=12)
                {
                    intent=new Intent(getContext(),payment_inquiry.class);
                    intent.putExtra("email",emailId);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }
                else {
                    startActivity(new Intent(getContext(),SubjectOptions.class));
                }
                break;
            case R.id.subSixImg5:
                if(classSelected>=11 && classSelected<=12)
                {
                    intent=new Intent(getContext(),payment_inquiry.class);
                    intent.putExtra("email",emailId);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }
                else {
                    startActivity(new Intent(getContext(),SubjectOptions.class));
                }
                break;


        }
    }

}