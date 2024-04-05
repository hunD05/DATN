/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.ChiTietSanPham;
import backend.respository.ChiTietSanPhamRespository;
import backend.viewmodel.SanPhamChiTietViewModel;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author leanb
 */
public class ChiTietSanPhamService {
    ChiTietSanPhamRespository respository = new ChiTietSanPhamRespository();
    public List<SanPhamChiTietViewModel> getAll() {
        return respository.getAll();
    }
    public SanPhamChiTietViewModel getById(long id) {
        return respository.getById(id);
    }
    
    public List<SanPhamChiTietViewModel> DanhMuc(String danhMuc) {
        return respository.DanhMuc(danhMuc);
    }

    public List<SanPhamChiTietViewModel> NSX(String danhMuc) {
        return respository.NSX(danhMuc);
    }
    
    public List<SanPhamChiTietViewModel> XuatXu(String danhMuc) {
        return respository.XuatXu(danhMuc);
    }
    
    public List<SanPhamChiTietViewModel> giaBan(String sortOrder) {
        return respository.giaBan(sortOrder);
    }
    public List<SanPhamChiTietViewModel> Search(String keyword) {
        return respository.Search(keyword);
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
    public List<SanPhamChiTietViewModel> filterData(String danhMuc, String xuatXu, String nsx, String giaBanSortOrder) {
        return respository.filterData(danhMuc, xuatXu, nsx, giaBanSortOrder);
    }
    public boolean update(ChiTietSanPham chiTietSanPham, String id) {
        return respository.update(chiTietSanPham, id);
    }
    
    public boolean delete(String maSP) {
        return respository.delete(maSP);
    }
    public List<SanPhamChiTietViewModel> SearchCbb(String danhmuc, String xuatxu, String nsx, boolean sapXepGiaTangDan) {
        return respository.SearchCbb(danhmuc, xuatxu, nsx, sapXepGiaTangDan);
    }
    
    public List<SanPhamChiTietViewModel> getSP(String tenSP) {
        return respository.getSP(tenSP);
    }

//    public List<SanPhamChiTietViewModel> getAllz(Long masp) {
//        return respository.getAllz(masp);
//    }
}
