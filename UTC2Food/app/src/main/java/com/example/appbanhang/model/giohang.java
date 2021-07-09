package com.example.appbanhang.model;

public class giohang {
   int id;

    public int getId() {
        return id;
    }

    public giohang() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Boolean getTinhtrangg() {
        return tinhtrangg;
    }

    public void setTinhtrangg(Boolean tinhtrangg) {
        this.tinhtrangg = tinhtrangg;
    }

    public giohang(int id, int gia, int sl, String ten, String hinhanh, String ghichu, Boolean tinhtrangg) {
        this.id = id;
        this.gia = gia;
        this.sl = sl;
        this.ten = ten;
        this.hinhanh = hinhanh;
        this.ghichu = ghichu;
        this.tinhtrangg = tinhtrangg;
    }

    int gia;
    int sl;
   String ten,hinhanh,ghichu;
   Boolean tinhtrangg;
}
