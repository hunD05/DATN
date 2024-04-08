/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.PhieuGiamGia;
import backend.viewmodel.PhieuGiamGiaViewModel;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author thanh
 */
public class PhieuGiamGiaRepo {

    public List<PhieuGiamGiaViewModel> getAll() {
        List<PhieuGiamGiaViewModel> listPGG = new ArrayList<>();
        String sql = """
                    SELECT       dbo.PhieuGiamGia.ID, dbo.PhieuGiamGia.MaGiamGia, dbo.PhieuGiamGia.TenGiamGia, dbo.PhieuGiamGia.NgayBatDau, dbo.PhieuGiamGia.NgayKetThuc, dbo.PhieuGiamGia.SoLuong, dbo.PhieuGiamGia.HoaDonToiThieu, 
                                             dbo.PhieuGiamGia.SoPhanTramGiam, dbo.PhieuGiamGia.GiamToiDa, dbo.NhanVien.ID AS Expr1, dbo.NhanVien.SoDienThoai, dbo.NhanVien.NgaySinh, dbo.PhieuGiamGia.TrangThai
                    FROM            dbo.NhanVien INNER JOIN
                                             dbo.PhieuGiamGia ON dbo.NhanVien.ID = dbo.PhieuGiamGia.IDNhanVien
                    WHERE dbo.PhieuGiamGia.Deleted = 0 ORDER BY dbo.PhieuGiamGia.Created_at DESC
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGiaViewModel pgg = new PhieuGiamGiaViewModel();
                pgg.setId(rs.getInt(1));
                pgg.setMaGiamGia(rs.getString(2));
                pgg.setTenGiamGia(rs.getString(3));
                pgg.setNgayBatDau(rs.getDate(4));
                pgg.setNgayKetThuc(rs.getDate(5));
                pgg.setSoLuong(rs.getInt(6));
                pgg.setHoaDonToiThieu(rs.getFloat(7));
                pgg.setSoPhanTramGiam(rs.getFloat(8));
                pgg.setGiamToiDa(rs.getFloat(9));
                pgg.setIdNhanVien(rs.getInt(10));
                pgg.setSdt(rs.getString(11));
                pgg.setNgaySinh(rs.getDate(12));
                pgg.setTrangThai(rs.getString(13));
                listPGG.add(pgg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPGG;
    }

    public boolean add(PhieuGiamGia pgg) {
        int check = 0;
        String sql = """
                 INSERT INTO [dbo].[PhieuGiamGia]
                            ([TenGiamGia]
                            ,[NgayBatDau]
                            ,[NgayKetThuc]
                            ,[SoLuong]
                            ,[HoaDonToiThieu]
                            ,[SoPhanTramGiam]
                            ,[GiamToiDa]
                            ,[IDNhanVien]
                            ,[TrangThai])
                      VALUES
                            (?,?,?,?,?,?,?,?,?)
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, pgg.getTenGiamGia());
            ps.setObject(2, pgg.getNgayBatDau());
            ps.setObject(3, pgg.getNgayKetThuc());
            ps.setObject(4, pgg.getSoLuong());
            ps.setObject(5, pgg.getHoaDonToiThieu());
            ps.setObject(6, pgg.getSoPhanTramGiam());
            ps.setObject(7, pgg.getGiamToiDa());
            ps.setObject(8, pgg.getIdNV());
            ps.setObject(9, pgg.getTrangThai());
            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean Xoa(String IdGiamGia) {
        int check = 0;

        String sql = """
                   UPDATE [dbo].[PhieuGiamGia]
                      SET 
                         [Deleted] = 1
                    WHERE ID = ?
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, IdGiamGia);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean Sua(PhieuGiamGia pgg, String newID) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[PhieuGiamGia]
                        SET [TenGiamGia] = ?
                           ,[NgayBatDau] = ?
                           ,[NgayKetThuc] = ?
                           ,[SoLuong] = ?
                           ,[HoaDonToiThieu] = ?
                           ,[SoPhanTramGiam] = ?
                           ,[GiamToiDa] = ?
                           ,[TrangThai] = ?
                      WHERE ID = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, pgg.getTenGiamGia());
            ps.setObject(2, pgg.getNgayBatDau());
            ps.setObject(3, pgg.getNgayKetThuc());
            ps.setObject(4, pgg.getSoLuong());
            ps.setObject(5, pgg.getHoaDonToiThieu());
            ps.setObject(6, pgg.getSoPhanTramGiam());
            ps.setObject(7, pgg.getGiamToiDa());
            ps.setObject(8, pgg.getTrangThai());
            ps.setObject(9, newID);
            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<PhieuGiamGiaViewModel> search(String ten) {
        List<PhieuGiamGiaViewModel> listSearch = new ArrayList<>();
        String sql = """
                SELECT PGG.ID, PGG.MaGiamGia, PGG.TenGiamGia, PGG.NgayBatDau, PGG.NgayKetThuc, PGG.SoLuong, PGG.HoaDonToiThieu, 
                                                                PGG.SoPhanTramGiam, PGG.GiamToiDa, NV.ID AS NhanVien, NV.SoDienThoai, NV.NgaySinh, PGG.TrangThai
                                                                FROM dbo.NhanVien NV
                                                                INNER JOIN dbo.PhieuGiamGia PGG ON NV.ID = PGG.IDNhanVien
                                                                WHERE PGG.TenGiamGia LIKE ? or
                                				        PGG.MaGiamGia LIKE ? or
                							PGG.SoLuong Like ? or
                							PGG.NgayKetThuc Like ? or
                							PGG.NgayBatDau Like ? or
                							PGG.HoaDonToiThieu Like ? or
                							PGG.SoPhanTramGiam Like ? or
                							PGG.GiamToiDa Like ? 
                								      
                     
                    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, '%' + ten + '%');
            ps.setObject(2, '%' + ten + '%');
            ps.setObject(3, '%' + ten + '%');
            ps.setObject(4, '%' + ten + '%');
            ps.setObject(5, '%' + ten + '%');
            ps.setObject(6, '%' + ten + '%');
            ps.setObject(7, '%' + ten + '%');
            ps.setObject(8, '%' + ten + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGiaViewModel pggv = new PhieuGiamGiaViewModel();
                pggv.setId(rs.getInt(1));
                pggv.setMaGiamGia(rs.getString(2));
                pggv.setTenGiamGia(rs.getString(3));
                pggv.setNgayBatDau(rs.getDate(4));
                pggv.setNgayKetThuc(rs.getDate(5));
                pggv.setSoLuong(rs.getInt(6));
                pggv.setHoaDonToiThieu(rs.getFloat(7));
                pggv.setSoPhanTramGiam(rs.getFloat(8));
                pggv.setGiamToiDa(rs.getFloat(9));
                pggv.setIdNhanVien(rs.getInt(10));
                pggv.setSdt(rs.getString(11));
                pggv.setNgaySinh(rs.getDate(12));
                pggv.setTrangThai(rs.getString(13));
                listSearch.add(pggv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSearch;
    }

    public List<PhieuGiamGiaViewModel> trangThai(String tt) {
        List<PhieuGiamGiaViewModel> listSearch = new ArrayList<>();
        String sql = """
                SELECT PGG.ID, PGG.MaGiamGia, PGG.TenGiamGia, PGG.NgayBatDau, PGG.NgayKetThuc, PGG.SoLuong, PGG.HoaDonToiThieu, 
                PGG.SoPhanTramGiam, PGG.GiamToiDa, NV.ID AS NhanVien, NV.SoDienThoai, NV.NgaySinh, PGG.TrangThai
                FROM dbo.NhanVien NV
                INNER JOIN dbo.PhieuGiamGia PGG ON NV.ID = PGG.IDNhanVien
                WHERE PGG.TrangThai = ?
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGiaViewModel pggv = new PhieuGiamGiaViewModel();
                pggv.setId(rs.getInt(1));
                pggv.setMaGiamGia(rs.getString(2));
                pggv.setTenGiamGia(rs.getString(3));
                pggv.setNgayBatDau(rs.getDate(4));
                pggv.setNgayKetThuc(rs.getDate(5));
                pggv.setSoLuong(rs.getInt(6));
                pggv.setHoaDonToiThieu(rs.getFloat(7));
                pggv.setSoPhanTramGiam(rs.getFloat(8));
                pggv.setGiamToiDa(rs.getFloat(9));
                pggv.setIdNhanVien(rs.getInt(10));
                pggv.setSdt(rs.getString(11));
                pggv.setNgaySinh(rs.getDate(12));
                pggv.setTrangThai(rs.getString(13));
                listSearch.add(pggv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSearch;
    }
    
    public List<PhieuGiamGiaViewModel> searchTT(String tenGG) {
        List<PhieuGiamGiaViewModel> listPGG = new ArrayList<>();
        String sql = """
                    SELECT       dbo.PhieuGiamGia.ID, dbo.PhieuGiamGia.MaGiamGia, dbo.PhieuGiamGia.TenGiamGia, dbo.PhieuGiamGia.NgayBatDau, dbo.PhieuGiamGia.NgayKetThuc, dbo.PhieuGiamGia.SoLuong, dbo.PhieuGiamGia.HoaDonToiThieu, 
                                             dbo.PhieuGiamGia.SoPhanTramGiam, dbo.PhieuGiamGia.GiamToiDa, dbo.NhanVien.ID AS Expr1, dbo.NhanVien.SoDienThoai, dbo.NhanVien.NgaySinh, dbo.PhieuGiamGia.TrangThai
                    FROM            dbo.NhanVien INNER JOIN
                                             dbo.PhieuGiamGia ON dbo.NhanVien.ID = dbo.PhieuGiamGia.IDNhanVien
                    WHERE dbo.PhieuGiamGia.Deleted = 0 AND dbo.PhieuGiamGia.TenGiamGia LIKE ? ORDER BY dbo.PhieuGiamGia.Created_at DESC
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tenGG);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGiaViewModel pgg = new PhieuGiamGiaViewModel();
                pgg.setId(rs.getInt(1));
                pgg.setMaGiamGia(rs.getString(2));
                pgg.setTenGiamGia(rs.getString(3));
                pgg.setNgayBatDau(rs.getDate(4));
                pgg.setNgayKetThuc(rs.getDate(5));
                pgg.setSoLuong(rs.getInt(6));
                pgg.setHoaDonToiThieu(rs.getFloat(7));
                pgg.setSoPhanTramGiam(rs.getFloat(8));
                pgg.setGiamToiDa(rs.getFloat(9));
                pgg.setIdNhanVien(rs.getInt(10));
                pgg.setSdt(rs.getString(11));
                pgg.setNgaySinh(rs.getDate(12));
                pgg.setTrangThai(rs.getString(13));
                listPGG.add(pgg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPGG;
    }
    
        public List<PhieuGiamGiaViewModel> sortGG() {
        List<PhieuGiamGiaViewModel> listPGG = new ArrayList<>();
        String sql = """
                    SELECT dbo.PhieuGiamGia.ID, dbo.PhieuGiamGia.MaGiamGia, dbo.PhieuGiamGia.TenGiamGia, dbo.PhieuGiamGia.NgayBatDau, dbo.PhieuGiamGia.NgayKetThuc, dbo.PhieuGiamGia.SoLuong, dbo.PhieuGiamGia.HoaDonToiThieu, 
                           dbo.PhieuGiamGia.SoPhanTramGiam, dbo.PhieuGiamGia.GiamToiDa, dbo.NhanVien.ID AS Expr1, dbo.NhanVien.SoDienThoai, dbo.NhanVien.NgaySinh, dbo.PhieuGiamGia.TrangThai
                    FROM   dbo.NhanVien INNER JOIN
                           dbo.PhieuGiamGia ON dbo.NhanVien.ID = dbo.PhieuGiamGia.IDNhanVien
                    WHERE dbo.PhieuGiamGia.Deleted = 0 
                    ORDER BY  dbo.PhieuGiamGia.GiamToiDa DESC, dbo.PhieuGiamGia.Created_at DESC
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGiaViewModel pgg = new PhieuGiamGiaViewModel();
                pgg.setId(rs.getInt(1));
                pgg.setMaGiamGia(rs.getString(2));
                pgg.setTenGiamGia(rs.getString(3));
                pgg.setNgayBatDau(rs.getDate(4));
                pgg.setNgayKetThuc(rs.getDate(5));
                pgg.setSoLuong(rs.getInt(6));
                pgg.setHoaDonToiThieu(rs.getFloat(7));
                pgg.setSoPhanTramGiam(rs.getFloat(8));
                pgg.setGiamToiDa(rs.getFloat(9));
                pgg.setIdNhanVien(rs.getInt(10));
                pgg.setSdt(rs.getString(11));
                pgg.setNgaySinh(rs.getDate(12));
                pgg.setTrangThai(rs.getString(13));
                listPGG.add(pgg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPGG;
    }
        
//                    txtSoDT.setText("");
//            txtTenKH.setText("");
//            txtTenKH2.setText("");
//            cbbPGG.setSelectedItem(null);
//            txtMaHD.setText("");
//            txtNTao.setText("");
//            txtNTToan.setText("");
//            txtMaNV.setText("");
//            txtTongTien.setText("");
//        [255,51,51]
}
