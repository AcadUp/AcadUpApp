package com.example.acadup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hbb20.CountryCodePicker;
import com.learn.wizdom.checkInternet.OfflineActivity;
import com.learn.wizdom.compliance.PrivacyPolicyActivity;
import com.learn.wizdom.compliance.TermsOfUseActivity;
import com.learn.wizdom.introslides.UserGenderActivity;
import com.learn.wizdom.referrals.Referral;
import com.learn.wizdom.subscribe.SubscriptionService;
import com.libizo.CustomEditText;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    ProgressDialog mLoginProgress;
    private LinearLayout mGoogleLoginRl;
    private static final int RC_SIGN_IN = 12;
    private GoogleApiClient mGoogleApiClient;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";
    private FirebaseAnalytics mFirebaseAnalytics;
    TextView privacy_policy_tv, terms_of_use_tv, google_tv;
    SubscriptionService subscriptionService;
    TextView or_tv;


    private CustomEditText editTextPhoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        subscriptionService = new SubscriptionService(this);

        initUI();

        editTextPhoneNumber.requestFocus();

        setTermsOfUseAndPrivacyPolicy();
        configureGoogleSignIn();

        setGoogleSignInButton();

    }

    private void setGoogleSignInButton() {

        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        if (tm != null) {
            String countryCodeValue = tm.getNetworkCountryIso();

            Log.d("countryCodeValue", countryCodeValue);
            if (countryCodeValue != null && countryCodeValue.equalsIgnoreCase("in")) {
                google_tv.setVisibility(View.VISIBLE);
                mGoogleLoginRl.setVisibility(View.INVISIBLE);
                or_tv.setVisibility(View.INVISIBLE);

            } else {
                google_tv.setVisibility(View.INVISIBLE);
                mGoogleLoginRl.setVisibility(View.VISIBLE);
                or_tv.setVisibility(View.VISIBLE);
            }

        }

    }

    private void setTermsOfUseAndPrivacyPolicy() {
        terms_of_use_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toTermsOfUse = new Intent(LoginActivity.this, TermsOfUseActivity.class);
                startActivity(toTermsOfUse);
            }
        });

        privacy_policy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent privacyPolicy = new Intent(LoginActivity.this, PrivacyPolicyActivity.class);//
                startActivity(privacyPolicy);

            }
        });
    }

    private void initUI() {
        mGoogleLoginRl = (LinearLayout) findViewById(R.id.google_rl);
        mLoginProgress = new ProgressDialog(this);
        privacy_policy_tv = (TextView) findViewById(R.id.privacy_policy_tv);
        terms_of_use_tv = (TextView) findViewById(R.id.terms_of_use_tv);
        google_tv = (TextView) findViewById(R.id.google_tv);
        or_tv = (TextView) findViewById(R.id.or_tv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            google_tv.setText(Html.fromHtml("Unable to login through phone?<br>" + "<u>Connect with Google</u>", Html.FROM_HTML_MODE_LEGACY));

        } else {
            google_tv.setText(Html.fromHtml("Unable to login through phone?<br>" + "<u>Connect with Google</u>"));
        }


        editTextPhoneNumber = findViewById(R.id.numberEditText);

        countryCodePicker = findViewById(R.id.countryCodePicker);
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.poppins_regular);
        countryCodePicker.setTypeFace(typeface);


    }


    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                Toast.makeText(LoginActivity.this, "Google Sign-In Failed", Toast.LENGTH_LONG).show();

            }
        })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        google_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT);
                signInWithGoogle();
            }
        });

        mGoogleLoginRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT);
                signInWithGoogle();
            }
        });


    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            mLoginProgress.setMessage("Starting Google Sign-In");
            mLoginProgress.setCancelable(false);
            mLoginProgress.show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign xyz failed", e);
                mLoginProgress.dismiss();
                // ...
            }
        }
        // This else is for facebook
        else {
//            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        User user = new User();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign xyz success, update UI with the signed-xyz user's information
                            Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();

                            final DocumentReference userRef = db.collection("Users").document(user.getUid());
                            if (isNetworkAvailable()) {
                                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                mLoginProgress.dismiss();
                                                User user = document.toObject(User.class);

                                                if ((user != null && user.getSubscription() != null && user.getSubscription()) ||
                                                        (user != null && user.getIn_app_purchase() != null && user.getIn_app_purchase()
                                                                && user.getIn_app_purchase_last_date() != null && Timestamp.now()
                                                                .toDate().before(user.getIn_app_purchase_last_date().toDate()))) {
                                                    Intent toMainActivity = new Intent(LoginActivity.this, MainActivity.class);//
                                                    toMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(toMainActivity);
                                                    finish();


                                                } else {
                                                    SubscriptionService subscriptionService = new SubscriptionService(getApplicationContext());
                                                    subscriptionService.goToSubscriptionActivityFirstTime();
                                                    finish();
                                                }

                                            } else {
                                                final String uid = user.getUid();
                                                String display_name = user.getDisplayName();
                                                String user_name;

                                                Uri image = user.getPhotoUrl();

                                                for (UserInfo userInfo : user.getProviderData()) {
                                                    if (display_name == null && userInfo.getDisplayName() != null) {
                                                        display_name = userInfo.getDisplayName();
                                                    }
                                                    if (image == null && userInfo.getPhotoUrl() != null) {
                                                        image = userInfo.getPhotoUrl();
                                                    }
                                                }
                                                String imageUri = null;
                                                String thumbImageUri = null;

                                                if (display_name != null) {
                                                    final User userObject = new User();
                                                    userObject.setName(display_name);


                                                    if (image != null) {
                                                        imageUri = user.getPhotoUrl().toString();
                                                        thumbImageUri = imageUri + "?width=100&height=100";
                                                        imageUri = imageUri + "?width=400&height=400";
                                                    } else {
                                                        imageUri = "default";
                                                    }

                                                    userObject.setImage(imageUri);
                                                    userObject.setThumb_image(thumbImageUri);
                                                    userObject.setJoined(Timestamp.now());
                                                    userObject.setId(user.getUid());


                                                    FirebaseInstanceId.getInstance().getInstanceId()
                                                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                                    if (task.isSuccessful()) {
                                                                        String deviceToken = task.getResult().getToken();
                                                                        userObject.setDevice_token(deviceToken);
                                                                    }
                                                                    userRef.set(userObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                            if (task.isSuccessful()) {

                                                                                if (getIntent().getStringExtra("isMarketeer") != null && getIntent().getStringExtra("marid") != null) {
                                                                                    if (getIntent().getStringExtra("isMarketeer").equals("true") && !getIntent().getStringExtra("marid").equals("")) {
                                                                                        Log.i("marketing Status", "marketin link found with marid");

                                                                                        marketingDeepLinkForReferral(userObject);

                                                                                    } else {
                                                                                        Log.i("Marketing  Status", "No referral link found or no UID");
                                                                                    }
                                                                                }
                                                                                if (getIntent().getStringExtra("isReferral") != null && getIntent().getStringExtra("uid") != null) {
                                                                                    if (getIntent().getStringExtra("isReferral").equals("true") && !getIntent().getStringExtra("uid").equals("")) {
                                                                                        Log.i("Referral Status", "Referral link found with UID");

                                                                                        handelDeepLinksForReferral();

                                                                                    } else {
                                                                                        Log.i("Referral Status", "No referral link found or no UID");
                                                                                    }
                                                                                }

                                                                                mLoginProgress.dismiss();
                                                                                Bundle bundle = new Bundle();
                                                                                bundle.putString(FirebaseAnalytics.Param.METHOD, "google");
//                                                                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN,bundle);

                                                                                Intent goToFirstPersonalizationSlide = new Intent(LoginActivity.this, UserGenderActivity.class);
                                                                                goToFirstPersonalizationSlide.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                                startActivity(goToFirstPersonalizationSlide);
                                                                                finish();

                                                                            } else {
                                                                                doWhenSignedInButDatabaseNotFilledWithDetails(user);
                                                                            }

                                                                        }
                                                                    });
                                                                }
                                                            });
                                                } else {
                                                    doWhenSignedInButDatabaseNotFilledWithDetails(user);
                                                }
                                                Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            doWhenSignedInButDatabaseNotFilledWithDetails(user);
                                            mLoginProgress.dismiss();
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });
                            } else {
                                startActivity(new Intent(LoginActivity.this, OfflineActivity.class));

                            }
                        } else {
                            // If sign xyz fails, display a message to the user.
                            mLoginProgress.dismiss();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void doWhenSignedInButDatabaseNotFilledWithDetails(FirebaseUser user) {
        if (user.getUid() != null) {
            db.collection("Users").document(user.getUid()).delete();
        }
        FirebaseAuth.getInstance().signOut();
        mLoginProgress.dismiss();
    }
    //-------------------Marketing Deep Referral--------------------------------------
    private void marketingDeepLinkForReferral(User userObject) {
        String marid = getIntent().getStringExtra("marid");
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("Affiliate_Marketing").document(marid).collection("data").document("login").collection("users")
                .document(userObject.getId()).set(userObject,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                HashMap<String,Object> map=new HashMap<>();
                map.put("number",FieldValue.increment(1));
                db.collection("Affiliate_Marketing").document(marid).collection("data")
                        .document("login").set(map,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("increment loign value ", "onsSuccess: SUccessfullincreent  in login ");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.i("increment won' work ", "onFailure: filaed to execute the taks");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.i("Marketindeeplink", "onFailure: filaed to execute the taks");
            }
        });

    }
