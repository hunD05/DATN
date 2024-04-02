/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.TayAo;
import backend.entity.ThuongHieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leanb
 */
public class ThuongHieuRespository {

    public List<ThuongHieu> getAll() {
        List<ThuongHieu> ctspList = new ArrayList<>();
        String sql = """
                 SELECT ROW_NUMBER() OVER (ORDER BY [ID]) AS STT, [ID]
                       ,[MaThuongHieu]
                       ,[TenThuongHieu]
                   FROM [dbo].[ThuongHieu]where deleted = 0
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThuongHieu chiTietSanPham = new ThuongHieu();
                chiTietSanPham.setSTT(rs.getInt(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setMaThuongHieu(rs.getString(3));
                chiTietSanPham.setTenThuongHieu(rs.getString(4));
                ctspList.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctspList;
    }

    public boolean add(ThuongHieu chiTietSanPham) {
        int check = 0;
        String sql = """
                 INSERT INTO [dbo].[ThuongHieu]
                            ([MaThuongHieu]
                            ,[TenThuongHieu])
                      VALUES
                            (?,?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getMaThuongHieu());
                ps.setObject(2, chiTietSanPham.getTenThuongHieu());
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    public boolean update(ThuongHieu thuongHieu, String id) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[ThuongHieu]
                        SET [MaThuongHieu] = ?
                           ,[TenThuongHieu] = ?
                      WHERE ID = ?
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (thuongHieu != null) {
                ps.setObject(1, thuongHieu.getMaThuongHieu());
                ps.setObject(2, thuongHieu.getTenThuongHieu());
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
                     UPDATE [dbo].[ThuongHieu]
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
