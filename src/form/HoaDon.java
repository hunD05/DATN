/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.entity.ChatLieu;
import backend.entity.MauSac;
import backend.entity.Size;
import backend.entity.ThuongHieu;
import backend.qrcodeBH.quetQR;
import backend.service.BanHangService;
import backend.service.ChatLieuService;
import backend.service.HDCTService;
import backend.service.HoaDonService;
import backend.service.LSHDService;
import backend.service.MauSacService;
import backend.service.QLHDService;
import backend.service.SizeService;
import backend.service.ThuonHieuService;
import backend.viewmodel.HDCTViewModel;
import backend.viewmodel.HoaDonViewModel;
import backend.viewmodel.LSHDViewModel;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import print.ReportManager;
import print.model.FieldReportPayment;
import print.model.ParameterReportPayment;
import raven.application.Application;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import raven.toast.Notifications;
import cellHD.TableActionCellEditor;
import cellHD.TableActionCellRender;
import cellHD.TableActionEvent;

/**
 *
 * @author leanb
 */
public class HoaDon extends javax.swing.JPanel {

    private DefaultTableModel dtmHD = new DefaultTableModel();
    private List<HoaDonViewModel> listHD = new ArrayList<>();
    private HoaDonService srHD = new HoaDonService();

    private DefaultTableModel dtmHDCT = new DefaultTableModel();
    private List<HDCTViewModel> listHDCT = new ArrayList<>();
    private HDCTService srHDCT = new HDCTService();

    private DefaultTableModel dtmLSHD = new DefaultTableModel();
    private List<LSHDViewModel> listLSHD = new ArrayList<>();
    private LSHDService srLSHD = new LSHDService();

    private DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
    private List<HoaDonViewModel> listTT = new ArrayList<>();

    private QLHDService srQLHD = new QLHDService();

    private int selectedInvoiceId = 0;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

    private NumberFormat currencyFormat = new DecimalFormat("###,###,### VND");

    private DefaultComboBoxModel dcbmMauSac = new DefaultComboBoxModel();
    private List<MauSac> mauSacs = new ArrayList<>();
    private MauSacService MauSacService = new MauSacService();

    private DefaultComboBoxModel dcbmSize = new DefaultComboBoxModel();
    private List<Size> sizes = new ArrayList<>();
    private SizeService sizeService = new SizeService();

    private DefaultComboBoxModel dcbmThuongHieu = new DefaultComboBoxModel();
    private List<ThuongHieu> thuongHieus = new ArrayList<>();
    private ThuonHieuService thuongHieuService = new ThuonHieuService();

    private DefaultComboBoxModel dcbmChatLieu = new DefaultComboBoxModel();
    private List<ChatLieu> chatLieus = new ArrayList<>();
    private ChatLieuService chatLieuService = new ChatLieuService();

    private BanHangService srBH = new BanHangService();

    private DefaultComboBoxModel dcbmGia = new DefaultComboBoxModel();

    private boolean showActions = false;

    /**
     * Creates new form HoaDon
     */
    public HoaDon() {
        initComponents();
        dtmHD = (DefaultTableModel) tblHD.getModel();
        listHD = srHD.getAll();
        showDataTable1(listHD);

        showCbbTT();

//tim min max
        txtMin.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchHD();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchHD();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchHD();
            }
        });

        txtMax.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchHD();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchHD();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchHD();
            }
        });

