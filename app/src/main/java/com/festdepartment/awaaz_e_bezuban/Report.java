package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.festdepartment.awaaz_e_bezuban.Model.ReportModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Report extends AppCompatActivity {
    //    back btn
    private CircleImageView back_btn;
//    back btn


    private TextInputEditText lost_des;
    private EditText location_edtxt;
    private ImageButton upload_image, take_camera;
    private ImageView take_image, image_1, image_2, image_3;
    private Button submit_btn;
    private Spinner type_spinner, age_spinner, gender_spinner,lost_type_spinner,pet_type_spinner;

    //    Storage wait task
    private StorageTask mstorageTask;
    //    Storage wait task

    //    Firebase
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageReference reference,reference2;
    private FirebaseAuth auth;
//    Firebase


    private static final int CAMERA_REQUEST = 1001;
    private static final int CAMERA_REQUEST_1 = 1002;
    private static final int CAMERA_REQUEST_2 = 1003;
    private static final int PERMISSION_CODE = 1000;

    private static final int GALLERY_REQUEST_2 = 1006;
    private static final int GALLERY_REQUEST_3 = 1005;
    private static final int GALLERY_REQUEST = 1004;
    private Uri imageUri, imageUri_2, imageUri_3;
    private Uri imageUriUpload, imageUriUpload_1, imageUriUpload_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        back_btn = findViewById(R.id.report_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Report.this, Dashboard.class));
            }
        });


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Report Posts");
        auth = FirebaseAuth.getInstance();
        init();
        takeImageOnclik();
        uploadImageOnclick();
        submitBtnonClick();


    }


    private void init() {

        image_1 = findViewById(R.id.image_1);
        image_2 = findViewById(R.id.image_2);
        image_3 = findViewById(R.id.image_3);


        lost_des = findViewById(R.id.lost_description);
        location_edtxt = findViewById(R.id.report_location);

        submit_btn = findViewById(R.id.lost_submit_btn);
        age_spinner = findViewById(R.id.age_spinner);
        gender_spinner = findViewById(R.id.spinner_gender);

        type_spinner = findViewById(R.id.type_spinner);
        pet_type_spinner = findViewById(R.id.pet_type_spinner);
        lost_type_spinner = findViewById(R.id.lost_type_spinner);


        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                    case 3:
                        
                    case 2:
                        lost_type_spinner.setVisibility(View.GONE);
                        break;
                    case 1:
                        lost_type_spinner.setVisibility(View.VISIBLE);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
//    init ends

    private void takeImageOnclik() {


    }

    private void uploadImageOnclick() {


        image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Login Dialogue Bar
                final AlertDialog.Builder builder = new AlertDialog.Builder(Report.this);
                View image_edit_view = getLayoutInflater().inflate(R.layout.image_pick_layout, null);

                Button upload_gallery = image_edit_view.findViewById(R.id.gallery_pick_dialog_btn);
                Button camera_upload = image_edit_view.findViewById(R.id.camera_pick_dialog_btn);
                Button delete_img = image_edit_view.findViewById(R.id.delete_img_dialog_btn);
                Button cancel_img = image_edit_view.findViewById(R.id.cancel_img_dialog_btn);


                builder.setView(image_edit_view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                camera_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                                    PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                        show popup
                                requestPermissions(permission, PERMISSION_CODE);
                                alertDialog.dismiss();
                            } else {
                                openCamera();
                                alertDialog.dismiss();

                            }

                        } else {
                            //system os < marshmallow
                            openCamera();
                            alertDialog.dismiss();
                        }
                    }
                });


                upload_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                        alertDialog.dismiss();

                    }
                });
                delete_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       File file = new File(String.valueOf(imageUriUpload));
                        if(file.exists())
                            file.delete();
                        image_1.setImageResource(R.drawable.add_img_icon);
                        alertDialog.dismiss();

                    }
                });
                cancel_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });
            }
        });

        image_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Report.this);
                View image_edit_view = getLayoutInflater().inflate(R.layout.image_pick_layout, null);

                Button upload_gallery = image_edit_view.findViewById(R.id.gallery_pick_dialog_btn);
                Button camera_upload = image_edit_view.findViewById(R.id.camera_pick_dialog_btn);
                Button delete_img = image_edit_view.findViewById(R.id.delete_img_dialog_btn);
                Button cancel_img = image_edit_view.findViewById(R.id.cancel_img_dialog_btn);


                builder.setView(image_edit_view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                camera_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCamera_1();
                        alertDialog.dismiss();
                    }
                });

                upload_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, GALLERY_REQUEST_2);
                        alertDialog.dismiss();
                    }
                });
                delete_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image_1.setImageResource(R.drawable.add_img_icon);
                        alertDialog.dismiss();

                    }
                });
                cancel_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });
            }
        });

        image_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Report.this);
                View image_edit_view = getLayoutInflater().inflate(R.layout.image_pick_layout, null);

                Button upload_gallery = image_edit_view.findViewById(R.id.gallery_pick_dialog_btn);
                Button camera_upload = image_edit_view.findViewById(R.id.camera_pick_dialog_btn);
                Button delete_img = image_edit_view.findViewById(R.id.delete_img_dialog_btn);
                Button cancel_img = image_edit_view.findViewById(R.id.cancel_img_dialog_btn);


                builder.setView(image_edit_view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                camera_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCamera_2();
                        alertDialog.dismiss();
                    }
                });

                upload_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, GALLERY_REQUEST_3);
                        alertDialog.dismiss();
                    }
                });
                delete_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image_1.setImageResource(R.drawable.add_img_icon);
                        alertDialog.dismiss();

                    }
                });
                cancel_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });
            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "New Picture");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void openCamera_1() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "New Picture");

        imageUri_2 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_2);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_1);
    }

    private void openCamera_2() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "New Picture");

        imageUri_3 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_3);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_2);
    }

    //    take image click end
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                openCamera();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
//    permission end

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//            set capture image to our image view
        image_1.setImageURI(imageUri);
        image_2.setImageURI(imageUri_2);
        image_3.setImageURI(imageUri_3);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            set capture image to our image view

            if (imageUriUpload != null) {
                //mimageview2.setImageURI(mimageUri);
                Picasso.get().load(imageUriUpload).into(image_1);
            }
        } else if (requestCode == CAMERA_REQUEST_1 && resultCode == Activity.RESULT_OK) {
//            set capture image to our image view

            if (imageUriUpload_1 != null) {
                //mimageview2.setImageURI(mimageUri);

                Picasso.get().load(imageUriUpload_1).into(image_2);
            }
        } else if (requestCode == CAMERA_REQUEST_2 && resultCode == Activity.RESULT_OK) {
//            set capture image to our image view

            if (imageUriUpload_2 != null) {
                //mimageview2.setImageURI(mimageUri);

                Picasso.get().load(imageUriUpload_2).into(image_3);
            }
        } else if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {


            try {
                imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image_1.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Report.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == GALLERY_REQUEST_2 && resultCode == Activity.RESULT_OK) {


            try {
                imageUri_2 = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri_2);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image_2.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Report.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == GALLERY_REQUEST_3 && resultCode == Activity.RESULT_OK) {


            try {
                imageUri_3 = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri_3);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image_3.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Report.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(Report.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }


    private void submitBtnonClick() {
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mstorageTask != null && mstorageTask.isInProgress()) {
                    Toast.makeText(Report.this, "upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    UploadData();
                }
            }
        });
    }

    private void UploadData() {
        final String description = lost_des.getText().toString().trim();
        final String location = location_edtxt.getText().toString().trim();


        if (imageUri == null && imageUriUpload == null) {
            Toast.makeText(Report.this, "upload image", Toast.LENGTH_SHORT).show();
            image_1.requestFocus();
            return;
        }
        final String Services = type_spinner.getSelectedItem().toString().trim();
        final String lost_type = lost_type_spinner.getSelectedItem().toString().trim();
        final String gender = gender_spinner.getSelectedItem().toString().trim();
        final String age = age_spinner.getSelectedItem().toString().trim();
        final String pet = pet_type_spinner.getSelectedItem().toString().trim();
        if (Services.equals("Select Report Type")) {
            Toast.makeText(this, "please select your service", Toast.LENGTH_SHORT).show();
            type_spinner.requestFocus();
            return;
        }
        if (age.equals("Age")) {
            Toast.makeText(this, "please select age", Toast.LENGTH_SHORT).show();
            age_spinner.requestFocus();
            return;
        }
        if (gender.equals("Gender")) {
            Toast.makeText(this, "please select gender", Toast.LENGTH_SHORT).show();
            gender_spinner.requestFocus();
            return;
        }
        if (lost_type.equals("Select Lost Type")) {
            Toast.makeText(this, "please select gender", Toast.LENGTH_SHORT).show();
            gender_spinner.requestFocus();
            return;
        }
        if (pet.equals("Select Pet Type Type")) {
            Toast.makeText(this, "please select gender", Toast.LENGTH_SHORT).show();
            gender_spinner.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(Report.this, "enter description", Toast.LENGTH_SHORT).show();
            lost_des.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(location)) {
            Toast.makeText(Report.this, "enter location", Toast.LENGTH_SHORT).show();
            location_edtxt.requestFocus();
            return;
        }


        //Upload Dialogue Bar
        final AlertDialog.Builder builder = new AlertDialog.Builder(Report.this);
        View loader_view = getLayoutInflater().inflate(R.layout.lazyloader_layout, null);

        LazyLoader lazy_loader = loader_view.findViewById(R.id.loader_progress);
        TextView lazy_loader_txt = loader_view.findViewById(R.id.loader_text);

        lazy_loader_txt.setText("uploading your data...");


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


        if (imageUri != null ) {
//
//            reference2 = storageReference.child("Report/" + auth.getUid() + "/" + System.currentTimeMillis() + "." + getFileExtension(imageUri_2));
//
//            reference2.putFile(imageUri_2);

            reference = storageReference.child("Report/" + auth.getUid() + "/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));

            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri ur = uriTask.getResult();
                    Toast.makeText(Report.this, "Taken 1", Toast.LENGTH_SHORT).show();
                    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

                    alertDialog.dismiss();
                    ReportModel reportModel = new ReportModel(auth.getCurrentUser().getDisplayName(), currentDateTimeString, auth.getUid().toString(), description, location, Services, gender, age, pet,lost_type, (ur.toString()));
                    String pID =  databaseReference.push().getKey();
                    reportModel.setPostID(pID);
                    databaseReference.child(pID).setValue(reportModel);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(Report.this, "Failed", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            });
        }
        else {
            Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        }

    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    public void onBackPressed() {
        Intent startMain = new Intent(Report.this, MainActivity.class);
        startActivity(startMain);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}