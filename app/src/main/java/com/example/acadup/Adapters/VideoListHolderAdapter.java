package com.example.acadup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acadup.Models.VideoListHolderModel;
import com.example.acadup.R;

import java.util.ArrayList;

public class VideoListHolderAdapter extends RecyclerView.Adapter<VideoListHolderAdapter.VideoListHolder> {
    ArrayList<VideoListHolderModel> list;
    Context context;

    public VideoListHolderAdapter(Context c, ArrayList<VideoListHolderModel> list){
        this.context=c;
        this.list=list;
    }

    @NonNull
    @Override
    public VideoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.video_list_holder,parent,false);
        return new VideoListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListHolder holder, int position) {
        holder.videoHeading.setText(list.get(position).getVideo_name());
        holder.videoDuration.setText("Duration -"+list.get(position).getVideo_duration());
        holder.dotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Dot "+position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Play "+position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Notes "+position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Quiz "+position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Track "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class VideoListHolder extends RecyclerView.ViewHolder{
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
}
