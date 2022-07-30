package com.festdepartment.awaaz_e_bezuban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class AwazTeam extends AppCompatActivity {

    private CircleImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awaz_team);

        back_btn = findViewById(R.id.team_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AwazTeam.this,MainActivity.class));
            }
        });
    }
}