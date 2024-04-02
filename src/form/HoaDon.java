/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.service.HDCTService;
import backend.service.HoaDonService;
import backend.service.LSHDService;
import backend.service.QLHDService;
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
import javax.swing.table.TableColumn;
import qrcode.qrcode;
import qrcode.qrcode.QRCodeListener;

/**
 *
 * @author leanb
 */
public class HoaDon extends javax.swing.JPanel implements QRCodeListener {

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

    /**
     * Creates new form HoaDon
     */
    public HoaDon() {
        initComponents();
        dtmHD = (DefaultTableModel) jTable1.getModel();
        listHD = srHD.getAll();
        showDataTable1(listHD);
        // Lấy đối tượng TableColumn của cột "STT" từ JTable
        TableColumn columnSTT = jTable1.getColumnModel().getColumn(0); // 0 là chỉ số cột, có thể là 1 nếu cột "STT" là cột thứ hai

// Đặt kích cỡ chiều rộng mong muốn
        columnSTT.setPreferredWidth(1); // là chiều rộng mong muốn

// Cập nhật lại JTable để áp dụng thay đổi
        jTable1.repaint();

        dcbm = (DefaultComboBoxModel) cbbTrangThai.getModel();
        dcbm.addElement("Tất cả");
        dcbm.addElement("Chưa Thanh Toán");
        dcbm.addElement("Đã Thanh Toán");

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

    }

    public void showDataTable1(List<HoaDonViewModel> listHD) {
        dtmHD.setRowCount(0);
        NumberFormat currencyFormat = new DecimalFormat("# VND"); // Định dạng số tiền
// Lấy đối tượng TableColumn của cột "STT" từ JTable
        TableColumn columnSTT = jTable1.getColumnModel().getColumn(0);

        // Đặt kích cỡ chiều rộng mong muốn và giới hạn chiều rộng
        columnSTT.setMinWidth(50);
        columnSTT.setMaxWidth(70);

        // Cập nhật lại JTable để áp dụng thay đổi
        jTable1.repaint();

        int i = 1;
        for (HoaDonViewModel hd : listHD) {
            // Loại bỏ chữ 'T' từ chuỗi ngày thời gian
            String ngayThanhToan = hd.getNgayThanhToan().format(formatter);
            dtmHD.addRow(new Object[]{
                i++, hd.getMaHD(),
                ngayThanhToan,
                currencyFormat.format(hd.getGiaTien()),
                hd.getMaNV(), hd.getTenKH(), hd.getDiaChi(),
                hd.getSoDT(), hd.getTrangThai()
            });
        }

    }

