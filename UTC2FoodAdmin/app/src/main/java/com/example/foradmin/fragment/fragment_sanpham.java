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
import com.example.foradmin.adapter.ThongKeSPAdapter;
import com.example.foradmin.model.thongkekh;
import com.example.foradmin.model.thongkesp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment_sanpham extends Fragment {
    ArrayList<thongkesp> arrayList;
    ThongKeSPAdapter adapter;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);;
        View view = inflater.inflate(R.layout.fragment_sanpham,container,false);
        arrayList = new ArrayList<>();
        adapter = new ThongKeSPAdapter(getContext(),arrayList);
        listView  = view.findViewById(R.id.lvthongkekh);
        listView.setAdapter(adapter);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIUtils.Base_Url + "thongkesanpham.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String tensp,ha,tenloai;
                int gia,tongsoluongban;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            tensp  = jsonObject.getString("TenSP");
                            ha = jsonObject.getString("HAnhSP");
                            tenloai = jsonObject.getString("TenLoaiSP");
                            gia = jsonObject.getInt("GiaSP");
                            tongsoluongban = jsonObject.getInt("tongsoluong");
                            arrayList.add(new thongkesp(tensp,ha,tenloai,gia,tongsoluongban));
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
