/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import backend.entity.PhieuGiamGia;
import backend.service.PhieuGiamGiaService;
import backend.viewmodel.PhieuGiamGiaViewModel;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import raven.toast.Notifications;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.management.Notification;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import ravenn.cell.TableActionCellEditor;
import ravenn.cell.TableActionCellRender;
import ravenn.cell.TableActionEvent;

/**
 *
 * @author leanb
 */
public class ViewPhieuGiamGia extends javax.swing.JPanel {

    /**
     * Creates new form HoaDon
     */
    private List<PhieuGiamGiaViewModel> lists = new ArrayList<>();
    private DefaultTableModel dtm = new DefaultTableModel();
    private PhieuGiamGiaService service = new PhieuGiamGiaService();

    private Date getFormDate(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    public ViewPhieuGiamGia() {
        initComponents();
        lists = service.getAll();
        dtm = (DefaultTableModel) tblHienThi.getModel();
        setCombobox();
        showDataTable(lists);

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                int choice = JOptionPane.showConfirmDialog(ViewPhieuGiamGia.this, "Bạn có chắc chắn muốn Sửa?", "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    if (check()) {
                        PhieuGiamGiaViewModel pgg = lists.get(row);
                        service.Sua(getFormData(), String.valueOf(pgg.getId()));
                        lists = service.getAll();
                        showDataTable(lists);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã sửa");
                    }
                } else if (choice == JOptionPane.NO_OPTION) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy");
                }
                clear();
            }

            @Override
            public void onDelete(int row) {
                int choice = JOptionPane.showConfirmDialog(ViewPhieuGiamGia.this, "Bạn có chắc chắn muốn Xóa?", "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
//            int row = tblHienThi.getSelectedRow();
                    PhieuGiamGiaViewModel pgg = lists.get(row);
                    service.Xoa(String.valueOf(pgg.getId()));
                    lists = service.getAll();
                    showDataTable(lists);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã xóa");

                } else if (choice == JOptionPane.NO_OPTION) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy");
                }
                clear();

            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
            }
        };
        tblHienThi.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender());
        tblHienThi.getColumnModel().getColumn(10).setCellEditor(new TableActionCellEditor(event));

