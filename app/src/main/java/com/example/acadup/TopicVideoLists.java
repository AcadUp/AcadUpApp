package com.example.acadup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acadup.Models.VideoListHolderModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TopicVideoLists extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<VideoListHolderModel> vdHolderList;
//    VideoListHolderAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    FirestoreRecyclerAdapter fAdapter;
    ImageView back,showImg;
    FirebaseFirestore fireStore;
    String chapterName,chapterImg,sub;
    int classNum;
    TextView headingTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_topic_video_lists);
        sub=getIntent().getStringExtra("subject");
        classNum=getIntent().getIntExtra("class",1);
        chapterName=getIntent().getStringExtra("chapterName");
        chapterImg=getIntent().getStringExtra("chapterImage");
        back=findViewById(R.id.back);
        headingTxt=findViewById(R.id.headingTxt);
        showImg=findViewById(R.id.showImg);
        recyclerView=findViewById(R.id.recycler);

        headingTxt.setText(chapterName);
        Glide.with(this).load(chapterImg).into(showImg);

        vdHolderList=new ArrayList<>();
        fireStore=FirebaseFirestore.getInstance();
        Query query= fireStore.collection("NCERT").document(sub).collection(String.valueOf(classNum))
                .document(chapterName).collection("Topics");

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(error!=null) {
                    Toast.makeText(TopicVideoLists.this, "No data Found", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        FirestoreRecyclerOptions<VideoListHolderModel> options=new FirestoreRecyclerOptions.Builder<VideoListHolderModel>()
                .setQuery(query, VideoListHolderModel.class)
                .build();
        fAdapter=new FirestoreRecyclerAdapter<VideoListHolderModel, VideoListHolder>(options) {


            @NonNull
            @Override
            public VideoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_holder,parent,false);
                return new VideoListHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull VideoListHolder holder, int position, @NonNull VideoListHolderModel model) {
                holder.videoHeading.setText(model.getName());
                holder.videoDuration.setText("Duration -"+model.getDuration());
                holder.dotBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Dot "+position, Toast.LENGTH_SHORT).show();
                    }
                });
                holder.playBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(getApplicationContext(), "Play "+position, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(TopicVideoLists.this,VideoPlayActivity.class);
                        intent.putExtra("video_link",model.getVideo());
                        intent.putExtra("chapter_name",model.getName());
                        startActivity(intent);
                    }
                });
                holder.notesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(getApplicationContext(), "Notes "+position, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), PDFOpening.class);
                        intent.putExtra("pdf_link",model.getNotes_pdf());
                        startActivity(intent);
                    }
                });
                holder.quizBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(getApplicationContext(),QuizIntroActivity.class);
                        intent.putExtra("from_which_activity",100);
                        intent.putExtra("quiz_time",30);

                        intent.putExtra("subject",sub);
                        intent.putExtra("classNum",String.valueOf(classNum));
                        intent.putExtra("chapterName",chapterName);
                        intent.putExtra("quiz_name",model.getName());


                        startActivity(intent);
                    }
                });
                holder.trackBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Track "+position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        layoutManager=new LinearLayoutManager(TopicVideoLists.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private class VideoListHolder extends RecyclerView.ViewHolder{
        TextView videoHeading,videoDuration;
        ImageView dotBtn,playBtn;
        AppCompatButton notesBtn,quizBtn,trackBtn;

        public VideoListHolder(@NonNull View itemView) {
            super(itemView);
            videoHeading=itemView.findViewById(R.id.headingVd);
            videoDuration=itemView.findViewById(R.id.durationVd);
            dotBtn=itemView.findViewById(R.id.dotsBtn);
            playBtn=itemView.findViewById(R.id.playBtn);
            notesBtn=itemView.findViewById(R.id.notesBtn);
            quizBtn=itemView.findViewById(R.id.quizBtn);
            trackBtn=itemView.findViewById(R.id.trackBtn);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAdapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fAdapter.startListening();
    }
}