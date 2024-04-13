/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.entity.DanhMuc;
import backend.entity.KhachHangEntity;
import backend.entity.NSX;
import backend.entity.PhieuGiamGia;
import backend.entity.PhuongThucThanhToan;
import backend.entity.XuatXu;
import backend.qrcodeBH.quetQR;
import backend.service.BanHangService;
import backend.service.ChiTietSanPhamService;
import backend.service.DanhMucService;
import backend.service.GiaBanService;
import backend.service.HDCTService;
import backend.service.HinhThucThanhToanService;
import backend.service.HoaDonService;
import backend.service.KhachHangService;
import backend.service.LSHDService;
import backend.service.NSXService;
import backend.service.PhieuGiamGiaService;
import backend.service.PhuongThucThanhToanService;
import backend.service.QLHDService;
import backend.service.XuatXuService;
import backend.viewmodel.BHHDViewModel;
import backend.viewmodel.BHSPViewModel;
import backend.viewmodel.HDCTViewModel;
import backend.viewmodel.HoaDonViewModel;
import backend.viewmodel.PhieuGiamGiaViewModel;
import backend.viewmodel.SanPhamChiTietViewModel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import raven.toast.Notifications;

/**
 *
 * @author leanb
 */
public class BanHang extends javax.swing.JPanel implements ThemKhachHang.KhachHangSelectedListener, ChonNVVanChuyen.nhanVienSelectedListener {

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
    private DefaultComboBoxModel dcbmPGG2 = new DefaultComboBoxModel();
    private PhieuGiamGiaService srPGG = new PhieuGiamGiaService();
    private List<PhieuGiamGiaViewModel> listPGG = new ArrayList<>();

    private DefaultComboBoxModel dcbmTT = new DefaultComboBoxModel();
    private DefaultComboBoxModel dcbmTT2 = new DefaultComboBoxModel();
    private PhuongThucThanhToanService srTT = new PhuongThucThanhToanService();
    private List<PhuongThucThanhToan> listTT = new ArrayList<>();

    private LSHDService srLSHD = new LSHDService();
    private QLHDService srQLHD = new QLHDService();

    private List<KhachHangEntity> listKH = new ArrayList<>();
    private KhachHangService srKH = new KhachHangService();

    private List<SanPhamChiTietViewModel> chitietsanpham = new ArrayList<>();
    private ChiTietSanPhamService chiTietSanPhamService = new ChiTietSanPhamService();

    private List<PhieuGiamGiaViewModel> listGG = new ArrayList<>();
    private PhieuGiamGiaService srGG = new PhieuGiamGiaService();

    private List<HoaDonViewModel> listHDVM = new ArrayList<>();

    private HinhThucThanhToanService srHTTT = new HinhThucThanhToanService();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

    private NumberFormat currencyFormat = new DecimalFormat("###,###,### VND");

    // Định dạng số tiền
    private int selectedRowIndex = -1; // Khởi tạo giá trị ban đầu là -1 để đại diện cho việc không có hàng nào được chọn
    private double tienKhachThua = 0.0;

    private boolean isCbbPGGSelected = false;

    private static volatile boolean qrCodeReceived = false;

    private String trangThai = "";

    private int indexTab = 0;

    private LocalDateTime now = LocalDateTime.now();

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

        chitietsanpham = chiTietSanPhamService.getAll();
        giaChiTietSanPhams = giaBanService.getAll();
        dcbmGia = (DefaultComboBoxModel) cbbGia.getModel();
        showcbbGia(chitietsanpham);

        dcbmTT = (DefaultComboBoxModel) cbbTT.getModel();
        dcbmTT2 = (DefaultComboBoxModel) cbbTTDH.getModel();
        listTT = srTT.getTT();
        showcbbTT(listTT);
        showcbbTT2(listTT);

        dcbmPGG = (DefaultComboBoxModel) cbbPGG.getModel();
        dcbmPGG2 = (DefaultComboBoxModel) cbbPGG2.getModel();
        listPGG = srPGG.sortGG();

        cbbPGG.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    isCbbPGGSelected = true;
                    int index = selectedRowIndex;
                    System.out.println("Đang xử lý phần tử thứ " + index);

