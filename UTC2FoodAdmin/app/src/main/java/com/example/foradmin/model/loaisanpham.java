package com.example.foradmin.model;

import java.io.Serializable;

public class loaisanpham implements Serializable {
    int idloaisp,position;
    String haloaisp,tenloaisp;

    public loaisanpham() {
    }

    public loaisanpham(int idloaisp, int position, String haloaisp, String tenloaisp) {
        this.idloaisp = idloaisp;
        this.position = position;
        this.haloaisp = haloaisp;
        this.tenloaisp = tenloaisp;
    }

    public int getIdloaisp() {
        return idloaisp;
    }

    public void setIdloaisp(int idloaisp) {
        this.idloaisp = idloaisp;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getHaloaisp() {
        return haloaisp;
    }

    public void setHaloaisp(String haloaisp) {
        this.haloaisp = haloaisp;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }
}
