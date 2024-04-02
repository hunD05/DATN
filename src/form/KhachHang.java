/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.entity.KhachHangEntity;
import backend.service.KhachHangService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;

/**
 *
 * @author leanb
 */
public class KhachHang extends javax.swing.JPanel {

    public List<KhachHangEntity> list = new ArrayList<>();
    public DefaultTableModel dtm = new DefaultTableModel();
    public KhachHangService sv = new KhachHangService();

    /**
     * Creates new form HoaDon
     */
    public KhachHang() {
        initComponents();
        list = sv.getAll();
        dtm = (DefaultTableModel) tblBang.getModel();
        showDataTable(list);

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một khách hàng để sửa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa thông tin khách hàng này?", "Xác nhận sửa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    KhachHangEntity kh = list.get(row);
                    sv.Update(getFormData(), kh.getId());
                    list = sv.getAll();
                    showDataTable(list);
                }
            }

            @Override
            public void onDelete(int row) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một khách hàng để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    KhachHangEntity kh = list.get(row);
                    sv.Delete(kh.getId());
                    list = sv.getAll();
                    showDataTable(list);
                }
            }

        };
        tblBang.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        tblBang.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
    }

    public void showDataTable(List<KhachHangEntity> khachHangEntitys) {
        dtm.setRowCount(0);
        String gt = "";
        tblBang.setRowHeight(35);
        for (KhachHangEntity khachHangEntity : khachHangEntitys) {
            if (khachHangEntity.getGioiTinh() == true) {
                gt = "Nữ";
            } else {
                gt = "Nam";
            }
            dtm.addRow(new Object[]{khachHangEntity.getStt(), khachHangEntity.getMaKhachHang(), khachHangEntity.getTenKhachHang(), gt, khachHangEntity.getSoDienThoai(), khachHangEntity.getNgaySinh()});
        }
    }

    public KhachHangEntity getFormData() {
        String ma = txtMa.getText();
        String ten = txtTen.getText();
        boolean gioiTinh = rdoNam.isSelected();
        String soDienThoai = txtSDT.getText();
        String ngaySinhStr = txtNgaySinh.getText();

        // Handling the possibility of invalid input for ngaySinh (assuming it represents a date)
        java.sql.Date ngaySinh = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);  // Ensure strict date parsing

            java.util.Date utilNgaySinh = dateFormat.parse(ngaySinhStr);
            ngaySinh = new java.sql.Date(utilNgaySinh.getTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lệ! Định dạng đúng là yyyy-MM-dd", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        KhachHangEntity kh = new KhachHangEntity(ma, ten, gioiTinh, soDienThoai, ngaySinh);
        return kh;
    }

    private boolean isFormDataValid() {
        // Kiểm tra trống các trường dữ liệu
        if (txtMa.getText().isEmpty() || txtTen.getText().isEmpty() || txtNgaySinh.getText().isEmpty() || txtSDT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        roundPanel1 = new swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        roundPanel4 = new swing.RoundPanel();
        txtMa = new textfield.TextField();
        txtSDT = new textfield.TextField();
        txtTen = new textfield.TextField();
        txtNgaySinh = new textfield.TextField();
        jPanel1 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        roundPanel10 = new swing.RoundPanel();
        jLabel6 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        roundPanel8 = new swing.RoundPanel();
        jPanel12 = new javax.swing.JPanel();
        txttim = new textfield.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();

        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel1.setText("Khách hàng");

        roundPanel4.setLayout(new java.awt.GridLayout(2, 10, 10, 10));

        txtMa.setLabelText("Mã khách hàng");
        roundPanel4.add(txtMa);

        txtSDT.setLabelText("SDT");
        roundPanel4.add(txtSDT);

        txtTen.setLabelText("Họ và tên");
        roundPanel4.add(txtTen);

        txtNgaySinh.setLabelText("Ngày sinh");
        roundPanel4.add(txtNgaySinh);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel1.add(btnThem);

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel1.add(btnSua);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel1.add(btnXoa);

        roundPanel10.setLayout(new java.awt.GridLayout(1, 10, 10, 10));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel6.setText("Giới tính");
        roundPanel10.add(jLabel6);

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");
        roundPanel10.add(rdoNam);

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");
        rdoNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNuActionPerformed(evt);
            }
        });
        roundPanel10.add(rdoNu);

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jPanel12.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        txttim.setLabelText("Tìm theo tên, Mã, Địa chỉ");
        txttim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttimActionPerformed(evt);
            }
        });
        jPanel12.add(txttim);

        tblBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Ma khach hang", "Ten khach hang", "Gioi tinh", "So dien thoai", "Ngay sinh", "Hanh dong"
            }
        ));
        tblBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblBang);

        javax.swing.GroupLayout roundPanel8Layout = new javax.swing.GroupLayout(roundPanel8);
        roundPanel8.setLayout(roundPanel8Layout);
        roundPanel8Layout.setHorizontalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 629, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addGap(12, 12, 12))
        );
        roundPanel8Layout.setVerticalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thông tin khách hàng", roundPanel8);

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1367, Short.MAX_VALUE)
                            .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(5, 5, 5))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(roundPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(roundPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane1)
                    .addContainerGap()))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(roundPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(571, Short.MAX_VALUE))
            .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(roundPanel1Layout.createSequentialGroup()
                    .addGap(210, 210, 210)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNuActionPerformed

    }//GEN-LAST:event_rdoNuActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (!isFormDataValid()) {
            return;
        }

        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm khách hàng này?", "Xác nhận thêm", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            sv.Add(getFormData());
            list = sv.getAll();
            showDataTable(list);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (!isFormDataValid()) {
            return;
        }

        int row = tblBang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một khách hàng để sửa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa thông tin khách hàng này?", "Xác nhận sửa", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            KhachHangEntity kh = list.get(row);
            sv.Update(getFormData(), kh.getId());
            list = sv.getAll();
            showDataTable(list);
        }

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tblBang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một khách hàng để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            KhachHangEntity kh = list.get(row);
            sv.Delete(kh.getId());
            list = sv.getAll();
            showDataTable(list);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        // TODO add your handling code here:
        int row = tblBang.getSelectedRow();
        KhachHangEntity kh = list.get(row);
        txtMa.setText(kh.getMaKhachHang());
        txtTen.setText(kh.getTenKhachHang());
        txtNgaySinh.setText(kh.getNgaySinh() + "");
        txtSDT.setText(kh.getSoDienThoai());
        if (kh.getGioiTinh() == true) {
            rdoNam.setSelected(false);
            rdoNu.setSelected(true);
        } else {
            rdoNam.setSelected(true);
            rdoNu.setSelected(false);
        }
    }//GEN-LAST:event_tblBangMouseClicked

    private void txttimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttimActionPerformed
        showDataTable(sv.Tim(txttim.getText()));
    }//GEN-LAST:event_txttimActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel10;
    private swing.RoundPanel roundPanel4;
    private swing.RoundPanel roundPanel8;
    private javax.swing.JTable tblBang;
    private textfield.TextField txtMa;
    private textfield.TextField txtNgaySinh;
    private textfield.TextField txtSDT;
    private textfield.TextField txtTen;
    private textfield.TextField txttim;
    // End of variables declaration//GEN-END:variables
}
