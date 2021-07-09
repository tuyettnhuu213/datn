package com.example.appbanhang.model;

public class khachhang {
    int dtl;
    String mssv, ten, sdt, diachi,password;
    public int getDtl() {
        return dtl;
    }

    public void setDtl(int dtl) {
        this.dtl = dtl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public khachhang(String mssv, int dtl, String ten, String sdt, String diachi, String password) {
        this.mssv = mssv;
        this.dtl = dtl;
        this.ten = ten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.password = password;
    }
}
