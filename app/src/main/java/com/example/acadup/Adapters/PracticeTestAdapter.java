package com.example.acadup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.acadup.Models.PracticeTestModel;
import com.example.acadup.R;

import java.util.ArrayList;

public class PracticeTestAdapter extends RecyclerView.Adapter<PracticeTestAdapter.PracticeTestHolder> {
    private Context context;
    ItemClicked activity;
    private ArrayList<PracticeTestModel> practiceTestModelArrayList;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public PracticeTestAdapter(Context context,ArrayList<PracticeTestModel> practiceTestModelArrayList,ItemClicked pos){
        this.context=context;
        this.practiceTestModelArrayList=practiceTestModelArrayList;
        this.activity=pos;
    }

    @NonNull
    @Override
    public PracticeTestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_test_layout,parent,false);
        return new PracticeTestHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PracticeTestHolder holder, int position) {
//        holder.practiceTestImage.setImageResource(practiceTestModelArrayList.get(position).getImageSource());
        Glide.with(context).load(practiceTestModelArrayList.get(position).getImageSource()).into(holder.practiceTestImage);
    }

    @Override
    public int getItemCount() {
        return practiceTestModelArrayList.size();
    }

    public class PracticeTestHolder extends RecyclerView.ViewHolder{
        ImageView practiceTestImage;
        public PracticeTestHolder(@NonNull View itemView) {
            super(itemView);
            practiceTestImage=itemView.findViewById(R.id.practiceImg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onItemClicked(getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
