/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.ChiTietSanPham;
import backend.entity.MauSac;
import backend.entity.NSX;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import raven.toast.Notifications;

/**
 *
 * @author leanb
 */
public class NSXRespository {

    public List<NSX> getAll() {
        List<NSX> ctspList = new ArrayList<>();
        String sql = """
                 SELECT ROW_NUMBER() OVER (ORDER BY [ID]) AS STT,
                     [ID]
                       ,[MaNSX]
                       ,[TenNSX]
                   FROM [dbo].[NSX]where deleted = 0 ORder by Updated_at desc
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NSX chiTietSanPham = new NSX();
                chiTietSanPham.setSTT(rs.getInt(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setMaNSX(rs.getString(3));
                chiTietSanPham.setTenNSX(rs.getString(4));
                ctspList.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctspList;
    }

    public boolean add(NSX chiTietSanPham) {
        int check = 0;
        String sqlCheckName = "SELECT COUNT(*) FROM NSX WHERE TenNSX = ?";
        String sqlInsert = "INSERT INTO [dbo].[NSX] ([TenNSX]) VALUES (?)";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement psCheckName = con.prepareStatement(sqlCheckName);  PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

            // Kiểm tra xem tên NSX đã tồn tại hay chưa
            psCheckName.setString(1, chiTietSanPham.getTenNSX());
            ResultSet rsName = psCheckName.executeQuery();
            if (rsName.next() && rsName.getInt(1) > 0) {
                // Tên NSX đã tồn tại, không thể thêm mới
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên thuộc tính đã tồn tại");
                return false;
            }

            // Tên NSX chưa tồn tại, tiến hành thêm mới vào cơ sở dữ liệu
            psInsert.setString(1, chiTietSanPham.getTenNSX());
            check = psInsert.executeUpdate();

            if (check > 0) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm mới thành công");
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm mới thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Đã xảy ra lỗi khi thêm mới");
        }

        return check > 0;
    }

    public boolean update(NSX nsx, String id) {
    int check = 0;
    String sqlCheckName = "SELECT COUNT(*) FROM NSX WHERE TenNSX = ?";
    String sqlUpdateNSX = "UPDATE [dbo].[NSX] SET [TenNSX] = ?, [Updated_at] = CURRENT_TIMESTAMP WHERE ID = ?";
    String sqlUpdateChiTietSanPham = "UPDATE [dbo].[ChiTietSanPham] SET [IDNSX] = (select top 1 id from NSX where TenNSX = ?) WHERE IDSanPham = ?";

    try (Connection con = DBConnect.getConnection();
         PreparedStatement psCheckName = con.prepareStatement(sqlCheckName);
         PreparedStatement psUpdateNSX = con.prepareStatement(sqlUpdateNSX);
         PreparedStatement psUpdateChiTietSanPham = con.prepareStatement(sqlUpdateChiTietSanPham)) {

        // Kiểm tra xem tên NSX đã tồn tại hay chưa
        psCheckName.setString(1, nsx.getTenNSX());
        ResultSet rsName = psCheckName.executeQuery();
        if (rsName.next() && rsName.getInt(1) > 0) {
            // Tên NSX đã tồn tại, không thể cập nhật
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên thuộc tính đã tồn tại");
            return false;
        }

        // Cập nhật thông tin NSX vào cơ sở dữ liệu
        psUpdateNSX.setObject(1, nsx.getTenNSX());
        psUpdateNSX.setObject(2, id);
        check = psUpdateNSX.executeUpdate();

        // Cập nhật tên NSX trong bảng ChiTietSanPham
        if (check > 0) {
            psUpdateChiTietSanPham.setObject(1, nsx.getTenNSX());
            psUpdateChiTietSanPham.setObject(2, id);
            psUpdateChiTietSanPham.executeUpdate();
        }

        if (check > 0) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Cập nhật thất bại");
        }
    } catch (Exception e) {
        e.printStackTrace();
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Đã xảy ra lỗi khi cập nhật");
    }

    return check > 0;
}


    public boolean delete(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[NSX]
                    SET 
                       [Deleted] = 1
                  WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
