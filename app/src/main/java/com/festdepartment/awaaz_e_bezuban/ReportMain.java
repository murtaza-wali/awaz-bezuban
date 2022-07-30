package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.festdepartment.awaaz_e_bezuban.Adapter.ReportAdapter;
import com.festdepartment.awaaz_e_bezuban.Common.Constantss;
import com.festdepartment.awaaz_e_bezuban.Model.ReportModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReportMain extends AppCompatActivity {

//    Floating action

    private FloatingActionButton report_fab;
//    Floating action

    //Img top init
    private CircleImageView all_list_img, cat_list_img,dog_list_img;
    //Img top init



    //Adapter Class
    private RecyclerView report_recycler;
    ReportAdapter reportAdapter;

    private List<ReportModel> reportModelList = new ArrayList<>();

//    firebase
    private DatabaseReference reference;
    private FirebaseAuth auth;
//    firebase

    //Adapter Class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_main);

        reference = FirebaseDatabase.getInstance().getReference("Report Posts");
        auth = FirebaseAuth.getInstance();

        topinit();





        final AlertDialog.Builder builder = new AlertDialog.Builder(ReportMain.this);
        View loader_view = getLayoutInflater().inflate(R.layout.lazyloader_layout, null);

        LazyLoader lazy_loader = loader_view.findViewById(R.id.loader_progress);
        TextView lazy_loader_txt = loader_view.findViewById(R.id.loader_text);

        lazy_loader_txt.setText("loading..");

        LazyLoader loader = new LazyLoader(ReportMain.this, 30, 20, ContextCompat.getColor(ReportMain.this, R.color.loader_selected),
                ContextCompat.getColor(ReportMain.this, R.color.loader_selected),
                ContextCompat.getColor(ReportMain.this, R.color.loader_selected));
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

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                reportModelList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    ReportModel availableDoctors = snapshot1.getValue(ReportModel.class);


                    reportModelList.add(availableDoctors);


                }
//                Log.d("DATA",""+categoryModelList.size());

                setReportRecycler( reportModelList );
                alertDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




    }

    private void topinit() {



        report_fab = findViewById(R.id.report_screen_fab);
        report_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportMain.this,Report.class));
            }
        });


        CircleImageView back_btn = findViewById(R.id.reportmain_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportMain.this,Dashboard.class));
            }
        });
        all_list_img = findViewById(R.id.all_list_img);
        cat_list_img = findViewById(R.id.cat_list_img);
        dog_list_img = findViewById(R.id.dog_list_img);

        all_list_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                all_list_img.setCircleBackgroundColor(ContextCompat.getColor(ReportMain.this,R.color.awaz_color));
                dog_list_img.setCircleBackgroundColor(ContextCompat.getColor(ReportMain.this,R.color.white));
                cat_list_img.setCircleBackgroundColor(ContextCompat.getColor(ReportMain.this,R.color.white));

                all_list_img.setBorderColor(ContextCompat.getColor(ReportMain.this,R.color.awaz_color));
                cat_list_img.setBorderColor(Color.parseColor("#680909"));
                dog_list_img.setBorderColor(Color.parseColor("#ff9912"));

                final AlertDialog.Builder builder = new AlertDialog.Builder(ReportMain.this);
                View loader_view = getLayoutInflater().inflate(R.layout.lazyloader_layout, null);

                LazyLoader lazy_loader = loader_view.findViewById(R.id.loader_progress);
                TextView lazy_loader_txt = loader_view.findViewById(R.id.loader_text);

                lazy_loader_txt.setText("loading..");

                LazyLoader loader = new LazyLoader(ReportMain.this, 30, 20, ContextCompat.getColor(ReportMain.this, R.color.loader_selected),
                        ContextCompat.getColor(ReportMain.this, R.color.loader_selected),
                        ContextCompat.getColor(ReportMain.this, R.color.loader_selected));
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

                Constantss.PET_TYPE = "All";
                reference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        reportModelList.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                            ReportModel availableDoctors = snapshot1.getValue(ReportModel.class);


                            reportModelList.add(availableDoctors);


                        }
//                Log.d("DATA",""+categoryModelList.size());

                        setReportRecycler( reportModelList );
                        alertDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });


        cat_list_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportModelList.clear();
                cat_list_img.setCircleBackgroundColor(Color.parseColor("#ff9912"));
                dog_list_img.setCircleBackgroundColor(ContextCompat.getColor(ReportMain.this,R.color.white));
                all_list_img.setCircleBackgroundColor(ContextCompat.getColor(ReportMain.this,R.color.white));

                cat_list_img.setBorderColor(Color.parseColor("#ff9912"));
                all_list_img.setBorderColor(ContextCompat.getColor(ReportMain.this,R.color.awaz_color));
                dog_list_img.setBorderColor(Color.parseColor("#ff9912"));
                Constantss.PET_TYPE = "Cat";
                reference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        reportModelList.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                            ReportModel availableDoctors = snapshot1.getValue(ReportModel.class);


                            reportModelList.add(availableDoctors);


                        }
//                Log.d("DATA",""+categoryModelList.size());

                        setReportRecycler( reportModelList );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


            }

        });

        dog_list_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dog_list_img.setCircleBackgroundColor(Color.parseColor("#680909"));
                cat_list_img.setCircleBackgroundColor(ContextCompat.getColor(ReportMain.this,R.color.white));
                all_list_img.setCircleBackgroundColor(ContextCompat.getColor(ReportMain.this,R.color.white));

                dog_list_img.setBorderColor(Color.parseColor("#ff9912"));
                all_list_img.setBorderColor(ContextCompat.getColor(ReportMain.this,R.color.awaz_color));
                cat_list_img.setBorderColor(Color.parseColor("#680909"));

                Constantss.PET_TYPE = "Dog";
                reference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        reportModelList.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                            ReportModel availableDoctors = snapshot1.getValue(ReportModel.class);


                            reportModelList.add(availableDoctors);


                        }
//                Log.d("DATA",""+categoryModelList.size());

                        setReportRecycler( reportModelList );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });



    }

    private void setReportRecycler(List<ReportModel>reportModelList){

        report_recycler = findViewById( R.id.report_recycler );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this,RecyclerView.VERTICAL,false );


        report_recycler.setHasFixedSize(true);
        report_recycler.getRecycledViewPool().clear();
        reportAdapter = new ReportAdapter(this,reportModelList);

        report_recycler.setLayoutManager( layoutManager );
        report_recycler.setAdapter(reportAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Constantss.ADOPTION.equals("Adopt")){
            LinearLayout category_visibility = findViewById(R.id.category_visibility);
            TextView category_txt = findViewById(R.id.adopt_lost_found);
            category_txt.setText("Adoption");
            category_visibility.setVisibility(View.GONE);
        }
    }
}