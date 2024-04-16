/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.ChiTietSanPham;
import backend.viewmodel.SanPhamChiTietViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author leanb
 */
public class ChiTietSanPhamRespository {

    public List<SanPhamChiTietViewModel> search(String danhMuc, String xuatXu, String nsx, String gia) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
    SELECT 
        ROW_NUMBER() OVER (ORDER BY ctsp.Updated_at DESC) AS RowNumber,
        sp.TenSanPham, 
        ctsp.GiaBan, 
        ctsp.SoLuong, 
        dm.TenDanhMuc, 
        xx.TenXuatXu, 
        nsx.TenNSX, 
        ms.TenMauSac, 
        sz.TenSize, 
        th.TenThuongHieu, 
        cl.TenChatLieu, 
        ca.TenCoAo, 
        da.TenDuoiAo, 
        ta.TenTayAo, 
        dangao.TenDangAo,
        ctsp.ID,
        ctsp.MoTa,
        ctsp.TrangThai
    FROM 
        dbo.ChatLieu cl 
    INNER JOIN dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
    INNER JOIN dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
    INNER JOIN dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
    INNER JOIN dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
    INNER JOIN dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
    INNER JOIN dbo.Size sz ON ctsp.IDSize = sz.ID 
    INNER JOIN dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
    INNER JOIN dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
    INNER JOIN dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
    INNER JOIN dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
    INNER JOIN dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
    INNER JOIN dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
    WHERE 
        ctsp.Deleted = 0
    """;

        if (xuatXu != null && !xuatXu.isEmpty()) {
            sql += " AND xx.TenXuatXu = N'" + xuatXu + "'";
        }
        if (danhMuc != null && !danhMuc.isEmpty()) {
            sql += " AND dm.TenDanhMuc = '" + danhMuc + "'";
        }
        if (nsx != null && !nsx.isEmpty()) {
            sql += " AND nsx.TenNSX = '" + nsx + "'";
        }
        if ("GiaBan ASC".equals(gia)) {
            sql += " ORDER BY ctsp.GiaBan ASC";
        } else if ("GiaBan DESC".equals(gia)) {
            sql += " ORDER BY ctsp.GiaBan DESC";
        } else {
            sql += " ORDER BY ctsp.Updated_at DESC";
        }

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setSTT(rs.getInt("RowNumber"));
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));

                // Định dạng giá bán
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));

                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));
                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
            if (!rs.next()) {
                System.out.println("Không có dữ liệu phù hợp với điều kiện.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamChiTietViewModels;
    }

    public List<SanPhamChiTietViewModel> getAll() {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
        SELECT 
                            ROW_NUMBER() OVER (ORDER BY ctsp.Updated_at DESC) AS RowNumber,
                            sp.TenSanPham, 
                            ctsp.GiaBan, 
                            ctsp.SoLuong, 
                            dm.TenDanhMuc, 
                            xx.TenXuatXu, 
                            nsx.TenNSX, 
                            ms.TenMauSac, 
                            sz.TenSize, 
                            th.TenThuongHieu, 
                            cl.TenChatLieu, 
                            ca.TenCoAo, 
                            da.TenDuoiAo, 
                            ta.TenTayAo, 
                            dangao.TenDangAo,
                            ctsp.ID,
                            ctsp.MoTa,
                            ctsp.TrangThai
                        FROM 
                            dbo.ChatLieu cl 
                        INNER JOIN dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
                        INNER JOIN dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
                        INNER JOIN dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
                        INNER JOIN dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
                        INNER JOIN dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
                        INNER JOIN dbo.Size sz ON ctsp.IDSize = sz.ID 
                        INNER JOIN dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
                        INNER JOIN dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
                        INNER JOIN dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
                        INNER JOIN dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
                        INNER JOIN dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
                        INNER JOIN dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
                        WHERE 
                            ctsp.Deleted = 0
                        ORDER BY 
                            ctsp.Updated_at DESC;
    """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setSTT(rs.getInt("RowNumber"));
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));

                // Định dạng giá bán
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));

                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));
                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamChiTietViewModels;
    }

    public SanPhamChiTietViewModel getById(long id) {
        SanPhamChiTietViewModel sanPhamChiTietViewModel = null;
        String sql = """
        SELECT 
                    
                    sp.TenSanPham, 
                    ctsp.GiaBan, 
                    ctsp.SoLuong, 
                    dm.TenDanhMuc, 
                    xx.TenXuatXu, 
                    nsx.TenNSX, 
                    ms.TenMauSac, 
                    sz.TenSize, 
                    th.TenThuongHieu, 
                    cl.TenChatLieu, 
                    ca.TenCoAo, 
                    da.TenDuoiAo, 
                    ta.TenTayAo, 
                    dangao.TenDangAo,
                    ctsp.ID,
        			ctsp.MoTa,
        			ctsp.TrangThai
                FROM 
                    dbo.ChatLieu cl 
                INNER JOIN 
                    dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
                INNER JOIN 
                    dbo.ChucVu cv ON cl.ID = cv.ID 
                INNER JOIN 
                    dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
                INNER JOIN 
                    dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
                INNER JOIN 
                    dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
                INNER JOIN 
                    dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
                INNER JOIN 
                    dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
                INNER JOIN 
                    dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
                INNER JOIN 
                    dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
                INNER JOIN 
                    dbo.Size sz ON ctsp.IDSize = sz.ID 
                INNER JOIN 
                    dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
                INNER JOIN 
                    dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
                INNER JOIN 
                    dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
                WHERE 
                    ctsp.ID = ?;
    """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));
                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamChiTietViewModel;
    }

    public List<SanPhamChiTietViewModel> DanhMuc(String danhMuc) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
                 SELECT 
                         ROW_NUMBER() OVER (ORDER BY ctsp.Updated_at DESC) AS RowNumber,
                         sp.TenSanPham, 
                         ctsp.GiaBan, 
                         ctsp.SoLuong, 
                         dm.TenDanhMuc, 
                         xx.TenXuatXu, 
                         nsx.TenNSX, 
                         ms.TenMauSac, 
                         sz.TenSize, 
                         th.TenThuongHieu, 
                         cl.TenChatLieu, 
                         ca.TenCoAo, 
                         da.TenDuoiAo, 
                         ta.TenTayAo, 
                         dangao.TenDangAo, 
                         ctsp.ID, 
                         ctsp.MoTa,
                         ctsp.TrangThai
                     FROM 
                         dbo.ChatLieu cl 
                     INNER JOIN 
                         dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
                     INNER JOIN 
                         dbo.ChucVu cv ON cl.ID = cv.ID 
                     INNER JOIN 
                         dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
                     INNER JOIN 
                         dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
                     INNER JOIN 
                         dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
                     INNER JOIN 
                         dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
                     INNER JOIN 
                         dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
                     INNER JOIN 
                         dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
                     INNER JOIN 
                         dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
                     INNER JOIN 
                         dbo.Size sz ON ctsp.IDSize = sz.ID 
                     INNER JOIN 
                         dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
                     INNER JOIN 
                         dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
                     INNER JOIN 
                         dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
                     WHERE 
                         ctsp.Deleted = 0 AND dm.TenDanhMuc = ?
                     ORDER BY 
                         ctsp.Updated_at DESC;
             """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, danhMuc);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setSTT(rs.getInt("RowNumber"));
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));
                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));

                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamChiTietViewModels;
    }

    public List<SanPhamChiTietViewModel> NSX(String danhMuc) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
                 SELECT 
                         ROW_NUMBER() OVER (ORDER BY ctsp.Updated_at DESC) AS RowNumber,
                         sp.TenSanPham, 
                         ctsp.GiaBan, 
                         ctsp.SoLuong, 
                         dm.TenDanhMuc, 
                         xx.TenXuatXu, 
                         nsx.TenNSX, 
                         ms.TenMauSac, 
                         sz.TenSize, 
                         th.TenThuongHieu, 
                         cl.TenChatLieu, 
                         ca.TenCoAo, 
                         da.TenDuoiAo, 
                         ta.TenTayAo,
                         dangao.TenDangAo,
                         ctsp.ID,
                         ctsp.MoTa,
                         ctsp.TrangThai
                     FROM 
                         dbo.ChatLieu cl 
                     INNER JOIN 
                         dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
                     INNER JOIN 
                         dbo.ChucVu cv ON cl.ID = cv.ID 
                     INNER JOIN 
                         dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
                     INNER JOIN 
                         dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
                     INNER JOIN 
                         dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
                     INNER JOIN 
                         dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
                     INNER JOIN 
                         dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
                     INNER JOIN 
                         dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
                     INNER JOIN 
                         dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
                     INNER JOIN 
                         dbo.Size sz ON ctsp.IDSize = sz.ID 
                     INNER JOIN 
                         dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
                     INNER JOIN 
                         dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
                     INNER JOIN 
                         dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
                     WHERE 
                         ctsp.Deleted = 0 AND nsx.TenNSX = ?
                     ORDER BY 
                         ctsp.Updated_at DESC;
             """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, danhMuc);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setSTT(rs.getInt("RowNumber"));
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));
                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));
                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamChiTietViewModels;
    }

    public List<SanPhamChiTietViewModel> XuatXu(String danhMuc) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
                 SELECT 
                         ROW_NUMBER() OVER (ORDER BY ctsp.Updated_at DESC) AS RowNumber,
                         sp.TenSanPham, 
                         ctsp.GiaBan, 
                         ctsp.SoLuong, 
                         dm.TenDanhMuc, 
                         xx.TenXuatXu, 
                         nsx.TenNSX, 
                         ms.TenMauSac, 
                         sz.TenSize, 
                         th.TenThuongHieu, 
                         cl.TenChatLieu, 
                         ca.TenCoAo, 
                         da.TenDuoiAo, 
                         ta.TenTayAo,
                         dangao.TenDangAo,
                         ctsp.ID,
                         ctsp.MoTa,
                         ctsp.TrangThai
                     FROM 
                         dbo.ChatLieu cl 
                     INNER JOIN 
                         dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
                     INNER JOIN 
                         dbo.ChucVu cv ON cl.ID = cv.ID 
                     INNER JOIN 
                         dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
                     INNER JOIN 
                         dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
                     INNER JOIN 
                         dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
                     INNER JOIN 
                         dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
                     INNER JOIN 
                         dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
                     INNER JOIN 
                         dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
                     INNER JOIN 
                         dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
                     INNER JOIN 
                         dbo.Size sz ON ctsp.IDSize = sz.ID 
                     INNER JOIN 
                         dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
                     INNER JOIN 
                         dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
                     INNER JOIN 
                         dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
                     WHERE 
                         ctsp.Deleted = 0 AND xx.TenXuatXu = ?
                     ORDER BY 
                         ctsp.Updated_at DESC;
             """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, danhMuc);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setSTT(rs.getInt("RowNumber"));
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));
                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));
                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamChiTietViewModels;
    }

    public List<SanPhamChiTietViewModel> giaBan(String sortOrder) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
    SELECT 
        sp.TenSanPham, 
        ctsp.GiaBan, 
        ctsp.SoLuong, 
        dm.TenDanhMuc, 
        xx.TenXuatXu, 
        nsx.TenNSX, 
        ms.TenMauSac, 
        sz.TenSize, 
        th.TenThuongHieu, 
        cl.TenChatLieu, 
        ca.TenCoAo, 
        da.TenDuoiAo, 
        ta.TenTayAo, 
        ctsp.ID,
        dangao.TenDangAo,
        ctsp.MoTa,
        ctsp.TrangThai
    FROM 
        dbo.ChatLieu cl 
    INNER JOIN 
        dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
    INNER JOIN 
        dbo.ChucVu cv ON cl.ID = cv.ID 
    INNER JOIN 
        dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
    INNER JOIN 
        dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
    INNER JOIN 
        dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
    INNER JOIN 
        dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
    INNER JOIN 
        dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
    INNER JOIN 
        dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
    INNER JOIN 
        dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
    INNER JOIN 
        dbo.Size sz ON ctsp.IDSize = sz.ID 
    INNER JOIN 
        dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
    INNER JOIN 
        dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
    INNER JOIN 
        dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
    WHERE 
        ctsp.Deleted = 0 
""";

