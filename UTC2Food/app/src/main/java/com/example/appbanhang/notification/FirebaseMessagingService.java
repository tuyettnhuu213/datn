package com.example.appbanhang.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.GioHang;
import com.example.appbanhang.activity.MainActivity;
import com.example.appbanhang.util.server;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("message"));
    }

    @Override
    public void onNewToken(@NonNull String s) {
        OkHttpClient client  = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", s).build();
        Request request = new Request.Builder()
                .url(server.localhost+"register.php").post(body).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void registerToken(Task<String> token) {
        OkHttpClient client  = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token.toString()+"").build();
        Request request = new Request.Builder()
                .url(server.localhost+"register.php").post(body).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void showNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("UTC2 Đặt món")
                .setContentText(message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
}
