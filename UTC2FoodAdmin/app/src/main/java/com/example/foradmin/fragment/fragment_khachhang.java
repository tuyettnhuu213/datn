package com.example.foradmin.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.adapter.ThongKeKHAdapter;
import com.example.foradmin.model.doanhthu;
import com.example.foradmin.model.thongkekh;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment_khachhang extends Fragment {
    ArrayList<thongkekh> arrayList;
    ThongKeKHAdapter adapter;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);;
        View view = inflater.inflate(R.layout.fragment_khachhang,container,false);
        arrayList = new ArrayList<>();
        adapter = new ThongKeKHAdapter(getContext(),arrayList);
        listView  = view.findViewById(R.id.lvthongkekh);
        listView.setAdapter(adapter);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIUtils.Base_Url + "thongkekhachhang.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int  DTL, SoDonMua, SoTien;
                String  MSSV, TenKH;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            MSSV  = jsonObject.getString("MSSV");
                            TenKH = jsonObject.getString("TenKH");
                            DTL = jsonObject.getInt("DTL");
                            SoDonMua = jsonObject.getInt("sohoadon");
                            SoTien = jsonObject.getInt("doanhthu");
                            arrayList.add(new thongkekh(DTL,SoDonMua,SoTien,MSSV,TenKH));
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
        return view;
    }
}
