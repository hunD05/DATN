/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.viewmodel;

import java.util.Date;

/**
 *
 * @author thanh
 */
public class PhieuGiamGiaViewModel {

    private int id;
    private String maGiamGia;
    private String tenGiamGia;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private int soLuong;
    private Float hoaDonToiThieu;
    private Float soPhanTramGiam;
    private Float giamToiDa;
    private int idNhanVien;
    private String trangThai;
    private String sdt;
    private Date ngaySinh;

    public PhieuGiamGiaViewModel() {
    }

    public PhieuGiamGiaViewModel(int id, String maGiamGia, String tenGiamGia, Date ngayBatDau, Date ngayKetThuc, int soLuong, Float hoaDonToiThieu, Float soPhanTramGiam, Float giamToiDa, int idNhanVien, String trangThai, String sdt, Date ngaySinh) {
        this.id = id;
        this.maGiamGia = maGiamGia;
        this.tenGiamGia = tenGiamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuong = soLuong;
        this.hoaDonToiThieu = hoaDonToiThieu;
        this.soPhanTramGiam = soPhanTramGiam;
        this.giamToiDa = giamToiDa;
        this.idNhanVien = idNhanVien;
        this.trangThai = trangThai;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public String getTenGiamGia() {
        return tenGiamGia;
    }

    public void setTenGiamGia(String tenGiamGia) {
        this.tenGiamGia = tenGiamGia;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Float getHoaDonToiThieu() {
        return hoaDonToiThieu;
    }

    public void setHoaDonToiThieu(Float hoaDonToiThieu) {
        this.hoaDonToiThieu = hoaDonToiThieu;
    }

    public Float getSoPhanTramGiam() {
        return soPhanTramGiam;
    }

    public void setSoPhanTramGiam(Float soPhanTramGiam) {
        this.soPhanTramGiam = soPhanTramGiam;
    }

    public Float getGiamToiDa() {
        return giamToiDa;
    }

    public void setGiamToiDa(Float giamToiDa) {
        this.giamToiDa = giamToiDa;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

}
