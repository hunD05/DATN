/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.DuoiAo;
import backend.respository.DuoiAoRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class DuoiAoService {
    DuoiAoRespository respository = new DuoiAoRespository();
    public List<DuoiAo> getAll(){
        return respository.getAll();
    }
    public boolean add(DuoiAo chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
    public boolean update(DuoiAo duoiAo, String id) {
        return respository.update(duoiAo, id);
    }
    
    public boolean delete(String id) {
        return respository.delete(id);
    }
}
