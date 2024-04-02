/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.ChiTietSanPham;
import backend.respository.ChiTietSanPhamRespository;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author leanb
 */
public class ChiTietSanPhamService {
    ChiTietSanPhamRespository respository = new ChiTietSanPhamRespository();
    public List<ChiTietSanPham> getAll() {
        return respository.getAll();
    }
    public ChiTietSanPham getById(long id) {
        return respository.getById(id);
    }
    
    public List<ChiTietSanPham> DanhMuc(int selectedDanhMucID) {
        return respository.DanhMuc(selectedDanhMucID);
    }
    public List<ChiTietSanPham> NSX(int selectedNsxID) {
        return respository.NSX(selectedNsxID);
    }
    
    public List<ChiTietSanPham> XuatXu(int selectedXuatXuID) {
        return respository.XuatXu(selectedXuatXuID);
    }
    
    public List<ChiTietSanPham> GiaBan(BigDecimal selectedGiaBan) {
        return respository.GiaBan(selectedGiaBan);
    }
    
    public List<ChiTietSanPham> ConHang() {
        return respository.ConHang();
    }
    
    public List<ChiTietSanPham> HetHang() {
        return respository.HetHang();
    }
    
    public boolean add(ChiTietSanPham chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
    public boolean update(ChiTietSanPham chiTietSanPham, String id) {
        return respository.update(chiTietSanPham, id);
    }
    
    public boolean delete(Long maSP) {
        return respository.delete(maSP);
    }
    
    public List<ChiTietSanPham> getAllz(Long masp) {
        return respository.getAllz(masp);
    }
}
