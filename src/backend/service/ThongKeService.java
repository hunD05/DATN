/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.HoaDon;
import backend.entity.HoaDonChiTiet;
import backend.entity.ThongKe;
import backend.respository.HDCTTKRepo;
import backend.respository.HDTKRepo;
import backend.respository.ThongKeRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author leanb
 */
public class ThongKeService {

    private ThongKeRepository repo = new ThongKeRepository();

    private HDCTTKRepo repoHDCT = new HDCTTKRepo();

    private HDTKRepo repoHD = new HDTKRepo();

    public List<ThongKe> getAll() {
        return repo.getAll();
    }

    public List<HoaDonChiTiet> getHDCT() {
        return repoHDCT.getAll();
    }

    public BigDecimal calculateTotalRevenueFromPaidHoaDonChiTiet() {
        return repoHDCT.calculateTotalRevenueFromPaidHoaDonChiTiet();
    }

    public Map<LocalDate, BigDecimal> getTotalRevenueByDate(LocalDate startDate, LocalDate endDate) {
        return repoHDCT.getTotalRevenueByDate(startDate, endDate);
    }
    
    public List<HoaDon> getHD() {
        return repoHD.getAll();
    }

    public int countUnpaidHoaDon() {
        return repoHD.countUnpaidHoaDon();
    }

    public int countPaidHoaDon() {
        return repoHD.countPaidHoaDon();
    }
    
    public int countUnpaidHoaDonDG(){
        return repoHD.countUnpaidHoaDonDG();
    }
    
    public int countUnppaidHoaDonCG(){
        return repoHD.countUnppaidHoaDonCG();
    }
//    private int countHoaDonByTrangThai(String trangThai) {
//        return hoaDonRespository.by
//    }
    public int countUniqueKhachHangInHoaDon() {
        return repoHD.countUniqueKhachHangInHoaDon();
    }
}
