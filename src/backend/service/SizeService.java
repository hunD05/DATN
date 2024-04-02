/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.Size;
import backend.respository.SizeRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class SizeService {
        SizeRespository respository = new SizeRespository();
    public List<Size> getAll(){
        return respository.getAll();
    }
    
    public boolean add(Size chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
    public boolean update(Size size, String id) {
        return respository.update(size, id);
    }

    public boolean delete(String id) {
        return respository.delete(id);
    }
}
