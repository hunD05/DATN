/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.viewmodel.HoaDonViewModel;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author VHC
 */
public class HDViewModelRepo {

    public List<HoaDonViewModel> getAll() {
        List<HoaDonViewModel> listHD = new ArrayList<>();
        String sql = """
                        SELECT dbo.HoaDon.ID, 
                               dbo.HoaDon.MaHoaDon, 
                               dbo.HoaDon.NgayTao, 
                               dbo.HoaDon.NgayThanhToan, 
                               SUM(dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.GiaBan) AS TongThanhTien,
                               dbo.NhanVien.MaNhanVien, 
                               dbo.KhachHang.TenKhachHang, 
                               dbo.HoaDon.DiaChi, 
                               dbo.HoaDon.SoDienThoai, 
                               dbo.HoaDon.TrangThai
                        FROM dbo.HoaDon
                        INNER JOIN dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
                        INNER JOIN dbo.KhachHang ON dbo.HoaDon.IDKhachHang = dbo.KhachHang.ID
                        INNER JOIN dbo.NhanVien ON dbo.HoaDon.IDNhanVien = dbo.NhanVien.ID
                        GROUP BY dbo.HoaDon.ID, 
                                 dbo.HoaDon.MaHoaDon, 
                                 dbo.HoaDon.NgayTao, 
                                 dbo.HoaDon.NgayThanhToan, 
                                 dbo.NhanVien.MaNhanVien, 
                                 dbo.KhachHang.TenKhachHang, 
                                 dbo.HoaDon.DiaChi, 
                                 dbo.HoaDon.SoDienThoai, 
                                 dbo.HoaDon.TrangThai
                        ORDER BY dbo.HoaDon.NgayTao DESC;
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel hd = new HoaDonViewModel();
                hd.setId(rs.getInt(1));
                hd.setMaHD(rs.getString(2));
                hd.setNgayTao(rs.getTimestamp(3).toLocalDateTime());
                hd.setNgayThanhToan(rs.getTimestamp(4).toLocalDateTime());
                hd.setGiaTien(rs.getDouble("TongThanhTien")); // Sửa đổi tên cột thành alias
                hd.setMaNV(rs.getString(6));
                hd.setTenKH(rs.getString(7));
                hd.setDiaChi(rs.getString(8));
                hd.setSoDT(rs.getString(9));
                hd.setTrangThai(rs.getString(10));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDonViewModel> searchHD(String tuKhoa) {
        List<HoaDonViewModel> listHD = new ArrayList<>();
        String sql = """
                    SELECT dbo.HoaDon.ID, 
                           dbo.HoaDon.MaHoaDon, 
                           dbo.HoaDon.NgayTao, 
                           dbo.HoaDon.NgayThanhToan, 
                           SUM(dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.GiaBan) AS TongThanhTien,
                           dbo.NhanVien.MaNhanVien, 
                           dbo.KhachHang.TenKhachHang, 
                           dbo.HoaDon.DiaChi, 
                           dbo.HoaDon.SoDienThoai, 
                           dbo.HoaDon.TrangThai
                    FROM dbo.HoaDon
                    INNER JOIN dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
                    INNER JOIN dbo.KhachHang ON dbo.HoaDon.IDKhachHang = dbo.KhachHang.ID
                    INNER JOIN dbo.NhanVien ON dbo.HoaDon.IDNhanVien = dbo.NhanVien.ID
                    WHERE dbo.HoaDon.MaHoaDon LIKE ?
                       OR dbo.KhachHang.TenKhachHang LIKE ?
                       OR dbo.HoaDon.DiaChi LIKE ?
                       OR dbo.HoaDon.SoDienThoai LIKE ?
                       OR dbo.NhanVien.MaNhanVien LIKE ?
                       OR dbo.KhachHang.TenKhachHang LIKE ?
                       OR dbo.HoaDon.NgayThanhToan LIKE ?
                    GROUP BY dbo.HoaDon.ID, 
                             dbo.HoaDon.MaHoaDon, 
                             dbo.HoaDon.NgayTao, 
                             dbo.HoaDon.NgayThanhToan, 
                             dbo.NhanVien.MaNhanVien, 
                             dbo.KhachHang.TenKhachHang, 
                             dbo.HoaDon.DiaChi, 
                             dbo.HoaDon.SoDienThoai, 
                             dbo.HoaDon.TrangThai;
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, '%' + tuKhoa + '%');
            ps.setObject(2, '%' + tuKhoa + '%');
            ps.setObject(3, '%' + tuKhoa + '%');
            ps.setObject(4, '%' + tuKhoa + '%');
            ps.setObject(5, '%' + tuKhoa + '%');
            ps.setObject(6, '%' + tuKhoa + '%');
            ps.setObject(7, '%' + tuKhoa + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel hd = new HoaDonViewModel();
                hd.setId(rs.getInt(1));
                hd.setMaHD(rs.getString(2));
                hd.setNgayTao(rs.getTimestamp(3).toLocalDateTime());
                hd.setNgayThanhToan(rs.getTimestamp(4).toLocalDateTime());
                hd.setGiaTien(rs.getDouble("TongThanhTien"));
                hd.setMaNV(rs.getString(6));
                hd.setTenKH(rs.getString(7));
                hd.setDiaChi(rs.getString(8));
                hd.setSoDT(rs.getString(9));
                hd.setTrangThai(rs.getString(10));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDonViewModel> searchGiaTien(double min, double max) {
        List<HoaDonViewModel> listHD = new ArrayList<>();
        String sql = """                    
                    SELECT 
                        dbo.HoaDon.ID, 
                        dbo.HoaDon.MaHoaDon, 
                        dbo.HoaDon.NgayTao, 
                        dbo.HoaDon.NgayThanhToan,  
                        SUM(dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.GiaBan) AS TongThanhTien, 
                        dbo.NhanVien.MaNhanVien, 
                        dbo.KhachHang.TenKhachHang, 
                        dbo.HoaDon.DiaChi, 
                        dbo.HoaDon.SoDienThoai, 
                        dbo.HoaDon.TrangThai
                    FROM
                        dbo.HoaDon
                    INNER JOIN
                        dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
                    INNER JOIN
                        dbo.KhachHang ON dbo.HoaDon.IDKhachHang = dbo.KhachHang.ID
                    INNER JOIN
                        dbo.NhanVien ON dbo.HoaDon.IDNhanVien = dbo.NhanVien.ID
                    GROUP BY 
                        dbo.HoaDon.ID, 
                        dbo.HoaDon.MaHoaDon, 
                        dbo.HoaDon.NgayTao, 
                        dbo.HoaDon.NgayThanhToan,  
                        dbo.HoaDonChiTiet.GiaBan, 
                        dbo.NhanVien.MaNhanVien, 
                        dbo.KhachHang.TenKhachHang, 
                        dbo.HoaDon.DiaChi, 
                        dbo.HoaDon.SoDienThoai, 
                        dbo.HoaDon.TrangThai
                    HAVING 
                        SUM(dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.GiaBan) BETWEEN ? AND ?;
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, min);
            ps.setObject(2, max);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel hd = new HoaDonViewModel();
                hd.setId(rs.getInt(1));
                hd.setMaHD(rs.getString(2));
                hd.setNgayTao(rs.getTimestamp(3).toLocalDateTime());
                hd.setNgayThanhToan(rs.getTimestamp(4).toLocalDateTime());
                hd.setGiaTien(rs.getDouble("TongThanhTien"));
                hd.setMaNV(rs.getString(6));
                hd.setTenKH(rs.getString(7));
                hd.setDiaChi(rs.getString(8));
                hd.setSoDT(rs.getString(9));
                hd.setTrangThai(rs.getString(10));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDonViewModel> searchTT(String trangThai) {
        List<HoaDonViewModel> listHD = new ArrayList<>();
        String sql = """
                            SELECT dbo.HoaDon.ID, 
                                                                        dbo.HoaDon.MaHoaDon, 
                                                                        dbo.HoaDon.NgayTao, 
                                                                        dbo.HoaDon.NgayThanhToan, 
                                                                        SUM(dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.GiaBan) AS TongThanhTien,
                                                                        dbo.NhanVien.MaNhanVien, 
                                                                        dbo.KhachHang.TenKhachHang, 
                                                                        dbo.HoaDon.DiaChi, 
                                                                        dbo.HoaDon.SoDienThoai, 
                                                                        dbo.HoaDon.TrangThai
                                                                 FROM dbo.HoaDon
                                                                 INNER JOIN dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
                                                                 INNER JOIN dbo.KhachHang ON dbo.HoaDon.IDKhachHang = dbo.KhachHang.ID
                                                                 INNER JOIN dbo.NhanVien ON dbo.HoaDon.IDNhanVien = dbo.NhanVien.ID
                                                                 WHERE dbo.HoaDon.TrangThai LIKE ?
                                                                 GROUP BY dbo.HoaDon.ID, 
                                                                          dbo.HoaDon.MaHoaDon, 
                                                                          dbo.HoaDon.NgayTao, 
                                                                          dbo.HoaDon.NgayThanhToan, 
                                                                          dbo.NhanVien.MaNhanVien, 
                                                                          dbo.KhachHang.TenKhachHang, 
                                                                          dbo.HoaDon.DiaChi, 
                                                                          dbo.HoaDon.SoDienThoai, 
                                                                          dbo.HoaDon.TrangThai;
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel hd = new HoaDonViewModel();
                hd.setId(rs.getInt(1));
                hd.setMaHD(rs.getString(2));
                hd.setNgayTao(rs.getTimestamp(3).toLocalDateTime());
                hd.setNgayThanhToan(rs.getTimestamp(4).toLocalDateTime());
                hd.setGiaTien(rs.getDouble("TongThanhTien"));
                hd.setMaNV(rs.getString(6));
                hd.setTenKH(rs.getString(7));
                hd.setDiaChi(rs.getString(8));
                hd.setSoDT(rs.getString(9));
                hd.setTrangThai(rs.getString(10));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDonViewModel> getOne(int idHD) {
        List<HoaDonViewModel> listHD = new ArrayList<>();
        String sql = """
                        SELECT dbo.HoaDon.ID, 
                                        dbo.HoaDon.MaHoaDon, 
                                        dbo.HoaDon.NgayTao, 
                                        dbo.HoaDon.NgayThanhToan, 
                                        SUM(dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.GiaBan) AS TongThanhTien,
                                        dbo.NhanVien.MaNhanVien, 
                                        dbo.KhachHang.TenKhachHang, 
                                        dbo.HoaDon.DiaChi, 
                                        dbo.HoaDon.SoDienThoai, 
                                        dbo.HoaDon.TrangThai
                                 FROM dbo.HoaDon
                                 INNER JOIN dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
                                 INNER JOIN dbo.KhachHang ON dbo.HoaDon.IDKhachHang = dbo.KhachHang.ID
                                 INNER JOIN dbo.NhanVien ON dbo.HoaDon.IDNhanVien = dbo.NhanVien.ID
                                 WHERE dbo.HoaDon.ID LIKE ?
                                 GROUP BY dbo.HoaDon.ID, 
                                          dbo.HoaDon.MaHoaDon, 
                                          dbo.HoaDon.NgayTao, 
                                          dbo.HoaDon.NgayThanhToan, 
                                          dbo.NhanVien.MaNhanVien, 
                                          dbo.KhachHang.TenKhachHang, 
                                          dbo.HoaDon.DiaChi, 
                                          dbo.HoaDon.SoDienThoai, 
                                          dbo.HoaDon.TrangThai;
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel hd = new HoaDonViewModel();
                hd.setId(rs.getInt(1));
                hd.setMaHD(rs.getString(2));
                hd.setNgayTao(rs.getTimestamp(3).toLocalDateTime());
                hd.setNgayThanhToan(rs.getTimestamp(4).toLocalDateTime());
                hd.setGiaTien(rs.getDouble("TongThanhTien")); // Sửa đổi tên cột thành alias
                hd.setMaNV(rs.getString(6));
                hd.setTenKH(rs.getString(7));
                hd.setDiaChi(rs.getString(8));
                hd.setSoDT(rs.getString(9));
                hd.setTrangThai(rs.getString(10));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }
}
