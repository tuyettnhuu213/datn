package com.example.foradmin.Retrofit2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class APIUtils extends Activity {
    public static final String Base_Url = "https://datn.webstudents.xyz/";
    public static String getloaisp = Base_Url+"getloaisp.php";
    public static String getsp = Base_Url+"getsp.php?page=";
    public static String getcthoadon = Base_Url+"getCTHD.php";
    public static String gethoadon = Base_Url+"gethoadon.php";
    public static String deleteloaisp = Base_Url + "deleteloaisp.php";
    public static String deletesp = Base_Url + "deletesp.php";
    public static String updateloaisp = Base_Url + "updateloaisp.php";
    public static String updatesanpham = Base_Url + "updatesanpham.php";
    public static String updatetthd = Base_Url + "updatetthd.php";
    public static String getdoanhthu = Base_Url + "getdoanhthu.php";
    public static String getSLHD = Base_Url + "inserttoken.php";
    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
