package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class QuenMatKhauActivity extends AppCompatActivity {
TextInputEditText text1,text2;
Button btnOK,btnCancel;
String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        username = getIntent().getStringExtra("username");Log.e("TAG",username);
        text1 = findViewById(R.id.edtpasswordnewCHANGE);
        text2 = findViewById(R.id.edtpasswordnew2CHANGE);
        btnOK = findViewById(R.id.btnXacNhanMKCHANGE);
        btnCancel = findViewById(R.id.btnTroVeMKCHANGE);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text1.getText().toString().equals(text2.getText().toString())){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = server.updatekhachhang+"?MSSV=%27"+username+"%27&password=%27"+text1.getText().toString().trim()+"%27";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("true1"))
                            {
                                Toast.makeText(getApplicationContext(),"?????i m???t kh???u th??nh c??ng",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else if(response.trim().equals("false1")) {
                                checkconnection.showToast_short(getApplicationContext(),"C???p nh???t th??ng tin th???t b???i. H??y th??? l???i");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(getApplicationContext(),"M???t kh???u nh???p l???i ch??a ????ng",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}