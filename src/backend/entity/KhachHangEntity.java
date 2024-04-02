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
    private Long stt;
    private String id;
    private String maKhachHang;
    private String tenKhachHang;
    private Boolean gioiTinh;
    private String soDienThoai;
    private Date ngaySinh;
    private Boolean deleted;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

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

    public KhachHangEntity(String maKhachHang, String tenKhachHang, Boolean gioiTinh, String soDienThoai, Date ngaySinh) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
    }
    
    
    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
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

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    

}