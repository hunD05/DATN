/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.SanPham;
import backend.respository.SanPhamCBBRespository;
import backend.respository.SanPhamRespository;
import backend.viewmodel.SanPhamViewModel;
import java.util.List;

/**
 *
 * @author leanb
 */
public class SanPhamService {
    
    SanPhamRespository spr = new SanPhamRespository();
    public List<SanPhamViewModel> getAll(){
        return spr.getAll();
    }
    
    public boolean add(SanPham sanPham){
        return spr.add(sanPham);
    }
    
    public boolean delete(String maSP){
        return spr.delete(maSP);
    }
    
    public boolean update(SanPham sanPham,String ID){
        return spr.update(sanPham, ID);
    }
    public List<SanPhamViewModel> search(String keyword) {
        return spr.search(keyword);
    }
    
    public boolean checkMaSanPhamExists(String maSP) {
        return spr.checkMaSanPhamExists(maSP);
    }
    
    public List<SanPhamViewModel> searchByStatus(String status) {
        return spr.searchByStatus(status);
    }
    
}
