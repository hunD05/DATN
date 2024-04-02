/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.NSX;
import backend.respository.NSXRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class NSXService {
    NSXRespository respository = new NSXRespository();
    public List<NSX> getAll() {
        return respository.getAll();
    }
    
    public boolean add(NSX chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
    public boolean update(NSX nsx, String id) {
        return respository.update(nsx, id);
    }

    public boolean delete(String id) {
        return respository.delete(id);
    }
}
