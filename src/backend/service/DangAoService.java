/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.DangAo;
import backend.respository.DangAoRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class DangAoService {
    DangAoRespository respository = new DangAoRespository();
    public List<DangAo> getAll(){
        return respository.getAll();
    }
    
    public boolean add(DangAo chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
     public boolean update(DangAo dangAo, String id) {
        return respository.update(dangAo, id);
    }

    public boolean delete(String id) {
        return respository.delete(id);
    }
}
