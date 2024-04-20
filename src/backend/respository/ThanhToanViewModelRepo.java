/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.viewmodel.ThanhToanViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author VHC
 */
public class ThanhToanViewModelRepo {

    public List<ThanhToanViewModel> getAll(int idHD) {
        List<ThanhToanViewModel> listTT = new ArrayList<>();
        String sql = """
                     SELECT       dbo.HinhThucThanhToan.IDHoaDon, dbo.HinhThucThanhToan.IDPhuongThucThanhToan, dbo.HinhThucThanhToan.TienCK, dbo.HinhThucThanhToan.TienMat, dbo.PhuongThucThanhToan.ID, 
                                              dbo.PhuongThucThanhToan.TenKieuThanhToan
                     FROM            dbo.HinhThucThanhToan INNER JOIN
                                              dbo.PhuongThucThanhToan ON dbo.HinhThucThanhToan.IDPhuongThucThanhToan = dbo.PhuongThucThanhToan.ID WHERE IDHoaDon = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1,idHD);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ThanhToanViewModel tt = new ThanhToanViewModel();
                tt.setIdHD(rs.getInt(1));
                tt.setIdPTTT(rs.getInt(2));
                tt.setTienTK(rs.getDouble(3));
                tt.setTienMat(rs.getDouble(4));
                tt.setTenKieuThanhToan(rs.getString(5));
                listTT.add(tt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTT;
    }
}
