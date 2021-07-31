package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.model.giohang;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.util.server;
import com.squareup.picasso.Picasso;
import com.example.appbanhang.util.checkconnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyPermission;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChiTietSanPham extends AppCompatActivity {
    Button btnThemVaoGio,btnMuaNgay;
    Spinner spinner;
    EditText editTextSL;
    CircleImageView imvCTSP;
    TextView txtTen,txtGia,txtTT;
    int idloai;
    EditText edtGhiChu;
    int id = 0, giasp=0;
    String tensp="",hasp="";
    Boolean ttsp=true;
    ArrayList<sanpham> arrayList;
    LinearLayout linearLayout;
    ScrollView scrollView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhxa();
        getInfo();
        EventBtnThemVaoGio();
        EventMuaNgay();

    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    private void EventBtnThemVaoGio() {
        btnThemVaoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.KHang == null)
                {
                    Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                    startActivity(intent);
                    onBackPressed();
                } else {
                    if(editTextSL.getText().toString()!=null && Integer.parseInt(editTextSL.getText().toString())>0 ){
                        EventThemVaoGio();
                        Toast.makeText(getApplicationContext(),"Đã thêm vào giỏ",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Hãy nhập số lượng",Toast.LENGTH_LONG).show();
                    }
                    }

                }


        });
    }
    private void EventMuaNgay() {
        btnMuaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.KHang == null)
                {
                    Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                    startActivity(intent);
                    onBackPressed();
                } else {
                    EventThemVaoGio();
                    Intent intent = new Intent(getApplicationContext(), com.example.appbanhang.activity.GioHang.class);
                    startActivity(intent);
                }
            }
        });

    }
    private void EventThemVaoGio() {
                if(MainActivity.manggiohang.size()>0)
                {
                    int sl = Integer.parseInt(editTextSL.getText().toString());
                    Boolean exit = false;
                    for (int i =0;i<MainActivity.manggiohang.size();i++)
                    {
                        if(MainActivity.manggiohang.get(i).getId()==id && MainActivity.manggiohang.get(i).getTen().equals(tensp)){
                            MainActivity.manggiohang.get(i).setSl(MainActivity.manggiohang.get(i).getSl()+sl);
                            MainActivity.manggiohang.get(i).setGia(giasp*MainActivity.manggiohang.get(i).getSl());
                            exit = true;
                        }
                    }
                    if(exit ==false)
                    {
                        int soluong = Integer.parseInt(editTextSL.getText().toString());
                        int giact = soluong* giasp;
                        MainActivity.manggiohang.add(new giohang(id,giact,soluong,tensp,hasp,edtGhiChu.getText().toString(),ttsp));
                    }

                }else {
                    int sl = Integer.parseInt(editTextSL.getText().toString());
                    int giamoi = sl* giasp;
                    MainActivity.manggiohang.add(new giohang(id,giamoi,sl,tensp,hasp,edtGhiChu.getText().toString(),ttsp));
                }
                try{
                    MainActivity.reloaiSL();
                    SanPhamActivity.reloaiSL();
                }catch (Exception e){

                }

    }
    @SuppressLint("ResourceAsColor")
    private void getInfo() {
        sanpham sp = (sanpham) getIntent().getSerializableExtra("thongtinsp");
        id = sp.getId();
        tensp = sp.getTensp();
        giasp = sp.getGiasp();
        hasp = sp.getHinhanhsp();
        ttsp = sp.isTinhtrang();
        txtTen.setText(tensp);
        idloai = sp.getIdsp();

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá: "+decimalFormat.format(giasp)+"VND");
        if(ttsp == true )
        {
            txtTT.setText("Còn hàng");
            btnThemVaoGio.setEnabled(true);
            edtGhiChu.setEnabled(true);
        }
        else
        {
            txtTT.setText("Tạm hết");
            txtTT.setTextColor(R.color.red);
            btnThemVaoGio.setEnabled(false);
            edtGhiChu.setEnabled(false);
            btnMuaNgay.setEnabled(false);
        }
        Picasso.get().load(server.localhost+hasp).placeholder(R.drawable.loading).error(R.drawable.nosignal).into(imvCTSP);
        if (idloai == 1){
            getdatemonanthem();
        }
    }
    private  void getdatemonanthem(){
        arrayList = new ArrayList<>();
        scrollView.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = server.localhost + "getdoanthem.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TUS",response);
                String tenmat="";
                int giamat = 0;
                if(response != null && response.length()!=2 )
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            tenmat = jsonObject.getString("TenSP");
                            giamat = jsonObject.getInt("GiaSP");
                            arrayList.add(new sanpham(i,tenmat,"",giamat,0,"",true));

                        }
                        Log.d("TUS",arrayList.size()+"");
                        for (int i=0;i<arrayList.size();i++)
                        {
                            CheckBox checkBox = new CheckBox(getApplicationContext());
                            String ten = arrayList.get(i).getTensp();
                            Integer gia = arrayList.get(i).getGiasp();
                            checkBox.setText(ten+" : "+gia);
                            linearLayout.addView(checkBox);
                            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked){
                                        tensp+= " + "+ten;
                                        giasp+=gia;
                                        txtTen.setText(tensp);
                                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                        txtGia.setText("Giá: "+decimalFormat.format(giasp)+"VND");
                                    }
                                    else {
                                        int vitri = tensp.lastIndexOf(" + ");
                                        tensp = tensp.substring(0,vitri);
                                        giasp-=gia;
                                        txtTen.setText(tensp);
                                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                        txtGia.setText("Giá: "+decimalFormat.format(giasp)+"VND");
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TUS",error.toString());
            }
        });
        requestQueue.add(stringRequest);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void anhxa() {
        imvCTSP =  findViewById(R.id.imvctsp);
        btnThemVaoGio = (Button) findViewById(R.id.btthemvaogio);
        txtTen = (TextView) findViewById(R.id.txtTen);
        txtGia = (TextView) findViewById(R.id.txtGia);
        txtTT = (TextView) findViewById(R.id.txtTT);
        editTextSL = findViewById(R.id.edtSL);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        btnMuaNgay = (Button) findViewById(R.id.btmuangay);
        linearLayout = findViewById(R.id.lnThemDoAn);
        scrollView = findViewById(R.id.scrollView);
    }
}