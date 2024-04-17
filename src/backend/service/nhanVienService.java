/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.nhanVien;
import backend.respository.nhanVienRepository;
import backend.viewmodel.nhanVienViewModel;
import java.util.List;

/**
 *
 * @author Huu Hai
 */
public class nhanVienService {
    private nhanVienRepository rep = new nhanVienRepository();
    public List<nhanVienViewModel>getAll() {
        return rep.getAll();
    }
    
    public boolean add(nhanVien NV, String tenChucVu) {
        return rep.add(NV, tenChucVu);
    }
    
     public List<nhanVienViewModel> search(String ten) {
         return rep.search(ten);
     }
    
    public boolean Update(nhanVien NV, String ma, String tenChucVu) {
        return rep.Update(NV, ma,tenChucVu);
    }
    
    public boolean Delete(String ma) {
        return rep.Delete(ma);
    }
    
    public List<nhanVienViewModel> trangThai(String tuKhoa) {
         return rep.getCbb(tuKhoa);
     }
    
    public boolean isCCCDExisted(String cccd){
        return rep.isCCCDExisted(cccd);
    }
    
    public boolean isCCCDExistedForAnotherEmployess(String cccd, int currentEmployesId){
        return rep.isCCCDExistedForAnotherEmployess(cccd, currentEmployesId);
    }
}
