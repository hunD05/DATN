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
        ROW_NUMBER() OVER (ORDER BY ctsp.Created_at DESC) AS RowNumber,
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
            sql += " ORDER BY ctsp.Created_at DESC";
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
                    ROW_NUMBER() OVER (ORDER BY ctsp.Created_at DESC) AS RowNumber,
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
                        ctsp.Created_at DESC;
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

    public List<SanPhamChiTietViewModel> filterData(String danhMuc, String xuatXu, String nsx, String giaBanSortOrder) {
        List<SanPhamChiTietViewModel> data = new ArrayList<>();
        String sql = """
            SELECT 
                ROW_NUMBER() OVER (ORDER BY ctsp.Created_at DESC) AS RowNumber,
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
                dbo.ChiTietSanPham ctsp
            INNER JOIN 
                dbo.ChatLieu cl ON cl.ID = ctsp.IDChatLieu 
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

        // Thêm các điều kiện vào câu truy vấn sử dụng PreparedStatement
        if (danhMuc != null && !danhMuc.isEmpty()) {
            sql += " AND dm.TenDanhMuc = ?";
        }
        if (xuatXu != null && !xuatXu.isEmpty()) {
            sql += " AND xx.TenXuatXu = ?";
        }
        if (nsx != null && !nsx.isEmpty()) {
            sql += " AND nsx.TenNSX = ?";
        }

        // Thêm điều kiện sắp xếp
        if ("Giá từ thấp đến cao".equals(giaBanSortOrder)) {
            sql += " ORDER BY ctsp.GiaBan ASC";
        } else if ("Giá từ cao đến thấp".equals(giaBanSortOrder)) {
            sql += " ORDER BY ctsp.GiaBan DESC";
        } else {
            sql += " ORDER BY ctsp.Created_at DESC";
        }

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            // Đặt giá trị cho các tham số của PreparedStatement
            int parameterIndex = 1;
            if (danhMuc != null && !danhMuc.isEmpty()) {
                ps.setString(parameterIndex++, danhMuc);
            }
            if (xuatXu != null && !xuatXu.isEmpty()) {
                ps.setString(parameterIndex++, xuatXu);
            }
            if (nsx != null && !nsx.isEmpty()) {
                ps.setString(parameterIndex++, nsx);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                // Thêm các thông tin từ ResultSet vào đối tượng SanPhamChiTietViewModel
                data.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<SanPhamChiTietViewModel> SearchCbb(String danhmuc, String xuatxu, String nsx, boolean sapXepGiaTangDan) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();

        // Câu truy vấn SQL không thay đổi
//        String sql = "SELECT "
//                + "sp.TenSanPham, "
//                + "ctsp.GiaBan, "
//                + "ctsp.SoLuong, "
//                + "dm.TenDanhMuc, "
//                + "xx.TenXuatXu, "
//                + "nsx.TenNSX, "
//                + "ms.TenMauSac, "
//                + "sz.TenSize, "
//                + "th.TenThuongHieu, "
//                + "cl.TenChatLieu, "
//                + "ca.TenCoAo, "
//                + "da.TenDuoiAo, "
//                + "ta.TenTayAo, "
//                + "dangao.TenDangAo, "
//                + "ctsp.ID, "
//                + "ctsp.MoTa, "
//                + "ctsp.TrangThai"
//                + "FROM "
//                + "dbo.ChatLieu cl"
//                + "INNER JOIN dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu "
//                + "INNER JOIN dbo.ChucVu cv ON cl.ID = cv.ID INNER JOIN dbo.CoAo ca ON ctsp.IDCoAo = ca.ID "
//                + "        INNER JOIN dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID "
//                + "        INNER JOIN dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID "
//                + "        INNER JOIN dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID "
//                + "        INNER JOIN dbo.MauSac ms ON ctsp.IDMauSac = ms.ID "
//                + "        INNER JOIN dbo.NSX nsx ON ctsp.IDNsx = nsx.ID "
//                + "        INNER JOIN dbo.SanPham sp ON ctsp.IDSanPham = sp.ID "
//                + "        INNER JOIN dbo.Size sz ON ctsp.IDSize = sz.ID "
//                + "        INNER JOIN dbo.TayAo ta ON ctsp.IDTayAo = ta.ID "
//                + "        INNER JOIN dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID "
//                + "        INNER JOIN dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID"
//                + "WHERE "
//                + "ctsp.Deleted = 0"
//                + "AND ( "
//                + "dm.TenDanhMuc LIKE ? OR "
//                + "xx.TenXuatXu LIKE ? OR "
//                + "nsx.TenNSX ? "
//                + ") "
//                + "GROUP BY "
//                + "sp.TenSanPham, "
//                + "ctsp.GiaBan, "
//                + "ctsp.SoLuong, "
//                + "dm.TenDanhMuc, "
//                + "xx.TenXuatXu, "
//                + "nsx.TenNSX, "
//                + "ms.TenMauSac, "
//                + "sz.TenSize, "
//                + "th.TenThuongHieu, "
//                + "cl.TenChatLieu, "
//                + "ca.TenCoAo, "
//                + "da.TenDuoiAo, "
//                + "ta.TenTayAo, "
//                + "dangao.TenDangAo, "
//                + "ctsp.ID, "
//                + "ctsp.MoTa, "
//                + "ctsp.TrangThai"
//                + "FROM "
//                + "dbo.ChatLieu cl"
//                + "ORDER BY "
//                + "ctsp.GiaBan " + (sapXepGiaTangDan ? "ASC" : "DESC");
        String sql = "SELECT "
                + "sp.TenSanPham, "
                + "ctsp.GiaBan, "
                + "ctsp.SoLuong, "
                + "dm.TenDanhMuc, "
                + "xx.TenXuatXu, "
                + "nsx.TenNSX, "
                + "ms.TenMauSac, "
                + "sz.TenSize, "
                + "th.TenThuongHieu, "
                + "cl.TenChatLieu, "
                + "ca.TenCoAo, "
                + "da.TenDuoiAo, "
                + "ta.TenTayAo, "
                + "dangao.TenDangAo, "
                + "ctsp.ID, "
                + "ctsp.MoTa, "
                + "ctsp.TrangThai "
                + "FROM "
                + "dbo.ChatLieu cl "
                + "INNER JOIN dbo.ChiTietSanPham ctsp ON cl.ID = ctsp.IDChatLieu "
                + "INNER JOIN dbo.ChucVu cv ON cl.ID = cv.ID INNER JOIN dbo.CoAo ca ON ctsp.IDCoAo = ca.ID "
                + "INNER JOIN dbo.DangAo dangao ON ctsp.IDDangAo = dangao.ID "
                + "INNER JOIN dbo.DanhMuc dm ON ctsp.IDDanhMuc = dm.ID "
                + "INNER JOIN dbo.DuoiAo da ON ctsp.IDDuoiAo = da.ID "
                + "INNER JOIN dbo.MauSac ms ON ctsp.IDMauSac = ms.ID "
                + "INNER JOIN dbo.NSX nsx ON ctsp.IDNsx = nsx.ID "
                + "INNER JOIN dbo.SanPham sp ON ctsp.IDSanPham = sp.ID "
                + "INNER JOIN dbo.Size sz ON ctsp.IDSize = sz.ID "
                + "INNER JOIN dbo.TayAo ta ON ctsp.IDTayAo = ta.ID "
                + "INNER JOIN dbo.ThuongHieu th ON ctsp.IDThuongHieu = th.ID "
                + "INNER JOIN dbo.XuatXu xx ON ctsp.IDXuatXu = xx.ID "
                + "WHERE "
                + "ctsp.Deleted = 0 "
                + "AND ( "
                + "dm.TenDanhMuc LIKE ? OR "
                + "xx.TenXuatXu LIKE ? OR "
                + "nsx.TenNSX LIKE ? "
                + ") "
                + "ORDER BY "
                + "ctsp.GiaBan " + (sapXepGiaTangDan ? "ASC" : "DESC");

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            // Gán giá trị cho các tham số của câu truy vấn
            ps.setObject(1, danhmuc);
            ps.setObject(2, xuatxu);
            ps.setObject(3, nsx);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTietViewModel sanPhamChiTietViewModel = new SanPhamChiTietViewModel();
                sanPhamChiTietViewModel.setTenSanPham(rs.getString("TenSanPham"));
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
                sanPhamChiTietViewModel.setGiaBan(rs.getBigDecimal("GiaBan"));
                sanPhamChiTietViewModels.add(sanPhamChiTietViewModel);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ cụ thể ở đây, ví dụ: ghi log hoặc thông báo người dùng
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
                     ROW_NUMBER() OVER (ORDER BY ctsp.Created_at DESC) AS RowNumber,
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
                     
                     dangao.TenDangAo, ctsp.ID, ctsp.MoTa,
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
                     ctsp.Created_at DESC;
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
                     ROW_NUMBER() OVER (ORDER BY ctsp.Created_at DESC) AS RowNumber,
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
                     ta.TenTayAo, ctsp.ID,
                     dangao.TenDangAo,ctsp.MoTa,
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
                     ctsp.Created_at DESC;
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
                     ROW_NUMBER() OVER (ORDER BY ctsp.Created_at DESC) AS RowNumber,
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
                     ta.TenTayAo, ctsp.ID,
                     dangao.TenDangAo,ctsp.MoTa,
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
                     ctsp.Created_at DESC;
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
            // Mặc định sắp xếp theo thứ tự giảm dần của ngày tạo
            sql += "ORDER BY ctsp.Created_at DESC;";
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

    public List<SanPhamChiTietViewModel> searchKey(String keyword) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
        SELECT 
            ROW_NUMBER() OVER (ORDER BY ctsp.Created_at DESC) AS RowNumber,
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
            ta.TenTayAo, ctsp.ID,
            dangao.TenDangAo,ctsp.MoTa,
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
            ctsp.Created_at DESC;
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

    public List<ChiTietSanPham> ConHang() {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
                     SELECT 
                                                                   ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                                                               	ID,
                                                                   [GiaBan],
                                                                   [SoLuong],
                                                                   [MoTa],
                                                                   [TrangThai],
                                                                   [Updated_at]
                                                               FROM 
                                                                   [dbo].[ChiTietSanPham]
                                          						 where TrangThai = 'Còn hàng' and deleted = 0
                                                               ORDER BY 
                                                                   [Updated_at] DESC;
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getString(1));
                chiTietSanPham.setId(rs.getString(2));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(3));
                chiTietSanPham.setSoLuong(rs.getString(4));
                chiTietSanPham.setMoTa(rs.getString(5));
                chiTietSanPham.setTrangThai(rs.getString(6));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(7));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }

    public List<ChiTietSanPham> HetHang() {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
SELECT 
                         ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                         ID,
                         [GiaBan],
                         [SoLuong],
                         [MoTa],
                         [TrangThai],
                         [Updated_at]
                     FROM 
                         [dbo].[ChiTietSanPham]
                     WHERE 
                         TrangThai = 'Het hang' and deleted = 0
                     ORDER BY 
                         [Updated_at] DESC;
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getString(1));
                chiTietSanPham.setId(rs.getString(2));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(3));
                chiTietSanPham.setSoLuong(rs.getString(4));
                chiTietSanPham.setMoTa(rs.getString(5));
                chiTietSanPham.setTrangThai(rs.getString(6));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(7));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chiTietSanPhams;
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
                               SET [IDSanPham] = (select top 1 id from SanPham where TenSanPham = ?)
                                  ,[GiaBan] = ?
                                  ,[SoLuong] = ?
                                  ,[MoTa] = ?
                                  ,[IDDanhMuc] = (select top 1 id from DanhMuc where TenDanhMuc = ?)
                                  ,[IDXuatXu] = (select top 1 id from XuatXu where TenXuatXu = ?)
                                  ,[IDNsx] = (select top 1 id from NSX where TenNSX = ?)
                                  ,[IDMauSac] = (select top 1 id from MauSac where TenMauSac = ?)
                                  ,[IDSize] = (select top 1 id from Size where TenSize = ?)
                                  ,[IDThuongHieu] = (select top 1 id from ThuongHieu where TenThuongHieu = ?)
                                  ,[IDChatLieu] = (select top 1 id from ChatLieu where TenChatLieu = ?)
                                  ,[IDCoAo] = (select top 1 id from CoAo where TenCoAo = ?)
                                  ,[IDDuoiAo] = (select top 1 id from DuoiAo where TenDuoiAo = ?)
                                  ,[IDTayAo] = (select top 1 id from TayAo where TenTayAo = ?)
                                  ,[IDDangAo] = (select top 1 id from DangAo where TenDangAo = ?)
                                  ,[TrangThai] = ?
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

//    public List<SanPhamChiTietViewModel> getAllz(Long masp) {
//        List<SanPhamChiTietViewModel> chiTietSanPhams = new ArrayList<>();
//        String sql = """
//                 SELECT 
//                     ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
//                     ID,
//                     [IDSanPham],
//                     [GiaBan],
//                     [SoLuong],
//                     [MoTa],
//                     [IDDanhMuc],
//                     [IDXuatXu],
//                     [IDNsx],
//                     [IDMauSac],
//                     [IDSize],
//                     [IDThuongHieu],
//                     [IDChatLieu],
//                     [IDCoAo],
//                     [IDDuoiAo],
//                     [IDTayAo],
//                     [IDDangAo],
//                     [TrangThai],
//                     [Deleted],
//                     [Updated_at]
//                 FROM 
//                     [dbo].[ChiTietSanPham]
//                     where Deleted = 0 and ID = ?
//                 ORDER BY 
//                     [Updated_at] DESC;
//                 """;
//        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
//            ps.setObject(1, masp);
//            while (rs.next()) {
//                SanPhamChiTietViewModel chiTietSanPham = new SanPhamChiTietViewModel();
//                chiTietSanPham.setStt(rs.getLong(1));
//                chiTietSanPham.setId(rs.getLong(2));
//                chiTietSanPham.setIdSanPham(rs.getLong(3));
//                chiTietSanPham.setGiaBan(rs.getBigDecimal(4));
//                chiTietSanPham.setSoLuong(rs.getLong(5));
//                chiTietSanPham.setMoTa(rs.getString(6));
//                chiTietSanPham.setIdDanhMuc(rs.getLong(7));
//                chiTietSanPham.setIdXuatXu(rs.getLong(8));
//                chiTietSanPham.setIdNsx(rs.getLong(9));
//                chiTietSanPham.setIdMauSac(rs.getLong(10));
//                chiTietSanPham.setIdSize(rs.getLong(11));
//                chiTietSanPham.setIdThuongHieu(rs.getLong(12));
//                chiTietSanPham.setIdChatLieu(rs.getLong(13));
//                chiTietSanPham.setIdCoAo(rs.getLong(14));
//                chiTietSanPham.setIdDuoiAo(rs.getLong(15));
//                chiTietSanPham.setIdTayAo(rs.getLong(16));
//                chiTietSanPham.setIdDangAo(rs.getLong(17));
//                chiTietSanPham.setTrangThai(rs.getString(18));
//                chiTietSanPham.setDeleted(rs.getBoolean(19));
//                chiTietSanPham.setUpdatedAt(rs.getTimestamp(20));
//                chiTietSanPhams.add(chiTietSanPham);
//            }
//        } catch (Exception e) {
//            // Xử lý ngoại lệ cụ thể ở đây, ví dụ: ghi log hoặc thông báo người dùng
//            e.printStackTrace();
//        }
//        return chiTietSanPhams;
//    }
    public List<SanPhamChiTietViewModel> getSP(String tenSP) {
        List<SanPhamChiTietViewModel> sanPhamChiTietViewModels = new ArrayList<>();
        String sql = """
        SELECT 
            ROW_NUMBER() OVER (ORDER BY ctsp.Created_at DESC) AS RowNumber,
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
            ctsp.Created_at DESC;
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
