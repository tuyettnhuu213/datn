package com.example.appbanhang.model;

public class Loaisp {
    public int id;
    public String tenloaisp;
    public String hinhanhloaisp;

    public Loaisp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public String getHinhanhloaisp() {
        return hinhanhloaisp;
    }

    public void setHinhanhloaisp(String hinhanhloaisp) {
        this.hinhanhloaisp = hinhanhloaisp;
    }

    public Loaisp(int id, String tenloaisp, String hinhanhloaisp) {
        this.id = id;
        this.tenloaisp = tenloaisp;
        this.hinhanhloaisp = hinhanhloaisp;
    }
}
