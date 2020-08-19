package com.example.Model;

import io.realm.RealmObject;

public class SanPham extends RealmObject {
    private String maSanPham;
    private String tenSanPham;
    private int donGia;
    private DanhMuc danhMuc;

    public SanPham() {
    }

    public SanPham(String maSanPham, String tenSanPham, int donGia) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    @Override
    public String toString() {
        return this.maSanPham + " - " + this.tenSanPham + " - " + this.donGia;
    }
}
