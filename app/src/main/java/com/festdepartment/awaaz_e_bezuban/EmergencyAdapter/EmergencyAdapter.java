package com.festdepartment.awaaz_e_bezuban.EmergencyAdapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.festdepartment.awaaz_e_bezuban.ChatAdaper.ChatUserAdapter;
import com.festdepartment.awaaz_e_bezuban.Emergency;
import com.festdepartment.awaaz_e_bezuban.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.EmergencyViewHolder> {

    Context econtext;

    List<EmergencyModel> emergencyModelList;

    public EmergencyAdapter(Context econtext, List<EmergencyModel> emergencyModelList) {
        this.econtext = econtext;
        this.emergencyModelList = emergencyModelList;
    }

    @NonNull
    @NotNull
    @Override
    public EmergencyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

            View emergencyView = LayoutInflater.from(econtext).inflate(R.layout.emergency_number_items, parent, false);
            return new EmergencyViewHolder(emergencyView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EmergencyViewHolder holder, int position) {

        EmergencyModel model = emergencyModelList.get(position);

        holder.name.setText(model.getService_name());
        holder.number.setText(model.getService_number());
        if(model.getService_address().equals("")){
            holder.address_visibility.setVisibility(View.GONE);
        }else {
            holder.address.setText(model.getService_address());
        }





        holder.call_rescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View emergency_call_view = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.call_emergency_item, null);

                Button call_btn = emergency_call_view.findViewById(R.id.call_emergency_now_item);
                Button cancel_btn = emergency_call_view.findViewById(R.id.cancel_emergency_now_item);

                builder.setView(emergency_call_view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();


                call_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(econtext, Manifest.permission.CALL_PHONE) !=
                                PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) econtext, new String[]{Manifest.permission.CALL_PHONE}, 200);
                        } else {


                            String motorway_dial = "tel:" + model.getService_number();
                            econtext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(motorway_dial)));
                            Toast.makeText(econtext, "Calling!", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();

                        }
                    }
                });

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });








            }
        });
    }



    @Override
    public int getItemCount() {
        return emergencyModelList.size();
    }
    public class EmergencyViewHolder extends RecyclerView.ViewHolder {



        private TextView name, number, address;
        private LinearLayout call_rescue,address_visibility;

        public EmergencyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.rescue_name_item);
            number = itemView.findViewById(R.id.rescue_number_item);
            address = itemView.findViewById(R.id.rescue_address_item);
            call_rescue = itemView.findViewById(R.id.call_now_rescue_item);
            address_visibility = itemView.findViewById(R.id.address_visibility_item);


        }
    }
}
