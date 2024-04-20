/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.respository.HDRepository;
import backend.respository.HDViewModelRepo;
import backend.viewmodel.HoaDonViewModel;
import java.util.Date;
import java.util.List;

/**
 *
 * @author VHC
 */
public class HoaDonService {

    private HDViewModelRepo repo = new HDViewModelRepo();
    
    private HDRepository repoHD = new HDRepository();

    public List<HoaDonViewModel> getAll() {
        return repo.getAll();
    }

    public List<HoaDonViewModel> searchHD(String tuKhoa) {
        return repo.searchHD(tuKhoa);
    }

    public List<HoaDonViewModel> searchGiaTienHD(double min, double max) {
        return repo.searchGiaTien(min, max);
    }

    public List<HoaDonViewModel> searchTT(String trangThai) {
        return repo.searchTT(trangThai);
    }

    public List<HoaDonViewModel> getOne(int idHD) {
        return repo.getOne(idHD);
    }
    
    public boolean addHD(String trangThai){
        return repoHD.addHD(trangThai);
    }
    
    public boolean updateHD(int idHD, String maNV, String soDT, String maGG){
        return repoHD.updateHD(idHD, maNV, soDT, maGG);
    }
    
    public boolean giaoHang(int idHD, String maNV, String soDT, String maGG, Date ngayNhan, String trangThai){
        return repoHD.giaoHang(idHD, maNV, soDT, maGG, ngayNhan, trangThai);
    }
    
   public boolean updateHDKH(int idHD, String soDT, String diaChi, String tenKH){
        return repoHD.updateHDKH(idHD, soDT, diaChi, tenKH);
    }
   
   public boolean updateHDKL(int idHD, String maNV, String tenKH, String maGG){
       return repoHD.updateHDKL(idHD, maNV, tenKH, maGG);
   }

   public boolean updateHDDG(int idHD){
       return repoHD.updateHDDG(idHD);
   }
   
   public boolean huyGiao(String trangThai, int idHD){
       return repoHD.huyGiao(trangThai, idHD);
   }
   
   public boolean traHang(int idHD){
       return repoHD.traHang(idHD);
   }
   
   public boolean capNhatNgaythanhToan(Date ngayNhan, int idHD){
       return repoHD.capNhatNgaythanhToan(ngayNhan, idHD);
   }
}
