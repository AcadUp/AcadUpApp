package com.example.acadup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.acadup.Adapters.ConnectUsAdapter;
import com.example.acadup.Models.ConnectUsModel;
import com.example.acadup.Models.SubjectsModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class ConnectUsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ConnectUsAdapter connectUsAdapter;
    ArrayList<ConnectUsModel> connect;
    RecyclerView.LayoutManager layoutManager;
    ConnectUsAdapter.ContactClick cClick;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference connectRef=db.document("ConnectPages/sites");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_us);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        connect=new ArrayList<>();


        connectRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Map<String,Object> map1=documentSnapshot.getData();
                            Object[] key= map1.keySet().toArray();
                            for(int i=0;i<map1.size();i++){
                                ConnectUsModel connectUsModel=documentSnapshot.get(key[i].toString(),ConnectUsModel.class);
                                connect.add(connectUsModel);
                            }
                            connectUsAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        cClick=new ConnectUsAdapter.ContactClick() {
            @Override
            public void clicking(int index) {
//                Toast.makeText(ConnectUsActivity.this, connect.get(index).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(connect.get(index).getLink()));
                startActivity(intent);

            }
        };
        connectUsAdapter=new ConnectUsAdapter(this,connect,cClick);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(connectUsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}