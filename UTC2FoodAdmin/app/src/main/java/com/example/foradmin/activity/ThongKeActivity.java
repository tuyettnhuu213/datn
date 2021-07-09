package com.example.foradmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.fragment.fragment_doanhthu;
import com.example.foradmin.fragment.fragment_khachhang;
import com.example.foradmin.fragment.fragment_sanpham;
import com.example.foradmin.model.doanhthu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {
    ArrayList<doanhthu> doanhthuArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
    }
    public void AddFragment(View view){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (view.getId()){
            case R.id.btnDoanhThu:
                fragment = new fragment_doanhthu();
                break;
            case R.id.btnSanPham:
                fragment = new fragment_sanpham();
                break;
            case R.id.btnKhachHang:
                fragment = new fragment_khachhang();
                break;
        }
        fragmentTransaction.add(R.id.fragmentThongKe,fragment);
        fragmentTransaction.commit();
    }

}