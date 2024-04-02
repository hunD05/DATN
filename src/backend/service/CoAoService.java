/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.CoAo;
import backend.respository.CoAoRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class CoAoService {
    CoAoRespository coAoRespository = new CoAoRespository();
    public List<CoAo> getAll(){
        return coAoRespository.getAll();
    }
    
    public boolean add(CoAo chiTietSanPham) {
        return coAoRespository.add(chiTietSanPham);
    }
    
    public boolean update(CoAo coAo, String id) {
        return coAoRespository.update(coAo, id);
    }

    public boolean delete(String id) {
        return coAoRespository.delete(id);
    }
}
