/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.QRCode.qrcode;
import backend.entity.ChatLieu;
import backend.entity.CoAo;
import backend.entity.DangAo;
import backend.entity.DanhMuc;
import backend.entity.DuoiAo;
import backend.entity.MauSac;
import backend.entity.NSX;
import backend.entity.Size;
import backend.entity.TayAo;
import backend.entity.ThuongHieu;
import backend.entity.XuatXu;
import backend.respository.DBConnect;
import backend.service.ChatLieuService;
import backend.service.ChiTietSanPhamService;
import backend.service.CoAoService;
import backend.service.DangAoService;
import backend.service.DanhMucService;
import backend.service.DuoiAoService;
import backend.service.GiaBanService;
import backend.service.MauSacService;
import backend.service.NSXService;
import backend.service.SanPhamCBBService;
import backend.service.SizeService;
import backend.service.TayAoService;
import backend.service.ThuonHieuService;
import backend.service.XuatXuService;
import backend.viewmodel.SanPhamChiTietViewModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import raven.application.Application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author leanb
 */
public class ChiTietSanPham extends javax.swing.JPanel implements qrcode.QRCodeListener {

    /**
     * Creates new form ChiTietSanPham
     */
    DefaultTableModel dtm = new DefaultTableModel();
    List<SanPhamChiTietViewModel> chitietsanpham = new ArrayList<>();
    ChiTietSanPhamService chiTietSanPhamService = new ChiTietSanPhamService();
    private int selectedProductIndex = -1;
    private List<SanPhamChiTietViewModel> locCTSP = new ArrayList<>();
    DefaultComboBoxModel dcbmDanhMuc = new DefaultComboBoxModel();
    List<DanhMuc> danhMucs = new ArrayList<>();
    DanhMucService danhMucService = new DanhMucService();

    DefaultComboBoxModel dcbmNSX = new DefaultComboBoxModel();
    List<NSX> nsx = new ArrayList<>();
    NSXService nsxs = new NSXService();

    DefaultComboBoxModel dcbmXuatXu = new DefaultComboBoxModel();
    List<XuatXu> xuatXus = new ArrayList<>();
    XuatXuService xuatXuService = new XuatXuService();

    DefaultComboBoxModel dcbmDanhMucz = new DefaultComboBoxModel();
    List<DanhMuc> danhMucsz = new ArrayList<>();
    DanhMucService danhMucServicez = new DanhMucService();

    DefaultComboBoxModel dcbmNSXz = new DefaultComboBoxModel();
    List<NSX> nsxz = new ArrayList<>();
    NSXService nsxsz = new NSXService();

    DefaultComboBoxModel dcbmXuatXuz = new DefaultComboBoxModel();
    List<XuatXu> xuatXusz = new ArrayList<>();
    XuatXuService xuatXuServicez = new XuatXuService();

    DefaultComboBoxModel dcbmGia = new DefaultComboBoxModel();
    List<backend.entity.ChiTietSanPham> giaChiTietSanPhams = new ArrayList<>();
    GiaBanService giaBanService = new GiaBanService();

    DefaultComboBoxModel dcbmSanPham = new DefaultComboBoxModel();
    List<backend.entity.SanPham> sanPhams = new ArrayList<>();
    SanPhamCBBService sanPhamCBBService = new SanPhamCBBService();

    DefaultComboBoxModel dcbmMauSac = new DefaultComboBoxModel();
    List<MauSac> mauSacs = new ArrayList<>();
    MauSacService MauSacService = new MauSacService();

    DefaultComboBoxModel dcbmSize = new DefaultComboBoxModel();
    List<Size> sizes = new ArrayList<>();
    SizeService sizeService = new SizeService();

    DefaultComboBoxModel dcbmThuongHieu = new DefaultComboBoxModel();
    List<ThuongHieu> thuongHieus = new ArrayList<>();
    ThuonHieuService thuongHieuService = new ThuonHieuService();

    DefaultComboBoxModel dcbmChatLieu = new DefaultComboBoxModel();
    List<ChatLieu> chatLieus = new ArrayList<>();
    ChatLieuService chatLieuService = new ChatLieuService();

    DefaultComboBoxModel dcbmCoAo = new DefaultComboBoxModel();
    List<CoAo> coAos = new ArrayList<>();
    CoAoService coAoService = new CoAoService();

    DefaultComboBoxModel dcbmDuoiAo = new DefaultComboBoxModel();
    List<DuoiAo> duoiAos = new ArrayList<>();
    DuoiAoService duoiAoService = new DuoiAoService();

    DefaultComboBoxModel dcbmTayAo = new DefaultComboBoxModel();
    List<TayAo> tayAos = new ArrayList<>();
    TayAoService tayAoService = new TayAoService();

    DefaultComboBoxModel dcbmDangAo = new DefaultComboBoxModel();
    List<DangAo> dangAo = new ArrayList<>();
    DangAoService dangAoService = new DangAoService();

