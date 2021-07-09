package com.example.appbanhang.model;

import android.content.Intent;

public class HoaDon {
    public int getIdHD() {
        return IdHD;
    }

    public void setIdHD(int idHD) {
        IdHD = idHD;
    }

    public int getIdKH() {
        return IdKH;
    }

    public void setIdKH(int idKH) {
        IdKH = idKH;
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public boolean isTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(boolean tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    int IdHD,IdKH,TongTien;

    public HoaDon(int idHD, int idKH, int tongTien, String thoiGian, boolean tinhtrang) {
        IdHD = idHD;
        IdKH = idKH;
        TongTien = tongTien;
        ThoiGian = thoiGian;
        this.tinhtrang = tinhtrang;
    }

    String ThoiGian;
    boolean tinhtrang;
}
