package com.example.acadup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    EditText emailLogIn,passwordLogIn;
    TextView forgotPassword,signUpPage;
    AppCompatButton signIn,googleSignInBtn,phoneSignInBtn;
    FrameLayout googleSignIn,phoneSignIn;

    TextView google_tv;
    FirebaseAuth auth;
    public static final int RC_SIGN_IN = 12;
    GoogleSignInClient googleSignInClient;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loginactivity);

        initUI();
        processRequest();

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogInGoogleSignIn();
            }
        });
        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogInGoogleSignIn();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLogIn.getText().toString().trim();
                String pass = passwordLogIn.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass))
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                else if (pass.length() < 6)
                    Toast.makeText(LoginActivity.this, "Very short password", Toast.LENGTH_SHORT).show();
                else {
                    logInUser(email, pass);
                }
            }
        });

        phoneSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,OtpActivity.class));
            }
        });
        phoneSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,OtpActivity.class));
            }
        });

        signUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, com.example.acadup.signup.class));
                finish();
            }
        });

    }


    private void processLogInGoogleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("google id", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

                Toast.makeText(LoginActivity.this, "Error getting Google's user information", Toast.LENGTH_SHORT).show();

                Log.w("error message google sign in ", "Google sign in failed", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("doit", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            //do the next work after login where it will redirect
                        } else {

                            Log.w("loginerror", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "there is an Error please try again later ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void processRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
        //   updateUI(currentUser);
    }

    private void logInUser(String user, String pass) {
        auth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                final FirebaseUser user = auth.getCurrentUser();

                if (isNetworkAvailable()) {
                    Toast.makeText(LoginActivity.this, "Update the Profile \n For Better Experience", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.i("loginerror -- >>", e.getMessage());
            }
        });
    }


    private void initUI() {
        emailLogIn=findViewById(R.id.emailLogin);//username
        passwordLogIn=findViewById(R.id.passwordLogin);//password
        forgotPassword=findViewById(R.id.forgot_pass);
        signUpPage=findViewById(R.id.signUpPage);//signup
        signIn=findViewById(R.id.logIn);//login
        googleSignIn=findViewById(R.id.googleSignIn);//googlesignin
        googleSignInBtn=findViewById(R.id.googleSignInBtn);
        phoneSignIn=findViewById(R.id.phoneSignIn);//phonesignin
        phoneSignInBtn=findViewById(R.id.phoneSignInBtn);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            google_tv.setText(Html.fromHtml("Unable to login through phone?<br>" + "<u>Connect with Google</u>", Html.FROM_HTML_MODE_LEGACY));
//
//        } else {
//            google_tv.setText(Html.fromHtml("Unable to login through phone?<br>" + "<u>Connect with Google</u>"));
//        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}