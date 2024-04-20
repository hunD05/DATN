/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.respository.ThanhToanViewModelRepo;
import backend.viewmodel.ThanhToanViewModel;
import java.util.List;

/**
 *
 * @author VHC
 */
public class ThanhToanVMService {
    
    private ThanhToanViewModelRepo repo = new ThanhToanViewModelRepo();
    
    public List<ThanhToanViewModel> getAll(int idHD){
        return repo.getAll(idHD);
    }
}
