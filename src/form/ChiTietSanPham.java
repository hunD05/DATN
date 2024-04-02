/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.QRCode.QRCodeResultWindow;
import backend.QRCode.qrcode;
import backend.entity.DanhMuc;
import backend.entity.NSX;
import backend.entity.XuatXu;
import backend.service.ChiTietSanPhamService;
import backend.service.DanhMucService;
import backend.service.GiaBanService;
import backend.service.NSXService;
import backend.service.XuatXuService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import raven.toast.Notifications;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import raven.application.Application;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;

/**
 *
 * @author leanb
 */
public class ChiTietSanPham extends javax.swing.JPanel implements qrcode.QRCodeListener {

    /**
     * Creates new form ChiTietSanPham
     */
    DefaultTableModel dtm = new DefaultTableModel();
    List<backend.entity.ChiTietSanPham> chitietsanpham = new ArrayList<>();
    ChiTietSanPhamService chiTietSanPhamService = new ChiTietSanPhamService();
    private int selectedProductIndex = -1;

    DefaultComboBoxModel dcbmDanhMuc = new DefaultComboBoxModel();
    List<DanhMuc> danhMucs = new ArrayList<>();
    DanhMucService danhMucService = new DanhMucService();

    DefaultComboBoxModel dcbmNSX = new DefaultComboBoxModel();
    List<NSX> nsx = new ArrayList<>();
    NSXService nsxs = new NSXService();

    DefaultComboBoxModel dcbmXuatXu = new DefaultComboBoxModel();
    List<XuatXu> xuatXus = new ArrayList<>();
    XuatXuService xuatXuService = new XuatXuService();

    DefaultComboBoxModel dcbmGia = new DefaultComboBoxModel();
    List<backend.entity.ChiTietSanPham> giaChiTietSanPhams = new ArrayList<>();
    GiaBanService giaBanService = new GiaBanService();

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

        giaChiTietSanPhams = giaBanService.getAll();
        dcbmGia = (DefaultComboBoxModel) cbbGia.getModel();
        showcbbGia(chitietsanpham);
        
        tblChiTietSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblChiTietSanPham.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    
                            backend.entity.ChiTietSanPham ctspViewModel = chiTietSanPhamService.getAll().get(index);

                            String idChiTietSP = ctspViewModel.getId()+"";
                            String sanPham = ctspViewModel.getIdSanPham()+"";
                            String danhMuc = ctspViewModel.getIdDanhMuc()+"";
                            String NSX = ctspViewModel.getIdNsx()+"";
                            String xuatxu = ctspViewModel.getIdXuatXu()+"";
                            String mauSac = ctspViewModel.getIdMauSac()+"";
                            String size = ctspViewModel.getIdSize()+"";
                            String thuongHieu = ctspViewModel.getIdThuongHieu()+"";
                            String chatLieu = ctspViewModel.getIdChatLieu()+"";
                            String coAo = ctspViewModel.getIdCoAo()+"";
                            String duoiAo = ctspViewModel.getIdDuoiAo()+"";
                            String tayAo = ctspViewModel.getIdTayAo()+"";
                            String dangAoz = ctspViewModel.getIdDangAo()+"";
                            String soLuong = ctspViewModel.getSoLuong()+"";
                            String gia = ctspViewModel.getGiaBan()+"";
                            String moTa = ctspViewModel.getMoTa();
                            String trangThai = ctspViewModel.getTrangThai();
                            System.out.println(danhMuc);
                            
                            

