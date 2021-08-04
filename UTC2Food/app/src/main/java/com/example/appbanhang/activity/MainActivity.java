package com.example.appbanhang.activity;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LoaiSPAdapter;
import com.example.appbanhang.adapter.ProductAdapter;
import com.example.appbanhang.adapter.listviewmanhinhAdapter;
import com.example.appbanhang.adapter.sanphamadapter;
import com.example.appbanhang.model.Loaisp;
import com.example.appbanhang.model.giohang;
import com.example.appbanhang.model.khachhang;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.model.listviewmanhinh;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.example.appbanhang.util.checkconnection;
import com.example.appbanhang.util.server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static TextView txtSLGIO;
    Toolbar toolbar;
    NavigationView navigationView;
    ListView listView, lvTimKiem;
    DrawerLayout drawerLayout;
    ViewFlipper viewFlipper;
    RecyclerView rcvLoaiSP, recyclerView;
    ArrayList<Loaisp> mangloaisp;
    LoaiSPAdapter loaispAdapter;
    ArrayList<sanpham> mangsp, mangtimkiem;
    ArrayList<listviewmanhinh> arrayListUser;
    listviewmanhinhAdapter arrayAdapter;
    AutoCompleteTextView edtTimKiem;
    sanphamadapter sanphamadapter;
    ProductAdapter timkiem;
    int id = 0;
    LinearLayout lin;
    Button search;
    String tenloaisp;
    String hinhanhloaisp;
    public static ArrayList<giohang> manggiohang;
    public static khachhang KHang;
    FloatingActionButton fab ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        if (checkconnection.havenetworkconnect(getApplicationContext())) {
            actionbar();
            mangtimkiem.clear();
            timkiem();
            getmoredate();
            actionviewflipper();
            getloaisp();
            getspmoi();
            catchonitemlistview();

        } else {
            checkconnection.showToast_short(getApplicationContext(), "Hãy kiểm tra kết nối mạng");
            finish();
        }

    }

    private void getmoredate() {
        lvTimKiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsp", mangtimkiem.get(position));
                startActivity(intent);
            }
        });
    }

    private void getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = server.timkiemsp;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idcom = 0, giacom = 0, tinhtrang = 0, idsp = 0;
                String tencom = "", motacom = "", hacom = "";
                Boolean ttcom = true;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //	function sanpham($IdSP,$HAnhSP,$TenSP,$GiaSP,$TTrangSP,$IdLoaiSP){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idcom = jsonObject.getInt("IdSP");
                            hacom = jsonObject.getString("HAnhSP");
                            tencom = jsonObject.getString("TenSP");
                            giacom = jsonObject.getInt("GiaSP");
                            idsp = jsonObject.getInt("IdLoaiSP");
                            tinhtrang = jsonObject.getInt("TTrangSP");
                            if (tinhtrang == 1)
                                ttcom = true;
                            else
                                ttcom = false;
                            mangtimkiem.add(new sanpham(idcom, tencom, hacom, giacom, idsp, motacom, ttcom));
                            timkiem.notifyDataSetChanged();
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("text", String.valueOf(edtTimKiem.getText().toString()));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void timkiem() {
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (edtTimKiem.getText().length() == 0 && edtTimKiem.getText().toString().trim().equals("")) {
                    lin.setVisibility(View.INVISIBLE);
                    mangtimkiem.clear();
                } else mangtimkiem.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtTimKiem.getText().length() == 0 || edtTimKiem.getText().toString().trim().equals("")) {
                    lin.setVisibility(View.INVISIBLE);
                    mangtimkiem.clear();
                } else {
                    lin.setVisibility(View.VISIBLE);
                    mangtimkiem.clear();
                    getdata();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtTimKiem.getText().length() == 0 || edtTimKiem.getText().toString().trim().equals("")) {
                    lin.setVisibility(View.INVISIBLE);
                    mangtimkiem.clear();

                } else {
                    mangtimkiem.clear();
                }

            }
        });
    }

    private void catchonitemlistview() {
        if (KHang == null) {
            arrayListUser.add(new listviewmanhinh(R.drawable.user, "Đăng nhập"));
        } else {
            arrayListUser.add(new listviewmanhinh(R.drawable.hi, "Xin chào " + KHang.getTen()));
            arrayListUser.add(new listviewmanhinh(R.drawable.info, "Thay đổi thông tin"));
            arrayListUser.add(new listviewmanhinh(R.drawable.history, "Đơn hàng đã mua"));
            arrayListUser.add(new listviewmanhinh(R.drawable.key, "Đổi mật khẩu"));
            arrayListUser.add(new listviewmanhinh(R.drawable.logout, "Đăng xuất"));
        }
        arrayAdapter = new listviewmanhinhAdapter(arrayListUser, getApplicationContext());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (KHang == null) {
                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                    startActivity(intent);
                } else {
                    switch (position) {
                        case 1:
                            Intent userinfor = new Intent(getApplicationContext(), CaNhanActivity.class);
                            startActivity(userinfor);
                            break;
                        case 2:
                            Intent personalorder = new Intent(getApplicationContext(), LichSuActivity.class);
                            startActivity(personalorder);
                            break;
                        case 3:
                            Intent changepasss = new Intent(getApplicationContext(), DoiMatKhauActivity.class);
                            startActivity(changepasss);
                            break;
                        case 4:
                            KHang = null;
                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            break;
                    }

                }
            }
        });
    }

    private void getspmoi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.duongdanspmoi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Log.d("TAG",response.toString());
                    int id = 0, idsp = 0, giasp = 0;
                    String tensp = "", hasp = "", mota = "";
                    int tinhtrang = 0;
                    boolean tt = true;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("IdSP");
                            hasp = jsonObject.getString("HAnhSP");
                            tensp = jsonObject.getString("TenSP");
                            giasp = jsonObject.getInt("GiaSP");
                            idsp = jsonObject.getInt("IdLoaiSP");
                            tinhtrang = jsonObject.getInt("TTrangSP");
                            if (tinhtrang == 1)
                                tt = true;
                            else
                                tt = false;

                            mangsp.add(new sanpham(id, tensp, hasp, giasp, idsp, mota, tt));
                            sanphamadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("IdLoaiSP");
                            hinhanhloaisp = jsonObject.getString("HAnhLoaiSP");
                            tenloaisp = jsonObject.getString("TenLoaiSP");
                            if (id != 0) {
                                mangloaisp.add(new Loaisp(id, tenloaisp, hinhanhloaisp));
                                loaispAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkconnection.showToast_short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionviewflipper() {
        ArrayList<String> mangqcao = new ArrayList<>();
        mangqcao.add(server.localhost + "/image/quangcao1.jpg");
        mangqcao.add(server.localhost + "/image/quangcao2.jpg");
        mangqcao.add(server.localhost + "/image/quangcao3.jpg");
        mangqcao.add(server.localhost + "/image/quangcao4.jpg");
        for (int i = 0; i < mangqcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangqcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void anhxa() {

        toolbar = findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listView = (ListView) findViewById(R.id.listviewmanhinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipperquangcao);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        search = findViewById(R.id.imvSearch);
        edtTimKiem = findViewById(R.id.edttimkiem);
        lvTimKiem = findViewById(R.id.lvTimKiem);
        lin = findViewById(R.id.lin);
        mangloaisp = new ArrayList<>();
        loaispAdapter = new LoaiSPAdapter(getApplicationContext(),mangloaisp);
        rcvLoaiSP = findViewById(R.id.rcvLoaiSP);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvLoaiSP.setLayoutManager(linearLayoutManager);
        rcvLoaiSP.setAdapter(loaispAdapter);
        arrayListUser = new ArrayList<>();
//        mangloaisp.add(0,new Loaisp(0,"Trang chủ","https://i.pinimg.com/originals/d7/8e/13/d78e13e00411a2b701cc20c4196e9b96.png"));
//        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
//        .setAdapter(loaispAdapter);

        mangtimkiem = new ArrayList<>();
        timkiem = new ProductAdapter(getApplicationContext(),mangtimkiem);
        lvTimKiem.setAdapter(timkiem);


        mangsp = new ArrayList<>();
        sanphamadapter = new sanphamadapter(getApplicationContext(),mangsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanphamadapter);
        if (manggiohang==null)
        {
            manggiohang = new ArrayList<>();
        }

        txtSLGIO = findViewById(R.id.txtSLGIOHANGmain);
        reloaiSL();
        fab = findViewById(R.id.fabmain);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(KHang == null){
                    Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(),GioHang.class);
                    startActivity(intent);
                }

            }
        });

    }
    public static void reloaiSL()
    {
        int SL =0;
        for(int i=0;i<MainActivity.manggiohang.size();i++)
        {
            SL+=MainActivity.manggiohang.get(i).getSl();
        }
        txtSLGIO.setText(SL+"");
    }
}