package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.CaNhanActivity;
import com.example.appbanhang.activity.MainActivity;
import com.example.appbanhang.activity.ThongTinNguoiMuaActivity;

public class ThongTinActivity extends AppCompatActivity {
Button btnThongTin,btnDoiMK,btnLichSu,btnDangXuat;

TextView txtThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnThongTin = findViewById(R.id.btnThongTinCaNhan);
        btnDoiMK = findViewById(R.id.btnDoiMatKhau);
        btnLichSu = findViewById(R.id.btnLichSuMuaHang);
        txtThongTin = findViewById(R.id.txtThongTin);
        actionbar();
        btnLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LichSuActivity.class);
                startActivity(intent);
            }
        });
        btnThongTin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), CaNhanActivity.class);
               startActivity(intent);
           }
       });
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DoiMatKhauActivity.class);
                startActivity(intent);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.KHang = null;
                MainActivity.manggiohang.clear();
                finish();
                Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    private void actionbar() {
//        setSupportActionBar(tbThongTin);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        tbThongTin.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        txtThongTin.setText("Xin chào "+MainActivity.KHang.getTen());
    }
}