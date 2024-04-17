/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.ChatLieu;
import backend.entity.CoAo;
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
public class CoAoRespository {

    public List<CoAo> getAll() {
        List<CoAo> ctspList = new ArrayList<>();
        String sql = """
                 SELECT
                     ROW_NUMBER() OVER (ORDER BY [ID]) AS STT, 
                     [ID],
                     [MaCoAo],
                     [TenCoAo]
                 FROM [dbo].[CoAo]
                 WHERE deleted = 0
                 ORDER BY Updated_at DESC
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CoAo chiTietSanPham = new CoAo();
                chiTietSanPham.setSTT(rs.getInt(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setMaCoAo(rs.getString(3));
                chiTietSanPham.setTenCoAo(rs.getString(4));
                ctspList.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctspList;
    }

    public boolean add(CoAo chiTietSanPham) {
        int check = 0;
        String sqlCheckName = "SELECT COUNT(*) FROM CoAo WHERE TenCoAo = ?";
        String sqlInsert = "INSERT INTO [dbo].[CoAo] ([TenCoAo]) VALUES (?)";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement psCheckName = con.prepareStatement(sqlCheckName);  PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

            // Kiểm tra xem tên cỡ áo đã tồn tại hay chưa
            psCheckName.setString(1, chiTietSanPham.getTenCoAo());
            ResultSet rsName = psCheckName.executeQuery();
            if (rsName.next() && rsName.getInt(1) > 0) {
                // Tên cỡ áo đã tồn tại, không thể thêm mới
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên thuộc tính đã tồn tại");
                return false;
            }

            // Tên cỡ áo chưa tồn tại, tiến hành thêm mới vào cơ sở dữ liệu
            psInsert.setString(1, chiTietSanPham.getTenCoAo());
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

    public boolean update(CoAo coAo, String id) {
    int check = 0;
    String sqlCheckName = "SELECT COUNT(*) FROM CoAo WHERE TenCoAo = ?";
    String sqlUpdateCoAo = "UPDATE [dbo].[CoAo] SET [TenCoAo] = ?, [Updated_at] = CURRENT_TIMESTAMP WHERE ID = ?";
    String sqlUpdateChiTietSanPham = "UPDATE [dbo].[ChiTietSanPham] SET [IDCoAo] = (select top 1 id from CoAo where TenCoAo = ?) WHERE IDCoAo = ?";

    try (Connection con = DBConnect.getConnection();
         PreparedStatement psCheckName = con.prepareStatement(sqlCheckName);
         PreparedStatement psUpdateCoAo = con.prepareStatement(sqlUpdateCoAo);
         PreparedStatement psUpdateChiTietSanPham = con.prepareStatement(sqlUpdateChiTietSanPham)) {

        // Kiểm tra xem tên CoAo đã tồn tại hay chưa
        psCheckName.setString(1, coAo.getTenCoAo());
        ResultSet rsName = psCheckName.executeQuery();
        if (rsName.next() && rsName.getInt(1) > 0) {
            // Tên CoAo đã tồn tại, không thể cập nhật
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên thuộc tính đã tồn tại");
            return false;
        }

        // Cập nhật thông tin CoAo vào cơ sở dữ liệu
        psUpdateCoAo.setObject(1, coAo.getTenCoAo());
        psUpdateCoAo.setObject(2, id);
        check = psUpdateCoAo.executeUpdate();

        // Cập nhật tên CoAo trong bảng ChiTietSanPham
        if (check > 0) {
            psUpdateChiTietSanPham.setObject(1, coAo.getTenCoAo());
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
                     UPDATE [dbo].[CoAo]
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
