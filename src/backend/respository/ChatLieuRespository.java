/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.ChatLieu;
import backend.entity.ChiTietSanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import raven.toast.Notifications;

/**
 *
 * @author leanb
 */
public class ChatLieuRespository {

    public List<ChatLieu> getAll() {
        List<ChatLieu> ctspList = new ArrayList<>();
        String sql = """
                 SELECT 
                     ROW_NUMBER() OVER (ORDER BY [ID]) AS STT,
                     [ID],
                     [MaChatLieu],
                     [TenChatLieu]
                 FROM 
                     [dbo].[ChatLieu]
                 WHERE 
                     deleted = 0 
                 ORDER BY 
                     Updated_at DESC
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieu chiTietSanPham = new ChatLieu();
                chiTietSanPham.setSTT(rs.getInt(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setMaChatLieu(rs.getString(3));
                chiTietSanPham.setTenChatLieu(rs.getString(4));
                ctspList.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctspList;
    }

    public boolean add(ChatLieu chiTietSanPham) {
    int check = 0;
    String sqlCheckName = "SELECT COUNT(*) FROM ChatLieu WHERE TenChatLieu = ?";
    String sqlInsert = "INSERT INTO [dbo].[ChatLieu] ([TenChatLieu]) VALUES (?)";

    try (Connection con = DBConnect.getConnection();
         PreparedStatement psCheckName = con.prepareStatement(sqlCheckName);
         PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

        // Kiểm tra xem tên thuộc tính đã tồn tại hay chưa
        psCheckName.setString(1, chiTietSanPham.getTenChatLieu());
        ResultSet rsName = psCheckName.executeQuery();
        if (rsName.next() && rsName.getInt(1) > 0) {
            // Tên thuộc tính đã tồn tại, không thể thêm mới
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên thuộc tính đã tồn tại");
            return false;
        }

        // Tên thuộc tính chưa tồn tại, tiến hành thêm mới vào cơ sở dữ liệu
        psInsert.setString(1, chiTietSanPham.getTenChatLieu());
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


    public boolean update(ChatLieu chatLieu, String id) {
    int check = 0;
    String sqlCheckName = "SELECT COUNT(*) FROM ChatLieu WHERE TenChatLieu = ?";
    String sqlUpdateChatLieu = "UPDATE [dbo].[ChatLieu] SET [TenChatLieu] = ?, [Updated_at] = CURRENT_TIMESTAMP WHERE ID = ?";
String sqlUpdateChiTietSanPham = "UPDATE [dbo].[ChiTietSanPham] SET [IDChatLieu] = (select top 1 id from ChatLieu where TenChatLieu = ?) WHERE IDChatLieu = ?";
    
    try (Connection con = DBConnect.getConnection()) {
        con.setAutoCommit(false);  // Tắt chế độ tự động commit
        
        try (PreparedStatement psCheckName = con.prepareStatement(sqlCheckName);
             PreparedStatement psUpdateChatLieu = con.prepareStatement(sqlUpdateChatLieu);
             PreparedStatement psUpdateChiTietSanPham = con.prepareStatement(sqlUpdateChiTietSanPham)) {

            // Kiểm tra xem tên ChatLieu đã tồn tại hay chưa
            psCheckName.setString(1, chatLieu.getTenChatLieu());
            ResultSet rsName = psCheckName.executeQuery();
            if (rsName.next() && rsName.getInt(1) > 0) {
                // Tên ChatLieu đã tồn tại, không thể cập nhật
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên chất liệu đã tồn tại");
                return false;
            }

            // Cập nhật thông tin ChatLieu vào cơ sở dữ liệu
            psUpdateChatLieu.setObject(1, chatLieu.getTenChatLieu());
            psUpdateChatLieu.setObject(2, id);
            check = psUpdateChatLieu.executeUpdate();

            // Cập nhật tên chất liệu trong bảng ChiTietSanPham
            if (check > 0) {
                psUpdateChiTietSanPham.setObject(1, chatLieu.getTenChatLieu());
                psUpdateChiTietSanPham.setObject(2, id);
                psUpdateChiTietSanPham.executeUpdate();
            }

            if (check > 0) {
                con.commit();  // Xác nhận các thay đổi
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công");
            } else {
                con.rollback();  // Hủy bỏ các thay đổi nếu có lỗi
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Cập nhật thất bại");
            }
        } catch (Exception e) {
            con.rollback();  // Hủy bỏ các thay đổi khi có lỗi
            e.printStackTrace();
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Đã xảy ra lỗi khi cập nhật");
        } finally {
            con.setAutoCommit(true);  // Bật lại chế độ tự động commit
        }
    } catch (Exception e) {
        e.printStackTrace();
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không thể kết nối đến cơ sở dữ liệu");
    }

    return check > 0;
}



    public boolean delete(String id) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[ChatLieu]
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
