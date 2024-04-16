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
                ROW_NUMBER() OVER (ORDER BY s.Updated_at DESC) AS STT,
                s.ID,
                s.MaSanPham,
                s.TenSanPham,
                s.Created_at,
                s.Updated_at,
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
                s.ID, s.MaSanPham, s.TenSanPham, s.Created_at, s.Updated_at
            ORDER BY 
                s.Updated_at DESC;
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
        String sqlUpdateSanPham = "UPDATE SanPham SET Deleted = 1 WHERE ID = ?";
        String sqlUpdateChiTietSanPham = "UPDATE ChiTietSanPham SET Deleted = 1 WHERE IDSanPham = ?";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement psUpdateSanPham = con.prepareStatement(sqlUpdateSanPham);  PreparedStatement psUpdateChiTietSanPham = con.prepareStatement(sqlUpdateChiTietSanPham)) {

            con.setAutoCommit(false);

            // Cập nhật trường Deleted của bảng SanPham
            psUpdateSanPham.setString(1, maSP);
            psUpdateSanPham.executeUpdate();

            // Cập nhật trường Deleted của bảng ChiTietSanPham
            psUpdateChiTietSanPham.setString(1, maSP);
            psUpdateChiTietSanPham.executeUpdate();

            // Commit các thay đổi
            con.commit();

            check = 1; // Đánh dấu thành công
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Connection con = DBConnect.getConnection();
                // Rollback nếu có lỗi xảy ra
                con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        String sqlUpdateSanPham = "UPDATE [dbo].[SanPham] SET [TenSanPham] = ?, Updated_at = CURRENT_TIMESTAMP WHERE ID = ?";
        String sqlUpdateChiTiet = "UPDATE [dbo].[ChiTietSanPham] SET [IDSanPham] = ?, Updated_at = CURRENT_TIMESTAMP WHERE IDSanPham = ?";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement psUpdateSanPham = con.prepareStatement(sqlUpdateSanPham);  PreparedStatement psUpdateChiTiet = con.prepareStatement(sqlUpdateChiTiet)) {

            con.setAutoCommit(false); // Tắt chế độ tự động commit

            // Thực hiện cập nhật tên sản phẩm trong bảng SanPham
            psUpdateSanPham.setObject(1, sanPham.getTenSanPham());
            psUpdateSanPham.setObject(2, ID);
            check = psUpdateSanPham.executeUpdate();

            // Thực hiện cập nhật IDSanPham trong bảng ChiTietSanPham
            psUpdateChiTiet.setObject(1, ID);
            psUpdateChiTiet.setObject(2, ID);
            psUpdateChiTiet.executeUpdate();

            con.commit(); // Commit thay đổi
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Connection con = DBConnect.getConnection();
                con.rollback(); // Rollback nếu có lỗi
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return check > 0;
    }

    public boolean isTenSanPhamExisted(String tenSanPham) {
        String sql = "SELECT COUNT(*) FROM dbo.SanPham WHERE TenSanPham = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenSanPham);
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

    public List<SanPhamViewModel> search(String keyword) {
        List<SanPhamViewModel> spList = new ArrayList<>();
        String sql = """
         SELECT 
                            ROW_NUMBER() OVER (ORDER BY s.Created_at DESC) AS STT,
                            s.ID,
                            s.MaSanPham,
                            s.TenSanPham,
                            s.Created_at,
                            s.Updated_at,
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
                            s.ID, s.MaSanPham, s.TenSanPham, s.Created_at, s.Updated_at
                        ORDER BY 
                            s.Updated_at DESC;
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
                s.Updated_at,
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
                s.ID, s.MaSanPham, s.TenSanPham, s.Created_at, s.Updated_at
            HAVING
                ((? = N'Còn hàng' AND COUNT(c.IDSanPham) > 0) OR (? = N'Hết hàng' AND COUNT(c.IDSanPham) = 0))
            ORDER BY 
                s.Updated_at DESC;
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
