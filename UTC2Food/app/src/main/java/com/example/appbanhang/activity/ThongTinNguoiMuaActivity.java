package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.util.checkconnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.util.server;
import com.example.appbanhang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ThongTinNguoiMuaActivity extends AppCompatActivity {
    int idkh;
    Button btnXacnhan, btnTrove, btnConfirm, btnCancel;
    TextInputEditText edtTen, edtSDT, edtDiachi,edtuser,edtpass,edtpass2;
    EditText edtverifycode;
    String phoneNumber,code;
    PhoneAuthProvider.ForceResendingToken token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_mua);
        anhxa();
        eventtrove();
        if(checkconnection.havenetworkconnect(getApplicationContext())){
            eventBtnXacnhan();
        } else checkconnection.showToast_short(getApplicationContext(),"Bạn hãy kiểm tra kết nối");
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    private void themkh() {
        String ten = edtTen.getText().toString().trim();
        String dc = edtDiachi.getText().toString().trim();
        String sd = edtSDT.getText().toString().trim();
        String us = edtuser.getText().toString().trim();
        String pa = edtpass.getText().toString().trim();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = server.insertkhachhang+"?TenKH=%27"+ten+"%27&DiaChi=%27"+dc+"%27&SDT=%27"+sd+"%27&MSSV=%27"+us+"%27&password=%27"+pa+"%27";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String idkh) {
                            if(idkh!=null)
                            {
                                checkconnection.showToast_short(getApplicationContext(),"Tạo tài khoản thành công");
                                Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
                    ;
                    requestQueue.add(stringRequest);
    }

    private void eventBtnXacnhan() {
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtuser.getText().length()>0&&edtpass.getText().length()>0&&edtDiachi.getText().length()>0&&edtpass2.getText().length()>0&&edtSDT.getText().length()>0&&edtTen.getText().length()>0)
                {
                    if(edtpass.getText().toString().trim().equals(edtpass2.getText().toString().trim()))
                    {
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        String url = server.checkuser+"?username="+edtuser.getText().toString().trim()+"&sdt="+edtSDT.getText().toString();
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("TAG",response);
                                if(response.trim().equals("11")){
                                    checkconnection.showToast_short(getApplicationContext(),"Tài khoản và số điện thoại đã tồn tại");
                                } else if(response.trim().equals("10")) {
                                    checkconnection.showToast_short(getApplicationContext(),"Tài khoản đã tồn tại");
                                }else if(response.trim().equals("01")) {
                                    checkconnection.showToast_short(getApplicationContext(), "Số điện thoại đã tồn tại");
                                }  else if(response.trim().equals("00")){
                                    opendiaalogcthd();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Không thể kiểm tra được dữ liệu",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }); queue.add(stringRequest);


                    } else {
                        checkconnection.showToast_short(getApplicationContext(),"Mật khẩu không khớp");
                    }
                } else {
                    checkconnection.showToast_short(getApplicationContext(), "Hãy nhập đầy đủ dữ liệu");
                }
            }
       });
    }
    @SuppressLint("ResourceAsColor")
    private void opendiaalogcthd() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_verify_dialog);
        Window window = dialog.getWindow();
        if(window == null ){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(R.color.transfer));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        dialog.setCancelable(true);
        edtverifycode = dialog.findViewById(R.id.edtVerify);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width)/8, (2 * height)/7);
        dialog.show();
        String phone;
        if(edtSDT.getText().length()==9)
        {
            phone = "+84"+edtSDT.getText().toString();
            phoneNumber = "0"+edtSDT.getText().toString();
        }
        else
        {
            String sdt = edtSDT.getText().toString().substring(1,10);
            phone = "+84"+sdt;
            phoneNumber = "0"+sdt;
        }
        Log.e("phone",phone);
        sendVerificationCode(phone);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KiemTraCode();

            }
        });
    }
    private void eventtrove() {
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void KiemTraCode() {
        PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(code,edtverifycode.getText().toString());
        FirebaseAuth.getInstance().signInWithCredential(authCredential).addOnCompleteListener(ThongTinNguoiMuaActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getApplicationContext(),"Xác nhận thành công",Toast.LENGTH_SHORT).show();
                themkh();
            }
        }).addOnFailureListener(ThongTinNguoiMuaActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Xác nhận thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendVerificationCode(String mobile) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks);
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            code = s;
            token = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.e("lỗi ", e.getMessage() );
        }
    };

    private void anhxa() {
        btnXacnhan = findViewById(R.id.btnXacNhan);
        btnTrove = findViewById(R.id.btnTroVe);
        edtTen = findViewById(R.id.edtTen);
        edtDiachi = findViewById(R.id.edtDiaChi);
        edtSDT = findViewById(R.id.edtSDT);
        edtuser = findViewById(R.id.edtdkusername);
        edtpass =  (TextInputEditText) findViewById(R.id.edtdkpassword);
        edtpass2 = (TextInputEditText) findViewById(R.id.edtdkpassword2);
    }
}