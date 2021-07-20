package com.example.acadup.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.acadup.FragmentAdapter;
import com.example.acadup.LoginActivity;
import com.example.acadup.MainActivity;
import com.example.acadup.R;
import com.example.acadup.UpdateProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class DashboardFragment extends Fragment {

    TextView logoutBtn,editProfile,nameTv,classTv;
    SwipeRefreshLayout swipeRefreshLayout;
    CircleImageView profileImageView;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userId;
    FirebaseUser user;
    final String[] email = new String[1];
    final String[] phone = new String[1];

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        logoutBtn=root.findViewById(R.id.logoutBtn);
        editProfile=root.findViewById(R.id.edirprofile);
        nameTv=root.findViewById(R.id.name);
        classTv=root.findViewById(R.id.classTv);
        swipeRefreshLayout=root.findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                        profileImageView.setRotation(90);
                    }
                });

                userId = fAuth.getCurrentUser().getUid();
                user = fAuth.getCurrentUser();
                DocumentReference documentReference = fStore.collection("users").document(userId);
                documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot.exists()){
                            nameTv.setText(documentSnapshot.getString("firstName")+" "+documentSnapshot.getString("lastName"));
                            classTv.setText(documentSnapshot.getString("class"));
                            phone[0] =documentSnapshot.getString("phone");
                            email[0] =documentSnapshot.getString("email");
                        }else {
                            Log.d("tag", "onEvent: Document do not exists");
                        }
                    }
                });
            }
        });
        profileImageView = root.findViewById(R.id.profileImage);
        // changeProfileImage = findViewById(R.id.changeProfile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
                profileImageView.setRotation(90);
            }
        });

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    nameTv.setText(documentSnapshot.getString("firstName")+" "+documentSnapshot.getString("lastName"));
                    classTv.setText(documentSnapshot.getString("class"));
                    phone[0] =documentSnapshot.getString("phone");
                    email[0] =documentSnapshot.getString("email");
                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), UpdateProfile.class);
                String name=nameTv.getText().toString();
                String[] firstLastName=name.split(" ",2);
                intent.putExtra("firstName",firstLastName[0]);
                intent.putExtra("lastName",firstLastName[1]);
                intent.putExtra("email",email[0]);
                intent.putExtra("phone",phone[0]);
                intent.putExtra("class",classTv.getText().toString());
                startActivity(intent);
            }
        });

        tabLayout=root.findViewById(R.id.tab_layout);
        /*viewPager2=root.findViewById(R.id.viewpager);
        //FragmentManager fm=getActivity().getApplication().getSupportFragmentManager();
        //fragmentAdapter =new FragmentAdapter(fm,getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Stats"));
        tabLayout.addTab(tabLayout.newTab().setText("Tests"));
        tabLayout.addTab(tabLayout.newTab().setText("Question"));
        tabLayout.addTab(tabLayout.newTab().setText("Docs/Videos"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#C11515"));
            }
        });*/
        return root;
    }
}