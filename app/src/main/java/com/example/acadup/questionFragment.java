package com.example.acadup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.SimpleDateFormat;

public class questionFragment extends Fragment  {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    RecyclerView subjectChapters;
    FirestoreRecyclerAdapter adapter;
    ImageView backButton;
    String sub;
    int classNum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_question, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        subjectChapters = root.findViewById(R.id.subjectChapters);

        Query query = firestore.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("purchasedCourses");

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), "No data Found", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        FirestoreRecyclerOptions<SubjectChaptersModel> options = new FirestoreRecyclerOptions.Builder<SubjectChaptersModel>()
                .setQuery(query, SubjectChaptersModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<SubjectChaptersModel,PrevPDFViewHolder>(options) {

            @NonNull
            @NotNull
            @Override
            public PrevPDFViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attempted_test_single_item, parent, false);
                return new PrevPDFViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull PrevPDFViewHolder holder, int position, @NonNull @NotNull SubjectChaptersModel model) {
                holder.testName.setText(model.getCourse_name());
                holder.testScore.setText("Amount Paid : "+model.getAmount());
                SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy");
                String date=null;
                try {
                    date=format1.format(model.getDate());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                holder.testDate.setText("Date : "+date);
                Glide.with(getContext()).load(R.drawable.classes).into(holder.image);
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), PDFOpening.class);
                        //intent.putExtra("pdf_link", model.getPdf());
                        startActivity(intent);
                    }
                });
            }
        };

        //subjectChapters.setHasFixedSize(true);
        subjectChapters.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        subjectChapters.setAdapter(adapter);


        return root;
    }
    private class PrevPDFViewHolder extends RecyclerView.ViewHolder {

        private TextView testName,testScore,testDate;
        private LinearLayout linearLayout;
        private ImageView image;


        public PrevPDFViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            testName = itemView.findViewById(R.id.testName);
            testDate = itemView.findViewById(R.id.testDate);
            testScore = itemView.findViewById(R.id.testScore);
            image = itemView.findViewById(R.id.testImage);
            linearLayout = itemView.findViewById(R.id.linearLayout);

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    @Override
    public void onStart () {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onResume () {
        super.onResume();
        adapter.startListening();
    }
}