/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author leanb
 */
public class ChiTietSanPham {

    
    private String id;
    private String idSanPham;
    private BigDecimal giaBan;
    private String soLuong;
    private String moTa;
    private String idDanhMuc;
    private String idXuatXu;
    private String idNsx;
    private String idMauSac;
    private String idSize;
    private String idThuongHieu;
    private String idChatLieu;
    private String idCoAo;
    private String idTayAo;
    private String idDuoiAo;
    private String idDangAo;
    private String trangThai;
    private boolean deleted;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public ChiTietSanPham(String stt) {
        this.stt = stt;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }
    private String stt;
    
    
    public ChiTietSanPham() {
    }

    public ChiTietSanPham(String idSanPham, BigDecimal giaBan, String soLuong, String moTa, String idDanhMuc, String idXuatXu, String idNsx, String idMauSac, String idSize, String idThuongHieu, String idChatLieu, String idCoAo, String idTayAo, String idDuoiAo, String idDangAo, String trangThai) {
        this.idSanPham = idSanPham;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.idDanhMuc = idDanhMuc;
        this.idXuatXu = idXuatXu;
        this.idNsx = idNsx;
        this.idMauSac = idMauSac;
        this.idSize = idSize;
        this.idThuongHieu = idThuongHieu;
        this.idChatLieu = idChatLieu;
        this.idCoAo = idCoAo;
        this.idTayAo = idTayAo;
        this.idDuoiAo = idDuoiAo;
        this.idDangAo = idDangAo;
        this.trangThai = trangThai;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getIdXuatXu() {
        return idXuatXu;
    }

    public void setIdXuatXu(String idXuatXu) {
        this.idXuatXu = idXuatXu;
    }

    public String getIdNsx() {
        return idNsx;
    }

    public void setIdNsx(String idNsx) {
        this.idNsx = idNsx;
    }

    public String getIdMauSac() {
        return idMauSac;
    }

    public void setIdMauSac(String idMauSac) {
        this.idMauSac = idMauSac;
    }

    public String getIdSize() {
        return idSize;
    }

    public void setIdSize(String idSize) {
        this.idSize = idSize;
    }

    public String getIdThuongHieu() {
        return idThuongHieu;
    }

    public void setIdThuongHieu(String idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    public String getIdChatLieu() {
        return idChatLieu;
    }

    public void setIdChatLieu(String idChatLieu) {
        this.idChatLieu = idChatLieu;
    }

    public String getIdCoAo() {
        return idCoAo;
    }

    public void setIdCoAo(String idCoAo) {
        this.idCoAo = idCoAo;
    }

    public String getIdTayAo() {
        return idTayAo;
    }

    public void setIdTayAo(String idTayAo) {
        this.idTayAo = idTayAo;
    }

    public String getIdDuoiAo() {
        return idDuoiAo;
    }

    public void setIdDuoiAo(String idDuoiAo) {
        this.idDuoiAo = idDuoiAo;
    }

    public String getIdDangAo() {
        return idDangAo;
    }

    public void setIdDangAo(String idDangAo) {
        this.idDangAo = idDangAo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
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
