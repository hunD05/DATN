/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.viewmodel;

import java.math.BigDecimal;

/**
 *
 * @author leanb
 */
public class SanPhamChiTietViewModel {

    private String id;
    private int STT;
    private String tenSanPham;
    private BigDecimal giaBan;
    private int soLuong;
    private String tenDanhMuc;
    private String tenXuatXu;
    private String tenNSX;
    private String tenMauSac;
    private String tenSize;
    private String tenThuongHieu;
    private String tenChatLieu;
    private String tenCoAo;
    private String maDuoiAo;
    private String maTayAo;
    private String tenDangAo;
    private String mota;
    private String trangThai;

    public SanPhamChiTietViewModel() {
    }

    public SanPhamChiTietViewModel(String id, int STT, String tenSanPham, BigDecimal giaBan, int soLuong, String tenDanhMuc, String tenXuatXu, String tenNSX, String tenMauSac, String tenSize, String tenThuongHieu, String tenChatLieu, String tenCoAo, String maDuoiAo, String maTayAo, String tenDangAo, String mota, String trangThai) {
        this.id = id;
        this.STT = STT;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.tenDanhMuc = tenDanhMuc;
        this.tenXuatXu = tenXuatXu;
        this.tenNSX = tenNSX;
        this.tenMauSac = tenMauSac;
        this.tenSize = tenSize;
        this.tenThuongHieu = tenThuongHieu;
        this.tenChatLieu = tenChatLieu;
        this.tenCoAo = tenCoAo;
        this.maDuoiAo = maDuoiAo;
        this.maTayAo = maTayAo;
        this.tenDangAo = tenDangAo;
        this.mota = mota;
        this.trangThai = trangThai;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getTenXuatXu() {
        return tenXuatXu;
    }

    public void setTenXuatXu(String tenXuatXu) {
        this.tenXuatXu = tenXuatXu;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public String getTenSize() {
        return tenSize;
    }

    public void setTenSize(String tenSize) {
        this.tenSize = tenSize;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
    }

    public String getTenCoAo() {
        return tenCoAo;
    }

    public void setTenCoAo(String tenCoAo) {
        this.tenCoAo = tenCoAo;
    }

    public String getMaDuoiAo() {
        return maDuoiAo;
    }

    public void setMaDuoiAo(String maDuoiAo) {
        this.maDuoiAo = maDuoiAo;
    }

    public String getMaTayAo() {
        return maTayAo;
    }

    public void setMaTayAo(String maTayAo) {
        this.maTayAo = maTayAo;
    }

    public String getTenDangAo() {
        return tenDangAo;
    }

    public void setTenDangAo(String tenDangAo) {
        this.tenDangAo = tenDangAo;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    
}
