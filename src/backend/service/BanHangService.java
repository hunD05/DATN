/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.respository.BanHangRepo;
import backend.viewmodel.BHHDViewModel;
import backend.viewmodel.BHSPViewModel;
import java.util.List;

/**
 *
 * @author VHC
 */
public class BanHangService {

    private BanHangRepo repo = new BanHangRepo();

    public List<BHHDViewModel> getHD() {
        return repo.getHD();
    }

    public List<BHSPViewModel> getSP() {
        return repo.getSP();
    }
}
