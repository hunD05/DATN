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

    
    private Long id;
    private Long idSanPham;
    private BigDecimal giaBan;
    private Long soLuong;
    private String moTa;
    private Long idDanhMuc;
    private Long idXuatXu;
    private Long idNsx;
    private Long idMauSac;
    private Long idSize;
    private Long idThuongHieu;
    private Long idChatLieu;
    private Long idCoAo;
    private Long idTayAo;
    private Long idDuoiAo;
    private Long idDangAo;
    private String trangThai;
    private boolean deleted;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public ChiTietSanPham(Long stt) {
        this.stt = stt;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }
    private Long stt;
    
    
    public ChiTietSanPham() {
    }

    public ChiTietSanPham(Long idSanPham, BigDecimal giaBan, Long soLuong, String moTa, Long idDanhMuc, Long idXuatXu, Long idNsx, Long idMauSac, Long idSize, Long idThuongHieu, Long idChatLieu, Long idCoAo, Long idTayAo, Long idDuoiAo, Long idDangAo, String trangThai) {
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

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(Long idSanPham) {
        this.idSanPham = idSanPham;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Long getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(Long idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public Long getIdXuatXu() {
        return idXuatXu;
    }

    public void setIdXuatXu(Long idXuatXu) {
        this.idXuatXu = idXuatXu;
    }

    public Long getIdNsx() {
        return idNsx;
    }

    public void setIdNsx(Long idNsx) {
        this.idNsx = idNsx;
    }

    public Long getIdMauSac() {
        return idMauSac;
    }

    public void setIdMauSac(Long idMauSac) {
        this.idMauSac = idMauSac;
    }

    public Long getIdSize() {
        return idSize;
    }

    public void setIdSize(Long idSize) {
        this.idSize = idSize;
    }

    public Long getIdThuongHieu() {
        return idThuongHieu;
    }

    public void setIdThuongHieu(Long idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    public Long getIdChatLieu() {
        return idChatLieu;
    }

    public void setIdChatLieu(Long idChatLieu) {
        this.idChatLieu = idChatLieu;
    }

    public Long getIdCoAo() {
        return idCoAo;
    }

    public void setIdCoAo(Long idCoAo) {
        this.idCoAo = idCoAo;
    }

    public Long getIdTayAo() {
        return idTayAo;
    }

    public void setIdTayAo(Long idTayAo) {
        this.idTayAo = idTayAo;
    }

    public Long getIdDuoiAo() {
        return idDuoiAo;
    }

    public void setIdDuoiAo(Long idDuoiAo) {
        this.idDuoiAo = idDuoiAo;
    }

    public Long getIdDangAo() {
        return idDangAo;
    }

    public void setIdDangAo(Long idDangAo) {
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
