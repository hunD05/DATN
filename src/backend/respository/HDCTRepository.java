/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.HDCTEntity;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author VHC
 */
public class HDCTRepository {
    public boolean addHDCT(int idHD, int idSPCT, int soLuong, double giaBan){
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[HoaDonChiTiet]
                                ([IDChiTietSP]
                                ,[IDHoaDon]
                                ,[SoLuong]
                                ,[GiaBan])
                          VALUES
                                (?,?,?,?)
                     """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idSPCT);
            ps.setObject(2, idHD);
            ps.setObject(3, soLuong);
            ps.setObject(4, giaBan);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
    
    public boolean updateHDCT(int idHD, int soLuong, int idCTSP){
        int check = 0;
        String sql = """
                     UPDATE [dbo].[HoaDonChiTiet]
                        SET [SoLuong] = ?
                           ,[Updated_at] = CURRENT_TIMESTAMP
                      WHERE IDHoaDon = ? AND [IDChiTietSP] = ?
                     """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, soLuong);
            ps.setObject(2, idHD);
            ps.setObject(3, idCTSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
    
        
    public boolean deleteHDCT(int idHD, int soLuong, int idCTSP){
        int check = 0;
        String sql = """
                     UPDATE [dbo].[HoaDonChiTiet]
                        SET [SoLuong] = ?
                           ,[Updated_at] = CURRENT_TIMESTAMP
                           ,[Deleted] = 1
                      WHERE IDHoaDon = ? AND [IDChiTietSP] = ?
                     """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, soLuong);
            ps.setObject(2, idHD);
            ps.setObject(3, idCTSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
    
}
