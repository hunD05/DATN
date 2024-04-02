/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.ThuongHieu;
import backend.respository.ThuongHieuRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class ThuonHieuService {
        ThuongHieuRespository respository = new ThuongHieuRespository();
    public List<ThuongHieu> getAll(){
        return respository.getAll();
    }
    public boolean add(ThuongHieu chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
    public boolean update(ThuongHieu thuongHieu, String id) {
        return respository.update(thuongHieu, id);
    }

    public boolean delete(String id) {
        return respository.delete(id);
    }
}
