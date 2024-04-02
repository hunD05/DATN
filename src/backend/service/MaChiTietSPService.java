/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.ChiTietSanPham;
import backend.entity.SanPham;
import backend.respository.ChiTietSanPhamRespository;
import backend.respository.MaChiTietSanPhamRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class MaChiTietSPService {
    MaChiTietSanPhamRespository maChiTietSanPhamRespository = new MaChiTietSanPhamRespository();
    public List<ChiTietSanPham> getAll() {
        return maChiTietSanPhamRespository.getAll();
    }
    
}
