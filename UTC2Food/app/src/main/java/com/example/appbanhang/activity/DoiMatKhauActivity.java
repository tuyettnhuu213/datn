package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.util.checkconnection;
import com.example.appbanhang.util.server;
import com.google.android.material.textfield.TextInputEditText;

public class DoiMatKhauActivity extends AppCompatActivity {
    TextInputEditText edtPass,edtPass2,edtPassOld;
    Button btnOK,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        anhxa();
        eventBtnOK();
        eventBtnCancel();


    }
    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void anhxa() {
        edtPassOld = findViewById(R.id.edtpasswordold);
        edtPass = findViewById(R.id.edtpasswordnew);
        edtPass2 = findViewById(R.id.edtpasswordnew2);
        btnOK = findViewById(R.id.btnXacNhanMK);
        btnCancel = findViewById(R.id.btnTroVeMK);
    }

    private void eventBtnCancel() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void eventBtnOK() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPassOld.getText().length()>0&&edtPass.getText().length()>0&&edtPass2.getText().length()>0){
                    if(edtPassOld.getText().toString().trim().equals(MainActivity.KHang.getPassword().trim())){
                        if(edtPass.getText().toString().trim().equals(edtPass2.getText().toString().trim())){
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            String url = server.updatekhachhang+"?MSSV="+MainActivity.KHang.getMssv()+"&TenKH=%27"+MainActivity.KHang.getTen().trim()+"%27&DiaChi=%27"+MainActivity.KHang.getDiachi().trim()+"%27&SDT=%27"+MainActivity.KHang.getSdt().trim()+"%27&password=%27"+edtPass.getText().toString().trim()+"%27";
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.trim().equals("true"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Đổi mật khẩu thành công",Toast.LENGTH_LONG).show();
                                        finish();
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
                            checkconnection.showToast_short(getApplicationContext(),"Mật khẩu không khớp");
                        }

                    } else {
                        checkconnection.showToast_short(getApplicationContext(),"Mật khẩu cũ không đúng");
                    }

                }else {
                    checkconnection.showToast_short(getApplicationContext(),"Hãy nhập đẩy đủ thông tin");
                }

            }
        });
    }

}