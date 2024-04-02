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

    public List<SanPham> getAll() {
        List<SanPham> spList = new ArrayList<>();
        String sql = """
                 SELECT 
                                        ROW_NUMBER() OVER (ORDER BY s.Created_at DESC) AS RowNumber,
                                        s.[ID],
                                        s.[MaSanPham],
                                        s.[TenSanPham],
                                        s.[Created_at]
                                    FROM 
                                        [dbo].[SanPham] s
                                    WHERE 
                                        s.[Deleted] = 0
                                    ORDER BY 
                                        s.[Created_at] DESC;
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sanPham = new SanPham();
                sanPham.setStt(rs.getInt(1));
                sanPham.setId(rs.getString(2));
                sanPham.setMaSanPham(rs.getString(3));
                sanPham.setTenSanPham(rs.getString(4));
                sanPham.setCreatedAt(rs.getTimestamp(5));
                spList.add(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return spList;
    }

    public boolean add(SanPham sanPham) {
        int check = 0;
        String sql = """
                 INSERT INTO [dbo].[SanPham]
                            (
                            [MaSanPham]
                            ,[TenSanPham])
                      VALUES
                            (?,?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (sanPham != null) {
                ps.setObject(1, sanPham.getMaSanPham());
                ps.setObject(2, sanPham.getTenSanPham());
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    public boolean delete(String maSP) {
        int check = 0;
        String sql = """
                 UPDATE SanPham
                 set Deleted = 1
                 where ID = ?
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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
        String sql = """
                     UPDATE [dbo].[SanPham]
                        SET 
                           [MaSanPham] = ?
                           ,[TenSanPham] = ?
                      WHERE ID = ?
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, sanPham.getMaSanPham());
            ps.setObject(2, sanPham.getTenSanPham());
            ps.setObject(3, ID);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