                    if (index >= 0) {
                        BHHDViewModel hd1 = listHD.get(index);
                        String pgg = null;
                        if (cbbPGG.getSelectedItem() != null) {
                            pgg = cbbPGG.getSelectedItem().toString();
                        }
                        double tongTien = hd1.getTongTien();

                        // Tính toán giảm giá dựa trên tên của phiếu giảm giá được chọn
                        double giamGia = 0.0;
                        double giamToiDa = 0.0;
                        if (pgg != null) {
                            // Tìm phần trăm và giảm tối đa từ danh sách phiếu giảm giá
                            for (PhieuGiamGiaViewModel phieuGiamGia : listPGG) {
                                if (phieuGiamGia.getTenGiamGia().equals(pgg)) {
                                    double phanTramGiam = phieuGiamGia.getSoPhanTramGiam() / 100.0;
                                    giamToiDa = phieuGiamGia.getGiamToiDa();
                                    giamGia = Math.min(tongTien * phanTramGiam, giamToiDa); // Lấy giá trị nhỏ nhất giữa giảm theo phần trăm và giảm tối đa
                                    break;
                                }
                            }
                        }

                        // Tính tổng tiền sau khi được giảm giá
                        double tongTienSauGiamGia = tongTien - giamGia;

                        // Hiển thị hộp thoại xác nhận trước khi áp dụng phiếu giảm giá
                        int option = JOptionPane.showConfirmDialog(null, "Phiếu giảm giả này có thể giảm tối đa " + currencyFormat.format(giamToiDa), "Xác nhận", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            // Áp dụng giảm giá và cập nhật tổng tiền
                            txtTongTien.setText(currencyFormat.format(tongTienSauGiamGia));
                            lbTong.setText(currencyFormat.format(tongTienSauGiamGia));
                        } else {
                            // Nếu người dùng chọn NO hoặc đóng hộp thoại, không thực hiện gì cả
                        }
                    }
                }
            }
        });

        cbbPGG2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    isCbbPGGSelected = true;
                    int index = selectedRowIndex;
                    System.out.println("Đang xử lý phần tử thứ " + index);

                    if (index >= 0) {
                        BHHDViewModel hd1 = listHD.get(index);
                        String pgg = null;
                        if (cbbPGG2.getSelectedItem() != null) {
                            pgg = cbbPGG2.getSelectedItem().toString();
                        }
                        double tongTien = hd1.getTongTien();

                        // Tính toán giảm giá dựa trên tên của phiếu giảm giá được chọn
                        double giamGia = 0.0;
                        double giamToiDa = 0.0;
                        if (pgg != null) {
                            // Tìm phần trăm và giảm tối đa từ danh sách phiếu giảm giá
                            for (PhieuGiamGiaViewModel phieuGiamGia : listPGG) {
                                if (phieuGiamGia.getTenGiamGia().equals(pgg)) {
                                    double phanTramGiam = phieuGiamGia.getSoPhanTramGiam() / 100.0;
                                    giamToiDa = phieuGiamGia.getGiamToiDa();
                                    giamGia = Math.min(tongTien * phanTramGiam, giamToiDa); // Lấy giá trị nhỏ nhất giữa giảm theo phần trăm và giảm tối đa
                                    break;
                                }
                            }
                        }

                        // Tính tổng tiền sau khi được giảm giá
                        double tongTienSauGiamGia = tongTien - giamGia;

                        // Hiển thị hộp thoại xác nhận trước khi áp dụng phiếu giảm giá
                        int option = JOptionPane.showConfirmDialog(null, "Phiếu giảm giả này có thể giảm tối đa " + currencyFormat.format(giamToiDa), "Xác nhận", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            // Áp dụng giảm giá và cập nhật tổng tiền
                            txtTongTienDH.setText(currencyFormat.format(tongTienSauGiamGia));
                            lbTongDH.setText(currencyFormat.format(tongTienSauGiamGia));
                        } else {
                            // Nếu người dùng chọn NO hoặc đóng hộp thoại, không thực hiện gì cả
                        }
                    }
                }
            }
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchSP();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchSP();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchSP();
            }
        });

        // Đính kèm sự kiện MouseListener cho tblHD
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });

        txtSearchHD.getDocument().addDocumentListener(new DocumentListener() {
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

        cbbTT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = cbbTT.getSelectedItem();
                if (selectedItem != null) {
                    if (selectedItem.equals("Tiền mặt")) {
                        txtTienDua.setEnabled(true);
                        txtTienCK.setEnabled(false); // Vô hiệu hóa JTextField nhập tiền chuyển khoản
                        txtTienCK.setText("0"); // Đặt giá trị mặc định là 0
                    } else if (selectedItem.equals("Chuyển khoản")) {
                        txtTienCK.setEnabled(true); // Kích hoạt lại JTextField nhập tiền chuyển khoản
                        txtTienDua.setEnabled(false);
                        txtTienDua.setText("0");
                    } else {
                        txtTienDua.setEnabled(true);
                        txtTienCK.setEnabled(true);
                        txtTienDua.setText("");
                        txtTienCK.setText("");
                    }
                }

            }
        });

        cbbTTDH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = cbbTTDH.getSelectedItem();
                if (selectedItem != null) {
                    if (selectedItem.equals("Tiền mặt")) {
                        txtTienDua.setEnabled(true);
                        txtTienCKDH.setEnabled(false); // Vô hiệu hóa JTextField nhập tiền chuyển khoản
                        txtTienCKDH.setText("0"); // Đặt giá trị mặc định là 0
                    } else if (selectedItem.equals("Chuyển khoản")) {
                        txtTienCKDH.setEnabled(true); // Kích hoạt lại JTextField nhập tiền chuyển khoản
                        txtTienDuaDH.setEnabled(false);
                        txtTienDuaDH.setText("0");
                        // Lấy ngày hiện tại từ JDateChooser
                        Calendar calendar = Calendar.getInstance();
                        // Đặt ngày hiện tại cho JDateChooser
                        jdcNgayNhan.setDate(calendar.getTime());
                    } else {
                        txtTienDuaDH.setEnabled(true);
                        txtTienCKDH.setEnabled(true);
                        txtTienDuaDH.setText("");
                        txtTienCKDH.setText("");
                    }
                }
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
        cbbNSX.setSelectedItem("Tất cả");
    }

    public void showcbbXuatXu(List<XuatXu> xuatXus) {
        dcbmXX.removeAllElements();
        // Thêm một mục null đầu tiên
        dcbmXX.addElement("Tất cả");
        for (XuatXu xuatXu : xuatXus) {
            dcbmXX.addElement(xuatXu.getTenXuatXu());
        }
        cbbXxu.setSelectedItem("Tất cả");
    }

    public void showcbbGia(List<SanPhamChiTietViewModel> chiTietSanPhamsz) {
        dcbmGia.removeAllElements();
        // Thêm các lựa chọn cho combobox
        dcbmGia.addElement("Tất cả");
        dcbmGia.addElement("Giá từ thấp đến cao");
        dcbmGia.addElement("Giá từ cao đến thấp");
        cbbGia.setSelectedItem("Tất cả");
    }

    public void showcbbTT(List<PhuongThucThanhToan> listTT) {
        dcbmTT.removeAllElements();
        for (PhuongThucThanhToan phuongThucThanhToan : listTT) {
            dcbmTT.addElement(phuongThucThanhToan.getTenKieuThanhToan());
        }
    }

    public void showcbbTT2(List<PhuongThucThanhToan> listTT) {
        dcbmTT2.removeAllElements();
        for (PhuongThucThanhToan phuongThucThanhToan : listTT) {
            dcbmTT2.addElement(phuongThucThanhToan.getTenKieuThanhToan());
        }
    }

    public void showDetailHD(int index) {
        LocalDateTime now = LocalDateTime.now();
        if (index >= 0) { // Kiểm tra index hợp lệ
            BHHDViewModel hd1 = listHD.get(index);

            // Truy xuất thông tin khách hàng từ hóa đơn
            if (hd1.getSoDT() != null && hd1.getTenKH() != null) {
                txtSoDT.setText(hd1.getSoDT());
                txtTenKH.setText(hd1.getTenKH());
                txtTenKH2.setText(hd1.getTenKH());
            } else {
                txtSoDT.setText("");
                txtTenKH.setText("");
                txtTenKH2.setText("");
            }

            // Hiển thị thông tin hóa đơn
            txtMaHD.setText(hd1.getMaHD());
            txtNTao.setText(hd1.getNgayTao().format(formatter));
            txtNTToan.setText(now.format(formatter));
            txtMaNV.setText(hd1.getMaNV());
            txtTongTien.setText(String.valueOf(currencyFormat.format(hd1.getTongTien())));
            lbTong.setText(String.valueOf(currencyFormat.format(hd1.getTongTien())));
            // ComboBox của Phương thức thanh toán
            Object selectedTT = hd1.getPhuongThucThanhToan() != null ? hd1.getPhuongThucThanhToan().toString() : null;
            cbbTT.setSelectedItem(selectedTT);

// Hiển thị phiếu giảm giá hợp lệ nếu cbbPGG chưa được chọn
            if (!isCbbPGGSelected && cbbPGG.getSelectedItem() == null) {
                double tongTien = hd1.getTongTien();
                hienThiPhieuGiamGiaHopLe(tongTien); // Hiển thị các phiếu giảm giá hợp lệ cho hóa đơn
            }

        } else {
            // Reset các trường thông tin nếu index không hợp lệ
            txtSoDT.setText("");
            txtTenKH.setText("");
            txtTenKH2.setText("");
            cbbPGG.setSelectedItem(null);
            txtMaHD.setText("");
            txtNTao.setText("");
            txtNTToan.setText("");
            txtMaNV.setText("");
            txtTongTien.setText("");
            lbTong.setText("");
            cbbTT.setSelectedIndex(0);
        }
    }

    public void showDetailHDGH(int index) {
        if (index >= 0) { // Kiểm tra index hợp lệ
            BHHDViewModel hd1 = listHD.get(index);

            // Truy xuất thông tin khách hàng từ hóa đơn
            if (hd1.getSoDT() != null && hd1.getTenKH() != null && hd1.getDiaChi() != null) {
                txtSoDTDH.setText(hd1.getSoDT());
                txtTenKHDH.setText(hd1.getTenKH());
                txtDiaChi.setText(hd1.getDiaChi());
            } else {
                txtSoDTDH.setText("");
                txtTenKHDH.setText("");
                txtDiaChi.setText("");
            }

            // Hiển thị thông tin hóa đơn
            txtMaHDDH.setText(hd1.getMaHD());
            txtMaNVDH.setText(hd1.getMaNV());
            txtTongTienDH.setText(String.valueOf(currencyFormat.format(hd1.getTongTien())));
            lbTongDH.setText(String.valueOf(currencyFormat.format(hd1.getTongTien())));
            // ComboBox của Phương thức thanh toán
            Object selectedTT = hd1.getPhuongThucThanhToan() != null ? hd1.getPhuongThucThanhToan().toString() : null;
            cbbTTDH.setSelectedItem(selectedTT);

// Hiển thị phiếu giảm giá hợp lệ nếu cbbPGG chưa được chọn
            if (!isCbbPGGSelected && cbbPGG.getSelectedItem() == null) {
                double tongTien = hd1.getTongTien();
                hienThiPhieuGiamGiaHopLe(tongTien); // Hiển thị các phiếu giảm giá hợp lệ cho hóa đơn
            }

        } else {
            // Reset các trường thông tin nếu index không hợp lệ
            txtSoDTDH.setText("");
            txtTenKHDH.setText("");
            cbbPGG2.setSelectedItem(null);
            txtMaHDDH.setText("");
            txtMaNVDH.setText("");
            txtTongTienDH.setText("");
            lbTongDH.setText("");
            cbbTTDH.setSelectedIndex(0);
        }
    }

    //TinhTienThua
    private void tinhTienThua() {
        if (indexTab == 0) {
            try {
                // Lấy tổng tiền từ label và tiền khách đưa từ text field
                String tongText = lbTong.getText().trim();
                String tienDuaText = txtTienDua.getText().trim();
                String tienCKText = txtTienCK.getText().trim();

                // Kiểm tra xem cả ba giá trị có khả dụng để tính toán không
                if (!tongText.isEmpty()) {
                    // Chuyển đổi chuỗi thành số hợp lệ bằng cách sử dụng NumberFormat
                    NumberFormat format = NumberFormat.getInstance();
                    double tongTien = format.parse(tongText).doubleValue();
                    double tienKhachDua = !tienDuaText.isEmpty() ? format.parse(tienDuaText).doubleValue() : 0;
                    double tienChuyenKhoan = !tienCKText.isEmpty() ? format.parse(tienCKText).doubleValue() : 0;

                    // Tính toán tiền thừa và hiển thị trên text field
                    double tienThua = tienKhachDua + tienChuyenKhoan - tongTien;
                    tienKhachThua = tienThua;
                    txtTienThua.setText(currencyFormat.format(tienThua));
                }
            } catch (ParseException ex) {
                // Xử lý ngoại lệ nếu không thể chuyển đổi chuỗi thành số hợp lệ
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá trị số hợp lệ.");
            }
        } else if (indexTab == 1) {
            try {
                // Lấy tổng tiền từ label và tiền khách đưa từ text field
                String tongText = lbTongDH.getText().trim();
                String tienDuaText = txtTienDuaDH.getText().trim();
                String tienCKText = txtTienCKDH.getText().trim();

                // Kiểm tra xem cả ba giá trị có khả dụng để tính toán không
                if (!tongText.isEmpty()) {
                    // Chuyển đổi chuỗi thành số hợp lệ bằng cách sử dụng NumberFormat
                    NumberFormat format = NumberFormat.getInstance();
                    double tongTien = format.parse(tongText).doubleValue();
                    double tienKhachDua = !tienDuaText.isEmpty() ? format.parse(tienDuaText).doubleValue() : 0;
                    double tienChuyenKhoan = !tienCKText.isEmpty() ? format.parse(tienCKText).doubleValue() : 0;

                    // Tính toán tiền thừa và hiển thị trên text field
                    double tienThua = tienKhachDua + tienChuyenKhoan - tongTien;
                    tienKhachThua = tienThua;
                    txtTienThuaDH.setText(currencyFormat.format(tienThua));
                }
            } catch (ParseException ex) {
                // Xử lý ngoại lệ nếu không thể chuyển đổi chuỗi thành số hợp lệ
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá trị số hợp lệ.");
            }
        }

    }

    @Override
    public void khachHangSelected(String sodt, String tenKhachHang, String diaChi) {
        if (indexTab == 0) {
            txtSoDT.setText(sodt);
            txtTenKH.setText(tenKhachHang);
            txtTenKH2.setText(tenKhachHang);
        } else if (indexTab == 1) {
            txtSoDTDH.setText(sodt);
            txtTenKHDH.setText(tenKhachHang);
            txtDiaChi.setText(diaChi);
        }
    }

    public boolean checkKHHD() {
        if (txtSoDT.getText().trim().isEmpty()) {
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

    public boolean checkKHDH() {
        if (txtSoDTDH.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mã khách hàng trống");
            return false;
        }
        if (txtTenKHDH.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên khách hàng trống");
            return false;
        }

        if (txtMaHDDH.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mã hóa đơn trống");
            return false;
        }

        if (txtMaNVDH.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mã nhân viên trống");
            return false;
        }

        if (txtDiaChi.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Địa chỉ trống");
            return false;
        }

        if (jdcNgayNhan.getDate() == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày bắt đầu không hợp lệ");
            return false;
        }

        LocalDate ngayNhan = jdcNgayNhan.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayHomNay = LocalDate.now();

        if (ngayNhan.isBefore(ngayHomNay)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày nhận không được nhỏ hơn ngày hôm nay");
            return false;
        }

        if (txtTongTienDH.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tổng tiền trống");
            return false;
        }

        if (cbbPGG2.getSelectedItem() == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Chưa chọn phiếu giảm giás");
            return false;
        }

        if (txtTienDuaDH.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tiền đưa trống");
            return false;
        }

        if (txtTienThuaDH.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tiền thừa trống");
            return false;
        }
        return true;
    }

    public void processQRCodeValue(String qrCodeValue) {
        System.out.println("Received QR Code value in BanHang: " + qrCodeValue);
        int idSPCT = Integer.valueOf(qrCodeValue);
        listSP = srBH.getOneSP(idSPCT);
        showDataSP(listSP);
    }

    public void searchSP() {
        listSP = srBH.searchSP(txtSearch.getText().trim());
        showDataSP(listSP);
    }

    public void searchHD() {
        listHD = srBH.searchHD(txtSearchHD.getText().trim());
        showDataHD(listHD);
        reset();
    }

    public void reset() {
        txtMaHD.setText("");
        dtmHDCT.setRowCount(0);
        txtSoDT.setText("");
        txtTenKH2.setText("");
        txtTenKH.setText("");
        txtNTao.setText("");
        txtNTToan.setText("");
        txtTienDua.setText("");
        txtTongTien.setText("0 VND");
        lbTong.setText("0 VND");
        cbbPGG.setSelectedItem(null);
        txtTienThua.setText("");
        cbbTT.setSelectedIndex(0);
    }

    private void filterCTSP() {

        String danhMuc = (String) cbbDanhMuc.getSelectedItem();
        String xuatXu = (String) cbbXxu.getSelectedItem();
        String nsx = (String) cbbNSX.getSelectedItem();

        // Kiểm tra xem giá trị được chọn từ combobox có null không
        boolean sapXepGiaTangDan = "Giá từ thấp đến cao".equals(cbbGia.getSelectedItem());

        listSP = srBH.searchCBBSP(danhMuc, xuatXu, nsx, sapXepGiaTangDan);
        showDataSP(listSP);
    }

    private void hienThiPhieuGiamGiaHopLe(double tongTienHoaDon) {
        if (indexTab == 0) {
            // Xóa tất cả các phần tử cũ trong combo box
            dcbmPGG.removeAllElements();

            BHHDViewModel hd = listHD.get(selectedRowIndex);

            dcbmPGG.addElement(null);
            // Duyệt qua danh sách phiếu giảm giá
            for (PhieuGiamGiaViewModel phieuGiamGia : listPGG) {
                double hoaDonToiThieu = phieuGiamGia.getHoaDonToiThieu();

                // Nếu tổng tiền của hóa đơn lớn hơn hoặc bằng giá trị hóa đơn tối thiểu
                if (tongTienHoaDon >= hoaDonToiThieu) {
                    // Thêm tên phiếu giảm giá vào combo box
                    dcbmPGG.addElement(phieuGiamGia.getTenGiamGia());
                }
            }
        } else if (indexTab == 1) {
            // Xóa tất cả các phần tử cũ trong combo box
            dcbmPGG2.removeAllElements();

            BHHDViewModel hd = listHD.get(selectedRowIndex);

            dcbmPGG2.addElement(null);
            // Duyệt qua danh sách phiếu giảm giá
            for (PhieuGiamGiaViewModel phieuGiamGia : listPGG) {
                double hoaDonToiThieu = phieuGiamGia.getHoaDonToiThieu();

                // Nếu tổng tiền của hóa đơn lớn hơn hoặc bằng giá trị hóa đơn tối thiểu
                if (tongTienHoaDon >= hoaDonToiThieu) {
                    // Thêm tên phiếu giảm giá vào combo box
                    dcbmPGG2.addElement(phieuGiamGia.getTenGiamGia());
                }
            }
        }
    }

//    private void handlePGGSelection(Object selectedItem, JTextField txtTongTien, JLabel lbTong) {
//    int index = selectedRowIndex;
//    System.out.println("Đang xử lý phần tử thứ " + index);
//
//    if (index >= 0) {
//        BHHDViewModel hd1 = listHD.get(index);
//        String pgg = null;
//        if (selectedItem != null) {
//            pgg = selectedItem.toString();
//        }
//        double tongTien = hd1.getTongTien();
//
//        // Tính toán giảm giá dựa trên tên của phiếu giảm giá được chọn
//        double giamGia = 0.0;
//        double giamToiDa = 0.0;
//        if (pgg != null) {
//            // Tìm phần trăm và giảm tối đa từ danh sách phiếu giảm giá
//            for (PhieuGiamGiaViewModel phieuGiamGia : listPGG) {
//                if (phieuGiamGia.getTenGiamGia().equals(pgg)) {
//                    double phanTramGiam = phieuGiamGia.getSoPhanTramGiam() / 100.0;
//                    giamToiDa = phieuGiamGia.getGiamToiDa();
//                    giamGia = Math.min(tongTien * phanTramGiam, giamToiDa); // Lấy giá trị nhỏ nhất giữa giảm theo phần trăm và giảm tối đa
//                    break;
//                }
//            }
//        }
//
//        // Tính tổng tiền sau khi được giảm giá
//        double tongTienSauGiamGia = tongTien - giamGia;
//
//        // Hiển thị hộp thoại xác nhận trước khi áp dụng phiếu giảm giá
//        int option = JOptionPane.showConfirmDialog(null, "Phiếu giảm giả này có thể giảm tối đa " + currencyFormat.format(giamToiDa), "Xác nhận", JOptionPane.YES_NO_OPTION);
//        if (option == JOptionPane.YES_OPTION) {
//            // Áp dụng giảm giá và cập nhật tổng tiền
//            txtTongTien.setText(currencyFormat.format(tongTienSauGiamGia));
//            lbTong.setText(currencyFormat.format(tongTienSauGiamGia));
//        } else {
//            // Nếu người dùng chọn NO hoặc đóng hộp thoại, không thực hiện gì cả
//        }
//    }
//}
    @Override
    public void nhanVienSelected(String sdtChon, String tenNV) {
        txtTenNV.setText(tenNV);
        txtSDTNV.setText(sdtChon);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel2 = new javax.swing.JPanel();
        roundPanel1 = new swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        btnQR = new javax.swing.JButton();
        btnAddHD = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtSearchHD = new textfield.TextField();
        roundPanel2 = new swing.RoundPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGH = new javax.swing.JTable();
        roundPanel3 = new swing.RoundPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cbbDanhMuc = new combobox.Combobox();
        cbbXxu = new combobox.Combobox();
        cbbNSX = new combobox.Combobox();
        cbbGia = new combobox.Combobox();
        txtSearch = new textfield.TextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        roundPanel4 = new swing.RoundPanel();
        jLabel4 = new javax.swing.JLabel();
        roundPanel5 = new swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        txtSoDT = new javax.swing.JTextField();
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
        lbTong = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btnHuy = new javax.swing.JButton();
        btnTToan = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cbbTT = new javax.swing.JComboBox<>();
        txtMaHD = new javax.swing.JTextField();
        txtNTao = new javax.swing.JTextField();
        txtNTToan = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        txtTenKH2 = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        txtTienDua = new javax.swing.JTextField();
        txtTienCK = new javax.swing.JTextField();
        txtTienThua = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        roundPanel10 = new swing.RoundPanel();
        jLabel41 = new javax.swing.JLabel();
        txtSoDTDH = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtTenKHDH = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        btnChonKHDH = new javax.swing.JButton();
        roundPanel11 = new swing.RoundPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        cbbPGG2 = new javax.swing.JComboBox<>();
        lbTongDH = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        btnTToanDH = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        cbbTTDH = new javax.swing.JComboBox<>();
        txtMaHDDH = new javax.swing.JTextField();
        txtMaNVDH = new javax.swing.JTextField();
        txtTongTienDH = new javax.swing.JTextField();
        txtTienDuaDH = new javax.swing.JTextField();
        txtTienCKDH = new javax.swing.JTextField();
        txtTienThuaDH = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jdcNgayNhan = new com.toedter.calendar.JDateChooser();
        roundPanel12 = new swing.RoundPanel();
        jLabel46 = new javax.swing.JLabel();
        txtSDTNV = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        btnChonNV = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1600, 900));

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
        btnQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQRActionPerformed(evt);
            }
        });

        btnAddHD.setText("Tạo hóa đơn");
        btnAddHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddHDActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setText("Hóa đơn");

        txtSearchHD.setLabelText("Tìm kiếm hóa đơn");

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
                            .addComponent(jLabel1)
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(btnQR)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddHD)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtSearchHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(txtSearchHD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        tblGH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Mã SPCT", "Tên SP", "Màu sắc", "Size", "Chất liệu", "Thương Hiệu", "Số lượng", "Giá bán", "Thành tiền"
            }
        ));
        jScrollPane2.setViewportView(tblGH);

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
        cbbDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbDanhMucActionPerformed(evt);
            }
        });

        cbbXxu.setLabeText("Xuất xứ");
        cbbXxu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbXxuActionPerformed(evt);
            }
        });

        cbbNSX.setLabeText("NSX");
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
                            .addGroup(roundPanel3Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbXxu, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 391, Short.MAX_VALUE))
                            .addComponent(jScrollPane3))
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
                    .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addComponent(roundPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setText("Đơn hàng");

        roundPanel5.setBackground(new java.awt.Color(3, 6, 55));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Thông tin khách hàng");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Số điện thoại");

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
                                    .addComponent(txtSoDT, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)))
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
                    .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jLabel8.setText("Thông tin hóa đơn");

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

        cbbPGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbPGGActionPerformed(evt);
            }
        });
        cbbPGG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbbPGGKeyReleased(evt);
            }
        });

        lbTong.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbTong.setForeground(new java.awt.Color(255, 51, 51));
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

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tiền CK");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Hình thức thanh toán");

        cbbTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtTongTien.setForeground(new java.awt.Color(255, 51, 51));
        txtTongTien.setText("0 VND");

        txtTienDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienDuaKeyReleased(evt);
            }
        });

        txtTienCK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienCKKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout roundPanel6Layout = new javax.swing.GroupLayout(roundPanel6);
        roundPanel6.setLayout(roundPanel6Layout);
        roundPanel6Layout.setHorizontalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(btnHuy)
                .addGap(68, 68, 68)
                .addComponent(btnTToan)
                .addGap(42, 42, 42)
                .addComponent(jLabel22)
                .addGap(0, 27, Short.MAX_VALUE))
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundPanel6Layout.createSequentialGroup()
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel6Layout.createSequentialGroup()
                                            .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel10)
                                                .addComponent(jLabel9)
                                                .addComponent(jLabel11)
                                                .addComponent(jLabel14))
                                            .addGap(34, 34, 34))
                                        .addGroup(roundPanel6Layout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addGap(39, 39, 39)))
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel21)))
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel17)))
                        .addGap(10, 10, 10)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbbPGG, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                .addComponent(txtTenKH2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNTToan, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNTao, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaHD, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(cbbTT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTienDua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTienCK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(roundPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8)
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(6, 6, 6)
                                .addComponent(lbTong)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel6Layout.setVerticalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnHuy)
                        .addComponent(btnTToan))
                    .addGroup(roundPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(16, 16, 16)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtNTToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtTenKH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbbPGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbbTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTienDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtTienCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTong, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(roundPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(roundPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tại Quầy", roundPanel4);

        jLabel40.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel40.setText("Đơn hàng");

        roundPanel10.setBackground(new java.awt.Color(3, 6, 55));

        jLabel41.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Thông tin khách hàng");

        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Số điện thoại");

        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Tên khách hàng");

        btnChonKHDH.setText("Chọn");
        btnChonKHDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHDHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel10Layout = new javax.swing.GroupLayout(roundPanel10);
        roundPanel10.setLayout(roundPanel10Layout);
        roundPanel10Layout.setHorizontalGroup(
            roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel10Layout.createSequentialGroup()
                .addGroup(roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel43))
                    .addGroup(roundPanel10Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel42)
                            .addComponent(txtSoDTDH, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChonKHDH))
                    .addGroup(roundPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtTenKHDH, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        roundPanel10Layout.setVerticalGroup(
            roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel10Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel41)
                .addGap(12, 12, 12)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoDTDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChonKHDH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKHDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        roundPanel11.setBackground(new java.awt.Color(54, 84, 134));

        jLabel44.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Thông tin hóa đơn");

        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Mã hóa đơn");

        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Tổng tiền");

        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Mã nhân viên");

        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Phiếu giảm giá");

        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Tiền khách đưa");

        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Tiền thừa");

        jLabel54.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Tổng: ");

        lbTongDH.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbTongDH.setForeground(new java.awt.Color(255, 51, 51));
        lbTongDH.setText("0 VND");

        jLabel55.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));

        btnTToanDH.setText("Giao hàng");
        btnTToanDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTToanDHActionPerformed(evt);
            }
        });

        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Tiền CK");

        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Hình thức thanh toán");

        cbbTTDH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtTongTienDH.setForeground(new java.awt.Color(255, 51, 51));
        txtTongTienDH.setText("0 VND");

        txtTienDuaDH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienDuaDHKeyReleased(evt);
            }
        });

        txtTienCKDH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienCKDHKeyReleased(evt);
            }
        });

        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Địa chỉ");

        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Ngày nhận");

        javax.swing.GroupLayout roundPanel11Layout = new javax.swing.GroupLayout(roundPanel11);
        roundPanel11.setLayout(roundPanel11Layout);
        roundPanel11Layout.setHorizontalGroup(
            roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel11Layout.createSequentialGroup()
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel11Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel44))
                    .addGroup(roundPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel51))
                    .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(roundPanel11Layout.createSequentialGroup()
                            .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(roundPanel11Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jLabel45))
                                .addGroup(roundPanel11Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel50)))
                            .addGap(61, 61, 61)
                            .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbbPGG2, javax.swing.GroupLayout.Alignment.LEADING, 0, 149, Short.MAX_VALUE)
                                .addComponent(txtMaNVDH, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaHDDH, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGroup(roundPanel11Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(roundPanel11Layout.createSequentialGroup()
                                    .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel48)
                                        .addComponent(jLabel58)
                                        .addComponent(jLabel59))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtTongTienDH, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                        .addComponent(jdcNgayNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(roundPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel52)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTienDuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(roundPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel57)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                    .addComponent(cbbTTDH, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(roundPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel56)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTienCKDH, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel54)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbTongDH)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(roundPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel53)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTienThuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel55)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(roundPanel11Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(btnTToanDH)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel11Layout.setVerticalGroup(
            roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel11Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel44)
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel11Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(txtMaHDDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaNVDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbPGG2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jdcNgayNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongTienDH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbTTDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienDuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addGap(18, 18, 18)
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienCKDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addGap(0, 21, Short.MAX_VALUE)
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienThuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addGap(15, 15, 15)
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTongDH, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(btnTToanDH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        roundPanel12.setBackground(new java.awt.Color(3, 6, 55));

        jLabel46.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Nhân viên vận chuyển");

        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Số điện thoại");

        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Tên nhân viên");

        btnChonNV.setText("Chọn");
        btnChonNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel12Layout = new javax.swing.GroupLayout(roundPanel12);
        roundPanel12.setLayout(roundPanel12Layout);
        roundPanel12Layout.setHorizontalGroup(
            roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel12Layout.createSequentialGroup()
                .addGroup(roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel49))
                    .addGroup(roundPanel12Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addComponent(jLabel47)
                            .addComponent(txtSDTNV, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChonNV))
                    .addGroup(roundPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel12Layout.setVerticalGroup(
            roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel12Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel46)
                .addGap(12, 12, 12)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDTNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChonNV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Đặt Hàng", jPanel1);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setText("Giỏ hàng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roundPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbXxuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbXxuActionPerformed
        String selectedXuatXu = (String) cbbXxu.getSelectedItem();
        if (selectedXuatXu == "Tất cả" || selectedXuatXu.isEmpty()) {
            // Nếu không có danh mục nào được chọn, lấy tất cả dữ liệu
            showDataSP(listSP);
        } else {
            // Ngược lại, lấy dữ liệu từ danh mục đã chọn
            filterCTSP();
        }
    }//GEN-LAST:event_cbbXxuActionPerformed

    private void btnAddHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddHDActionPerformed
        int answer = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm hóa đơn?");
        String hanhDong = "Tạo hóa đơn mới";
        if (answer == JOptionPane.YES_OPTION) {
            if (listHD.size() < 10) {
                srHD.addHD(trangThai);
                listHD = srBH.getHD();
                showDataHD(listHD);
                srLSHD.addLSHD(hanhDong);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã thêm");
                reset();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Bạn chỉ được tạo tối đa 10 hóa đơn!");
            }
        } else {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy");
        }
    }//GEN-LAST:event_btnAddHDActionPerformed

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        int rowIndex = tblHD.getSelectedRow();

        // Kiểm tra xem rowIndex có hợp lệ không
        if (rowIndex >= 0 && rowIndex < listHD.size()) {
            BHHDViewModel hd = listHD.get(rowIndex);

            selectedRowIndex = rowIndex;

            dtmHDCT = (DefaultTableModel) tblGH.getModel();
            listHDCT = srHDCT.getAll(hd.getId());
            showDataHDCT(listHDCT);

            // Hiển thị chi tiết hóa đơn tương ứng với hàng được chọn
            if (indexTab == 0) {
                showDetailHD(rowIndex);
            } else if (indexTab == 1) {
                showDetailHDGH(rowIndex);
            }
        } else {
            // Nếu rowIndex không hợp lệ, xử lý tương tự khi không có hóa đơn nào được chọn
            selectedRowIndex = -1;
            dtmHDCT.setRowCount(0);
            if (indexTab == 0) {
                showDetailHD(-1); // Gọi showDetailHD với chỉ số -1 để xóa thông tin chi tiết
            } else if (indexTab == 1) {
                showDetailHDGH(-1); // Gọi showDetailHDGH với chỉ số -1 để xóa thông tin chi tiết
            }
            // Xóa tất cả các phần tử trong combo box khi không có hóa đơn nào được chọn
            dcbmPGG.removeAllElements();
            dcbmPGG2.removeAllElements();
        }
    }//GEN-LAST:event_tblHDMouseClicked

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked
// Lấy chỉ mục của hàng được chọn trong bảng hóa đơn
        int rowIndexHD = selectedRowIndex;
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
                    try {
                        // Chuyển đổi chuỗi nhập vào thành số nguyên
                        int soLuongMua = Integer.parseInt(input);

                        // Kiểm tra xem số lượng mua có lớn hơn 0 không
                        if (soLuongMua > 0) {
                            // Kiểm tra xem số lượng mua có lớn hơn số lượng hiện có không
                            if (soLuongMua > soLuongHienCo) {
                                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số lượng mua vượt quá số lượng hiện có!");
                            } else {
                                // Tiếp tục xử lý
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

                                if (indexTab == 0) {
                                    showDetailHD(rowIndexHD);
                                } else if (indexTab == 1) {
                                    showDetailHDGH(rowIndexHD);
                                }

                                System.out.println("idHD: " + idHD);
                                System.out.println("idSPCT: " + idSPCT);
                                System.out.println("soLuongmua: " + soLuongMua);
                                System.out.println("giaBan: " + giaBan);
                                // Hiển thị kết quả hoặc thực hiện các hành động khác với kết quả này
                                System.out.println("Số lượng còn lại:" + soLuongConLai);
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã thêm sản phẩm vào giỏ hàng!");
                            }
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số lượng mua phải lớn hơn 0!");
                        }
                    } catch (NumberFormatException e) {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Vui lòng nhập vào một số nguyên dương!");
                    }
                }
            }
        }

    }//GEN-LAST:event_tblSPMouseClicked

    private void btnQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQRActionPerformed
        quetQR quet = new quetQR();
        quet.setQRCodeListener(new quetQR.codeQR() {
            @Override
            public void onQRCodeScanned(int result) {
                System.out.println(result);
                listSP = srBH.getOneSP(result);
                showDataSP(listSP);

                quet.closeQRFrame();
            }
        });
        quet.openWebcam();
    }//GEN-LAST:event_btnQRActionPerformed

    private void cbbNSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNSXActionPerformed
        String selectedNSX = (String) cbbNSX.getSelectedItem();
