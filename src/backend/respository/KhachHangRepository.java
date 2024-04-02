/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.KhachHangEntity;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author HOME
 */
public class KhachHangRepository {
   public List<KhachHangEntity> getAll(){
       List<KhachHangEntity> listkhachhang = new ArrayList<>();
       String sql = """
                                                SELECT 
                                                    ROW_NUMBER() OVER (ORDER BY Updated_At DESC) AS STT,
                                                    [ID],
                                                    [MaKhachHang],
                                                    [TenKhachHang],
                                                    [GioiTinh],
                                                    [SoDienThoai],
                                                    [DiaChi]
                                                FROM 
                                                    [dbo].[KhachHang]
                                                ORDER BY 
                                                    Updated_At DESC;
                    """;
       try(Connection con = DBConnect.getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               KhachHangEntity kh = new KhachHangEntity();
               kh.setStt(rs.getLong(1));
               kh.setId(rs.getString(2));
               kh.setMaKhachHang(rs.getString(3));
               kh.setTenKhachHang(rs.getString(4));
               kh.setGioiTinh(rs.getBoolean(5));
               kh.setSoDienThoai(rs.getString(6));
               kh.setNgaySinh(rs.getDate(7));
               listkhachhang.add(kh);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return listkhachhang;
   }
   public boolean Add(KhachHangEntity khachhang){
       int check = 0;
       String sql = """
                    INSERT INTO [dbo].[KhachHang]
                               ([MaKhachHang]
                               ,[TenKhachHang]
                               ,[GioiTinh]
                               ,[SoDienThoai]
                               ,[DiaChi])
                               
                         VALUES
                               (?,?,?,?,?)
                    """;
       try(Connection con = DBConnect.getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
           ps.setObject(1, khachhang.getMaKhachHang());
           ps.setObject(2, khachhang.getTenKhachHang());
           ps.setObject(3, khachhang.getGioiTinh());
           ps.setObject(4, khachhang.getSoDienThoai());
           ps.setObject(5, khachhang.getNgaySinh());
           check = ps.executeUpdate();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return check > 0;
   }
    public boolean Update(KhachHangEntity khachhang, String Manew) {
    int check = 0;
    String sql = """
                        UPDATE [dbo].[KhachHang]
                      SET [MaKhachHang] = ?
                         ,[TenKhachHang] = ?
                         ,[GioiTinh] = ?
                         ,[SoDienThoai] = ?
                         ,[NgaySinh] = ?
                    WHERE ID = ?
                """;
    try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setObject(1, khachhang.getMaKhachHang());
        ps.setObject(2, khachhang.getTenKhachHang());
        ps.setObject(3, khachhang.getGioiTinh());
        ps.setObject(4, khachhang.getSoDienThoai());
        ps.setObject(5, khachhang.getNgaySinh());
        ps.setObject(6, Manew); // Đặt giá trị cho điều kiện WHERE

        check = ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return check > 0;
}
         public boolean Delete(String Ma){
       int check = 0;
       String sql = """
                    update KhachHang set Deleted = 1
                    where ID = ?
                    """;
       try(Connection con = DBConnect.getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
           ps.setObject(1, Ma);
           check = ps.executeUpdate();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return check > 0;
   }
          public List<KhachHangEntity> Tim(String ten){
       List<KhachHangEntity> listkhachhang = new ArrayList<>();
       String sql = """
                                                SELECT 
                                                                                                    ROW_NUMBER() OVER (ORDER BY Updated_At DESC) AS STT,
                                                                                                    [ID],
                                                                                                    [MaKhachHang],
                                                                                                    [TenKhachHang],
                                                                                                    [GioiTinh],
                                                                                                    [SoDienThoai],
                                                                                                    [NgaySinh]
                                                                                                FROM 
                                                                                                    [dbo].[KhachHang]
                                                                    Where KhachHang.MaKhachHang Like ? or
                                                					      KhachHang.TenKhachHang Like ? or
                                                						  KhachHang.SoDienThoai like ? 
                    """;
       try(Connection con = DBConnect.getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
           ps.setObject(1,'%'+ ten +'%');
           ps.setObject(2,'%'+ ten +'%');
           ps.setObject(3,'%'+ ten +'%');
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               KhachHangEntity kh = new KhachHangEntity();
               kh.setStt(rs.getLong(1));
               kh.setId(rs.getString(2));
               kh.setMaKhachHang(rs.getString(3));
               kh.setTenKhachHang(rs.getString(4));
               kh.setGioiTinh(rs.getBoolean(5));
               kh.setSoDienThoai(rs.getString(6));
               kh.setNgaySinh(rs.getDate(7));
               listkhachhang.add(kh);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return listkhachhang;
   }
    public static void main(String[] args) {
        List<KhachHangEntity> khachhang = new KhachHangRepository().getAll();
        for (KhachHangEntity khachHangEntity : khachhang) {
            System.out.println(khachHangEntity.toString());
        }
    }
}
