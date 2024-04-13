/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.service.ChiTietSanPhamService;
import backend.service.MaChiTietSPService;
import backend.service.SanPhamService;
import backend.viewmodel.SanPhamChiTietViewModel;
import backend.viewmodel.SanPhamViewModel;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;
import raven.toast.Notifications;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import raven.application.Application;
import raven.application.form.MainForm;
import raven.cell.PanelAction;

/**
 *
 * @author leanb
 */
public class SanPhamz extends javax.swing.JPanel {

    /**
     * Creates new form SanPhamz
     */
    List<SanPhamChiTietViewModel> chitietsanpham = new ArrayList<>();

    DefaultTableModel dtm = new DefaultTableModel();
    List<SanPhamViewModel> ListSanPhamViewModels = new ArrayList<>();
    SanPhamService sanPhamService = new SanPhamService();

    DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
    List<backend.entity.ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
    MaChiTietSPService maChiTietSPService = new MaChiTietSPService();
    ChiTietSanPhamService chiTietSanPhamService = new ChiTietSanPhamService();

    public SanPhamz() {
        initComponents();

        dtm = (DefaultTableModel) tblSanPham.getModel();
        ListSanPhamViewModels = sanPhamService.getAll();
        showDataTable(ListSanPhamViewModels);

        chiTietSanPhams = maChiTietSPService.getAll();

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {

                if (row == -1) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một sản phẩm");
                    return;
                }

                // Lấy tên sản phẩm hiện tại từ bảng
                String tenSanPhamHienTai = (String) tblSanPham.getValueAt(row, 2);
                String tenSP = txtTenSP.getText().trim();

                // Kiểm tra các trường thông tin có đầy đủ hay không
                if (tenSP.isEmpty()) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    return;
                }

                // Kiểm tra xem tên sản phẩm đã tồn tại trong cơ sở dữ liệu hay chưa
                if (!tenSP.equals(tenSanPhamHienTai) && sanPhamService.isTenSanPhamExisted(tenSP)) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên sản phẩm đã tồn tại. Vui lòng chọn tên khác.");
                    return;
                }

                int confirmResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn sửa thông tin sản phẩm này?", "Xác nhận sửa sản phẩm", JOptionPane.YES_NO_OPTION);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    SanPhamViewModel sanPham = ListSanPhamViewModels.get(row);

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
                    SanPhamViewModel sanPham = ListSanPhamViewModels.get(row);

                    if (sanPhamService.delete(sanPham.getId())) {
                        ListSanPhamViewModels = sanPhamService.getAll();
                        showDataTable(ListSanPhamViewModels);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Xóa thất bại");
                    }
                }
            }

            @Override
            public void onView(int row) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
        tblSanPham.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender());
        tblSanPham.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event));

