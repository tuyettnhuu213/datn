package com.example.appbanhang.model;

import java.io.Serializable;

public class sanpham implements Serializable {
    public int id,giasp,idsp;

    public sanpham() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public boolean isTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(boolean tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public sanpham(int id,  String tensp, String hinhanhsp,int giasp, int idsp, String mota, boolean tinhtrang) {
        this.id = id;
        this.giasp = giasp;
        this.idsp = idsp;
        this.tensp = tensp;
        this.hinhanhsp = hinhanhsp;
        this.mota = mota;
        this.tinhtrang = tinhtrang;
    }

    public String tensp,hinhanhsp,mota;
    public boolean tinhtrang;
}
