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

    public boolean updateSP(int idSPCT, int soLuongCL) {
        return repo.updateSP(idSPCT, soLuongCL);
    }

    public List<BHSPViewModel> searchSP(String tuKhoa) {
        return repo.searchSP(tuKhoa);
    }

    public List<BHHDViewModel> searchHD(String tuKhoa) {
        return repo.searchHD(tuKhoa);
    }

    public List<BHSPViewModel> searchCBBSP(String danhmuc, String xuatxu, String nsx, String gia) {
        return repo.searchCBBSP(danhmuc, xuatxu, nsx, gia);
    }

    public List<BHSPViewModel> getOneSP(int idSPCT) {
        return repo.getOneSP(idSPCT);
    }

    public boolean increaseSoLuong(int soLuongMoi, int idCTSP) {
        return repo.increaseSoLuong(soLuongMoi, idCTSP);
    }

    public boolean decreaseSoLuong(int soLuongMoi, int idCTSP){
        return repo.decreaseSoLuong(soLuongMoi, idCTSP);
    }
    
}
