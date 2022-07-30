package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.festdepartment.awaaz_e_bezuban.ChatAdaper.ChatUserAdapter;
import com.festdepartment.awaaz_e_bezuban.Model.ChatListModel;
import com.festdepartment.awaaz_e_bezuban.Model.ResgisteredUser;
import com.festdepartment.awaaz_e_bezuban.Notification.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserChats extends AppCompatActivity {


    private RecyclerView userchat_rv;

    ChatUserAdapter chatUserAdapter;
    List<ChatListModel> userList;
    private List<ResgisteredUser>resgisteredUsers;


//    Database
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;

//    Database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chats);


        userchat_rv = findViewById(R.id.userchat_rv);
        userchat_rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserChats.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        userchat_rv.setLayoutManager(linearLayoutManager);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        userList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                userList.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ChatListModel chatListModel = snapshot1.getValue(ChatListModel.class);
                    userList.add(chatListModel);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void chatList() {
        resgisteredUsers = new ArrayList<>();

        DatabaseReference userreference = FirebaseDatabase.getInstance().getReference("UserRegister");
        userreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                resgisteredUsers.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    ResgisteredUser user = snapshot1.getValue(ResgisteredUser.class);
                    for(ChatListModel chatListModel : userList){
                        if(user.getUserId().equals(chatListModel.getId())){
                            resgisteredUsers.add(user);
                        }
                    }
                }
                chatUserAdapter = new ChatUserAdapter(UserChats.this,resgisteredUsers,true);
                userchat_rv.setAdapter(chatUserAdapter);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void user_chat_back(View view) {
        startActivity(new Intent(UserChats.this,Dashboard.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserChats.this,MainActivity.class));
    }
}