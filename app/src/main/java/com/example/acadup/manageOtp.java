package com.example.acadup;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class manageOtp extends AppCompatActivity {
    EditText otp;
    Button verify;
    String phoneno, otpid;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);
        otp = findViewById(R.id.t2);
        auth = FirebaseAuth.getInstance();
        verify = findViewById(R.id.b2);
        phoneno = getIntent().getStringExtra("mobile");
        initiateotp();

       verify.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(otp.getText().toString().isEmpty())
               {
                   Toast.makeText(manageOtp.this, "OTP is empty Please try again ", Toast.LENGTH_SHORT).show();

               }
               else if(otp.getText().toString().length()!=6)
               {
                   Toast.makeText(manageOtp.this, "Invalid Otp", Toast.LENGTH_SHORT).show();

               }
               else
               {
                   PhoneAuthCredential credential =PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                   signInWithPhoneAuthCredential(credential);
               }
           }
       });
    }

    private void initiateotp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                                Toast.makeText(manageOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("checkstatus", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            Toast.makeText(manageOtp.this, "there is an Error please try again later ", Toast.LENGTH_SHORT).show();

                            Log.w("check the user", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }

}