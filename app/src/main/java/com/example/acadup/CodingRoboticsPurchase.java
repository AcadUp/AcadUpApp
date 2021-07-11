package com.example.acadup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class CodingRoboticsPurchase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_robotics_purchase);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

//set adapter to your ViewPager
        viewPager.setAdapter(new TabPagerAdapter(getFragmentManager()));

        RoundTabLayout tabLayout = (RoundTabLayout) findViewById(R.id.roundTabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }
}