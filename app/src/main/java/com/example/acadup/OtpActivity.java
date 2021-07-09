package com.example.acadup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class OtpActivity extends AppCompatActivity {
CountryCodePicker countryCodePicker;
EditText phoneno;
Button getotp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
    phoneno=findViewById(R.id.t1);
    countryCodePicker=findViewById(R.id.ccp);
        countryCodePicker.registerCarrierNumberEditText(phoneno);
        getotp=findViewById(R.id.b1);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OtpActivity.this,manageOtp.class);
                intent.putExtra("mobile", countryCodePicker.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
            }
        });
    }
}