// Thêm điều kiện sắp xếp
        if (sortOrder.equals("Giá từ thấp đến cao")) {
            sql += "ORDER BY ctsp.GiaBan ASC;";
        } else if (sortOrder.equals("Giá từ cao đến thấp")) {
            sql += "ORDER BY ctsp.GiaBan DESC;";
        } else {
            // Mặc định sắp xếp theo thứ tự giảm dần của ngày cập nhật
            sql += "ORDER BY ctsp.Updated_at DESC;";
        }

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            int rowNumber = 1; // Số thứ tự ban đầu

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setSTT(rowNumber++);
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));
                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));
                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamChiTietViewModels;
    }

    public List<SanPhamChiTietViewModel> Search(String keyword) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
        SELECT 
                ROW_NUMBER() OVER (ORDER BY ctsp.Updated_at DESC) AS RowNumber,
                sp.TenSanPham, 
                ctsp.GiaBan, 
                ctsp.SoLuong, 
                dm.TenDanhMuc, 
                xx.TenXuatXu, 
                nsx.TenNSX, 
                ms.TenMauSac, 
                sz.TenSize, 
                th.TenThuongHieu, 
                cl.TenChatLieu, 
                ca.TenCoAo, 
                da.TenDuoiAo, 
                ta.TenTayAo, 
                ctsp.ID,
                dangao.TenDangAo,
                ctsp.MoTa,
                ctsp.TrangThai
            FROM 
                dbo.ChatLieu cl 
            INNER JOIN 
                dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
            INNER JOIN 
                dbo.ChucVu cv ON cl.ID = cv.ID 
            INNER JOIN 
                dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
            INNER JOIN 
                dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
            INNER JOIN 
                dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
            INNER JOIN 
                dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
            INNER JOIN 
                dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
            INNER JOIN 
                dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
            INNER JOIN 
                dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
            INNER JOIN 
                dbo.Size sz ON ctsp.IDSize = sz.ID 
            INNER JOIN 
                dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
            INNER JOIN 
                dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
            INNER JOIN 
                dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
            WHERE 
                ctsp.Deleted = 0 AND
                (sp.TenSanPham LIKE ? OR
                ctsp.GiaBan LIKE ? OR
                dm.TenDanhMuc LIKE ? OR
                xx.TenXuatXu LIKE ? OR
                nsx.TenNSX LIKE ? OR
                ms.TenMauSac LIKE ? OR
                sz.TenSize LIKE ? OR
                th.TenThuongHieu LIKE ? OR
                cl.TenChatLieu LIKE ? OR
                ca.TenCoAo LIKE ? OR
                da.TenDuoiAo LIKE ? OR
                ta.TenTayAo LIKE ? OR
                dangao.TenDangAo LIKE ?)
            ORDER BY 
                ctsp.Updated_at DESC;
    """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            // Gán giá trị cho các tham số của câu truy vấn
            for (int i = 1; i <= 13; i++) {
                ps.setString(i, "%" + keyword + "%");
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setSTT(rs.getInt("RowNumber"));
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));
                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));
                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ cụ thể ở đây, ví dụ: ghi log hoặc thông báo người dùng
            e.printStackTrace();
        }
        return sanPhamChiTietViewModels;
    }

    public boolean add(ChiTietSanPham chiTietSanPham) {
        int check = 0;
        String sql = """
                 INSERT INTO [dbo].[ChiTietSanPham]
                                             ([IDSanPham]
                                             ,[GiaBan]
                                             ,[SoLuong]
                                             ,[MoTa]
                                             ,[IDDanhMuc]
                                             ,[IDXuatXu]
                                             ,[IDNsx]
                                             ,[IDMauSac]
                                             ,[IDSize]
                                             ,[IDThuongHieu]
                                             ,[IDChatLieu]
                                             ,[IDCoAo]
                                             ,[IDDuoiAo]
                                             ,[IDTayAo]
                                             ,[IDDangAo]
                                             ,[TrangThai])
                                       VALUES
                                             ((select top 1 id from SanPham where TenSanPham = ?),
                 							?,
                 							?,
                 							?,
                 							(select top 1 id from DanhMuc where TenDanhMuc = ?),
                 							(select top 1 id from XuatXu where TenXuatXu = ?),
                 							(select top 1 id from NSX where TenNSX = ?),
                 							(select top 1 id from MauSac where TenMauSac = ?),
                 							(select top 1 id from Size where TenSize = ?),
                 							(select top 1 id from ThuongHieu where TenThuongHieu = ?),
                 							(select top 1 id from ChatLieu where TenChatLieu = ?),
                 							(select top 1 id from CoAo where TenCoAo = ?),
                 							(select top 1 id from DuoiAo where TenDuoiAo = ?),
                 							(select top 1 id from TayAo where TenTayAo = ?),
                 							(select top 1 id from DangAo where TenDangAo = ?),
                 							?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getIdSanPham());
                ps.setObject(2, chiTietSanPham.getGiaBan());
                ps.setObject(3, chiTietSanPham.getSoLuong());
                ps.setObject(4, chiTietSanPham.getMoTa());
                ps.setObject(5, chiTietSanPham.getIdDanhMuc());
                ps.setObject(6, chiTietSanPham.getIdXuatXu());
                ps.setObject(7, chiTietSanPham.getIdNsx());
                ps.setObject(8, chiTietSanPham.getIdMauSac());
                ps.setObject(9, chiTietSanPham.getIdSize());
                ps.setObject(10, chiTietSanPham.getIdThuongHieu());
                ps.setObject(11, chiTietSanPham.getIdChatLieu());
                ps.setObject(12, chiTietSanPham.getIdCoAo());
                ps.setObject(13, chiTietSanPham.getIdDuoiAo());
                ps.setObject(14, chiTietSanPham.getIdTayAo());
                ps.setObject(15, chiTietSanPham.getIdDangAo());
                ps.setObject(16, chiTietSanPham.getTrangThai());
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    public boolean update(ChiTietSanPham chiTietSanPham, String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[ChiTietSanPham]
                 SET [IDSanPham] = (select top 1 id from SanPham where TenSanPham = ?),
                     [GiaBan] = ?,
                     [SoLuong] = ?,
                     [MoTa] = ?,
                     [IDDanhMuc] = (select top 1 id from DanhMuc where TenDanhMuc = ?),
                     [IDXuatXu] = (select top 1 id from XuatXu where TenXuatXu = ?),
                     [IDNsx] = (select top 1 id from NSX where TenNSX = ?),
                     [IDMauSac] = (select top 1 id from MauSac where TenMauSac = ?),
                     [IDSize] = (select top 1 id from Size where TenSize = ?),
                     [IDThuongHieu] = (select top 1 id from ThuongHieu where TenThuongHieu = ?),
                     [IDChatLieu] = (select top 1 id from ChatLieu where TenChatLieu = ?),
                     [IDCoAo] = (select top 1 id from CoAo where TenCoAo = ?),
                     [IDDuoiAo] = (select top 1 id from DuoiAo where TenDuoiAo = ?),
                     [IDTayAo] = (select top 1 id from TayAo where TenTayAo = ?),
                     [IDDangAo] = (select top 1 id from DangAo where TenDangAo = ?),
                     [TrangThai] = ?,
                     [Updated_at] = CURRENT_TIMESTAMP
                 WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getIdSanPham());
                ps.setObject(2, chiTietSanPham.getGiaBan());
                ps.setObject(3, chiTietSanPham.getSoLuong());
                ps.setObject(4, chiTietSanPham.getMoTa());
                ps.setObject(5, chiTietSanPham.getIdDanhMuc());
                ps.setObject(6, chiTietSanPham.getIdXuatXu());
                ps.setObject(7, chiTietSanPham.getIdNsx());
                ps.setObject(8, chiTietSanPham.getIdMauSac());
                ps.setObject(9, chiTietSanPham.getIdSize());
                ps.setObject(10, chiTietSanPham.getIdThuongHieu());
                ps.setObject(11, chiTietSanPham.getIdChatLieu());
                ps.setObject(12, chiTietSanPham.getIdCoAo());
                ps.setObject(13, chiTietSanPham.getIdDuoiAo());
                ps.setObject(14, chiTietSanPham.getIdTayAo());
                ps.setObject(15, chiTietSanPham.getIdDangAo());
                ps.setObject(16, chiTietSanPham.getTrangThai());
                ps.setObject(17, id);
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    public boolean delete(String maSP) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[ChiTietSanPham]
                    SET Deleted = 1
                  WHERE ID = ? 
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    public List<SanPhamChiTietViewModel> getSP(String tenSP) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
        SELECT 
            ROW_NUMBER() OVER (ORDER BY ctsp.Updated_at DESC) AS RowNumber,
            sp.TenSanPham, 
            ctsp.GiaBan, 
            ctsp.SoLuong, 
            dm.TenDanhMuc, 
            xx.TenXuatXu, 
            nsx.TenNSX, 
            ms.TenMauSac, 
            sz.TenSize, 
            th.TenThuongHieu, 
            cl.TenChatLieu, 
            ca.TenCoAo, 
            da.TenDuoiAo, 
            ta.TenTayAo, 
            dangao.TenDangAo,
            ctsp.ID,
            ctsp.MoTa,
            ctsp.TrangThai
        FROM 
            dbo.ChatLieu cl 
        INNER JOIN dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
        INNER JOIN dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
        INNER JOIN dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
        INNER JOIN dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
        INNER JOIN dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
        INNER JOIN dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
        INNER JOIN dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
        INNER JOIN dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
        INNER JOIN dbo.Size sz ON ctsp.IDSize = sz.ID 
        INNER JOIN dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
        INNER JOIN dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
        INNER JOIN dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
        WHERE 
            ctsp.Deleted = 0
            AND sp.TenSanPham = ?
        ORDER BY 
            ctsp.Updated_at DESC;
    """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenSP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setSTT(rs.getInt("RowNumber"));
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));

                // Định dạng giá bán
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));

                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));
                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamChiTietViewModels;
    }

    public List<SanPhamChiTietViewModel> getTop10BestSellingProducts() {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
        SELECT TOP 5
            ROW_NUMBER() OVER (ORDER BY ctsp.SoLuong DESC) AS RowNumber,
            sp.TenSanPham, 
            ctsp.GiaBan, 
            ctsp.SoLuong, 
            dm.TenDanhMuc, 
            xx.TenXuatXu, 
            nsx.TenNSX, 
            ms.TenMauSac, 
            sz.TenSize, 
            th.TenThuongHieu, 
            cl.TenChatLieu, 
            ca.TenCoAo, 
            da.TenDuoiAo, 
            ta.TenTayAo, 
            dangao.TenDangAo,
            ctsp.ID,
            ctsp.MoTa,
            ctsp.TrangThai
        FROM 
            dbo.ChatLieu cl 
        INNER JOIN dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu 
        INNER JOIN dbo.ChucVu cv ON cl.ID = cv.ID 
        INNER JOIN dbo.CoAo ca ON ctsp.IDCoAo = ca.ID 
        INNER JOIN dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID 
        INNER JOIN dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID 
        INNER JOIN dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID 
        INNER JOIN dbo.MauSac ms ON ctsp.IDMauSac = ms.ID 
        INNER JOIN dbo.NSX nsx ON ctsp.IDNsx = nsx.ID 
        INNER JOIN dbo.SanPham sp ON ctsp.IDSanPham = sp.ID 
        INNER JOIN dbo.Size sz ON ctsp.IDSize = sz.ID 
        INNER JOIN dbo.TayAo ta ON ctsp.IDTayAo = ta.ID 
        INNER JOIN dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID 
        INNER JOIN dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID
        WHERE 
            ctsp.Deleted = 0
        ORDER BY 
            ctsp.SoLuong DESC;
    """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setSTT(rs.getInt("RowNumber"));
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));

                // Định dạng giá bán
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));

                sanPhamChiTietViewModel.setSoLuong(rs.getInt("SoLuong"));
                sanPhamChiTietViewModel.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sanPhamChiTietViewModel.setTenXuatXu(rs.getString("TenXuatXu"));
                sanPhamChiTietViewModel.setTenNSX(rs.getString("TenNSX"));
                sanPhamChiTietViewModel.setTenMauSac(rs.getString("TenMauSac"));
                sanPhamChiTietViewModel.setTenSize(rs.getString("TenSize"));
                sanPhamChiTietViewModel.setTenThuongHieu(rs.getString("TenThuongHieu"));
                sanPhamChiTietViewModel.setTenChatLieu(rs.getString("TenChatLieu"));
                sanPhamChiTietViewModel.setTenCoAo(rs.getString("TenCoAo"));
                sanPhamChiTietViewModel.setMaDuoiAo(rs.getString("TenDuoiAo"));
                sanPhamChiTietViewModel.setMaTayAo(rs.getString("TenTayAo"));
                sanPhamChiTietViewModel.setTenDangAo(rs.getString("TenDangAo"));
                sanPhamChiTietViewModel.setId(rs.getString("ID"));
                sanPhamChiTietViewModel.setMota(rs.getString("MoTa"));
                sanPhamChiTietViewModel.setTrangThai(rs.getString("TrangThai"));
                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamChiTietViewModels;
    }
}
