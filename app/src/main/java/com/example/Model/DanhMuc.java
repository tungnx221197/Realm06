package com.example.Model;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DanhMuc extends RealmObject {
    private String maDanhMuc;
    private String tenDanhMuc;
    private RealmList<SanPham> sanPhams = new RealmList<>();

    public DanhMuc() {
    }

    public DanhMuc(String maDanhMuc, String tenDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public RealmList<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(RealmList<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }

    @Override
    public String toString() {
        return this.maDanhMuc + " - " + this.tenDanhMuc;
    }
}
