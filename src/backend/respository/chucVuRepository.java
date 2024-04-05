/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.chucVu;
import backend.entity.nhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Huu Hai
 */
public class chucVuRepository {
    public List<chucVu> getAll() {
        List<chucVu> lists = new ArrayList<>();
        String sql = """
                     SELECT [ID]
                           
                           ,[TenChucVu]
                       FROM [dbo].[ChucVu]
                     """;
        try ( Connection ct = DBConnect.getConnection();  PreparedStatement ps = ct.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chucVu CV = new chucVu();
                CV.setId(rs.getString(1));
                CV.setTenCV(rs.getString(2));
                
                lists.add(CV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }
}