    public ChiTietSanPham() {
        initComponents();
        chitietsanpham = chiTietSanPhamService.getAll();
        dtm = (DefaultTableModel) tblChiTietSanPham.getModel();
        showDataTable(chitietsanpham);

        danhMucs = danhMucService.getAll();
        dcbmDanhMuc = (DefaultComboBoxModel) cbbDanhMuc.getModel();
        showcbbDanhMuc(danhMucs);

        nsx = nsxs.getAll();
        dcbmNSX = (DefaultComboBoxModel) cbbNSX.getModel();
        showcbbNSX(nsx);

        xuatXus = xuatXuService.getAll();
        dcbmXuatXu = (DefaultComboBoxModel) cbbXuatXu.getModel();
        showcbbXuatXu(xuatXus);

        danhMucsz = danhMucService.getAll();
        dcbmDanhMucz = (DefaultComboBoxModel) cbbDanhMucz.getModel();
        showcbbDanhMucz(danhMucsz);

        nsxz = nsxs.getAll();
        dcbmNSXz = (DefaultComboBoxModel) cbbNSXz.getModel();
        showcbbNSXz(nsxz);

        xuatXusz = xuatXuService.getAll();
        dcbmXuatXuz = (DefaultComboBoxModel) cbbXuatXuZ.getModel();
        showcbbXuatXuz(xuatXusz);

        giaChiTietSanPhams = giaBanService.getAll();
        dcbmGia = (DefaultComboBoxModel) cbbGia.getModel();
        showcbbGia(chitietsanpham);

        mauSacs = MauSacService.getAll();
        dcbmMauSac = (DefaultComboBoxModel) cbbMauSac.getModel();
        showcbbMauSac(mauSacs);

        sizes = sizeService.getAll();
        dcbmSize = (DefaultComboBoxModel) cbbSize.getModel();
        showcbbSize(sizes);

        thuongHieus = thuongHieuService.getAll();
        dcbmThuongHieu = (DefaultComboBoxModel) cbbThuongHieu.getModel();
        showcbbThuongHieu(thuongHieus);

        chatLieus = chatLieuService.getAll();
        dcbmChatLieu = (DefaultComboBoxModel) cbbChatLieu.getModel();
        showcbbChatLieu(chatLieus);

        coAos = coAoService.getAll();
        dcbmCoAo = (DefaultComboBoxModel) cbbCoAo.getModel();
        showcbbCoAo(coAos);

        duoiAos = duoiAoService.getAll();
        dcbmDuoiAo = (DefaultComboBoxModel) cbbDuoiAo.getModel();
        showcbbDuoiAo(duoiAos);

        tayAos = tayAoService.getAll();
        dcbmTayAo = (DefaultComboBoxModel) cbbTayAo.getModel();
        showcbbTayAo(tayAos);

        dangAo = dangAoService.getAll();
        dcbmDangAo = (DefaultComboBoxModel) cbbDangAo.getModel();
        showcbbDangAo(dangAo);

        sanPhams = sanPhamCBBService.getAllCBB();
        dcbmSanPham = (DefaultComboBoxModel) cbbSanPham.getModel();
        showcbbSanPham(sanPhams);

//        tblChiTietSanPham.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent evt) {
//                int index = tblChiTietSanPham.getSelectedRow();
//                if (index >= 0 && evt.getClickCount() == 2) {
//                    // Lấy dữ liệu từ danh sách chitietsanphams theo index được chọn
//                    SanPhamChiTietViewModel ctspViewModel = chitietsanpham.get(index);
//
//                    // Thiết lập dữ liệu cho các trường trong dialog SuaSanPhamChiTiet
//                    String idChiTietSP = ctspViewModel.getId() + "";
//                    String sanPham = ctspViewModel.getTenSanPham() + "";
//                    String danhMuc = ctspViewModel.getTenDanhMuc() + "";
//                    String NSX = ctspViewModel.getTenNSX() + "";
//                    String xuatxu = ctspViewModel.getTenXuatXu() + "";
//                    String mauSac = ctspViewModel.getTenMauSac() + "";
//                    String size = ctspViewModel.getTenSize() + "";
//                    String thuongHieu = ctspViewModel.getTenThuongHieu() + "";
//                    String chatLieu = ctspViewModel.getTenChatLieu() + "";
//                    String coAo = ctspViewModel.getTenCoAo() + "";
//                    String duoiAo = ctspViewModel.getMaDuoiAo() + "";
//                    String tayAo = ctspViewModel.getMaTayAo() + "";
//                    String dangAoz = ctspViewModel.getTenDangAo() + "";
//                    String soLuong = ctspViewModel.getSoLuong() + "";
//                    String gia = ctspViewModel.getGiaBan() + "";
//                    String moTa = ctspViewModel.getMota();
//                    String trangThai = ctspViewModel.getTrangThai();
//                    System.out.println(danhMuc);
//
//                    // Tạo dialog SuaSanPhamChiTiet với dữ liệu được thiết lập
//                    SuaSanPhamChiTiet chiTietSP = new SuaSanPhamChiTiet(idChiTietSP, sanPham, danhMuc, NSX, xuatxu, mauSac, size, thuongHieu, chatLieu, coAo, duoiAo, tayAo, dangAoz, soLuong, gia, moTa, trangThai);
//                    Application.showForm(chiTietSP);
//                }
//
//            }
//        });
        cbbDanhMuc.addActionListener(e -> performSearch());
        cbbXuatXu.addActionListener(e -> performSearch());
        cbbNSX.addActionListener(e -> performSearch());
        cbbGia.addActionListener(e -> performSearch());
    }

    public void showcbbDanhMuc(List<DanhMuc> danhMucs) {
        dcbmDanhMuc.removeAllElements();
        dcbmDanhMuc.addElement(null);
        for (DanhMuc danhMuc : danhMucs) {
            dcbmDanhMuc.addElement(danhMuc.getTenDanhMuc());
        }
//        cbbDanhMuc.setSelectedItem("Tất cả");
    }

    public void showcbbNSX(List<NSX> nsxs) {
        dcbmNSX.removeAllElements();
        // Thêm một mục null đầu tiên
        dcbmNSX.addElement(null);
        for (NSX nsx1 : nsxs) {
            dcbmNSX.addElement(nsx1.getTenNSX());
        }
//        cbbDanhMuc.setSelectedItem("Tất cả");
    }

    public void showcbbXuatXu(List<XuatXu> xuatXus) {
        dcbmXuatXu.removeAllElements();
        // Thêm một mục null đầu tiên
        dcbmXuatXu.addElement(null);
        for (XuatXu xuatXu : xuatXus) {
            dcbmXuatXu.addElement(xuatXu.getTenXuatXu());
        }
        cbbDanhMuc.setSelectedItem(null);
    }

    public void showcbbGia(List<SanPhamChiTietViewModel> chiTietSanPhamsz) {
        dcbmGia.removeAllElements();
        // Thêm các lựa chọn cho combobox
        dcbmGia.addElement(null);
        dcbmGia.addElement("Giá từ thấp đến cao");
        dcbmGia.addElement("Giá từ cao đến thấp");
        cbbDanhMuc.setSelectedItem("Tất cả");
    }

    public void showcbbDanhMucz(List<DanhMuc> danhMucs) {
        dcbmDanhMucz.removeAllElements();
        for (DanhMuc danhMuc : danhMucs) {
            dcbmDanhMucz.addElement(danhMuc.getTenDanhMuc());
        }
    }

    public void showcbbNSXz(List<NSX> nsxs) {
        dcbmNSXz.removeAllElements();
        for (NSX nsx1 : nsxs) {
            dcbmNSXz.addElement(nsx1.getTenNSX());
        }
    }

    public void showcbbXuatXuz(List<XuatXu> xuatXus) {
        dcbmXuatXuz.removeAllElements();
        for (XuatXu xuatXu : xuatXus) {
            dcbmXuatXuz.addElement(xuatXu.getTenXuatXu());
        }
    }

