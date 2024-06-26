/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.entity.chucVu;
import backend.entity.nhanVien;
import backend.service.chucVuService;
import backend.service.nhanVienService;
import backend.viewmodel.nhanVienViewModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;
import raven.toast.Notifications;

/**
 *
 * @author leanb
 */
public class NhanVien extends javax.swing.JPanel {

    private List<nhanVienViewModel> lists = new ArrayList<>();
    private DefaultTableModel dtm = new DefaultTableModel();
    private nhanVienService service = new nhanVienService();

    private DefaultComboBoxModel dcdm = new DefaultComboBoxModel();
    private List<chucVu> lists2 = new ArrayList<>();
    private chucVuService service2 = new chucVuService();

    /**
     * Creates new form HoaDon
     */
    public NhanVien() {
        initComponents();

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
            }

            @Override
            public void onDelete(int row) {
                // TODO add your handling code here:
                int selectedRow = tblHienThi.getSelectedRow();

                if (selectedRow != -1) {
                    int option = JOptionPane.showConfirmDialog(btnXoa, "Bạn có muốn xóa hay không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        nhanVienViewModel NV = lists.get(selectedRow);
                        service.Delete(NV.getId());
                        lists = service.getAll();
                        showDataTable(lists);
                    }
                } else {
                    JOptionPane.showMessageDialog(btnXoa, "Vui lòng chọn một dòng để xóa.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
// Kiểm tra số lượng cột của bảng trước khi truy cập cột thứ 11
        int columnCount = tblHienThi.getColumnCount();
        if (columnCount >= 11) {
            // Chỉ sử dụng chỉ số cột nếu nó hợp lệ
            tblHienThi.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender());
            tblHienThi.getColumnModel().getColumn(10).setCellEditor(new TableActionCellEditor(event));
        } else {
            System.out.println("Số lượng cột không đủ để truy cập cột thứ 11.");
        }

        lists = service.getAll();
        dtm = (DefaultTableModel) tblHienThi.getModel();
        showDataTable(lists);

        lists2 = service2.getAll();
        dcdm = (DefaultComboBoxModel) cbbChucVu.getModel();
        showCombobox(lists2);

        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchTT();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchTT();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchTT();
            }
        });

        rdoAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdoAll.isSelected() == true) {
                    lists = service.getAll();
                    showDataTable(lists);
                }
            }
        });

        rdoDang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdoDang.isSelected() == true) {
                    lists = service.trangThai("Đang làm");
                    showDataTable(lists);
                }
            }
        });

        rdoNghi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdoNghi.isSelected() == true) {
                    lists = service.trangThai("Đã nghỉ");
                    showDataTable(lists);
                }
            }
        });
    }

    public void searchTT() {
        lists = service.search(txtTimKiem.getText());
        showDataTable(lists);
    }

    public void showDataTable(List<nhanVienViewModel> lists) {
        dtm.setRowCount(0);
        tblHienThi.setRowHeight(35);
        int i = 1;
        for (nhanVienViewModel list : lists) {
            dtm.addRow(new Object[]{
                i++,
                list.getMaNV(),
                list.getTenNV(),
                list.isGioiTinh() ? "Nam" : "Nữ",
                list.getSDT(),
                list.getCCCD(),
                list.getTenChucVu(),
                list.getNgaySinh(),
                list.getEmail(),
                list.getTrangThai(),});
        }
    }

    public void showCombobox(List<chucVu> lists2) {
        dcdm.removeAllElements();
        for (chucVu vu : lists2) {
            dcdm.addElement(vu.getTenCV());
        }
    }

    public boolean check() {
        if (txtTen.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên nhân viên trống");
            return false;
        }

        if (txtSDT.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số điện thoại trống");
            return false;
        }

        if (txtCCCD.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "CCCD trống");
            return false;
        }

        if (txtEmail.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Email trống");
            return false;
        }

        Date ngaySinh = jdcNgaySinh.getDate();

        if (ngaySinh == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày sinh trống");
            return false;
        }

        if (cbbTrangThai.getSelectedItem() == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Trạng thái làm việc chưa được chọn!");
            return false;
        }

        if (cbbChucVu.getSelectedItem() == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Chức vụ chưa được chọn");
            return false;
        }

        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Giới tính chưa được chọn!");
            return false;
        }
        return true;
    }

    public nhanVien getFormData() {

        String tenNV = txtTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String cccd = txtCCCD.getText().trim();
        String email = txtEmail.getText().trim();
        Date ngaySinh = jdcNgaySinh.getDate();
        String trangThai = (String) cbbTrangThai.getSelectedItem();
        boolean gioiTinh = rdoNam.isSelected();

        // Chuyển đổi chuỗi ngày tháng sang java.sql.Date (assumption: yyyy-MM-dd)
        // Tạo đối tượng nhanVien từ dữ liệu nhập vào
        nhanVien nv = new nhanVien(tenNV, gioiTinh, sdt, cccd, email, ngaySinh, trangThai);

        return nv;
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
        roundPanel1 = new swing.RoundPanel();
        roundPanel2 = new swing.RoundPanel();
        txtTen = new textfield.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtSDT = new textfield.TextField();
        txtCCCD = new textfield.TextField();
        cbbTrangThai = new combobox.Combobox();
        txtEmail = new textfield.TextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHienThi = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiem = new textfield.TextField();
        rdoDang = new javax.swing.JRadioButton();
        rdoAll = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        rdoNghi = new javax.swing.JRadioButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        cbbChucVu = new combobox.Combobox();
        jdcNgaySinh = new com.toedter.calendar.JDateChooser();

        setOpaque(false);

        txtTen.setLabelText("Họ tên");

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel2.setText("Thiết lập thông tin nhân viên");

        jLabel1.setText("Giới tính");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        txtSDT.setLabelText("SDT");

        txtCCCD.setLabelText("CCCD");

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Tất cả", "Đang làm", "Đã nghỉ" }));
        cbbTrangThai.setAutoscrolls(true);
        cbbTrangThai.setLabeText("Trạng thái");

        txtEmail.setLabelText("Email");

        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        tblHienThi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "Giới tính", "Số điện thoại", "CCCD", "Tên chức vụ", "Ngày sinh", "Email", "Trạng thái", "Hành động"
            }
        ));
        tblHienThi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHienThiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHienThi);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel3.setText("Danh sách nhân viên");

        txtTimKiem.setLabelText("Tìm kiếm theo mã NV, Email, Tên, Địa chỉ");

        buttonGroup2.add(rdoDang);
        rdoDang.setText("Đang làm");

        buttonGroup2.add(rdoAll);
        rdoAll.setText("Tất cả");

        jLabel4.setText("Trạng thái");

        buttonGroup2.add(rdoNghi);
        rdoNghi.setText("Đã nghỉ");

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        cbbChucVu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Quản lý", "Bán hàng", "Kế toán" }));
        cbbChucVu.setAutoscrolls(true);
        cbbChucVu.setLabeText("Chức vụ");
        cbbChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbChucVuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoDang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoNghi)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addGap(1224, 1224, 1224)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundPanel2Layout.createSequentialGroup()
                                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel2Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel1)
                                                .addGap(12, 12, 12)
                                                .addComponent(rdoNam)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rdoNu)))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cbbChucVu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                                                .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundPanel2Layout.createSequentialGroup()
                                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtCCCD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jdcNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(roundPanel2Layout.createSequentialGroup()
                                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(233, 233, 233))
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(cbbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(rdoAll)
                            .addComponent(rdoDang)
                            .addComponent(rdoNghi))))
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHienThiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHienThiMouseClicked
        // TODO add your handling code here:
        int row = tblHienThi.getSelectedRow();
        nhanVienViewModel NV = lists.get(row);
        txtTen.setText(NV.getTenNV());
        txtSDT.setText(NV.getSDT() + "");
        txtCCCD.setText(NV.getCCCD());
        txtEmail.setText(NV.getEmail());
        jdcNgaySinh.setDate(NV.getNgaySinh());
        cbbTrangThai.setSelectedItem(NV.getTrangThai());

        boolean gt;
        gt = NV.isGioiTinh();
        if (gt) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }

        cbbChucVu.setSelectedItem(NV.getTenChucVu());
    }//GEN-LAST:event_tblHienThiMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        nhanVien formData = getFormData();
        String tenChucVu = (String) cbbChucVu.getSelectedItem();
        String cccd = txtCCCD.getText().trim();
        if (check()) {
            if (service.isCCCDExisted(cccd)) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "CCCD đã tồn tại. Vui lòng nhập cccd khác!");
                return;
            }
            int option = JOptionPane.showConfirmDialog(btnThem, "Bạn có muốn thêm hay không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                service.add(formData, tenChucVu);
                lists = service.getAll();
                showDataTable(lists);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm thành công nhân viên mới!");
            }
        }


    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblHienThi.getSelectedRow();
        String tenChucVu = (String) cbbChucVu.getSelectedItem();
        if (selectedRow != -1) {
            nhanVien formData = getFormData();
            if (check()) {
                String cccd = txtCCCD.getText().trim();
                int selectedEmployesId = Integer.valueOf(lists.get(selectedRow).getId());

                if (service.isCCCDExistedForAnotherEmployess(cccd, selectedEmployesId)) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "CCCD đã tồn tại cho một nhân viên khác. Vui lòng nhập số CCCD khác!");
                    return;
                }
                int option = JOptionPane.showConfirmDialog(btnSua, "Bạn có muốn sửa hay không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    nhanVienViewModel NV = lists.get(selectedRow);
                    service.Update(formData, NV.getId(), tenChucVu);
                    lists = service.getAll();
                    showDataTable(lists);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa thông tin nhân viên thành công!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(btnSua, "Vui lòng chọn một dòng để sửa.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblHienThi.getSelectedRow();

        if (selectedRow != -1) {
            int option = JOptionPane.showConfirmDialog(btnXoa, "Bạn có muốn xóa hay không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                nhanVienViewModel NV = lists.get(selectedRow);
                service.Delete(NV.getId());
                lists = service.getAll();
                showDataTable(lists);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã xóa nhân viên!");
            }
        } else {
            JOptionPane.showMessageDialog(btnXoa, "Vui lòng chọn một dòng để xóa.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void cbbChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbChucVuActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbbChucVuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private combobox.Combobox cbbChucVu;
    private combobox.Combobox cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdcNgaySinh;
    private javax.swing.JRadioButton rdoAll;
    private javax.swing.JRadioButton rdoDang;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNghi;
    private javax.swing.JRadioButton rdoNu;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel2;
    private javax.swing.JTable tblHienThi;
    private textfield.TextField txtCCCD;
    private textfield.TextField txtEmail;
    private textfield.TextField txtSDT;
    private textfield.TextField txtTen;
    private textfield.TextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
