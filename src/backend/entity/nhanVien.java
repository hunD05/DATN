/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.entity;

import java.util.Date;

/**
 *
 * @author Huu Hai
 */
public class nhanVien {
    
    
    private String maNV;
    private String tenNV;
    private boolean gioiTinh;
    private String SDT;
    private String CCCD;
    private String Email;
    private Date ngaySinh;
    private String trangThai;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public nhanVien(String tenNV, boolean gioiTinh, String SDT, String CCCD, String Email, Date ngaySinh, String trangThai) {

        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.SDT = SDT;
       
        this.CCCD = CCCD;
        this.Email = Email;
        this.ngaySinh = ngaySinh;
        this.trangThai = trangThai;
    }

    public nhanVien() {
    }
}
