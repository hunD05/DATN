/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.viewmodel;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author VHC
 */
public class HoaDonViewModel {

    private int id;
    private String maHD;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayThanhToan;
    private double giaTien;
    private String maNV;
    private String tenKH;
    private String diaChi;
    private String soDT;
    private String trangThai;
    private String hinhThucThanhToan;

    public HoaDonViewModel() {
    }

    public HoaDonViewModel(int id, String maHD, LocalDateTime ngayTao, LocalDateTime ngayThanhToan, double giaTien, String maNV, String tenKH, String diaChi, String soDT, String trangThai, String hinhThucThanhToan) {
        this.id = id;
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.giaTien = giaTien;
        this.maNV = maNV;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.trangThai = trangThai;
        this.hinhThucThanhToan = hinhThucThanhToan;
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

    public LocalDateTime getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    
}
