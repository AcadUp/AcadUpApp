package com.example.acadup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acadup.Models.SubjectChaptersModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestsActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    RecyclerView subjectChapters;
    FirestoreRecyclerAdapter adapter;
    ImageView backButton;
    String sub;
    int classNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        getSupportActionBar().hide();

        sub=getIntent().getStringExtra("subject");
        classNum=getIntent().getIntExtra("class",1);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestsActivity.this.onBackPressed();
            }
        });
        subjectChapters=findViewById(R.id.subjectChapters);

        Query query= firestore.collection("NCERT").document(sub).collection(String.valueOf(classNum));

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(error!=null) {
                    Toast.makeText(TestsActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        FirestoreRecyclerOptions<SubjectChaptersModel> options=new FirestoreRecyclerOptions.Builder<SubjectChaptersModel>()
                .setQuery(query, SubjectChaptersModel.class)
                .build();

        adapter=new FirestoreRecyclerAdapter<SubjectChaptersModel, TestsActivity.TestsViewHolder>(options) {

            @NonNull
            @NotNull
            @Override
            public TestsActivity.TestsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.concept_pdf_single_item,parent,false);
                return new TestsActivity.TestsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull TestsActivity.TestsViewHolder holder, int position, @NonNull @NotNull SubjectChaptersModel model) {
                holder.name.setText(model.getName());
                Glide.with(getApplicationContext()).load(model.getImage()).into(holder.image);
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(getApplicationContext(),QuizIntroActivity.class);
                        intent.putExtra("from_which_activity",102);
                        intent.putExtra("quiz_time",30);
                        //intent.putExtra("pdfLink",model.getAnswerPdf());
                        intent.putExtra("subject",sub);
                        intent.putExtra("classNum",String.valueOf(classNum));
                        intent.putExtra("chapterName",model.getName());
                        startActivity(intent);

//                        Toast.makeText(TestsActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        //subjectChapters.setHasFixedSize(true);
        subjectChapters.setLayoutManager(new LinearLayoutManager(this));
        subjectChapters.setAdapter(adapter);

    }
    private  class TestsViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private LinearLayout linearLayout;
        private CircleImageView image;


        public TestsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.chName);
            image=itemView.findViewById(R.id.chImage);
            linearLayout=itemView.findViewById(R.id.linearLayout);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }
}