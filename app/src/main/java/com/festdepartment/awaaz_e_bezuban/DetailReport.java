package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.festdepartment.awaaz_e_bezuban.Common.Constantss;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class DetailReport extends AppCompatActivity {
    private ImageView main_img;
    private TextView uname, date,lost_type, sex, age, postType, location, description;


    FirebaseUser user;
    private Button chat_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        user = FirebaseAuth.getInstance().getCurrentUser();
        topinit();
        chatDirect();


    }

    private void chatDirect() {

        chat_btn = findViewById(R.id.chat_direct_btn);

        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chat_btn.getText().equals("Chat Now")){
                    Constantss.CHAT = "Chat";
                    String uid = getIntent().getStringExtra("Uid");
                    Intent docIntent = new Intent(DetailReport.this, Chat.class);
                    docIntent.putExtra("uname", uname.getText());
                    docIntent.putExtra("uid", uid);
                    startActivity(docIntent);
                }else if(chat_btn.getText().equals("Delete Post")){
                    new AlertDialog.Builder(DetailReport.this)
                            .setMessage("Are you sure to delete this post?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String postId = getIntent().getStringExtra("postID");
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Report Posts");
                                    reference.child(postId).removeValue();
                                    startActivity(new Intent(DetailReport.this,ReportMain.class));
                                    Toast.makeText(DetailReport.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                }
                else {
                    Toast.makeText(DetailReport.this, "Working!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void topinit() {
        main_img = findViewById(R.id.main_detail_img);
        uname = findViewById(R.id.detail_uname_txt);
        date = findViewById(R.id.detail_date_txt);
        lost_type = findViewById(R.id.detail_losttype_txt);
        sex = findViewById(R.id.detail_sex_txt);
        age = findViewById(R.id.detail_age_txt);
        postType = findViewById(R.id.detail_postType_txt);
        location = findViewById(R.id.detail_location_txt);
        description = findViewById(R.id.detail_des_txt);




        String name = getIntent().getStringExtra("uname");
        String date_ = getIntent().getStringExtra("date");
        String lost_type_ = getIntent().getStringExtra("lostType");
        String gender = getIntent().getStringExtra("sex");
        String age_ = getIntent().getStringExtra("age");
        String post_type_ = getIntent().getStringExtra("postType");
        String location_ = getIntent().getStringExtra("location");
        String des = getIntent().getStringExtra("description");
        String img = getIntent().getStringExtra("img");




        uname.setText(name);
        date.setText(date_);
        lost_type.setText(lost_type_);
        sex.setText(gender);
        age.setText(age_);
        postType.setText(post_type_);
        location.setText(location_);
        description.setText(des);
//        Picasso.get().load(img).into(main_img);

        final AlertDialog.Builder builder = new AlertDialog.Builder(DetailReport.this);
        View loader_view = getLayoutInflater().inflate(R.layout.lazyloader_layout, null);

        LazyLoader lazy_loader = loader_view.findViewById(R.id.loader_progress);
        TextView lazy_loader_txt = loader_view.findViewById(R.id.loader_text);

        lazy_loader_txt.setText("loading..");

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

        Glide.with(this).load(img).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                alertDialog.dismiss();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                alertDialog.dismiss();
                return false;
            }
        }).into(main_img);

    }

    @Override
    protected void onStart() {
        super.onStart();

        String uid = getIntent().getStringExtra("Uid");
        if(user.getUid().equals(uid)){
            chat_btn.setText("Delete Post");
        }
    }
}