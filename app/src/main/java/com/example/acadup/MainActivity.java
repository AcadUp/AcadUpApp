package com.example.acadup;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

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

public class MainActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        getSupportActionBar().hide();
        logoutBtn=findViewById(R.id.logoutBtn);
        editProfile=findViewById(R.id.edirprofile);
        nameTv=findViewById(R.id.name);
        classTv=findViewById(R.id.classTv);
        swipeRefreshLayout=findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                        //profileImageView.setRotation(90);
                    }
                });

                userId = fAuth.getCurrentUser().getUid();
                user = fAuth.getCurrentUser();
                DocumentReference documentReference = fStore.collection("users").document(userId);
                documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
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
        profileImageView = findViewById(R.id.profileImage);
       // changeProfileImage = findViewById(R.id.changeProfile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
                //profileImageView.setRotation(90f);
            }
        });

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
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

                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure that you want to Logout?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        finishAffinity();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                AlertDialog mDialog=builder.create();
                mDialog.show();

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,UpdateProfile.class);
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

        tabLayout=findViewById(R.id.tab_layout);
        viewPager2=findViewById(R.id.viewpager);
        FragmentManager fm=getSupportFragmentManager();
        fragmentAdapter =new FragmentAdapter(fm,getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Stats"));
        tabLayout.addTab(tabLayout.newTab().setText("Attempted Tests"));
        tabLayout.addTab(tabLayout.newTab().setText("Purchased courses"));
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
        });


    }

}
