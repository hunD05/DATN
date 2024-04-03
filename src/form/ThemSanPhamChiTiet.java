/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

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
import backend.service.ChatLieuService;
import backend.service.ChiTietSanPhamService;
import backend.service.CoAoService;
import backend.service.DangAoService;
import backend.service.DanhMucService;
import backend.service.DuoiAoService;
import backend.service.MauSacService;
import backend.service.NSXService;
import backend.service.SanPhamCBBService;
import backend.service.SizeService;
import backend.service.TayAoService;
import backend.service.ThuonHieuService;
import backend.service.XuatXuService;
import backend.viewmodel.SanPhamChiTietViewModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import raven.application.Application;
import raven.toast.Notifications;

/**
 *
 * @author leanb
 */
public class ThemSanPhamChiTiet extends javax.swing.JPanel {

    /**
     * Creates new form ThuocTinhSanPham
     */
    List<SanPhamChiTietViewModel> chiTietSanPhams = new ArrayList<>();
    ChiTietSanPhamService chiTietSanPhamService = new ChiTietSanPhamService();

    DefaultComboBoxModel dcbmDanhMuc = new DefaultComboBoxModel();
    List<DanhMuc> danhMucs = new ArrayList<>();
    DanhMucService danhMucService = new DanhMucService();

    DefaultComboBoxModel dcbmNSX = new DefaultComboBoxModel();
    List<NSX> nsx = new ArrayList<>();
    NSXService nsxs = new NSXService();

    DefaultComboBoxModel dcbmXuatXu = new DefaultComboBoxModel();
    List<XuatXu> xuatXus = new ArrayList<>();
    XuatXuService xuatXuService = new XuatXuService();

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

    public ThemSanPhamChiTiet() {
        initComponents();
        chiTietSanPhams = chiTietSanPhamService.getAll();

        danhMucs = danhMucService.getAll();
        dcbmDanhMuc = (DefaultComboBoxModel) cbbDanhMuc.getModel();
        showcbbDanhMuc(danhMucs);

        nsx = nsxs.getAll();
        dcbmNSX = (DefaultComboBoxModel) cbbNSX.getModel();
        showcbbNSX(nsx);

        xuatXus = xuatXuService.getAll();
        dcbmXuatXu = (DefaultComboBoxModel) cbbXuatXu.getModel();
        showcbbXuatXu(xuatXus);

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
        dcbmTayAo = (DefaultComboBoxModel) CbbTayAo.getModel();
        showcbbTayAo(tayAos);

        dangAo = dangAoService.getAll();
        dcbmDangAo = (DefaultComboBoxModel) cbbDangAo.getModel();
        showcbbDangAo(dangAo);

        sanPhams = sanPhamCBBService.getAllCBB();
        dcbmSanPham = (DefaultComboBoxModel) cbbSanPham.getModel();
        showcbbSanPham(sanPhams);
        
        addDefaultItemsToComboBox(cbbTrangThai);
    }

    public void showcbbDanhMuc(List<DanhMuc> danhMucs) {
        dcbmDanhMuc.removeAllElements();
        for (DanhMuc danhMuc : danhMucs) {
            dcbmDanhMuc.addElement(danhMuc.getTenDanhMuc());
        }
    }

    public void showcbbNSX(List<NSX> nsxs) {
        dcbmNSX.removeAllElements();
        for (NSX nsx1 : nsxs) {
            dcbmNSX.addElement(nsx1.getTenNSX());
        }
    }

