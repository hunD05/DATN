/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.TayAo;
import backend.respository.TayAoRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class TayAoService {
        TayAoRespository respository = new TayAoRespository();
    public List<TayAo> getAll(){
        return respository.getAll();
    }
    
    public boolean add(TayAo chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
    public boolean update(TayAo tayAo, String id) {
        return respository.update(tayAo, id);
    }

    public boolean delete(String id) {
        return respository.delete(id);
    }
}