    public void showcbbMauSac(List<MauSac> mauSacs) {
        dcbmMauSac.removeAllElements();
        for (MauSac mauSac : mauSacs) {
            dcbmMauSac.addElement(mauSac.getTenMauSac());
        }
    }

    public void showcbbSize(List<Size> sizes) {
        dcbmSize.removeAllElements();
        for (Size size : sizes) {
            dcbmSize.addElement(size.getTenSize());
        }
    }

    public void showcbbThuongHieu(List<ThuongHieu> thuongHieus) {
        dcbmThuongHieu.removeAllElements();
        for (ThuongHieu thuongHieu : thuongHieus) {
            dcbmThuongHieu.addElement(thuongHieu.getTenThuongHieu());
        }
    }

    public void showcbbChatLieu(List<ChatLieu> chatLieus) {
        dcbmChatLieu.removeAllElements();
        for (ChatLieu chatLieu : chatLieus) {
            dcbmChatLieu.addElement(chatLieu.getTenChatLieu());
        }
    }

    public void showcbbCoAo(List<CoAo> coAos) {
        dcbmCoAo.removeAllElements();
        for (CoAo coAo : coAos) {
            dcbmCoAo.addElement(coAo.getTenCoAo());
        }
    }

    public void showcbbDuoiAo(List<DuoiAo> duoiAos) {
        dcbmDuoiAo.removeAllElements();
        for (DuoiAo duoiAo : duoiAos) {
            dcbmDuoiAo.addElement(duoiAo.getTenDuoiAo());
        }
    }

    public void showcbbTayAo(List<TayAo> tayAos) {
        dcbmTayAo.removeAllElements();
        for (TayAo tayAo : tayAos) {
            dcbmTayAo.addElement(tayAo.getTenTayAo());
        }
    }

    public void showcbbDangAo(List<DangAo> dangAos) {
        dcbmDangAo.removeAllElements();
        for (DangAo dangAo1 : dangAos) {
            dcbmDangAo.addElement(dangAo1.getTenDangAo());
        }
    }

    public void showcbbSanPham(List<backend.entity.SanPham> sanPhams) {
        dcbmSanPham.removeAllElements();
        for (backend.entity.SanPham sanPham : sanPhams) {
            dcbmSanPham.addElement(sanPham.getTenSanPham());
        }
    }

    public void showDataTable(List<SanPhamChiTietViewModel> sanPhamChiTietViewModels) {
        dtm.setRowCount(0);
        int stt = 1;
        for (SanPhamChiTietViewModel sanPhamChiTietViewModel : sanPhamChiTietViewModels) {
            // Định dạng giá bán
            BigDecimal giaBan = sanPhamChiTietViewModel.getGiaBan();
            String giaBanFormatted = formatGiaBan(giaBan);

            dtm.addRow(new Object[]{
                false, // Thêm một cột checkbox với giá trị mặc định là false
                stt++,
                sanPhamChiTietViewModel.getTenSanPham(),
                sanPhamChiTietViewModel.getTenDanhMuc(),
                sanPhamChiTietViewModel.getTenXuatXu(),
                sanPhamChiTietViewModel.getTenNSX(),
                sanPhamChiTietViewModel.getTenMauSac(),
                sanPhamChiTietViewModel.getTenSize(),
                sanPhamChiTietViewModel.getTenThuongHieu(),
                sanPhamChiTietViewModel.getTenChatLieu(),
                sanPhamChiTietViewModel.getTenCoAo(),
                sanPhamChiTietViewModel.getMaDuoiAo(),
                sanPhamChiTietViewModel.getMaTayAo(),
                sanPhamChiTietViewModel.getTenDangAo(),
                sanPhamChiTietViewModel.getSoLuong(),
                giaBanFormatted,
                sanPhamChiTietViewModel.getSoLuong()
            });
        }

        tblChiTietSanPham.setRowHeight(40);
        tblChiTietSanPham.getColumnModel().getColumn(0).setMaxWidth(50);

        // Thiết lập renderer cho tất cả các cột để canh giữa
        TableColumnModel columnModel = tblChiTietSanPham.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    ((JLabel) rendererComponent).setHorizontalAlignment(SwingConstants.CENTER);
                    return rendererComponent;
                }
            });
        }
        TableColumn checkboxColumn = tblChiTietSanPham.getColumnModel().getColumn(0); // Cột checkbox là cột đầu tiên
        checkboxColumn.setCellRenderer(new CheckBoxRenderer());
    }

    private BigDecimal extractNumericValue(String formattedPrice) {
        DecimalFormatSymbols symbols = ((DecimalFormat) NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"))).getDecimalFormatSymbols();
        String currencySymbol = "" + symbols.getCurrencySymbol();

        String numericValue = formattedPrice.replaceAll("[^\\d.,]", "").replace(currencySymbol, "");

        try {
            return new BigDecimal(numericValue);
        } catch (NumberFormatException e) {
            System.out.println("Không thể chuyển đổi chuỗi thành số BigDecimal.");
            return BigDecimal.ZERO; // hoặc một giá trị mặc định khác
        }
    }

    private String formatGiaBan(BigDecimal giaBan) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
        return formatter.format(giaBan);
    }

    @Override
    public void onQRCodeScanned(long result) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

