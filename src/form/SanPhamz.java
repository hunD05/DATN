/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.service.MaChiTietSPService;
import backend.service.SanPhamService;
import backend.viewmodel.SanPhamViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;
import raven.toast.Notifications;

/**
 *
 * @author leanb
 */
public class SanPhamz extends javax.swing.JPanel {

    /**
     * Creates new form SanPhamz
     */
    DefaultTableModel dtm = new DefaultTableModel();
    List<backend.entity.SanPham> ListSanPhamViewModels = new ArrayList<>();
    SanPhamService sanPhamService = new SanPhamService();

    DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
    List<backend.entity.ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
    MaChiTietSPService maChiTietSPService = new MaChiTietSPService();

    public SanPhamz() {
        initComponents();

        dtm = (DefaultTableModel) tblSanPham.getModel();
        ListSanPhamViewModels = sanPhamService.getAll();
        showDataTable(ListSanPhamViewModels);

        chiTietSanPhams = maChiTietSPService.getAll();
        showComboBoxData(chiTietSanPhams);

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                if (row == -1) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một sản phẩm");
                    return;
                }

                int confirmResult = JOptionPane.showConfirmDialog(SanPhamz.this, "Bạn có chắc muốn sửa thông tin sản phẩm này?", "Xác nhận sửa sản phẩm", JOptionPane.YES_NO_OPTION);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    backend.entity.SanPham sanPham = ListSanPhamViewModels.get(row);

                    if (sanPhamService.update(getFormData(), sanPham.getId())) {
                        ListSanPhamViewModels = sanPhamService.getAll();
                        showDataTable(ListSanPhamViewModels);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Cập nhật thất bại");
                    }
                }
            }

            @Override
            public void onDelete(int row) {
                //int row = tblSanPham.getSelectedRow();

                if (row == -1) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một sản phẩm");
                    return;
                }

                int confirmResult = JOptionPane.showConfirmDialog(SanPhamz.this, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận xóa sản phẩm", JOptionPane.YES_NO_OPTION);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    backend.entity.SanPham sanPham = ListSanPhamViewModels.get(row);

                    if (sanPhamService.delete(sanPham.getId())) {
                        ListSanPhamViewModels = sanPhamService.getAll();
                        showDataTable(ListSanPhamViewModels);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Xóa thất bại");
                    }
                }
            }

        };
        tblSanPham.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
        tblSanPham.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
    }

    public void showDataTable(List<backend.entity.SanPham> sanPhams) {
        dtm.setRowCount(0);
        for (backend.entity.SanPham sanPham : sanPhams) {
            dtm.addRow(new Object[]{sanPham.getStt(), sanPham.getMaSanPham(), sanPham.getTenSanPham()});
        }
        tblSanPham.setRowHeight(40);
    }

    public void showComboBoxData(List<backend.entity.ChiTietSanPham> chiTietSanPhams) {
        dcbm.removeAllElements();
        for (backend.entity.ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
            dcbm.addElement(chiTietSanPham.getId());
        }
    }

    public backend.entity.SanPham getFormData() {
        backend.entity.ChiTietSanPham chiTietSanPham = new backend.entity.ChiTietSanPham();
        String tenSP = txtTenSP.getText().trim();
        String maSP = txtMaSP.getText().trim();
        backend.entity.SanPham sanPham = new backend.entity.SanPham(maSP, tenSP);
        return sanPham;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel5 = new swing.RoundPanel();
        roundPanel12 = new swing.RoundPanel();
        txtMaSP = new textfield.TextField();
        txtTenSP = new textfield.TextField();
        roundPanel13 = new swing.RoundPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();

        roundPanel12.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        txtMaSP.setLabelText("Mã sản phẩm");
        roundPanel12.add(txtMaSP);

        txtTenSP.setLabelText("Tên sản phẩm");
        roundPanel12.add(txtTenSP);

        roundPanel13.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        roundPanel13.add(btnThem);

        btnSua.setText("Sửa ");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        roundPanel13.add(btnSua);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        roundPanel13.add(btnXoa);

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Hành động"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(roundPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(roundPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(roundPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(roundPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1104, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(roundPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(roundPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int row = tblSanPham.getSelectedRow();
        backend.entity.SanPham sanPham = ListSanPhamViewModels.get(row);
        txtMaSP.setText(sanPham.getMaSanPham());
        txtTenSP.setText(sanPham.getTenSanPham());
        // con 
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        String tenSP = txtTenSP.getText();
        String maSP = txtMaSP.getText();

        if (tenSP.isEmpty() || maSP.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return;
        }

        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm sản phẩm?", "Xác nhận thêm sản phẩm", JOptionPane.YES_NO_OPTION);

        if (confirmResult == JOptionPane.YES_OPTION) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm sản phẩm thành công");
            sanPhamService.add(getFormData());
            ListSanPhamViewModels = sanPhamService.getAll();
            showDataTable(ListSanPhamViewModels);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tblSanPham.getSelectedRow();

        if (row == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một sản phẩm");
            return;
        }

        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận xóa sản phẩm", JOptionPane.YES_NO_OPTION);

        if (confirmResult == JOptionPane.YES_OPTION) {
            backend.entity.SanPham sanPham = ListSanPhamViewModels.get(row);

            if (sanPhamService.delete(sanPham.getId())) {
                ListSanPhamViewModels = sanPhamService.getAll();
                showDataTable(ListSanPhamViewModels);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thành công");
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Xóa thất bại");
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int row = tblSanPham.getSelectedRow();

        if (row == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một sản phẩm");
            return;
        }

        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa thông tin sản phẩm này?", "Xác nhận sửa sản phẩm", JOptionPane.YES_NO_OPTION);

        if (confirmResult == JOptionPane.YES_OPTION) {
            backend.entity.SanPham sanPham = ListSanPhamViewModels.get(row);

            if (sanPhamService.update(getFormData(), sanPham.getId())) {
                ListSanPhamViewModels = sanPhamService.getAll();
                showDataTable(ListSanPhamViewModels);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công");
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Cập nhật thất bại");
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JScrollPane jScrollPane3;
    private swing.RoundPanel roundPanel12;
    private swing.RoundPanel roundPanel13;
    private swing.RoundPanel roundPanel5;
    private javax.swing.JTable tblSanPham;
    private textfield.TextField txtMaSP;
    private textfield.TextField txtTenSP;
    // End of variables declaration//GEN-END:variables

}
