/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;
import backend.entity.PhuongThucThanhToan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author VHC
 */
public class PhuongThucThanhToanRepo {
    public List<PhuongThucThanhToan> getTT(){
        List<PhuongThucThanhToan> listTT = new ArrayList<>();
        String sql = """
                     SELECT [ID]
                           ,[TenKieuThanhToan]
                       FROM [dbo].[PhuongThucThanhToan]
                     """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PhuongThucThanhToan pt = new PhuongThucThanhToan();
                pt.setId(rs.getInt(1));
                pt.setTenKieuThanhToan(rs.getString(2));
                listTT.add(pt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTT;
    }
}
