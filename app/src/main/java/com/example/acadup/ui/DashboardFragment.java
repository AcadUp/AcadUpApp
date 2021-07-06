package com.example.acadup.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.acadup.R;


public class DashboardFragment extends Fragment {

    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textView = root.findViewById(R.id.text_dashboard);
        textView.setText("This is Dashboard Fragment");

        return root;
    }
}