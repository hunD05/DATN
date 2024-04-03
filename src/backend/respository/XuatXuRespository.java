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
        String sql = """
                 INSERT INTO [dbo].[XuatXu]
                            (
                            [TenXuatXu])
                      VALUES
                            (?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {

                ps.setObject(1, chiTietSanPham.getTenXuatXu());
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    public boolean update(XuatXu xuatXu, String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[XuatXu]
                    SET 
                       [TenXuatXu] = ?
                  WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (xuatXu != null) {
                
                ps.setObject(1, xuatXu.getTenXuatXu());
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
