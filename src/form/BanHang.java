/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.entity.DanhMuc;
import backend.entity.HoaDonEntity;
import backend.entity.KhachHangEntity;
import backend.entity.NSX;
import backend.entity.PhieuGiamGia;
import backend.entity.XuatXu;
import backend.service.BanHangService;
import backend.service.DanhMucService;
import backend.service.GiaBanService;
import backend.service.HDCTService;
import backend.service.HoaDonService;
import backend.service.KhachHangService;
import backend.service.LSHDService;
import backend.service.NSXService;
import backend.service.PhieuGiamGiaService;
import backend.service.QLHDService;
import backend.service.XuatXuService;
import backend.viewmodel.BHHDViewModel;
import backend.viewmodel.BHSPViewModel;
import backend.viewmodel.HDCTViewModel;
import backend.viewmodel.HoaDonViewModel;
import backend.viewmodel.PhieuGiamGiaViewModel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import raven.application.Application;
import raven.toast.Notifications;

/**
 *
 * @author leanb
 */
public class BanHang extends javax.swing.JPanel implements ThemKhachHang.KhachHangSelectedListener {

    private List<BHHDViewModel> listHD = new ArrayList<>();
    private DefaultTableModel dtmHD = new DefaultTableModel();
    private BanHangService srBH = new BanHangService();

    private DefaultTableModel dtmSP = new DefaultTableModel();
    private List<BHSPViewModel> listSP = new ArrayList<>();

    private DefaultComboBoxModel dcbmDM = new DefaultComboBoxModel();
    private List<DanhMuc> listDM = new ArrayList<>();
    private DanhMucService srDM = new DanhMucService();

    private DefaultComboBoxModel dcbmNSX = new DefaultComboBoxModel();
    private List<NSX> listNSX = new ArrayList<>();
    private NSXService srNSX = new NSXService();

    private DefaultComboBoxModel dcbmXX = new DefaultComboBoxModel();
    private List<XuatXu> listXX = new ArrayList<>();
    private XuatXuService srXX = new XuatXuService();

    private HoaDonService srHD = new HoaDonService();

    DefaultComboBoxModel dcbmGia = new DefaultComboBoxModel();
    List<backend.entity.ChiTietSanPham> giaChiTietSanPhams = new ArrayList<>();
    GiaBanService giaBanService = new GiaBanService();

    private DefaultTableModel dtmHDCT = new DefaultTableModel();
    private List<HDCTViewModel> listHDCT = new ArrayList<>();
    private HDCTService srHDCT = new HDCTService();

    private DefaultComboBoxModel dcbmPGG = new DefaultComboBoxModel();
    private PhieuGiamGiaService srPGG = new PhieuGiamGiaService();
    private List<PhieuGiamGiaViewModel> listPGG = new ArrayList<>();

    private LSHDService srLSHD = new LSHDService();
    private QLHDService srQLHD = new QLHDService();

    private List<KhachHangEntity> listKH = new ArrayList<>();
    private KhachHangService srKH = new KhachHangService();

    private List<HoaDonViewModel> listHDVM = new ArrayList<>();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

    private NumberFormat currencyFormat = new DecimalFormat("# VND"); // Định dạng số tiền

