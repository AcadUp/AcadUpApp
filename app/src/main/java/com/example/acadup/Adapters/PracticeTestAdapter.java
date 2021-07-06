package com.example.acadup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acadup.Models.PracticeTestModel;
import com.example.acadup.R;

import java.util.ArrayList;

public class PracticeTestAdapter extends RecyclerView.Adapter<PracticeTestAdapter.PracticeTestHolder> {
    private Context context;
    private ArrayList<PracticeTestModel> practiceTestModelArrayList;

    public PracticeTestAdapter(Context context,ArrayList<PracticeTestModel> practiceTestModelArrayList){
        this.context=context;
        this.practiceTestModelArrayList=practiceTestModelArrayList;
    }

    @NonNull
    @Override
    public PracticeTestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_test_layout,parent,false);
        return new PracticeTestHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PracticeTestHolder holder, int position) {
        holder.practiceTestImage.setImageResource(practiceTestModelArrayList.get(position).getImageSource());
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
        }
    }
}
