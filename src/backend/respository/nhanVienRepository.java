/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import backend.entity.nhanVien;
import backend.viewmodel.nhanVienViewModel;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author Huu Hai
 */
public class nhanVienRepository {

    public List<nhanVienViewModel> getAll() {
        List<nhanVienViewModel> lists = new ArrayList<>();
        String sql = """
                    SELECT dbo.NhanVien.ID, dbo.NhanVien.MaNhanVien, dbo.NhanVien.TenNhanVien, dbo.NhanVien.GioiTinh, dbo.NhanVien.SoDienThoai, dbo.NhanVien.CCCD, dbo.ChucVu.TenChucVu, dbo.NhanVien.Email, dbo.NhanVien.NgaySinh, 
                                      dbo.NhanVien.TrangThai
                    FROM     dbo.NhanVien INNER JOIN
                                      dbo.ChucVu ON dbo.NhanVien.IDChucVu = dbo.ChucVu.ID
                    				  where dbo.NhanVien.Deleted = 0 order by dbo.NhanVien.Created_at DESC
                     """;
        try (Connection ct = DBConnect.getConnection(); PreparedStatement ps = ct.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nhanVienViewModel NV = new nhanVienViewModel();

                NV.setId(rs.getString(1));
                NV.setMaNV(rs.getString(2));
                NV.setTenNV(rs.getString(3));
                NV.setGioiTinh(rs.getBoolean(4));
                NV.setSDT(rs.getString(5));
                NV.setCCCD(rs.getString(6));
                NV.setTenChucVu(rs.getString(7));
                NV.setEmail(rs.getString(8));
                NV.setNgaySinh(rs.getDate(9));
                NV.setTrangThai(rs.getString(10));

                lists.add(NV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

    public List<nhanVienViewModel> search(String ten) {
        List<nhanVienViewModel> lists = new ArrayList<>();
        String sql = """
                    SELECT dbo.NhanVien.ID, dbo.NhanVien.MaNhanVien, dbo.NhanVien.TenNhanVien, dbo.NhanVien.GioiTinh, dbo.NhanVien.SoDienThoai, dbo.NhanVien.CCCD, dbo.ChucVu.TenChucVu, dbo.NhanVien.Email, dbo.NhanVien.NgaySinh, 
                                                                                                 dbo.NhanVien.TrangThai
                                                                               FROM     dbo.NhanVien INNER JOIN
                                                                                                 dbo.ChucVu ON dbo.NhanVien.IDChucVu = dbo.ChucVu.ID
                                        where dbo.NhanVien.TenNhanVien like ? OR dbo.NhanVien.MaNhanVien like ? OR dbo.NhanVien.Email like ?
                     """;
        try (Connection ct = DBConnect.getConnection(); PreparedStatement ps = ct.prepareStatement(sql)) {
            ps.setObject(1, '%' + ten + '%');
            ps.setObject(2, '%' + ten + '%');
            ps.setObject(3, '%' + ten + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nhanVienViewModel NV = new nhanVienViewModel();
                NV.setId(rs.getString(1));
                NV.setMaNV(rs.getString(2));
                NV.setTenNV(rs.getString(3));
                NV.setGioiTinh(rs.getBoolean(4));
                NV.setSDT(rs.getString(5));
                NV.setCCCD(rs.getString(6));
                NV.setTenChucVu(rs.getString(7));
                NV.setEmail(rs.getString(8));
                NV.setNgaySinh(rs.getDate(9));
                NV.setTrangThai(rs.getString(10));
                lists.add(NV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

    public boolean add(nhanVien NV, String tenChucVu) {
        int check = 0;
        String sql = """
                    declare @idcv bigint
                    SET @idcv = (SELECT TOP (1) id FROM ChucVu Where TenChucVu = ?)
                    INSERT INTO [dbo].[NhanVien]
                               ([TenNhanVien]
                               ,[GioiTinh]
                               ,[SoDienThoai]
                               ,[CCCD]
                               ,[IDChucVu]
                               ,[Email]
                               ,[NgaySinh]
                               ,[TrangThai])
                         VALUES
                               (?,?,?,?,@idcv,?,?,?)
                     """;
        try (Connection ct = DBConnect.getConnection(); PreparedStatement ps = ct.prepareStatement(sql)) {
            ps.setObject(1, tenChucVu);
            ps.setObject(2, NV.getTenNV());
            ps.setObject(3, NV.isGioiTinh());
            ps.setObject(4, NV.getSDT());
            ps.setObject(5, NV.getCCCD());
            ps.setObject(6, NV.getEmail());
            ps.setObject(7, NV.getNgaySinh());
            ps.setObject(8, NV.getTrangThai());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean Update(nhanVien NV, String ma, String tenChucVu) {
        int check = 0;
        String sql = """
                     declare @idcv bigint
                     SET @idcv = (SELECT TOP (1) id FROM ChucVu Where TenChucVu = ?)
                    UPDATE [dbo].[NhanVien]
                       SET [TenNhanVien] = ?
                          ,[GioiTinh] = ?
                          ,[SoDienThoai] = ?
                          ,[CCCD] = ?
                          ,[IDChucVu] = @idcv
                          ,[Email] = ?
                          ,[NgaySinh] = ?
                          ,[TrangThai] = ?
                     WHERE [ID] = ?
                     """;
        try (Connection ct = DBConnect.getConnection(); PreparedStatement ps = ct.prepareStatement(sql)) {
            ps.setObject(1, tenChucVu);
            ps.setObject(2, NV.getTenNV());
            ps.setObject(3, NV.isGioiTinh());
            ps.setObject(4, NV.getSDT());
            ps.setObject(5, NV.getCCCD());
            ps.setObject(6, NV.getEmail());
            ps.setObject(7, NV.getNgaySinh());
            ps.setObject(8, NV.getTrangThai());
            ps.setObject(9, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean Delete(String ma) {
        int check = 0;
        String sql = """
                    UPDATE [dbo].[NhanVien]
                       SET Deleted = 1
                     WHERE ID = ?
                     """;
        try (Connection ct = DBConnect.getConnection(); PreparedStatement ps = ct.prepareStatement(sql)) {
            ps.setObject(1, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<nhanVienViewModel> getCbb(String tuKhoa) {
        List<nhanVienViewModel> lists = new ArrayList<>();
        String sql = """
                    SELECT dbo.NhanVien.ID, dbo.NhanVien.MaNhanVien, dbo.NhanVien.TenNhanVien, dbo.NhanVien.GioiTinh, dbo.NhanVien.SoDienThoai, dbo.NhanVien.CCCD, dbo.ChucVu.TenChucVu, dbo.NhanVien.Email, dbo.NhanVien.NgaySinh, 
                                                                                                 dbo.NhanVien.TrangThai
                                                                               FROM     dbo.NhanVien INNER JOIN
                                                                                                 dbo.ChucVu ON dbo.NhanVien.IDChucVu = dbo.ChucVu.ID
                                        where dbo.NhanVien.TrangThai LIKE ?
                     """;
        try (Connection ct = DBConnect.getConnection(); PreparedStatement ps = ct.prepareStatement(sql)) {
            ps.setObject(1, '%' + tuKhoa + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nhanVienViewModel NV = new nhanVienViewModel();
                NV.setId(rs.getString(1));
                NV.setMaNV(rs.getString(2));
                NV.setTenNV(rs.getString(3));
                NV.setGioiTinh(rs.getBoolean(4));
                NV.setSDT(rs.getString(5));
                NV.setCCCD(rs.getString(6));
                NV.setTenChucVu(rs.getString(7));
                NV.setEmail(rs.getString(8));
                NV.setNgaySinh(rs.getDate(9));
                NV.setTrangThai(rs.getString(10));
                lists.add(NV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

    public boolean isCCCDExisted(String cccd) {
        String sql = "SELECT COUNT(*) FROM dbo.NhanVien WHERE CCCD = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cccd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isCCCDExistedForAnotherEmployess(String cccd, int currentEmployesId) {
        String sql = "SELECT COUNT(*) FROM dbo.NhanVien WHERE CCCD = ? AND ID != ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cccd);
            ps.setInt(2, currentEmployesId); // Loại trừ khách hàng đang được sửa
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
