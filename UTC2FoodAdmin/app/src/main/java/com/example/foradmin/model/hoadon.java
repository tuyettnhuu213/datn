package com.example.foradmin.model;
import java.util.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class hoadon {
    int idhd, tongtien, tthoadon, ttthanhtoan;
    //LocalDateTime ngay;
    Date ngay;
    String tgdat, tgnhan;
    String tenkh,diachi,sdt,pttt, mssv;

    public hoadon() {
    }

    public int getIdhd() {
        return idhd;
    }

    public void setIdhd(int idhd) {
        this.idhd = idhd;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getTthoadon() {
        return tthoadon;
    }

    public void setTthoadon(int tthoadon) {
        this.tthoadon = tthoadon;
    }

    public int getTtthanhtoan() {
        return ttthanhtoan;
    }

    public void setTtthanhtoan(int ttthanhtoan) {
        this.ttthanhtoan = ttthanhtoan;
    }

    public String getMssv() {
        return mssv;
    }
//
//    public LocalDateTime getNgay() {
//        return ngay;
//    }
//
//    public void setNgay(LocalDateTime ngay) {
//        this.ngay = ngay;
//    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

//    public hoadon(int idhd, int tongtien, int tthoadon, int ttthanhtoan, LocalDateTime ngay, String tgdat, String tgnhan, String tenkh, String diachi, String sdt, String pttt, String mssv) {
//        this.idhd = idhd;
//        this.tongtien = tongtien;
//        this.tthoadon = tthoadon;
//        this.ttthanhtoan = ttthanhtoan;
//        this.ngay = ngay;
//        this.tgdat = tgdat;
//        this.tgnhan = tgnhan;
//        this.tenkh = tenkh;
//        this.diachi = diachi;
//        this.sdt = sdt;
//        this.pttt = pttt;
//        this.mssv = mssv;
//    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public hoadon(int idhd, int tongtien, int tthoadon, int ttthanhtoan, Date ngay, String tgdat, String tgnhan, String tenkh, String diachi, String sdt, String pttt, String mssv) {
        this.idhd = idhd;
        this.tongtien = tongtien;
        this.tthoadon = tthoadon;
        this.ttthanhtoan = ttthanhtoan;
        this.ngay = ngay;
        this.tgdat = tgdat;
        this.tgnhan = tgnhan;
        this.tenkh = tenkh;
        this.diachi = diachi;
        this.sdt = sdt;
        this.pttt = pttt;
        this.mssv = mssv;
    }

    public String getTgdat() {
        return tgdat;
    }

    public void setTgdat(String tgdat) {
        this.tgdat = tgdat;
    }

    public String getTgnhan() {
        return tgnhan;
    }

    public void setTgnhan(String tgnhan) {
        this.tgnhan = tgnhan;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPttt() {
        return pttt;
    }

    public void setPttt(String pttt) {
        this.pttt = pttt;
    }


}
