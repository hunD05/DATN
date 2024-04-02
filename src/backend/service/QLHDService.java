/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.respository.QLHDRepo;

/**
 *
 * @author VHC
 */
public class QLHDService {
    private QLHDRepo repo = new QLHDRepo();
    
    public void exportToExcel(){
        repo.xuatHoaDon();
    }
}
