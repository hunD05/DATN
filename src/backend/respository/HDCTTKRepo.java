/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.HoaDonChiTiet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leanb
 */
public class HDCTTKRepo {

    public List<HoaDonChiTiet> getAll() {
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        String sql = """
                 SELECT 
                     IDChiTietSP, IDHoaDon, SoLuong, GiaBan, Deleted, Created_at, Updated_at, Created_by, Updated_by
                 FROM 
                     HoaDonChiTiet where deleted = 0
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setIdChiTietSP(rs.getLong("IDChiTietSP"));
                hoaDonChiTiet.setIdHoaDon(rs.getLong("IDHoaDon"));
                hoaDonChiTiet.setSoLuong(rs.getLong("SoLuong"));
                hoaDonChiTiet.setGiaBan(rs.getDouble("GiaBan"));
                hoaDonChiTiet.setDeleted(rs.getBoolean("Deleted"));
                hoaDonChiTiet.setCreatedAt(rs.getDate("Created_at"));
                hoaDonChiTiet.setUpdatedAt(rs.getDate("Updated_at"));
                hoaDonChiTiet.setCreatedBy(rs.getString("Created_by"));
                hoaDonChiTiet.setUpdatedBy(rs.getString("Updated_by"));
                hoaDonChiTietList.add(hoaDonChiTiet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hoaDonChiTietList;
    }

    public BigDecimal calculateTotalRevenueFromPaidHoaDonChiTiet() {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        String sql = """
             SELECT SUM(HDCT.SoLuong * HDCT.GiaBan) AS TotalRevenue
             FROM HoaDonChiTiet HDCT
             INNER JOIN HoaDon HD ON HDCT.IDHoaDon = HD.ID
             WHERE HD.TrangThai = N'Đã Thanh Toán'
             """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalRevenue = rs.getBigDecimal("TotalRevenue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalRevenue;
    }

    public Map<LocalDate, BigDecimal> getTotalRevenueByDate(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, BigDecimal> revenueByDate = new HashMap<>();

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement("""
                                                     SELECT CAST(NgayThanhToan AS DATE) AS NgayThanhToan,
                                                                                                                 SUM(SoLuong * GiaBan) AS TotalRevenue
                                                                                                          FROM HoaDonChiTiet HDCT
                                                                                                          INNER JOIN HoaDon HD ON HDCT.IDHoaDon = HD.ID
                                                                                                          WHERE HD.TrangThai = N'Đã Thanh Toán' 
                                                                                                                AND NgayThanhToan BETWEEN ? AND ?
                                                                                                          GROUP BY CAST(NgayThanhToan AS DATE)
                                                                                                          ORDER BY CAST(NgayThanhToan AS DATE);
                                                     """)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getDate("NgayThanhToan").toLocalDate();
                BigDecimal totalRevenue = rs.getBigDecimal("TotalRevenue");
                revenueByDate.put(date, totalRevenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return revenueByDate;
    }
    
    
    
    public Map<LocalDate, BigDecimal> Searchbydate(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, BigDecimal> revenueByDate = new HashMap<>();

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement("""
                                                     SELECT CAST(NgayThanhToan AS DATE) AS NgayThanhToan,
                                                                                                                 SUM(SoLuong * GiaBan) AS TotalRevenue
                                                                                                          FROM HoaDonChiTiet HDCT
                                                                                                          INNER JOIN HoaDon HD ON HDCT.IDHoaDon = HD.ID
                                                                                                          WHERE HD.TrangThai = N'Đã Thanh Toán' 
                                                                                                                AND NgayThanhToan BETWEEN ? AND ?
                                                                                                          GROUP BY CAST(NgayThanhToan AS DATE)
                                                                                                          ORDER BY CAST(NgayThanhToan AS DATE);
                                                     """)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getDate("NgayThanhToan").toLocalDate();
                BigDecimal totalRevenue = rs.getBigDecimal("TotalRevenue");
                revenueByDate.put(date, totalRevenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return revenueByDate;
    }

}
