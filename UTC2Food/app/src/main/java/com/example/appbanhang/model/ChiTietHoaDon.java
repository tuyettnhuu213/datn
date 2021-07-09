package com.example.appbanhang.model;

public class ChiTietHoaDon {

    int SL,Gia;
    String tensp,ha;
    String Ghichu;

    public ChiTietHoaDon(int SL, int gia, String tensp, String ha, String ghichu) {
        this.SL = SL;
        Gia = gia;
        this.tensp = tensp;
        this.ha = ha;
        Ghichu = ghichu;
    }

    public int getSL() {
        return SL;
    }

    public void setSL(int SL) {
        this.SL = SL;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHa() {
        return ha;
    }

    public void setHa(String ha) {
        this.ha = ha;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String ghichu) {
        Ghichu = ghichu;
    }
}
