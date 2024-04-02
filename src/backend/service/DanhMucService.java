/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.ChiTietSanPham;
import backend.entity.DanhMuc;
import backend.respository.ChiTietSanPhamRespository;
import backend.respository.DanhMucRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class DanhMucService {
    DanhMucRespository respository = new DanhMucRespository();
    public List<DanhMuc> getAll() {
        return respository.getAll();
    }
    
    public boolean add(DanhMuc chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
    public boolean update(DanhMuc chiTietSanPham, String id) {
        return respository.update(chiTietSanPham, id);
    }
    
    public boolean delete(String id) {
        return respository.delete(id);
    }
}
