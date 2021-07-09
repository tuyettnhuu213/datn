package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.HoaDonAdapter;
import com.example.appbanhang.model.ChiTietHoaDon;
import com.example.appbanhang.model.HoaDon;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.util.checkconnection;
import com.example.appbanhang.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LichSuActivity extends AppCompatActivity {
 ListView lvLichSu;
 int page = 1,IdKH =0;
 HoaDonAdapter hoaDonAdapter;
 ArrayList<HoaDon> arrayHoaDon;
 View footer;
 Boolean isLoading = false, limitdata = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);
        anhxa();
        getdulieu();
        getmoredate();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugio,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),com.example.appbanhang.activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void getdulieu() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = server.gethoadon+"?MSSV='"+MainActivity.KHang.getMssv()+"'";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int IdHD = 0, IdKH = 0, tinhtrang = 0, TongTien =0,TTThanhToan;
                String ThoiGianDat="",ThoiGianNhan,Ngay,PTThanhToan,MSSV;
                Boolean tt = true;
                if (response != null && response.length() != 2) {
                      lvLichSu.removeFooterView(footer);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //function hoadon($IdHD,$ThoiGianDat,$ThoiGianNhan,$Ngay,$TongTien,$TinhTrangHD,$TTThanhToan,$PTThanhToan){
                            IdHD = jsonObject.getInt("IdHD");
                            ThoiGianDat = jsonObject.getString("ThoiGianDat");
                            ThoiGianNhan = jsonObject.getString("ThoiGianNhan");
                            Ngay = jsonObject.getString("Ngay");
                            TongTien = jsonObject.getInt("TongTien");
                            tinhtrang = jsonObject.getInt("TinhTrangHD");
                            TTThanhToan = jsonObject.getInt("TTThanhToan");
                            PTThanhToan = jsonObject.getString("PTThanhToan");
                            MSSV = jsonObject.getString("MSSV");
                            if (tinhtrang == 1)
                                tt = true;
                            else
                                tt = false;
                            arrayHoaDon.add(new HoaDon(IdHD, IdKH,TongTien,ThoiGianDat,tt));
                            hoaDonAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {

                    }
                }
                else {
                    limitdata = true;
                    lvLichSu.removeFooterView(footer);
                    checkconnection.showToast_short(getApplicationContext(), "Hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    private void anhxa() {
        lvLichSu = findViewById(R.id.lvLichSu);
        arrayHoaDon = new ArrayList<>();
        hoaDonAdapter = new HoaDonAdapter(getApplicationContext(),arrayHoaDon);
        lvLichSu.setAdapter(hoaDonAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footer = inflater.inflate(R.layout.processbar,null);

    }
    private void getmoredate() {
        lvLichSu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietHoaDon.class);
                intent.putExtra("idhd", String.valueOf(arrayHoaDon.get(position).getIdHD()));
                startActivity(intent);
            }
        });
       lvLichSu.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {

            }
        });
    }


}