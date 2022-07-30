package com.festdepartment.awaaz_e_bezuban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.festdepartment.awaaz_e_bezuban.ChatAdaper.ChatAdapter;
import com.festdepartment.awaaz_e_bezuban.Common.Constantss;
import com.festdepartment.awaaz_e_bezuban.Model.ResgisteredUser;
import com.festdepartment.awaaz_e_bezuban.Notification.Client;
import com.festdepartment.awaaz_e_bezuban.Notification.Data;
import com.festdepartment.awaaz_e_bezuban.Notification.MyResponse;
import com.festdepartment.awaaz_e_bezuban.Notification.Sender;
import com.festdepartment.awaaz_e_bezuban.Notification.Token;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {

    private EditText message_Edxt;
    private AppCompatImageView send_msg_btn;
    private TextView chatUserName;

    ChatAdapter chatAdapter;
    List<com.festdepartment.awaaz_e_bezuban.Model.Chat> chatList;

    private RecyclerView chat_rv;

    private DatabaseReference chatRef;
    private FirebaseAuth auth;


    APISERVICE apiservice;

    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        apiservice = Client.getClient("https://fcm.googleapis.com/").create(APISERVICE.class);

        chatRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();


        chat_rv = findViewById(R.id.chat_rv);
        chat_rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chat_rv.setLayoutManager(linearLayoutManager);

        initInstances();
        addChat();



        //        Textwatcher
        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (message_Edxt.getText().toString().startsWith(" ")   ) {
                    message_Edxt.setText("");

                }
            }

        };
        message_Edxt.addTextChangedListener(watcher);


        chatRef.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String uid = getIntent().getStringExtra("uid");
                String uid_ = getIntent().getStringExtra("userID");
                if(Constantss.CHAT.equals("Chat")){
                    readMessage(auth.getUid(),uid);
                }else {
                    readMessage(auth.getUid(),uid_);

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void addChat() {



        send_msg_btn.setOnClickListener(new View.OnClickListener() {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            @Override
            public void onClick(View view) {
                String msg = message_Edxt.getText().toString();
                notify = true;
                if (!msg.equals("")) {
                    if (Constantss.CHAT.equals("Chat")) {

                        String uid = getIntent().getStringExtra("uid");
                        String name = getIntent().getStringExtra("uname");
                        DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

                        String cID = ref.push().getKey();
                        String cDay = dateFormat1.format(new Date());
                        com.festdepartment.awaaz_e_bezuban.Model.Chat chat = new com.festdepartment.awaaz_e_bezuban.Model.Chat(auth.getUid().toString(), uid, message_Edxt.getText().toString(),cDay,auth.getCurrentUser().getDisplayName(),name);
                        chat.setChatID(cID);
                        ref.child("Chat").child(cID).setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Chat.this, "Success!", Toast.LENGTH_SHORT).show();
                                message_Edxt.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(Chat.this, "Can not send message", Toast.LENGTH_SHORT).show();
                            }
                        });


                        DatabaseReference chatref = FirebaseDatabase.getInstance().getReference("Chatlist").
                                child(auth.getUid()).child(uid);
                        chatref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                if (!snapshot.exists()) {
                                    chatref.child("id").setValue(uid);
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


                        DatabaseReference chatref_other = FirebaseDatabase.getInstance().getReference().child("Chatlist").
                                child(uid).child(auth.getUid());
                        chatref_other.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                if (!snapshot.exists()) {
                                    chatref_other.child("id").setValue(auth.getUid());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


//                        notfication
                        final String message = msg;
                        DatabaseReference notref = FirebaseDatabase.getInstance().getReference("UserRegister").child(auth.getUid());

                        notref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if(notify) {
                                    ResgisteredUser resgisteredUser = snapshot.getValue(ResgisteredUser.class);
                                    sendNotification(uid, resgisteredUser.getUserName(), msg);
                                }
                                notify = false;
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

//                        notfication



                    } else if (Constantss.CHAT.equals("directChat")) {
                        String uid = getIntent().getStringExtra("userID");
                        String name_ = getIntent().getStringExtra("userName");
                        DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

                        String cID = ref.push().getKey();
                        String cDay = dateFormat1.format(new Date());
                        com.festdepartment.awaaz_e_bezuban.Model.Chat chat = new com.festdepartment.awaaz_e_bezuban.Model.Chat(auth.getUid().toString(), uid, message_Edxt.getText().toString(),cDay,auth.getCurrentUser().getDisplayName(),name_);
                        chat.setChatID(cID);
                        ref.child("Chat").child(cID).setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                message_Edxt.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(Chat.this, "Can not send message", Toast.LENGTH_SHORT).show();
                            }
                        });


                        DatabaseReference chatref = FirebaseDatabase.getInstance().getReference("Chatlist").
                                child(auth.getUid()).child(uid);
                        chatref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (!snapshot.exists()) {
                                    chatref.child("id").setValue(uid);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


                        //                        notfication
                        final String message = msg;
                        DatabaseReference notref = FirebaseDatabase.getInstance().getReference("UserRegister").child(auth.getUid());

                        notref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if(notify) {
                                    ResgisteredUser resgisteredUser = snapshot.getValue(ResgisteredUser.class);
                                    sendNotification(uid, resgisteredUser.getUserName(), msg);
                                }
                                notify = false;
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

//                        notfication




                    }
                } else {
                    Toast.makeText(Chat.this, "Can not send empty message.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void sendNotification(String uid, String userName, String msg) {
        DatabaseReference tokens =  FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(uid);

        String userId = getIntent().getStringExtra("uid");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Token token = snapshot1.getValue(Token.class);
                    Data data = new Data(auth.getUid(),R.drawable.logo,userName+": "+msg,"New Message",userId);

                    Sender sender = new Sender(data,token.getToken());
                    apiservice.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if(response.code() ==200){
                                if(response.body().success != 1){
                                    Toast.makeText(Chat.this, "Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void initInstances() {
        message_Edxt = findViewById(R.id.type_message_edTxt);
        send_msg_btn = findViewById(R.id.chat_send_btn);
        chatUserName = findViewById(R.id.chat_with_username);


        String name = getIntent().getStringExtra("uname");
        String name_ = getIntent().getStringExtra("userName");

        if(Constantss.CHAT.equals("Chat")){
            chatUserName.setText(name);
        }else {
            chatUserName.setText(name_);

        }

    }

    private void readMessage(String myid, String userid){
        chatList = new ArrayList<com.festdepartment.awaaz_e_bezuban.Model.Chat>();

        chatRef = FirebaseDatabase.getInstance().getReference("Chat");
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                chatList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    com.festdepartment.awaaz_e_bezuban.Model.Chat chat = snapshot1.getValue(com.festdepartment.awaaz_e_bezuban.Model.Chat.class);
                    if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                    chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        chatList.add(chat);

                    }

                    chatAdapter = new ChatAdapter(Chat.this,chatList);
                    chat_rv.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void message_back(View view) {
        startActivity(new Intent(Chat.this,UserChats.class));
    }


    public void user_info(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Chat.this);
        View user_view = getLayoutInflater().inflate(R.layout.profile_details_chat_item, null);

        TextView chat_uname = user_view.findViewById(R.id.chat_username);
        TextView member_since = user_view.findViewById(R.id.member_since);
        ImageView cancel_chat_profile = user_view.findViewById(R.id.cancel_chat_profile);

        builder.setView(user_view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.getWindow().getAttributes().gravity = Gravity.TOP;
        alertDialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.MATCH_PARENT);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        chat_uname.setText(chatUserName.getText().toString());

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("UserRegister");
        myref.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String membersince = snapshot.child("account_createdOn").getValue(String.class);

                member_since.setText(membersince);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        cancel_chat_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


    }
}