package com.festdepartment.awaaz_e_bezuban.ChatAdaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.festdepartment.awaaz_e_bezuban.Chat;
import com.festdepartment.awaaz_e_bezuban.Common.Constantss;
import com.festdepartment.awaaz_e_bezuban.Model.ResgisteredUser;
import com.festdepartment.awaaz_e_bezuban.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ChatUserViewHolder> {


    Context userContext;
    private List<ResgisteredUser> resgisteredUserList;

    String lastmsg;
    String lasttime;
    private boolean ischat;


    public ChatUserAdapter(Context userContext, List<ResgisteredUser> resgisteredUserList, boolean ischat) {
        this.userContext = userContext;
        this.resgisteredUserList = resgisteredUserList;
        this.ischat = ischat;
    }

    @NonNull
    @NotNull
    @Override
    public ChatUserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View chatuser = LayoutInflater.from(userContext).inflate(R.layout.userchat_layout_item,parent,false);
        return new ChatUserViewHolder(chatuser);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatUserViewHolder holder, int position) {


        holder.userChatImg.setImageResource(R.drawable.logo);

        holder.userChat_name.setText(resgisteredUserList.get(position).getUserName());

        holder.userchat_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constantss.CHAT = "directChat";
                Intent intent = new Intent(userContext, Chat.class);
                intent.putExtra("userName",resgisteredUserList.get(position).getUserName());
                intent.putExtra("userID",resgisteredUserList.get(position).getUserId());
                userContext.startActivity(intent);
            }
        });

        if(ischat){
            lastMessage(resgisteredUserList.get(position).getUserId(),holder.last_msg, holder.last_msg_time);
        }else {
            holder.last_msg.setVisibility(View.GONE);
            holder.last_msg_time.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return resgisteredUserList.size();
    }

    public class ChatUserViewHolder extends RecyclerView.ViewHolder {



        private CircleImageView userChatImg;
        private TextView userChat_name;
        private LinearLayout userchat_list_btn;

        private TextView last_msg, last_msg_time;

        public ChatUserViewHolder(@NonNull View itemView) {
            super(itemView);

            userChatImg = itemView.findViewById(R.id.userchat_img_item);
            userChat_name = itemView.findViewById(R.id.chat_username_item);
            userchat_list_btn = itemView.findViewById(R.id.userchat_list_btn_item);
            last_msg = itemView.findViewById(R.id.last_chat_item);
            last_msg_time = itemView.findViewById(R.id.last_chat_time_item);

        }
    }

    //check for last msg
    private void lastMessage(String userid, TextView last_msg, TextView last_time){

        lastmsg = "default";
        lasttime = "default";

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chat");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    com.festdepartment.awaaz_e_bezuban.Model.Chat chat = snapshot1.getValue(com.festdepartment.awaaz_e_bezuban.Model.Chat.class);
                    if(chat.getReceiver().equals(firebaseUser.getUid())&& chat.getSender().equals(userid)
                || chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())){

                        lastmsg = chat.getChat();
                        lasttime = chat.getChatTime();
                    }
                }
                switch (lastmsg){
                    case "default":
                        last_msg.setText("No Message");
                        last_time.setText("");
                        break;
                    default:
                        last_msg.setText(lastmsg);
                        last_time.setText(lasttime);
                        break;
                }
                lastmsg = "default";
                lasttime = "default";
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}
