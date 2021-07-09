package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.adapter.ProductAdapter;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.util.checkconnection;
import com.example.appbanhang.util.server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbanhang.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanPhamActivity extends AppCompatActivity {
    ListView listView;
    int idsp = 0;
    int page =1;
    public static int SL;
    View footer;
    public static TextView txtSL;
    Boolean isLoading = false, limitdata = false;
    mHandler mHandler;
    ProductAdapter productAdapter;
    EditText edtMax, edtMin, edtSearch;
    Button btnSearch, btnFilter;
    ArrayList<sanpham> arrayList;
    String max="", min="", text="";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(checkconnection.havenetworkconnect(getApplicationContext())){
            anhxa();
            getidloaisp();
            getdata(page,max,min,text);
            getmoredate();
        } else {
            checkconnection.showToast_short(getApplicationContext(),"Hãy kiểm tra kết nối mạng");
            finish();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                max=edtMax.getText().toString();
                min=edtMin.getText().toString();
                edtSearch.setText("");
                text = edtSearch.getText().toString();
                arrayList.clear();
                productAdapter.notifyDataSetChanged();
                page=1;
                isLoading = false;
                limitdata = false;
                getdata(page,max,min,text);
                getmoredate();

            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMax.setText("");
                max=edtMax.getText().toString();
                edtMin.setText("");
                min=edtMin.getText().toString();
                text = edtSearch.getText().toString();
                arrayList.clear();
                page=1;
                isLoading = false;
                limitdata = false;
                productAdapter.notifyDataSetChanged();
                getdata(page,max,min,text);
                getmoredate();
            }
        });
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),com.example.appbanhang.activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void getmoredate() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsp",arrayList.get(position));
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem == totalItem && totalItem!=0 && isLoading == false && limitdata == false )
                {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();

                }

            }
        });
    }

    private void getdata(int Page, String max, String min, String text) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = server.duongdansp+String.valueOf(Page)+"&MAX="+max+"&MIN="+min+"&text="+text;
        Log.e("TAG",link);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG",response);
                int id = 0,gia=0,tinhtrang=0;
                String ten="",hacom="";
                Boolean ttcom = true;
                if(response != null && response.length()!=2 )
                {
                    listView.removeFooterView(footer);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("IdSP");
                            hacom = jsonObject.getString("HAnhSP");
                            ten = jsonObject.getString("TenSP");
                            gia = jsonObject.getInt("GiaSP");
                            idsp = jsonObject.getInt("IdLoaiSP");
                            tinhtrang = jsonObject.getInt("TTrangSP");
                            if(tinhtrang == 1)
                                ttcom = true;
                            else
                                ttcom = false;
                            arrayList.add(new sanpham(id,ten,hacom,gia,idsp,"",ttcom));
                            productAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {

                    }
                } else {
                    limitdata = true;
                    listView.removeFooterView(footer);
                    checkconnection.showToast_short(getApplicationContext(),"Hết dữ liệu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param =new HashMap<String, String>();
                param.put("IdLoaiSP",String.valueOf(idsp));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void getidloaisp() {
        idsp = getIntent().getIntExtra("IdLoaiSP",-1);
    }

    public static void reloaiSL() {
        SL =0;
        for(int i=0;i<MainActivity.manggiohang.size();i++)
        {
            SL+=MainActivity.manggiohang.get(i).getSl();
        }
        try{ txtSL.setText(SL+"");}catch (Exception e){}


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void anhxa() {
        listView = (ListView)findViewById(R.id.lvProduct);
        arrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(),arrayList);
        listView.setAdapter(productAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footer = inflater.inflate(R.layout.processbar,null);
        mHandler = new mHandler();
        txtSL = findViewById(R.id.txtSLGIOHANG);
        reloaiSL();
        edtMax = findViewById(R.id.edtMax);
        edtMin = findViewById(R.id.edtMin);
        edtSearch =findViewById(R.id.edtSearch);
        btnFilter = (Button) findViewById(R.id.btnfilter);
        btnSearch = (Button) findViewById(R.id.btnSearch);

    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listView.addFooterView(footer);
                    break;
                case 1:
                    getdata(++page, max, min,text);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}