// Lớp TableCellRenderer để hiển thị checkbox
    class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

        public CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setSelected((Boolean) value);
            return this;
        }
    }

    public backend.entity.ChiTietSanPham getFormData() {
        try {
            String sp = cbbSanPham.getSelectedItem().toString();
            BigDecimal gia;
            try {
                String giaText = txtGiaBan.getText().replaceAll("[^\\d.]", "");
                if (!giaText.isEmpty()) {
                    gia = new BigDecimal(giaText);
                } else {
                    gia = BigDecimal.ZERO; // hoặc giá trị mặc định khác nếu cần
                }
            } catch (NumberFormatException e) {
                // Xử lý ngoại lệ khi chuỗi không chứa số hợp lệ
                System.out.println("Không thể chuyển đổi chuỗi thành số BigDecimal.");
                gia = BigDecimal.ZERO; // hoặc một giá trị mặc định khác
            }

            String soLuong = txtSoLuong.getText();
            String moTa = txtMoTa.getText();
            String danhMuc = cbbDanhMucz.getSelectedItem().toString();
            String xuatXu = cbbXuatXuZ.getSelectedItem().toString();
            String nsx = cbbNSXz.getSelectedItem().toString();
            String mauSac = cbbMauSac.getSelectedItem().toString();
            String size = cbbSize.getSelectedItem().toString();
            String thuongHieu = cbbThuongHieu.getSelectedItem().toString();
            String chatLieu = cbbChatLieu.getSelectedItem().toString();
            String coAo = cbbCoAo.getSelectedItem().toString();
            String tayAo = cbbTayAo.getSelectedItem().toString();
            String duoiAo = cbbDuoiAo.getSelectedItem().toString();
            String dangAo = cbbDangAo.getSelectedItem().toString();
            String tinhTrang = cbbTrangThai.getSelectedItem() + "";

            backend.entity.ChiTietSanPham chiTietSanPham = new backend.entity.ChiTietSanPham(
                    sp,
                    gia,
                    soLuong,
                    moTa,
                    danhMuc,
                    xuatXu,
                    nsx,
                    mauSac,
                    size,
                    thuongHieu,
                    chatLieu,
                    coAo,
                    tayAo,
                    duoiAo,
                    dangAo,
                    tinhTrang
            );

            return chiTietSanPham;
        } catch (NumberFormatException e) {
            // Xử lý ngoại lệ khi có lỗi xảy ra trong việc chuyển đổi các giá trị thành chuỗi
            e.printStackTrace();
            return null;
        }
    }

    public void exportToExcel(List<SanPhamChiTietViewModel> chiTietSanPhams, String filePath) {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Chi tiết sản phẩm");

            // Tiêu đề cột
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Tên sản phẩm", "Danh mục", "Xuất xứ", "Nhà sản xuất", "Màu sắc", "Size", "Thương hiệu", "Chất liệu", "Cổ áo", "Đuôi áo", "Tay Áo", "Dáng áo", "Số lượng", "Giá bán"};

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Dữ liệu
            int rowNum = 1;
            for (SanPhamChiTietViewModel chiTietSanPham : chiTietSanPhams) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(chiTietSanPham.getTenSanPham());
                row.createCell(1).setCellValue(chiTietSanPham.getTenDanhMuc());
                row.createCell(2).setCellValue(chiTietSanPham.getTenXuatXu());
                row.createCell(3).setCellValue(chiTietSanPham.getTenNSX());
                row.createCell(4).setCellValue(chiTietSanPham.getTenMauSac());
                row.createCell(5).setCellValue(chiTietSanPham.getTenSize());
                row.createCell(6).setCellValue(chiTietSanPham.getTenThuongHieu());
                row.createCell(7).setCellValue(chiTietSanPham.getTenChatLieu());
                row.createCell(8).setCellValue(chiTietSanPham.getTenCoAo());
                row.createCell(9).setCellValue(chiTietSanPham.getMaDuoiAo());
                row.createCell(10).setCellValue(chiTietSanPham.getMaTayAo());
                row.createCell(11).setCellValue(chiTietSanPham.getTenDangAo());
                row.createCell(12).setCellValue(chiTietSanPham.getSoLuong());
                row.createCell(13).setCellValue(chiTietSanPham.getGiaBan().doubleValue()); // hoặc intValue() nếu giá trị là nguyên

            }

            // Lưu file Excel
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("File Excel đã được tạo thành công!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage generateQRCode(SanPhamChiTietViewModel product) {
        if (product == null) {
            return null; // Trả về null nếu product là null
        }

        // Tạo dữ liệu dựa trên thông tin sản phẩm
        String data = "Tên sản phẩm: " + product.getTenSanPham() + "\n"
                + "Danh mục: " + product.getTenDanhMuc() + "\n"
                + "Xuất xứ: " + product.getTenXuatXu() + "\n"
                + "NSX: " + product.getTenNSX() + "\n"
                + "Mô tả: " + product.getMota() + "\n"
                + // Sử dụng mô tả thay vì các thuộc tính khác
                "Màu sắc: " + product.getTenMauSac() + "\n"
                + "Size: " + product.getTenSize() + "\n"
                + "Trạng thái: " + product.getTrangThai() + "\n"
                + // Sử dụng trạng thái thay vì màu sắc
                "Thương hiệu: " + product.getTenThuongHieu() + "\n"
                + "Chất liệu: " + product.getTenChatLieu() + "\n"
                + "Cổ áo: " + product.getTenCoAo() + "\n"
                + "Đuôi áo: " + product.getMaDuoiAo() + "\n"
                + "Tay áo: " + product.getMaTayAo() + "\n"
                + "Dáng áo: " + product.getTenDangAo() + "\n"
                + "Số lượng: " + product.getSoLuong() + "\n"
                + "Giá bán: " + product.getGiaBan();

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 300, 300);

            int matrixWidth = bitMatrix.getWidth();
            int matrixHeight = bitMatrix.getHeight();

            BufferedImage image = new BufferedImage(matrixWidth, matrixHeight, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < matrixWidth; x++) {
                for (int y = 0; y < matrixHeight; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            return image;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private void searchAndDisplayData() {
//        String selectedDanhMuc = (String) cbbDanhMuc.getSelectedItem();
//        String selectedXuatXu = (String) cbbXuatXu.getSelectedItem();
//        String selectedNSX = (String) cbbNSX.getSelectedItem();
//        String selectedGia = (String) cbbGia.getSelectedItem();
//
//        // Gọi phương thức từ repository để lấy dữ liệu dựa trên các giá trị được chọn từ các combobox
//        List<SanPhamChiTietViewModel> filteredData = chiTietSanPhamService.search(selectedDanhMuc, selectedXuatXu, selectedNSX, selectedGia);
//
//        // Hiển thị dữ liệu trong bảng
//        showDataTable(filteredData);
//    }
//    private void filterCTSP() {
//        // Lấy các giá trị được chọn từ combobox
//        String danhmuc = cbbDanhMuc.getSelectedItem().toString();
//        String xuatxu = cbbXuatXu.getSelectedItem().toString();
//        String nsx = cbbNSX.getSelectedItem().toString();
//
//        // Kiểm tra xem giá trị được chọn từ combobox có null không
//
//
//        // Gọi phương thức SearchCbb với các tham số đã lấy từ combobox
//        locCTSP = chiTietSanPhamService.SearchCbb(danhmuc, xuatxu, nsx);
//        showDataTable(locCTSP);
//    }
//    private void openEditForm(SanPhamChiTietViewModel chiTietSanPham) {
//        SuaSanPhamChiTiet editForm = new SuaSanPhamChiTiet();
//        editForm.setData(chiTietSanPham);
//        Application.showForm(editForm);
//    }
//    @Override
//    public void onQRCodeScanned(long result) {
////        System.out.println("Nhận được giá trị QR Code trong HoaDon: " + result);
//
//        // Gọi phương thức để cập nhật dữ liệu hóa đơn tương ứng với giá trị QRCode
//        chitietsanpham = chiTietSanPhamService.getAllz(result);
//        showDataTable(chitietsanpham);
//
//        // Cập nhật dữ liệu cho CTSP
//        chitietsanpham = chiTietSanPhamService.getAll();
//        dtm = (DefaultTableModel) tblChiTietSanPham.getModel();
//        showDataTable(chitietsanpham);
//
//        QRCodeResultWindow resultWindow = new QRCodeResultWindow(chitietsanpham.toString());
//    }
//    private void filterCTSP() {
//
//        String nsx = (String) cbbDanhMuc.getSelectedItem();
//        String pin = (String) cbbXuatXu.getSelectedItem();
//        String manHinh = (String) cbbNSX.getSelectedItem();
//
//
//        // Kiểm tra xem giá trị được chọn từ combobox có null không
//        boolean sapXepGiaTangDan = "Giá Tăng Dần".equals(cbbGia.getSelectedItem());
//
//        locCTSP = chiTietSanPhamService.SearchCbb(manHinh, pin, manHinh, sapXepGiaTangDan);
//        showDataTable(locCTSP);
//        String selectedDanhMuc = (String) cbbDanhMuc.getSelectedItem();
//        String selectedXuatXu = (String) cbbXuatXu.getSelectedItem();
//        String selectedNSX = (String) cbbNSX.getSelectedItem();
//        String selectedGia = (String) cbbGia.getSelectedItem();
//
//        String giaBanSortOrder;
//        if ("Tất cả".equals(selectedGia)) {
//            // Thiết lập giá trị mặc định khi không có giá trị nào được chọn từ combobox
//            giaBanSortOrder = "Giá từ thấp đến cao";
//        } else {
//            // Sử dụng giá trị từ combobox
//            giaBanSortOrder = selectedGia;
//        }
//
//        // Kiểm tra xem giá đã được chọn hay không
//        if (!"Tất cả".equals(selectedDanhMuc) && !selectedDanhMuc.isEmpty()) {
//            List<SanPhamChiTietViewModel> filteredData = chiTietSanPhamService.filterData(selectedDanhMuc, selectedXuatXu, selectedNSX, giaBanSortOrder);
//            showDataTable(filteredData);
//        } else {
//            // Hiển thị toàn bộ dữ liệu
//            List<SanPhamChiTietViewModel> allData = chiTietSanPhamService.getAll();
//            showDataTable(allData);
//        }
//    }
    private void performSearch() {
        String selectedDanhMuc = (String) cbbDanhMuc.getSelectedItem();
        String selectedXuatXu = (String) cbbXuatXu.getSelectedItem();
        String selectedNSX = (String) cbbNSX.getSelectedItem();
        String selectedGia = (String) cbbGia.getSelectedItem();
        if ("Giá từ thấp đến cao".equals((String) cbbGia.getSelectedItem())) {
            selectedGia = "GiaBan ASC";
        } else if ("Giá từ cao đến thấp".equals((String) cbbGia.getSelectedItem())) {
            selectedGia = "GiaBan DESC";
        } else {
            selectedGia = "Created_at DESC";
        }
        chitietsanpham = chiTietSanPhamService.search(selectedDanhMuc, selectedXuatXu, selectedNSX, selectedGia);
        System.out.println(cbbXuatXu.getSelectedItem());
        // Hiển thị dữ liệu đã lọc
        showDataTable(chitietsanpham);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        roundPanel8 = new swing.RoundPanel();
        jPanel11 = new javax.swing.JPanel();
        btnThemSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnQuetQR = new javax.swing.JButton();
        btnTaiMaQR = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        btnResetBoLoc = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTietSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cbbGia = new combobox.Combobox();
        cbbNSX = new combobox.Combobox();
        cbbXuatXu = new combobox.Combobox();
        cbbDanhMuc = new combobox.Combobox();
        txtTimKiem = new textfield.TextField();
        btnTim = new javax.swing.JButton();
        roundPanel1 = new swing.RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbbDanhMucz = new javax.swing.JComboBox<>();
        cbbXuatXuZ = new javax.swing.JComboBox<>();
        cbbNSXz = new javax.swing.JComboBox<>();
        cbbMauSac = new javax.swing.JComboBox<>();
        cbbSize = new javax.swing.JComboBox<>();
        cbbThuongHieu = new javax.swing.JComboBox<>();
        cbbChatLieu = new javax.swing.JComboBox<>();
        cbbCoAo = new javax.swing.JComboBox<>();
        cbbDuoiAo = new javax.swing.JComboBox<>();
        cbbTayAo = new javax.swing.JComboBox<>();
        cbbDangAo = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cbbTrangThai = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        cbbSanPham = new javax.swing.JComboBox<>();
        txtSoLuong = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        panelQR = new javax.swing.JPanel();
        qrLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(222, 222, 222));
        setMaximumSize(new java.awt.Dimension(1138, 768));

        jPanel11.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        btnThemSua.setText("Thêm");
        btnThemSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSuaActionPerformed(evt);
            }
        });
        jPanel11.add(btnThemSua);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel11.add(btnXoa);

        btnQuetQR.setText("Quét QR");
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });
        jPanel11.add(btnQuetQR);

        btnTaiMaQR.setText("Tải mã QR");
        btnTaiMaQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiMaQRjButton5ActionPerformed(evt);
            }
        });
        jPanel11.add(btnTaiMaQR);

        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExceljButton5ActionPerformed(evt);
            }
        });
        jPanel11.add(btnXuatExcel);

        btnResetBoLoc.setText("Reset bộ lọc");
        btnResetBoLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetBoLocActionPerformed(evt);
            }
        });
        jPanel11.add(btnResetBoLoc);

        tblChiTietSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "STT", "Sản phẩm", "Danh mục", "Xuất xứ", "NSX", "Màu sắc", "Size", "Thương hiệu", "Chất liệu", "Cổ áo", "Đuôi áo", "Tay áo", "Dáng áo", "Số lượng", "Giá bán"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblChiTietSanPham);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Sản phẩm chi tiết");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1065, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Bộ lọc"));

        cbbGia.setLabeText("Giá");
        cbbGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGiaActionPerformed(evt);
            }
        });

        cbbNSX.setLabeText("Nhà sản xuất");
        cbbNSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNSXActionPerformed(evt);
            }
        });

        cbbXuatXu.setLabeText("Xuất xứ");
        cbbXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbXuatXuActionPerformed(evt);
            }
        });

        cbbDanhMuc.setLabeText("Danh mục");
        cbbDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbDanhMucActionPerformed(evt);
            }
        });

        txtTimKiem.setLabelText("Tìm kiếm");
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(cbbXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnTim, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel8Layout = new javax.swing.GroupLayout(roundPanel8);
        roundPanel8.setLayout(roundPanel8Layout);
        roundPanel8Layout.setHorizontalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 296, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel8Layout.setVerticalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        roundPanel1.setMaximumSize(new java.awt.Dimension(256, 756));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Thông tin sản phẩm chi tiết");

        jLabel3.setText("Sản phẩm:");

        jLabel4.setText("Danh mục:");

        jLabel5.setText("Xuất xứ:");

        jLabel6.setText("NSX: ");

        jLabel7.setText("Màu sắc:");

        jLabel8.setText("Size:");

        jLabel9.setText("Thương hiệu:");

        jLabel10.setText("Chất liệu:");

        jLabel11.setText("Cổ áo:");

        jLabel12.setText("Đuôi áo:");

        jLabel13.setText("Tay áo:");

        jLabel14.setText("Dáng áo:");

        jLabel15.setText("Số lượng:");

        jLabel16.setText("Giá bán:");

        cbbDanhMucz.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbDanhMucz.setAutoscrolls(true);
        cbbDanhMucz.setLightWeightPopupEnabled(false);
        cbbDanhMucz.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbDanhMucz.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbDanhMucz.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbXuatXuZ.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbXuatXuZ.setAutoscrolls(true);
        cbbXuatXuZ.setLightWeightPopupEnabled(false);
        cbbXuatXuZ.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbXuatXuZ.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbXuatXuZ.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbNSXz.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbNSXz.setAutoscrolls(true);
        cbbNSXz.setLightWeightPopupEnabled(false);
        cbbNSXz.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbNSXz.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbNSXz.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMauSac.setAutoscrolls(true);
        cbbMauSac.setLightWeightPopupEnabled(false);
        cbbMauSac.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbMauSac.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbMauSac.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbSize.setAutoscrolls(true);
        cbbSize.setLightWeightPopupEnabled(false);
        cbbSize.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbSize.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbSize.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbThuongHieu.setAutoscrolls(true);
        cbbThuongHieu.setLightWeightPopupEnabled(false);
        cbbThuongHieu.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbThuongHieu.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbThuongHieu.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbChatLieu.setAutoscrolls(true);
        cbbChatLieu.setLightWeightPopupEnabled(false);
        cbbChatLieu.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbChatLieu.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbChatLieu.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbCoAo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbCoAo.setAutoscrolls(true);
        cbbCoAo.setLightWeightPopupEnabled(false);
        cbbCoAo.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbCoAo.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbCoAo.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbDuoiAo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbDuoiAo.setAutoscrolls(true);
        cbbDuoiAo.setLightWeightPopupEnabled(false);
        cbbDuoiAo.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbDuoiAo.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbDuoiAo.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbTayAo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbTayAo.setAutoscrolls(true);
        cbbTayAo.setLightWeightPopupEnabled(false);
        cbbTayAo.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbTayAo.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbTayAo.setPreferredSize(new java.awt.Dimension(64, 25));

        cbbDangAo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbDangAo.setAutoscrolls(true);
        cbbDangAo.setLightWeightPopupEnabled(false);
        cbbDangAo.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbDangAo.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbDangAo.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel18.setText("Mô tả:");

        jLabel19.setText("Trạng thái:");

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn hàng", "Hết hàng" }));
        cbbTrangThai.setAutoscrolls(true);
        cbbTrangThai.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbTrangThai.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbTrangThai.setPreferredSize(new java.awt.Dimension(64, 25));

        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        cbbSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbSanPham.setAutoscrolls(true);
        cbbSanPham.setLightWeightPopupEnabled(false);
        cbbSanPham.setMaximumSize(new java.awt.Dimension(64, 25));
        cbbSanPham.setMinimumSize(new java.awt.Dimension(64, 25));
        cbbSanPham.setPreferredSize(new java.awt.Dimension(64, 25));
        cbbSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSanPhamActionPerformed(evt);
            }
        });

        txtSoLuong.setMaximumSize(new java.awt.Dimension(64, 25));
        txtSoLuong.setMinimumSize(new java.awt.Dimension(64, 25));
        txtSoLuong.setPreferredSize(new java.awt.Dimension(64, 25));

        txtGiaBan.setMaximumSize(new java.awt.Dimension(64, 25));
        txtGiaBan.setMinimumSize(new java.awt.Dimension(64, 25));
        txtGiaBan.setPreferredSize(new java.awt.Dimension(64, 25));

        txtMoTa.setMaximumSize(new java.awt.Dimension(64, 25));
        txtMoTa.setMinimumSize(new java.awt.Dimension(64, 25));
        txtMoTa.setPreferredSize(new java.awt.Dimension(64, 25));

        panelQR.setMaximumSize(new java.awt.Dimension(187, 187));
        panelQR.setMinimumSize(new java.awt.Dimension(187, 187));
        panelQR.setPreferredSize(new java.awt.Dimension(187, 187));

        javax.swing.GroupLayout panelQRLayout = new javax.swing.GroupLayout(panelQR);
        panelQR.setLayout(panelQRLayout);
        panelQRLayout.setHorizontalGroup(
            panelQRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQRLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(qrLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelQRLayout.setVerticalGroup(
            panelQRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQRLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(qrLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Mã QR sản phẩm");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbThuongHieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addGap(29, 29, 29)
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbMauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbbSize, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addGap(33, 33, 33)
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbXuatXuZ, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbbNSXz, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(cbbDanhMucz, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(26, 26, 26)
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbCoAo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbbChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addGap(32, 32, 32)
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbTayAo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbbDuoiAo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addGap(26, 26, 26)
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbDangAo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(cbbSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(33, 33, 33)
                                .addComponent(txtGiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))
                                .addGap(20, 20, 20)
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbTrangThai, 0, 121, Short.MAX_VALUE)
                                    .addComponent(txtMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(10, 10, 10))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelQR, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbbSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbbDanhMucz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbbXuatXuZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbbNSXz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbbThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbbCoAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbbDuoiAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbbTayAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cbbDangAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelQR, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed

    }//GEN-LAST:event_btnTimActionPerformed

    private void cbbDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbDanhMucActionPerformed

    }//GEN-LAST:event_cbbDanhMucActionPerformed

    private void cbbXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbXuatXuActionPerformed

    }//GEN-LAST:event_cbbXuatXuActionPerformed

    private void cbbNSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNSXActionPerformed

    }//GEN-LAST:event_cbbNSXActionPerformed

    private void cbbGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGiaActionPerformed

    }//GEN-LAST:event_cbbGiaActionPerformed

    private void tblChiTietSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietSanPhamMouseClicked
        int row = tblChiTietSanPham.getSelectedRow();
        if (row == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một sản phẩm từ bảng.");
            return;
        }

        SanPhamChiTietViewModel selectedProduct = chitietsanpham.get(row);

        // Cập nhật các trường dữ liệu lên giao diện người dùng
        cbbChatLieu.setSelectedItem(selectedProduct.getTenChatLieu());
        cbbCoAo.setSelectedItem(selectedProduct.getTenCoAo());
        cbbDangAo.setSelectedItem(selectedProduct.getTenDangAo());
        cbbDanhMucz.setSelectedItem(selectedProduct.getTenDanhMuc());
        cbbDuoiAo.setSelectedItem(selectedProduct.getMaDuoiAo());

        // Định dạng giá bán sang chuỗi trước khi hiển thị
        String giaBanFormatted = formatGiaBan(selectedProduct.getGiaBan());
        txtGiaBan.setText(giaBanFormatted);

        cbbMauSac.setSelectedItem(selectedProduct.getTenMauSac());
        cbbNSXz.setSelectedItem(selectedProduct.getTenNSX());
        cbbSanPham.setSelectedItem(selectedProduct.getTenSanPham());
        cbbSize.setSelectedItem(selectedProduct.getTenSize());
        txtSoLuong.setText(String.valueOf(selectedProduct.getSoLuong()));
        cbbTayAo.setSelectedItem(selectedProduct.getMaTayAo());
        cbbThuongHieu.setSelectedItem(selectedProduct.getTenThuongHieu());
        cbbXuatXuZ.setSelectedItem(selectedProduct.getTenXuatXu());
        cbbTrangThai.setSelectedItem(selectedProduct.getTrangThai());
        txtMoTa.setText(selectedProduct.getMota());

        // Lấy ID sản phẩm từ dòng được chọn
        int selectedProductId = (int) tblChiTietSanPham.getValueAt(row, 1);

        // Lấy thông tin chi tiết sản phẩm từ ID
        SanPhamChiTietViewModel selectedProductz = chiTietSanPhamService.getById(selectedProductId);

        if (selectedProductz != null) {
            // Tạo mã QR
            BufferedImage qrImage = generateQRCode(selectedProduct);

            if (qrImage != null) {
                // Lấy kích thước của panelQR
                int panelWidth = panelQR.getWidth();
                int panelHeight = panelQR.getHeight();

                // Điều chỉnh kích thước của hình ảnh mã QR
                Image scaledImage = qrImage.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
                ImageIcon qrIcon = new ImageIcon(scaledImage);

                // Cập nhật hình ảnh QR code vào qrLabel
                qrLabel.setIcon(qrIcon);
                qrLabel.setText(""); // Xóa bất kỳ văn bản nào có thể hiển thị trong qrLabel
                panelQR.revalidate(); // Cập nhật panel để hiển thị lại
                panelQR.repaint(); // Vẽ lại panel để hiển thị lại
            } else {
                // Xử lý trường hợp không tạo được hình ảnh mã QR
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không thể tạo hình ảnh mã QR.");
            }
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm với ID cung cấp
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không tìm thấy sản phẩm với ID: " + selectedProductId);
        }
    }//GEN-LAST:event_tblChiTietSanPhamMouseClicked

    private void btnResetBoLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetBoLocActionPerformed
        cbbDanhMuc.setSelectedIndex(0);
        cbbGia.setSelectedIndex(0);
        cbbNSX.setSelectedIndex(0);
        cbbXuatXu.setSelectedIndex(0);
    }//GEN-LAST:event_btnResetBoLocActionPerformed

    private void btnXuatExceljButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExceljButton5ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí để lưu file Excel");

        // Mở hộp thoại chọn đường dẫn và kiểm tra xem người dùng đã chọn hay chưa
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath() + ".xlsx"; // Đảm bảo có phần mở rộng .xlsx

            // Tạo danh sách để lưu các mục được chọn
            List<SanPhamChiTietViewModel> selectedItems = new ArrayList<>();

            // Biến kiểm tra xem có checkbox nào được chọn không
            boolean hasSelectedCheckbox = false;

            // Lặp qua các hàng trong bảng
            for (int i = 0; i < tblChiTietSanPham.getRowCount(); i++) {
                // Kiểm tra xem checkbox ở hàng này có được chọn hay không
                Boolean isSelected = (Boolean) tblChiTietSanPham.getValueAt(i, 0); // Giả sử cột checkbox là cột đầu tiên

                // Nếu được chọn, thêm dữ liệu của hàng này vào danh sách các mục được chọn và đặt hasSelectedCheckbox thành true
                if (isSelected) {
                    selectedItems.add(chitietsanpham.get(i));
                    hasSelectedCheckbox = true;
                }
            }

            // Nếu không có checkbox nào được chọn, xuất toàn bộ dữ liệu
            if (!hasSelectedCheckbox) {
                selectedItems = chitietsanpham;
            }

            // Xuất danh sách các mục được chọn ra file Excel
            exportToExcel(selectedItems, filePath);

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file Excel thành công");

        } else {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Đã hủy xuất file Excel");
        }
    }//GEN-LAST:event_btnXuatExceljButton5ActionPerformed

    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        qrcode qrScannerFrame = new qrcode();
        ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
        qrScannerFrame.setQRCodeListener(chiTietSanPham);
