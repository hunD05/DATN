/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.viewmodel.LSHDViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VHC
 */
public class LSHDViewModelRepo {
    public List<LSHDViewModel> getAll(int idHD) {
        List<LSHDViewModel> listLSHD = new ArrayList<>();
        String sql = """
                            SELECT  dbo.LichSuHoaDon.IDHoaDon, dbo.NhanVien.MaNhanVien, dbo.LichSuHoaDon.Gio, dbo.LichSuHoaDon.HanhDong
                            FROM      dbo.HoaDon INNER JOIN
                                             dbo.LichSuHoaDon ON dbo.HoaDon.ID = dbo.LichSuHoaDon.IDHoaDon INNER JOIN
                                             dbo.NhanVien ON dbo.HoaDon.IDNhanVien = dbo.NhanVien.ID AND dbo.LichSuHoaDon.IDNhanVien = dbo.NhanVien.ID
                            WHERE dbo.LichSuHoaDon.IDHoaDon = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LSHDViewModel lshd = new LSHDViewModel();
                lshd.setId(rs.getInt(1));
                lshd.setMaNV(rs.getString(2));
                lshd.setNgayCapNhat(rs.getTimestamp(3).toLocalDateTime());
                lshd.setHanhDong(rs.getString(4));
                listLSHD.add(lshd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLSHD;
    }
    
    public boolean addLSHD(String hanhDong){
        int check = 0;
        String sql = """
                     DECLARE @idHD int
                     SET @idHD = (SELECT TOP 1 ID FROM HoaDon ORDER BY Created_at DESC)
                     INSERT INTO [dbo].[LichSuHoaDon]
                                ([IDHoaDon]
                                ,[IDNhanVien]
                                ,[Gio]
                                ,[HanhDong]
                                )
                          VALUES
                                (@idHD,1,CURRENT_TIMESTAMP,?)
                     """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1,hanhDong);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
