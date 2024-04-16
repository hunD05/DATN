/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.PhieuGiamGia;
import backend.respository.PhieuGiamGiaRepo;
import backend.viewmodel.PhieuGiamGiaViewModel;
import java.util.List;

/**
 *
 * @author thanh
 */
public class PhieuGiamGiaService {

    private PhieuGiamGiaRepo repo = new PhieuGiamGiaRepo();

    public List<PhieuGiamGiaViewModel> getAll() {
        return repo.getAll();
    }

    public boolean add(PhieuGiamGia pgg) {
        return repo.add(pgg);
    }

    public boolean Xoa(String IdGiamGia) {
        return repo.Xoa(IdGiamGia);
    }

    public boolean Sua(PhieuGiamGia pgg, String newID) {
        return repo.Sua(pgg, newID);
    }

    public List<PhieuGiamGiaViewModel> search(String ten) {
        return repo.search(ten);
    }

    public List<PhieuGiamGiaViewModel> trangThai(String tt) {
        return repo.trangThai(tt);
    }
    
    public List<PhieuGiamGiaViewModel> searchTT(String tenGG){
        return repo.searchTT(tenGG);
    }
    
    public List<PhieuGiamGiaViewModel> sortGG() {
        return repo.sortGG();
    }
    
    public boolean updateAfter(String tenGG){
        return repo.updateAfter(tenGG);
    }
}