//        qrScannerFrame.setQRCodeListener(this);
        qrScannerFrame.setVisible(true);
    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // Kiểm tra xem người dùng đã chọn một hàng từ bảng hay chưa
        int row = tblChiTietSanPham.getSelectedRow();
        if (row == -1) {
            // Hiển thị thông báo lỗi nếu không có hàng nào được chọn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết sản phẩm để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hiển thị hộp thoại xác nhận trước khi thực hiện xóa
        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa chi tiết sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirmResult != JOptionPane.YES_OPTION) {
            // Người dùng không xác nhận xóa
            return;
        }

        // Lấy chi tiết sản phẩm từ danh sách
        SanPhamChiTietViewModel chiTietSanPham = chitietsanpham.get(row);

        // Thực hiện xóa
        if (chiTietSanPhamService.delete(chiTietSanPham.getId())) {
            // Xóa thành công, cập nhật lại danh sách và hiển thị
            chitietsanpham = chiTietSanPhamService.getAll();
            showDataTable(chitietsanpham);
            JOptionPane.showMessageDialog(this, "Xóa chi tiết sản phẩm thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Xóa không thành công, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Xóa chi tiết sản phẩm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSuaActionPerformed
        Application.showForm(new ThemSanPhamChiTiet());
    }//GEN-LAST:event_btnThemSuaActionPerformed

    private void cbbSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbSanPhamActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (validateFormData()) {
            int row = tblChiTietSanPham.getSelectedRow();
            SanPhamChiTietViewModel sanPhamChiTietViewModel = chitietsanpham.get(row);
            backend.entity.ChiTietSanPham chiTietSanPham = getFormData();

            if (chiTietSanPham == null) {
                // Có lỗi xảy ra khi lấy dữ liệu từ form
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi lấy dữ liệu từ form");
                return;
            }

            chiTietSanPhamService.update(chiTietSanPham, sanPhamChiTietViewModel.getId());
            chitietsanpham = chiTietSanPhamService.getAll();
            showDataTable(chitietsanpham);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnTaiMaQRjButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiMaQRjButton5ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu mã QR");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Lặp qua các hàng trong bảng
            for (int i = 0; i < tblChiTietSanPham.getRowCount(); i++) {
                // Kiểm tra xem checkbox ở hàng này có được chọn hay không
                Boolean isSelected = (Boolean) tblChiTietSanPham.getValueAt(i, 0); // Giả sử cột checkbox là cột đầu tiên

                // Nếu được chọn, tạo mã QR và lưu vào tập tin
                if (isSelected) {
                    SanPhamChiTietViewModel selectedProduct = chitietsanpham.get(i);
                    BufferedImage qrImage = generateQRCode(selectedProduct);

                    if (qrImage != null) {
                        try {
                            // Ghi hình ảnh QR vào tập tin
                            ImageIO.write(qrImage, "png", new File(fileToSave.getAbsolutePath() + "_" + i + ".png"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không thể tạo hình ảnh mã QR.");
                    }
                }
            }
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã lưu mã QR.");
        } else {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Đã hủy lưu mã QR.");
        }
    }//GEN-LAST:event_btnTaiMaQRjButton5ActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        chitietsanpham = chiTietSanPhamService.Search(txtTimKiem.getText());
        showDataTable(chitietsanpham);
    }//GEN-LAST:event_txtTimKiemKeyReleased
    private boolean validateFormData() {
        try {
            // Kiểm tra các ô nhập liệu có trống không
            if (cbbSanPham.getSelectedItem() == null || cbbDanhMucz.getSelectedItem() == null
                    || cbbNSXz.getSelectedItem() == null || cbbXuatXuZ.getSelectedItem() == null
                    || cbbMauSac.getSelectedItem() == null || cbbSize.getSelectedItem() == null
                    || cbbThuongHieu.getSelectedItem() == null || cbbChatLieu.getSelectedItem() == null
                    || cbbCoAo.getSelectedItem() == null || cbbDuoiAo.getSelectedItem() == null
                    || cbbTayAo.getSelectedItem() == null || cbbDangAo.getSelectedItem() == null
                    || txtSoLuong.getText().isEmpty() || txtGiaBan.getText().isEmpty()
                    || txtMoTa.getText().isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                return false;
            }

            // Kiểm tra các ô nhập số
            try {
                int soLuong = Integer.parseInt(txtSoLuong.getText());
                if (soLuong <= 0) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập số lượng là số dương");
                    return false;
                }
            } catch (NumberFormatException e) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập số lượng là số nguyên");
                return false;
            }

            try {
                String giaText = txtGiaBan.getText().replaceAll("[^\\d.]", "");
                BigDecimal gia = new BigDecimal(giaText);
                if (gia.compareTo(BigDecimal.ZERO) <= 0) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập giá là số dương");
                    return false;
                }
            } catch (NumberFormatException e) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập giá là số");
                return false;
            }

            // Kiểm tra ô trạng thái
            // (Bạn cần thêm kiểm tra ô trạng thái ở đây nếu cần)
        } catch (Exception ex) {
            ex.printStackTrace();
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi kiểm tra dữ liệu");
            return false;
        }
        return true;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnQuetQR;
    private javax.swing.JButton btnResetBoLoc;
    private javax.swing.JButton btnTaiMaQR;
    private javax.swing.JButton btnThemSua;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbChatLieu;
    private javax.swing.JComboBox<String> cbbCoAo;
    private javax.swing.JComboBox<String> cbbDangAo;
    private combobox.Combobox cbbDanhMuc;
    private javax.swing.JComboBox<String> cbbDanhMucz;
    private javax.swing.JComboBox<String> cbbDuoiAo;
    private combobox.Combobox cbbGia;
    private javax.swing.JComboBox<String> cbbMauSac;
    private combobox.Combobox cbbNSX;
    private javax.swing.JComboBox<String> cbbNSXz;
    private javax.swing.JComboBox<String> cbbSanPham;
    private javax.swing.JComboBox<String> cbbSize;
    private javax.swing.JComboBox<String> cbbTayAo;
    private javax.swing.JComboBox<String> cbbThuongHieu;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private combobox.Combobox cbbXuatXu;
    private javax.swing.JComboBox<String> cbbXuatXuZ;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelQR;
    private javax.swing.JLabel qrLabel;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel8;
    private javax.swing.JTable tblChiTietSanPham;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtSoLuong;
    private textfield.TextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
