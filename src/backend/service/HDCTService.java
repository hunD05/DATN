/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.respository.HDCTRepository;
import backend.respository.HDCTViewModelRepo;
import backend.viewmodel.HDCTViewModel;
import java.util.List;

/**
 *
 * @author VHC
 */
public class HDCTService {

    private HDCTViewModelRepo repo = new HDCTViewModelRepo();

    private HDCTRepository repoHDCT = new HDCTRepository();

    public List<HDCTViewModel> getAll(int idHD) {
        return repo.getAll(idHD);
    }

//    public void inHDCT(int idHD) {
//        repo.inHDCT(idHD);
//    }
    public boolean addHDCT(int idHD, int idSPCT, int soLuongMua, double giaBan) {
        return repoHDCT.addHDCT(idHD, idSPCT, soLuongMua, giaBan);
    }

    public boolean updateHDCT(int idHD, int soLuong, int idCTSP) {
        return repoHDCT.updateHDCT(idHD, soLuong, idCTSP);
    }

    public List<HDCTViewModel> searchCBBSP(int idHD, String mauSac, String size, String chatLieu, String thuongHieu, String gia) {
        return repo.searchCBBSP(idHD, mauSac, size, chatLieu, thuongHieu, gia);
    }

    public boolean deleteHDCT(int idHD, int soLuong, int idCTSP) {
        return repoHDCT.deleteHDCT(idHD, soLuong, idCTSP);
    }

}
