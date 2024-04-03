/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;


import backend.entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leanb
 */
public class SanPhamCBBRespository {
    public List<SanPham> getAll() {
        List<SanPham> ctspList = new ArrayList<>();
        String sql = """
                 SELECT [ID],
                     [TenSanPham]
                   FROM [dbo].[SanPham] where deleted = 0
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham chiTietSanPham = new SanPham();
                chiTietSanPham.setId(rs.getString(1));
                chiTietSanPham.setTenSanPham(rs.getString(2));
                ctspList.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctspList;
    }
}
