package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.appbanhang.model.ChiTietHoaDon;
import com.example.appbanhang.util.server;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.util.checkconnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.giohangadapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.remote.TokenResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;

public class GioHang extends AppCompatActivity {
    ListView lvgiohang;
    static TextView txtTongtien;
    TextView txtThongBao;
    Button btnThanhToan,btnMuaTiep,btnDelete,btnPaymentMoMo,btnMappingMoMo,btnHuyDiaLog;
    giohangadapter giohangAdapter;
    int mahoadon=0;
    String pttt="",tvMessage="";
    static int tien =0;
    int ttt=0;
    Spinner spinner;
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "";
    private String merchantCode = "";
    private String merchantNameLabel = "";
    private String description = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhxa();
        checkdata();
        evenutil();
        evenBtn();
    }

    private void getMoMoinfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = server.localhost+"getmerchantCode.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            merchantCode = jsonObject.getString("merchancode");
                            merchantName = jsonObject.getString("merchantName");
                            merchantNameLabel = merchantName;
                            requestPayment();
                        }

                    } catch (JSONException e) {

                    }
                } else {

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {

                    tvMessage="Thanh toán thành công";
                    ThemHoaDon();
                    if(data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {

                    } else {
                        tvMessage=this.getString(R.string.not_receive_info);
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    tvMessage=message;
                } else if(data.getIntExtra("status", -1) == 2) {
                    tvMessage=this.getString(R.string.not_receive_info);
                } else {
                    tvMessage= this.getString(R.string.not_receive_info);
                }
            } else {
                tvMessage= this.getString(R.string.not_receive_info);
            }
        } else {
            tvMessage= this.getString(R.string.not_receive_info_err);
        }
        Toast.makeText(getApplicationContext(),tvMessage,Toast.LENGTH_LONG).show();
    }
    private void requestPayment() {
        for(int i=0;i<MainActivity.manggiohang.size();i++){
            if(i==0){
                description = MainActivity.manggiohang.get(0).getTen();
            } else description+= ", "+MainActivity.manggiohang.get(i).getTen();

        }
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, merchantName);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE, merchantCode);
        eventValue.put(MoMoParameterNamePayment.AMOUNT, tien);
        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, description);
        eventValue.put(MoMoParameterNamePayment.FEE, fee);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, merchantNameLabel);
        eventValue.put(MoMoParameterNamePayment.REQUEST_ID,  UUID.randomUUID().toString());
        eventValue.put(MoMoParameterNamePayment.PARTNER_CODE, merchantCode);

        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", merchantNameLabel);
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
            objExtraData.put("ticket", "{\"ticket\":{\"01\":{\"type\":\"std\",\"price\":"+tien+",\"qty\":3}}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put(MoMoParameterNamePayment.EXTRA_DATA, objExtraData.toString());
        eventValue.put(MoMoParameterNamePayment.REQUEST_TYPE, "payment");
        eventValue.put(MoMoParameterNamePayment.LANGUAGE, "vi");
        eventValue.put(MoMoParameterNamePayment.EXTRA, "");
        //Request momo app
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }
    private void ThemHoaDon(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        java.util.Date date=new java.util.Date();
        String tgd = date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        String ngay = (date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate();
        pttt = spinner.getSelectedItem().toString();
        String url = server.themhoadon+"?MSSV=%27"+MainActivity.KHang.getMssv()+"%27&TongTien="+tien+
                "&ThoiGianDat=%27"+tgd+"%27&Ngay=%27"+ngay+"%27&TTThanhToan="+ttt+"&PTThanhToan=%27"+pttt+"%27";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String idhd) {
                try {
                    mahoadon=Integer.parseInt(idhd);
                    eventThemCTHD();
                } catch (Exception e){

                }


            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkconnection.showToast_short(getApplicationContext(),"Thêm hóa đơn thất bại");
            }});
        requestQueue.add(stringRequest);
        String link = server.localhost+"sendnotification.php?name="+MainActivity.KHang.getTen()+"";
        Log.e("TAG",link);
        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String idhd) {
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }});
        requestQueue.add(request);

    }
    private void evenBtn() {
        btnMuaTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
              if(MainActivity.manggiohang.size()>0)
              {
                  if(spinner.getSelectedItemPosition() == 0)
                  {
                      getMoMoinfo();
                  }
                  else{
                      ThemHoaDon();

                  }
              } else { checkconnection.showToast_short(getApplicationContext(),"Giỏ hàng trống. Hãy thêm sản phẩm"); }
           }});
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    private void eventThemCTHD() {
        int idsp=0,sl=0,gia=0;
        String ghichu="",tensp="";

        for(int i=0;i<MainActivity.manggiohang.size();i++)
        {
            idsp = MainActivity.manggiohang.get(i).getId();
            ghichu = MainActivity.manggiohang.get(i).getGhichu();
            sl = MainActivity.manggiohang.get(i).getSl();
            gia = MainActivity.manggiohang.get(i).getGia();
            tensp = MainActivity.manggiohang.get(i).getTen();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url = server.themcthoadon+"?IdHD="+mahoadon+
                    "&IdSP="+idsp+"&GhiChu='"+ghichu+"'&SoLuong="+sl+"&Gia="+gia+"&TenSP='"+tensp+"'";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(stringRequest);
        }


        MainActivity.manggiohang.clear();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                checkconnection.showToast_short(getApplicationContext(),"Bạn đã đặt hàng thành công");
                Intent intent = new Intent(getApplicationContext(),DatHangThanhCongActivity.class);
                intent.putExtra("idhd", mahoadon);
                intent.putExtra("tongtien",tien);
                startActivity(intent);
                finish();
            }
        }, 1000);

    }
    public static void evenutil() {
        int tongtien =0;
        for(int i=0;i<MainActivity.manggiohang.size();i++)
        {
            tongtien+=MainActivity.manggiohang.get(i).getGia();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien)+"VND");
        tien = tongtien;
    }
    private void checkdata() {
            if(MainActivity.manggiohang.size()<=0){
                giohangAdapter.notifyDataSetChanged();
                txtThongBao.setVisibility(View.VISIBLE);
                lvgiohang.setVisibility(View.INVISIBLE);
            } else {
                giohangAdapter.notifyDataSetChanged();
                txtThongBao.setVisibility(View.INVISIBLE);
                lvgiohang.setVisibility(View.VISIBLE);
            }
    }
    private void anhxa() {
        spinner = findViewById(R.id.Spinnerpttt);
        String[] mang = new String[]{ "Qua ví momo" , "Thanh toán khi nhận hàng"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mang);
        spinner.setAdapter(arrayAdapter);
        lvgiohang = (ListView) findViewById(R.id.lvgiohang);
        txtTongtien = (TextView) findViewById(R.id.txtTongTien);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToan);
        btnMuaTiep = (Button) findViewById(R.id.btnMuaTiep);
        txtThongBao = (TextView) findViewById(R.id.txtThongBao);
        giohangAdapter = new giohangadapter(GioHang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);
        btnDelete = (Button) findViewById(R.id.btnDelete);
    }
}