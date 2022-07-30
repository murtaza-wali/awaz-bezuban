package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.anstrontechnologies.corehelper.AnstronCoreHelper;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.festdepartment.awaaz_e_bezuban.DiscussionAdapter.DiscussionAdapter;
import com.festdepartment.awaaz_e_bezuban.Model.Discussion_Post_Model;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteck.silicompressorr.FileUtils;
import com.iceteck.silicompressorr.SiliCompressor;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Discussion_Main extends AppCompatActivity {


//    init popup
    FloatingActionButton floatingActionButton;
    ImageView popupuserImage,popupPostImage, popupAddbtn;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    TextView popupTitle, popupDescription;
    ProgressBar popupClickProgress;
    Dialog popupAddpost;

    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int PReqCode = 2;
    private Uri mimageUri;

//    init popup



    //    Retrive Data post
    private RecyclerView row_post_recycler;
    private  DiscussionAdapter discussionAdapter;
    private  List<Discussion_Post_Model> discussion_post_models;

    private  FirebaseDatabase postFirebaseDatabase;
    private  DatabaseReference postdatabaseReference,usernameDatabase;
    private  StorageReference storageReference;
    private  StorageReference reference;
    private  AnstronCoreHelper coreHelper;
    private  FirebaseUser firebaseUser;

    String myUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_main);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        coreHelper = new AnstronCoreHelper(this);
        myUid = currentUser.getUid();


        initpopup();
        setUpPopUpImageClick();
        retreiverowPost();

    }


    private void initpopup() {
        popupAddpost = new Dialog(this);
        popupAddpost.setContentView(R.layout.discussion_add_popup);
        popupAddpost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupAddpost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popupAddpost.getWindow().getAttributes().gravity = Gravity.TOP;


        //  ini popup widgets
        popupuserImage = popupAddpost.findViewById(R.id.popup_user);
        popupPostImage = popupAddpost.findViewById(R.id.popup_image_post);
        popupAddbtn = popupAddpost.findViewById(R.id.popup_add);
        popupTitle = popupAddpost.findViewById(R.id.popup_title);
        popupDescription = popupAddpost.findViewById(R.id.popup_description);

        popupClickProgress = popupAddpost.findViewById(R.id.discussion_popup_progressbar);


        floatingActionButton = findViewById(R.id.discussion_screen_fab);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupAddpost.show();
            }
        });


        //        addpost click Listener
        popupAddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isNetworkAvailable()) {
                        popupAddbtn.setVisibility(View.INVISIBLE);
                        popupClickProgress.setVisibility(View.VISIBLE);

                    if(mimageUri !=null && !popupTitle.getText().toString().isEmpty() && !popupDescription.getText().toString().isEmpty()) {
//                        final File file = new File(SiliCompressor.with(Discussion_Main.this).
//                                compress(FileUtils.getPath(Discussion_Main.this, mimageUri), new File(Discussion_Main.this.getCacheDir(), "temp")));
//                        Uri uri = Uri.fromFile(file);

//                    everthing is okay no empty or null value
                            reference = storageReference.child("Discussion Title Image").child("Discussion_image_" + mAuth.getUid().toString() + "_" + System.currentTimeMillis() + ".png");

                            reference.putFile(mimageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            popupAddpost.setCancelable(false);
                                            String imageDownloadLink = uri.toString();
                                            String username = currentUser.getDisplayName();
                                            //create post object
                                            Discussion_Post_Model post = new Discussion_Post_Model(popupTitle.getText().toString(),
                                                    popupDescription.getText().toString(), imageDownloadLink, currentUser.getUid(), username);
                                            /*currentUser.getPhotoUrl().toString()*/
                                            popupTitle.setText("");
                                            popupDescription.setText("");
                                            popupPostImage.setImageResource(R.drawable.add_img_icon);


                                            addPost(post);
//                                            file.delete();




                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            showMessage(e.getMessage());
                                            popupClickProgress.setVisibility(View.INVISIBLE);
                                            popupAddbtn.setVisibility(View.VISIBLE);
//                                            file.delete();
                                        }


                                    });

                                }


                            });

                        } else {
                            showMessageerror("Please enter all fields and choose Post Image");
                            popupTitle.requestFocus();
                            popupDescription.requestFocus();
                            popupPostImage.requestFocus();
                            popupAddbtn.setVisibility(View.VISIBLE);
                            popupClickProgress.setVisibility(View.INVISIBLE);


                        }


                } else {
                    Toast.makeText(Discussion_Main.this, "No internet", Toast.LENGTH_SHORT).show();
                }
            }

            private void addPost(Discussion_Post_Model post) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Discussions").push();

//               post unique id
                String key = myRef.getKey();
                post.setPostKey(key);
//               post unique id

                myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("Post added successfully");
                        popupClickProgress.setVisibility(View.INVISIBLE);
                        popupAddbtn.setVisibility(View.VISIBLE);
                        popupAddpost.dismiss();
                    }
                });

            }

            private void showMessage(String message) {
                Toasty.success(Discussion_Main.this, message, Toasty.LENGTH_LONG).show();
            }

            private void showMessageerror(String message) {
                Toasty.warning(Discussion_Main.this, message, Toasty.LENGTH_LONG).show();

            }
        });

//        addpost click Listener




    }

    private void setUpPopUpImageClick() {

        popupPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              here when we take it to gallery to get image
                checkRequestforPermission();


            }

            private void checkRequestforPermission() {
                if(ContextCompat.checkSelfPermission(Discussion_Main.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(Discussion_Main.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                        Toasty.warning(Discussion_Main.this, "Please accept required permission", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ActivityCompat.requestPermissions(Discussion_Main.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);

                    }

                }else {
                    openGallery();
                }

            }
        });
    }





    private void openGallery() {
        startActivityForResult(Intent.createChooser(new Intent()
                        .setAction(Intent.ACTION_GET_CONTENT)
                        .setType("image/*"),"Select image")
                ,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data) {
        super.onActivityResult(requestCode , resultCode , data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            mimageUri = data.getData();
            if (mimageUri != null) {
                Picasso.get().load(mimageUri).into(popupPostImage);
            }
        }
    }


    private void retreiverowPost() {

        row_post_recycler = findViewById(R.id.row_post_recycler);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        row_post_recycler.setLayoutManager(layoutManager);
        row_post_recycler.setHasFixedSize(true);
        postFirebaseDatabase = FirebaseDatabase.getInstance();
        postdatabaseReference = postFirebaseDatabase.getReference("Discussions");
        usernameDatabase = postFirebaseDatabase.getReference("UserRegister");

    }

    @Override
    protected void onStart() {
        super.onStart();
        //        get post list from the database.
        postdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                discussion_post_models = new ArrayList<>();
                for(DataSnapshot postsnap: snapshot.getChildren()){
                    Discussion_Post_Model post = postsnap.getValue(Discussion_Post_Model.class);
                    discussion_post_models.add(post);

                }
                discussionAdapter = new DiscussionAdapter(getApplicationContext(),discussion_post_models,myUid);
                row_post_recycler.setAdapter(discussionAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}