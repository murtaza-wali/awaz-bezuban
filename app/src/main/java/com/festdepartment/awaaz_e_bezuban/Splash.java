package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;
    //variables
//    Animation topanim,bottomanim;
    Animation topanim,bottomanim;
    private ProgressBar progressBar_Splash;
    private ImageView top_image;
    private TextView loading_txt;

    //    Lang
    private TextView slogan;

    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.activity_splash );



//        if( CommonUtils.isRooted(Splash_screen.this)){
//            System.exit(0);
//            Toasty.error(Splash_screen.this,"Sorry! Rooted OS won't work with Pink Pakistan.",Toasty.LENGTH_SHORT).show();
//        }


        //Animations
        topanim = AnimationUtils.loadAnimation( this,R.anim.top_animation );
//        bottomanim = AnimationUtils.loadAnimation( this,R.anim.bottom_animation );

        progressBar_Splash = findViewById(R.id.splash_progress);
        loading_txt = findViewById(R.id.splash_loading);

        slogan = findViewById(R.id.early_detection);


        progressBar_Splash = findViewById(R.id.splash_progress);

        Handler handler = new Handler();
        top_image = findViewById(R.id.splash_image);
        top_image.setAnimation(topanim);



        ProgressBarAnimation();



    }

    @Override
    protected void onStart() {
        super.onStart();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar_Splash.setMax(100);
                progressBar_Splash.setScaleY(3f);


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){
                    startActivity(new Intent(Splash.this,MainActivity.class));
                    finish();
                }
                startActivity(new Intent(Splash.this,Dashboard.class));
                finish();


            }
        },2000);




    }

    public void ProgressBarAnimation(){

//        ProgressBarAnimation anim = new ProgressBarAnimation(this,progressBar_Splash,loading_txt,0f,100f);
//        anim.setDuration(2000);
//        progressBar_Splash.setAnimation(anim);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}

