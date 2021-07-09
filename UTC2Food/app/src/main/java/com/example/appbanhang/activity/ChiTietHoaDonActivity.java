package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.CTHDAdapter;
import com.example.appbanhang.model.ChiTietHoaDon;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.util.checkconnection;
import com.example.appbanhang.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietHoaDonActivity extends AppCompatActivity {
    ListView lvCTHD;
    int idhd = 0;
    CTHDAdapter cthdAdapter;
    ArrayList<ChiTietHoaDon> mangcthd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        anhxa();
        getidhd();
        getdulieu();
    }

    private void getidhd() {
        idhd = getIntent().getIntExtra("idhd", -1);
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    private void anhxa() {

        lvCTHD =(ListView) findViewById(R.id.lvCTHD);
        mangcthd = new ArrayList<>();
        cthdAdapter = new CTHDAdapter(getApplicationContext(), mangcthd);
        lvCTHD.setAdapter(cthdAdapter);
    }

    private void getdulieu() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = server.getcthoadon + "?IdHD=" + idhd;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int gia = 0, sl = 0;
                String ten = "", ghichu = "",ha="";
                Boolean ttcom = true;
                if (response != null && response.length() != 3) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
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
                    checkconnection.showToast_short(getApplicationContext(),"Không có dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idhd", String.valueOf(idhd));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}