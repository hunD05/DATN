/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.HoaDon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leanb
 */
public class HDTKRepo {

    public List<HoaDon> getAll() {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String sql = """
                 SELECT 
                     ID, MaHoaDon, NgayTao, NgayThanhToan, IDNhanVien, IDKhachHang, IDPhieuGG, DiaChi, SoDienThoai, TrangThai, Deleted, Created_at, Updated_at, Created_by, Updated_by
                 FROM 
                     HoaDon where deleted = 0
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getLong("ID"));
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                hoaDon.setNgayTao(rs.getDate("NgayTao"));
                hoaDon.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                hoaDon.setIdNhanVien(rs.getLong("IDNhanVien"));
                hoaDon.setIdKhachHang(rs.getLong("IDKhachHang"));
                hoaDon.setIdPhieuGG(rs.getLong("IDPhieuGG"));
                hoaDon.setDiaChi(rs.getString("DiaChi"));
                hoaDon.setSoDienThoai(rs.getString("SoDienThoai"));
                hoaDon.setTrangThai(rs.getString("TrangThai"));
                hoaDon.setDeleted(rs.getBoolean("Deleted"));
                hoaDon.setCreatedAt(rs.getDate("Created_at"));
                hoaDon.setUpdatedAt(rs.getDate("Updated_at"));
                hoaDon.setCreatedBy(rs.getString("Created_by"));
                hoaDon.setUpdatedBy(rs.getString("Updated_by"));
                hoaDonList.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hoaDonList;
    }

    public int countUnpaidHoaDon() {
        return countHoaDonByTrangThai("Chưa Thanh Toán");
    }

    public int countPaidHoaDon() {
        return countHoaDonByTrangThai("Đã Thanh Toán");
    }

    private int countHoaDonByTrangThai(String trangThai) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE TrangThai = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countUniqueKhachHangInHoaDon() {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT IDKhachHang) FROM HoaDon WHERE IDKhachHang IS NOT NULL";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
