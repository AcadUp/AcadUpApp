package com.example.acadup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acadup.Adapters.CustomExpandableAdapter;
import com.example.acadup.Models.ExpandableModel;
import com.example.acadup.ui.DashboardFragment;
import com.example.acadup.ui.HomeFragment;
import com.example.acadup.ui.NotificationsFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;
import com.google.type.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinner;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView nav;
    SubjectView subView;
    HomeFragment homeFragment;
    CustomExpandableAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    FirebaseAuth auth;
    FirebaseFirestore fireStore;

    String userId;
    public static int classTxt;
//    final int[] spinnerPos = new int[1];
    HashMap<String, List<ExpandableModel>> listDataChild;
    LinearLayout homeLayout,scheduleTrialLayout,upcomingLiveLayout,notesLayout,progressLayout
            ,leaderBoardLayout,referFriendLayout,logOutLayout,rateLayout,demoSlotsLayout;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fireStore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null  ){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }

        drawerLayout=findViewById(R.id.drawarLayout);
        nav=findViewById(R.id.navMenu);
        nav.setItemIconTintList(null);
        v=nav.getHeaderView(0);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        spinner=findViewById(R.id.spinner);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.classes,
                R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout); //Spinner Dropdown Text
        spinner.setAdapter(adapter);

        userId = auth.getCurrentUser().getUid();

//        DocumentReference documentReference = fireStore.collection("users").document(userId);
//        documentReference.addSnapshotListener(HomeActivity.this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                if(documentSnapshot.exists()){
//                    String classVal=documentSnapshot.getString("class");
//                    spinnerPos[0] =adapter.getPosition(classVal);
//                    spinner.setSelection(spinnerPos[0]);
//
//                }else {
//                    Log.d("tag", "onEvent: Document do not exists");
//                }
//            }
//        });
        loadClassFromFB();
        spinner.setOnItemSelectedListener(this);



        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);
        homeFragment=new HomeFragment();
        subView=homeFragment;

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment,"HOME_FRAGMENT").commit();

        homeLayout=v.findViewById(R.id.homeClick);
        scheduleTrialLayout=v.findViewById(R.id.scheduleClick);
        upcomingLiveLayout=v.findViewById(R.id.upcomingLiveClicked);
        notesLayout=v.findViewById(R.id.notesClicked);
        progressLayout=v.findViewById(R.id.progressClicked);
        leaderBoardLayout=v.findViewById(R.id.leaderboardClicked);
        referFriendLayout=v.findViewById(R.id.refer_friend);
        logOutLayout=v.findViewById(R.id.logOutLayout);
        rateLayout=v.findViewById(R.id.rateLayout);
        demoSlotsLayout=v.findViewById(R.id.demo_slots);

        //Expandable about
        expListView=v.findViewById(R.id.aboutExpand);

        expListView.setChildIndicator(null);
        expListView.setChildDivider(getResources().getDrawable(R.color.trans));
        expListView.setDividerHeight(0);
        // preparing list data
        prepareListData();
        listAdapter = new CustomExpandableAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) logOutLayout.getLayoutParams();
        params.setMargins(0,-380,0,0);
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) logOutLayout.getLayoutParams();
                params.setMargins(0,-380,0,0);

            }
        });
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) logOutLayout.getLayoutParams();
                params.setMargins(0,0,0,0);

            }
        });

        // child click listener

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " : " +
                                listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getText(),
                        Toast.LENGTH_SHORT).show();
                if(groupPosition==0 && childPosition==1){
                    startActivity(new Intent(HomeActivity.this,ConnectUsActivity.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        //nav_header item clicks
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(HomeActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        scheduleTrialLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Schedule Trail Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this,demo_choice.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        upcomingLiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Upcoming live Clicked", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        notesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "My notes Clicked", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        progressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Progress Report Clicked", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        leaderBoardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Leaderboard Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this,LeaderBoardActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        referFriendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Refer friend Clicked", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        demoSlotsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,demo_slot.class);
                intent.putExtra("FromHomeActivity",7);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        logOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Are you sure that you want to Logout?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        finishAffinity();
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                AlertDialog mDialog=builder.create();
                mDialog.show();                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        rateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Rate this app Clicked", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ExpandableModel>>();

        // Adding header data
        listDataHeader.add("About Us");
        // Adding child data
        List<ExpandableModel> about_us = new ArrayList<>();

        about_us.add(new ExpandableModel("About Company",R.drawable.company));
        about_us.add(new ExpandableModel("Connect with us",R.drawable.contacts));
        about_us.add(new ExpandableModel("Terms of use",R.drawable.terms));
        about_us.add(new ExpandableModel("Refund Policy",R.drawable.refund));
        about_us.add(new ExpandableModel("Privacy Policy",R.drawable.compliant));
        about_us.add(new ExpandableModel("FAQs",R.drawable.terms));

        // Header, Child data
        listDataChild.put(listDataHeader.get(0), about_us);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        expListView.setIndicatorBounds(expListView.getRight()-100,expListView.getWidth());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bell1:
                Toast.makeText(this, "Bell", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile:
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment(),"HOME_FRAGMENT").commit();
                            break;
                        case R.id.navigation_test:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NotificationsFragment()).commit();
                            break;
                        case R.id.navigation_dashboard:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commitNow();
                            break;
                        case R.id.navigation_notifications:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NotificationsFragment()).commit();
                            break;
                    }
                    return true;
                }
            };

    //Spinner item clicked
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        classTxt=i+1;
        subView.spinnerClicked(i);
        HomeFragment homeFrag=(HomeFragment)getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT");
        if(homeFrag!=null && homeFrag.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    void loadClassFromFB(){
        DocumentReference db=fireStore.collection("users").document(auth.getCurrentUser().getUid());
        db.addSnapshotListener(HomeActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()){
                            classTxt=Integer.parseInt(documentSnapshot.getString("class"))-1;
                            spinner.setSelection(classTxt);
                        }
                else{
                    Toast.makeText(HomeActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);

        }

    }
}