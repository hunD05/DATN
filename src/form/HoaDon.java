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
import backend.service.ChatLieuService;
import backend.service.HDCTService;
import backend.service.HoaDonService;
import backend.service.LSHDService;
import backend.service.MauSacService;
import backend.service.QLHDService;
import backend.service.SizeService;
import backend.service.ThuonHieuService;
import backend.viewmodel.BHHDViewModel;
import backend.viewmodel.BHSPViewModel;
import backend.viewmodel.HDCTViewModel;
import backend.viewmodel.HoaDonViewModel;
import backend.viewmodel.LSHDViewModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private String selectedInvoiceId;

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

    private DefaultComboBoxModel dcbmGia = new DefaultComboBoxModel();

    /**
     * Creates new form HoaDon
     */
    public HoaDon() {
        initComponents();
        dtmHD = (DefaultTableModel) tblHD.getModel();
        listHD = srHD.getAll();
        showDataTable1(listHD);

        dcbm = (DefaultComboBoxModel) cbbTrangThai.getModel();
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
//combobox
        cbbTrangThai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTT();
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

    private void searchTT() {
        String trangThai = cbbTrangThai.getSelectedItem().toString();
        if (trangThai.equalsIgnoreCase("Tất cả")) {
            listHD = srHD.getAll();
            showDataTable1(listHD);
        } else {
            listHD = srHD.searchTT(trangThai);
            dtmHDCT.setRowCount(0);
            dtmLSHD.setRowCount(0);
            showDataTable1(listHD);
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
                    InputStream qrCodeStream = generateQrcode(selectedInvoiceId);
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
        cbbTrangThai = new combobox.Combobox();
        txtSearch = new textfield.TextField();
        txtMin = new textfield.TextField();
        txtMax = new textfield.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDaGiao = new javax.swing.JButton();
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

        cbbTrangThai.setLabeText("Trạng thái hóa đơn");
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        txtSearch.setLabelText("Tìm kiếm hóa đơn");

        txtMin.setLabelText("Giá từ");

        txtMax.setLabelText("Đến");

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày thanh toán", "Mã nhân viên", "Tên khách hàng", "Địa chỉ", "SĐT", "Tổng tiền", "Hình thức thanh toán", "Trạng thái"
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

        btnDaGiao.setText("Đã giao xong");
        btnDaGiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaGiaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(roundPanel1Layout.createSequentialGroup()
                                        .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMin, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)))
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
                        .addComponent(btnDaGiao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPrint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExport)
                        .addGap(6, 6, 6))))
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
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrint)
                    .addComponent(btnExport)
                    .addComponent(btnDaGiao))
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addComponent(roundPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        Application.showForm(new BanHang());
    }//GEN-LAST:event_btnAddActionPerformed

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed

    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        int rowIndex = tblHD.getSelectedRow();
        HoaDonViewModel hd = listHD.get(rowIndex);
        selectedInvoiceId = String.valueOf(hd.getId());
        showCbbHDCT();

        dtmHDCT = (DefaultTableModel) tblHDCT.getModel();
        listHDCT = srHDCT.getAll(hd.getId());
        showDataTable2(listHDCT);

        dtmLSHD = (DefaultTableModel) tblLSHD.getModel();
        listLSHD = srLSHD.getAll(hd.getId());
        showDataTable3(listLSHD);
    }//GEN-LAST:event_tblHDMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xuất Excel?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            srQLHD.exportToExcel();
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn in hóa đơn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            printHD();
            String hanhDong = "In hóa đơn";
            srLSHD.addLSHD(hanhDong);
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

    private void btnDaGiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaGiaoActionPerformed
        int index = tblHD.getSelectedRow();
        if (index >= 0) {
            HoaDonViewModel hd = listHD.get(index);
            if (hd.getTrangThai().equals("Đang Giao")) {
                int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đánh dấu hóa đơn đã giao?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    int idHD = hd.getId();
                    srHD.updateHDDG(idHD);
                    listHD = srHD.getAll();
                    showDataTable1(listHD);
                    listHDCT = srHDCT.getAll(idHD);
                    showDataTable2(listHDCT);
                    listLSHD = srLSHD.getAll(idHD);
                    showDataTable3(listLSHD);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã giao xong đơn hàng này!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hóa đơn không ở trạng thái 'Đang Giao'.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để đánh dấu đang giao.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDaGiaoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDaGiao;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnQR;
    private combobox.Combobox cbbChatLieu;
    private combobox.Combobox cbbGiaTien;
    private combobox.Combobox cbbMau;
    private combobox.Combobox cbbSize;
    private combobox.Combobox cbbThuongHieu;
    private combobox.Combobox cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel2;
    private swing.RoundPanel roundPanel3;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblLSHD;
    private textfield.TextField txtMax;
    private textfield.TextField txtMin;
    private textfield.TextField txtSearch;
    private textfield.TextField txtSearch1;
    // End of variables declaration//GEN-END:variables
}
