package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.festdepartment.awaaz_e_bezuban.Adapter.SliderAdapter;
import com.festdepartment.awaaz_e_bezuban.Common.Constantss;
import com.festdepartment.awaaz_e_bezuban.Model.SliderItem;
import com.festdepartment.awaaz_e_bezuban.Notification.Token;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    private Button started_br;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Check if application is opened for the first time

        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        String firstTIme = preferences.getString("FirstTimeOpn","");

        if(firstTIme.equals("Yes")){
            //if application was opened for the first time
            startActivity(new Intent(Dashboard.this,Pre_signin.class));
            finish();
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeOpn","Yes");
            editor.apply();
        }



        started_br = findViewById(R.id.get_started_btn);
        started_br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,Pre_signin.class));
                finish();
            }
        });
    }


}
