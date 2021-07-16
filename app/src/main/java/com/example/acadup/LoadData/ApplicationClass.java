package com.example.acadup.LoadData;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.acadup.Models.SubjectsModel;
import com.example.acadup.demo_choice;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class ApplicationClass extends Application {
    FirebaseFirestore db;
    DocumentReference lowClassRef,midClassRef,upperClassRef;

    public static ArrayList<SubjectsModel> lowerClass,midClass,upperClass;
    @Override
    public void onCreate() {
        super.onCreate();
        lowerClass=new ArrayList<>();
        midClass=new ArrayList<>();
        upperClass=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        lowClassRef=db.document("Class/lowerClass");
        midClassRef=db.document("Class/midClass");
        upperClassRef=db.document("Class/upperClass");
        loadData();
    }

    public void loadData(){
        lowClassRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Map<String,Object> map= (Map<String, Object>) documentSnapshot.get("Subjects");
                            Object[] key= map.keySet().toArray();

                            for(int i=0;i<map.size();i++){
                                SubjectsModel subjectsModel1=documentSnapshot.get("Subjects."+key[i].toString(),SubjectsModel.class);
                                lowerClass.add(subjectsModel1);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ApplicationClass.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        midClassRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Map<String,Object> map= (Map<String, Object>) documentSnapshot.get("Subjects");
                            Object[] key= map.keySet().toArray();

                            for(int i=0;i<map.size();i++){
                                SubjectsModel subjectsModel1=documentSnapshot.get("Subjects."+key[i].toString(),SubjectsModel.class);
                                midClass.add(subjectsModel1);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ApplicationClass.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        upperClassRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Map<String,Object> map= (Map<String, Object>) documentSnapshot.get("Subjects");
                            Object[] key= map.keySet().toArray();
                            String[] names=new String[map.size()];
                            for(int i=0;i<map.size();i++){
                                SubjectsModel subjectsModel1=documentSnapshot.get("Subjects."+key[i].toString(),SubjectsModel.class);
                                upperClass.add(subjectsModel1);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ApplicationClass.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
