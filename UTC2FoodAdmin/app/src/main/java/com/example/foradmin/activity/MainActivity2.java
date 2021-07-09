package com.example.foradmin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.adapter.ChiTietHoaDonAdapter;
import com.example.foradmin.adapter.HDAdapter;
import com.example.foradmin.adapter.ItemMenuAdapter;
import com.example.foradmin.model.ItemMenu;
import com.example.foradmin.model.chitiethoadon;
import com.example.foradmin.model.hoadon;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class 
MainActivity2 extends AppCompatActivity {
ListView lvMenu;
Toolbar toolbarMain;
DrawerLayout drawerLayout;
ArrayList<ItemMenu> arrayListItemMenu;
NavigationView navigationView;
ItemMenuAdapter itemMenuAdapter;
ChiTietHoaDonAdapter chiTietHoaDonAdapter;
ListView lvDonHang,lvCTHD;
TextView txtTen,txtSDT,txtDiaChi,txtTongTien,txtPTTT,txtTTTT,txtTTHD,txtIdHD,txtMSSV,txtTgdat,txtTgnhan;
ArrayList<hoadon> arrayList;
ArrayAdapter<String> arrayAdapter;
Spinner spinner;
HDAdapter hoaDonAdapter;
int btnId;
TextView txtCXN,txtCDG, txtCGH,txtDG;
Button btn1,btn2,btn3,btn4,btnsave,btncancel;
ArrayList<chitiethoadon> arrayListCTHD;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        addViews();
        loadData(0,spinner.getSelectedItemPosition());
        actionToolbar();
        eventItemselected();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadData(btnId,spinner.getSelectedItemPosition());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadData(btnId,0);
            }
        });
    }
    private void getchitiethoadon(int IdHD){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = APIUtils.getcthoadon + "?IdHD=" + IdHD;
        Log.e("CTHD",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("CTHD",response);
                int gia = 0, sl = 0;
                String ten = "", ghichu = "",ha="";
                Boolean ttcom = true;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i <= jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ha = jsonObject.getString("HAnhSP");
                            ten = jsonObject.getString("TenSP");
                            gia = jsonObject.getInt("Gia");
                            sl = jsonObject.getInt("SoLuong");
                            ghichu = jsonObject.getString("GhiChu");
                            arrayListCTHD.add(new chitiethoadon(sl, gia, ten,ha, ghichu));
                            chiTietHoaDonAdapter.notifyDataSetChanged();
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

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void opendiaalogcthd(hoadon hd) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_chitiethoadon_dialog);
        Window window = dialog.getWindow();
        if(window == null ){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(R.color.transfer));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        dialog.setCancelable(true);
        txtTen = dialog.findViewById(R.id.txtTenCTHD);
        txtSDT= dialog.findViewById(R.id.txtSDTCTHD);
        txtDiaChi= dialog.findViewById(R.id.txtDiaChiCTHD);
        txtTongTien= dialog.findViewById(R.id.txtTongTienCTHD);
        txtPTTT= dialog.findViewById(R.id.txtPTThanhToanCTHD);
        txtTTTT= dialog.findViewById(R.id.txtTTThanhToanCTHD);
        txtTTHD= dialog.findViewById(R.id.txtTTCTHD);
        txtIdHD= dialog.findViewById(R.id.txtIdCTHD);
        txtMSSV= dialog.findViewById(R.id.txtMSSSCTHD);
        txtTgdat= dialog.findViewById(R.id.txtThoiGianDatCTHD);
        txtTgnhan= dialog.findViewById(R.id.txtThoiGianNhanCTHD);
        btnsave = dialog.findViewById(R.id.btnXacNhanCTHD);
        btncancel = dialog.findViewById(R.id.btnHuyCTHD);
        lvCTHD = dialog.findViewById(R.id.lvCTHD);
        arrayListCTHD = new ArrayList<>();
        chiTietHoaDonAdapter = new ChiTietHoaDonAdapter(getApplicationContext(),arrayListCTHD);
        lvCTHD.setAdapter(chiTietHoaDonAdapter);
        getchitiethoadon(hd.getIdhd());

        txtTen.setText("Tên: "+hd.getTenkh());
        txtSDT.setText("/Số điện thoại: "+hd.getSdt());
        txtDiaChi.setText("Địa chỉ: "+hd.getDiachi());
        txtPTTT.setText(hd.getPttt());
        if(hd.getTtthanhtoan()==0) { txtTTTT.setText("Chưa thanh toán"); } else { txtTTTT.setText("Đã thanh toán"); }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPTTT.setText(hd.getPttt()+"");
        txtTgdat.setText("Thời gian đặt: ngày " +hd.getNgay()+" lúc "+ hd.getTgdat());
        txtTgnhan.setText("Thời gian đặt: ngày " +hd.getNgay()+" lúc "+hd.getTgnhan());
        txtTongTien.setText(decimalFormat.format(hd.getTongtien())+"đ");
        if (hd.getTthoadon() == 0){txtTTHD.setText("Chờ xác nhận");}
        else if (hd.getTthoadon() == 1) {txtTTHD.setText("Chờ đóng gói");}
        else if (hd.getTthoadon() == 2) {txtTTHD.setText("Chờ giao hàng");}
        else { txtTTHD.setText("Đã giao hàng"); }
        txtIdHD.setText("Mã hóa đơn: "+hd.getIdhd());
        txtMSSV.setText("/Tài khoản: "+hd.getMssv());

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width)/7, (4 * height)/5);
        dialog.show();

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                int TTHD = hd.getTthoadon();
                String link = APIUtils.updatetthd+"?IdHD="+hd.getIdhd()+"&TinhTrangHD="+ TTHD;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ketqua",response);
                        if (response.equals("success"))
                        {
                             Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_LONG).show();
                             arrayList.clear();
                             hoaDonAdapter.notifyDataSetChanged();
                             loadData(TTHD,spinner.getSelectedItemPosition());
                             dialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_LONG).show();
                             dialog.dismiss();
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
    private void loadsl() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = APIUtils.getSLHD;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() != 2) {
                    try {
                        int sl0=0,sl1=0,sl2=0,sl3=0;
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            sl0 = jsonObject.getInt("SL0");
                            sl1 = jsonObject.getInt("SL1");
                            sl2 = jsonObject.getInt("SL2");
                            sl3 = jsonObject.getInt("SL3");
                        }
                        txtCXN.setText(sl0+"");
                        txtCDG.setText(sl1+"");
                        txtCGH.setText(sl2+"");
                        txtDG.setText(sl3+"");

                    } catch (JSONException e) {

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
    private void eventItemselected() {
        lvDonHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                opendiaalogcthd(arrayList.get(position));
            }
        });
    }
    private void getdulieu(int TinhTrangHD, Integer SapXep){
        arrayList.clear();
        hoaDonAdapter.notifyDataSetChanged();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = APIUtils.gethoadon+"?TinhTrangHD="+TinhTrangHD+"&SapXep="+SapXep;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() != 2) {
                    try {
                        int IdHD,TongTien,TinhTrangHD = 0,TTThanhToan;
                        String ThoiGianDat,ThoiGianNhan,Ngay,PTThanhToan,MSSV,TenKH,DiaChi,SDT;
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            IdHD = jsonObject.getInt("IdHD");
                            ThoiGianDat = jsonObject.getString("ThoiGianDat");
                            ThoiGianNhan = jsonObject.getString("ThoiGianNhan");
                            Ngay = jsonObject.getString("Ngay");
                            TongTien   = jsonObject.getInt("TongTien");
                            TinhTrangHD     = jsonObject.getInt("TinhTrangHD");
                            TTThanhToan       = jsonObject.getInt("TTThanhToan");
                            PTThanhToan = jsonObject.getString("PTThanhToan");
                            MSSV = jsonObject.getString("MSSV");
                            TenKH = jsonObject.getString("TenKH");
                            DiaChi = jsonObject.getString("DiaChi");
                            SDT = jsonObject.getString("SDT");
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            formatter.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                            Date day = formatter.parse(Ngay);
                            arrayList.add(new hoadon(IdHD, TongTien, TinhTrangHD, TTThanhToan, day,ThoiGianDat,ThoiGianNhan,TenKH,DiaChi,SDT,PTThanhToan,MSSV));
                            hoaDonAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException | ParseException e) {

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
    private void loadData(int TinhTrangHD, Integer SapXep) {
        SapXep = spinner.getSelectedItemPosition();
        if(TinhTrangHD==0)
        {
            btn1.setBackground(getResources().getDrawable(R.drawable.custom_btn));
            btn2.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            btn3.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            btn4.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            getdulieu(0,SapXep);
        } else if (TinhTrangHD==1){
            btn1.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            btn2.setBackground(getResources().getDrawable(R.drawable.custom_btn));
            btn3.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            btn4.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            getdulieu(1,SapXep);
        } else if (TinhTrangHD==2){
            btn1.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            btn2.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            btn3.setBackground(getResources().getDrawable(R.drawable.custom_btn));
            btn4.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            getdulieu(2,SapXep);
        } else if (TinhTrangHD==3){
            btn1.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            btn2.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            btn3.setBackground(getResources().getDrawable(R.drawable.custom_edt));
            btn4.setBackground(getResources().getDrawable(R.drawable.custom_btn));
            getdulieu(3,SapXep);
        }
        loadsl();
    }
    private void catchOnMenuItem() {
        arrayListItemMenu = new ArrayList<>();
        itemMenuAdapter = new ItemMenuAdapter(arrayListItemMenu,getApplicationContext());
        lvMenu.setAdapter(itemMenuAdapter);
        arrayListItemMenu.add(0, new ItemMenu(R.drawable.menu2,"Quản lí đơn hàng"));
        arrayListItemMenu.add(1, new ItemMenu(R.drawable.menu1,"Quản lí thực đơn"));
        //   arrayListItemMenu.add(2, new ItemMenu(R.drawable.menu3,"Nhắn tin"));
        arrayListItemMenu.add(2, new ItemMenu(R.drawable.menu4,"Thống kê"));
        arrayListItemMenu.add(3, new ItemMenu(R.drawable.info,"Thay đổi thông tin"));
        arrayListItemMenu.add(4, new ItemMenu(R.drawable.logout,"Đăng xuất"));
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent qlhd = new Intent(getApplicationContext(),MainActivity2.class);
                        startActivity(qlhd);
                        finish();
                        break;
                    case 1:

                        Intent qltd = new Intent(getApplicationContext(),QuanLiThucDonActivity.class);
                        startActivity(qltd);
                        break;
                    case 2:
                        Intent tk = new Intent(getApplicationContext(),ThongKeActivity.class);
                        startActivity(tk);
                        break;
                    case 3:
                        Intent ifo = new Intent(getApplicationContext(),ChangeInforActivity.class);
                        startActivity(ifo);
                        break;
                    case 4:
                        Intent lo = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(lo);
                        finish();
                        break;
                }
            }
        });
    }
    private void actionToolbar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarMain.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        catchOnMenuItem();

    }
    private void addViews() {
        arrayList = new ArrayList<>();
        lvDonHang = findViewById(R.id.lvDonHang);
        hoaDonAdapter = new HDAdapter(getApplicationContext(),arrayList);
        lvDonHang.setAdapter(hoaDonAdapter);
        btn1 =  findViewById(R.id.btnChoXacNhan);
        btn2 =  findViewById(R.id.btnChoLayHang);
        btn3 =  findViewById(R.id.btnDangGiao);
        btn4 =  findViewById(R.id.btnDaGiao);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(0,spinner.getSelectedItemPosition());

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(1,spinner.getSelectedItemPosition());
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(2,spinner.getSelectedItemPosition());
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(3,spinner.getSelectedItemPosition());
            }
        });

        toolbarMain = findViewById(R.id.toolbarMain);
        drawerLayout = findViewById(R.id.drawlayMain);
        lvMenu = findViewById(R.id.lvMenu);
        navigationView = findViewById(R.id.navigationview);
        String[] mang = new String[]{ "Theo số phòng" , "Theo thời gian đặt"};
        spinner = findViewById(R.id.spinnerHoaDon);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mang);
        spinner.setAdapter(arrayAdapter);
        txtCXN = findViewById(R.id.txtSLCXN);
        txtCDG = findViewById(R.id.txtSLCDG);
        txtCGH = findViewById(R.id.txtSLCGH);
        txtDG = findViewById(R.id.txtSLDGH);
    }
}