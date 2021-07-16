package com.example.acadup.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.acadup.Models.ConnectUsModel;
import com.example.acadup.R;
import com.example.acadup.Splash;

import java.util.ArrayList;

public class ConnectUsAdapter extends RecyclerView.Adapter<ConnectUsAdapter.ConnectHolder> {
    ArrayList<ConnectUsModel> list;
    Context context;
    ContactClick contactClick;

    public interface ContactClick{
        void clicking(int index);
    }

    public ConnectUsAdapter(Context c, ArrayList<ConnectUsModel> list,ContactClick contactClick){
        this.context=c;
        this.list=list;
        this.contactClick=contactClick;

    }

    @NonNull
    @Override
    public ConnectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.connect_show_layout,parent,false);
        return new ConnectHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectHolder holder, int position) {
        if(list.size()>0) {
            holder.headingContact.setText(list.get(position).getTitle());
            holder.linkContact.setText(list.get(position).getLink());
            Glide.with(context).load(list.get(position).getImage()).into(holder.imgContact);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ConnectHolder extends RecyclerView.ViewHolder{
        ImageView imgContact;
        TextView headingContact;
        TextView linkContact;

        public ConnectHolder(@NonNull View itemView) {
            super(itemView);
            imgContact=itemView.findViewById(R.id.handleImg);
            headingContact=itemView.findViewById(R.id.heading);
            linkContact=itemView.findViewById(R.id.link);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contactClick.clicking(getAbsoluteAdapterPosition());

                }
            });
        }
    }
}
