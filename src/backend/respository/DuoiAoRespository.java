/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.DuoiAo;
import backend.entity.DanhMuc;
import backend.entity.DuoiAo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leanb
 */
public class DuoiAoRespository {

    public List<DuoiAo> getAll() {
        List<DuoiAo> ctspList = new ArrayList<>();
        String sql = """
                 SELECT ROW_NUMBER() OVER (ORDER BY [ID]) AS STT, 
                     [ID]
                       ,[MaDuoiAo]
                       ,[TenDuoiAo]
                   FROM [dbo].[DuoiAo]where deleted = 0
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DuoiAo chiTietSanPham = new DuoiAo();
                chiTietSanPham.setSTT(rs.getInt(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setMaDuoiAo(rs.getString(3));
                chiTietSanPham.setTenDuoiAo(rs.getString(4));
                ctspList.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctspList;
    }

    public boolean add(DuoiAo chiTietSanPham) {
        int check = 0;
        String sql = """
                 INSERT INTO [dbo].[DuoiAo]
                            ([MaDuoiAo]
                            ,[TenDuoiAo])
                      VALUES
                            (?,?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getMaDuoiAo());
                ps.setObject(2, chiTietSanPham.getTenDuoiAo());
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    public boolean update(DuoiAo duoiAo, String id) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[DuoiAo]
                        SET [MaDuoiAo] = ?
                           ,[TenDuoiAo] = ?
                      WHERE ID = ?
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (duoiAo != null) {
                ps.setObject(1, duoiAo.getMaDuoiAo());
                ps.setObject(2, duoiAo.getTenDuoiAo());
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
                     UPDATE [dbo].[DuoiAo]
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
