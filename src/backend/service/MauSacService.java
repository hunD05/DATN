/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.MauSac;
import backend.respository.MauSacRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class MauSacService {
        MauSacRespository respository = new MauSacRespository();
    public List<MauSac> getAll(){
        return respository.getAll();
    }
    
    public boolean add(MauSac chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
     public boolean update(MauSac mauSac, String id) {
        return respository.update(mauSac, id);
    }

    public boolean delete(String id) {
        return respository.delete(id);
    }
}
