package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.festdepartment.awaaz_e_bezuban.Common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {

    private TextInputEditText txtPassword, txtEmail;

    private TextView mrecovery;
    private FirebaseAuth firebaseAuth;
    //Progress Dialogue
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtPassword = findViewById(R.id.password_login);

        txtEmail = findViewById(R.id.email_login);
        mrecovery = findViewById(R.id.recover);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        mrecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowRecoverPaswword();
            }
        });

    }

    private void ShowRecoverPaswword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        //setting linear layout
        LinearLayout linearLayout = new LinearLayout(this);
        //Views to set in dialog
        final EditText remail = new EditText(this);
        remail.setHint("Email");
        remail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        remail.setMinEms(10);


        linearLayout.addView(remail);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);
        //Buttons
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input Email
                if (isNetworkAvailable()) {

                    String email = remail.getText().toString().trim();
                    if (email == null || email.isEmpty()) {
                        Toast.makeText(Login.this, "enter email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    beginRecover(email);

                } else {
                    Toast.makeText(Login.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Cancel Buttons
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss Dialog
                dialog.dismiss();


            }
        });
        builder.create().show();

    }

    private void beginRecover(String email) {
        if (isNetworkAvailable()) {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Recovery has been Email Sent", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(Login.this, "Failed...", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //progressDialog.dismiss();
                    ///Show Proper Error

                    Toast.makeText(Login.this, "Not exist" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void signup_btn(View view) {
        startActivity(new Intent(Login.this,Signup.class));
    }

    public void login(View view) {
        if (isNetworkAvailable()) {

            final String memail = txtEmail.getText().toString().trim();
            final String mpaswword = txtPassword.getText().toString().trim();

            if (TextUtils.isEmpty(memail)) {
                Toast.makeText(Login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                txtEmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(mpaswword)) {
                Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                txtEmail.requestFocus();
                return;
            }
            if (mpaswword.length() < 6) {
                Toast.makeText(Login.this, "Password too short", Toast.LENGTH_SHORT).show();
                txtPassword.requestFocus();
                return;
            }


            //Login Dialogue Bar
            final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            View loader_view = getLayoutInflater().inflate(R.layout.lazyloader_layout, null);

            LazyLoader lazy_loader = loader_view.findViewById(R.id.loader_progress);
            TextView lazy_loader_txt = loader_view.findViewById(R.id.loader_text);

            lazy_loader_txt.setText("Signing in..");



            LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                    ContextCompat.getColor(this, R.color.loader_selected),
                    ContextCompat.getColor(this, R.color.loader_selected));
            loader.setAnimDuration(500);
            loader.setFirstDelayDuration(100);
            loader.setSecondDelayDuration(200);
            loader.setInterpolator(new LinearInterpolator());
            lazy_loader.addView(loader);
            builder.setView(loader_view);
            final AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();



            firebaseAuth.signInWithEmailAndPassword(memail, mpaswword)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {


                                // Sign in success, update UI with the signed-in user's information
                                Intent dashintent = new Intent( Login.this,MainActivity.class );
                                Common.currentUser = User.UNAUTHENTICATED;
                                startActivity( dashintent );
                                Toasty.success(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "Login Failed or Invalid User", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();


                                // ...
                            }

                        }
                    });


        }else {
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user !=null){
            startActivity(new Intent(Login.this,MainActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Login.this,Pre_signin.class));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}