//        tblHienThi.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
//                setHorizontalAlignment(SwingConstants.RIGHT);
//                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
//            }
//        });
    }

    public void showDataTable(List<PhieuGiamGiaViewModel> listPGG) {
        dtm.setRowCount(0);
        int n = 1;
        tblHienThi.setRowHeight(35);
        for (PhieuGiamGiaViewModel pgg : listPGG) {
            dtm.addRow(new Object[]{
                n++,
                pgg.getMaGiamGia(),
                pgg.getTenGiamGia(),
                pgg.getNgayBatDau(),
                pgg.getNgayKetThuc(),
                pgg.getSoLuong(),
                currencyFormat.format(pgg.getHoaDonToiThieu()), // Định dạng hóa đơn tối thiểu
                formatPercent(pgg.getSoPhanTramGiam()), // Định dạng phần trăm giảm
                currencyFormat.format(pgg.getGiamToiDa()), // Định dạng giảm tối đa
                pgg.getTrangThai(),});
        }
    }
    // định dạng tiền 

    private NumberFormat currencyFormat = new DecimalFormat("###,###,### VND");

    // để định dạng phần trăm
    private String formatPercent(float value) {
        return String.format("%.0f%%", value); // format lại số phần trăm giảm
    }

    public boolean check() {
        if (txtTen.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên trống");
            return false;
        }
        if (txtGiamToiDa.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Giảm tối đa trống");
            return false;
        }
        if (txtSoLuong.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng trống");
            return false;
        }
        if (txtHoaDon.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Hóa đơn trống");
            return false;
        }
        if (txtSoPhanTram.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Phần trăm giảm trống");
            return false;
        }

        // Kiểm tra ngày bắt đầu và kết thúc
        Date ngayBatDau = jdcNgayBatDau.getDate();
        Date ngayKetThuc = jdcNgayKetThuc.getDate();

        if (ngayBatDau == null || ngayKetThuc == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày bắt đầu hoặc Ngày kết thúc không hợp lệ");
            return false;
        }

        if (ngayKetThuc.before(ngayBatDau)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày kết thúc phải sau ngày bắt đầu");
            return false;
        }

        // check ngày hiện tại
        java.util.Date selectedDate = jdcNgayBatDau.getDate();

        // Chuyển đổi ngày thành LocalDate
        LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

//        if (localDate.isBefore(LocalDate.now())) {
//            // Nếu ngày bắt đầu trước ngày hiện tại, bạn có thể thực hiện các hành động phù hợp ở đây
//            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày bắt đầu không được trước ngày hiện tại");
//            return false;
//        }
        try {
            Float.parseFloat(txtGiamToiDa.getText());
            Float.parseFloat(txtHoaDon.getText());
            Float.parseFloat(txtSoPhanTram.getText());
            Integer.parseInt(txtSoLuong.getText());
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Giá trị không hợp lệ");
            return false;
        }
        return true;
    }

    private void setCombobox() {
        cbbTrangThai.removeAllItems();
        cbbTrangThai.addItem("Tất cả");
        cbbTrangThai.addItem("Đang diễn ra");
        cbbTrangThai.addItem("Chưa diễn ra");
        cbbTrangThai.addItem("Kết thúc");

    }

    public PhieuGiamGia getFormData() {
        String ten = txtTen.getText();
        Date ngayBD = jdcNgayBatDau.getDate();
        Date ngayKT = jdcNgayKetThuc.getDate();
        String soLuong = txtSoLuong.getText();
        String hoaDon = txtHoaDon.getText();
        String phanTram = txtSoPhanTram.getText();
        String giam = txtGiamToiDa.getText();
        String trangThai;
        int idNV = 2;

        // kết thúc
        java.util.Date local = jdcNgayKetThuc.getDate();
        LocalDate kt = local.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // hiện tại
        java.util.Date selectedDate = jdcNgayBatDau.getDate();
        LocalDate ht = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (kt.isBefore(LocalDate.now())) {
            trangThai = "Kết thúc";
        } else {
            trangThai = "Đang diễn ra";
        }
        if (ht.isAfter(LocalDate.now())) {
            trangThai = "Chưa diễn ra";
        }

        PhieuGiamGia pgg = new PhieuGiamGia(ten, ngayBD, ngayKT, Integer.valueOf(soLuong), Float.valueOf(hoaDon), Float.valueOf(phanTram), Float.valueOf(giam), trangThai, idNV);
        return pgg;
    }

    private void clear() {
        txtTen.setText("");
        jdcNgayBatDau.setDate(null);
        jdcNgayKetThuc.setDate(null);
        txtSoLuong.setText("");
        txtHoaDon.setText("");
        txtSoPhanTram.setText("");
        txtGiamToiDa.setText("");
        cbbTrangThai.setSelectedIndex(0);
    }

    private void capNhatTrangThai() {
        LocalDate ngayHienTai = LocalDate.now();
        for (PhieuGiamGiaViewModel pgg : lists) {
            LocalDate ngayBatDau = pgg.getNgayBatDau().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ngayKetThuc = pgg.getNgayKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String trangThai;
            if (ngayHienTai.isBefore(ngayBatDau)) {
                trangThai = "Chưa diễn ra";
            } else if (ngayHienTai.isAfter(ngayKetThuc)) {
                trangThai = "Kết thúc";
            } else {
                trangThai = "Đang diễn ra";
            }
            pgg.setTrangThai(trangThai);
        }
        showDataTable(lists);
    }

    private void startAutoUpdateTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> capNhatTrangThai());
            }
        };
        // Thiết lập lịch trình cho timer (ví dụ: cập nhật mỗi 1 phút)
        timer.scheduleAtFixedRate(task, 0, 60000); // 60000 milliseconds = 1 phút
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHienThi = new javax.swing.JTable();
        roundPanel4 = new swing.RoundPanel();
        txtTen = new textfield.TextField();
        txtSoLuong = new textfield.TextField();
        txtSoPhanTram = new textfield.TextField();
        txtHoaDon = new textfield.TextField();
        jdcNgayBatDau = new com.toedter.calendar.JDateChooser();
        jdcNgayKetThuc = new com.toedter.calendar.JDateChooser();
        txtGiamToiDa = new textfield.TextField();
        jPanel1 = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        cbbTrangThai = new combobox.Combobox();
        txtTim = new textfield.TextField();

        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel1.setText("Phiếu giảm giá");

        tblHienThi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã", "Tên", "Ngày BĐ", "Ngày KT", "Số lượng", "Hóa đơn tối thiểu", "Số % giảm", "Giảm tối đa", "Trạng thái", "Hành động"
            }
        ));
        tblHienThi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHienThiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHienThi);

        roundPanel4.setLayout(new java.awt.GridLayout(3, 10, 10, 10));

        txtTen.setLabelText("Tên khuyến mãi");
        roundPanel4.add(txtTen);

        txtSoLuong.setLabelText("Số lượng được phép sử dụng");
        roundPanel4.add(txtSoLuong);

        txtSoPhanTram.setLabelText("Phần trăm giảm");
        roundPanel4.add(txtSoPhanTram);

        txtHoaDon.setLabelText("Hóa đơn tối thiểu");
        roundPanel4.add(txtHoaDon);

        jdcNgayBatDau.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Ngày bắt đầu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(102, 102, 102))); // NOI18N
        jdcNgayBatDau.setOpaque(false);
        roundPanel4.add(jdcNgayBatDau);

        jdcNgayKetThuc.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Ngày kết thúc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(102, 102, 102))); // NOI18N
        jdcNgayKetThuc.setOpaque(false);
        roundPanel4.add(jdcNgayKetThuc);

        txtGiamToiDa.setLabelText("Số tiền giảm tối đa");
        roundPanel4.add(txtGiamToiDa);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel1.add(btnXoa);

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel1.add(btnSua);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel1.add(btnThem);

        cbbTrangThai.setLabeText("Trạng thái");
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        txtTim.setLabelText("Tìm kiếm ");
        txtTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimActionPerformed(evt);
            }
        });
        txtTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(5, 5, 5))))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1407, Short.MAX_VALUE)
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addGap(46, 46, 46))
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

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (check()) {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm?", "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                service.add(getFormData());
                lists = service.getAll();
                showDataTable(lists);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã thêm");
            } else if (choice == JOptionPane.NO_OPTION) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy");
            }
            clear();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int row = tblHienThi.getSelectedRow();
        if (row > -1) {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn Sửa?", "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                if (check()) {
                    PhieuGiamGiaViewModel pgg = lists.get(row);
                    service.Sua(getFormData(), String.valueOf(pgg.getId()));
                    lists = service.getAll();
                    showDataTable(lists);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã sửa");
                }
            } else if (choice == JOptionPane.NO_OPTION) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy");
            }
            clear();
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa chọn dữ liệu để sửa");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblHienThiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHienThiMouseClicked
        // TODO add your handling code here:
        int row = tblHienThi.getSelectedRow();
        if (row >= 0 && row < lists.size()) {
            PhieuGiamGiaViewModel pgg = lists.get(row);
            txtTen.setText(pgg.getTenGiamGia());
            txtGiamToiDa.setText(String.format("%.0f", pgg.getGiamToiDa())); // Định dạng giảm tối đa
            txtHoaDon.setText(String.format("%.0f", pgg.getHoaDonToiThieu())); // Định dạng hóa đơn tối thiểu và thêm kí hiệu "$" vào sau
            if (pgg.getNgayBatDau() != null) {
                jdcNgayBatDau.setDate(pgg.getNgayBatDau());
            } else {
                jdcNgayBatDau.setDate(null);
            }
            if (pgg.getNgayKetThuc() != null) {
                jdcNgayKetThuc.setDate(pgg.getNgayKetThuc());
            } else {
                jdcNgayKetThuc.setDate(null);
            }
            txtSoLuong.setText(String.valueOf(pgg.getSoLuong()));
            txtSoPhanTram.setText(String.format("%.0f", pgg.getSoPhanTramGiam())); // Định dạng phần trăm giảm

        }
    }//GEN-LAST:event_tblHienThiMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int row = tblHienThi.getSelectedRow();
        if (row > -1) {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn Xóa?", "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {

                PhieuGiamGiaViewModel pgg = lists.get(row);
                service.Xoa(String.valueOf(pgg.getId()));
                lists = service.getAll();
                showDataTable(lists);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã xóa");

            } else if (choice == JOptionPane.NO_OPTION) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đã hủy");
            }
            clear();
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa chọn dữ liệu để xóa");
        }


    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimActionPerformed

    private void txtTimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKeyReleased
        // TODO add your handling code here:
        showDataTable(service.search(txtTim.getText()));
    }//GEN-LAST:event_txtTimKeyReleased

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed
        // TODO add your handling code here:
        String chon = cbbTrangThai.getSelectedItem().toString();

        if (chon.equals("Tất cả")) {
            showDataTable(service.getAll());
        } else {
            showDataTable(service.trangThai(chon));
        }
    }//GEN-LAST:event_cbbTrangThaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private combobox.Combobox cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcNgayBatDau;
    private com.toedter.calendar.JDateChooser jdcNgayKetThuc;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel4;
    private javax.swing.JTable tblHienThi;
    private textfield.TextField txtGiamToiDa;
    private textfield.TextField txtHoaDon;
    private textfield.TextField txtSoLuong;
    private textfield.TextField txtSoPhanTram;
    private textfield.TextField txtTen;
    private textfield.TextField txtTim;
    // End of variables declaration//GEN-END:variables
}
