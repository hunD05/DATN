/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.PhuongThucThanhToan;
import backend.respository.PhuongThucThanhToanRepo;
import java.util.List;

/**
 *
 * @author VHC
 */
public class PhuongThucThanhToanService {
    
    private PhuongThucThanhToanRepo repo = new PhuongThucThanhToanRepo();
    
    public List<PhuongThucThanhToan> getTT(){
        return repo.getTT();
    }
}
