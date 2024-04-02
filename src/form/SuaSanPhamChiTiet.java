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
import backend.service.GiaBanService;
import backend.service.MauSacService;
import backend.service.NSXService;
import backend.service.SanPhamCBBService;
import backend.service.SanPhamService;
import backend.service.SizeService;
import backend.service.TayAoService;
import backend.service.ThuonHieuService;
import backend.service.XuatXuService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.application.Application;
import raven.toast.Notifications;

/**
 *
 * @author leanb
 */
public class SuaSanPhamChiTiet extends javax.swing.JPanel {

    /**
     * Creates new form ThuocTinhSanPham
     */
    
    DefaultTableModel dtm = new DefaultTableModel();
    List<backend.entity.ChiTietSanPham> chitietsanphams = new ArrayList<>();
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
    String idctsp;
    public SuaSanPhamChiTiet(String idChiTietSP, String sanPham,String danhMuc,String NSX,String xuatxu,String mauSac,String size,String thuongHieu,String chatLieu,String coAo,String duoiAo,String tayAo,String dangAoz,String soLuong,String gia,String moTa,String trangThai) {
        initComponents();
        chitietsanphams = chiTietSanPhamService.getAll();
        
//        chitietsanphams = chiTietSanPhamService.getAll();
//        dtm = (DefaultTableModel) tblChiTietSanPham.getModel();
//        showDataTable(chitietsanphams);
        
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
        
        txtGia.setText(gia);
        txtSoLuong.setText(soLuong);
        txtMoTa.setText(moTa);
        txtTrangThai.setText(trangThai);
        cbbDanhMuc.setSelectedItem(danhMuc);
        cbbNSX.setSelectedItem(NSX);
        cbbXuatXu.setSelectedItem(xuatxu);
        cbbMauSac.setSelectedItem(mauSac);
        cbbSize.setSelectedItem(size);
        cbbThuongHieu.setSelectedItem(thuongHieu);
        cbbChatLieu.setSelectedItem(chatLieu);
        cbbCoAo.setSelectedItem(coAo);
        cbbDuoiAo.setSelectedItem(duoiAo);
        CbbTayAo.setSelectedItem(tayAo);
        cbbDangAo.setSelectedItem(dangAoz);
        cbbSanPham.setSelectedItem(sanPham);
        idctsp = idChiTietSP;
    }

        
    public void showDataTable(List<backend.entity.ChiTietSanPham> chiTietSanPhams) {
        dtm.setRowCount(0);
        for (backend.entity.ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
            dtm.addRow(new Object[]{chiTietSanPham.getStt(), chiTietSanPham.getGiaBan(), chiTietSanPham.getSoLuong(), chiTietSanPham.getMoTa(), chiTietSanPham.getTrangThai()});
        }
    }
    
    public void showcbbDanhMuc(List<DanhMuc> danhMucs) {
        dcbmDanhMuc.removeAllElements();
        for (DanhMuc danhMuc : danhMucs) {
            dcbmDanhMuc.addElement((int) danhMuc.getId());
        }
    }

    public void showcbbNSX(List<NSX> nsxs) {
        dcbmNSX.removeAllElements();
        for (NSX nsx1 : nsxs) {
            dcbmNSX.addElement(nsx1.getId());
        }
    }

    public void showcbbXuatXu(List<XuatXu> xuatXus) {
        dcbmXuatXu.removeAllElements();
        for (XuatXu xuatXu : xuatXus) {
            dcbmXuatXu.addElement(xuatXu.getId());
        }
    }

    public void showcbbMauSac(List<MauSac> mauSacs) {
        dcbmMauSac.removeAllElements();
        for (MauSac mauSac : mauSacs) {
            dcbmMauSac.addElement(mauSac.getId());
        }
    }

    public void showcbbSize(List<Size> sizes) {
        dcbmSize.removeAllElements();
        for (Size size : sizes) {
            dcbmSize.addElement(size.getId());
        }
    }

    public void showcbbThuongHieu(List<ThuongHieu> thuongHieus) {
        dcbmThuongHieu.removeAllElements();
        for (ThuongHieu thuongHieu : thuongHieus) {
            dcbmThuongHieu.addElement(thuongHieu.getId());
        }
    }

    public void showcbbChatLieu(List<ChatLieu> chatLieus) {
        dcbmChatLieu.removeAllElements();
        for (ChatLieu chatLieu : chatLieus) {
            dcbmChatLieu.addElement(chatLieu.getId());
        }
    }

    public void showcbbCoAo(List<CoAo> coAos) {
        dcbmCoAo.removeAllElements();
        for (CoAo coAo : coAos) {
            dcbmCoAo.addElement(coAo.getId());
        }
    }

    public void showcbbDuoiAo(List<DuoiAo> duoiAos) {
        dcbmDuoiAo.removeAllElements();
        for (DuoiAo duoiAo : duoiAos) {
            dcbmDuoiAo.addElement(duoiAo.getId());
        }
    }

    public void showcbbTayAo(List<TayAo> tayAos) {
        dcbmTayAo.removeAllElements();
        for (TayAo tayAo : tayAos) {
            dcbmTayAo.addElement(tayAo.getId());
        }
    }

    public void showcbbDangAo(List<DangAo> dangAos) {
        dcbmDangAo.removeAllElements();
        for (DangAo dangAo1 : dangAos) {
            dcbmDangAo.addElement(dangAo1.getId());
        }
    }

    public void showcbbSanPham(List<backend.entity.SanPham> sanPhams) {
        dcbmSanPham.removeAllElements();
        for (backend.entity.SanPham sanPham : sanPhams) {
            dcbmSanPham.addElement(sanPham.getId());
        }
    }

