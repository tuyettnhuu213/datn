package com.example.foradmin.model;

public class thongkekh {
    int  DTL, SoDonMua, SoTien;
    String  MSSV, TenKH;

    public thongkekh(int DTL, int soDonMua, int soTien, String MSSV, String tenKH) {
        this.DTL = DTL;
        SoDonMua = soDonMua;
        SoTien = soTien;
        this.MSSV = MSSV;
        TenKH = tenKH;
    }

    public int getDTL() {
        return DTL;
    }

    public void setDTL(int DTL) {
        this.DTL = DTL;
    }

    public int getSoDonMua() {
        return SoDonMua;
    }

    public void setSoDonMua(int soDonMua) {
        SoDonMua = soDonMua;
    }

    public int getSoTien() {
        return SoTien;
    }

    public void setSoTien(int soTien) {
        SoTien = soTien;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }
}
