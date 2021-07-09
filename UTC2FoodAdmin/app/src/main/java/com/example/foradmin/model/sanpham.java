package com.example.foradmin.model;

import java.io.Serializable;

public class sanpham implements Serializable {
    int idsp, gia, tinhtrang, idloai;
    String tensp, HASP;

    public sanpham() {
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(int tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public int getIdloai() {
        return idloai;
    }

    public void setIdloai(int idloai) {
        this.idloai = idloai;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHASP() {
        return HASP;
    }

    public void setHASP(String HASP) {
        this.HASP = HASP;
    }

    public sanpham(int idsp, int gia, int tinhtrang, int idloai, String tensp, String HASP) {
        this.idsp = idsp;
        this.gia = gia;
        this.tinhtrang = tinhtrang;
        this.idloai = idloai;
        this.tensp = tensp;
        this.HASP = HASP;
    }
}