    public backend.entity.ChiTietSanPham getFormData() {
        String sp = cbbSanPham.getSelectedItem().toString();
        String danhMuc = cbbDanhMuc.getSelectedItem().toString();
        String nsx = cbbNSX.getSelectedItem().toString();
        String xuatXu = cbbXuatXu.getSelectedItem().toString();
        String mauSac = cbbMauSac.getSelectedItem().toString();
        String size = cbbSize.getSelectedItem().toString();
        String thuongHieu = cbbThuongHieu.getSelectedItem().toString();
        String chatLieu = cbbChatLieu.getSelectedItem().toString();
        String coAo = cbbCoAo.getSelectedItem().toString();
        String duoiAo = cbbDuoiAo.getSelectedItem().toString();
        String tayAo = CbbTayAo.getSelectedItem().toString();
        String dangAo = cbbDangAo.getSelectedItem().toString();
        String soLuong = txtSoLuong.getText();
        String gia = txtGia.getText();
        String moTa = txtMoTa.getText();
        String tinhTrang = txtTrangThai.getText();

        backend.entity.ChiTietSanPham chiTietSanPham = new backend.entity.ChiTietSanPham(
                Long.parseLong(sp),
                new BigDecimal(gia),
                Long.parseLong(soLuong),
                moTa,
                Long.parseLong(danhMuc),
                Long.parseLong(xuatXu),
                Long.parseLong(nsx),
                Long.parseLong(mauSac),
                Long.parseLong(size),
                Long.parseLong(thuongHieu),
                Long.parseLong(chatLieu),
                Long.parseLong(coAo),
                Long.parseLong(tayAo),
                Long.parseLong(duoiAo),
                Long.parseLong(dangAo),
                tinhTrang
        );

        return chiTietSanPham;
    }
    public void setData() {
        
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
        txtTrangThai = new textfield.TextField();
        btnTroLai = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        roundPanel2.setBackground(new java.awt.Color(239, 239, 239));
        roundPanel2.setForeground(new java.awt.Color(235, 235, 235));

        roundPanel11.setLayout(new java.awt.GridLayout(1, 0, 16, 0));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(4, 0, 10, 20));

        cbbSanPham.setLabeText("Sản phẩm");
        jPanel1.add(cbbSanPham);

        cbbDanhMuc.setLabeText("Danh mục");
        jPanel1.add(cbbDanhMuc);

        cbbNSX.setLabeText("Nhà sản xuất");
        jPanel1.add(cbbNSX);

        cbbXuatXu.setLabeText("Xuất xứ");
        jPanel1.add(cbbXuatXu);

        cbbMauSac.setLabeText("Màu sắc");
        jPanel1.add(cbbMauSac);

        cbbSize.setLabeText("Size");
        jPanel1.add(cbbSize);

        cbbThuongHieu.setLabeText("Thương hiệu");
        jPanel1.add(cbbThuongHieu);

        cbbChatLieu.setLabeText("Chất liệu");
        jPanel1.add(cbbChatLieu);

        cbbCoAo.setLabeText("Cổ áo");
        jPanel1.add(cbbCoAo);

        cbbDuoiAo.setLabeText("Đuôi áo");
        jPanel1.add(cbbDuoiAo);

        CbbTayAo.setLabeText("Tay áo");
        jPanel1.add(CbbTayAo);

        cbbDangAo.setLabeText("Dáng áo");
        jPanel1.add(cbbDangAo);

        txtSoLuong.setLabelText("Số lượng");
        jPanel1.add(txtSoLuong);

        txtGia.setLabelText("Giá");
        jPanel1.add(txtGia);

        txtMoTa.setLabelText("Mô tả");
        jPanel1.add(txtMoTa);

        txtTrangThai.setLabelText("Trạng thái");
        jPanel1.add(txtTrangThai);

        btnTroLai.setText("Trở lại");
        btnTroLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroLaiActionPerformed(evt);
            }
        });

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText(" Sửa chi tiết sản phẩm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 363, 31, 361);
        jPanel2.add(jLabel1, gridBagConstraints);

        jButton1.setText("Cập nhật");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(btnTroLai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(roundPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTroLai)
                            .addComponent(jButton1))
                        .addGap(14, 14, 14))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(roundPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (validateFormData()) {
        backend.entity.ChiTietSanPham chiTietSanPham = getFormData();
        chiTietSanPhamService.update(chiTietSanPham, idctsp);
        JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
    }
    }//GEN-LAST:event_jButton1ActionPerformed
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
                    || txtMoTa.getText().isEmpty() || txtTrangThai.getText().isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                return false;
            }

            // Kiểm tra các ô nhập số
            Long.parseLong(txtSoLuong.getText());
            new BigDecimal(txtGia.getText());

            // Kiểm tra ô trạng thái
            String trangThai = txtTrangThai.getText();
            if (!trangThai.equalsIgnoreCase("Còn hàng") && !trangThai.equalsIgnoreCase("Het hang")) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập trạng thái là 'Còn hàng' hoặc 'Hết hàng'");
                return false;
            }

        } catch (NumberFormatException ex) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập 2 trường số lượng và giá là số");
            return false;
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private combobox.Combobox CbbTayAo;
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
    private combobox.Combobox cbbXuatXu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private swing.RoundPanel roundPanel11;
    private swing.RoundPanel roundPanel2;
    private textfield.TextField txtGia;
    private textfield.TextField txtMoTa;
    private textfield.TextField txtSoLuong;
    private textfield.TextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
