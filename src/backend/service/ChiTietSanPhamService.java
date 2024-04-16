/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.ChiTietSanPham;
import backend.respository.ChiTietSanPhamRespository;
import backend.viewmodel.SanPhamChiTietViewModel;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    public boolean add(ChiTietSanPham chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }

    public boolean update(ChiTietSanPham chiTietSanPham, String id) {
        return respository.update(chiTietSanPham, id);
    }

    public boolean delete(String maSP) {
        return respository.delete(maSP);
    }

    public List<SanPhamChiTietViewModel> getSP(String tenSP) {
        return respository.getSP(tenSP);
    }

    public List<SanPhamChiTietViewModel> getSanPhamHetHang() {
        List<SanPhamChiTietViewModel> sanPhamHetHang = new ArrayList<>();
        // Thực hiện truy vấn hoặc xử lý để lấy danh sách các sản phẩm có số lượng hàng dưới 10
        // Ví dụ:
        List<SanPhamChiTietViewModel> allSanPham = getAll(); // Sử dụng phương thức getAll() của bạn hoặc tương tự
        for (SanPhamChiTietViewModel sp : allSanPham) {
            if (sp.getSoLuong() < 10) {
                sanPhamHetHang.add(sp);
            }
        }
        return sanPhamHetHang;
    }

    public List<SanPhamChiTietViewModel> getTopProducts() {
        return respository.getTop10BestSellingProducts();
    }

    public List<SanPhamChiTietViewModel> search(String danhMuc, String xuatXu, String nsx, String gia) {
        return respository.search(danhMuc, xuatXu, nsx, gia);
    }

}
