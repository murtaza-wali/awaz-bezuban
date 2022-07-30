
package com.festdepartment.awaaz_e_bezuban;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBarDrawerToggle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.drawerlayout.widget.DrawerLayout;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.Gravity;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
        import android.widget.Toast;
        import android.widget.Toolbar;

        import com.festdepartment.awaaz_e_bezuban.Adapter.SliderAdapter;
        import com.festdepartment.awaaz_e_bezuban.Common.Constantss;
        import com.festdepartment.awaaz_e_bezuban.Model.SliderItem;
        import com.festdepartment.awaaz_e_bezuban.Notification.Token;
        import com.google.android.material.navigation.NavigationView;
        import com.google.android.material.textfield.TextInputEditText;
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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle ab; //Toggle bar
    private Toolbar toolbar;

    private NavigationView navigationView;

    //    Drawer LayoutLayout


    //    Slider
    private SliderView sliderView;
    private SliderAdapter adapter;



    //    dash main btn
    private LinearLayout lost_btn, adoption_btn,tips_tricks_btn, emergency_btn,discussion_btn,event_btn;

    private Button logout_btn;
//    dash main btn


    //    Token DatabaseRef
    DatabaseReference tokenRef;
//    Token DatabaseRef

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        Notification Token
        tokenRef = FirebaseDatabase.getInstance().getReference().child("Tokens");
        UpdateToken(FirebaseInstanceId.getInstance().getToken());
//        Notification Token
        mainBtnInit();
        Slider();
        NavigationDrawer();


    }


    private void UpdateToken(String token){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        tokenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Token token1 = new Token(token);
                tokenRef.child(user.getUid()).setValue(token1);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }


    private void mainBtnInit() {
        lost_btn = findViewById(R.id.lost_dash_btn);
        lost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constantss.PET_TYPE = "All";
                Constantss.ADOPTION = "";
                startActivity(new Intent(MainActivity.this,ReportMain.class));
            }
        });

        adoption_btn = findViewById(R.id.adoption_btn);
        adoption_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constantss.ADOPTION = "Adopt";
                Constantss.PET_TYPE = "";
                startActivity(new Intent(MainActivity.this,ReportMain.class));
            }
        });


        tips_tricks_btn = findViewById(R.id.tips_trick_btn);
        tips_tricks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,TipsTricks.class));
            }
        });


        emergency_btn = findViewById(R.id.emergency_btn);
        emergency_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Emergency.class));
            }
        });


        discussion_btn = findViewById(R.id.discussion_btn);
        discussion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Discussion_Main.class));
            }
        });

        event_btn = findViewById(R.id.event_btn);
        event_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Events.class));
            }
        });

        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
                Toast.makeText(getApplicationContext(),"Logout Successfully!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void NavigationDrawer() {
        //###Navigation bar
        dl = findViewById(R.id.dl);
        ab = new ActionBarDrawerToggle(MainActivity.this,dl,R.string.Openn,R.string.Closee);
        ab.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(ab);
        ab.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        getSupportActionBar().setHomeButtonEnabled(true);
        //naVIGATION vIEW

        navigationView = findViewById(R.id.navbar);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.report_nav){

                    startActivity(new Intent(MainActivity.this,Report.class));
                }
                if (id == R.id.my_post_nav){
                    Toast.makeText(MainActivity.this, "Post", Toast.LENGTH_SHORT).show();

                }

                if (id == R.id.settings_nav){
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();

                }

                else if (id == R.id.share_nav){

                    Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();

                }else if (id == R.id.team_nav){

                    startActivity(new Intent(MainActivity.this,AwazTeam.class));
                }else if (id == R.id.my_chats_nav){

                    startActivity(new Intent(MainActivity.this,UserChats.class));
                }

                return true;
            }
        });
        //naVIGATION vIEW

    }

    private void Slider() {


        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapter(this);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation( IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation( SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor( Color.BLUE);
        sliderView.setIndicatorUnselectedColor(R.color.awaz_grey_font);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();


        sliderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i <= 4; i++) {
            SliderItem sliderItem = new SliderItem();
            switch (i){
                case 0:
                    sliderItem.setSlider_Img(R.drawable.slider_1);
                    break;
                case 1:
                    sliderItem.setSlider_Img(R.drawable.slider_2);
                    break;
                case 2:
                    sliderItem.setSlider_Img(R.drawable.team_awaaz);
                    break;
                case 3:
                    sliderItem.setSlider_Img(R.drawable.cat_awaz);
                    break;
                case 4:
                    sliderItem.setSlider_Img(R.drawable.slider_3);
                    break;

            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });
    }

    public void navigation_open(View view) {
        DrawerLayout navDrawer = findViewById(R.id.dl);
        // If the navigation drawer is not open then open it, if its already open then close it.
        if(!navDrawer.isDrawerOpen(Gravity.START)){
            navDrawer.openDrawer(Gravity.START);
        }
        else {
            navDrawer.closeDrawer(Gravity.END);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }

}