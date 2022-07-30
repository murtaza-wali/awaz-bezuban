package com.festdepartment.awaaz_e_bezuban.DiscussionAdapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.festdepartment.awaaz_e_bezuban.Model.Discussion_Comment;
import com.festdepartment.awaaz_e_bezuban.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Comment_Adapter extends RecyclerView.Adapter<Comment_Adapter.CommentViewHolder> {
    Context cContext;
    private List<Discussion_Comment> discussion_comments;
    String myUid,postkey;

    public Comment_Adapter(Context cContext, List<Discussion_Comment> discussion_comments, String myUid, String postkey) {
        this.cContext = cContext;
        this.discussion_comments = discussion_comments;
        this.myUid = myUid;
        this.postkey = postkey;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row_comment = LayoutInflater.from(cContext).inflate(R.layout.row_comment_discussion,parent,false);

        return new CommentViewHolder(row_comment);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, final int position) {
//        Glide.with(cContext).load(discussion_comments.get(position).getimg).into(userprofileImg);

        final Discussion_Comment item = discussion_comments.get(position);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        final String userreport = user.getUid();
        final String userreportName = user.getDisplayName();
        final String usercomment = discussion_comments.get(position).getContent();
        final String userCommentName = discussion_comments.get(position).getUnname();
        final String usercommentId = discussion_comments.get(position).getUid();

        final String name = discussion_comments.get(position).getUnname();

        final String uid = discussion_comments.get(position).getUid();
        final String cId = discussion_comments.get(position).getcId();
        final Object timestamp = discussion_comments.get(position).getTimestamp();

        holder.edit_comment.setImageResource(R.drawable.ic_baseline_more_vert_24);
//        final String gender = discussion_comments.get(position).getgender();









        holder.username.setText(discussion_comments.get(position).getUnname());
        holder.comment.setText(discussion_comments.get(position).getContent());
        holder.comment_time.setText(timeStampToString((Long) discussion_comments.get(position).getTimestamp()));

        holder.edit_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(cContext, view);
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
                                final ProgressDialog progressBar = new ProgressDialog(view.getRootView().getContext());
                                progressBar.show();
                                progressBar.setMessage("Deleting...");
                                progressBar.setCancelable(false);



                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        DatabaseReference ref_del_comment = FirebaseDatabase.getInstance().getReference("Discussions Comment").child(postkey)
                                                .child("Comment");
                                        ref_del_comment.child(cId).removeValue();
                                        discussion_comments.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(0, discussion_comments.size());
                                        notifyDataSetChanged();
                                        progressBar.dismiss();

                                    }
                                },2000);


                                break;


                            case R.id.report_comment:

                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                                View dialogview = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.report_comment,null);

                                //
                                TextView appointment_dialog_text;
                                final TextInputEditText report_editText;
                                ImageButton report_btn;

                                report_editText = dialogview.findViewById(R.id.report_reason_editText);
                                report_btn = dialogview.findViewById(R.id.report_comment_btn);
                                builder.setView(dialogview);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                alertDialog.show();
                                alertDialog.setCancelable(true);

                                report_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final String report_text = report_editText.getText().toString().trim();
                                        if (TextUtils.isEmpty(report_text)) {
                                            Toast.makeText(cContext, "please share reason", Toast.LENGTH_SHORT).show();
                                            report_editText.requestFocus();
                                            return;
                                        } else {

                                            final ProgressDialog progressBar = new ProgressDialog(view.getRootView().getContext());
                                            progressBar.show();
                                            progressBar.setMessage("Reporting...");
                                            progressBar.setCancelable(false);


                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    DatabaseReference report_comment_ref = FirebaseDatabase.getInstance().getReference("Discussions Comment").child("Report Comment")
                                                            .child(postkey);
                                                    String ts = String.valueOf(System.currentTimeMillis());
                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                    hashMap.put("comment", usercomment);
                                                    hashMap.put("Comment_User_Id", usercommentId);
                                                    hashMap.put("comment_User_Name", userCommentName);
                                                    hashMap.put("Report_By", userreportName);
                                                    hashMap.put("Report_By_id", userreport);
                                                    hashMap.put("Report_By", userreportName);
                                                    hashMap.put("Report_Reason", report_text);

                                                    report_comment_ref.child(ts).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(cContext, "Report Sent", Toast.LENGTH_SHORT).show();
                                                            Menu menu = popupMenu.getMenu();
                                                            MenuItem report_message = menu.findItem(R.id.report_comment);
                                                            report_message.setVisible(false);
                                                            alertDialog.dismiss();
                                                            progressBar.dismiss();

                                                        }
                                                    });

                                                }
                                            },2000);
                                        }
                                    }
                                });

                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });

            }


        });
    }


    @Override
    public int getItemCount() {
        return discussion_comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MyViewHolder";
        private TextView username, comment, comment_time;
        private ImageButton edit_comment;
        private ImageView userprofileImg;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.comment_username);
            comment = itemView.findViewById(R.id.user_coment);
            comment_time = itemView.findViewById(R.id.time_comment);
            userprofileImg = itemView.findViewById(R.id.comment_profile_img);
            edit_comment = itemView.findViewById(R.id.delete_comment);

        }
    }


    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.discussion_report_menu_item, popupMenu.getMenu());
        popupMenu.show();

    }


    private String timeStampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("hh:mma dd-MM-yyyy", calendar).toString();
        return date;
    }
}