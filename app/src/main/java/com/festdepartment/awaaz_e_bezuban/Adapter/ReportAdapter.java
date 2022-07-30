package com.festdepartment.awaaz_e_bezuban.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.festdepartment.awaaz_e_bezuban.Common.Constantss;
import com.festdepartment.awaaz_e_bezuban.DetailReport;
import com.festdepartment.awaaz_e_bezuban.Model.ReportModel;
import com.festdepartment.awaaz_e_bezuban.R;
import com.festdepartment.awaaz_e_bezuban.Report;
import com.festdepartment.awaaz_e_bezuban.ReportMain;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReportAdapter  extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    Context reportContext;
    private List<ReportModel>  reportModelList;


    public ReportAdapter(Context reportContext, List<ReportModel> reportModelList) {
        this.reportContext = reportContext;
        this.reportModelList = reportModelList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View report_view = LayoutInflater.from(reportContext).inflate(R.layout.report_list_item,parent,false);
        return new ReportViewHolder(report_view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {




        if(Constantss.PET_TYPE.equals("All")){
            Glide.with(reportContext).load(reportModelList.get(position).getReportImg()).apply(RequestOptions.circleCropTransform()).into(holder.report_img);

            holder.uname_txt.setText(reportModelList.get(position).getUserName());
            holder.postDes_txt.setText(reportModelList.get(position).getPostDes());
            holder.postLocation_txt.setText(reportModelList.get(position).getLocation());
            holder.report_type_txt.setText(reportModelList.get(position).getPostType());
            holder.post_time.setText(reportModelList.get(position).getPost_time());
            if(reportModelList.get(position).getLocation().equals("")){
                holder.location_icon.setVisibility(View.GONE);
            }else {
                holder.location_icon.setImageResource(R.drawable.ic_baseline_location_on_24);
            }

            if(reportModelList.get(position).getGender().equals("Male")){
                holder.pet_gender_img.setImageResource(R.drawable.ic_male);

            }else if(reportModelList.get(position).getGender().equals("Female")) {

                holder.pet_gender_img.setImageResource(R.drawable.ic_female);

            }else {
                holder.pet_gender_img.setVisibility(View.INVISIBLE);
            }

            holder.report_btn_ful.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent docIntent = new Intent(reportContext, DetailReport.class);
                    docIntent.putExtra("uname", reportModelList.get(position).getUserName());
                    docIntent.putExtra("date", reportModelList.get(position).getPost_time());
                    docIntent.putExtra("lostType", reportModelList.get(position).getLostType());
                    docIntent.putExtra("sex", reportModelList.get(position).getGender());
                    docIntent.putExtra("age", reportModelList.get(position).getAge());
                    docIntent.putExtra("postType", reportModelList.get(position).getPostType());
                    docIntent.putExtra("location", reportModelList.get(position).getLocation());
                    docIntent.putExtra("description", reportModelList.get(position).getPostDes());
                    docIntent.putExtra("img", reportModelList.get(position).getReportImg());
                    docIntent.putExtra("Uid", reportModelList.get(position).getUserID());
                    docIntent.putExtra("postID", reportModelList.get(position).getPostID());
                    reportContext.startActivity(docIntent);
                    return;
                }
            });

            if(reportModelList.get(position).getPostType().equals("Lost")){
                holder.report_lost_type_txt.setVisibility(View.VISIBLE);
                holder.report_lost_type_txt.setText(reportModelList.get(position).getLostType());
            }else {
                holder.report_lost_type_txt.setVisibility(View.GONE);
            }
        }
        else if(reportModelList.get(position).getPetType().equals(Constantss.PET_TYPE)){
            Glide.with(reportContext).load(reportModelList.get(position).getReportImg()).apply(RequestOptions.circleCropTransform()).into(holder.report_img);

            holder.uname_txt.setText(reportModelList.get(position).getUserName());
            holder.postDes_txt.setText(reportModelList.get(position).getPostDes());
            holder.postLocation_txt.setText(reportModelList.get(position).getLocation());
            holder.report_type_txt.setText(reportModelList.get(position).getPostType());
            holder.post_time.setText(reportModelList.get(position).getPost_time());
            if(reportModelList.get(position).getLocation().equals("")){
                holder.location_icon.setVisibility(View.GONE);
            }else {
                holder.location_icon.setImageResource(R.drawable.ic_baseline_location_on_24);
            }

            if(reportModelList.get(position).getGender().equals("Male")){
                holder.pet_gender_img.setImageResource(R.drawable.ic_male);

            }else if(reportModelList.get(position).getGender().equals("Female")) {

                holder.pet_gender_img.setImageResource(R.drawable.ic_female);

            }else {
                holder.pet_gender_img.setVisibility(View.INVISIBLE);
            }

            holder.report_btn_ful.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent docIntent = new Intent(reportContext, DetailReport.class);
                    docIntent.putExtra("uname", reportModelList.get(position).getUserName());
                    docIntent.putExtra("date", reportModelList.get(position).getPost_time());
                    docIntent.putExtra("lostType", reportModelList.get(position).getLostType());
                    docIntent.putExtra("sex", reportModelList.get(position).getGender());
                    docIntent.putExtra("age", reportModelList.get(position).getAge());
                    docIntent.putExtra("postType", reportModelList.get(position).getPostType());
                    docIntent.putExtra("location", reportModelList.get(position).getLocation());
                    docIntent.putExtra("description", reportModelList.get(position).getPostDes());
                    docIntent.putExtra("img", reportModelList.get(position).getReportImg());
                    reportContext.startActivity(docIntent);
                    return;
                }
            });

            if(reportModelList.get(position).getPostType().equals("Lost")){
                holder.report_lost_type_txt.setVisibility(View.VISIBLE);
                holder.report_lost_type_txt.setText(reportModelList.get(position).getLostType());
            }else {
                holder.report_lost_type_txt.setVisibility(View.GONE);
            }
        }
         else if(reportModelList.get(position).getPostType().equals(Constantss.ADOPTION)){
            Glide.with(reportContext).load(reportModelList.get(position).getReportImg()).apply(RequestOptions.circleCropTransform()).into(holder.report_img);

            holder.uname_txt.setText(reportModelList.get(position).getUserName());
            holder.postDes_txt.setText(reportModelList.get(position).getPostDes());
            holder.postLocation_txt.setText(reportModelList.get(position).getLocation());
            holder.report_type_txt.setText(reportModelList.get(position).getPostType());
            holder.post_time.setText(reportModelList.get(position).getPost_time());
            if(reportModelList.get(position).getLocation().equals("")){
                holder.location_icon.setVisibility(View.GONE);
            }else {
                holder.location_icon.setImageResource(R.drawable.ic_baseline_location_on_24);
            }

            if(reportModelList.get(position).getGender().equals("Male")){
                holder.pet_gender_img.setImageResource(R.drawable.ic_male);

            }else if(reportModelList.get(position).getGender().equals("Female")) {

                holder.pet_gender_img.setImageResource(R.drawable.ic_female);

            }else {
                holder.pet_gender_img.setVisibility(View.INVISIBLE);
            }

            holder.report_btn_ful.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent docIntent = new Intent(reportContext, DetailReport.class);
                    docIntent.putExtra("uname", reportModelList.get(position).getUserName());
                    docIntent.putExtra("date", reportModelList.get(position).getPost_time());
                    docIntent.putExtra("lostType", reportModelList.get(position).getLostType());
                    docIntent.putExtra("sex", reportModelList.get(position).getGender());
                    docIntent.putExtra("age", reportModelList.get(position).getAge());
                    docIntent.putExtra("postType", reportModelList.get(position).getPostType());
                    docIntent.putExtra("location", reportModelList.get(position).getLocation());
                    docIntent.putExtra("description", reportModelList.get(position).getPostDes());
                    docIntent.putExtra("img", reportModelList.get(position).getReportImg());
                    reportContext.startActivity(docIntent);
                    return;
                }
            });

            if(reportModelList.get(position).getPostType().equals("Lost")){
                holder.report_lost_type_txt.setVisibility(View.VISIBLE);
                holder.report_lost_type_txt.setText(reportModelList.get(position).getLostType());
            }else {
                holder.report_lost_type_txt.setVisibility(View.GONE);
            }
        }
        else {
            holder.Layout_hide();
        }

    }

    @Override
    public int getItemCount() {
        return reportModelList.size();
    }
    public class ReportViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView report_img;
           private ImageView pet_gender_img,location_icon;
        private TextView uname_txt,postDes_txt, postLocation_txt, report_type_txt,post_time,report_lost_type_txt;

        private LinearLayout report_btn_ful,visibility;

        ViewGroup.LayoutParams params;


        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            report_img = itemView.findViewById(R.id.report_img);
            pet_gender_img = itemView.findViewById(R.id.report_gender_item);
            location_icon = itemView.findViewById(R.id.location_icon_item);

            uname_txt = itemView.findViewById(R.id.report_uname_item);
            postDes_txt = itemView.findViewById(R.id.report_des_item);
            postLocation_txt = itemView.findViewById(R.id.report_location_item);
            report_type_txt = itemView.findViewById(R.id.report_type_item);
            report_lost_type_txt = itemView.findViewById(R.id.report_lost_type_item);
            report_btn_ful = itemView.findViewById(R.id.report_btn_full);
            post_time = itemView.findViewById(R.id.report_time_item);

            visibility = itemView.findViewById(R.id.visible);


            params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);


        }
        private void Layout_hide() {
            params.height = 0;
            //itemView.setLayoutParams(params); //This One.
            visibility.setLayoutParams(params);   //Or This one.

        }

    }

}
