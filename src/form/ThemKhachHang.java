/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.entity.KhachHangEntity;
import backend.service.KhachHangService;
import java.awt.Window;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;

/**
 *
 * @author VHC
 */
public class ThemKhachHang extends javax.swing.JPanel {

    private List<KhachHangEntity> listKH = new ArrayList<>();
    private DefaultTableModel dtmKH = new DefaultTableModel();
    private KhachHangService srKH = new KhachHangService();

//// Phương thức để trả về mã khách hàng được chọn
//    public String getMaKhachHangChon() {
//        return maKhachHangChon;
//    }
//
//// Phương thức để trả về tên khách hàng được chọn
//    public String getTenKhachHangChon() {
//        return tenKhachHangChon;
//    }
    interface KhachHangSelectedListener extends EventListener {

        void khachHangSelected(String sdtChon, String tenKhachHang, String diaChi);
    };

    private List<KhachHangSelectedListener> listeners = new ArrayList<>();
    private String sdtChon;
    private String tenKhachHangChon;
    private String diaChiChon;

    // Các phương thức để lấy mã và tên khách hàng đã chọn
    public String getSDTChon() {
        return sdtChon;
    }

    public String getTenKhachHangChon() {
        return tenKhachHangChon;
    }

    public String getDiaChiChon() {
        return diaChiChon;
    }
    
    

    /**
     * Creates new form ThemKhachHang
     */
    public ThemKhachHang() {
        initComponents();
        dtmKH = (DefaultTableModel) tblKH.getModel();
        listKH = srKH.getAll();
        showDataTable(listKH);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchKH();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchKH();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchKH();
            }
        });
    }
    
    public void searchKH(){
        listKH = srKH.Tim(txtSearch.getText().trim());
        showDataTable(listKH);
    }

    public void showDataTable(List<KhachHangEntity> khachHangEntitys) {
        dtmKH.setRowCount(0);
        tblKH.setRowHeight(35);
        for (KhachHangEntity khachHangEntity : khachHangEntitys) {
            dtmKH.addRow(new Object[]{
                khachHangEntity.getStt(), khachHangEntity.getMaKhachHang(), khachHangEntity.getTenKhachHang(), khachHangEntity.isGioiTinh() ? "Nam" : " Nữ",
                khachHangEntity.getSoDienThoai(), khachHangEntity.getDiaChi()
            });
        }
    }

    // Phương thức để đăng ký lắng nghe cho sự kiện chọn khách hàng
    public void addKhachHangSelectedListener(KhachHangSelectedListener listener) {
        listeners.add(listener);
    }

    // Phương thức để thông báo cho tất cả các lắng nghe về sự kiện rằng một khách hàng đã được chọn
    private void fireKhachHangSelectedEvent(String sdt, String tenKhachHang, String diaChi) {
        for (KhachHangSelectedListener listener : listeners) {
            listener.khachHangSelected(sdt, tenKhachHang, diaChi);
        }
    }

    public boolean check() {
        if (txtTen.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên khách hàng trống");
            return false;
        }
        if (txtSDT.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số điện thoại trống");
            return false;
        }
        if (txtDiaChi.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Địa chỉ trống");
            return false;
        }

        // Kiểm tra xem người dùng đã chọn giới tính hay chưa
        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Giới tính chưa được chọn");
            return false;
        }
        return true;
    }

    public KhachHangEntity getFormData() {
        String ten = txtTen.getText();
        boolean gioiTinh = rdoNam.isSelected();
        String soDienThoai = txtSDT.getText();
        String diaChi = txtDiaChi.getText();
        KhachHangEntity kh = new KhachHangEntity(ten, gioiTinh, soDienThoai, diaChi);
        return kh;
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtSearch = new textfield.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKH = new javax.swing.JTable();
        btnChonKH = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtTen = new textfield.TextField();
        txtSDT = new textfield.TextField();
        jLabel1 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();

        txtSearch.setLabelText("Tìm kiếm");

        tblKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KH", "Tên KH", "Giới tính", "SDT", "Địa chỉ"
            }
        ));
        tblKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKH);

        btnChonKH.setText("Chọn");
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(btnChonKH)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        jTabbedPane1.addTab("Danh sách khách hàng", jPanel1);

        txtTen.setLabelText("Tên khách hàng");
        txtTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenActionPerformed(evt);
            }
        });

        txtSDT.setLabelText("SDT");
        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        jLabel1.setText("Giới tính");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel2.setText("Địa chỉ");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(16, 16, 16)
                        .addComponent(rdoNam)
                        .addGap(32, 32, 32)
                        .addComponent(rdoNu))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 10, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(btnAdd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Thiết lập thông tin khách hàng", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKHMouseClicked
//        int selectedRow = tblKH.getSelectedRow();
//        if (selectedRow != -1) {
//            // Lấy mã và tên khách hàng từ hàng được chọn trong bảng
//            maKhachHangChon = tblKH.getValueAt(selectedRow, 1).toString();
//            tenKhachHangChon = tblKH.getValueAt(selectedRow, 2).toString();
//            System.out.println(maKhachHangChon + tenKhachHangChon);
//        }
    }//GEN-LAST:event_tblKHMouseClicked

    private void txtTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        // Lấy thông tin mã và tên khách hàng từ hàng được chọn trong bảng
        int selectedRow = tblKH.getSelectedRow();
        if (selectedRow != -1) {
            sdtChon = tblKH.getValueAt(selectedRow, 4).toString();
            tenKhachHangChon = tblKH.getValueAt(selectedRow, 2).toString();
            diaChiChon = tblKH.getValueAt(selectedRow, 5).toString();

            // Thông báo cho các lắng nghe về sự kiện rằng một khách hàng đã được chọn
            fireKhachHangSelectedEvent(sdtChon, tenKhachHangChon, diaChiChon);
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame) {
            ((JFrame) window).dispose();
        }

    }//GEN-LAST:event_btnChonKHActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (check()) {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm?", "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                srKH.Add(getFormData());
                listKH = srKH.getAll();
                showDataTable(listKH);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã thêm khách hàng mới");
            } else if (choice == JOptionPane.NO_OPTION) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy");
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnChonKH;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblKH;
    private javax.swing.JTextArea txtDiaChi;
    private textfield.TextField txtSDT;
    private textfield.TextField txtSearch;
    private textfield.TextField txtTen;
    // End of variables declaration//GEN-END:variables
}
