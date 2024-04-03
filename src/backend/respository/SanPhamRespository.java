/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.ChiTietSanPham;
import backend.entity.SanPham;
import backend.viewmodel.SanPhamViewModel;
import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author leanb
 */
public class SanPhamRespository {

    public List<SanPhamViewModel> getAll() {
        List<SanPhamViewModel> spList = new ArrayList<>();
        String sql = """
         SELECT 
            ROW_NUMBER() OVER (ORDER BY s.Created_at DESC) AS STT,
            s.ID,
            s.MaSanPham,
            s.TenSanPham,
            s.Created_at,
            COUNT(c.IDSanPham) AS SoLuong,
            CASE
                WHEN COUNT(c.IDSanPham) = 0 THEN N'Hết hàng'
                ELSE N'Còn hàng'
            END AS TrangThai
         FROM 
            dbo.SanPham s
         LEFT JOIN
            dbo.ChiTietSanPham c ON c.IDSanPham = s.ID
         WHERE 
            s.Deleted = 0
         GROUP BY 
            s.ID, s.MaSanPham, s.TenSanPham, s.Created_at
         ORDER BY 
            s.Created_at DESC;
         """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamViewModel sanPham = new SanPhamViewModel();
                sanPham.setStt(rs.getInt("STT"));
                sanPham.setId(rs.getString("ID"));
                sanPham.setMaSanPham(rs.getString("MaSanPham"));
                sanPham.setTenSanPham(rs.getString("TenSanPham"));
                sanPham.setCreatedAt(rs.getTimestamp("Created_at"));
                sanPham.setSoLuong(rs.getLong("SoLuong"));
                sanPham.setTrangThai(rs.getString("TrangThai"));
                spList.add(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return spList;
    }

    public boolean add(SanPham sanPham) {
        int check = 0;
        String sqlCheckDuplicate = "SELECT COUNT(*) FROM dbo.SanPham WHERE MaSanPham = ?";
        String sqlInsert = "INSERT INTO [dbo].[SanPham] ([TenSanPham]) VALUES (?)";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement psCheck = con.prepareStatement(sqlCheckDuplicate);  PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

            // Kiểm tra xem mã sản phẩm đã tồn tại chưa
            psCheck.setString(1, sanPham.getMaSanPham());
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                // Nếu mã sản phẩm chưa tồn tại, thêm mới
                psInsert.setString(1, sanPham.getTenSanPham());
                check = psInsert.executeUpdate();
            } else {
                // Nếu mã sản phẩm đã tồn tại, không thêm mới
                System.out.println("Mã sản phẩm đã tồn tại. Không thêm mới.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    public boolean checkMaSanPhamExists(String maSP) {
        boolean exists = false;
        String sql = "SELECT COUNT(*) FROM dbo.SanPham WHERE MaSanPham = ? AND Deleted = 0";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    exists = count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }

    public boolean delete(String maSP) {
        int check = 0;
        String sql = """
                 UPDATE SanPham
                 set Deleted = 1
                 where ID = ?
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

//    public boolean delete(String maSP) {
//        int check = 0;
//        try ( Connection con = DBConnect.getConnection()) {
//            con.setAutoCommit(false);
//            String deleteChiTietSQL = "DELETE FROM ChiTietSanPham WHERE IDSanPham = ?";
//            try ( PreparedStatement deleteChiTietPS = con.prepareStatement(deleteChiTietSQL)) {
//                deleteChiTietPS.setObject(1, maSP);
//                deleteChiTietPS.executeUpdate();
//            }
//            String deleteSanPhamSQL = "DELETE FROM SanPham WHERE ID = ?";
//            try ( PreparedStatement deleteSanPhamPS = con.prepareStatement(deleteSanPhamSQL)) {
//                deleteSanPhamPS.setObject(1, maSP);
//                check = deleteSanPhamPS.executeUpdate();
//            }
//            con.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return check > 0;
//    }
    public boolean update(SanPham sanPham, String ID) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[SanPham]
                        SET 
                           [TenSanPham] = ?
                      WHERE ID = ?
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, sanPham.getTenSanPham());
            ps.setObject(2, ID);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<SanPhamViewModel> search(String keyword) {
        List<SanPhamViewModel> spList = new ArrayList<>();
        String sql = """
         SELECT 
                        ROW_NUMBER() OVER (ORDER BY s.Created_at DESC) AS STT,
                        s.ID,
                        s.MaSanPham,
                        s.TenSanPham,
                        s.Created_at,
                        COUNT(c.IDSanPham) AS SoLuong,
                        CASE
                            WHEN COUNT(c.IDSanPham) = 0 THEN N'Hết hàng'
                            ELSE N'Còn hàng'
                        END AS TrangThai
                     FROM 
                        dbo.SanPham s
                     LEFT JOIN
                        dbo.ChiTietSanPham c ON c.IDSanPham = s.ID
                     WHERE 
                        s.Deleted = 0 AND
                        (s.MaSanPham LIKE ? OR s.TenSanPham LIKE ?)
                     GROUP BY 
                        s.ID, s.MaSanPham, s.TenSanPham, s.Created_at
                     ORDER BY 
                        s.Created_at DESC;
         """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamViewModel sanPham = new SanPhamViewModel();
                sanPham.setStt(rs.getInt("STT"));
                sanPham.setId(rs.getString("ID"));
                sanPham.setMaSanPham(rs.getString("MaSanPham"));
                sanPham.setTenSanPham(rs.getString("TenSanPham"));
                sanPham.setCreatedAt(rs.getTimestamp("Created_at"));
                sanPham.setSoLuong(rs.getLong("SoLuong"));
                sanPham.setTrangThai(rs.getString("TrangThai"));
                spList.add(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return spList;
    }

    public List<SanPhamViewModel> searchByStatus(String status) {
        List<SanPhamViewModel> spList = new ArrayList<>();
        String sql = """
         SELECT 
            ROW_NUMBER() OVER (ORDER BY s.Created_at DESC) AS STT,
            s.ID,
            s.MaSanPham,
            s.TenSanPham,
            s.Created_at,
            COUNT(c.IDSanPham) AS SoLuong,
            CASE
                WHEN COUNT(c.IDSanPham) = 0 THEN N'Hết hàng'
                ELSE N'Còn hàng'
            END AS TrangThai
         FROM 
            dbo.SanPham s
         LEFT JOIN
            dbo.ChiTietSanPham c ON c.IDSanPham = s.ID
         WHERE 
            s.Deleted = 0
         GROUP BY 
            s.ID, s.MaSanPham, s.TenSanPham, s.Created_at
         HAVING
            ((? = N'Còn hàng' AND COUNT(c.IDSanPham) > 0) OR (? = N'Hết hàng' AND COUNT(c.IDSanPham) = 0))
         ORDER BY 
            s.Created_at DESC;
         """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamViewModel sanPham = new SanPhamViewModel();
                sanPham.setStt(rs.getInt("STT"));
                sanPham.setId(rs.getString("ID"));
                sanPham.setMaSanPham(rs.getString("MaSanPham"));
                sanPham.setTenSanPham(rs.getString("TenSanPham"));
                sanPham.setCreatedAt(rs.getTimestamp("Created_at"));
                sanPham.setSoLuong(rs.getLong("SoLuong"));
                sanPham.setTrangThai(rs.getString("TrangThai"));
                spList.add(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return spList;
    }

}