//        String selectedGia = (String) cbbGia.getSelectedItem();
//        List<SanPhamChiTietViewModel> data;
//
        if (selectedNSX == "Tất cả" || selectedNSX.isEmpty()) {
            // Nếu không có danh mục nào được chọn, lấy tất cả dữ liệu
            showDataSP(listSP);
        } else {
            // Ngược lại, lấy dữ liệu từ danh mục đã chọn
            filterCTSP();
        }
    }//GEN-LAST:event_cbbNSXActionPerformed

    private void cbbDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbDanhMucActionPerformed
        String selectedDanhMuc = (String) cbbDanhMuc.getSelectedItem();
        if (selectedDanhMuc == "Tất cả" || selectedDanhMuc.isEmpty()) {
            // Nếu không có danh mục nào được chọn, lấy tất cả dữ liệu
            showDataSP(listSP);
        } else {
            // Ngược lại, lấy dữ liệu từ danh mục đã chọn
            filterCTSP();
        }
    }//GEN-LAST:event_cbbDanhMucActionPerformed

    private void cbbGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGiaActionPerformed
        String selectedGia = (String) cbbGia.getSelectedItem();
//
//        // Khởi tạo giá trị min và max mặc định để lấy tất cả sản phẩm
//        // Kiểm tra lựa chọn của combobox
        if (selectedGia.equals("Tất cả")) {
            // Nếu chọn "Tất cả", hiển thị tất cả sản phẩm
            showDataSP(listSP);
        } else if (selectedGia.equals("Giá từ thấp đến cao")) {
            // Nếu chọn "Giá từ thấp đến cao", sắp xếp dữ liệu theo giá tăng dần
            filterCTSP();
        } else if (selectedGia.equals("Giá từ cao đến thấp")) {
            // Nếu chọn "Giá từ cao đến thấp", sắp xếp dữ liệu theo giá giảm dần
            filterCTSP();
        } else {
            // Xử lý các trường hợp còn lại (nếu có)
        }
    }//GEN-LAST:event_cbbGiaActionPerformed

    private void btnTToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTToanActionPerformed
        if (checkKHHD()) {
            int rowIndexHD = selectedRowIndex;
            if (rowIndexHD >= 0) {
                BHHDViewModel hd = listHD.get(rowIndexHD);
                int idHD = hd.getId();
                String maNV = txtMaNV.getText().trim();
                String soDT = txtSoDT.getText().trim();
                String maGG = cbbPGG.getSelectedItem().toString();

                String tenKTT = "";
                double chuyenKhoan = 0.0;
                double tienMat = 0.0;
                if (cbbTT.getSelectedItem() != null) {
                    tenKTT = cbbTT.getSelectedItem().toString();
                }
                if (!txtTienCK.getText().isEmpty()) {
                    chuyenKhoan = Double.parseDouble(txtTienCK.getText().trim());
                }
                if (!txtTienDua.getText().isEmpty()) {
                    tienMat = Double.parseDouble(txtTienDua.getText().trim());
                }

                // Kiểm tra xem tiền thừa có lớn hơn hoặc bằng 0 không
                if (tienKhachThua >= 0) {
                    System.out.println("Mã nhân viên: " + maNV);
                    System.out.println("Mã KH: " + soDT);
                    System.out.println("Mã GG: " + maGG);
                    srHD.updateHD(idHD, maNV, soDT, maGG);
                    listHD = srBH.getHD();
                    showDataHD(listHD);
                    dtmHDCT = (DefaultTableModel) tblGH.getModel();
                    dtmHDCT.setRowCount(0);

                    srHTTT.addHTTT(idHD, tenKTT, chuyenKhoan, tienMat);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã thanh toán thành công");
                    reset();
                } else {
                    // Hiển thị thông báo lỗi nếu tiền thừa là số âm
                    JOptionPane.showMessageDialog(this, "Tiền thừa không đủ để thanh toán.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnTToanActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        int index = selectedRowIndex;
        if (index >= 0) {
            BHHDViewModel hd = listHD.get(index);
            srQLHD.deleteHD(hd.getId());
            listHD = srBH.getHD();
            showDataHD(listHD);

            dtmHDCT = (DefaultTableModel) tblGH.getModel();
            dtmHDCT.setRowCount(0);

            showDetailHD(index); // Truyền index vào phương thức
            showDetailHDGH(index);

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy thành công");
            reset(); 
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Vui lòng chọn hóa đơn để hủy");
        }
    }//GEN-LAST:event_btnHuyActionPerformed

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

    private void btnChonKHDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHDHActionPerformed
        ThemKhachHang panel = new ThemKhachHang();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Đăng ký lắng nghe sự kiện chọn khách hàng từ ThemKhachHang
        panel.addKhachHangSelectedListener(this);

        frame.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//GEN-LAST:event_btnChonKHDHActionPerformed

    private void btnTToanDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTToanDHActionPerformed
        if (checkKHDH()) {
            int rowIndexHD = selectedRowIndex;
            if (rowIndexHD >= 0) {
                BHHDViewModel hd = listHD.get(rowIndexHD);
                int idHD = hd.getId();
                String maNV = txtMaNVDH.getText().trim();
                String soDT = txtSoDTDH.getText().trim();
                String maGG = cbbPGG2.getSelectedItem().toString();
                Date ngayNhan = jdcNgayNhan.getDate();

                String tenKTT = "";
                double chuyenKhoan = 0.0;
                double tienMat = 0.0;
                if (cbbTTDH.getSelectedItem() != null) {
                    tenKTT = cbbTTDH.getSelectedItem().toString();
                }
                if (!txtTienCKDH.getText().isEmpty()) {
                    chuyenKhoan = Double.parseDouble(txtTienCKDH.getText().trim());
                }
                if (!txtTienDuaDH.getText().isEmpty()) {
                    tienMat = Double.parseDouble(txtTienDuaDH.getText().trim());
                }

                // Kiểm tra xem tiền thừa có lớn hơn hoặc bằng 0 không
                if (tienKhachThua >= 0) {
                    System.out.println("Mã nhân viên: " + maNV);
                    System.out.println("Mã KH: " + soDT);
                    System.out.println("Mã GG: " + maGG);
                    srHD.giaoHang(idHD, maNV, soDT, maGG, ngayNhan);
                    listHD = srBH.getHD();
                    showDataHD(listHD);
                    dtmHDCT = (DefaultTableModel) tblGH.getModel();
                    dtmHDCT.setRowCount(0);

                    srHTTT.addHTTT(idHD, tenKTT, chuyenKhoan, tienMat);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã thanh toán thành công");
                } else {
                    // Hiển thị thông báo lỗi nếu tiền thừa là số âm
                    JOptionPane.showMessageDialog(this, "Tiền thừa không đủ để thanh toán.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnTToanDHActionPerformed

    private void btnChonNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonNVActionPerformed
        ChonNVVanChuyen panel = new ChonNVVanChuyen();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Đăng ký lắng nghe sự kiện chọn khách hàng từ ThemKhachHang
        panel.addNhanVienSelectedListener(this);

        frame.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//GEN-LAST:event_btnChonNVActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        if (jTabbedPane1.getSelectedIndex() == 1) { // Kiểm tra xem tab hiện tại có phải là tab đặt hàng không
            trangThai = "Chờ Giao"; // Cập nhật trạng thái
            indexTab = 1;
        } else if (jTabbedPane1.getSelectedIndex() == 0) {
            trangThai = "Chưa Thanh Toán";
            indexTab = 0;
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void cbbPGGKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbbPGGKeyReleased

    }//GEN-LAST:event_cbbPGGKeyReleased

    private void cbbPGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbPGGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbPGGActionPerformed

    private void txtTienDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienDuaKeyReleased
        tinhTienThua();
    }//GEN-LAST:event_txtTienDuaKeyReleased

    private void txtTienCKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienCKKeyReleased
        tinhTienThua();
    }//GEN-LAST:event_txtTienCKKeyReleased

    private void txtTienDuaDHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienDuaDHKeyReleased
        tinhTienThua();
    }//GEN-LAST:event_txtTienDuaDHKeyReleased

    private void txtTienCKDHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienCKDHKeyReleased
        tinhTienThua();
    }//GEN-LAST:event_txtTienCKDHKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddHD;
    private javax.swing.JButton btnChonKHDH;
    private javax.swing.JButton btnChonKh;
    private javax.swing.JButton btnChonNV;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btnTToan;
    private javax.swing.JButton btnTToanDH;
    private combobox.Combobox cbbDanhMuc;
    private combobox.Combobox cbbGia;
    private combobox.Combobox cbbNSX;
    private javax.swing.JComboBox<String> cbbPGG;
    private javax.swing.JComboBox<String> cbbPGG2;
    private javax.swing.JComboBox<String> cbbTT;
    private javax.swing.JComboBox<String> cbbTTDH;
    private combobox.Combobox cbbXxu;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdcNgayNhan;
    private javax.swing.JLabel lbTong;
    private javax.swing.JLabel lbTongDH;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel10;
    private swing.RoundPanel roundPanel11;
    private swing.RoundPanel roundPanel12;
    private swing.RoundPanel roundPanel2;
    private swing.RoundPanel roundPanel3;
    private swing.RoundPanel roundPanel4;
    private swing.RoundPanel roundPanel5;
    private swing.RoundPanel roundPanel6;
    private javax.swing.JTable tblGH;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaHDDH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaNVDH;
    private javax.swing.JTextField txtNTToan;
    private javax.swing.JTextField txtNTao;
    private javax.swing.JTextField txtSDTNV;
    private textfield.TextField txtSearch;
    private textfield.TextField txtSearchHD;
    private javax.swing.JTextField txtSoDT;
    private javax.swing.JTextField txtSoDTDH;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenKH2;
    private javax.swing.JTextField txtTenKHDH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTienCK;
    private javax.swing.JTextField txtTienCKDH;
    private javax.swing.JTextField txtTienDua;
    private javax.swing.JTextField txtTienDuaDH;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTienThuaDH;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTongTienDH;
    // End of variables declaration//GEN-END:variables

}
