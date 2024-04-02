/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.viewmodel;

import java.util.List;

/**
 *
 * @author VHC
 */
public class BHSPViewModel {
    private int maSPCT;
    private String tenSP;
    private String danhMuc;
    private String xuatXu;
    private String nsx;
    private String size;
    private double giaBan;
    private int soLuong;

    public BHSPViewModel() {
    }

    public BHSPViewModel(int maSPCT, String tenSP, String danhMuc, String xuatXu, String nsx, String size, double giaBan, int soLuong) {
        this.maSPCT = maSPCT;
        this.tenSP = tenSP;
        this.danhMuc = danhMuc;
        this.xuatXu = xuatXu;
        this.nsx = nsx;
        this.size = size;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public int getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(int maSPCT) {
        this.maSPCT = maSPCT;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getNsx() {
        return nsx;
    }

    public void setNsx(String nsx) {
        this.nsx = nsx;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    
}