//tìm kiếm theo mã hóa đơn, tên khách hàng, địa chỉ
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchHDTheoTen();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchHDTheoTen();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchHDTheoTen();
            }
        });

        mauSacs = MauSacService.getAll();
        dcbmMauSac = (DefaultComboBoxModel) cbbMau.getModel();

        sizes = sizeService.getAll();
        dcbmSize = (DefaultComboBoxModel) cbbSize.getModel();

        thuongHieus = thuongHieuService.getAll();
        dcbmThuongHieu = (DefaultComboBoxModel) cbbThuongHieu.getModel();

        chatLieus = chatLieuService.getAll();
        dcbmChatLieu = (DefaultComboBoxModel) cbbChatLieu.getModel();

        dcbmGia = (DefaultComboBoxModel) cbbGiaTien.getModel();

        cbbMau.addActionListener(e -> searchHDCT(Integer.valueOf(selectedInvoiceId)));
        cbbChatLieu.addActionListener(e -> searchHDCT(Integer.valueOf(selectedInvoiceId)));
        cbbThuongHieu.addActionListener(e -> searchHDCT(Integer.valueOf(selectedInvoiceId)));
        cbbSize.addActionListener(e -> searchHDCT(Integer.valueOf(selectedInvoiceId)));
        cbbGiaTien.addActionListener(e -> searchHDCT(Integer.valueOf(selectedInvoiceId)));

    }

    public void showcbbMauSac(List<MauSac> mauSacs) {
        dcbmMauSac.removeAllElements();
        dcbmMauSac.addElement(null);
        for (MauSac mauSac : mauSacs) {
            dcbmMauSac.addElement(mauSac.getTenMauSac());
        }
    }

    public void showcbbSize(List<Size> sizes) {
        dcbmSize.removeAllElements();
        dcbmSize.addElement(null);
        for (Size size : sizes) {
            dcbmSize.addElement(size.getTenSize());
        }
    }

    public void showcbbThuongHieu(List<ThuongHieu> thuongHieus) {
        dcbmThuongHieu.removeAllElements();
        dcbmThuongHieu.addElement(null);
        for (ThuongHieu thuongHieu : thuongHieus) {
            dcbmThuongHieu.addElement(thuongHieu.getTenThuongHieu());
        }
    }

    public void showcbbChatLieu(List<ChatLieu> chatLieus) {
        dcbmChatLieu.removeAllElements();
        dcbmChatLieu.addElement(null);
        for (ChatLieu chatLieu : chatLieus) {
            dcbmChatLieu.addElement(chatLieu.getTenChatLieu());
        }
    }

    public void showcbbGia() {
        dcbmGia.removeAllElements();
        dcbmGia.addElement(null);
        dcbmGia.addElement("Giá từ thấp đến cao");
        dcbmGia.addElement("Giá từ cao đến thấp");
    }

    public void showCbbTT() {
        dcbm.addElement("Tất cả");
        dcbm.addElement("Chưa Thanh Toán");
        dcbm.addElement("Đã Thanh Toán");
        dcbm.addElement("Chờ Giao");
        dcbm.addElement("Đang Giao");
    }

    public void showDataTable1(List<HoaDonViewModel> listHD) {
        dtmHD.setRowCount(0);
        int i = 1;
        tblHD.setRowHeight(35);
        for (HoaDonViewModel hd : listHD) {
            String ngayThanhToan = hd.getNgayThanhToan().format(formatter);
            dtmHD.addRow(new Object[]{
                i++, hd.getMaHD(),
                ngayThanhToan,
                hd.getMaNV(), hd.getTenKH(), hd.getDiaChi(),
                hd.getSoDT(),
                currencyFormat.format(hd.getGiaTien()),
                hd.getHinhThucThanhToan(),
                hd.getTrangThai(),});
        }
    }

    public void showDataTable2(List<HDCTViewModel> listHDCT) {
        dtmHDCT.setRowCount(0);
        int i = 1;
        for (HDCTViewModel hdct : listHDCT) {
            dtmHDCT.addRow(new Object[]{
                i++,
                "SPCT-" + hdct.getMaSPCT(),
                hdct.getTenSP(),
                hdct.getMauSac(),
                hdct.getSize(),
                hdct.getChatLieu(),
                hdct.getThuongHieu(),
                hdct.getSoLuong(),
                currencyFormat.format(hdct.getGiaBan()),
                currencyFormat.format(hdct.getSoLuong() * hdct.getGiaBan())
            });
        }
    }

    public void showDataTable3(List<LSHDViewModel> listLSHD) {
        dtmLSHD.setRowCount(0);
        int i = 1;
        for (LSHDViewModel lshd : listLSHD) {
            // Loại bỏ chữ 'T' từ chuỗi ngày thời gian
            String ngayCapNhat = lshd.getNgayCapNhat().format(formatter);
            dtmLSHD.addRow(new Object[]{
                i++,
                lshd.getMaNV(),
                ngayCapNhat,
                lshd.getHanhDong()
            });
        }
    }

    private void searchHDTheoTen() {
        listHD = srHD.searchHD(txtSearch.getText());
        showDataTable1(listHD);
        dtmHDCT.setRowCount(0);
        dtmLSHD.setRowCount(0);
    }

    private void searchHD() {
        try {
            String minText = txtMin.getText().trim();
            String maxText = txtMax.getText().trim();

            // Kiểm tra xem minText và maxText có phải là số hợp lệ hay không
            if (!minText.isEmpty() && !maxText.isEmpty()) {
                double min = Double.parseDouble(minText);
                double max = Double.parseDouble(maxText);

                // Đảm bảo rằng phương thức searchGiaTienHD đã triển khai đúng
                listHD = srHD.searchGiaTienHD(min, max);
                showDataTable1(listHD);
            }
        } catch (NumberFormatException e) {
            // Xử lý ngoại lệ, ví dụ: hiển thị thông báo lỗi cho người dùng
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá trị số hợp lệ.");
        }
    }

    public void printHD() {
        try {
            int rowIndex = tblHD.getSelectedRow();
            if (rowIndex >= 0) {
                HoaDonViewModel hd = listHD.get(rowIndex);
                dtmHDCT = (DefaultTableModel) tblHDCT.getModel();
                listHDCT = srHDCT.getAll(hd.getId());
                showDataTable2(listHDCT);
                List<FieldReportPayment> fields = new ArrayList<>();

                // Lặp qua các hàng trong bảng jTable2 để lấy thông tin chi tiết
                for (int i = 0; i < tblHDCT.getRowCount(); i++) {
                    // Lấy dữ liệu từ mỗi hàng
                    HDCTViewModel hdct = listHDCT.get(i);
                    String tenSP = hdct.getTenSP();
                    int soLuong = hdct.getSoLuong();
                    double giaBan = hdct.getGiaBan();
                    double tongTien = soLuong * giaBan;
                    fields.add(new FieldReportPayment(i + 1, tenSP, currencyFormat.format(giaBan), String.valueOf(soLuong), currencyFormat.format(tongTien)));
                }
                // Kiểm tra nếu có dữ liệu để tạo báo cáo
                if (!fields.isEmpty()) {
                    // Tạo mã QR Code
                    InputStream qrCodeStream = generateQrcode(String.valueOf(selectedInvoiceId));
                    if (qrCodeStream != null) {
                        // Tạo tham số để in báo cáo
                        ParameterReportPayment dataPrint = new ParameterReportPayment(
                                hd.getMaHD(), String.valueOf(hd.getNgayTao().format(formatter)), hd.getTenKH(), hd.getSoDT(), hd.getDiaChi(), currencyFormat.format(hd.getGiaTien()), qrCodeStream, fields);

                        // Trước khi gọi printReportPayment
                        ReportManager.getInstance().checkJRXMLPath();
                        ReportManager.getInstance().checkCompilation();
                        ReportManager.getInstance().checkReportParameters(dataPrint);

                        // Gọi phương thức in báo cáo
                        ReportManager.getInstance().printReportPayment(dataPrint);
                        System.out.println(currencyFormat.format(hd.getGiaTien()));
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không có dữ liệu để tạo báo cáo.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn để in ra.");
                selectedInvoiceId = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputStream generateQrcode(String invoiceNumber) {
        try {
            // Kiểm tra xem invoiceNumber có giá trị không
            if (invoiceNumber == null || invoiceNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn không hợp lệ");
                return null;
            }

            // Sử dụng mã hóa đơn để tạo mã QR
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 0);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(invoiceNumber, BarcodeFormat.QR_CODE, 100, 100, hints);

            // Chuyển đổi bitMatrix thành hình ảnh
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Chuyển đổi hình ảnh thành mảng byte
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);

            // Hiển thị kết quả
            System.out.println(invoiceNumber);

            // Trả về InputStream từ mảng byte
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (WriterException | IOException e) {
            // Xử lý ngoại lệ, ví dụ: hiển thị thông báo lỗi cho người dùng hoặc log lỗi
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo mã QR Code: " + e.getMessage());
            return null;
        }
    }

    private void searchHDCT(int idHD) {
        String selectedMau = (String) cbbMau.getSelectedItem();
        String selectedSize = (String) cbbSize.getSelectedItem();
        String selectedChatLieu = (String) cbbChatLieu.getSelectedItem();
        String selectedGia = (String) cbbGiaTien.getSelectedItem();
        String selectedThuongHieu = (String) cbbThuongHieu.getSelectedItem();
        
        System.out.println(selectedChatLieu + selectedMau + selectedSize + selectedThuongHieu );
        System.out.println(idHD);
        if ("Giá từ thấp đến cao".equals((String) cbbGiaTien.getSelectedItem())) {
            selectedGia = "GiaBan ASC";
        } else if ("Giá từ cao đến thấp".equals((String) cbbGiaTien.getSelectedItem())) {
            selectedGia = "GiaBan DESC";
        } else {
            selectedGia = "Created_at DESC";
        }
        listHDCT = srHDCT.searchCBBSP(idHD, selectedMau, selectedSize, selectedChatLieu, selectedThuongHieu, selectedGia);
        // Hiển thị dữ liệu đã lọc
        showDataTable2(listHDCT);
    }

    public void showCbbHDCT() {
        showcbbChatLieu(chatLieus);
        showcbbMauSac(mauSacs);
        showcbbSize(sizes);
        showcbbThuongHieu(thuongHieus);
        showcbbGia();
    }

    private void resetColumn10() {
        tblHD.getColumnModel().getColumn(10).setCellRenderer(null);
        tblHD.getColumnModel().getColumn(10).setCellEditor(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        btnQR = new javax.swing.JButton();
        txtSearch = new textfield.TextField();
        txtMin = new textfield.TextField();
        txtMax = new textfield.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        roundPanel9 = new swing.RoundPanel();
        jLabel9 = new javax.swing.JLabel();
        btnTT2 = new javax.swing.JButton();
        btnTT1 = new javax.swing.JButton();
        btnTT5 = new javax.swing.JButton();
        btnTT4 = new javax.swing.JButton();
        btnTT3 = new javax.swing.JButton();
        btnTT6 = new javax.swing.JButton();
        roundPanel2 = new swing.RoundPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cbbMau = new combobox.Combobox();
        cbbSize = new combobox.Combobox();
        cbbChatLieu = new combobox.Combobox();
        cbbThuongHieu = new combobox.Combobox();
        cbbGiaTien = new combobox.Combobox();
        txtSearch1 = new textfield.TextField();
        roundPanel3 = new swing.RoundPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLSHD = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel1.setText("Hóa đơn");

        btnQR.setText("Quét QR");
        btnQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQRActionPerformed(evt);
            }
        });

        txtSearch.setLabelText("Tìm kiếm hóa đơn");

        txtMin.setLabelText("Giá từ");

        txtMax.setLabelText("Đến");

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HD", "Ngày thanh toán", "Mã NV", "Tên KH", "Địa chỉ", "SĐT", "Tổng tiền", "Hình thức thanh toán", "Trạng thái", "Hành động"
            }
        ));
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHD);
        if (tblHD.getColumnModel().getColumnCount() > 0) {
            tblHD.getColumnModel().getColumn(0).setMaxWidth(50);
            tblHD.getColumnModel().getColumn(1).setMaxWidth(60);
            tblHD.getColumnModel().getColumn(3).setMaxWidth(60);
            tblHD.getColumnModel().getColumn(9).setMaxWidth(100);
        }

        btnPrint.setText("In hóa đơn");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnExport.setText("Xuất danh sách");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnAdd.setText("Tạo HD mới");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel9.setText("Trạng thái hóa đơn");

        btnTT2.setText("Đã Thanh Toán");
        btnTT2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTT2ActionPerformed(evt);
            }
        });

        btnTT1.setText("Tất Cả");
        btnTT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTT1ActionPerformed(evt);
            }
        });

        btnTT5.setText("Đang Giao");
        btnTT5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTT5ActionPerformed(evt);
            }
        });

        btnTT4.setText("Chờ Giao");
        btnTT4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTT4ActionPerformed(evt);
            }
        });

        btnTT3.setText("Chưa Thanh Toán");
        btnTT3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTT3ActionPerformed(evt);
            }
        });

        btnTT6.setText("Hủy Giao");
        btnTT6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTT6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel9Layout = new javax.swing.GroupLayout(roundPanel9);
        roundPanel9.setLayout(roundPanel9Layout);
        roundPanel9Layout.setHorizontalGroup(
            roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel9Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(roundPanel9Layout.createSequentialGroup()
                        .addComponent(btnTT1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnTT2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnTT3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnTT4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnTT5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnTT6, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        roundPanel9Layout.setVerticalGroup(
            roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel9Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel9)
                .addGap(12, 12, 12)
                .addGroup(roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTT1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTT2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTT3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTT4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTT5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTT6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(5, 5, 5)
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(roundPanel1Layout.createSequentialGroup()
                                        .addComponent(btnQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtMax, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPrint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExport)
                        .addGap(16, 16, 16))
                    .addComponent(roundPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnQR, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrint)
                    .addComponent(btnExport))
                .addContainerGap())
        );

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SPCT", "Tên SP", "Màu sắc", "Size", "Chất liệu", "Thương hiệu", "Số lượng", "Đơn giá", "Tổng tiền"
            }
        ));
        jScrollPane2.setViewportView(tblHDCT);
        if (tblHDCT.getColumnModel().getColumnCount() > 0) {
            tblHDCT.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel2.setText("Hóa đơn chi tiết");

        cbbMau.setLabeText("Màu sắc");
        cbbMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMauActionPerformed(evt);
            }
        });

        cbbSize.setLabeText("Size");
        cbbSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSizeActionPerformed(evt);
            }
        });

        cbbChatLieu.setLabeText("Chất liệu");
        cbbChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbChatLieuActionPerformed(evt);
            }
        });

        cbbThuongHieu.setLabeText("Thương hiệu");
        cbbThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbThuongHieuActionPerformed(evt);
            }
        });

        cbbGiaTien.setLabeText("Giá tiền");
        cbbGiaTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGiaTienActionPerformed(evt);
            }
        });

        txtSearch1.setLabelText("Tìm kiếm hóa đơn");

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(txtSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbMau, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbMau, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        tblLSHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Người tác động", "Ngày cập nhật", "Hành động"
            }
        ));
        jScrollPane3.setViewportView(tblLSHD);
        if (tblLSHD.getColumnModel().getColumnCount() > 0) {
            tblLSHD.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel3.setText("Lịch sử hóa đơn");

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        Application.showForm(new BanHang());
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xuất Excel?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            srQLHD.exportToExcel();
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn in hóa đơn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (selectedInvoiceId > -1) {
                if (srLSHD.isInvoiceIdValid(selectedInvoiceId)) { // Kiểm tra tính hợp lệ của ID hóa đơn
                    printHD();
                    String hanhDong = "In hóa đơn";
                    srLSHD.addLSHD2(Integer.valueOf(selectedInvoiceId), hanhDong);
                    listLSHD = srLSHD.getAll(Integer.valueOf(selectedInvoiceId));
                    showDataTable3(listLSHD);
                } else {
                    // Hiển thị thông báo lỗi cho người dùng nếu ID hóa đơn không hợp lệ
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "ID hóa đơn không hợp lệ. Vui lòng chọn một hóa đơn khác.");
                }
            }
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQRActionPerformed
        quetQR quet = new quetQR();
        quet.setQRCodeListener(new quetQR.codeQR() {
            @Override
            public void onQRCodeScanned(int result) {
                System.out.println(result);
                listHD = srHD.getOne(result);
                showDataTable1(listHD);
                showCbbHDCT();

                // Cập nhật dữ liệu cho HDCT
                dtmHDCT = (DefaultTableModel) tblHDCT.getModel();
                listHDCT = srHDCT.getAll(result);
                showDataTable2(listHDCT);

                // Cập nhật dữ liệu cho LSHD
                dtmLSHD = (DefaultTableModel) tblLSHD.getModel();
                listLSHD = srLSHD.getAll(result);
                showDataTable3(listLSHD);
                quet.closeQRFrame();
            }
        });
        quet.openWebcam();
    }//GEN-LAST:event_btnQRActionPerformed

    private void cbbMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbMauActionPerformed

    private void cbbSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbSizeActionPerformed

    private void cbbChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbChatLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbChatLieuActionPerformed

    private void cbbThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbThuongHieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbThuongHieuActionPerformed

    private void cbbGiaTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGiaTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbGiaTienActionPerformed

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        int rowIndex = tblHD.getSelectedRow();
        if (rowIndex >= 0 && rowIndex < listHD.size()) {
            selectedInvoiceId = listHD.get(rowIndex).getId();
            System.out.println(selectedInvoiceId);
            showCbbHDCT();

            dtmHDCT = (DefaultTableModel) tblHDCT.getModel();
            listHDCT = srHDCT.getAll(selectedInvoiceId);
            showDataTable2(listHDCT);

            dtmLSHD = (DefaultTableModel) tblLSHD.getModel();
            listLSHD = srLSHD.getAll(selectedInvoiceId);
            showDataTable3(listLSHD);
        }
    }//GEN-LAST:event_tblHDMouseClicked

    private void btnTT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTT1ActionPerformed
        listHD = srHD.getAll();
        showDataTable1(listHD);

        dtmHDCT.setRowCount(0);
        dtmLSHD.setRowCount(0);
        resetColumn10();
    }//GEN-LAST:event_btnTT1ActionPerformed

    private void btnTT2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTT2ActionPerformed
        listHD = srHD.searchTT("Đã Thanh Toán");
        if (listHD.size() >= 1) {
            showDataTable1(listHD);

            dtmHDCT.setRowCount(0);
            dtmLSHD.setRowCount(0);
            resetColumn10();

        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không có hóa đơn nào đang ở trạng thái 'Đã Thanh Toán'!");
        }
    }//GEN-LAST:event_btnTT2ActionPerformed

    private void btnTT3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTT3ActionPerformed
        listHD = srHD.searchTT("Chưa Thanh Toán");
        if (listHD.size() >= 1) {
            showDataTable1(listHD);

            dtmHDCT.setRowCount(0);
            dtmLSHD.setRowCount(0);
            resetColumn10();
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không có hóa đơn nào đang ở trạng thái 'Chưa Thanh Toán'!");
        }

    }//GEN-LAST:event_btnTT3ActionPerformed

    private void btnTT4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTT4ActionPerformed
        listHD = srHD.searchTT("Chờ Giao");
        if (listHD.size() >= 1) {
            showDataTable1(listHD);

            dtmHDCT.setRowCount(0);
            dtmLSHD.setRowCount(0);
            resetColumn10();
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không có hóa đơn nào đang ở trạng thái 'Chờ Giao'!");
        }
    }//GEN-LAST:event_btnTT4ActionPerformed

    private void btnTT5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTT5ActionPerformed
        listHD = srHD.searchTT("Đang Giao");
        if (listHD.size() >= 1) {
            showDataTable1(listHD);

            dtmHDCT.setRowCount(0);
            dtmLSHD.setRowCount(0);

            TableActionEvent event = new TableActionEvent() {
                @Override
                public void onHG(int row) {
                    if (row >= 0) {
                        HoaDonViewModel hd = listHD.get(row);
                        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy giao đơn hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            int idHD = hd.getId();
                            if (hd.getTrangThai().equals("Đang Giao")) {
                                String trangThaiMoi = "Chờ Giao 2";
                                srHD.huyGiao(trangThaiMoi, idHD);
                            } else if (hd.getTrangThai().equals("Đang Giao 2")) {
                                String trangThaiMoi = "Chờ Giao 3";
                                srHD.huyGiao(trangThaiMoi, idHD);
                            } else if (hd.getTrangThai().equals("Đang Giao 3")) {
                                String trangThaiMoi = "Hủy Giao";
                                srHD.huyGiao(trangThaiMoi, idHD);
                            }
                            listHD = srHD.getAll();
                            showDataTable1(listHD);
                            listHDCT = srHDCT.getAll(idHD);
                            showDataTable2(listHDCT);
                            listLSHD = srLSHD.getAll(idHD);
                            showDataTable3(listLSHD);
                            resetColumn10();
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy giao hàng thành công!");
                        } else {
                            listHD = srHD.getAll();
                            showDataTable1(listHD);
                            dtmHDCT.setRowCount(0);
                            dtmLSHD.setRowCount(0);
                            resetColumn10();
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Bạn đã ấn NO!");
                        }
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Vui lòng chọn một hóa đơn hủy giao.");
                    }

                }

                @Override
                public void onTT(int row) {
                    if (row >= 0) {
                        HoaDonViewModel hd = listHD.get(row);
                        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn đánh dấu hóa đơn đã giao?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            int idHD = hd.getId();
                            srHD.updateHDDG(idHD);
                            listHD = srHD.getAll();
                            showDataTable1(listHD);
                            listHDCT = srHDCT.getAll(idHD);
                            showDataTable2(listHDCT);
                            listLSHD = srLSHD.getAll(idHD);
                            showDataTable3(listLSHD);
                            resetColumn10();
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã giao xong đơn hàng này!");
                        } else {
                            listHD = srHD.getAll();
                            showDataTable1(listHD);
                            dtmHDCT.setRowCount(0);
                            dtmLSHD.setRowCount(0);
                            resetColumn10();
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Bạn đã ấn NO!");
                        }
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Vui lòng chọn một hóa đơn để đánh dấu đã giao.");
                    }
                }

                @Override
                public void onTH(int row) {
                    if (row >= 0) {
                        HoaDonViewModel hd = listHD.get(row);
                        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn trả hàng và hủy hóa đơn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            int idHD = hd.getId();
                            listHDCT = srHDCT.getAll(idHD);
                            for (HDCTViewModel hdct : listHDCT) {
                                // Lấy thông tin sản phẩm chi tiết
                                int idSanPham = hdct.getMaSPCT();
                                int soLuong = hdct.getSoLuong();
                                srBH.increaseSoLuong(soLuong, idSanPham);
                                System.out.println("ID Sản phẩm: " + idSanPham + ", Số lượng: " + soLuong);
                            }
                            srHD.traHang(idHD);

                            listHD = srHD.getAll();
                            showDataTable1(listHD);

                            showDataTable2(listHDCT);
                            listLSHD = srLSHD.getAll(idHD);
                            showDataTable3(listLSHD);
                            resetColumn10();
                        } else {
                            listHD = srHD.getAll();
                            showDataTable1(listHD);
                            dtmHDCT.setRowCount(0);
                            dtmLSHD.setRowCount(0);
                            resetColumn10();
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Bạn đã ấn NO!");
                        }
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Vui lòng chọn một hóa đơn để đánh dấu trả hàng.");
                    }
                }
            };
            tblHD.getColumnModel().getColumn(10).setCellRenderer(new cellHD.TableActionCellRender());
            tblHD.getColumnModel().getColumn(10).setCellEditor(new cellHD.TableActionCellEditor(event));
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không có hóa đơn nào đang ở trạng thái 'Đang Giao'!");
        }
    }//GEN-LAST:event_btnTT5ActionPerformed

    private void btnTT6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTT6ActionPerformed
        listHD = srHD.searchTT("Hủy Giao");
        if (listHD.size() >= 1) {
            showDataTable1(listHD);

            dtmHDCT.setRowCount(0);
            dtmLSHD.setRowCount(0);

            cellTraHang.TableActionEvent event = new cellTraHang.TableActionEvent() {
                @Override
                public void onTH(int row) {
                    if (row >= 0) {
                        HoaDonViewModel hd = listHD.get(row);
                        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn trả hàng và hủy hóa đơn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            int idHD = hd.getId();
                            listHDCT = srHDCT.getAll(idHD);
                            for (HDCTViewModel hdct : listHDCT) {
                                // Lấy thông tin sản phẩm chi tiết
                                int idSanPham = hdct.getMaSPCT();
                                int soLuong = hdct.getSoLuong();
                                srBH.increaseSoLuong(soLuong, idSanPham);
                                System.out.println("ID Sản phẩm: " + idSanPham + ", Số lượng: " + soLuong);
                            }
                            srHD.traHang(idHD);

                            listHD = srHD.getAll();
                            showDataTable1(listHD);

                            showDataTable2(listHDCT);
                            listLSHD = srLSHD.getAll(idHD);
                            showDataTable3(listLSHD);
                            resetColumn10();
                        } else {
                            listHD = srHD.getAll();
                            showDataTable1(listHD);
                            dtmHDCT.setRowCount(0);
                            dtmLSHD.setRowCount(0);
                            resetColumn10();
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Bạn đã ấn NO!");
                        }
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Vui lòng chọn một hóa đơn để đánh dấu trả hàng.");
                    }
                }
            };
            tblHD.getColumnModel().getColumn(10).setCellRenderer(new cellTraHang.TableActionCellRender());
            tblHD.getColumnModel().getColumn(10).setCellEditor(new cellTraHang.TableActionCellEditor(event));
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không có hóa đơn nào đang ở trạng thái 'Hủy Giao'!");
        }
    }//GEN-LAST:event_btnTT6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btnTT1;
    private javax.swing.JButton btnTT2;
    private javax.swing.JButton btnTT3;
    private javax.swing.JButton btnTT4;
    private javax.swing.JButton btnTT5;
    private javax.swing.JButton btnTT6;
    private combobox.Combobox cbbChatLieu;
    private combobox.Combobox cbbGiaTien;
    private combobox.Combobox cbbMau;
    private combobox.Combobox cbbSize;
    private combobox.Combobox cbbThuongHieu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel2;
    private swing.RoundPanel roundPanel3;
    private swing.RoundPanel roundPanel9;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblLSHD;
    private textfield.TextField txtMax;
    private textfield.TextField txtMin;
    private textfield.TextField txtSearch;
    private textfield.TextField txtSearch1;
    // End of variables declaration//GEN-END:variables
}
