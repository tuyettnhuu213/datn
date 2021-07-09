package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.CTHDAdapter;
import com.example.appbanhang.model.ChiTietHoaDon;
import com.example.appbanhang.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatHangThanhCongActivity extends AppCompatActivity {
int idhd = 0,tongtien=0;
ArrayList<ChiTietHoaDon> mangcthd;
CTHDAdapter cthdAdapter;
ListView lvDatHang;
int page=1;
ImageView imvthanhcong;
TextView tien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang_thanh_cong);
        anhxa();
        getidhd();
        getdulieu();
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void anhxa() {
        tien = findViewById(R.id.txtTongTienDatHang);
        mangcthd = new ArrayList<>();
        cthdAdapter = new CTHDAdapter(getApplicationContext(),mangcthd);
        lvDatHang = findViewById(R.id.lvDatHang);
        lvDatHang.setAdapter(cthdAdapter);
        imvthanhcong = findViewById(R.id.imvthanhcong);
        Glide.with(this).load(R.drawable.giphy).into(imvthanhcong);
    }
    private void getidhd() {
            idhd = getIntent().getIntExtra("idhd", -1);
    }

    private void getdulieu() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tien.setText("$"+decimalFormat.format(GioHang.tien)+"đ");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = server.getcthoadon + "?IdHD=" + idhd;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int gia = 0, sl = 0;
                String ten = "", ghichu = "",ha="";
                Boolean ttcom = true;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i <= jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ha = jsonObject.getString("HAnhSP");
                            ten = jsonObject.getString("TenSP");
                            gia = jsonObject.getInt("Gia");
                            sl = jsonObject.getInt("SoLuong");
                            ghichu = jsonObject.getString("GhiChu");
                            mangcthd.add(new ChiTietHoaDon(sl, gia, ten,ha, ghichu));
                            cthdAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {

                    }
                } else {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
        MainActivity.manggiohang.clear();
        MainActivity.reloaiSL();
        SanPhamActivity.reloaiSL();
    }


}