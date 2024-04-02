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
        String sql = """
                 INSERT INTO [dbo].[ChatLieu]
                            ([MaChatLieu]
                            ,[TenChatLieu])
                      VALUES
                            (?,?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getMaChatLieu());
                ps.setObject(2, chiTietSanPham.getTenChatLieu());
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }
    
    public boolean update(ChatLieu chatLieu, String id) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[ChatLieu]
                        SET [MaChatLieu] = ?
                           ,[TenChatLieu] = ?
                      WHERE ID = ?
                     """;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (chatLieu != null) {
                ps.setObject(1, chatLieu.getMaChatLieu());
                ps.setObject(2, chatLieu.getTenChatLieu());
                ps.setObject(3, id);
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

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
