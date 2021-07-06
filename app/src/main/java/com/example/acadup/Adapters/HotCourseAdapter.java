package com.example.acadup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acadup.Models.HotCourseModel;
import com.example.acadup.R;
import com.example.acadup.Models.HotCourseModel;
import com.example.acadup.R;

import java.util.ArrayList;

public class HotCourseAdapter extends RecyclerView.Adapter<HotCourseAdapter.HotCourseHolder> {

    private ArrayList<HotCourseModel> hotCourseModelsName;
    private Context context;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public HotCourseAdapter(Context context,ArrayList<HotCourseModel> hotCourseModelsName,ItemClicked pos){
        this.context=context;
        this.hotCourseModelsName=hotCourseModelsName;
        this.activity=pos;
    }

    @NonNull
    @Override
    public HotCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_course_layout,parent,false);
        return new HotCourseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotCourseHolder holder, int position) {
        holder.hotCourseName.setText(hotCourseModelsName.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return hotCourseModelsName.size();
    }


    public class HotCourseHolder extends RecyclerView.ViewHolder{
        TextView hotCourseName;
        public HotCourseHolder(@NonNull View itemView) {
            super(itemView);
            hotCourseName=itemView.findViewById(R.id.hotCourseTxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onItemClicked(getAdapterPosition());
                }
            });
        }
    }

}
