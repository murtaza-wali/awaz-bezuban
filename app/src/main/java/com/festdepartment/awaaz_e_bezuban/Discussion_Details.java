package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.festdepartment.awaaz_e_bezuban.DiscussionAdapter.Comment_Adapter;
import com.festdepartment.awaaz_e_bezuban.Model.Discussion_Comment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class Discussion_Details extends AppCompatActivity {


    private ImageView profileImg;
    private ImageButton add_comment_btn;
    private TextView detail_title,time_posted,posted_by,description_post;
    private EditText add_comment;
    private String Postkey,myuid;

    private FirebaseDatabase comment_firebaseDatabse;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private RecyclerView comment_recycler;
    Comment_Adapter commentAdapter;
    private List<Discussion_Comment> discussionCommentList;
    static String COMMENT_KEY = "Discussions Comment" ;
    static String COMMENT_USER_NAME = "UserRegister" ;
    ArrayList<String> userskeyArrayList;
    ArrayList<String> commentkeyArrayList;
    private ProgressBar add_comment_progress;

    private TextView posted_by_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_details);
        init();
        fetchData();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        myuid = firebaseUser.getUid();
        comment_firebaseDatabse = FirebaseDatabase.getInstance();


        iniRvComment();

        fetchData();
//        final LoggedInUser loggedInUser=new LoggedInUser(firebaseUser.getUid(),firebaseUser.getDisplayName());


        posted_by_txt = findViewById(R.id.posted_by);

//        Textwatcher
        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (add_comment.getText().toString().startsWith(" ")   ) {
                    add_comment.setText("");

                }
            }

        };
        add_comment.addTextChangedListener(watcher);
//        Textwatcher ends


    }

    private void init() {
        profileImg = findViewById(R.id.discussion_profile_img_detail);
        add_comment_btn = findViewById(R.id.image_button_add_coment);
        detail_title = findViewById(R.id.dicussion_detail_title);
        time_posted = findViewById(R.id.dicussion_detail_time);
        posted_by = findViewById(R.id.discussion_post_by);

        description_post = findViewById(R.id.detail_description);
        add_comment = findViewById(R.id.add_coment_edittxt);

        comment_recycler = findViewById(R.id.add_comment_recycler);

        add_comment_progress = findViewById(R.id.progressBar);
    }

    private void addCommentBtn(){
        add_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    firebaseUser = firebaseAuth.getCurrentUser();
                    if (add_comment.getText().toString().isEmpty()) {
                        Toasty.error(Discussion_Details.this, "please write something", Toasty.LENGTH_LONG).show();
                    } else {

                        final KProgressHUD kh = KProgressHUD.create(Discussion_Details.this)
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setCancellable(true)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
                        kh.setCancellable(false);



                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                final String ts = String.valueOf(System.currentTimeMillis());
                                final DatabaseReference commentReference = comment_firebaseDatabse.getReference(COMMENT_KEY).child(Postkey).child("Comment") ;
                                final String comment_content = add_comment.getText().toString().trim();
                                final String uname = firebaseUser.getDisplayName();
                                final String uid = firebaseUser.getUid();
                                DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
                                mUserDatabase.child("UserRegister").child(uid).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String gender = snapshot.child("gender").getValue(String.class);
                                        Discussion_Comment discussion_comment = new Discussion_Comment(comment_content, uid, uname, ts,gender);
                                        commentReference.child(ts).setValue(discussion_comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                kh.dismiss();

                                                showMessage("comment added");
                                                add_comment.setText("");
                                                add_comment_progress.setVisibility(View.INVISIBLE);
                                                add_comment_btn.setVisibility(View.VISIBLE);
                                            }

                                            private void showMessage(String message) {
                                                Toasty.success(Discussion_Details.this, message, Toasty.LENGTH_SHORT).show();
                                            }


                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                showMessageerror("comment failed" + e.getMessage());
                                                add_comment_progress.setVisibility(View.INVISIBLE);

                                            }

                                            private void showMessageerror(String message) {
                                                Toasty.warning(Discussion_Details.this, message, Toasty.LENGTH_SHORT).show();
                                            }

                                        });

                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        },1500);


                    }
                }else {
                    Toast.makeText(Discussion_Details.this, "No internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchData() {

        String postTitle = getIntent().getExtras().getString("title");
        detail_title.setText(postTitle);

        String postDes = getIntent().getExtras().getString("description");
        description_post.setText(postDes);

        String profileimg = getIntent().getExtras().getString("post_picture");
        Glide.with(this).load(profileimg).into(profileImg);

        String postTime = getIntent().getExtras().getString("timestamp");
        time_posted.setText(postTime);

        //get post id.
        Postkey = getIntent().getExtras().getString("postKey");
        String  date = timeStampToString(getIntent().getExtras().getLong("timeStamp"));
        time_posted.setText(date);

        String uname = getIntent().getExtras().getString("username");
        posted_by.setText(uname);
    }


    private String  timeStampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }

    private void iniRvComment() {
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        comment_recycler.setLayoutManager(layoutManager);
        comment_recycler.setHasFixedSize(true);

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        DatabaseReference commentRef = comment_firebaseDatabse.getReference("Discussions Comment").child(Postkey).child("Comment");
        Query query = commentRef.orderByKey().limitToLast(15);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                discussionCommentList = new ArrayList<>();
                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    Discussion_Comment comment = snapshot1.getValue(Discussion_Comment.class);
                    discussionCommentList.add(comment);

                }
                commentAdapter = new Comment_Adapter(getApplicationContext(),discussionCommentList,myuid,Postkey);
                comment_recycler.setAdapter(commentAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        addCommentBtn();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}