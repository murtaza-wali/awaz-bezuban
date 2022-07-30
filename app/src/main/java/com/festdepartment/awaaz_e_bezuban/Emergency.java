package com.festdepartment.awaaz_e_bezuban;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.festdepartment.awaaz_e_bezuban.Adapter.ReportAdapter;
import com.festdepartment.awaaz_e_bezuban.Common.Constantss;
import com.festdepartment.awaaz_e_bezuban.EmergencyAdapter.EmergencyAdapter;
import com.festdepartment.awaaz_e_bezuban.EmergencyAdapter.EmergencyModel;
import com.festdepartment.awaaz_e_bezuban.Model.ReportModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Emergency extends AppCompatActivity {



    private RecyclerView emergency_rv;



    private EmergencyAdapter emergencyAdapter;
    private List<EmergencyModel> emergencyModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);


        final AlertDialog.Builder builder = new AlertDialog.Builder(Emergency.this);
        View suggestion_layout_view = getLayoutInflater().inflate(R.layout.custom_emergency_layout, null);

        Button rescue_btn = suggestion_layout_view.findViewById(R.id.rescue_services_btn);
        Button clinic_btn = suggestion_layout_view.findViewById(R.id.clinic_btn);

        builder.setView(suggestion_layout_view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();



        emergencyModelList = new ArrayList<>();
        rescue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constantss.EMERGENCY_TYPE = "Rescue";
                emergencyModelList.add(new EmergencyModel("ACF Animal Rescue","","03345665000"));
                emergencyModelList.add(new EmergencyModel("IPS Animal Rescue","","03332302043"));
                emergencyModelList.add(new EmergencyModel("Karachi Animal Welfare Society - KAWS","","03213832583"));
                emergencyModelList.add(new EmergencyModel("Creatures Comfort – Sascap Rays of Light Animal Welfare","","03323680625"));
                emergencyModelList.add(new EmergencyModel("Edhi Animal Shelter","","02132413232"));
                emergencyModelList.add(new EmergencyModel("Hope for Hopeless","","03422252400"));
                setEmergencyRecycler(emergencyModelList);

                alertDialog.dismiss();
            }
        });

        clinic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constantss.EMERGENCY_TYPE = "Clinic";
                emergencyModelList.add(new EmergencyModel("Defence Veterinary Hospital","Sailor Street DHA Phase 2, PNS Shifa, near Masjid AlKaremi, Karachi, 75500","030253622398"));
                emergencyModelList.add(new EmergencyModel("Dr Asudani Mobile Vet Clinic","17, Bath Island, Karachi, Karachi City, Sindh 75500","03332734108"));
                emergencyModelList.add(new EmergencyModel("Pirzada Pets Clinic","Shop No 1 & 2, 38 C Stadium Lane 4, Phase V Stadium Commercial Area Defence V Defence Housing Authority, Karachi","02135852988 "));
                emergencyModelList.add(new EmergencyModel("Creatures Comfort – Sascap Rays of Light Animal Welfare","Shop No 1 & 2, 38 C Stadium Lane 4, Phase V Stadium Commercial Area Defence V Defence Housing Authority, Karachi","03323680625"));
                emergencyModelList.add(new EmergencyModel("SurgVet Pet Hospital - Veterinary Clinic & Home Care","Shop No. 1, Plot، 18-C Lane 6, Bukhari Commercial Area Phase 6 Defence Housing Authority, Karachi, Karachi City, Sindh 75560","03329602723"));
                emergencyModelList.add(new EmergencyModel("SurgVet Pet Hospital - Veterinary Clinic & Home Care","Shop No. 1, Plot، 18-C Lane 6, Bukhari Commercial Area Phase 6 Defence Housing Authority, Karachi, Karachi City, Sindh 75560","03452228426"));
                emergencyModelList.add(new EmergencyModel("RPK Critter Care & Animal Hospital ","Plot No. 7-C, Lane-11, Nishat Commercial, Opp،, Nishat Commercial Area Phase-6 Ext Defence Housing Authority, Karachi","03226160876"));
                emergencyModelList.add(new EmergencyModel("Kathio Animal Hospital","21 C Lane 2, Komal Arcade Khayaba-e-Rahat","03453776359"));
                emergencyModelList.add(new EmergencyModel("Dr. Javed's Veterinary Clinic & Sugery Centrelinic","Badar Comm. Street 12, Phase V Badar Commercial Area Defence V Defence Housing Authority, Karachi, Karachi City, Sindh 75500","03178221223"));
                emergencyModelList.add(new EmergencyModel(" Healthy Tails Animal Hospital","33 C / 2, Lane 8, D.H.A Phase 6 Bukhari Commercial Area","03486815898"));
                emergencyModelList.add(new EmergencyModel("Defence Pet Hospital Karachi","Lane 4, D.H.A Phase 6 Bukhari Commercial Area Phase 6 Defence Housing Authority, Karachi, Karachi City, Sindh 75500","03348484893"));
                emergencyModelList.add(new EmergencyModel("Hats Vets ","Ittehad Commercial area, lane#4, building #8c, Ittehad Commercial Area Phase 6 Defence Housing  Karachi,Sindh 75500","02134521859"));
                emergencyModelList.add(new EmergencyModel("Dr. Pirzada Vet Clinic (Animal Centre)","Shop No.22, Royal Appartment K.D.A. Scheme No.1 Mudassir Park Rd, KDA Scheme #1 KDA Scheme 1, Karachi, Karachi City, Sindh","03322394822"));
                emergencyModelList.add(new EmergencyModel("Alpha Pets Clinic","Plot 61 L, Pakistan Employees Co-Operative Housing Society Block 6 PECHS, Karachi, Karachi City, Sindh","03333694230"));
                emergencyModelList.add(new EmergencyModel("Vet Care Clinic Dr. Farid","A-547, Shadman Town, Sector 14 B Sakhi Hasan, Karachi, Karachi City, Sindh","02135851296"));
                emergencyModelList.add(new EmergencyModel("Animal Care Centre","C5E Nishat، 2 Khayaban-eNishat, Phase 6 Defence Housing Authority, Karachi, Karachi City, Sindh 75500","03242627051"));
                emergencyModelList.add(new EmergencyModel("Animal Rescue and Transportation Service (ARTS) ","Phase V، Phase V Badar Commercial Area Defence V Defence Housing Authority Karachi, Sindh 75500","03377622142"));
                emergencyModelList.add(new EmergencyModel("Dr. Tasneem Ahmed Pet Care Services","Sahba Akhtar Rd, Block 13D-1 Block 13 D 1 Gulshan-eIqbal, Karachi, Karachi City, Sindh","03322446168"));
                emergencyModelList.add(new EmergencyModel("Animal Care Clinic","Rashid Minhas Rd Service Ln, Block 10-A","03062983391"));
                emergencyModelList.add(new EmergencyModel("Rajput Vet Clinic","Korangi no 3, near essa lab PSO petrol pump, Karachi","03333694230"));
                emergencyModelList.add(new EmergencyModel("Stress Free Vet Care ","Shop A, 14 Vet Care Shadman No. 2, near Lassani","03422252400"));
                emergencyModelList.add(new EmergencyModel("All Animal Health Care Hospital","B-42 Nawab Health Market opposite Nadra Office، Gulshan e Hadeed Phase 1 Karachi, Sindh 75010, Pakistan","03460227788"));
                emergencyModelList.add(new EmergencyModel("Animals and Birds Care Centre","Shop No 2 c plot Animals # 781 behind Tariq road","03352124033"));
                emergencyModelList.add(new EmergencyModel("First Aid Animal Hospital","Main Khayaban- e- Badar Commercial، 29th St، Badar Commercial Area Defence V Defence Housing Authority, Karachi,Sindh 7","03422252400"));
                setEmergencyRecycler(emergencyModelList);
                alertDialog.dismiss();


            }
        });
    }

    private void setEmergencyRecycler(List<EmergencyModel>emergencyModelList){

        emergency_rv = findViewById( R.id.emergency_rv );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this,RecyclerView.VERTICAL,false );


        emergency_rv.setHasFixedSize(true);
        emergency_rv.getRecycledViewPool().clear();
        emergencyAdapter = new EmergencyAdapter(this,emergencyModelList);

        emergency_rv.setLayoutManager( layoutManager );
        emergency_rv.setAdapter(emergencyAdapter);
    }

    public void emergency_back(View view) {
        startActivity(new Intent(Emergency.this,Dashboard.class));
    }
}