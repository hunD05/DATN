/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.viewmodel;

import java.time.LocalDateTime;

/**
 *
 * @author VHC
 */
public class BHHDViewModel {
    
    private int id;
    private String maHD;
    private LocalDateTime ngayTao;
    private String maKH;
    private String tenKH;
    private String maNV;
    private int tongSP;
    private String trangThai;
    private double tongTien;

    public BHHDViewModel() {
    }

    public BHHDViewModel(int id, String maHD, LocalDateTime ngayTao, String maKH, String tenKH, String maNV, int tongSP, String trangThai, double tongTien) {
        this.id = id;
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.maNV = maNV;
        this.tongSP = tongSP;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getTongSP() {
        return tongSP;
    }

    public void setTongSP(int tongSP) {
        this.tongSP = tongSP;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    
}
