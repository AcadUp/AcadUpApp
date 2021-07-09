package com.example.acadup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    EditText firstNameEt,lastNameEt,emailEt,passwordEt,confirm_passwordEt,phoneEt;
    TextView loginBtn;
    MaterialButton signupBtn;
    FirebaseAuth fAuth;
    TextView errorTv;
    ProgressBar progressBar;
    CheckBox checkBox;
    Spinner classesSpinner;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstNameEt=findViewById(R.id.firstname);
        lastNameEt=findViewById(R.id.lastname);
        emailEt=findViewById(R.id.email);
        passwordEt=findViewById(R.id.password);
        confirm_passwordEt=findViewById(R.id.confirm_password);
        loginBtn=findViewById(R.id.loginBtn);
        signupBtn=findViewById(R.id.signupbtn);
        checkBox=findViewById(R.id.checkBox);
        phoneEt=findViewById(R.id.phone);
        errorTv=findViewById(R.id.errorTv);
        classesSpinner=findViewById(R.id.classSpinner);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEt.getText().toString().trim();
                final String password = passwordEt.getText().toString().trim();
                final String confirm_password = confirm_passwordEt.getText().toString().trim();
                final String firstName = firstNameEt.getText().toString();
                final String lastName    = lastNameEt.getText().toString();
                final String phone    = phoneEt.getText().toString();
                final String selectedClass=classesSpinner.getSelectedItem().toString();

                if(TextUtils.isEmpty(email)){
                    emailEt.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    passwordEt.setError("Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(confirm_password)){
                    confirm_passwordEt.setError("Confirm Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    phoneEt.setError("Phone Number is Required.");
                    return;
                }
                if(TextUtils.isEmpty(selectedClass)){
                    phoneEt.setError("Please select your class.");
                    return;
                }

                if(password.length() < 6){
                    passwordEt.setError("Password Must be >= 6 Characters");
                    errorTv.setText("Password Must be >= 6 Characters");
                    return;
                }
                if(phone.length() !=10){
                    phoneEt.setError("Phone number should be of 10 digits");
                    return;
                }
                if(!(password.equals(confirm_password)))
                {
                    errorTv.setText("Both Passwords should match");
                    return;
                }
                if(!checkBox.isChecked())
                {
                    errorTv.setText("Please agree to the Terms and Conditions");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // send verification link

                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(signup.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(signup.this, "User Created.", Toast.LENGTH_SHORT).show();
                                    userID = fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("users").document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("firstName",firstName);
                                    user.put("lastName",lastName);
                                    user.put("email",email);
                                    user.put("phone",phone);
                                    user.put("class",selectedClass);

                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG", "onSuccess: user Profile is created for "+ userID);
                                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("TAG", "onFailure: " + e.toString());
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: Email not sent " + e.getMessage());
                                }
                            });



                        }else {
                            Toast.makeText(signup.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }

}
