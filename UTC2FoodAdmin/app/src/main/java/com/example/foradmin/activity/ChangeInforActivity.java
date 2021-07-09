package com.example.foradmin.activity;

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
import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangeInforActivity extends AppCompatActivity {
TextInputEditText editTextusername, editTextPass1,editTextPass2, editTextsdt, editTextMerCode, editTextMerName;
Button btnConfirm,btnCancel;
String sodienthoai,merchancode,merchantName,password,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_infor);
        editTextMerCode = findViewById(R.id.edtmerchancodeCHANGE);
        editTextMerName = findViewById(R.id.edtmerchantNameCHANGE);
        editTextPass1 = findViewById(R.id.edtdCHANGEpass);
        editTextPass2 = findViewById(R.id.edtdCHANGEpass2);
        editTextusername = findViewById(R.id.edtusernameCHANGE);
        editTextsdt = findViewById(R.id.edtSDTCHANGE);
        btnConfirm = findViewById(R.id.btnXacNhanCHANGE);
        btnCancel = findViewById(R.id.btnTroVeCHANGE);
        getdulieu();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextPass1.getText().toString().trim().equals(editTextPass2.getText().toString().trim())){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = APIUtils.Base_Url+"updateadmin.php?username="+editTextusername.getText().toString()
                            +"&password="+editTextPass1.getText().toString()+"&merchancode="+editTextMerCode.getText().toString()
                            +"&merchantName="+editTextMerName.getText().toString()+"&sdt="+editTextsdt.getText().toString();
                    Log.e("Tag",url);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Tag",response);
                            if(response.equals("success")){
                                Toast.makeText(getApplicationContext(),"Đổi thông tin thành công",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),"Thất bại. Hãy thử lại",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(stringRequest);
                } else Toast.makeText(getApplicationContext(),"Mật khẩu nhập lại chưa đúng",Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void getdulieu(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = APIUtils.Base_Url+"getmerchantCode.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length()>2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            username= jsonObject.getString("username");
                            password = jsonObject.getString("password");
                            merchancode = jsonObject.getString("merchancode");
                            merchantName = jsonObject.getString("merchantName");
                            sodienthoai = jsonObject.getString("sdt");
                        }
                        editTextusername.setText(username);
                        editTextMerCode.setText(merchancode);
                        editTextMerName.setText(merchantName);
                        editTextPass1.setText(password);
                        editTextPass2.setText(password);
                        editTextsdt.setText(sodienthoai);
                        // sendVerificationCode(phone);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}