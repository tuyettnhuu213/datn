package com.example.appbanhang.activity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.util.server;
import com.example.appbanhang.util.checkconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.model.khachhang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class DangNhapActivity extends AppCompatActivity {
Button btnDangnhap,btnQuaylai,btnDangki,btnQuenMatKhau,btnConfirm,btnCancel,btnConfirmTenTK, btnCancelTenTK;
TextInputEditText edtUsername,edtPassword;
int idkh = 0,dtl=0;
EditText editTenTK,edtverifycode;
String tenkh = "", diachi = "", sdt = "", username = "", password = "",sodienthoai="";
ImageView imvdangnhap;
String phoneNumber,code;
PhoneAuthProvider.ForceResendingToken token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhxa();
        eventBtnQuaylai();
        eventBtnDangki();
        eventBtnDangNhap();
        eventBtnQuenMatKhau();
        String phoneNumber,code;
        PhoneAuthProvider.ForceResendingToken token;

    }
    @SuppressLint("ResourceAsColor")
    private void opendiaalogcthd(String mssv) {
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KiemTraCode(mssv);

            }
        });
    }
    @SuppressLint("ResourceAsColor")
    private void opendiaalognhaptentaikhoan() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_timmatkhau);
        Window window = dialog.getWindow();
        if(window == null ){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(R.color.transfer));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        dialog.setCancelable(true);
        editTenTK = dialog.findViewById(R.id.edtTimTaiKhoan);
        btnConfirmTenTK = dialog.findViewById(R.id.btnConfirmTenTK);
        btnCancelTenTK = dialog.findViewById(R.id.btnCancelTenTK);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width)/8, (2 * height)/7);
        dialog.show();

        btnCancelTenTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirmTenTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = server.checkuser+"?username="+editTenTK.getText().toString().trim();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("false")) {
                            checkconnection.showToast_short(getApplicationContext(), "Tài khoản chưa tồn tại, vui lòng kiểm tra lại");
                        } else if(response.length()>2) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    sodienthoai = jsonObject.getString("SDT");
                                }
                                String phone;
                                if(sodienthoai.length()==9)
                                {
                                    phone = "+84"+sodienthoai;
                                    phoneNumber = "0"+sodienthoai;
                                }
                                else
                                {
                                    String sdtfinal = sodienthoai.substring(1,10);
                                    phone = "+84"+sdtfinal;
                                    phoneNumber = "0"+sdtfinal;
                                }
                                sendVerificationCode(phone);
                                dialog.dismiss();
                                opendiaalogcthd(editTenTK.getText().toString().trim());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(stringRequest);

            }
        });
    }
    private void KiemTraCode(String mssv) {
        PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(code,edtverifycode.getText().toString());
        FirebaseAuth.getInstance().signInWithCredential(authCredential).addOnCompleteListener(DangNhapActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getApplicationContext(),"Xác nhận thành công",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),QuenMatKhauActivity.class);
                intent.putExtra("username",mssv);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(DangNhapActivity.this, new OnFailureListener() {
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


    private void eventBtnQuenMatKhau() {
        btnQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendiaalognhaptentaikhoan();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
    private void eventBtnDangNhap() {
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = server.getkhachhang+"?username="+edtUsername.getText().toString().trim()+"&password="+edtPassword.getText().toString().trim();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("false")) {
                            checkconnection.showToast_short(getApplicationContext(), "Sai mật khẩu hoặc tài khoản, vui lòng kiểm tra lại");
                        } else {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++){
                                    //function khachhang($MSSV,$TenKH,$DiaChi,$SDT,$DTL,$password)
                                    //	{
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    username = jsonObject.getString("MSSV");
                                    tenkh = jsonObject.getString("TenKH");
                                    diachi = jsonObject.getString("DiaChi");
                                    sdt = jsonObject.getString("SDT");
                                    dtl = jsonObject.getInt("DTL");
                                    password = jsonObject.getString("password");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            MainActivity.KHang = new khachhang(username,dtl,tenkh,sdt,diachi,password);
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            MainActivity.reloaiSL();
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(stringRequest);
            }
        });
    }
    private void eventBtnDangki() {
        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ThongTinNguoiMuaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void eventBtnQuaylai() {
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        btnDangnhap = findViewById(R.id.btnDangNhap);
        btnQuaylai = findViewById(R.id.btnTrove);
        btnDangki = findViewById(R.id.btnDangKi);
        imvdangnhap = findViewById(R.id.imvdangnhap);
        edtUsername = findViewById(R.id.edtusername);
        edtPassword = findViewById(R.id.edtpassword);
        btnQuenMatKhau = findViewById(R.id.btnQuenMatKhau);
    }
}