    public void showDataTable2(List<HDCTViewModel> listHDCT) {
        dtmHDCT.setRowCount(0);
        NumberFormat currencyFormat = new DecimalFormat("# VND");
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

    private void printHD() {
        try {
            int rowIndex = jTable1.getSelectedRow();
            if (rowIndex >= 0) {
                HoaDonViewModel hd = listHD.get(rowIndex);
                dtmHDCT = (DefaultTableModel) jTable2.getModel();
                listHDCT = srHDCT.getAll(hd.getId());
                showDataTable2(listHDCT);
                List<FieldReportPayment> fields = new ArrayList<>();

                // Lặp qua các hàng trong bảng jTable2 để lấy thông tin chi tiết
                for (int i = 0; i < jTable2.getRowCount(); i++) {
                    // Lấy dữ liệu từ mỗi hàng
                    String tenSP = jTable2.getValueAt(i, 2).toString();

                    // Kiểm tra và chuyển đổi số lượng
                    int soLuong = 0;
                    try {
                        soLuong = Integer.parseInt(jTable2.getValueAt(i, 7).toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        // Xử lý nếu giá trị không hợp lệ
                    }

                    // Kiểm tra và chuyển đổi giá bán
                    double giaBan = 0.0;
                    String giaBanStr = jTable2.getValueAt(i, 8).toString().replaceAll("[^\\d.]", "");
                    if (!giaBanStr.isEmpty()) {
                        try {
                            giaBan = Double.parseDouble(giaBanStr);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            // Xử lý nếu giá trị không hợp lệ
                        }
                    }

                    double tongTien = soLuong * giaBan;

                    // In dữ liệu ra console
                    System.out.println("Tên sản phẩm: " + tenSP);
                    System.out.println("Số lượng: " + soLuong);
                    System.out.println("Đơn giá: " + giaBan);
                    System.out.println("Tổng tiền: " + tongTien);

                    // Thêm thông tin vào danh sách fields để tạo báo cáo
                    fields.add(new FieldReportPayment(i + 1, tenSP, String.valueOf(giaBan), String.valueOf(soLuong), String.valueOf(tongTien)));
                }
                // Kiểm tra nếu có dữ liệu để tạo báo cáo
                if (!fields.isEmpty()) {
                    // Tạo mã QR Code
                    InputStream qrCodeStream = generateQrcode(selectedInvoiceId);
                    if (qrCodeStream != null) {
                        // Tạo tham số để in báo cáo
                        ParameterReportPayment dataPrint = new ParameterReportPayment(
                                hd.getMaHD(), String.valueOf(hd.getNgayTao().format(formatter)), hd.getTenKH(), hd.getSoDT(), hd.getDiaChi(), String.valueOf(hd.getGiaTien()).replaceAll("[^\\d.]", ""), qrCodeStream, fields);

                        // Trước khi gọi printReportPayment
                        ReportManager.getInstance().checkJRXMLPath();
                        ReportManager.getInstance().checkCompilation();
                        ReportManager.getInstance().checkReportParameters(dataPrint);

                        // Gọi phương thức in báo cáo
                        ReportManager.getInstance().printReportPayment(dataPrint);
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

    @Override
    public void onQRCodeScanned(int result) {
        // Xử lý giá trị QR Code tại đây, bạn có thể làm gì đó với giá trị này
        System.out.println("Nhận được giá trị QR Code trong HoaDon: " + result);

        // Gọi phương thức để cập nhật dữ liệu hóa đơn tương ứng với giá trị QRCode
        listHD = srHD.getOne(result);
        showDataTable1(listHD);

        // Cập nhật dữ liệu cho HDCT
        dtmHDCT = (DefaultTableModel) jTable2.getModel();
        listHDCT = srHDCT.getAll(result);
        showDataTable2(listHDCT);

        // Cập nhật dữ liệu cho LSHD
        dtmLSHD = (DefaultTableModel) jTable3.getModel();
        listLSHD = srLSHD.getAll(result);
        showDataTable3(listLSHD);

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
        jTable1 = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        roundPanel2 = new swing.RoundPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        roundPanel3 = new swing.RoundPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày thanh toán", "Tổng tiền", "Mã nhân viên", "Tên khách hàng", "Địa chỉ", "SĐT", "Trạng thái"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

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

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPrint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExport))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMin, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)))
                        .addGap(5, 5, 5)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(btnQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMax, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExport)
                    .addComponent(btnPrint))
                .addContainerGap())
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel2.setText("Hóa đơn chi tiết");

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel3.setText("Lịch sử hóa đơn");

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                        .addComponent(roundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        Application.showForm(new BanHang());
    }//GEN-LAST:event_btnAddActionPerformed

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed

    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int rowIndex = jTable1.getSelectedRow();
        HoaDonViewModel hd = listHD.get(rowIndex);
        selectedInvoiceId = String.valueOf(hd.getId());

        dtmHDCT = (DefaultTableModel) jTable2.getModel();
        listHDCT = srHDCT.getAll(hd.getId());
        showDataTable2(listHDCT);

        dtmLSHD = (DefaultTableModel) jTable3.getModel();
        listLSHD = srLSHD.getAll(hd.getId());
        showDataTable3(listLSHD);
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        srQLHD.exportToExcel();
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        printHD();
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQRActionPerformed
        qrcode qrScannerFrame = new qrcode();
        qrScannerFrame.setQRCodeListener(this);
        qrScannerFrame.setVisible(true);

    }//GEN-LAST:event_btnQRActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnQR;
    private combobox.Combobox cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel2;
    private swing.RoundPanel roundPanel3;
    private textfield.TextField txtMax;
    private textfield.TextField txtMin;
    private textfield.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
