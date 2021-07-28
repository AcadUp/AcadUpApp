package com.example.acadup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.acadup.Adapters.VideoListHolderAdapter;
import com.example.acadup.Models.VideoListHolderModel;

import java.util.ArrayList;

public class TopicVideoLists extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<VideoListHolderModel> vdHolderList;
    VideoListHolderAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_topic_video_lists);
        back=findViewById(R.id.back);
        recyclerView=findViewById(R.id.recycler);
        vdHolderList=new ArrayList<>();
        vdHolderList.add(new VideoListHolderModel("Introduction to Similarity of Shapes","12:45"));
        vdHolderList.add(new VideoListHolderModel("Introduction to Similarity of Triangles","09:19"));
        vdHolderList.add(new VideoListHolderModel("Basics Proportionality Theorem and Its Converse","11:08"));
        vdHolderList.add(new VideoListHolderModel("Application of Proportionality Theorem","10:48"));

        adapter=new VideoListHolderAdapter(TopicVideoLists.this,vdHolderList);
        layoutManager=new LinearLayoutManager(TopicVideoLists.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}