package com.example.foradmin.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.notification.FirebaseMessagingService;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText txtUsername,txtPass,txtverify;
    TextView btnForgetpass;
    Button btnLogin,btnConfirm,btnCancel;
    String phoneNumber,code,sodienthoai="";
    PhoneAuthProvider.ForceResendingToken token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseMessaging.getInstance().subscribeToTopic("test");
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        Log.d("TAG", task.getResult().toString());
//                    }
//                });
//        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
//            @Override
//            public void onSuccess(String s) {
//            }
//        });
//        FirebaseMessaging.getInstance().getToken().addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull @NotNull Exception e) {
//
//            }
//        });
        addView();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().toString().trim().equals("")||txtPass.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Hãy nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                } else {
                    checkadmin();
                }

            }
        });
        btnForgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = APIUtils.Base_Url+"getmerchantCode.php";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Tag",response);
                        if(response.length()>2) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    sodienthoai = jsonObject.getString("sdt");
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
//                                Log.e("TAG",sodienthoai+"");
//                                Intent intent = new Intent(MainActivity.this,ForgetPasswordActivity.class);
//                                startActivity(intent);
//                                finish();
                                sendVerificationCode(phone);
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
                opendiaalogcthd();
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    private void opendiaalogcthd() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        Window window = dialog.getWindow();
        if(window == null ){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(R.color.transfer));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        dialog.setCancelable(true);
        txtverify = dialog.findViewById(R.id.edtVerify);
        btnConfirm = dialog.findViewById(R.id.btnConfirmOTP);
        btnCancel = dialog.findViewById(R.id.btnCancelOTP);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((7 * width)/8, (2 * height)/9);
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
                KiemTraCode();
                dialog.dismiss();
            }
        });
    }
    private void addView() {
        btnLogin = findViewById(R.id.btnLogin);
        txtPass = findViewById(R.id.txtPass);
        txtUsername = findViewById(R.id.txtUsername);
        btnForgetpass = findViewById(R.id.btnForgetpass);
    }
    private void KiemTraCode() {
        PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(code,txtverify.getText().toString());
        FirebaseAuth.getInstance().signInWithCredential(authCredential).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getApplicationContext(),"Xác nhận thành công",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
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

    private  void checkadmin()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = APIUtils.Base_Url +"checkadmin.php?username="+txtUsername.getText().toString().trim()+"&password="+txtPass.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", response);
                if(Integer.parseInt(response.trim())==1)
                {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Thông tin chưa chính xác. Vui lòng thử lại",Toast.LENGTH_SHORT).show();
                    txtUsername.setText(""); txtPass.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.toString());
            }
        });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(stringRequest);

    }
}