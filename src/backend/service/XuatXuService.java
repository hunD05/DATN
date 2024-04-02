/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;


import backend.entity.XuatXu;
import backend.respository.XuatXuRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class XuatXuService {
    XuatXuRespository respository = new XuatXuRespository();
    public List<backend.entity.XuatXu> getAll() {
        return respository.getAll();
    }
    
    public boolean add(XuatXu chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
    public boolean update(XuatXu xuatXu, String id) {
        return respository.update(xuatXu, id);
    }

    public boolean delete(String id) {
        return respository.delete(id);
    }
}
