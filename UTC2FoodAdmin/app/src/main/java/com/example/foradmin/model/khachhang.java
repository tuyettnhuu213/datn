package com.example.foradmin.model;

public class khachhang {
    int mssv;
    String ten, sdt, diachi, dtl;

    public khachhang(int mssv, String ten, String sdt, String diachi, String dtl) {
        this.mssv = mssv;
        this.ten = ten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.dtl = dtl;
    }

    public int getMssv() {
        return mssv;
    }

    public void setMssv(int mssv) {
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

    public String getDtl() {
        return dtl;
    }

    public void setDtl(String dtl) {
        this.dtl = dtl;
    }
}
