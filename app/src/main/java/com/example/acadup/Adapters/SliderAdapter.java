package com.example.acadup.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acadup.Models.SliderModel;

import com.example.acadup.R;


import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderHolder> {
    ArrayList<SliderModel> list;

    public SliderAdapter(ArrayList<SliderModel> sliderList) {
        this.list = sliderList;
    }

    @NonNull
    @Override
    public SliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false);
        return new SliderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderHolder holder, int position) {
        holder.ImgView.setBackgroundResource(list.get(position).getSlideImg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SliderHolder extends RecyclerView.ViewHolder{
        ImageView ImgView;
        public SliderHolder(@NonNull View itemView) {
            super(itemView);
            ImgView=itemView.findViewById(R.id.ImageView);
        }
    }

}
