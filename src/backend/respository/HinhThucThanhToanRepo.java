/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author VHC
 */
public class HinhThucThanhToanRepo {
    public boolean addHTTT(int idHD, String tenKieuTT, double tienCK, double tienMat){
        int check = 0;
        String sql = """
                     DECLARE @idHD int
                     SET @idHD = (SELECT TOP 1 ID FROM HoaDon WHERE ID LIKE ?)
                     DECLARE @idPTTT int
                     SET @idPTTT = (SELECT TOP 1 ID FROM PhuongThucThanhToan WHERE TenKieuThanhToan LIKE ?)
                     INSERT INTO [dbo].[HinhThucThanhToan]
                                ([IDHoaDon]
                                ,[IDPhuongThucThanhToan]
                                ,[TienCK]
                                ,[TienMat]
                                ,[Created_at]
                                ,[Updated_at])
                          VALUES
                                (@idHD, @idPTTT, ?, ?, Current_timestamp, Current_timestamp)
                     """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idHD);
            ps.setObject(2, tenKieuTT);
            ps.setObject(3, tienCK);
            ps.setObject(4, tienMat);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
