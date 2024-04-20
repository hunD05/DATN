/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.viewmodel.BHHDViewModel;
import backend.viewmodel.BHSPViewModel;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author VHC
 */
public class BanHangRepo {

    public List<BHHDViewModel> getHD() {
        List<BHHDViewModel> listBH = new ArrayList<>();
        String sql = """
                SELECT dbo.HoaDon.MaHoaDon,
                       dbo.HoaDon.NgayTao,
                       dbo.NhanVien.MaNhanVien,
                       ISNULL(SUM(dbo.HoaDonChiTiet.SoLuong), 0) AS TongSP,
                       dbo.HoaDon.TrangThai,
                       dbo.HoaDon.ID,
                       ISNULL(SUM(dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.GiaBan),0) AS TongThanhTien,
                       dbo.KhachHang.SoDienThoai,
                       dbo.KhachHang.TenKhachHang,
                       dbo.PhuongThucThanhToan.TenKieuThanhToan,
                       dbo.KhachHang.DiaChi
                FROM dbo.HoaDon
                INNER JOIN dbo.NhanVien ON dbo.HoaDon.IDNhanVien = dbo.NhanVien.ID
                LEFT JOIN dbo.KhachHang ON dbo.HoaDon.IDKhachHang = dbo.KhachHang.ID
                LEFT JOIN dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
                LEFT JOIN dbo.HinhThucThanhToan ON dbo.HoaDon.ID = dbo.HinhThucThanhToan.IDHoaDon
                LEFT JOIN dbo.PhuongThucThanhToan ON dbo.HinhThucThanhToan.IDPhuongThucThanhToan = dbo.PhuongThucThanhToan.ID
                WHERE (dbo.HoaDon.TrangThai LIKE N'Chưa Thanh Toán' OR dbo.HoaDon.TrangThai LIKE N'Chờ giao%') AND dbo.HoaDon.Deleted = 0
                GROUP BY dbo.HoaDon.MaHoaDon, dbo.HoaDon.NgayTao, dbo.NhanVien.MaNhanVien, dbo.HoaDon.TrangThai, dbo.HoaDon.ID, dbo.KhachHang.SoDienThoai, dbo.KhachHang.TenKhachHang, dbo.PhuongThucThanhToan.TenKieuThanhToan, dbo.KhachHang.DiaChi
                ORDER BY dbo.HoaDon.NgayTao DESC;
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BHHDViewModel bh = new BHHDViewModel();
                bh.setMaHD(rs.getString(1));
                bh.setNgayTao(rs.getTimestamp(2).toLocalDateTime());
                bh.setMaNV(rs.getString(3));
                bh.setTongSP(rs.getInt(4)); // Lấy tổng số lượng sản phẩm từ kết quả truy vấn SQL
                bh.setTrangThai(rs.getString(5));
                bh.setId(rs.getInt(6));
                bh.setTongTien(rs.getDouble(7));
                bh.setSoDT(rs.getString(8));
                bh.setTenKH(rs.getString(9));
                bh.setPhuongThucThanhToan(rs.getString(10));
                bh.setDiaChi(rs.getString(11));
                listBH.add(bh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBH;
    }

    public List<BHSPViewModel> getSP() {
        List<BHSPViewModel> listSP = new ArrayList<>();
        String sql = """
                     SELECT    dbo.ChiTietSanPham.ID, dbo.SanPham.TenSanPham, dbo.DanhMuc.TenDanhMuc, dbo.XuatXu.TenXuatXu, dbo.NSX.TenNSX, dbo.Size.TenSize, dbo.ChiTietSanPham.SoLuong, dbo.ChiTietSanPham.GiaBan
                     FROM         dbo.ChiTietSanPham INNER JOIN
                                           dbo.DanhMuc ON dbo.ChiTietSanPham.IDDanhMuc = dbo.DanhMuc.ID INNER JOIN
                                           dbo.NSX ON dbo.ChiTietSanPham.IDNsx = dbo.NSX.ID INNER JOIN
                                           dbo.SanPham ON dbo.ChiTietSanPham.IDSanPham = dbo.SanPham.ID INNER JOIN
                                           dbo.Size ON dbo.ChiTietSanPham.IDSize = dbo.Size.ID INNER JOIN
                                           dbo.XuatXu ON dbo.ChiTietSanPham.IDXuatXu = dbo.XuatXu.ID              
                     WHERE dbo.SanPham.Deleted = 0
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BHSPViewModel bh = new BHSPViewModel();
                bh.setMaSPCT(rs.getInt(1));
                bh.setTenSP(rs.getString(2));
                bh.setDanhMuc(rs.getString(3));
                bh.setXuatXu(rs.getString(4));
                bh.setNsx(rs.getString(5));
                bh.setSize(rs.getString(6));
                bh.setGiaBan(rs.getDouble(8));
                bh.setSoLuong(rs.getInt(7));
                listSP.add(bh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public boolean updateSP(int idSPCT, int soLuongConLai) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[ChiTietSanPham]
                        SET [SoLuong] = ?,
                            [Updated_at] = CURRENT_TIMESTAMP
                        WHERE ID = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, soLuongConLai);
            ps.setObject(2, idSPCT);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean increaseSoLuong(int soLuongMoi, int idCTSP) {
        int check = 0;
        String sql = """
                    UPDATE [dbo].[ChiTietSanPham]
                                             SET [SoLuong] = [SoLuong] + ?,
                                                 [Updated_at] = CURRENT_TIMESTAMP
                                             WHERE ID = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, soLuongMoi);
            ps.setObject(2, idCTSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean decreaseSoLuong(int soLuongMoi, int idCTSP) {
        int check = 0;
        String sql = """
                    UPDATE [dbo].[ChiTietSanPham]
                                             SET [SoLuong] = [SoLuong] - ?,
                                                 [Updated_at] = CURRENT_TIMESTAMP
                                             WHERE ID = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, soLuongMoi);
            ps.setObject(2, idCTSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<BHSPViewModel> searchSP(String tuKhoa) {
        List<BHSPViewModel> listSP = new ArrayList<>();
        String sql = """
                     SELECT    dbo.ChiTietSanPham.ID, dbo.SanPham.TenSanPham, dbo.DanhMuc.TenDanhMuc, dbo.XuatXu.TenXuatXu, dbo.NSX.TenNSX, dbo.Size.TenSize, dbo.ChiTietSanPham.SoLuong, dbo.ChiTietSanPham.GiaBan
                     FROM         dbo.ChiTietSanPham INNER JOIN
                                           dbo.DanhMuc ON dbo.ChiTietSanPham.IDDanhMuc = dbo.DanhMuc.ID INNER JOIN
                                           dbo.NSX ON dbo.ChiTietSanPham.IDNsx = dbo.NSX.ID INNER JOIN
                                           dbo.SanPham ON dbo.ChiTietSanPham.IDSanPham = dbo.SanPham.ID INNER JOIN
                                           dbo.Size ON dbo.ChiTietSanPham.IDSize = dbo.Size.ID INNER JOIN
                                           dbo.XuatXu ON dbo.ChiTietSanPham.IDXuatXu = dbo.XuatXu.ID
                     WHERE dbo.SanPham.TenSanPham LIKE ? OR dbo.DanhMuc.TenDanhMuc LIKE ? OR dbo.XuatXu.TenXuatXu LIKE ? OR dbo.NSX.TenNSX LIKE ? OR dbo.Size.TenSize LIKE ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, '%' + tuKhoa + '%');
            ps.setObject(2, '%' + tuKhoa + '%');
            ps.setObject(3, '%' + tuKhoa + '%');
            ps.setObject(4, '%' + tuKhoa + '%');
            ps.setObject(5, '%' + tuKhoa + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BHSPViewModel bh = new BHSPViewModel();
                bh.setMaSPCT(rs.getInt(1));
                bh.setTenSP(rs.getString(2));
                bh.setDanhMuc(rs.getString(3));
                bh.setXuatXu(rs.getString(4));
                bh.setNsx(rs.getString(5));
                bh.setSize(rs.getString(6));
                bh.setGiaBan(rs.getDouble(8));
                bh.setSoLuong(rs.getInt(7));
                listSP.add(bh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<BHHDViewModel> searchHD(String tuKhoa) {
        List<BHHDViewModel> listBH = new ArrayList<>();
        String sql = """
                     SELECT dbo.HoaDon.MaHoaDon,
                                            dbo.HoaDon.NgayTao,
                                            dbo.NhanVien.MaNhanVien,
                                            ISNULL(SUM(dbo.HoaDonChiTiet.SoLuong), 0) AS TongSP,
                                            dbo.HoaDon.TrangThai,
                                            dbo.HoaDon.ID,
                                            ISNULL(SUM(dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.GiaBan),0) AS TongThanhTien,
                                            dbo.KhachHang.SoDienThoai,
                                            dbo.KhachHang.TenKhachHang,
                                            dbo.PhuongThucThanhToan.TenKieuThanhToan,
                                            dbo.KhachHang.DiaChi
                                     FROM dbo.HoaDon
                                     INNER JOIN dbo.NhanVien ON dbo.HoaDon.IDNhanVien = dbo.NhanVien.ID
                                     LEFT JOIN dbo.KhachHang ON dbo.HoaDon.IDKhachHang = dbo.KhachHang.ID
                                     LEFT JOIN dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
                                     LEFT JOIN dbo.HinhThucThanhToan ON dbo.HoaDon.ID = dbo.HinhThucThanhToan.IDHoaDon
                                     LEFT JOIN dbo.PhuongThucThanhToan ON dbo.HinhThucThanhToan.IDPhuongThucThanhToan = dbo.PhuongThucThanhToan.ID
                                     WHERE dbo.HoaDon.TrangThai LIKE N'Chưa Thanh Toán' AND dbo.HoaDon.Deleted = 0 AND (dbo.HoaDon.MaHoaDon LIKE ? OR dbo.NhanVien.MaNhanVien LIKE ?)
                                     GROUP BY dbo.HoaDon.MaHoaDon, dbo.HoaDon.NgayTao, dbo.NhanVien.MaNhanVien, dbo.HoaDon.TrangThai, dbo.HoaDon.ID, dbo.KhachHang.SoDienThoai, dbo.KhachHang.TenKhachHang, dbo.PhuongThucThanhToan.TenKieuThanhToan, dbo.KhachHang.DiaChi
                                     ORDER BY dbo.HoaDon.NgayTao DESC;
                 """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, '%' + tuKhoa + '%');
            ps.setObject(2, '%' + tuKhoa + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BHHDViewModel bh = new BHHDViewModel();
                bh.setMaHD(rs.getString(1));
                bh.setNgayTao(rs.getTimestamp(2).toLocalDateTime());
                bh.setMaNV(rs.getString(3));
                bh.setTongSP(rs.getInt(4)); // Lấy tổng số lượng sản phẩm từ kết quả truy vấn SQL
                bh.setTrangThai(rs.getString(5));
                bh.setId(rs.getInt(6));
                bh.setTongTien(rs.getDouble(7));
                bh.setSoDT(rs.getString(8));
                bh.setTenKH(rs.getString(9));
                bh.setPhuongThucThanhToan(rs.getString(10));
                bh.setDiaChi(rs.getString(11));
                listBH.add(bh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBH;
    }

    public List<BHSPViewModel> searchCBBSP(String danhMuc, String xuatXu, String nsx, String gia) {
        List<BHSPViewModel> listSP = new ArrayList<>();
        String sql = """
                 SELECT dbo.ChiTietSanPham.ID, dbo.SanPham.TenSanPham, dbo.DanhMuc.TenDanhMuc, dbo.XuatXu.TenXuatXu, dbo.NSX.TenNSX, dbo.Size.TenSize, dbo.ChiTietSanPham.SoLuong, dbo.ChiTietSanPham.GiaBan
                     FROM dbo.ChiTietSanPham INNER JOIN 
                     dbo.DanhMuc ON dbo.ChiTietSanPham.IDDanhMuc = dbo.DanhMuc.ID INNER JOIN 
                     dbo.NSX ON dbo.ChiTietSanPham.IDNsx = dbo.NSX.ID INNER JOIN 
                     dbo.SanPham ON dbo.ChiTietSanPham.IDSanPham = dbo.SanPham.ID INNER JOIN 
                     dbo.Size ON dbo.ChiTietSanPham.IDSize = dbo.Size.ID INNER JOIN 
                     dbo.XuatXu ON dbo.ChiTietSanPham.IDXuatXu = dbo.XuatXu.ID 
                     WHERE dbo.SanPham.Deleted = 0 
                 """;
        if (danhMuc != null && !danhMuc.isEmpty()) {
            sql += " AND dbo.DanhMuc.TenDanhMuc = N'" + danhMuc + "'";
        }
        if (xuatXu != null && !xuatXu.isEmpty()) {
            sql += " AND dbo.XuatXu.TenXuatXu = N'" + xuatXu + "'";
        }
        if (nsx != null && !nsx.isEmpty()) {
            sql += " AND dbo.NSX.TenNSX = N'" + nsx + "'";
        }
        if ("GiaBan ASC".equals(gia)) {
            sql += " ORDER BY dbo.ChiTietSanPham.GiaBan ASC";
        } else if ("GiaBan DESC".equals(gia)) {
            sql += " ORDER BY dbo.ChiTietSanPham.GiaBan DESC";
        }
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BHSPViewModel bh = new BHSPViewModel();
                bh.setMaSPCT(rs.getInt(1));
                bh.setTenSP(rs.getString(2));
                bh.setDanhMuc(rs.getString(3));
                bh.setXuatXu(rs.getString(4));
                bh.setNsx(rs.getString(5));
                bh.setSize(rs.getString(6));
                bh.setGiaBan(rs.getDouble(8));
                bh.setSoLuong(rs.getInt(7));
                listSP.add(bh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<BHSPViewModel> getOneSP(int idSPCT) {
        List<BHSPViewModel> listSP = new ArrayList<>();
        String sql = """
                     SELECT    dbo.ChiTietSanPham.ID, dbo.SanPham.TenSanPham, dbo.DanhMuc.TenDanhMuc, dbo.XuatXu.TenXuatXu, dbo.NSX.TenNSX, dbo.Size.TenSize, dbo.ChiTietSanPham.SoLuong, dbo.ChiTietSanPham.GiaBan
                     FROM         dbo.ChiTietSanPham INNER JOIN
                                           dbo.DanhMuc ON dbo.ChiTietSanPham.IDDanhMuc = dbo.DanhMuc.ID INNER JOIN
                                           dbo.NSX ON dbo.ChiTietSanPham.IDNsx = dbo.NSX.ID INNER JOIN
                                           dbo.SanPham ON dbo.ChiTietSanPham.IDSanPham = dbo.SanPham.ID INNER JOIN
                                           dbo.Size ON dbo.ChiTietSanPham.IDSize = dbo.Size.ID INNER JOIN
                                           dbo.XuatXu ON dbo.ChiTietSanPham.IDXuatXu = dbo.XuatXu.ID              
                     WHERE dbo.SanPham.Deleted = 0 AND dbo.ChiTietSanPham.ID = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idSPCT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BHSPViewModel bh = new BHSPViewModel();
                bh.setMaSPCT(rs.getInt(1));
                bh.setTenSP(rs.getString(2));
                bh.setDanhMuc(rs.getString(3));
                bh.setXuatXu(rs.getString(4));
                bh.setNsx(rs.getString(5));
                bh.setSize(rs.getString(6));
                bh.setGiaBan(rs.getDouble(8));
                bh.setSoLuong(rs.getInt(7));
                listSP.add(bh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

}
