/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.entity;

import java.sql.Date;

/**
 *
 * @author HOME
 */
public class KhachHangEntity {

    private long stt;
    private String id;
    private String maKhachHang;
    private String tenKhachHang;
    private boolean gioiTinh;
    private String soDienThoai;
    private String diaChi;

    public KhachHangEntity() {
    }

//    public KhachHangEntity(Long stt, String id, String maKhachHang, String tenKhachHang, Boolean gioiTinh, String soDienThoai, Date ngaySinh, Boolean deleted, Date createdAt, Date updatedAt, String createdBy, String updatedBy) {
//        this.stt = stt;
//        this.id = id;
//        this.maKhachHang = maKhachHang;
//        this.tenKhachHang = tenKhachHang;
//        this.gioiTinh = gioiTinh;
//        this.soDienThoai = soDienThoai;
//        this.ngaySinh = ngaySinh;
//        this.deleted = deleted;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.createdBy = createdBy;
//        this.updatedBy = updatedBy;
//    }
    public KhachHangEntity(String tenKhachHang, boolean gioiTinh, String soDienThoai, String diaChi) {
        this.tenKhachHang = tenKhachHang;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }

    public long getStt() {
        return stt;
    }

    public void setStt(long stt) {
        this.stt = stt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    
    
}
