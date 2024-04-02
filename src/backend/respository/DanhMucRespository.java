/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.ChiTietSanPham;
import backend.entity.DanhMuc;
import backend.entity.DangAo;
import backend.entity.DanhMuc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leanb
 */
public class DanhMucRespository {
    
    public List<DanhMuc> getAll() {
        List<DanhMuc> ctspList = new ArrayList<>();
        String sql = """
                 SELECT ROW_NUMBER() OVER (ORDER BY [ID]) AS STT, 
                     [ID]
                       ,[MaDanhMuc]
                       ,[TenDanhMuc]
                   FROM [dbo].[DanhMuc]where deleted = 0
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DanhMuc chiTietSanPham = new DanhMuc();
                chiTietSanPham.setSTT(rs.getInt(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setMaDanhMuc(rs.getString(3));
                chiTietSanPham.setTenDanhMuc(rs.getString(4));
                ctspList.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctspList;
    }
    public boolean add(DanhMuc chiTietSanPham) {
        int check = 0;
        String sql = """
                 INSERT INTO [dbo].[DanhMuc]
                            ([MaDanhMuc]
                            ,[TenDanhMuc])
                      VALUES
                            (?,?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getMaDanhMuc());
                ps.setObject(2, chiTietSanPham.getTenDanhMuc());
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }
    
    public boolean update(DanhMuc chiTietSanPham, String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[DanhMuc]
                    SET [MaDanhMuc] = ?
                       ,[TenDanhMuc] = ?
                  WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getMaDanhMuc());
                ps.setObject(2, chiTietSanPham.getTenDanhMuc());
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
                 UPDATE [dbo].[DanhMuc]
                    SET 
                       [Deleted] = 1
                  WHERE id = ?
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
