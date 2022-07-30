package com.festdepartment.awaaz_e_bezuban.ChatAdaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.festdepartment.awaaz_e_bezuban.Adapter.ReportAdapter;
import com.festdepartment.awaaz_e_bezuban.Model.Chat;
import com.festdepartment.awaaz_e_bezuban.Model.ReportModel;
import com.festdepartment.awaaz_e_bezuban.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatAdapterViewholder> {



    Context chatContext;
    private List<Chat> chatList;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    int i = 0;

    FirebaseUser firebaseUser;


    public ChatAdapter(Context chatContext, List<Chat> chatList) {
        this.chatContext = chatContext;
        this.chatList = chatList;
    }

    @NonNull
    @NotNull
    @Override
    public ChatAdapterViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_RIGHT){
            View userView = LayoutInflater.from(chatContext).inflate(R.layout.message_container_sent_item,parent,false);
            return new ChatAdapterViewholder(userView);
        }else {
            View userView = LayoutInflater.from(chatContext).inflate(R.layout.message_container_receive_item,parent,false);
            return new ChatAdapterViewholder(userView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatAdapterViewholder holder, int position) {

        Chat chat = chatList.get(position);

        holder.showMessage_txt.setText(chat.getChat());
        holder.chatTime_txt.setText(chat.getChatTime());


        holder.showMessage_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                i++;
                if (i == 1) {
                    holder.chatTime_txt.setVisibility(View.VISIBLE);
                    return;
                }else if(i == 2){
                    holder.chatTime_txt.setVisibility(View.INVISIBLE);
                    return;
                }
                i = 0;
            }
        });



    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatAdapterViewholder extends RecyclerView.ViewHolder {


        private TextView showMessage_txt,chatTime_txt;
        public ChatAdapterViewholder(@NonNull View itemView) {
            super(itemView);

            showMessage_txt = itemView.findViewById(R.id.show_message_txt_item);
            chatTime_txt = itemView.findViewById(R.id.chat_time_item);


        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
