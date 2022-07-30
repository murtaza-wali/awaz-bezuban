package com.festdepartment.awaaz_e_bezuban.DiscussionAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.festdepartment.awaaz_e_bezuban.Discussion_Details;
import com.festdepartment.awaaz_e_bezuban.Model.Discussion_Post_Model;
import com.festdepartment.awaaz_e_bezuban.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder> {
    private Context dContext;
    List<Discussion_Post_Model> mData;

    String myUid;


    public DiscussionAdapter(Context dContext, List<Discussion_Post_Model> mData, String myUid) {
        this.dContext = dContext;
        this.mData = mData;
        this.myUid = myUid;
    }

    @NonNull
    @Override
    public DiscussionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(dContext).inflate(R.layout.row_discussion_item, parent, false);

        return new DiscussionViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final DiscussionViewHolder holder, final int position) {

        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvdescription.setText(mData.get(position).getDescription());
        holder.delete_post.setImageResource(R.drawable.ic_baseline_more_vert_24);


        final String postKey = mData.get(position).getPostKey();

        final String uid = mData.get(position).getUserId();
        if (myUid.equals(uid)) {
            holder.delete_post.setVisibility(View.VISIBLE);
        } else {
            holder.delete_post.setVisibility(View.GONE);
        }
        Glide.with(dContext).load(mData.get(position).getPost_picture()).apply(RequestOptions.circleCropTransform()).into(holder.imagePost);

        holder.delete_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(dContext, v);
                popupMenu.getMenuInflater().inflate(R.menu.discussion_report_menu_item, popupMenu.getMenu());
                popupMenu.show();
                if(myUid.equals(uid)){
                    Menu menu = popupMenu.getMenu();
                    MenuItem delete_message = menu.findItem(R.id.delete_comment_item);
                    delete_message.setVisible(true);
                }else {
                    Menu menu = popupMenu.getMenu();
                    MenuItem delete_message = menu.findItem(R.id.delete_comment_item);
                    delete_message.setVisible(false);
                }

                if(!myUid.equals(uid)){
                    Menu menu = popupMenu.getMenu();
                    MenuItem report_message = menu.findItem(R.id.report_comment);
                    report_message.setVisible(true);
                }else {
                    Menu menu = popupMenu.getMenu();
                    MenuItem report_message = menu.findItem(R.id.report_comment);
                    report_message.setVisible(false);
                }

                popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete_comment_item:
                                //delete code to do.

                                if(isNetworkAvailable()) {
                                    final ProgressDialog progressDialog = new ProgressDialog(v.getRootView().getContext());
                                    progressDialog.show();
                                    progressDialog.setMessage("Deleting post...");
                                    progressDialog.setCancelable(false);


                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            DatabaseReference ref_del_comment = FirebaseDatabase.getInstance().getReference("Discussions");
                                            ref_del_comment.child(postKey).removeValue();
                                            mData.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(0, mData.size());
                                            notifyDataSetChanged();
                                            progressDialog.dismiss();
                                        }
                                    },1500);
                                }else {
                                    Resources resources;
                                    resources = dContext.getResources();
                                    Toast.makeText(dContext, "No internet!", Toast.LENGTH_SHORT).show();
                                }
                                break;


                            case R.id.report_comment:
                                Toast.makeText(dContext, "Reported", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
//
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DiscussionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvdescription;
        private CircleImageView imagePost, ImageProfile;
        private Spinner spinner;
        private ImageView delete_post;

        private ConstraintLayout constraintLayout_item;

        public DiscussionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.row_post_title);
            tvdescription = itemView.findViewById(R.id.row_description);
            ImageProfile = itemView.findViewById(R.id.row_post_profile);
            imagePost = itemView.findViewById(R.id.row_post_profile);
            delete_post = itemView.findViewById(R.id.delete_post);
            constraintLayout_item = itemView.findViewById(R.id.discussion_main_btn_item);


            constraintLayout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        Intent intent = new Intent(dContext, Discussion_Details.class);
                        int position = getAdapterPosition();

                        intent.putExtra("title", mData.get(position).getTitle());
                        intent.putExtra("description", mData.get(position).getDescription());
                        intent.putExtra("postKey", mData.get(position).getPostKey());
                        intent.putExtra("username", mData.get(position).getUsername());
                        intent.putExtra("post_picture", mData.get(position).getPost_picture());
                        long timestamp = (long) mData.get(position).getTimeStamp();
                        intent.putExtra("timeStamp", timestamp);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        dContext.startActivity(intent);

                    } else {
                        Toast.makeText(dContext, "No Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) dContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
