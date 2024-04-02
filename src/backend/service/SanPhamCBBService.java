/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.SanPham;
import backend.respository.SanPhamCBBRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class SanPhamCBBService {
    SanPhamCBBRespository sanPhamCBBRespository = new SanPhamCBBRespository();
    public List<SanPham> getAllCBB() {
        return sanPhamCBBRespository.getAll();
    }
}
