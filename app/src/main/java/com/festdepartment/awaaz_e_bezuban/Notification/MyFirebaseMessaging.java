package com.festdepartment.awaaz_e_bezuban.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.festdepartment.awaaz_e_bezuban.Chat;
import com.festdepartment.awaaz_e_bezuban.UserChats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String sented = remoteMessage.getData().get("sented");
//
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if(firebaseUser != null && sented.equals(firebaseUser.getUid())){


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                sendOreoNotification(remoteMessage);
            }
            sendNotification(remoteMessage);
//        }else {
//            Toast.makeText(this, "User Null", Toast.LENGTH_SHORT).show();
//        }

    }

    private void sendOreoNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent intent = new Intent(this, Chat.class);
        Bundle bundle = new Bundle();
        bundle.putString("userid",user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,j,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoNotification oreoNotification = new OreoNotification(this);
        Notification.Builder builder = oreoNotification.getOreoNotification(title,body,pendingIntent,defaultsound,icon);

        int i = 0;

        if(j>0){
            i = j;
        }
        oreoNotification.getManager().notify(i,builder.build());
    }


    private void sendNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent intent = new Intent(this, UserChats.class);
        Bundle bundle = new Bundle();
        bundle.putString("userid",user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,j,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultsound).
                setVibrate(new long[]{1000,1000}).
                setLights(Color.RED,1000,5000)
                .setContentIntent(pendingIntent);

        NotificationManager noti = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int i = 0;

        if(j>0){
            i = j;
        }
        noti.notify(i,builder.build());

    }
}
