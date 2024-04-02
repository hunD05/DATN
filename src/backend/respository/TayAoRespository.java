/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.Size;
import backend.entity.TayAo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leanb
 */
public class TayAoRespository {
    public List<TayAo> getAll() {
        List<TayAo> ctspList = new ArrayList<>();
        String sql = """
                 SELECT ROW_NUMBER() OVER (ORDER BY [ID]) AS STT, 
                     [ID]
                       ,[MaTayAo]
                       ,[TenTayAo]
                   FROM [dbo].[TayAo]where deleted = 0
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TayAo chiTietSanPham = new TayAo();
                chiTietSanPham.setSTT(rs.getInt(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setMaTayAo(rs.getString(3));
                chiTietSanPham.setTenTayAo(rs.getString(4));
                ctspList.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctspList;
    }
    
    public boolean add(TayAo chiTietSanPham) {
        int check = 0;
        String sql = """
                 INSERT INTO [dbo].[TayAo]
                            ([MaTayAo]
                            ,[TenTayAo])
                      VALUES
                            (?,?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getMaTayAo());
                ps.setObject(2, chiTietSanPham.getTenTayAo());
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }
    
    public boolean update(TayAo tayAo, String id) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[TayAo]
                        SET [MaTayAo] = ?
                           ,[TenTayAo] = ?
                      WHERE ID = ?
                     """;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (tayAo != null) {
                ps.setObject(1, tayAo.getMaTayAo());
                ps.setObject(2, tayAo.getTenTayAo());
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
                     UPDATE [dbo].[TayAo]
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