                            // Open your ThongTinChiTietSP dialog or perform any other action here
                            SuaSanPhamChiTiet chiTietSP = new SuaSanPhamChiTiet(idChiTietSP, sanPham, danhMuc,NSX, xuatxu, mauSac,size,thuongHieu,chatLieu,coAo,duoiAo,tayAo,dangAoz,soLuong,gia,moTa,trangThai);
                            Application.showForm(chiTietSP);
                        }}});
        
        
    
    }

    public void showcbbDanhMuc(List<DanhMuc> danhMucs) {
        dcbmDanhMuc.removeAllElements();
        dcbmDanhMuc.addElement(null);
        for (DanhMuc danhMuc : danhMucs) {
            dcbmDanhMuc.addElement((int) danhMuc.getId());
        }
        cbbDanhMuc.setSelectedItem(null);
    }

    public void showcbbNSX(List<NSX> nsxs) {
        dcbmNSX.removeAllElements();
        // Thêm một mục null đầu tiên
        dcbmNSX.addElement(null);
        for (NSX nsx1 : nsxs) {
            dcbmNSX.addElement(nsx1.getId());
        }
        cbbDanhMuc.setSelectedItem(null);
    }

    public void showcbbXuatXu(List<XuatXu> xuatXus) {
        dcbmXuatXu.removeAllElements();
        // Thêm một mục null đầu tiên
        dcbmXuatXu.addElement(null);
        for (XuatXu xuatXu : xuatXus) {
            dcbmXuatXu.addElement(xuatXu.getId());
        }
        cbbDanhMuc.setSelectedItem(null);
    }

    public void showcbbGia(List<backend.entity.ChiTietSanPham> chiTietSanPhamsz) {
        dcbmGia.removeAllElements();
        // Thêm một mục null đầu tiên
        dcbmGia.addElement(null);
        for (backend.entity.ChiTietSanPham chiTietSanPham : chiTietSanPhamsz) {
            dcbmGia.addElement(chiTietSanPham.getGiaBan());
        }
        cbbDanhMuc.setSelectedItem(null);
    }

    public void showDataTable(List<backend.entity.ChiTietSanPham> chiTietSanPhams) {
        dtm.setRowCount(0);
        for (backend.entity.ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
            dtm.addRow(new Object[]{chiTietSanPham.getStt(), chiTietSanPham.getGiaBan(), chiTietSanPham.getSoLuong(), chiTietSanPham.getMoTa(), chiTietSanPham.getTrangThai()});
        }
        tblChiTietSanPham.setRowHeight(40);
    }

    public void exportToExcel(List<backend.entity.ChiTietSanPham> chiTietSanPhams, String filePath) {

        try ( Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("ChiTietSanPham Data");

            // Tiêu đề cột
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "GiaBan", "SoLuong", "MoTa", "TrangThai"};

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Dữ liệu
            int rowNum = 1;
            for (backend.entity.ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(chiTietSanPham.getId());
                row.createCell(1).setCellValue(chiTietSanPham.getGiaBan().doubleValue());
                row.createCell(2).setCellValue(chiTietSanPham.getSoLuong());
                row.createCell(3).setCellValue(chiTietSanPham.getMoTa());
                row.createCell(4).setCellValue(chiTietSanPham.getTrangThai());
            }

            // Lưu file Excel
            try ( FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("File Excel đã được tạo thành công!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage generateQRCode(backend.entity.ChiTietSanPham product) {
        String data = "ID: " + product.getId() + "\n"
                + "Giá bán: " + product.getGiaBan().doubleValue() + "\n"
                + "Số lượng: " + product.getSoLuong() + "\n"
                + "Mô tả: " + product.getMoTa() + "\n"
                + "Trạng thái: " + product.getTrangThai();

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200);

            BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            return image;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void openEditForm(backend.entity.ChiTietSanPham chiTietSanPham) {
//        SuaSanPhamChiTiet editForm = new SuaSanPhamChiTiet();
//        editForm.setData(chiTietSanPham);
//        Application.showForm(editForm);
    }

    @Override
    public void onQRCodeScanned(long result) {
//        System.out.println("Nhận được giá trị QR Code trong HoaDon: " + result);

        // Gọi phương thức để cập nhật dữ liệu hóa đơn tương ứng với giá trị QRCode
        chitietsanpham = chiTietSanPhamService.getAllz(result);
        showDataTable(chitietsanpham);

        // Cập nhật dữ liệu cho CTSP
        chitietsanpham = chiTietSanPhamService.getAll();
        dtm = (DefaultTableModel) tblChiTietSanPham.getModel();
        showDataTable(chitietsanpham);

        QRCodeResultWindow resultWindow = new QRCodeResultWindow(chitietsanpham.toString());
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
        jPanel12 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        btnThemSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnTaoQR = new javax.swing.JButton();
        btnQuetQR = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        btnResetBoLoc = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTietSanPham = new javax.swing.JTable();
        cbbDanhMuc = new combobox.Combobox();
        cbbXuatXu = new combobox.Combobox();
        cbbNSX = new combobox.Combobox();
        cbbGia = new combobox.Combobox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        rdoTatCa = new javax.swing.JRadioButton();
        rdoConHang = new javax.swing.JRadioButton();
        rdoHetHang = new javax.swing.JRadioButton();

        jPanel12.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

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

        btnTaoQR.setText("Tạo mã QR");
        btnTaoQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoQRActionPerformed(evt);
            }
        });
        jPanel11.add(btnTaoQR);

        btnQuetQR.setText("Quét QR");
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });
        jPanel11.add(btnQuetQR);

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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Giá bán", "Số lượng", "Mô tả", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

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
        if (tblChiTietSanPham.getColumnModel().getColumnCount() > 0) {
            tblChiTietSanPham.getColumnModel().getColumn(1).setResizable(false);
            tblChiTietSanPham.getColumnModel().getColumn(1).setPreferredWidth(1);
            tblChiTietSanPham.getColumnModel().getColumn(2).setResizable(false);
            tblChiTietSanPham.getColumnModel().getColumn(3).setResizable(false);
            tblChiTietSanPham.getColumnModel().getColumn(4).setResizable(false);
        }

        cbbDanhMuc.setLabeText("Danh mục");
        cbbDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbDanhMucActionPerformed(evt);
            }
        });

        cbbXuatXu.setLabeText("Xuất xứ");
        cbbXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbXuatXuActionPerformed(evt);
            }
        });

        cbbNSX.setLabeText("Nhà sản xuất");
        cbbNSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNSXActionPerformed(evt);
            }
        });

        cbbGia.setLabeText("Giá");
        cbbGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGiaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Bộ lọc");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Trạng thái");

        buttonGroup1.add(rdoTatCa);
        rdoTatCa.setText("Tất cả");
        rdoTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoConHang);
        rdoConHang.setText("Còn hàng");
        rdoConHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoConHangActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoHetHang);
        rdoHetHang.setText("Hết hàng");
        rdoHetHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHetHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel8Layout = new javax.swing.GroupLayout(roundPanel8);
        roundPanel8.setLayout(roundPanel8Layout);
        roundPanel8Layout.setHorizontalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoTatCa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoConHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoHetHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel8Layout.createSequentialGroup()
                        .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbGia, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        roundPanel8Layout.setVerticalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel1)
                .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel8Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(rdoTatCa)
                            .addComponent(rdoConHang)
                            .addComponent(rdoHetHang)))
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatExceljButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExceljButton5ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí để lưu file Excel");

        // Mở hộp thoại chọn đường dẫn và kiểm tra xem người dùng đã chọn hay chưa
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath() + ".xlsx"; // Đảm bảo có phần mở rộng .xlsx
            List<backend.entity.ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.getAll(); // Lấy dữ liệu từ bảng
            exportToExcel(chiTietSanPhams, filePath);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file Excel thành công");

        } else {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Đã hủy xuất file Excel");
        }
    }//GEN-LAST:event_btnXuatExceljButton5ActionPerformed

    private void btnTaoQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoQRActionPerformed
        int selectedRow = tblChiTietSanPham.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một sản phẩm từ bảng.");
            return;
        }

        // Lấy ID sản phẩm từ dòng được chọn
        Long selectedProductId = (Long) tblChiTietSanPham.getValueAt(selectedRow, 0);

        // Lấy thông tin chi tiết sản phẩm từ ID
        backend.entity.ChiTietSanPham selectedProduct = chiTietSanPhamService.getById(selectedProductId);

        // Tạo mã QR
        BufferedImage qrImage = generateQRCode(selectedProduct);

        // Hiển thị hình ảnh QR code trong JOptionPane
        ImageIcon icon = new ImageIcon(qrImage);
        JOptionPane.showMessageDialog(this, icon, "Mã QR", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_btnTaoQRActionPerformed

    private void tblChiTietSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietSanPhamMouseClicked
//        if (evt.getClickCount() == 2) { // Kiểm tra xem có phải double click không
//            int row = tblChiTietSanPham.getSelectedRow();
//            if (row != -1) {
//                backend.entity.ChiTietSanPham selectedChiTietSanPham = chitietsanpham.get(row);
//                openEditForm(selectedChiTietSanPham);
//            }
//        }
    }//GEN-LAST:event_tblChiTietSanPhamMouseClicked

    private void cbbDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbDanhMucActionPerformed
        // Lấy phần tử được chọn từ ComboBox
        Integer selectedDanhMucID = (Integer) cbbDanhMuc.getSelectedItem();

        // Kiểm tra xem phần tử được chọn có phải là null không
        if (selectedDanhMucID != null) {
            // Gọi phương thức DanhMuc từ ChiTietSanPhamService để lấy danh sách sản phẩm tương ứng với danh mục đã chọn
            List<backend.entity.ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.DanhMuc(selectedDanhMucID);

            // Xóa dữ liệu hiện tại trong bảng
            dtm.setRowCount(0);

            // Hiển thị danh sách sản phẩm mới trong bảng
            showDataTable(chiTietSanPhams);
        } else {
            showDataTable(chitietsanpham);
        }
    }//GEN-LAST:event_cbbDanhMucActionPerformed

    private void btnResetBoLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetBoLocActionPerformed
        cbbDanhMuc.setSelectedIndex(0);
        cbbGia.setSelectedIndex(0);
        cbbNSX.setSelectedIndex(0);
        cbbXuatXu.setSelectedIndex(0);
        rdoTatCa.setSelected(true);
    }//GEN-LAST:event_btnResetBoLocActionPerformed

    private void cbbXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbXuatXuActionPerformed
        // Lấy phần tử được chọn từ ComboBox
        Long selectedXuatXuID = (Long) cbbXuatXu.getSelectedItem();

        // Kiểm tra xem phần tử được chọn có phải là null không
        if (selectedXuatXuID != null) {
            // Chuyển đổi Long thành int
            int selectedXuatXuIDInt = selectedXuatXuID.intValue();

            // Gọi phương thức XuatXu từ ChiTietSanPhamService để lấy danh sách sản phẩm tương ứng với xuất xứ đã chọn
            List<backend.entity.ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.XuatXu(selectedXuatXuIDInt);

            // Xóa dữ liệu hiện tại trong bảng
            dtm.setRowCount(0);

            // Hiển thị danh sách sản phẩm mới trong bảng
            showDataTable(chiTietSanPhams);
        } else {
            showDataTable(chitietsanpham);
        }
    }//GEN-LAST:event_cbbXuatXuActionPerformed

    private void cbbNSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNSXActionPerformed
        Long selectedNSXID = (Long) cbbNSX.getSelectedItem(); // Lấy phần tử được chọn từ ComboBox

        // Kiểm tra xem phần tử được chọn có phải là null không
        if (selectedNSXID != null) {
            // Ép kiểu từ Long sang Integer
            Integer nsxID = selectedNSXID.intValue();

            // Gọi phương thức NSX từ ChiTietSanPhamService để lấy danh sách sản phẩm tương ứng với NSX đã chọn
            List<backend.entity.ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.NSX(nsxID);

            // Xóa dữ liệu hiện tại trong bảng
            dtm.setRowCount(0);

            // Hiển thị danh sách sản phẩm mới trong bảng
            showDataTable(chiTietSanPhams);
        } else {
            showDataTable(chitietsanpham);
        }
    }//GEN-LAST:event_cbbNSXActionPerformed

    private void cbbGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGiaActionPerformed
        BigDecimal selectedGia = (BigDecimal) cbbGia.getSelectedItem();

        // Kiểm tra xem phần tử được chọn có phải là null không
        if (selectedGia != null) {
            // Gọi phương thức GiaBan từ ChiTietSanPhamService để lấy danh sách sản phẩm tương ứng với giá đã chọn
            List<backend.entity.ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.GiaBan(selectedGia);

            // Xóa dữ liệu hiện tại trong bảng
            dtm.setRowCount(0);

            // Hiển thị danh sách sản phẩm mới trong bảng
            showDataTable(chiTietSanPhams);
        } else {
            showDataTable(chitietsanpham);
        }
    }//GEN-LAST:event_cbbGiaActionPerformed

    private void rdoConHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoConHangActionPerformed
        List<backend.entity.ChiTietSanPham> conHangList = chiTietSanPhamService.ConHang();

        // Hiển thị danh sách sản phẩm còn hàng trong bảng hoặc cập nhật giao diện người dùng
        showDataTable(conHangList); // Thay thế showDataTable bằng phương thức hiển thị danh sách sản phẩm của bạn
    }//GEN-LAST:event_rdoConHangActionPerformed

    private void rdoHetHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHetHangActionPerformed
        List<backend.entity.ChiTietSanPham> hetHangList = chiTietSanPhamService.HetHang();

        // Hiển thị danh sách sản phẩm hết hàng trong bảng hoặc cập nhật giao diện người dùng
        showDataTable(hetHangList); // Thay thế showDataTable bằng phương thức hiển thị danh sách sản phẩm của bạn
    }//GEN-LAST:event_rdoHetHangActionPerformed

    private void rdoTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaActionPerformed
        showDataTable(chitietsanpham);
    }//GEN-LAST:event_rdoTatCaActionPerformed

    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        qrcode qrScannerFrame = new qrcode();
        qrScannerFrame.setQRCodeListener(this);
        qrScannerFrame.setVisible(true);
    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void btnThemSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSuaActionPerformed
        Application.showForm(new ThemSanPhamChiTiet());
    }//GEN-LAST:event_btnThemSuaActionPerformed

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
        backend.entity.ChiTietSanPham chiTietSanPham = chitietsanpham.get(row);

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnQuetQR;
    private javax.swing.JButton btnResetBoLoc;
    private javax.swing.JButton btnTaoQR;
    private javax.swing.JButton btnThemSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private combobox.Combobox cbbDanhMuc;
    private combobox.Combobox cbbGia;
    private combobox.Combobox cbbNSX;
    private combobox.Combobox cbbXuatXu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoConHang;
    private javax.swing.JRadioButton rdoHetHang;
    private javax.swing.JRadioButton rdoTatCa;
    private swing.RoundPanel roundPanel8;
    private javax.swing.JTable tblChiTietSanPham;
    // End of variables declaration//GEN-END:variables

}
