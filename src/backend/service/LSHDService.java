/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.respository.LSHDViewModelRepo;
import backend.viewmodel.LSHDViewModel;
import java.util.List;

/**
 *
 * @author VHC
 */
public class LSHDService {

    private LSHDViewModelRepo repo = new LSHDViewModelRepo();

    public List<LSHDViewModel> getAll(int idHD) {
        return repo.getAll(idHD);
    }

    public boolean addLSHD(String hanhDong){
        return repo.addLSHD(hanhDong);
    }
    
    public boolean addLSHD2(int idHD, String hanhDong){
        return repo.addLSHD2(idHD, hanhDong);
    }
    
    public boolean isInvoiceIdValid(int invoiceId){
        return repo.isInvoiceIdValid(invoiceId);
    }
}
