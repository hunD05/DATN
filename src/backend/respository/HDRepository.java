/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.HoaDonEntity;
import backend.respository.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author VHC
 */
public class HDRepository {

    public boolean addHD() {
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[HoaDon]
                                ([NgayTao]
                                ,[IDNhanVien]
                                )
                          VALUES
                                (current_timestamp,1)
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean updateHD(int idHD, String maNV, String soDT, String maGG){
        int check = 0; 
        String sql = """
                     UPDATE [dbo].[HoaDon]
                        SET [NgayThanhToan] = CURRENT_TIMESTAMP
                           ,[IDNhanVien] = (SElECT ID FROM NhanVien WHERE MaNhanVien LIKE ?)
                           ,[IDKhachHang] = (SElECT ID FROM KhachHang WHERE SoDienThoai LIKE ?)
                           ,[IDPhieuGG] = (SElECT ID FROM PhieuGiamGia WHERE TenGiamGia LIKE ?)
                           ,[DiaChi] = (SElECT DiaChi FROM KhachHang WHERE SoDienThoai LIKE ?)
                           ,[SoDienThoai] = (SElECT SoDienThoai FROM KhachHang WHERE SoDienThoai LIKE ?)
                     	  ,[TrangThai] = N'Đã Thanh Toán'
                     	  ,[Updated_at] = CURRENT_TIMESTAMP
                      WHERE ID = ?
                     """;
        try(Connection con =DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maNV);
            ps.setObject(2, soDT);
            ps.setObject(3, maGG);
            ps.setObject(4, soDT);
            ps.setObject(5, soDT);
            ps.setObject(6, idHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