//-------------------Marketing Deep Referral--------------------------------------
    private void handelDeepLinksForReferral() {

        String uid = getIntent().getStringExtra("uid");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Referral referral = new Referral();
        referral.setReferFrom(uid);
        referral.setReferTo(FirebaseAuth.getInstance().getUid());


        db.collection("Referrals").document(uid).collection("refers").document(FirebaseAuth.getInstance().getUid()).set(referral).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("referrals", "Successful");

                if (isNetworkAvailable()) {
                    db.collection("Referrals").document(uid).collection("refers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Log.i("Referrals", String.valueOf(task.getResult().size()));
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("countOfCoupons", FieldValue.increment(1));
                                hashMap.put("id", uid);

                                db.collection("Referrals").document(uid).set(hashMap, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.e("Referrals", "Coupon count set successfully!");
                                        } else {
                                            Log.e("Referrals", "Failed to set coupon count");
                                        }
                                    }
                                });
                            } else {
                                Log.e("Referrals", "Task unsuccessful");
                            }
                        }
                    });
                } else {
                    startActivity(new Intent(LoginActivity.this, OfflineActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("failure", e.getMessage());
            }
        });
    }


    // Mobile no Login!
    public void sendOTP(View view) {

        Log.i("check", "send otp clicked");
        String phoneNumber1 = editTextPhoneNumber.getText().toString();

        if (validatePhoneNumber(phoneNumber1)) {
            Log.i("Selected country code", countryCodePicker.getSelectedCountryCode());
            String phoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + editTextPhoneNumber.getText().toString();
            Log.e("Working", "" + phoneNumber);
            Log.i("check", "Intent created");

            Intent intent = new Intent(this, OtpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            if (getIntent().getStringExtra("isReferral") != null && getIntent().getStringExtra("uid") != null) {
                intent.putExtra("isReferral", getIntent().getStringExtra("isReferral"));
                intent.putExtra("uid", getIntent().getStringExtra("uid"));
            }
            if(getIntent().getStringExtra("isMarketeer") != null && getIntent().getStringExtra("marid") != null) {
                intent.putExtra("isMarketeer", getIntent().getStringExtra("isMarketeer"));
                intent.putExtra("marid", getIntent().getStringExtra("marid"));
            }

            intent.putExtra("phoneNumber", phoneNumber);
            startActivity(intent);
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.equals("")) {
            editTextPhoneNumber.setError("Field can not be Empty!");
            editTextPhoneNumber.requestFocus();
            return false;
        } else if (phoneNumber.length() < 10 || phoneNumber.length() > 10) {
            editTextPhoneNumber.setError("Number should be 10 digit!");
            editTextPhoneNumber.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}