    /**
     * Creates new form BanHang
     */
    public BanHang() {
        initComponents();
        dtmHD = (DefaultTableModel) tblHD.getModel();
        listHD = srBH.getHD();
        showDataHD(listHD);

        dtmSP = (DefaultTableModel) tblSP.getModel();
        listSP = srBH.getSP();
        showDataSP(listSP);

        listDM = srDM.getAll();
        dcbmDM = (DefaultComboBoxModel) cbbDanhMuc.getModel();
        showcbbDanhMuc(listDM);

        listNSX = srNSX.getAll();
        dcbmNSX = (DefaultComboBoxModel) cbbNSX.getModel();
        showcbbNSX(listNSX);

        listXX = srXX.getAll();
        dcbmXX = (DefaultComboBoxModel) cbbXxu.getModel();
        showcbbXuatXu(listXX);

        giaChiTietSanPhams = giaBanService.getAll();
        dcbmGia = (DefaultComboBoxModel) cbbGia.getModel();
        showcbbGia(giaChiTietSanPhams);

        dcbmPGG = (DefaultComboBoxModel) cbbPGG.getModel();
        listPGG = srPGG.getAll();
        showcbbPGG(listPGG);

        txtTienDua.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                tinhTienThua();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tinhTienThua();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tinhTienThua();
            }
        });
    }

    public void showDataHD(List<BHHDViewModel> listHD) {
        dtmHD.setRowCount(0);
        // Lấy đối tượng TableColumn của cột "STT" từ JTable
        TableColumn columnSTT = tblHD.getColumnModel().getColumn(0); // 0 là chỉ số cột, có thể là 1 nếu cột "STT" là cột thứ hai

        // Đặt kích cỡ chiều rộng mong muốn
        columnSTT.setPreferredWidth(0); // là chiều rộng mong muốn

        // Cập nhật lại JTable để áp dụng thay đổi
        tblHD.repaint();

        int i = 1;
        for (BHHDViewModel hd : listHD) {
            // Kiểm tra nếu tổng số lượng sản phẩm bằng 0, thiết lập giá trị hiển thị là 0
            int tongSP = hd.getTongSP() == 0 ? 0 : hd.getTongSP();

            dtmHD.addRow(new Object[]{
                i++, hd.getMaHD(), hd.getNgayTao().format(formatter), hd.getMaNV(), tongSP, hd.getTrangThai()
            });
        }
    }

    public void showDataHDCT(List<HDCTViewModel> listHDCT) {
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
            txtTongTien.setText(String.valueOf(currencyFormat.format(hdct.getSoLuong() * hdct.getGiaBan())));
        }
    }

    public void showDataSP(List<BHSPViewModel> listSP) {
        dtmSP.setRowCount(0);
        // Lấy đối tượng TableColumn của cột "STT" từ JTable
        TableColumn columnSTT = tblSP.getColumnModel().getColumn(0); // 0 là chỉ số cột, có thể là 1 nếu cột "STT" là cột thứ hai

        // Đặt kích cỡ chiều rộng mong muốn
        columnSTT.setPreferredWidth(0); // là chiều rộng mong muốn

        // Cập nhật lại JTable để áp dụng thay đổi
        tblSP.repaint();

        int i = 1;
        for (BHSPViewModel sp : listSP) {
            dtmSP.addRow(new Object[]{
                i++, "SPCT-" + sp.getMaSPCT(), sp.getTenSP(),
                sp.getDanhMuc(), sp.getXuatXu(), sp.getNsx(), sp.getSize(),
                sp.getSoLuong(), currencyFormat.format(sp.getGiaBan())
            });
        }
    }

    public void showcbbDanhMuc(List<DanhMuc> danhMucs) {
        dcbmDM.removeAllElements();
        dcbmDM.addElement("Tất cả");
        for (DanhMuc danhMuc : danhMucs) {
            dcbmDM.addElement(danhMuc.getTenDanhMuc());
        }
        cbbDanhMuc.setSelectedItem("Tất cả");
    }

    public void showcbbNSX(List<NSX> nsxs) {
        dcbmNSX.removeAllElements();
        // Thêm một mục null đầu tiên
        dcbmNSX.addElement("Tất cả");
        for (NSX nsx1 : nsxs) {
            dcbmNSX.addElement(nsx1.getTenNSX());
        }
        cbbDanhMuc.setSelectedItem("Tất cả");
    }

    public void showcbbXuatXu(List<XuatXu> xuatXus) {
        dcbmXX.removeAllElements();
        // Thêm một mục null đầu tiên
        dcbmXX.addElement("Tất cả");
        for (XuatXu xuatXu : xuatXus) {
            dcbmXX.addElement(xuatXu.getTenXuatXu());
        }
        cbbDanhMuc.setSelectedItem("Tất cả");
    }

    public void showcbbGia(List<backend.entity.ChiTietSanPham> chiTietSanPhamsz) {
        dcbmGia.removeAllElements();
        // Thêm một mục null đầu tiên
        dcbmGia.addElement("Tất cả");
        for (backend.entity.ChiTietSanPham chiTietSanPham : chiTietSanPhamsz) {
            dcbmGia.addElement(chiTietSanPham.getGiaBan());
        }
        cbbDanhMuc.setSelectedItem("Tất cả");
    }

    public void showcbbPGG(List<PhieuGiamGiaViewModel> listPGG) {
        dcbmPGG.removeAllElements();
        dcbmPGG.addElement(null);
        for (PhieuGiamGiaViewModel phieuGiamGiaViewModel : listPGG) {
            dcbmPGG.addElement(phieuGiamGiaViewModel.getTenGiamGia());
        }
    }

    public void showDetailHD() {
        LocalDateTime now = LocalDateTime.now();
        int index = tblHD.getSelectedRow();

        if (index >= 0) {
            listHDVM = srHD.getAll();
            BHHDViewModel hd1 = listHD.get(index);

            listHDCT = srHDCT.getAll(index);

            HoaDonViewModel hd = listHDVM.get(index);
//            listKH = srKH.getMT(hd1.get);
//            KhachHangEntity kh = li

            // Truy xuất thông tin khách hàng từ hóa đơn
            if (hd1.getMaKH() != null && hd1.getMaKH() != null) {              
                    txtMaKH.setText(hd1.getMaKH());
                    txtTenKH.setText(hd1.getTenKH());
                    txtTenKH2.setText(hd1.getTenKH());

            }else{
                    txtMaKH.setText("KHL00001");
                    txtTenKH.setText("Khách bán lẻ");
                    txtTenKH2.setText("Khách bán lẻ");
            }

            txtMaHD.setText(hd1.getMaHD());
            txtNTao.setText(hd1.getNgayTao().format(formatter));
            txtNTToan.setText(now.format(formatter));
            txtMaNV.setText(hd1.getMaNV());
            txtTongTien.setText(String.valueOf(currencyFormat.format(hd1.getTongTien())));
            lbTong.setText(String.valueOf(currencyFormat.format(hd1.getTongTien())));

        }
    }

    //TinhTienThua
    public void tinhTienThua() {
        try {
            String tongText = txtTongTien.getText().trim().replaceAll("[^\\d.]", "");
            String tienDuaText = txtTienDua.getText().trim().replaceAll("[^\\d.]", "");

            if (!tongText.isEmpty() && !tienDuaText.isEmpty()) {
                double tongTien = Double.parseDouble(tongText);
                double tienKhachDua = Double.parseDouble(tienDuaText);

                double tienThua = tienKhachDua - tongTien;
                txtTienThua.setText(currencyFormat.format(tienThua));
            }

        } catch (NumberFormatException ex) {
            // Xử lý ngoại lệ, ví dụ: hiển thị thông báo lỗi cho người dùng
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá trị số hợp lệ.");
        }
    }

