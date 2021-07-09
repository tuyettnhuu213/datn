package com.example.foradmin.model;

public class doanhthu {
    String thang;
    int doanhthu;

    public doanhthu(String thang, int doanhthu) {
        this.thang = thang;
        this.doanhthu = doanhthu;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public int getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(int doanhthu) {
        this.doanhthu = doanhthu;
    }
}