//        tblSanPham.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent evt) {
//                int index = tblSanPham.getSelectedRow();
//                if (index >= 0 && evt.getClickCount() == 2) {
//                    String tenSP = (String) tblSanPham.getValueAt(index, 1); // Lấy tên sản phẩm từ hàng đã chọn
//                    List<SanPhamChiTietViewModel> productList = chiTietSanPhamService.getSP(tenSP); // Lấy danh sách sản phẩm chi tiết dựa trên tên sản phẩm
//                    ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
//                    chiTietSanPham.showDataTable(productList); // Hiển thị dữ liệu trên form ChiTietSanPham
//                    Application.showForm(chiTietSanPham);
//                }
//            }
//        });
    }

    public void showDataTable(List<SanPhamViewModel> sanPhams) {
        dtm.setRowCount(0);
        for (SanPhamViewModel sanPham : sanPhams) {
            dtm.addRow(new Object[]{sanPham.getStt(), sanPham.getMaSanPham(), sanPham.getTenSanPham(), sanPham.getSoLuong(), sanPham.getTrangThai()});
        }
        tblSanPham.setRowHeight(40);
        tblSanPham.getColumnModel().getColumn(0).setMaxWidth(50);
        tblSanPham.getColumnModel().getColumn(3).setMaxWidth(100);

        // Thiết lập renderer cho tất cả các cột để canh giữa
        TableColumnModel columnModel = tblSanPham.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    ((JLabel) rendererComponent).setHorizontalAlignment(SwingConstants.CENTER);
                    return rendererComponent;
                }
            });
        }

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                if (row == -1) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một sản phẩm");
                    return;
                }

                // Lấy tên sản phẩm hiện tại từ bảng
                String tenSanPhamHienTai = (String) tblSanPham.getValueAt(row, 2);
                String tenSP = txtTenSP.getText().trim();

                // Kiểm tra các trường thông tin có đầy đủ hay không
                if (tenSP.isEmpty()) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    return;
                }

                // Kiểm tra xem tên sản phẩm đã tồn tại trong cơ sở dữ liệu hay chưa
                if (!tenSP.equals(tenSanPhamHienTai) && sanPhamService.isTenSanPhamExisted(tenSP)) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên sản phẩm đã tồn tại. Vui lòng chọn tên khác.");
                    return;
                }

                int confirmResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn sửa thông tin sản phẩm này?", "Xác nhận sửa sản phẩm", JOptionPane.YES_NO_OPTION);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    SanPhamViewModel sanPham = ListSanPhamViewModels.get(row);

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
                    SanPhamViewModel sanPham = ListSanPhamViewModels.get(row);

                    if (sanPhamService.delete(sanPham.getId())) {
                        ListSanPhamViewModels = sanPhamService.getAll();
                        showDataTable(ListSanPhamViewModels);
                        rdoTatCa.setSelected(true);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Xóa thất bại");
                    }
                }
            }

            @Override
            public void onView(int row) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

        };
        tblSanPham.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender());
        tblSanPham.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event));
    }

    public backend.entity.SanPham getFormData() {
        backend.entity.ChiTietSanPham chiTietSanPham = new backend.entity.ChiTietSanPham();
        String tenSP = txtTenSP.getText().trim();
        backend.entity.SanPham sanPham = new backend.entity.SanPham(tenSP);
        return sanPham;
    }

    public List<SanPhamChiTietViewModel> getSelectedProductListImel() {
        List<SanPhamChiTietViewModel> productList = new ArrayList<>();
        int index = tblSanPham.getSelectedRow();
        if (index >= 0) {
            String tenSP = (String) tblSanPham.getValueAt(index, 2); // Lấy tên sản phẩm từ hàng đã chọn
            productList = chiTietSanPhamService.getSP(tenSP); // Lấy danh sách sản phẩm chi tiết dựa trên tên sản phẩm
        }
        return productList;
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        roundPanel5 = new swing.RoundPanel();
        roundPanel12 = new swing.RoundPanel();
        txtTenSP = new textfield.TextField();
        roundPanel13 = new swing.RoundPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        rdoConHang = new javax.swing.JRadioButton();
        rdoHetHang = new javax.swing.JRadioButton();
        rdoTatCa = new javax.swing.JRadioButton();
        btnReset = new javax.swing.JButton();
        txtTimKiem = new textfield.TextField();
        btnTimKiem = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(222, 222, 222));

        roundPanel12.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Trạng thái", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Tình trạng");

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

        buttonGroup1.add(rdoTatCa);
        rdoTatCa.setText("Tất cả");
        rdoTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaActionPerformed(evt);
            }
        });

        btnReset.setText("Reset bộ lọc");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        txtTimKiem.setLabelText("Tìm kiếm");
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTimKiem.setText("Tim");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Sản phẩm");

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(roundPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addComponent(roundPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoTatCa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoConHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoHetHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReset))
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(roundPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(roundPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoConHang)
                    .addComponent(rdoHetHang)
                    .addComponent(jLabel1)
                    .addComponent(rdoTatCa)
                    .addComponent(btnReset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int index = tblSanPham.getSelectedRow();
        SanPhamViewModel sanPhamViewModel = ListSanPhamViewModels.get(index);
        txtTenSP.setText(sanPhamViewModel.getTenSanPham());

        if (index >= 0 && evt.getClickCount() == 2) {
            String tenSP = (String) tblSanPham.getValueAt(index, 2); // Lấy tên sản phẩm từ hàng đã chọn
            List<SanPhamChiTietViewModel> productList = chiTietSanPhamService.getSP(tenSP); // Lấy danh sách sản phẩm chi tiết dựa trên tên sản phẩm
            ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
            chiTietSanPham.chitietsanpham = productList;
            chiTietSanPham.showDataTable(productList); // Hiển thị dữ liệu trên form ChiTietSanPham
            MainForm mainForm = new MainForm();
            Application.showForm(chiTietSanPham);
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        String tenSP = txtTenSP.getText().trim();

        // Kiểm tra các trường thông tin có đầy đủ hay không
        if (tenSP.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return;
        }

        // Kiểm tra xem tên sản phẩm đã tồn tại trong cơ sở dữ liệu hay chưa
        if (sanPhamService.isTenSanPhamExisted(tenSP)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên sản phẩm đã tồn tại. Vui lòng chọn tên khác.");
            return;
        }

        // Kiểm tra mã sản phẩm có tồn tại hay không
        // Xác nhận thêm sản phẩm
        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm sản phẩm?", "Xác nhận thêm sản phẩm", JOptionPane.YES_NO_OPTION);

        if (confirmResult == JOptionPane.YES_OPTION) {
            // Thêm sản phẩm vào cơ sở dữ liệu
            boolean added = sanPhamService.add(getFormData());
            if (added) {
                // Hiển thị thông báo thành công
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm sản phẩm thành công");
                // Lấy và hiển thị danh sách sản phẩm sau khi thêm
                ListSanPhamViewModels = sanPhamService.getAll();
                showDataTable(ListSanPhamViewModels);
            } else {
                // Hiển thị thông báo thất bại
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm sản phẩm thất bại. Vui lòng thử lại sau.");
            }
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
            SanPhamViewModel sanPham = ListSanPhamViewModels.get(row);

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

        String tenSP = txtTenSP.getText().trim();

        // Kiểm tra các trường thông tin có đầy đủ hay không
        if (tenSP.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return;
        }

        // Kiểm tra xem tên sản phẩm đã tồn tại trong cơ sở dữ liệu hay chưa
        if (sanPhamService.isTenSanPhamExisted(tenSP)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên sản phẩm đã tồn tại. Vui lòng chọn tên khác.");
            return;
        }

        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa thông tin sản phẩm này?", "Xác nhận sửa sản phẩm", JOptionPane.YES_NO_OPTION);

        if (confirmResult == JOptionPane.YES_OPTION) {
            SanPhamViewModel sanPham = ListSanPhamViewModels.get(row);

            if (sanPhamService.update(getFormData(), sanPham.getId())) {
                ListSanPhamViewModels = sanPhamService.getAll();
                showDataTable(ListSanPhamViewModels);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công");
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Cập nhật thất bại");
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        ListSanPhamViewModels = sanPhamService.search(txtTimKiem.getText());
        showDataTable(ListSanPhamViewModels);
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void rdoConHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoConHangActionPerformed
        List<SanPhamViewModel> conHangList = sanPhamService.searchByStatus("Còn hàng");
        ListSanPhamViewModels = conHangList; // Cập nhật danh sách sản phẩm
        showDataTable(conHangList); // Hiển thị dữ liệu đã lọc lên bảng
    }//GEN-LAST:event_rdoConHangActionPerformed

    private void rdoHetHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHetHangActionPerformed
        List<SanPhamViewModel> hetHangList = sanPhamService.searchByStatus("Hết hàng");
        ListSanPhamViewModels = hetHangList; // Cập nhật danh sách sản phẩm
        showDataTable(hetHangList); // Hiển thị dữ liệu đã lọc lên bảng
    }//GEN-LAST:event_rdoHetHangActionPerformed

    private void rdoTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaActionPerformed
        ListSanPhamViewModels = sanPhamService.getAll();
        showDataTable(ListSanPhamViewModels);
    }//GEN-LAST:event_rdoTatCaActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        rdoTatCa.setSelected(true);
        txtTimKiem.setText("");
        ListSanPhamViewModels = sanPhamService.getAll();
        showDataTable(ListSanPhamViewModels);
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton rdoConHang;
    private javax.swing.JRadioButton rdoHetHang;
    private javax.swing.JRadioButton rdoTatCa;
    private swing.RoundPanel roundPanel12;
    private swing.RoundPanel roundPanel13;
    private swing.RoundPanel roundPanel5;
    private javax.swing.JTable tblSanPham;
    private textfield.TextField txtTenSP;
    private textfield.TextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
