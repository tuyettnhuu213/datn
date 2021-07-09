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

public class ForgetPasswordActivity extends AppCompatActivity {
TextInputEditText editText1, editText2;
Button btnConfirm, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        editText1 = findViewById(R.id.txtpass1);
        editText2 = findViewById(R.id.txtpass2);
        btnConfirm = findViewById(R.id.btnXacNhanMKCHANGE);
        btnCancel= findViewById(R.id.btnTroVeMKCHANGE);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventbtnConfirm();
            }
        });


    }

    private void eventbtnConfirm() {
        if(editText1.getText().toString().trim().equals(editText2.getText().toString().trim())){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url = APIUtils.Base_Url+"updateadmin.php?password="+editText1.getText().toString().trim();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Tag",response);
                    if(response.equals("success2")){
                        Toast.makeText(getApplicationContext(),"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
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
        }
        else{

        }
    }
}