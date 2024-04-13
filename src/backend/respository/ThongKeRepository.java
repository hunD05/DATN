/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.ThongKe;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
/**
 *
 * @author leanb
 */
public class ThongKeRepository {
    public List<ThongKe> getAll() {
        List<ThongKe> lists = new ArrayList<>();
        String sql = """
                     SELECT 
                         HD.MaHoaDon, 
                         HD.NgayTao, 
                         SUM(HDCT.SoLuong * HDCT.GiaBan) AS TongTien
                     FROM 
                         HoaDon HD
                     INNER JOIN 
                         HoaDonChiTiet HDCT ON HD.ID = HDCT.IDHoaDon
                     GROUP BY 
                         HD.MaHoaDon, 
                         HD.NgayTao
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setMa(rs.getString(1));
                tk.setNgayTao(rs.getDate(2));
                tk.setTongTien(rs.getFloat(3));
                lists.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }
}
