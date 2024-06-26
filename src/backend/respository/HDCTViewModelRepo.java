/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.viewmodel.HDCTViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VHC
 */
public class HDCTViewModelRepo {

    public List<HDCTViewModel> getAll(int idHD) {
        List<HDCTViewModel> listHDCT = new ArrayList<>();
        String sql = """
                                SELECT    dbo.HoaDonChiTiet.IDHoaDon, dbo.ChiTietSanPham.ID, dbo.SanPham.TenSanPham, dbo.HoaDonChiTiet.SoLuong, dbo.ChiTietSanPham.GiaBan, dbo.MauSac.TenMauSac, dbo.Size.TenSize, 
                                                     dbo.ChatLieu.TenChatLieu, dbo.ThuongHieu.TenThuongHieu
                               FROM         dbo.ChiTietSanPham INNER JOIN
                                                     dbo.HoaDonChiTiet ON dbo.ChiTietSanPham.ID = dbo.HoaDonChiTiet.IDChiTietSP INNER JOIN
                                                     dbo.SanPham ON dbo.ChiTietSanPham.IDSanPham = dbo.SanPham.ID INNER JOIN
                                                     dbo.ChatLieu ON dbo.ChiTietSanPham.IDChatLieu = dbo.ChatLieu.ID INNER JOIN
                                                     dbo.MauSac ON dbo.ChiTietSanPham.IDMauSac = dbo.MauSac.ID INNER JOIN
                                                     dbo.Size ON dbo.ChiTietSanPham.IDSize = dbo.Size.ID INNER JOIN
                                                     dbo.ThuongHieu ON dbo.ChiTietSanPham.IDThuongHieu = dbo.ThuongHieu.ID
                                                        WHERE dbo.HoaDonChiTiet.IDHoaDon = ? AND dbo.HoaDonChiTiet.Deleted = 0
                                                        ORDER BY HoaDonChiTiet.Updated_at DESC;
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HDCTViewModel hdct = new HDCTViewModel();
                hdct.setId(rs.getInt(1));
                hdct.setMaSPCT(rs.getInt(2));
                hdct.setTenSP(rs.getString(3));
                hdct.setSoLuong(rs.getInt(4));
                hdct.setGiaBan(rs.getDouble(5));
                hdct.setMauSac(rs.getString(6));
                hdct.setSize(rs.getString(7));
                hdct.setChatLieu(rs.getString(8));
                hdct.setThuongHieu(rs.getString(9));
                listHDCT.add(hdct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHDCT;
    }

    public List<HDCTViewModel> searchCBBSP(int idHD, String mauSac, String size, String chatLieu, String thuongHieu, String gia) {
        List<HDCTViewModel> listHDCT = new ArrayList<>();
        String sql = """
                                SELECT    dbo.HoaDonChiTiet.IDHoaDon, dbo.ChiTietSanPham.ID, dbo.SanPham.TenSanPham, dbo.HoaDonChiTiet.SoLuong, dbo.ChiTietSanPham.GiaBan, dbo.MauSac.TenMauSac, dbo.Size.TenSize, 
                                                                      dbo.ChatLieu.TenChatLieu, dbo.ThuongHieu.TenThuongHieu
                                                FROM         dbo.ChiTietSanPham INNER JOIN
                                                                      dbo.HoaDonChiTiet ON dbo.ChiTietSanPham.ID = dbo.HoaDonChiTiet.IDChiTietSP INNER JOIN
                                                                      dbo.SanPham ON dbo.ChiTietSanPham.IDSanPham = dbo.SanPham.ID INNER JOIN
                                                                      dbo.ChatLieu ON dbo.ChiTietSanPham.IDChatLieu = dbo.ChatLieu.ID INNER JOIN
                                                                      dbo.MauSac ON dbo.ChiTietSanPham.IDMauSac = dbo.MauSac.ID INNER JOIN
                                                                      dbo.Size ON dbo.ChiTietSanPham.IDSize = dbo.Size.ID INNER JOIN
                                                                      dbo.ThuongHieu ON dbo.ChiTietSanPham.IDThuongHieu = dbo.ThuongHieu.ID
                                                                         WHERE dbo.HoaDonChiTiet.IDHoaDon = ? AND dbo.HoaDonChiTiet.Deleted = 0
                     ORDER BY HoaDonChiTiet.Updated_at DESC;
                 """;
        if (mauSac != null && !mauSac.isEmpty()) {
            sql += " AND dbo.MauSac.TenMauSac = N'" + mauSac + "'";
        }
        if (size != null && !size.isEmpty()) {
            sql += " AND  dbo.Size.TenSize = N'" + size + "'";
        }
        if (chatLieu != null && !chatLieu.isEmpty()) {
            sql += " AND  dbo.ChatLieu.TenChatLieu = N'" + chatLieu + "'";
        }
        if (thuongHieu != null && !thuongHieu.isEmpty()) {
            sql += " AND dbo.ThuongHieu.TenThuongHieu = N'" + thuongHieu + "'";
        }
        if ("GiaBan ASC".equals(gia)) {
            sql += " ORDER BY dbo.ChiTietSanPham.GiaBan ASC";
        } else if ("GiaBan DESC".equals(gia)) {
            sql += " ORDER BY dbo.ChiTietSanPham.GiaBan DESC";
        }
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HDCTViewModel hdct = new HDCTViewModel();
                hdct.setId(rs.getInt(1));
                hdct.setMaSPCT(rs.getInt(2));
                hdct.setTenSP(rs.getString(3));
                hdct.setSoLuong(rs.getInt(4));
                hdct.setGiaBan(rs.getDouble(5));
                hdct.setMauSac(rs.getString(6));
                hdct.setSize(rs.getString(7));
                hdct.setChatLieu(rs.getString(8));
                hdct.setThuongHieu(rs.getString(9));
                listHDCT.add(hdct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHDCT;
    }

    public List<HDCTViewModel> searchTheoTT(int idHD, String tuKhoa) {
        List<HDCTViewModel> listHDCT = new ArrayList<>();
        String sql = """
                                SELECT    dbo.HoaDonChiTiet.IDHoaDon, dbo.ChiTietSanPham.ID, dbo.SanPham.TenSanPham, dbo.HoaDonChiTiet.SoLuong, dbo.ChiTietSanPham.GiaBan, dbo.MauSac.TenMauSac, dbo.Size.TenSize, 
                                                                      dbo.ChatLieu.TenChatLieu, dbo.ThuongHieu.TenThuongHieu
                                                FROM         dbo.ChiTietSanPham INNER JOIN
                                                                      dbo.HoaDonChiTiet ON dbo.ChiTietSanPham.ID = dbo.HoaDonChiTiet.IDChiTietSP INNER JOIN
                                                                      dbo.SanPham ON dbo.ChiTietSanPham.IDSanPham = dbo.SanPham.ID INNER JOIN
                                                                      dbo.ChatLieu ON dbo.ChiTietSanPham.IDChatLieu = dbo.ChatLieu.ID INNER JOIN
                                                                      dbo.MauSac ON dbo.ChiTietSanPham.IDMauSac = dbo.MauSac.ID INNER JOIN
                                                                      dbo.Size ON dbo.ChiTietSanPham.IDSize = dbo.Size.ID INNER JOIN
                                                                      dbo.ThuongHieu ON dbo.ChiTietSanPham.IDThuongHieu = dbo.ThuongHieu.ID
                                                                      WHERE dbo.HoaDonChiTiet.IDHoaDon = ? AND dbo.HoaDonChiTiet.Deleted = 0 AND (dbo.SanPham.TenSanPham LIKE ? OR dbo.MauSac.TenMauSac LIKE ? OR
                                                                      dbo.Size.TenSize LIKE ? OR dbo.ChatLieu.TenChatLieu LIKE ? OR dbo.ThuongHieu.TenThuongHieu LIKE ?)
                     ORDER BY HoaDonChiTiet.Updated_at DESC;
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idHD);
            ps.setObject(2, '%' + tuKhoa + '%');
            ps.setObject(3, '%' + tuKhoa + '%');
            ps.setObject(4, '%' + tuKhoa + '%');
            ps.setObject(5, '%' + tuKhoa + '%');
            ps.setObject(6, '%' + tuKhoa + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HDCTViewModel hdct = new HDCTViewModel();
                hdct.setId(rs.getInt(1));
                hdct.setMaSPCT(rs.getInt(2));
                hdct.setTenSP(rs.getString(3));
                hdct.setSoLuong(rs.getInt(4));
                hdct.setGiaBan(rs.getDouble(5));
                hdct.setMauSac(rs.getString(6));
                hdct.setSize(rs.getString(7));
                hdct.setChatLieu(rs.getString(8));
                hdct.setThuongHieu(rs.getString(9));
                listHDCT.add(hdct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHDCT;
    }

}
