package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.festdepartment.awaaz_e_bezuban.Model.ResgisteredUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import es.dmoral.toasty.Toasty;

public class Signup extends AppCompatActivity {

    private TextInputEditText txtfullname,txtemail,txtpassword,txtconfirmpassword,txtphone;



    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuthUserCollisionException firebaseAuthUserCollisionException;
    private ProgressDialog progressDialog;

    //loader
    //loader
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //        Initi
        txtfullname = findViewById(R.id.userName_signup);
        txtemail = findViewById(R.id.userEmail_signup);
        txtpassword = findViewById(R.id.userPass_signup);
        txtconfirmpassword = findViewById(R.id.userCPass_signup);
        txtphone = findViewById(R.id.userPhone_signup);
//        Initi ends
    }



    public void SignUpBtn(View view) {

        databaseReference = FirebaseDatabase.getInstance().getReference("UserRegister");
        firebaseAuth= FirebaseAuth.getInstance();

        if (isNetworkAvailable()){

            final String mfullName = txtfullname.getText().toString().trim();
            final String memail = txtemail.getText().toString().trim();

            final String mpaswword = txtpassword.getText().toString().trim();
            final String mcpassword = txtconfirmpassword.getText().toString().trim();
            final String mphone = txtphone.getText().toString().trim();


            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
            if (TextUtils.isEmpty(mfullName)) {
                Toasty.warning(Signup.this, "Please enter Full Name", Toast.LENGTH_SHORT).show();
                txtfullname.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(memail)) {
                Toasty.warning(Signup.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                txtemail.requestFocus();
                return;
            }


            if(TextUtils.isEmpty(mphone)) {
                Toasty.warning(Signup.this, "Please Enter Phone No", Toast.LENGTH_SHORT).show();
                txtphone.requestFocus();
            }

            if (mphone.length() == 11) {
            } else {
                Toasty.warning(Signup.this, "incorrect number", Toast.LENGTH_SHORT).show();
                txtphone.requestFocus();
                return;
            }


            if (TextUtils.isEmpty(mpaswword)) {
                Toasty.warning(Signup.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(mcpassword)) {
                    Toasty.warning(Signup.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    txtpassword.requestFocus();
                    txtconfirmpassword.requestFocus();
                    return;
                }
            }


            if (mpaswword.length() < 6) {
                txtpassword.requestFocus();
                return;
            }
            if (!mpaswword.equals(mcpassword)) {
                Toasty.warning(Signup.this, "Password not match", Toast.LENGTH_SHORT).show();
                txtconfirmpassword.requestFocus();
                return;
            }


            if (TextUtils.isEmpty(mphone)) {
                Toasty.warning(Signup.this, "Please Enter Phone#", Toast.LENGTH_SHORT).show();
                txtphone.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(memail).matches()  ) {
                txtemail.requestFocus();
                Toasty.warning(Signup.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                return;
            }




            final AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
            View loader_view = getLayoutInflater().inflate(R.layout.lazyloader_layout, null);

            LazyLoader lazy_loader = loader_view.findViewById(R.id.loader_progress);
            TextView lazy_loader_txt = loader_view.findViewById(R.id.loader_text);

            lazy_loader_txt.setText("please wait creating account..");

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




            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    firebaseAuth.createUserWithEmailAndPassword(memail, mcpassword)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

//                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//
//                                                Toast.makeText(Signup.this, "Verification Sent!", Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        });

                                        final FirebaseUser firebaseUser = task.getResult().getUser();


                                        Task<Void> updateTask = firebaseUser.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(mfullName).build());
                                        updateTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                                String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

                                                String id = firebaseAuth.getUid().toString();
                                                ResgisteredUser information = new ResgisteredUser(mfullName, memail, mphone,currentDateTimeString,id);

                                                FirebaseDatabase.getInstance().getReference("UserRegister")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        Toast.makeText(Signup.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(Signup.this,Login.class));
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull @NotNull Exception e) {
                                                        Toast.makeText(Signup.this, "Failed! try again", Toast.LENGTH_SHORT).show();
                                                    }
                                                });



//                                    Intent login = new Intent(Signup.this,SignupVerification.class);

                                                finish();
                                                alertDialog.dismiss();
                                            }
                                        });

                                    } else {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            alertDialog.dismiss();
                                            Toasty.error(Signup.this, "User with this email already exist.", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    // ...
                                }

                            });
                }
            },4500);

        } else {
            Toast.makeText(this, "Check your internet", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}