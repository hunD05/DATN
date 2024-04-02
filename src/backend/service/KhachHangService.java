/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.KhachHangEntity;
import backend.respository.KhachHangRepository;
import java.util.List;

/**
 *
 * @author HOME
 */
public class KhachHangService {

    KhachHangRepository repo = new KhachHangRepository();

    public List<KhachHangEntity> getAll() {

        return repo.getAll();

    }

    public boolean Add(KhachHangEntity khachhang) {

        return repo.Add(khachhang);

    }

    public boolean Update(KhachHangEntity khachhang, String Manew) {
        return repo.Update(khachhang, Manew);
    }

    public boolean Delete(String Ma) {

        return repo.Delete(Ma);

    }
    public List<KhachHangEntity> Tim(String ten){
        return repo.Tim(ten);
    }
}
