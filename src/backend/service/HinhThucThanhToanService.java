/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.respository.HinhThucThanhToanRepo;

/**
 *
 * @author VHC
 */
public class HinhThucThanhToanService {

    private HinhThucThanhToanRepo repo = new HinhThucThanhToanRepo();

    public boolean addHTTT(int idHD, String tenKieuTT, double tienCK, double tienMat) {
        return repo.addHTTT(idHD, tenKieuTT, tienCK, tienMat);
    }

    public boolean updateHTTT(int idHD, String tenKieuTT, double tienCK, double tienMat){
        return repo.updateHTTT(idHD, tenKieuTT, tienCK, tienMat);
    }
}
