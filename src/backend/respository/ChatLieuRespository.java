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
                                      where deleted = 0 
                                      ORder by Created_at desc
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
        String sqlCheck = "SELECT COUNT(*) FROM ChatLieu WHERE MaChatLieu = ?";
        String sqlInsert = "INSERT INTO [dbo].[ChatLieu] ([TenChatLieu]) VALUES ( ?)";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement psCheck = con.prepareStatement(sqlCheck);  PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

            // Kiểm tra xem mã thuộc tính đã tồn tại hay chưa
            psCheck.setString(1, chiTietSanPham.getMaChatLieu());
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Mã thuộc tính đã tồn tại, không thể thêm mới
                JOptionPane.showMessageDialog(null, "Mã thuộc tính đã tồn tại. Không thể thêm mới.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Mã thuộc tính chưa tồn tại, tiến hành thêm mới vào cơ sở dữ liệu
            psInsert.setString(1, chiTietSanPham.getTenChatLieu());
            check = psInsert.executeUpdate();

            if (check > 0) {
                JOptionPane.showMessageDialog(null, "Thêm mới thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Thêm mới thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi thêm mới.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return check > 0;
    }

    public boolean update(ChatLieu chatLieu, String id) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[ChatLieu]
                        SET 
                           [TenChatLieu] = ?
                      WHERE ID = ?
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chatLieu != null) {

                ps.setObject(1, chatLieu.getTenChatLieu());
                ps.setObject(2, id);
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
