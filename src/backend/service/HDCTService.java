/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.respository.HDCTViewModelRepo;
import backend.viewmodel.HDCTViewModel;
import java.util.List;

/**
 *
 * @author VHC
 */
public class HDCTService {

    private HDCTViewModelRepo repo = new HDCTViewModelRepo();

    public List<HDCTViewModel> getAll(int idHD) {
        return repo.getAll(idHD);
    }

//    public void inHDCT(int idHD) {
//        repo.inHDCT(idHD);
//    }

}
