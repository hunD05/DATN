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
    public boolean addHD(){
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[HoaDon]
                                ([NgayTao]
                                ,[IDNhanVien]
                                )
                          VALUES
                                (current_timestamp,1)
                     """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
             check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
