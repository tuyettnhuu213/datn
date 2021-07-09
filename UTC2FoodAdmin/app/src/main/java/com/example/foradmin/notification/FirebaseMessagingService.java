package com.example.foradmin.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.activity.MainActivity;
import com.google.android.gms.tasks.Task;
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
        Log.d("TAGTOKEN",s);
        OkHttpClient client  = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", s).build();
        Request request = new Request.Builder()
                .url(APIUtils.Base_Url +"register.php").post(body).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }
        registerToken(s);


    }

    private void registerToken(String token) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = APIUtils.Base_Url +"register.php?Token='"+token+"'";
        Log.e("TAG",url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

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
