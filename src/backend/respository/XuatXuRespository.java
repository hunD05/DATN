/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.ThuongHieu;
import backend.entity.XuatXu;
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
public class XuatXuRespository {

    public List<XuatXu> getAll() {
        List<XuatXu> ctspList = new ArrayList<>();
        String sql = """
                 SELECT ROW_NUMBER() OVER (ORDER BY [ID]) AS STT, [ID]
                       ,[MaXuatXu]
                       ,[TenXuatXu]
                   FROM [dbo].[XuatXu]where deleted = 0 ORder by Created_at desc
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                XuatXu chiTietSanPham = new XuatXu();
                chiTietSanPham.setSTT(rs.getInt(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setMaXuatXu(rs.getString(3));
                chiTietSanPham.setTenXuatXu(rs.getString(4));
                ctspList.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctspList;
    }

    public boolean add(XuatXu chiTietSanPham) {
        int check = 0;
        String sqlCheckName = "SELECT COUNT(*) FROM XuatXu WHERE TenXuatXu = ?";
        String sqlInsert = "INSERT INTO [dbo].[XuatXu] ([TenXuatXu]) VALUES (?)";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement psCheckName = con.prepareStatement(sqlCheckName);  PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

            // Kiểm tra xem tên XuatXu đã tồn tại hay chưa
            psCheckName.setString(1, chiTietSanPham.getTenXuatXu());
            ResultSet rsName = psCheckName.executeQuery();
            if (rsName.next() && rsName.getInt(1) > 0) {
                // Tên XuatXu đã tồn tại, không thể thêm mới
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên thuộc tính đã tồn tại");
                return false;
            }

            // Tên XuatXu chưa tồn tại, tiến hành thêm mới vào cơ sở dữ liệu
            psInsert.setString(1, chiTietSanPham.getTenXuatXu());
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

    public boolean update(XuatXu xuatXu, String id) {
    int check = 0;
    String sqlCheckName = "SELECT COUNT(*) FROM XuatXu WHERE TenXuatXu = ?";
    String sqlUpdate = "UPDATE [dbo].[XuatXu] SET [TenXuatXu] = ? WHERE ID = ?";

    try (Connection con = DBConnect.getConnection();
         PreparedStatement psCheckName = con.prepareStatement(sqlCheckName);
         PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {

        // Kiểm tra xem tên XuatXu đã tồn tại hay chưa
        psCheckName.setString(1, xuatXu.getTenXuatXu());
        ResultSet rsName = psCheckName.executeQuery();
        if (rsName.next() && rsName.getInt(1) > 0) {
            // Tên XuatXu đã tồn tại, không thể cập nhật
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên thuộc tính đã tồn tại");
            return false;
        }

        // Cập nhật thông tin XuatXu vào cơ sở dữ liệu
        psUpdate.setObject(1, xuatXu.getTenXuatXu());
        psUpdate.setObject(2, id);
        check = psUpdate.executeUpdate();

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
                 UPDATE [dbo].[XuatXu]
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
