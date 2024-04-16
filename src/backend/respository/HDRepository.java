/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.HoaDonEntity;
import backend.respository.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author VHC
 */
public class HDRepository {

    public boolean addHD(String trangThai) {
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[HoaDon]
                                ([NgayTao]
                                ,[IDNhanVien]
                                ,[TrangThai]
                                )
                          VALUES
                                (current_timestamp,1,?)
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, trangThai);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean updateHD(int idHD, String maNV, String soDT, String maGG) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[HoaDon]
                    SET [NgayThanhToan] = CURRENT_TIMESTAMP
                       ,[IDNhanVien] = (SELECT TOP 1 ID FROM NhanVien WHERE MaNhanVien LIKE ?)
                       ,[IDKhachHang] = (SELECT TOP 1 ID FROM KhachHang WHERE SoDienThoai LIKE ?)
                       ,[IDPhieuGG] = (SELECT TOP 1 ID FROM PhieuGiamGia WHERE TenGiamGia LIKE ?)
                       ,[DiaChi] = (SELECT TOP 1 DiaChi FROM KhachHang WHERE SoDienThoai LIKE ?)
                       ,[SoDienThoai] = ?
                       ,[TrangThai] = N'Đã Thanh Toán'
                       ,[Updated_at] = CURRENT_TIMESTAMP
                  WHERE ID = ?
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setString(1, maNV);
            ps.setString(2, soDT);
            ps.setString(3, maGG);
            ps.setString(4, soDT);
            ps.setString(5, soDT);
            ps.setInt(6, idHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean updateHDKL(int idHD, String maNV, String tenKH, String maGG) {
        int check = 0;
        String sql = """
             UPDATE [dbo].[HoaDon]
             SET [NgayThanhToan] = CURRENT_TIMESTAMP
                 ,[IDNhanVien] = (SELECT TOP 1 ID FROM NhanVien WHERE MaNhanVien LIKE ?)
                 ,[IDKhachHang] = (SELECT TOP 1 ID FROM KhachHang WHERE TenKhachHang LIKE ?)
                 ,[IDPhieuGG] = (SELECT TOP 1 ID FROM PhieuGiamGia WHERE TenGiamGia LIKE ?)
                 ,[DiaChi] = (SELECT TOP 1 DiaChi FROM KhachHang WHERE TenKhachHang LIKE ?)
                 ,[SoDienThoai] = (SELECT TOP 1 SoDienThoai FROM KhachHang WHERE TenKhachHang LIKE ?)
                 ,[TrangThai] = N'Đã Thanh Toán'
                 ,[Updated_at] = CURRENT_TIMESTAMP
             WHERE ID = ?
             """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maNV);
            ps.setObject(2, tenKH);
            ps.setObject(3, maGG);
            ps.setObject(4, tenKH);
            ps.setObject(5, tenKH);
            ps.setObject(6, idHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean giaoHang(int idHD, String maNV, String soDT, String maGG, Date ngayNhan) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[HoaDon]
                        SET [NgayThanhToan] = ?
                           ,[IDNhanVien] = (SElECT ID FROM NhanVien WHERE MaNhanVien LIKE ?)
                           ,[IDKhachHang] = (SElECT ID FROM KhachHang WHERE SoDienThoai LIKE ?)
                           ,[IDPhieuGG] = (SElECT ID FROM PhieuGiamGia WHERE TenGiamGia LIKE ?)
                           ,[DiaChi] = (SElECT DiaChi FROM KhachHang WHERE SoDienThoai LIKE ?)
                           ,[SoDienThoai] = (SElECT SoDienThoai FROM KhachHang WHERE SoDienThoai LIKE ?)
                     	  ,[TrangThai] = N'Đang Giao'
                     	  ,[Updated_at] = CURRENT_TIMESTAMP
                      WHERE ID = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, ngayNhan);
            ps.setObject(2, maNV);
            ps.setObject(3, soDT);
            ps.setObject(4, maGG);
            ps.setObject(5, soDT);
            ps.setObject(6, soDT);
            ps.setObject(7, idHD);
            check = ps.executeUpdate();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean updateHDKH(int idHD, String soDT, String diaChi, String tenKH) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[HoaDon]
                 SET [IDKhachHang] = (SELECT ID FROM KhachHang WHERE dbo.KhachHang.TenKhachHang = ?),
                     [DiaChi] = ?,
                     [SoDienThoai] = ?,
                     [Updated_at] = CURRENT_TIMESTAMP
                 WHERE ID = ?
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, tenKH);
            ps.setObject(2, diaChi);
            ps.setObject(3, soDT);
            ps.setObject(4, idHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

}
