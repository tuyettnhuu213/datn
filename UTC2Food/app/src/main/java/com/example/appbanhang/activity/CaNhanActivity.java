package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.util.checkconnection;
import com.example.appbanhang.util.server;
import com.example.appbanhang.R;
import com.google.android.material.textfield.TextInputEditText;

public class CaNhanActivity extends AppCompatActivity {
TextInputEditText edtTen,edtSDT,edtDiaChi;
TextView edtUsername;
Button btnXacnhan,btnQuaylai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_nhan);
        anhxa();
        getdulieu();
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        eventBtnXacnhan();
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    private void getdulieu() {
        edtUsername.setText("Xin chào "+MainActivity.KHang.getMssv());
        edtTen.setText(MainActivity.KHang.getTen());
        edtDiaChi.setText(MainActivity.KHang.getDiachi());
        edtSDT.setText(MainActivity.KHang.getSdt());
    }

    private void eventBtnXacnhan() {
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(edtTen.getText().length()>0 && edtDiaChi.getText().length()>0 && edtSDT.getText().length()>0){
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            String url = server.updatekhachhang+"?MSSV="+MainActivity.KHang.getMssv()+"&TenKH=%27"+edtTen.getText().toString().trim()+"%27&DiaChi=%27"+edtDiaChi.getText().toString().trim()+"%27&SDT=%27"+edtSDT.getText().toString().trim()+"%27&password=%27"+MainActivity.KHang.getPassword().trim()+"%27";
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.trim().equals("true"))
                                    {
                                        checkconnection.showToast_short(getApplicationContext(),"Cập nhật thông tin thành công");
                                    }
                                    else {
                                        checkconnection.showToast_short(getApplicationContext(),"Cập nhật thông tin thất bại. Hãy thử lại");
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            requestQueue.add(stringRequest);

                    } else {
                        checkconnection.showToast_short(getApplicationContext(),"Hãy điền đầy đủ thông tin");
                    }
            }
        });
    }

    private void anhxa() {
        edtUsername = findViewById(R.id.edtCNusername);
        edtTen = findViewById(R.id.edtCNTen);
        edtSDT = findViewById(R.id.edtCNSDT);
        edtDiaChi = findViewById(R.id.edtCNDiaChi);
        btnXacnhan = findViewById(R.id.btnCNXacNhan);
        btnQuaylai = findViewById(R.id.btnCNTroVe);
    }
}