    public void showcbbXuatXu(List<XuatXu> xuatXus) {
        dcbmXuatXu.removeAllElements();
        for (XuatXu xuatXu : xuatXus) {
            dcbmXuatXu.addElement(xuatXu.getTenXuatXu());
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

    public void addDefaultItemsToComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems(); // Xóa các mục hiện tại trong combobox (nếu có)
        comboBox.addItem("Còn hàng"); // Thêm mục "Còn hàng"
        comboBox.addItem("Hết hàng"); // Thêm mục "Hết hàng"
    }

    public void showcbbSanPham(List<backend.entity.SanPham> sanPhams) {
        dcbmSanPham.removeAllElements();
        for (backend.entity.SanPham sanPham : sanPhams) {
            dcbmSanPham.addElement(sanPham.getTenSanPham());
        }
    }

    public MauSac getFormDataMauSac(JTextField txtTenThuocTinh) {

        String tenMauSac = txtTenThuocTinh.getText().trim();

        if (tenMauSac.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new MauSac( tenMauSac);
    }

    public ChatLieu getFormDataChatLieu(JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new ChatLieu( ten);
    }

    public XuatXu getFormDataXuatXu( JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new XuatXu(ten);
    }

    public Size getFormDataSize(JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new Size(ten);
    }

    public NSX getFormDataNSX(JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new NSX(ten);
    }

    public ThuongHieu getFormDataThuongHieu(JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new ThuongHieu( ten);
    }

    public DanhMuc getFormDataDanhMuc(JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new DanhMuc(ten);
    }

    public CoAo getFormDataCoAo(JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new CoAo(ten);
    }

    public DuoiAo getFormDataDuoiAo(JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new DuoiAo(ten);
    }

    public TayAo getFormDataTayAo(JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new TayAo(ten);
    }

    public DangAo getFormDataDangAo( JTextField txtTenThuocTinh) {

        String ten = txtTenThuocTinh.getText().trim();

        if (ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new DangAo( ten);
    }

    public backend.entity.ChiTietSanPham getFormData() {
        try {
            String sp = cbbSanPham.getSelectedItem().toString();
            BigDecimal gia = new BigDecimal(txtGia.getText());
            String soLuong = txtSoLuong.getText();
            String moTa = txtMoTa.getText();
            String danhMuc = cbbDanhMuc.getSelectedItem().toString();
            String xuatXu = cbbXuatXu.getSelectedItem().toString();
            String nsx = cbbNSX.getSelectedItem().toString();
            String mauSac = cbbMauSac.getSelectedItem().toString();
            String size = cbbSize.getSelectedItem().toString();
            String thuongHieu = cbbThuongHieu.getSelectedItem().toString();
            String chatLieu = cbbChatLieu.getSelectedItem().toString();
            String coAo = cbbCoAo.getSelectedItem().toString();
            String tayAo = CbbTayAo.getSelectedItem().toString();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        roundPanel2 = new swing.RoundPanel();
        roundPanel11 = new swing.RoundPanel();
        jPanel1 = new javax.swing.JPanel();
        cbbSanPham = new combobox.Combobox();
        cbbDanhMuc = new combobox.Combobox();
        cbbNSX = new combobox.Combobox();
        cbbXuatXu = new combobox.Combobox();
        cbbMauSac = new combobox.Combobox();
        cbbSize = new combobox.Combobox();
        cbbThuongHieu = new combobox.Combobox();
        cbbChatLieu = new combobox.Combobox();
        cbbCoAo = new combobox.Combobox();
        cbbDuoiAo = new combobox.Combobox();
        CbbTayAo = new combobox.Combobox();
        cbbDangAo = new combobox.Combobox();
        txtSoLuong = new textfield.TextField();
        txtGia = new textfield.TextField();
        txtMoTa = new textfield.TextField();
        btnAddMauSac = new javax.swing.JButton();
        btnAddCoAo = new javax.swing.JButton();
        btnAddDanhMuc = new javax.swing.JButton();
        btnAddSize = new javax.swing.JButton();
        btnAddDuoiAo = new javax.swing.JButton();
        btnAddNSX = new javax.swing.JButton();
        btnAddThuongHieu = new javax.swing.JButton();
        btnAddTayAo = new javax.swing.JButton();
        btnAddXuatXu = new javax.swing.JButton();
        btnAddChatLieu = new javax.swing.JButton();
        btnAddDangAo = new javax.swing.JButton();
        cbbTrangThai = new combobox.Combobox();
        btnTroLai = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();

        roundPanel2.setBackground(new java.awt.Color(239, 239, 239));
        roundPanel2.setForeground(new java.awt.Color(235, 235, 235));

        roundPanel11.setLayout(new java.awt.GridLayout(1, 0, 16, 0));

        jPanel1.setOpaque(false);

        cbbSanPham.setLabeText("Sản phẩm");

        cbbDanhMuc.setLabeText("Danh mục");

        cbbNSX.setLabeText("Nhà sản xuất");

        cbbXuatXu.setLabeText("Xuất xứ");

        cbbMauSac.setLabeText("Màu sắc");

        cbbSize.setLabeText("Size");

        cbbThuongHieu.setLabeText("Thương hiệu");

        cbbChatLieu.setLabeText("Chất liệu");

        cbbCoAo.setLabeText("Cổ áo");

        cbbDuoiAo.setLabeText("Đuôi áo");

        CbbTayAo.setLabeText("Tay áo");
        CbbTayAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbbTayAoActionPerformed(evt);
            }
        });

        cbbDangAo.setLabeText("Dáng áo");

        txtSoLuong.setLabelText("Số lượng");

        txtGia.setLabelText("Giá");

        txtMoTa.setLabelText("Mô tả");

        btnAddMauSac.setText("+");
        btnAddMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMauSacActionPerformed(evt);
            }
        });

        btnAddCoAo.setText("+");
        btnAddCoAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCoAoActionPerformed(evt);
            }
        });

        btnAddDanhMuc.setText("+");
        btnAddDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDanhMucActionPerformed(evt);
            }
        });

        btnAddSize.setText("+");
        btnAddSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSizeActionPerformed(evt);
            }
        });

        btnAddDuoiAo.setText("+");
        btnAddDuoiAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDuoiAoActionPerformed(evt);
            }
        });

        btnAddNSX.setText("+");
        btnAddNSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNSXActionPerformed(evt);
            }
        });

        btnAddThuongHieu.setText("+");
        btnAddThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddThuongHieuActionPerformed(evt);
            }
        });

        btnAddTayAo.setText("+");
        btnAddTayAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTayAoActionPerformed(evt);
            }
        });

        btnAddXuatXu.setText("+");
        btnAddXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddXuatXuActionPerformed(evt);
            }
        });

        btnAddChatLieu.setText("+");
        btnAddChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddChatLieuActionPerformed(evt);
            }
        });

        btnAddDangAo.setText("+");
        btnAddDangAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDangAoActionPerformed(evt);
            }
        });

        cbbTrangThai.setLabeText("Trạng thái");
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbbSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(cbbXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbbCoAo, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddCoAo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbbDuoiAo, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddDuoiAo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(CbbTayAo, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddTayAo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbDangAo, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addComponent(cbbTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddDangAo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddSize, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnAddNSX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAddXuatXu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbbXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(cbbSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbThuongHieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddThuongHieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbCoAo, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                            .addComponent(cbbDuoiAo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CbbTayAo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbDangAo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddCoAo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddDuoiAo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddTayAo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnAddDangAo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                            .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );

        btnTroLai.setText("Trở lại");
        btnTroLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroLaiActionPerformed(evt);
            }
        });

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Thêm Chi tiết sản phẩm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 363, 31, 361);
        jPanel2.add(jLabel1, gridBagConstraints);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(691, 691, 691)
                        .addComponent(roundPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                        .addComponent(btnTroLai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(201, 201, 201)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnTroLai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 237, Short.MAX_VALUE)
                .addComponent(roundPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTroLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTroLaiActionPerformed
        Application.showForm(new ChiTietSanPham());
    }//GEN-LAST:event_btnTroLaiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateFormData()) {
            chiTietSanPhamService.add(getFormData());
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm thành công");
        } else {
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void CbbTayAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbbTayAoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbbTayAoActionPerformed

    private void btnAddDangAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDangAoActionPerformed
        JFrame frame = new JFrame("Thêm Dáng áo");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                DangAo mauSac = getFormDataDangAo(txtTenThuocTinh);
                if (mauSac != null) {
                    dangAoService.add(mauSac);
                    dangAo = dangAoService.getAll();
                    showcbbDangAo(dangAo);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 140);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }//GEN-LAST:event_btnAddDangAoActionPerformed

    private void btnAddMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMauSacActionPerformed
        JFrame frame = new JFrame("Thêm Dáng áo");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                MauSac mauSac = getFormDataMauSac( txtTenThuocTinh);
                if (mauSac != null) {
                    MauSacService.add(mauSac);
                    mauSacs = MauSacService.getAll();
                    showcbbMauSac(mauSacs);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }//GEN-LAST:event_btnAddMauSacActionPerformed

    private void btnAddCoAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCoAoActionPerformed
        JFrame frame = new JFrame("Thêm Cổ áo");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                CoAo mauSac = getFormDataCoAo( txtTenThuocTinh);
                if (mauSac != null) {
                    coAoService.add(mauSac);
                    coAos = coAoService.getAll();
                    showcbbCoAo(coAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }//GEN-LAST:event_btnAddCoAoActionPerformed

    private void btnAddDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDanhMucActionPerformed
        JFrame frame = new JFrame("Thêm Danh mục");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                DanhMuc mauSac = getFormDataDanhMuc( txtTenThuocTinh);
                if (mauSac != null) {
                    danhMucService.add(mauSac);
                    danhMucs = danhMucService.getAll();
                    showcbbDanhMuc(danhMucs);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }//GEN-LAST:event_btnAddDanhMucActionPerformed

    private void btnAddSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSizeActionPerformed
        JFrame frame = new JFrame("Thêm Size");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                Size mauSac = getFormDataSize( txtTenThuocTinh);
                if (mauSac != null) {
                    sizeService.add(mauSac);
                    sizes = sizeService.getAll();
                    showcbbSize(sizes);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }//GEN-LAST:event_btnAddSizeActionPerformed

    private void btnAddDuoiAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDuoiAoActionPerformed
        JFrame frame = new JFrame("Thêm Đuôi áo");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                DuoiAo mauSac = getFormDataDuoiAo(txtTenThuocTinh);
                if (mauSac != null) {
                    duoiAoService.add(mauSac);
                    duoiAos = duoiAoService.getAll();
                    showcbbDuoiAo(duoiAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }//GEN-LAST:event_btnAddDuoiAoActionPerformed

    private void btnAddNSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNSXActionPerformed
        JFrame frame = new JFrame("Thêm NSX");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                NSX mauSac = getFormDataNSX(txtTenThuocTinh);
                if (mauSac != null) {
                    nsxs.add(mauSac);
                    nsx = nsxs.getAll();
                    showcbbNSX(nsx);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }//GEN-LAST:event_btnAddNSXActionPerformed

    private void btnAddThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddThuongHieuActionPerformed
        JFrame frame = new JFrame("Thêm Thương hiệu");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                ThuongHieu mauSac = getFormDataThuongHieu( txtTenThuocTinh);
                if (mauSac != null) {
                    thuongHieuService.add(mauSac);
                    thuongHieus = thuongHieuService.getAll();
                    showcbbThuongHieu(thuongHieus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }//GEN-LAST:event_btnAddThuongHieuActionPerformed

    private void btnAddTayAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTayAoActionPerformed
        JFrame frame = new JFrame("Thêm Tay Áo");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                TayAo mauSac = getFormDataTayAo(txtTenThuocTinh);
                if (mauSac != null) {
                    tayAoService.add(mauSac);
                    tayAos = tayAoService.getAll();
                    showcbbTayAo(tayAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }//GEN-LAST:event_btnAddTayAoActionPerformed

    private void btnAddXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddXuatXuActionPerformed
        JFrame frame = new JFrame("Thêm Màu Sắc");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                XuatXu mauSac = getFormDataXuatXu(txtTenThuocTinh);
                if (mauSac != null) {
                    xuatXuService.add(mauSac);
                    xuatXus = xuatXuService.getAll();
                    showcbbXuatXu(xuatXus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }//GEN-LAST:event_btnAddXuatXuActionPerformed

    private void btnAddChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddChatLieuActionPerformed
        JFrame frame = new JFrame("Thêm Màu Sắc");
        JPanel panel = new JPanel();
        frame.add(panel);

        // Tạo các ô textField và nút "Thêm"
        JTextField txtMaThuocTinh = new JTextField(20);
        JTextField txtTenThuocTinh = new JTextField(20);
        JButton btnThem = new JButton("Thêm");

        // Thêm các thành phần vào panel

        panel.add(new JLabel("Tên thuộc tính:"));
        panel.add(txtTenThuocTinh);
        panel.add(btnThem);

        // Xử lý sự kiện khi người dùng ấn nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức getFormDataMauSac để lấy dữ liệu từ cửa sổ
                ChatLieu mauSac = getFormDataChatLieu(txtTenThuocTinh);
                if (mauSac != null) {
                    chatLieuService.add(mauSac);
                    chatLieus = chatLieuService.getAll();
                    showcbbChatLieu(chatLieus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
                frame.dispose();
            }

        });

        // Cấu hình cửa sổ dialog
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }//GEN-LAST:event_btnAddChatLieuActionPerformed

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed

    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private boolean validateFormData() {
        try {
            // Kiểm tra các ô nhập liệu có trống không
            if (cbbSanPham.getSelectedItem() == null || cbbDanhMuc.getSelectedItem() == null
                    || cbbNSX.getSelectedItem() == null || cbbXuatXu.getSelectedItem() == null
                    || cbbMauSac.getSelectedItem() == null || cbbSize.getSelectedItem() == null
                    || cbbThuongHieu.getSelectedItem() == null || cbbChatLieu.getSelectedItem() == null
                    || cbbCoAo.getSelectedItem() == null || cbbDuoiAo.getSelectedItem() == null
                    || CbbTayAo.getSelectedItem() == null || cbbDangAo.getSelectedItem() == null
                    || txtSoLuong.getText().isEmpty() || txtGia.getText().isEmpty()
                    || txtMoTa.getText().isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                return false;
            }

            // Kiểm tra các ô nhập số
            Long.parseLong(txtSoLuong.getText());
            new BigDecimal(txtGia.getText());

            // Kiểm tra ô trạng thái
        } catch (NumberFormatException ex) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập 2 trường số lượng và giá là số");
            return false;
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private combobox.Combobox CbbTayAo;
    private javax.swing.JButton btnAddChatLieu;
    private javax.swing.JButton btnAddCoAo;
    private javax.swing.JButton btnAddDangAo;
    private javax.swing.JButton btnAddDanhMuc;
    private javax.swing.JButton btnAddDuoiAo;
    private javax.swing.JButton btnAddMauSac;
    private javax.swing.JButton btnAddNSX;
    private javax.swing.JButton btnAddSize;
    private javax.swing.JButton btnAddTayAo;
    private javax.swing.JButton btnAddThuongHieu;
    private javax.swing.JButton btnAddXuatXu;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTroLai;
    private combobox.Combobox cbbChatLieu;
    private combobox.Combobox cbbCoAo;
    private combobox.Combobox cbbDangAo;
    private combobox.Combobox cbbDanhMuc;
    private combobox.Combobox cbbDuoiAo;
    private combobox.Combobox cbbMauSac;
    private combobox.Combobox cbbNSX;
    private combobox.Combobox cbbSanPham;
    private combobox.Combobox cbbSize;
    private combobox.Combobox cbbThuongHieu;
    private combobox.Combobox cbbTrangThai;
    private combobox.Combobox cbbXuatXu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private swing.RoundPanel roundPanel11;
    private swing.RoundPanel roundPanel2;
    private textfield.TextField txtGia;
    private textfield.TextField txtMoTa;
    private textfield.TextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
