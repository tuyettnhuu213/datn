package com.example.foradmin.model;

public class thongkesp {
    String tensp,ha,tenloai;
    int gia,tongsoluongban;

    public thongkesp(String tensp, String ha, String tenloai, int gia, int tongsoluongban) {
        this.tensp = tensp;
        this.ha = ha;
        this.tenloai = tenloai;
        this.gia = gia;
        this.tongsoluongban = tongsoluongban;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
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

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getTongsoluongban() {
        return tongsoluongban;
    }

    public void setTongsoluongban(int tongsoluongban) {
        this.tongsoluongban = tongsoluongban;
    }
}
