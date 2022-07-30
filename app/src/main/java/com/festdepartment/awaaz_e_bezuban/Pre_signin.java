package com.festdepartment.awaaz_e_bezuban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pre_signin extends AppCompatActivity {
    private Button pre_sign_btn, emergency_pre_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_signin);
        pre_sign_btn = findViewById(R.id.pre_signin_btn);
        emergency_pre_sign = findViewById(R.id.emergency_pre_sign_in_btn);

        pre_sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pre_signin.this,Login.class));
                finish();
            }
        });

        emergency_pre_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pre_signin.this,Emergency.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(Pre_signin.this,MainActivity.class));
            finish();
        }
    }
}