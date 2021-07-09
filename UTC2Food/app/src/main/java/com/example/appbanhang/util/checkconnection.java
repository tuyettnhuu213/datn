package com.example.appbanhang.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class checkconnection {
    public static boolean havenetworkconnect(Context context) {
        boolean havewifi =false;
        boolean haveconnect = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netinfo = cm.getAllNetworkInfo();
        for(NetworkInfo ni : netinfo){
            if(ni.getTypeName().equalsIgnoreCase("WIFI"))
                if(ni.isConnected())
                    havewifi = true;
            if(ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if(ni.isConnected())
                    haveconnect = true;
        }
        return havewifi || haveconnect;

    }
    public static void showToast_short(Context context,String thongbao)
    {
        Toast.makeText(context,thongbao,Toast.LENGTH_SHORT).show();
    }
}