//    // Phương thức để nhận giá trị được chọn từ ThemKhachHang
//    public void nhanGiaTriKhachHang(String maKhachHang, String tenKhachHang) {
//        // Đặt các giá trị vào các thành phần trên BanHang
//        txtMaKH.setText(maKhachHang);
//        txtTenKH.setText(tenKhachHang);
//        txtTenKH2.setText(tenKhachHang);
//    }
    @Override
    public void khachHangSelected(String maKhachHang, String tenKhachHang) {
        // Đặt thông tin vào các JTextField trong JPanel BanHang
        txtMaKH.setText(maKhachHang);
        txtTenKH.setText(tenKhachHang);
        txtTenKH2.setText(tenKhachHang);
    }

    public boolean checkKHHD() {
        if (txtMaKH.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mã khách hàng trống");
            return false;
        }
        if (txtTenKH.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên khách hàng trống");
            return false;
        }

        if (txtMaHD.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mã hóa đơn trống");
            return false;
        }

        if (txtNTao.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày tạo trống");
            return false;
        }

        if (txtNTToan.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày thanh toán trống");
            return false;
        }
        if (txtMaNV.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mã nhân viên trống");
            return false;
        }

        if (txtTenKH2.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên khách hàng trống");
            return false;
        }

        if (txtTongTien.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tổng tiền trống");
            return false;
        }

        if (cbbPGG.getSelectedItem() == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Chưa chọn phiếu giảm giás");
            return false;
        }

        if (txtTienDua.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tiền đưa trống");
            return false;
        }

        if (txtTienThua.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tiền thừa trống");
            return false;
        }
        return true;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        btnQR = new javax.swing.JButton();
        btnAddHD = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        roundPanel2 = new swing.RoundPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGH = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        roundPanel3 = new swing.RoundPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cbbDanhMuc = new combobox.Combobox();
        cbbXxu = new combobox.Combobox();
        cbbNSX = new combobox.Combobox();
        cbbGia = new combobox.Combobox();
        txtSearch = new textfield.TextField();
        roundPanel4 = new swing.RoundPanel();
        jLabel4 = new javax.swing.JLabel();
        roundPanel5 = new swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnChonKh = new javax.swing.JButton();
        roundPanel6 = new swing.RoundPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbbPGG = new javax.swing.JComboBox<>();
        txtMaHD = new textfield.TextField();
        txtNTao = new textfield.TextField();
        txtNTToan = new textfield.TextField();
        txtMaNV = new textfield.TextField();
        txtTenKH2 = new textfield.TextField();
        txtTongTien = new textfield.TextField();
        txtTienDua = new textfield.TextField();
        txtTienThua = new textfield.TextField();
        lbTong = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btnHuy = new javax.swing.JButton();
        btnTToan = new javax.swing.JButton();

        setOpaque(false);

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Mã hóa đơn", "Ngày tạo", "Nhân viên", "Tổng SP", "Trạng thái"
            }
        ));
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHD);

        btnQR.setText("Quét mã");

        btnAddHD.setText("Tạo hóa đơn");
        btnAddHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddHDActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setText("Hóa đơn");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(btnQR)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddHD))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQR)
                    .addComponent(btnAddHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        tblGH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Mã SPCT", "Tên SP", "Màu sắc", "Size", "Chất liệu", "Thương Hiệu", "Giá bán", "Số lượng", "Thành tiền"
            }
        ));
        jScrollPane2.setViewportView(tblGH);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setText("Giỏ hàng");

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
                        .addGap(0, 650, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Mã SPCT", "Tên SP", "Danh mục", "Xuất xứ", "NSX", "Size", "Số lượng tồn", "Đơn giá"
            }
        ));
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSP);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setText("Danh sách sản phẩm");

        cbbDanhMuc.setLabeText("Danh mục");

        cbbXxu.setLabeText("Xuất xứ");
        cbbXxu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbXxuActionPerformed(evt);
            }
        });

        cbbNSX.setLabeText("NSX");

        cbbGia.setLabeText("Giá");

        txtSearch.setLabelText("Tìm kiếm");

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(roundPanel3Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbXxu, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbXxu, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setText("Đơn hàng");

        roundPanel5.setBackground(new java.awt.Color(3, 6, 55));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Thông tin khách hàng");

        txtMaKH.setText("KHL00001");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Mã khách hàng");

        txtTenKH.setText("Khách bán lẻ");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tên khách hàng");

        btnChonKh.setText("Chọn");
        btnChonKh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(roundPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(txtMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)))
                            .addGroup(roundPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtTenKH)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChonKh)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChonKh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        roundPanel6.setBackground(new java.awt.Color(54, 84, 134));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Thông tin khách hàng");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Mã hóa đơn");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Ngày tạo");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Ngày thanh toán");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tổng tiền");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Tên khách hàng");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Mã nhân viên");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Phiếu giảm giá");

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Tiền khách đưa");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Tiền thừa");

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Tổng: ");

        cbbPGG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtMaHD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtMaHD.setLabelText("");
        txtMaHD.setMargin(new java.awt.Insets(2, 0, 2, 0));

        txtNTao.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNTao.setLabelText("");

        txtNTToan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNTToan.setLabelText("");

        txtMaNV.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtMaNV.setLabelText("");

        txtTenKH2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTenKH2.setLabelText("");

        txtTongTien.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTongTien.setLabelText("");

        txtTienDua.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTienDua.setLabelText("");

        txtTienThua.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTienThua.setLabelText("");

        lbTong.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbTong.setForeground(new java.awt.Color(255, 255, 255));
        lbTong.setText("0 VND");

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));

        btnHuy.setText("Hủy HD");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnTToan.setText("Thanh Toán");
        btnTToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel6Layout = new javax.swing.GroupLayout(roundPanel6);
        roundPanel6.setLayout(roundPanel6Layout);
        roundPanel6Layout.setHorizontalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel6Layout.createSequentialGroup()
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addComponent(txtTienDua, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(txtTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(roundPanel6Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnHuy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTToan)
                        .addGap(60, 60, 60))
                    .addGroup(roundPanel6Layout.createSequentialGroup()
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel22)
                                .addGroup(roundPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbTong)
                                    .addGap(13, 13, 13)))
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtTongTien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTenKH2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNTToan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNTao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMaHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbbPGG, javax.swing.GroupLayout.Alignment.LEADING, 0, 196, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        roundPanel6Layout.setVerticalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtNTToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtTenKH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbPGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTienDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTong, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHuy)
                            .addComponent(btnTToan))
                        .addGap(3, 3, 3)))
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(roundPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(roundPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbXxuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbXxuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbXxuActionPerformed

    private void btnAddHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddHDActionPerformed
        int answer = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm hóa đơn?");
        String hanhDong = "Tạo hóa đơn mới";
        if (answer == JOptionPane.YES_OPTION) {
            if (listHD.size() < 10) {
                srHD.addHD();
                listHD = srBH.getHD();
                showDataHD(listHD);
                srLSHD.addLSHD(hanhDong);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã thêm");
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Bạn chỉ được tạo tối đa 10 hóa đơn!");
            }
        } else {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy");
        }
    }//GEN-LAST:event_btnAddHDActionPerformed

    private void btnChonKhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKhActionPerformed
        ThemKhachHang panel = new ThemKhachHang();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Đăng ký lắng nghe sự kiện chọn khách hàng từ ThemKhachHang
        panel.addKhachHangSelectedListener(this);

        frame.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//GEN-LAST:event_btnChonKhActionPerformed

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        int rowIndex = tblHD.getSelectedRow();
        BHHDViewModel hd = listHD.get(rowIndex);

        dtmHDCT = (DefaultTableModel) tblGH.getModel();
        listHDCT = srHDCT.getAll(hd.getId());
        showDataHDCT(listHDCT);

        showDetailHD();
    }//GEN-LAST:event_tblHDMouseClicked

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        int index = tblHD.getSelectedRow();
        if (index >= 0) {
            BHHDViewModel hd = listHD.get(index);
            srQLHD.deleteHD(hd.getId());
            listHD = srBH.getHD();
            showDataHD(listHD);

            dtmHDCT = (DefaultTableModel) tblGH.getModel();
            dtmHDCT.setRowCount(0);

            showDetailHD();

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy thành công");
        }
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnTToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTToanActionPerformed
        if (checkKHHD()) {
            int rowIndexHD = tblHD.getSelectedRow();
            if (rowIndexHD >= 0) {
                BHHDViewModel hd = listHD.get(rowIndexHD);
                int idHD = hd.getId();
                String maNV = txtMaNV.getText().trim();
                String maKH = txtMaKH.getText().trim();
                String maGG = cbbPGG.getSelectedItem().toString();
                
                System.out.println("Mã nhân viên: " + maNV);
                System.out.println("Mã KH: " + maKH);
                System.out.println("Mã GG: " + maGG);
                srHD.updateHD(idHD, maNV, maKH, maGG);
                listHD = srBH.getHD();
                showDataHD(listHD);
                dtmHDCT = (DefaultTableModel) tblGH.getModel();
                dtmHDCT.setRowCount(0);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã thanh toán thành công");
            }
        }
    }//GEN-LAST:event_btnTToanActionPerformed

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked
// Lấy chỉ mục của hàng được chọn trong bảng hóa đơn
        int rowIndexHD = tblHD.getSelectedRow();
        // Kiểm tra xem có hàng nào được chọn trong bảng hóa đơn không
        if (rowIndexHD >= 0) {
            BHHDViewModel hd = listHD.get(rowIndexHD);
            int idHD = hd.getId();
            System.out.println(idHD);

            // Lấy chỉ mục của hàng được chọn trong bảng sản phẩm
            int rowIndexSP = tblSP.getSelectedRow();
            // Kiểm tra xem có hàng nào được chọn trong bảng sản phẩm không
            if (rowIndexSP >= 0) {
                BHSPViewModel sp = listSP.get(rowIndexSP);
                int idSPCT = sp.getMaSPCT();
                double giaBan = sp.getGiaBan();

                // Lấy số lượng hiện có từ bảng
                int soLuongHienCo = (int) tblSP.getValueAt(rowIndexSP, 7); // Giả sử số lượng hiện có ở cột thứ 8 (index 7)

                // Hiển thị hộp thoại để yêu cầu nhập số lượng mới
                String input = JOptionPane.showInputDialog(this, "Nhập số lượng muốn mua:");

                // Kiểm tra xem người dùng đã nhập hay chưa
                if (input != null && !input.isEmpty()) {
                    // Chuyển đổi chuỗi nhập vào thành số nguyên
                    int soLuongMua = Integer.parseInt(input);

                    // Kiểm tra xem số lượng mua có lớn hơn số lượng hiện có không
                    if (soLuongMua > soLuongHienCo) {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số lượng mua vượt quá số lượng hiện có!");
                    } else {
                        // Trừ đi số lượng mới từ số lượng hiện có để cập nhật số lượng còn lại
                        int soLuongConLai = soLuongHienCo - soLuongMua;

                        // Cập nhật số lượng còn lại vào cơ sở dữ liệu
                        srBH.updateSP(sp.getMaSPCT(), soLuongConLai);

                        // Lấy lại danh sách sản phẩm sau khi cập nhật
                        listSP = srBH.getSP();
                        showDataSP(listSP);

                        // Thêm hóa đơn chi tiết với số lượng mua vào cơ sở dữ liệu
                        srHDCT.addHDCT(idHD, idSPCT, soLuongMua, giaBan);

                        // Lấy lại danh sách hóa đơn chi tiết sau khi thêm mới
                        dtmHDCT = (DefaultTableModel) tblGH.getModel();
                        listHDCT = srHDCT.getAll(idHD);
                        showDataHDCT(listHDCT);

                        listHD = srBH.getHD();
                        showDataHD(listHD);

                        showDetailHD();

                        System.out.println("idHD: " + idHD);
                        System.out.println("idSPCT: " + idSPCT);
                        System.out.println("soLuongmua: " + soLuongMua);
                        System.out.println("giaBan: " + giaBan);
                        // Hiển thị kết quả hoặc thực hiện các hành động khác với kết quả này
                        System.out.println("Số lượng còn lại:" + soLuongConLai);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã thêm sản phẩm vào giỏ hàng!");
                    }
                }
            }
        }

    }//GEN-LAST:event_tblSPMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddHD;
    private javax.swing.JButton btnChonKh;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btnTToan;
    private combobox.Combobox cbbDanhMuc;
    private combobox.Combobox cbbGia;
    private combobox.Combobox cbbNSX;
    private javax.swing.JComboBox<String> cbbPGG;
    private combobox.Combobox cbbXxu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbTong;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel2;
    private swing.RoundPanel roundPanel3;
    private swing.RoundPanel roundPanel4;
    private swing.RoundPanel roundPanel5;
    private swing.RoundPanel roundPanel6;
    private javax.swing.JTable tblGH;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblSP;
    private textfield.TextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private textfield.TextField txtMaNV;
    private textfield.TextField txtNTToan;
    private textfield.TextField txtNTao;
    private textfield.TextField txtSearch;
    private javax.swing.JTextField txtTenKH;
    private textfield.TextField txtTenKH2;
    private textfield.TextField txtTienDua;
    private textfield.TextField txtTienThua;
    private textfield.TextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
