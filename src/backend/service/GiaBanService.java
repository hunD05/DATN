/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.ChiTietSanPham;
import backend.entity.DanhMuc;
import backend.respository.DanhMucRespository;
import backend.respository.GiaRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class GiaBanService {
    GiaRespository respository = new GiaRespository();
    public List<ChiTietSanPham> getAll() {
        return respository.getAll();
    }
}
