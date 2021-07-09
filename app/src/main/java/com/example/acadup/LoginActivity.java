package com.example.acadup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;


public class LoginActivity extends AppCompatActivity {
    TextView signupBtn, google_tv,forgot_password,msg;
    EditText usernameEt, passwordEt;
    Button loginBtn, googlesignin, phonesignin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    public static final int RC_SIGN_IN = 12;
    GoogleSignInClient googleSignInClient;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        initUI();
        if(fAuth.getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified() ){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = usernameEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    usernameEt.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    passwordEt.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    passwordEt.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        if(task.isSuccessful()){
                            if(user.isEmailVerified())
                            {
                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                finish();
                            }
                            else {
                                FirebaseAuth.getInstance().signOut();
                                msg.setText("Please Verify you e-mail...");
                                msg.setTextSize(15);
                            }
                        }else {
                            Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });



        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email-id To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                msg.setText("Reset Link Sent To Your Email.");
                                msg.setTextSize(15);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });



        phonesignin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(LoginActivity.this,OtpActivity.class));

    }
});


    }


    private void initUI() {
        signupBtn = findViewById(R.id.signUp);
        usernameEt = findViewById(R.id.usernamelogin);
        passwordEt = findViewById(R.id.passwordlogin);
        loginBtn = findViewById(R.id.loginBtn);
        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        googlesignin = findViewById(R.id.signingoogle);
        phonesignin = findViewById(R.id.phonesignin);
        msg=findViewById(R.id.msg);
        forgot_password=findViewById(R.id.textView);
        progressBar=findViewById(R.id.progressBar);
        msg.setText("Welcome Back");
        msg.setTextSize(20);
    }


    public void signUpActivity(View view) {
        Log.i("inside","signup");
        startActivity(new Intent(LoginActivity.this,signup.class));
        finish();
    }
}