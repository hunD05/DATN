package backend.respository;

import java.sql.ResultSetMetaData;
import backend.respository.DBConnect;
import backend.viewmodel.HDCTViewModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QLHDRepo {

    public boolean xuatHoaDon() {
        try (Connection connection = DBConnect.getConnection()) {
            String query = """
                    SELECT 
                        dbo.HoaDon.ID, 
                        dbo.HoaDon.MaHoaDon, 
                        dbo.HoaDon.NgayTao, 
                        dbo.HoaDon.NgayThanhToan, 
                        SUM(dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.GiaBan) AS TongThanhTien, 
                        dbo.NhanVien.MaNhanVien, 
                        dbo.KhachHang.TenKhachHang, 
                        dbo.HoaDon.DiaChi, 
                        dbo.HoaDon.SoDienThoai, 
                        dbo.HoaDon.TrangThai 
                    FROM 
                        dbo.HoaDon 
                    INNER JOIN 
                        dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon 
                    INNER JOIN 
                        dbo.KhachHang ON dbo.HoaDon.IDKhachHang = dbo.KhachHang.ID 
                    INNER JOIN 
                        dbo.NhanVien ON dbo.HoaDon.IDNhanVien = dbo.NhanVien.ID 
                    GROUP BY 
                        dbo.HoaDon.ID, 
                        dbo.HoaDon.MaHoaDon, 
                        dbo.HoaDon.NgayTao, 
                        dbo.HoaDon.NgayThanhToan, 
                        dbo.NhanVien.MaNhanVien, 
                        dbo.KhachHang.TenKhachHang, 
                        dbo.HoaDon.DiaChi, 
                        dbo.HoaDon.SoDienThoai, 
                        dbo.HoaDon.TrangThai
                    """;

            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Danh sách hóa đơn");

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                Row headerRow = sheet.createRow(0);

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Cell cell = headerRow.createCell(i - 1);
                    cell.setCellValue(columnName);
                }

                int rowIndex = 1;
                while (resultSet.next()) {
                    Row row = sheet.createRow(rowIndex++);

                    // Tạo hyperlink cho ID
                    CreationHelper createHelper = workbook.getCreationHelper();
                    Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
                    hyperlink.setAddress("'Chi tiết hóa đơn - " + resultSet.getString("ID") + "'!A1");
                    Cell idCell = row.createCell(0);
                    idCell.setCellValue(resultSet.getInt("ID"));
                    idCell.setHyperlink(hyperlink);

                    // Fill data into cells
                    row.createCell(1).setCellValue(resultSet.getString("MaHoaDon"));
                    row.createCell(2).setCellValue(resultSet.getDate("NgayTao").toString());
                    row.createCell(3).setCellValue(resultSet.getDate("NgayThanhToan").toString());
                    row.createCell(4).setCellValue(resultSet.getDouble("TongThanhTien"));
                    row.createCell(5).setCellValue(resultSet.getString("MaNhanVien"));
                    row.createCell(6).setCellValue(resultSet.getString("TenKhachHang"));
                    row.createCell(7).setCellValue(resultSet.getString("DiaChi"));
                    row.createCell(8).setCellValue(resultSet.getString("SoDienThoai"));
                    row.createCell(9).setCellValue(resultSet.getString("TrangThai"));

                    // Lấy ID hóa đơn để lấy thông tin chi tiết hóa đơn
                    int idHoaDon = resultSet.getInt("ID");
                    HDCTViewModelRepo repo = new HDCTViewModelRepo();
                    List<HDCTViewModel> hoaDonChiTietList = repo.getAll(idHoaDon);

                    // Tạo sheet mới cho chi tiết hóa đơn
                    Sheet chiTietSheet = workbook.createSheet("Chi tiết hóa đơn - " + idHoaDon);
                    Row headerChiTietRow = chiTietSheet.createRow(0);
                    String[] chiTietHeaders = {"ID hóa đơn", "Tên sản phẩm", "Màu sắc", "Size", "Chất liệu", "Thương hiệu", "Số lượng", "Giá bán", "Tổng tiền"};
                    for (int i = 0; i < chiTietHeaders.length; i++) {
                        Cell chiTietCell = headerChiTietRow.createCell(i);
                        chiTietCell.setCellValue(chiTietHeaders[i]);
                    }

                    // Đổ dữ liệu chi tiết hóa đơn vào sheet mới
                    int chiTietRowIndex = 1;
                    for (HDCTViewModel hoaDonChiTiet : hoaDonChiTietList) {
                        Row chiTietRow = chiTietSheet.createRow(chiTietRowIndex++);
                        chiTietRow.createCell(0).setCellValue(hoaDonChiTiet.getId());
                        chiTietRow.createCell(1).setCellValue(hoaDonChiTiet.getTenSP());
                        chiTietRow.createCell(2).setCellValue(hoaDonChiTiet.getMauSac());
                        chiTietRow.createCell(3).setCellValue(hoaDonChiTiet.getSize());
                        chiTietRow.createCell(4).setCellValue(hoaDonChiTiet.getChatLieu());
                        chiTietRow.createCell(5).setCellValue(hoaDonChiTiet.getThuongHieu());
                        chiTietRow.createCell(6).setCellValue(hoaDonChiTiet.getSoLuong());
                        chiTietRow.createCell(7).setCellValue(hoaDonChiTiet.getGiaBan());
                        chiTietRow.createCell(8).setCellValue(hoaDonChiTiet.getGiaBan() * hoaDonChiTiet.getSoLuong());
                    }
                }

                // Tạo một CellStyle mới cho việc căn giữa
                CellStyle centerAlignStyle = workbook.createCellStyle();
                centerAlignStyle.setAlignment(HorizontalAlignment.CENTER);

                // Áp dụng CellStyle cho từng ô trong hàng tiêu đề
                for (int i = 0; i < columnCount; i++) {
                    Cell headerCell = headerRow.getCell(i);
                    headerCell.setCellStyle(centerAlignStyle);
                }

                // Set size for columns
                for (int i = 0; i < columnCount; i++) {
                    sheet.autoSizeColumn(i); // Tự động điều chỉnh kích thước của cột để vừa với nội dung
                }

                // Tạo hộp thoại chọn file
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setDialogTitle("Chọn nơi lưu file");

                // Hiển thị hộp thoại và lấy đường dẫn mục tiêu từ người dùng
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();

                    // Lưu workbook vào file Excel
                    try (FileOutputStream fileOutputStream = new FileOutputStream(filePath + ".xlsx")) {
                        workbook.write(fileOutputStream);
                        workbook.close();
                        System.out.println("File Excel đã được lưu tại: " + filePath + ".xlsx");
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Đã xảy ra lỗi khi lưu file Excel.");
                        return false;
                    }
                } else if (userSelection == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Đã hủy việc lưu file.");
                    return false;
                } else if (userSelection == JFileChooser.ERROR_OPTION) {
                    System.out.println("Đã xảy ra lỗi khi chọn nơi lưu file.");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
