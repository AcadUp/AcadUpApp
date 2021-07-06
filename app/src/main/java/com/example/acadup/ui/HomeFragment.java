package com.example.acadup.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.acadup.Adapters.HotCourseAdapter;
import com.example.acadup.Adapters.PracticeTestAdapter;
import com.example.acadup.Adapters.SliderAdapter;
import com.example.acadup.Models.HotCourseModel;
import com.example.acadup.Models.PracticeTestModel;
import com.example.acadup.Models.SliderModel;
import com.example.acadup.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    AppCompatButton moreSubjectBtn;
    RecyclerView hotCourseRecyclerView,practiceTestRecyclerView,whyAcadupRecyclerView,subjectRecyclerView;
    ArrayList<HotCourseModel> hotCourseModelArrayList;
    ArrayList<PracticeTestModel> practiceTestModelArrayList;
    RecyclerView.LayoutManager layoutManagerHotCourse,layoutManagerPracticeTest,layoutManagerWhyAcadup,layoutManagerSubject;
    HotCourseAdapter hotCourseAdapter;
    PracticeTestAdapter practiceTestAdapter;
    HotCourseAdapter.ItemClicked hotCourseItem;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    ViewPager2 pager2;
    ArrayList<SliderModel> sliderModelArrayList;
    TextView[] dots;
    ConstraintLayout consEight,consFour;
    int showMoreCount=0;


    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        root= inflater.inflate(R.layout.fragment_home, container, false);
        moreSubjectBtn=root.findViewById(R.id.moreSubButton);

        hotCourseRecyclerView=root.findViewById(R.id.hotCourseRecyclerView);
        practiceTestRecyclerView=root.findViewById(R.id.practiceTestRecyclerView);
        hotCourseModelArrayList=new ArrayList<>();
        practiceTestModelArrayList=new ArrayList<>();
        pager2=root.findViewById(R.id.view_pager2);
        dotsLayout=root.findViewById(R.id.dots_container);
        sliderModelArrayList=new ArrayList<>();

        consEight=root.findViewById(R.id.consEight);
        consFour=root.findViewById(R.id.consFour);

        consEight.setVisibility(View.GONE);


        moreSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showMoreCount==0) {
                    showMoreCount=1;
                    moreSubjectBtn.setText("See less subjects");
                    consEight.setVisibility(View.VISIBLE);
                    consFour.setVisibility(View.GONE);
                }
                else{
                    showMoreCount=0;
                    moreSubjectBtn.setText("See more subjects");
                    consFour.setVisibility(View.VISIBLE);
                    consEight.setVisibility(View.GONE);
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
        practiceTestAdapter=new PracticeTestAdapter(getContext(),practiceTestModelArrayList);
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
}