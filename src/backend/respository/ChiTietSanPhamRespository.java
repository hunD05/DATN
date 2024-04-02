/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.respository;

import backend.entity.ChiTietSanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 *
 * @author leanb
 */
public class ChiTietSanPhamRespository {

    public List<ChiTietSanPham> getAll() {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
                 SELECT 
                     ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                     ID,
                     [IDSanPham],
                     [GiaBan],
                     [SoLuong],
                     [MoTa],
                     [IDDanhMuc],
                     [IDXuatXu],
                     [IDNsx],
                     [IDMauSac],
                     [IDSize],
                     [IDThuongHieu],
                     [IDChatLieu],
                     [IDCoAo],
                     [IDDuoiAo],
                     [IDTayAo],
                     [IDDangAo],
                     [TrangThai],
                     [Deleted],
                     [Updated_at]
                 FROM 
                     [dbo].[ChiTietSanPham]
                     where Deleted = 0
                 ORDER BY 
                     [Updated_at] DESC;
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getLong(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setIdSanPham(rs.getLong(3));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(4));
                chiTietSanPham.setSoLuong(rs.getLong(5));
                chiTietSanPham.setMoTa(rs.getString(6));
                chiTietSanPham.setIdDanhMuc(rs.getLong(7));
                chiTietSanPham.setIdXuatXu(rs.getLong(8));
                chiTietSanPham.setIdNsx(rs.getLong(9));
                chiTietSanPham.setIdMauSac(rs.getLong(10));
                chiTietSanPham.setIdSize(rs.getLong(11));
                chiTietSanPham.setIdThuongHieu(rs.getLong(12));
                chiTietSanPham.setIdChatLieu(rs.getLong(13));
                chiTietSanPham.setIdCoAo(rs.getLong(14));
                chiTietSanPham.setIdDuoiAo(rs.getLong(15));
                chiTietSanPham.setIdTayAo(rs.getLong(16));
                chiTietSanPham.setIdDangAo(rs.getLong(17));
                chiTietSanPham.setTrangThai(rs.getString(18));
                chiTietSanPham.setDeleted(rs.getBoolean(19));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(20));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ cụ thể ở đây, ví dụ: ghi log hoặc thông báo người dùng
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }

    public ChiTietSanPham getById(long id) {
        ChiTietSanPham chiTietSanPham = null;
        String sql = "SELECT * FROM ChiTietSanPham WHERE ID = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setId(rs.getLong("ID"));
                chiTietSanPham.setIdSanPham(rs.getLong("IDSanPham"));
                chiTietSanPham.setGiaBan(rs.getBigDecimal("GiaBan"));
                chiTietSanPham.setSoLuong(rs.getLong("SoLuong"));
                chiTietSanPham.setMoTa(rs.getString("MoTa"));
                chiTietSanPham.setTrangThai(rs.getString("TrangThai"));
                chiTietSanPham.setCreatedAt(rs.getTimestamp("Created_at"));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp("Updated_at"));
                chiTietSanPham.setCreatedBy(rs.getString("Created_by"));
                chiTietSanPham.setUpdatedBy(rs.getString("Updated_by"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chiTietSanPham;
    }

    public List<ChiTietSanPham> DanhMuc(int selectedDanhMucID) {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
                     SELECT 
                                              ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                                          	ID,
                                              [GiaBan],
                                              [SoLuong],
                                              [MoTa],
                                              [TrangThai],
                                              [Updated_at]
                                          FROM 
                                              [dbo].[ChiTietSanPham]
                     						 where IDDanhMuc = ? and deleted = 0
                                          ORDER BY 
                                              [Updated_at] DESC;
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, selectedDanhMucID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getLong(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(3));
                chiTietSanPham.setSoLuong(rs.getLong(4));
                chiTietSanPham.setMoTa(rs.getString(5));
                chiTietSanPham.setTrangThai(rs.getString(6));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(7));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }

    public List<ChiTietSanPham> NSX(int selectedNsxID) {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
                     SELECT 
                                              ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                                          	ID,
                                              [GiaBan],
                                              [SoLuong],
                                              [MoTa],
                                              [TrangThai],
                                              [Updated_at]
                                          FROM 
                                              [dbo].[ChiTietSanPham]
                     						 where IDNsx = ? and deleted = 0
                                          ORDER BY 
                                              [Updated_at] DESC;
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, selectedNsxID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getLong(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(3));
                chiTietSanPham.setSoLuong(rs.getLong(4));
                chiTietSanPham.setMoTa(rs.getString(5));
                chiTietSanPham.setTrangThai(rs.getString(6));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(7));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }

    public List<ChiTietSanPham> XuatXu(int selectedXuatXuID) {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
                     SELECT 
                                              ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                                          	ID,
                                              [GiaBan],
                                              [SoLuong],
                                              [MoTa],
                                              [TrangThai],
                                              [Updated_at]
                                          FROM 
                                              [dbo].[ChiTietSanPham]
                     						 where IDXuatXu = ? and deleted = 0
                                          ORDER BY 
                                              [Updated_at] DESC;
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, selectedXuatXuID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getLong(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(3));
                chiTietSanPham.setSoLuong(rs.getLong(4));
                chiTietSanPham.setMoTa(rs.getString(5));
                chiTietSanPham.setTrangThai(rs.getString(6));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(7));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }

    public List<ChiTietSanPham> GiaBan(BigDecimal selectedGiaBan) {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
                     SELECT 
                                              ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                                          	ID,
                                              [GiaBan],
                                              [SoLuong],
                                              [MoTa],
                                              [TrangThai],
                                              [Updated_at]
                                          FROM 
                                              [dbo].[ChiTietSanPham]
                     						 where GiaBan = ? and deleted = 0
                                          ORDER BY 
                                              [Updated_at] DESC;
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, selectedGiaBan);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getLong(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(3));
                chiTietSanPham.setSoLuong(rs.getLong(4));
                chiTietSanPham.setMoTa(rs.getString(5));
                chiTietSanPham.setTrangThai(rs.getString(6));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(7));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }

    public List<ChiTietSanPham> ConHang() {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
                     SELECT 
                                                                   ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                                                               	ID,
                                                                   [GiaBan],
                                                                   [SoLuong],
                                                                   [MoTa],
                                                                   [TrangThai],
                                                                   [Updated_at]
                                                               FROM 
                                                                   [dbo].[ChiTietSanPham]
                                          						 where TrangThai LIKE N'Còn hàng' and deleted = 0
                                                               ORDER BY 
                                                                   [Updated_at] DESC;
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getLong(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(3));
                chiTietSanPham.setSoLuong(rs.getLong(4));
                chiTietSanPham.setMoTa(rs.getString(5));
                chiTietSanPham.setTrangThai(rs.getString(6));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(7));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }

    public List<ChiTietSanPham> HetHang() {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
SELECT 
                         ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                         ID,
                         [GiaBan],
                         [SoLuong],
                         [MoTa],
                         [TrangThai],
                         [Updated_at]
                     FROM 
                         [dbo].[ChiTietSanPham]
                     WHERE 
                         TrangThai LIKE N'Het hang' and deleted = 0
                     ORDER BY 
                         [Updated_at] DESC;
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getLong(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(3));
                chiTietSanPham.setSoLuong(rs.getLong(4));
                chiTietSanPham.setMoTa(rs.getString(5));
                chiTietSanPham.setTrangThai(rs.getString(6));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(7));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }

    public boolean add(ChiTietSanPham chiTietSanPham) {
        int check = 0;
        String sql = """
                 INSERT INTO [dbo].[ChiTietSanPham]
                            ([IDSanPham]
                            ,[GiaBan]
                            ,[SoLuong]
                            ,[MoTa]
                            ,[IDDanhMuc]
                            ,[IDXuatXu]
                            ,[IDNsx]
                            ,[IDMauSac]
                            ,[IDSize]
                            ,[IDThuongHieu]
                            ,[IDChatLieu]
                            ,[IDCoAo]
                            ,[IDDuoiAo]
                            ,[IDTayAo]
                            ,[IDDangAo]
                            ,[TrangThai])
                      VALUES
                            (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getIdSanPham());
                ps.setObject(2, chiTietSanPham.getGiaBan());
                ps.setObject(3, chiTietSanPham.getSoLuong());
                ps.setObject(4, chiTietSanPham.getMoTa());
                ps.setObject(5, chiTietSanPham.getIdDanhMuc());
                ps.setObject(6, chiTietSanPham.getIdXuatXu());
                ps.setObject(7, chiTietSanPham.getIdNsx());
                ps.setObject(8, chiTietSanPham.getIdMauSac());
                ps.setObject(9, chiTietSanPham.getIdSize());
                ps.setObject(10, chiTietSanPham.getIdThuongHieu());
                ps.setObject(11, chiTietSanPham.getIdChatLieu());
                ps.setObject(12, chiTietSanPham.getIdCoAo());
                ps.setObject(13, chiTietSanPham.getIdDuoiAo());
                ps.setObject(14, chiTietSanPham.getIdTayAo());
                ps.setObject(15, chiTietSanPham.getIdDangAo());
                ps.setObject(16, chiTietSanPham.getTrangThai());
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }
    
    public boolean update(ChiTietSanPham chiTietSanPham, String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[ChiTietSanPham]
                               SET [IDSanPham] = ?
                                  ,[GiaBan] = ?
                                  ,[SoLuong] = ?
                                  ,[MoTa] = ?
                                  ,[IDDanhMuc] = ?
                                  ,[IDXuatXu] = ?
                                  ,[IDNsx] = ?
                                  ,[IDMauSac] = ?
                                  ,[IDSize] = ?
                                  ,[IDThuongHieu] = ?
                                  ,[IDChatLieu] = ?
                                  ,[IDCoAo] = ?
                                  ,[IDDuoiAo] = ?
                                  ,[IDTayAo] = ?
                                  ,[IDDangAo] = ?
                                  ,[TrangThai] = ?
                             WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            if (chiTietSanPham != null) {
                ps.setObject(1, chiTietSanPham.getIdSanPham());
                ps.setObject(2, chiTietSanPham.getGiaBan());
                ps.setObject(3, chiTietSanPham.getSoLuong());
                ps.setObject(4, chiTietSanPham.getMoTa());
                ps.setObject(5, chiTietSanPham.getIdDanhMuc());
                ps.setObject(6, chiTietSanPham.getIdXuatXu());
                ps.setObject(7, chiTietSanPham.getIdNsx());
                ps.setObject(8, chiTietSanPham.getIdMauSac());
                ps.setObject(9, chiTietSanPham.getIdSize());
                ps.setObject(10, chiTietSanPham.getIdThuongHieu());
                ps.setObject(11, chiTietSanPham.getIdChatLieu());
                ps.setObject(12, chiTietSanPham.getIdCoAo());
                ps.setObject(13, chiTietSanPham.getIdDuoiAo());
                ps.setObject(14, chiTietSanPham.getIdTayAo());
                ps.setObject(15, chiTietSanPham.getIdDangAo());
                ps.setObject(16, chiTietSanPham.getTrangThai());
                ps.setObject(17, id);
                check = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }
    
    public boolean delete(Long maSP) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[ChiTietSanPham]
                    SET Deleted = 1
                  WHERE ID = ? 
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }
    
    public List<ChiTietSanPham> getAllz(Long masp) {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        String sql = """
                 SELECT 
                     ROW_NUMBER() OVER (ORDER BY [Updated_at] DESC) AS RowNumber,
                     ID,
                     [IDSanPham],
                     [GiaBan],
                     [SoLuong],
                     [MoTa],
                     [IDDanhMuc],
                     [IDXuatXu],
                     [IDNsx],
                     [IDMauSac],
                     [IDSize],
                     [IDThuongHieu],
                     [IDChatLieu],
                     [IDCoAo],
                     [IDDuoiAo],
                     [IDTayAo],
                     [IDDangAo],
                     [TrangThai],
                     [Deleted],
                     [Updated_at]
                 FROM 
                     [dbo].[ChiTietSanPham]
                     where Deleted = 0 and ID = ?
                 ORDER BY 
                     [Updated_at] DESC;
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            ps.setObject(1, masp);
            while (rs.next()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setStt(rs.getLong(1));
                chiTietSanPham.setId(rs.getLong(2));
                chiTietSanPham.setIdSanPham(rs.getLong(3));
                chiTietSanPham.setGiaBan(rs.getBigDecimal(4));
                chiTietSanPham.setSoLuong(rs.getLong(5));
                chiTietSanPham.setMoTa(rs.getString(6));
                chiTietSanPham.setIdDanhMuc(rs.getLong(7));
                chiTietSanPham.setIdXuatXu(rs.getLong(8));
                chiTietSanPham.setIdNsx(rs.getLong(9));
                chiTietSanPham.setIdMauSac(rs.getLong(10));
                chiTietSanPham.setIdSize(rs.getLong(11));
                chiTietSanPham.setIdThuongHieu(rs.getLong(12));
                chiTietSanPham.setIdChatLieu(rs.getLong(13));
                chiTietSanPham.setIdCoAo(rs.getLong(14));
                chiTietSanPham.setIdDuoiAo(rs.getLong(15));
                chiTietSanPham.setIdTayAo(rs.getLong(16));
                chiTietSanPham.setIdDangAo(rs.getLong(17));
                chiTietSanPham.setTrangThai(rs.getString(18));
                chiTietSanPham.setDeleted(rs.getBoolean(19));
                chiTietSanPham.setUpdatedAt(rs.getTimestamp(20));
                chiTietSanPhams.add(chiTietSanPham);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ cụ thể ở đây, ví dụ: ghi log hoặc thông báo người dùng
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }
}
