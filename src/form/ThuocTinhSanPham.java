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
import backend.service.CoAoService;
import backend.service.DangAoService;
import backend.service.DanhMucService;
import backend.service.DuoiAoService;
import backend.service.MauSacService;
import backend.service.NSXService;
import backend.service.SizeService;
import backend.service.TayAoService;
import backend.service.ThuonHieuService;
import backend.service.XuatXuService;
import java.util.ArrayList;
import java.util.List;
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
public class ThuocTinhSanPham extends javax.swing.JPanel {

    DefaultTableModel dtmMauSac = new DefaultTableModel();
    List<MauSac> mausac = new ArrayList<>();
    MauSacService mauSacService = new MauSacService();

    DefaultTableModel dtmChatLieu = new DefaultTableModel();
    List<ChatLieu> chatLieus = new ArrayList<>();
    ChatLieuService chatLieuService = new ChatLieuService();

    DefaultTableModel dtmXuatXu = new DefaultTableModel();
    List<XuatXu> xuatXus = new ArrayList<>();
    XuatXuService xuatXuService = new XuatXuService();

    DefaultTableModel dtmSize = new DefaultTableModel();
    List<Size> sizes = new ArrayList<>();
    SizeService sizeService = new SizeService();

    DefaultTableModel dtmNSX = new DefaultTableModel();
    List<NSX> nsxs = new ArrayList<>();
    NSXService nsxService = new NSXService();

    DefaultTableModel dtmThuongHieu = new DefaultTableModel();
    List<ThuongHieu> thuongHieus = new ArrayList<>();
    ThuonHieuService thuongHieuService = new ThuonHieuService();

    DefaultTableModel dtmDanhMuc = new DefaultTableModel();
    List<DanhMuc> danhMucs = new ArrayList<>();
    DanhMucService danhMucService = new DanhMucService();

    DefaultTableModel dtmCoAo = new DefaultTableModel();
    List<CoAo> coAos = new ArrayList<>();
    CoAoService coAoService = new CoAoService();

    DefaultTableModel dtmDuoiAo = new DefaultTableModel();
    List<DuoiAo> duoiAos = new ArrayList<>();
    DuoiAoService duoiAoService = new DuoiAoService();

    DefaultTableModel dtmTayAo = new DefaultTableModel();
    List<TayAo> tayAos = new ArrayList<>();
    TayAoService tayAoService = new TayAoService();

    DefaultTableModel dtmDangAo = new DefaultTableModel();
    List<DangAo> dangAos = new ArrayList();
    DangAoService dangAoService = new DangAoService();

    /**
     * Creates new form ThuocTinhSanPham
     */
    public ThuocTinhSanPham() {
        initComponents();
        rdoDanhMuc.setSelected(true);
        danhMucs = danhMucService.getAll();
        dtmDanhMuc = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableDanhMuc(danhMucs);

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                if (rdoDanhMuc.isSelected()) {
                    DanhMuc danhMuc = getFormDataDanhMuc();
                    if (danhMuc != null) {
                        DanhMuc danhMuc1 = danhMucs.get(row);
                        danhMucService.update(danhMuc, danhMuc1.getId() + "");
                        danhMucs = danhMucService.getAll();
                        showDataTableDanhMuc(danhMucs);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa danh mục thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoXuatXu.isSelected()) {
                    XuatXu xuatXu = getFormDataXuatXu();
                    if (xuatXu != null) {
                        XuatXu danhMuc1 = xuatXus.get(row);
                        xuatXuService.update(xuatXu, danhMuc1.getId() + "");
                        xuatXus = xuatXuService.getAll();
                        showDataTableXuatXu(xuatXus);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa xuất xứ thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoNSX.isSelected()) {
                    NSX nsx = getFormDataNSX();
                    if (nsx != null) {
                        NSX nsxz = nsxs.get(row);
                        nsxService.update(nsx, nsxz.getId() + "");
                        nsxs = nsxService.getAll();
                        showDataTableNSX(nsxs);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa nhà sản xuất thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoMauSac.isSelected()) {
                    MauSac mauSac = getFormDataMauSac();
                    if (mauSac != null) {
                        MauSac mauSac1 = mausac.get(row);
                        mauSacService.update(mauSac, mauSac1.getId() + "");
                        mausac = mauSacService.getAll();
                        ShowDataTableMauSac(mausac);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa màu sắc thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoSize.isSelected()) {
                    Size size = getFormDataSize();
                    if (size != null) {
                        Size size1 = sizes.get(row);
                        sizeService.update(size, size1.getId() + "");
                        sizes = sizeService.getAll();
                        showDataTableSize(sizes);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa size thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoThuongHieu.isSelected()) {
                    ThuongHieu thuongHieu = getFormDataThuongHieu();
                    if (thuongHieu != null) {
                        ThuongHieu thuongHieu1 = thuongHieus.get(row);
                        thuongHieuService.update(thuongHieu, thuongHieu1.getId() + "");
                        thuongHieus = thuongHieuService.getAll();
                        showDataTableThuongHieu(thuongHieus);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa thương hiệu thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoCoAo.isSelected()) {
                    CoAo coAo = getFormDataCoAo();
                    if (coAo != null) {
                        CoAo coAo1 = coAos.get(row);
                        coAoService.update(coAo, coAo1.getId() + "");
                        coAos = coAoService.getAll();
                        showDataTableCoAo(coAos);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa cổ áo thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoDuoiAo.isSelected()) {
                    DuoiAo duoiAo = getFormDataDuoiAo();
                    if (duoiAo != null) {
                        DuoiAo duoiAo1 = duoiAos.get(row);
                        duoiAoService.update(duoiAo, duoiAo1.getId() + "");
                        duoiAos = duoiAoService.getAll();
                        showDataTableDuoiAo(duoiAos);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa dưới áo thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoTayAo.isSelected()) {
                    TayAo tayAo = getFormDataTayAo();
                    if (tayAo != null) {
                        TayAo tayAo1 = tayAos.get(row);
                        tayAoService.update(tayAo, tayAo1.getId() + "");
                        tayAos = tayAoService.getAll();
                        showDataTableTayAo(tayAos);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa tay áo thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoDangAo.isSelected()) {
                    DangAo dangAo = getFormDataDangAo();
                    if (dangAo != null) {;
                        DangAo dangAo1 = dangAos.get(row);
                        dangAoService.update(dangAo, dangAo1.getId() + "");
                        dangAos = dangAoService.getAll();
                        showDataTableDangAo(dangAos);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa dáng áo thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoChatLieu.isSelected()) {
                    ChatLieu chatLieu = getFormDataChatLieu();
                    if (chatLieu != null) {
                        ChatLieu chatLieu1 = chatLieus.get(row);
                        chatLieuService.update(chatLieu, chatLieu1.getId() + "");
                        chatLieus = chatLieuService.getAll();
                        showDataTableChatLieu(chatLieus);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa chất liệu thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                }
            }

            @Override
            public void onDelete(int row) {
                if (rdoDanhMuc.isSelected()) {
                    DanhMuc danhMuc = getFormDataDanhMuc();
                    if (danhMuc != null) {
                        DanhMuc danhMuc1 = danhMucs.get(row);
                        danhMucService.delete(danhMuc1.getId() + "");
                        danhMucs = danhMucService.getAll();
                        showDataTableDanhMuc(danhMucs);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa danh mục thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoXuatXu.isSelected()) {
                    XuatXu xuatXu = getFormDataXuatXu();
                    if (xuatXu != null) {
                        XuatXu danhMuc1 = xuatXus.get(row);
                        xuatXuService.delete(danhMuc1.getId() + "");
                        xuatXus = xuatXuService.getAll();
                        showDataTableXuatXu(xuatXus);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa xuất xứ thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoNSX.isSelected()) {
                    NSX nsx = getFormDataNSX();
                    if (nsx != null) {
                        NSX nsxz = nsxs.get(row);
                        nsxService.delete(nsxz.getId() + "");
                        nsxs = nsxService.getAll();
                        showDataTableNSX(nsxs);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa nhà sản xuất thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoMauSac.isSelected()) {
                    MauSac mauSac = getFormDataMauSac();
                    if (mauSac != null) {
                        MauSac mauSac1 = mausac.get(row);
                        mauSacService.delete(mauSac1.getId() + "");
                        mausac = mauSacService.getAll();
                        ShowDataTableMauSac(mausac);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa màu sắc thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoSize.isSelected()) {
                    Size size = getFormDataSize();
                    if (size != null) {
                        Size size1 = sizes.get(row);
                        sizeService.delete(size1.getId() + "");
                        sizes = sizeService.getAll();
                        showDataTableSize(sizes);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa size thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoThuongHieu.isSelected()) {
                    ThuongHieu thuongHieu = getFormDataThuongHieu();
                    if (thuongHieu != null) {
                        ThuongHieu thuongHieu1 = thuongHieus.get(row);
                        thuongHieuService.delete(thuongHieu1.getId() + "");
                        thuongHieus = thuongHieuService.getAll();
                        showDataTableThuongHieu(thuongHieus);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thương hiệu thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoCoAo.isSelected()) {
                    CoAo coAo = getFormDataCoAo();
                    if (coAo != null) {
                        CoAo coAo1 = coAos.get(row);
                        coAoService.delete(coAo1.getId() + "");
                        coAos = coAoService.getAll();
                        showDataTableCoAo(coAos);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa cổ áo thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoDuoiAo.isSelected()) {
                    DuoiAo duoiAo = getFormDataDuoiAo();
                    if (duoiAo != null) {
                        DuoiAo duoiAo1 = duoiAos.get(row);
                        duoiAoService.delete(duoiAo1.getId() + "");
                        duoiAos = duoiAoService.getAll();
                        showDataTableDuoiAo(duoiAos);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa dưới áo thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoTayAo.isSelected()) {
                    TayAo tayAo = getFormDataTayAo();
                    if (tayAo != null) {
                        TayAo tayAo1 = tayAos.get(row);
                        tayAoService.delete(tayAo1.getId() + "");
                        tayAos = tayAoService.getAll();
                        showDataTableTayAo(tayAos);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa tay áo thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoDangAo.isSelected()) {
                    DangAo dangAo = getFormDataDangAo();
                    if (dangAo != null) {
                        DangAo dangAo1 = dangAos.get(row);
                        dangAoService.delete(dangAo1.getId() + "");
                        dangAos = dangAoService.getAll();
                        showDataTableDangAo(dangAos);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa dáng áo thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                } else if (rdoChatLieu.isSelected()) {
                    ChatLieu chatLieu = getFormDataChatLieu();
                    if (chatLieu != null) {

                        ChatLieu chatLieu1 = chatLieus.get(row);
                        chatLieuService.delete(chatLieu1.getId() + "");
                        chatLieus = chatLieuService.getAll();
                        showDataTableChatLieu(chatLieus);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa chất liệu thành công");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                    }
                }

            }
        };
        tblThuocTinh.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
        tblThuocTinh.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
    }

    public void ShowDataTableMauSac(List<MauSac> mauSacs) {
        dtmMauSac.setRowCount(0);
        for (MauSac mauSac : mauSacs) {
            dtmMauSac.addRow(new Object[]{mauSac.getSTT(), mauSac.getMaMauSac(), mauSac.getTenMauSac()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableChatLieu(List<ChatLieu> chatLieus) {
        dtmChatLieu.setRowCount(0);
        for (ChatLieu chatLieu : chatLieus) {
            dtmChatLieu.addRow(new Object[]{chatLieu.getSTT(), chatLieu.getMaChatLieu(), chatLieu.getTenChatLieu()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableXuatXu(List<XuatXu> xuatXus) {
        dtmXuatXu.setRowCount(0);
        for (XuatXu xuatXu : xuatXus) {
            dtmXuatXu.addRow(new Object[]{xuatXu.getSTT(), xuatXu.getMaXuatXu(), xuatXu.getTenXuatXu()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableSize(List<Size> sizes) {
        dtmSize.setRowCount(0);
        for (Size size : sizes) {
            dtmSize.addRow(new Object[]{size.getSTT(), size.getMaSize(), size.getTenSize()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableNSX(List<NSX> nsxs) {
        dtmNSX.setRowCount(0);
        for (NSX nsx : nsxs) {
            dtmNSX.addRow(new Object[]{nsx.getSTT(), nsx.getMaNSX(), nsx.getTenNSX()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableThuongHieu(List<ThuongHieu> thuongHieus) {
        dtmThuongHieu.setRowCount(0);
        for (ThuongHieu thuongHieu : thuongHieus) {
            dtmThuongHieu.addRow(new Object[]{thuongHieu.getSTT(), thuongHieu.getMaThuongHieu(), thuongHieu.getTenThuongHieu()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableDanhMuc(List<DanhMuc> danhMucs) {
        dtmDanhMuc.setRowCount(0);
        for (DanhMuc danhMuc : danhMucs) {
            dtmDanhMuc.addRow(new Object[]{danhMuc.getSTT(), danhMuc.getMaDanhMuc(), danhMuc.getTenDanhMuc()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableCoAo(List<CoAo> coAos) {
        dtmCoAo.setRowCount(0);
        for (CoAo coAo : coAos) {
            dtmCoAo.addRow(new Object[]{coAo.getSTT(), coAo.getMaCoAo(), coAo.getTenCoAo()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableDuoiAo(List<DuoiAo> duoiAos) {
        dtmDuoiAo.setRowCount(0);
        for (DuoiAo duoiAo1 : duoiAos) {
            dtmDuoiAo.addRow(new Object[]{duoiAo1.getSTT(), duoiAo1.getMaDuoiAo(), duoiAo1.getTenDuoiAo()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableTayAo(List<TayAo> tayAos) {
        dtmTayAo.setRowCount(0);
        for (TayAo tayAo : tayAos) {
            dtmTayAo.addRow(new Object[]{tayAo.getSTT(), tayAo.getMaTayAo(), tayAo.getTenTayAo()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public void showDataTableDangAo(List<DangAo> dangAos) {
        dtmDangAo.setRowCount(0);
        for (DangAo dangAo1 : dangAos) {
            dtmDangAo.addRow(new Object[]{dangAo1.getSTT(), dangAo1.getMaDangAo(), dangAo1.getTenDangAo()});
        }
        tblThuocTinh.setRowHeight(40);
    }

    public MauSac getFormDataMauSac() {
        String maMauSac = txtMaThuocTinh.getText().trim();
        String tenMauSac = txtTenThuocTinh.getText().trim();

        if (maMauSac.isEmpty() || tenMauSac.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new MauSac(maMauSac, tenMauSac);
    }

    public ChatLieu getFormDataChatLieu() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new ChatLieu(ma, ten);
    }

    public XuatXu getFormDataXuatXu() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new XuatXu(ma, ten);
    }

    public Size getFormDataSize() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new Size(ma, ten);
    }

    public NSX getFormDataNSX() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new NSX(ma, ten);
    }

    public ThuongHieu getFormDataThuongHieu() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new ThuongHieu(ma, ten);
    }

    public DanhMuc getFormDataDanhMuc() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new DanhMuc(ma, ten);
    }

    public CoAo getFormDataCoAo() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new CoAo(ma, ten);
    }

    public DuoiAo getFormDataDuoiAo() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new DuoiAo(ma, ten);
    }

    public TayAo getFormDataTayAo() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new TayAo(ma, ten);
    }

    public DangAo getFormDataDangAo() {
        String ma = txtMaThuocTinh.getText().trim();
        String ten = txtTenThuocTinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        return new DangAo(ma, ten);
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
        roundPanel2 = new swing.RoundPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        rdoDanhMuc = new javax.swing.JRadioButton();
        rdoXuatXu = new javax.swing.JRadioButton();
        rdoNSX = new javax.swing.JRadioButton();
        rdoMauSac = new javax.swing.JRadioButton();
        rdoSize = new javax.swing.JRadioButton();
        rdoThuongHieu = new javax.swing.JRadioButton();
        rdoCoAo = new javax.swing.JRadioButton();
        rdoDuoiAo = new javax.swing.JRadioButton();
        rdoTayAo = new javax.swing.JRadioButton();
        rdoDangAo = new javax.swing.JRadioButton();
        rdoChatLieu = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtMaThuocTinh = new textfield.TextField();
        txtTenThuocTinh = new textfield.TextField();
        jPanel5 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel7.setText("Danh sách thuộc tính");

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã thuộc tính", "Tên thuộc tính", "Hành động"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblThuocTinh);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(2, 30, 50, 10));

        buttonGroup1.add(rdoDanhMuc);
        rdoDanhMuc.setText("Danh mục");
        rdoDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDanhMucMouseClicked(evt);
            }
        });
        rdoDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDanhMucActionPerformed(evt);
            }
        });
        jPanel2.add(rdoDanhMuc);

        buttonGroup1.add(rdoXuatXu);
        rdoXuatXu.setText("Xuất xứ");
        rdoXuatXu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoXuatXuMouseClicked(evt);
            }
        });
        rdoXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoXuatXuActionPerformed(evt);
            }
        });
        jPanel2.add(rdoXuatXu);

        buttonGroup1.add(rdoNSX);
        rdoNSX.setText("Nhà sản xuất");
        rdoNSX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoNSXMouseClicked(evt);
            }
        });
        jPanel2.add(rdoNSX);

        buttonGroup1.add(rdoMauSac);
        rdoMauSac.setText("Màu sắc");
        rdoMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoMauSacMouseClicked(evt);
            }
        });
        jPanel2.add(rdoMauSac);

        buttonGroup1.add(rdoSize);
        rdoSize.setText("Size");
        rdoSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSizeMouseClicked(evt);
            }
        });
        jPanel2.add(rdoSize);

        buttonGroup1.add(rdoThuongHieu);
        rdoThuongHieu.setText("Thương hiệu");
        rdoThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoThuongHieuMouseClicked(evt);
            }
        });
        jPanel2.add(rdoThuongHieu);

        buttonGroup1.add(rdoCoAo);
        rdoCoAo.setText("Cổ áo");
        rdoCoAo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoCoAoMouseClicked(evt);
            }
        });
        rdoCoAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCoAoActionPerformed(evt);
            }
        });
        jPanel2.add(rdoCoAo);

        buttonGroup1.add(rdoDuoiAo);
        rdoDuoiAo.setText("Đuôi áo");
        rdoDuoiAo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDuoiAoMouseClicked(evt);
            }
        });
        jPanel2.add(rdoDuoiAo);

        buttonGroup1.add(rdoTayAo);
        rdoTayAo.setText("Tay áo");
        rdoTayAo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTayAoMouseClicked(evt);
            }
        });
        jPanel2.add(rdoTayAo);

        buttonGroup1.add(rdoDangAo);
        rdoDangAo.setText("Dáng áo");
        rdoDangAo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDangAoMouseClicked(evt);
            }
        });
        jPanel2.add(rdoDangAo);

        buttonGroup1.add(rdoChatLieu);
        rdoChatLieu.setText("Chất liệu");
        rdoChatLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoChatLieuMouseClicked(evt);
            }
        });
        jPanel2.add(rdoChatLieu);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        txtMaThuocTinh.setLabelText("Mã Thuộc tính");
        jPanel4.add(txtMaThuocTinh);

        txtTenThuocTinh.setLabelText("Tên thuộc tính");
        jPanel4.add(txtTenThuocTinh);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 3, 30));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel5.add(btnThem);

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel5.add(btnSua);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel5.add(btnXoa);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 956, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(235, 235, 235))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 712, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        int row = tblThuocTinh.getSelectedRow();
        if (row >= 0) {
            if (rdoMauSac.isSelected()) {
                // Lấy dòng được chọn từ danh sách màu sắc
                MauSac mauSac = mausac.get(row);
                // Hiển thị thông tin của MauSac
                txtMaThuocTinh.setText(mauSac.getMaMauSac());
                txtTenThuocTinh.setText(mauSac.getTenMauSac());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoSize.isSelected()) {
                // Lấy dòng được chọn từ danh sách size
                Size size = sizes.get(row);
                // Hiển thị thông tin của Size
                txtMaThuocTinh.setText(size.getMaSize());
                txtTenThuocTinh.setText(size.getTenSize());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoDanhMuc.isSelected()) {
                // Lấy dòng được chọn từ danh sách danh mục
                DanhMuc danhMuc = danhMucs.get(row);
                // Hiển thị thông tin của DanhMuc
                txtMaThuocTinh.setText(danhMuc.getMaDanhMuc());
                txtTenThuocTinh.setText(danhMuc.getTenDanhMuc());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoXuatXu.isSelected()) {
                // Lấy dòng được chọn từ danh sách xuất xứ
                XuatXu xuatXu = xuatXus.get(row);
                // Hiển thị thông tin của XuatXu
                txtMaThuocTinh.setText(xuatXu.getMaXuatXu());
                txtTenThuocTinh.setText(xuatXu.getTenXuatXu());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoNSX.isSelected()) {
                // Lấy dòng được chọn từ danh sách NSX
                NSX nsx = nsxs.get(row);
                // Hiển thị thông tin của NSX
                txtMaThuocTinh.setText(nsx.getMaNSX());
                txtTenThuocTinh.setText(nsx.getTenNSX());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoChatLieu.isSelected()) {
                // Lấy dòng được chọn từ danh sách chất liệu
                ChatLieu chatLieu = chatLieus.get(row);
                // Hiển thị thông tin của ChatLieu
                txtMaThuocTinh.setText(chatLieu.getMaChatLieu());
                txtTenThuocTinh.setText(chatLieu.getTenChatLieu());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoCoAo.isSelected()) {
                // Lấy dòng được chọn từ danh sách cỡ áo
                CoAo coAo = coAos.get(row);
                // Hiển thị thông tin của CoAo
                txtMaThuocTinh.setText(coAo.getMaCoAo());
                txtTenThuocTinh.setText(coAo.getTenCoAo());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoDangAo.isSelected()) {
                // Lấy dòng được chọn từ danh sách dáng áo
                DangAo dangAo = dangAos.get(row);
                // Hiển thị thông tin của DangAo
                txtMaThuocTinh.setText(dangAo.getMaDangAo());
                txtTenThuocTinh.setText(dangAo.getTenDangAo());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoDuoiAo.isSelected()) {
                // Lấy dòng được chọn từ danh sách đuôi áo
                DuoiAo duoiAo = duoiAos.get(row);
                // Hiển thị thông tin của DuoiAo
                txtMaThuocTinh.setText(duoiAo.getMaDuoiAo());
                txtTenThuocTinh.setText(duoiAo.getTenDuoiAo());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoTayAo.isSelected()) {
                // Lấy dòng được chọn từ danh sách tay áo
                TayAo tayAo = tayAos.get(row);
                // Hiển thị thông tin của TayAo
                txtMaThuocTinh.setText(tayAo.getMaTayAo());
                txtTenThuocTinh.setText(tayAo.getTenTayAo());
                // Thực hiện các xử lý khác nếu cần
            } else if (rdoThuongHieu.isSelected()) {
                // Lấy dòng được chọn từ danh sách thương hiệu
                ThuongHieu thuongHieu = thuongHieus.get(row);
                // Hiển thị thông tin của ThuongHieu
                txtMaThuocTinh.setText(thuongHieu.getMaThuongHieu());
                txtTenThuocTinh.setText(thuongHieu.getTenThuongHieu());
                // Thực hiện các xử lý khác nếu cần
            }
        }

    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void rdoDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDanhMucActionPerformed

    private void rdoXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoXuatXuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoXuatXuActionPerformed

    private void rdoDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDanhMucMouseClicked
        danhMucs = danhMucService.getAll();
        dtmDanhMuc = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableDanhMuc(danhMucs);
    }//GEN-LAST:event_rdoDanhMucMouseClicked

    private void rdoXuatXuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoXuatXuMouseClicked
        xuatXus = xuatXuService.getAll();
        dtmXuatXu = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableXuatXu(xuatXus);
    }//GEN-LAST:event_rdoXuatXuMouseClicked

    private void rdoNSXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoNSXMouseClicked
        nsxs = nsxService.getAll();
        dtmNSX = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableNSX(nsxs);
    }//GEN-LAST:event_rdoNSXMouseClicked

    private void rdoMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoMauSacMouseClicked
        mausac = mauSacService.getAll();
        dtmMauSac = (DefaultTableModel) tblThuocTinh.getModel();
        ShowDataTableMauSac(mausac);
    }//GEN-LAST:event_rdoMauSacMouseClicked

    private void rdoSizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSizeMouseClicked
        sizes = sizeService.getAll();
        dtmSize = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableSize(sizes);
    }//GEN-LAST:event_rdoSizeMouseClicked

    private void rdoThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoThuongHieuMouseClicked
        thuongHieus = thuongHieuService.getAll();
        dtmThuongHieu = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableThuongHieu(thuongHieus);
    }//GEN-LAST:event_rdoThuongHieuMouseClicked

    private void rdoCoAoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoCoAoMouseClicked
        coAos = coAoService.getAll();
        dtmCoAo = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableCoAo(coAos);
    }//GEN-LAST:event_rdoCoAoMouseClicked

    private void rdoCoAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCoAoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoCoAoActionPerformed

    private void rdoDuoiAoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDuoiAoMouseClicked
        duoiAos = duoiAoService.getAll();
        dtmDuoiAo = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableDuoiAo(duoiAos);
    }//GEN-LAST:event_rdoDuoiAoMouseClicked

    private void rdoTayAoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTayAoMouseClicked
        tayAos = tayAoService.getAll();
        dtmTayAo = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableTayAo(tayAos);
    }//GEN-LAST:event_rdoTayAoMouseClicked

    private void rdoDangAoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDangAoMouseClicked
        dangAos = dangAoService.getAll();
        dtmDangAo = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableDangAo(dangAos);
    }//GEN-LAST:event_rdoDangAoMouseClicked

    private void rdoChatLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoChatLieuMouseClicked
        chatLieus = chatLieuService.getAll();
        dtmChatLieu = (DefaultTableModel) tblThuocTinh.getModel();
        showDataTableChatLieu(chatLieus);
    }//GEN-LAST:event_rdoChatLieuMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm dữ liệu không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (rdoDanhMuc.isSelected()) {
                DanhMuc danhMuc = getFormDataDanhMuc();
                if (danhMuc != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    DanhMuc danhMuc1 = danhMucs.get(row);
                    danhMucService.update(danhMuc, danhMuc1.getId() + "");
                    danhMucs = danhMucService.getAll();
                    showDataTableDanhMuc(danhMucs);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa danh mục thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoXuatXu.isSelected()) {
                XuatXu xuatXu = getFormDataXuatXu();
                if (xuatXu != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    XuatXu danhMuc1 = xuatXus.get(row);
                    xuatXuService.update(xuatXu, danhMuc1.getId() + "");
                    xuatXus = xuatXuService.getAll();
                    showDataTableXuatXu(xuatXus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa xuất xứ thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoNSX.isSelected()) {
                NSX nsx = getFormDataNSX();
                if (nsx != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    NSX nsxz = nsxs.get(row);
                    nsxService.update(nsx, nsxz.getId() + "");
                    nsxs = nsxService.getAll();
                    showDataTableNSX(nsxs);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa nhà sản xuất thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoMauSac.isSelected()) {
                MauSac mauSac = getFormDataMauSac();
                if (mauSac != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    MauSac mauSac1 = mausac.get(row);
                    mauSacService.update(mauSac, mauSac1.getId() + "");
                    mausac = mauSacService.getAll();
                    ShowDataTableMauSac(mausac);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoSize.isSelected()) {
                Size size = getFormDataSize();
                if (size != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    Size size1 = sizes.get(row);
                    sizeService.update(size, size1.getId() + "");
                    sizes = sizeService.getAll();
                    showDataTableSize(sizes);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa size thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoThuongHieu.isSelected()) {
                ThuongHieu thuongHieu = getFormDataThuongHieu();
                if (thuongHieu != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    ThuongHieu thuongHieu1 = thuongHieus.get(row);
                    thuongHieuService.update(thuongHieu, thuongHieu1.getId() + "");
                    thuongHieus = thuongHieuService.getAll();
                    showDataTableThuongHieu(thuongHieus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa thương hiệu thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoCoAo.isSelected()) {
                CoAo coAo = getFormDataCoAo();
                if (coAo != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    CoAo coAo1 = coAos.get(row);
                    coAoService.update(coAo, coAo1.getId() + "");
                    coAos = coAoService.getAll();
                    showDataTableCoAo(coAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa cổ áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoDuoiAo.isSelected()) {
                DuoiAo duoiAo = getFormDataDuoiAo();
                if (duoiAo != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    DuoiAo duoiAo1 = this.duoiAos.get(row);
                    duoiAoService.update(duoiAo, duoiAo1.getId() + "");
                    this.duoiAos = duoiAoService.getAll();
                    showDataTableDuoiAo(this.duoiAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa dưới áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoTayAo.isSelected()) {
                TayAo tayAo = getFormDataTayAo();
                if (tayAo != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    TayAo tayAo1 = tayAos.get(row);
                    tayAoService.update(tayAo, tayAo1.getId() + "");
                    tayAos = tayAoService.getAll();
                    showDataTableTayAo(tayAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa tay áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoDangAo.isSelected()) {
                DangAo dangAo = getFormDataDangAo();
                if (dangAo != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    DangAo dangAo1 = dangAos.get(row);
                    dangAoService.update(dangAo, dangAo1.getId() + "");
                    dangAos = dangAoService.getAll();
                    showDataTableDangAo(dangAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa dáng áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoChatLieu.isSelected()) {
                ChatLieu chatLieu = getFormDataChatLieu();
                if (chatLieu != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    ChatLieu chatLieu1 = chatLieus.get(row);
                    chatLieuService.update(chatLieu, chatLieu1.getId() + "");
                    chatLieus = chatLieuService.getAll();
                    showDataTableChatLieu(chatLieus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Sửa chất liệu thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            }
        }


    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm dữ liệu không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {

            if (rdoDanhMuc.isSelected()) {
                DanhMuc danhMuc = getFormDataDanhMuc();
                if (danhMuc != null) {
                    danhMucService.add(danhMuc);
                    danhMucs = danhMucService.getAll();
                    showDataTableDanhMuc(danhMucs);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm danh mục thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoXuatXu.isSelected()) {
                XuatXu xuatXu = getFormDataXuatXu();
                if (xuatXu != null) {
                    xuatXuService.add(xuatXu);
                    xuatXus = xuatXuService.getAll();
                    showDataTableXuatXu(xuatXus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm xuất xứ thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoNSX.isSelected()) {
                NSX nsx = getFormDataNSX();
                if (nsx != null) {
                    nsxService.add(nsx);
                    nsxs = nsxService.getAll();
                    showDataTableNSX(nsxs);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm nhà sản xuất thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoMauSac.isSelected()) {
                MauSac mauSac = getFormDataMauSac();
                if (mauSac != null) {
                    mauSacService.add(mauSac);
                    mausac = mauSacService.getAll();
                    ShowDataTableMauSac(mausac);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoSize.isSelected()) {
                Size size = getFormDataSize();
                if (size != null) {
                    sizeService.add(size);
                    sizes = sizeService.getAll();
                    showDataTableSize(sizes);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm size thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoThuongHieu.isSelected()) {
                ThuongHieu thuongHieu = getFormDataThuongHieu();
                if (thuongHieu != null) {
                    thuongHieuService.add(thuongHieu);
                    thuongHieus = thuongHieuService.getAll();
                    showDataTableThuongHieu(thuongHieus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm thương hiệu thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoCoAo.isSelected()) {
                CoAo coAo = getFormDataCoAo();
                if (coAo != null) {
                    coAoService.add(coAo);
                    coAos = coAoService.getAll();
                    showDataTableCoAo(coAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm cổ áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoDuoiAo.isSelected()) {
                DuoiAo duoiAo = getFormDataDuoiAo();
                if (duoiAo != null) {
                    duoiAoService.add(duoiAo);
                    duoiAos = duoiAoService.getAll();
                    showDataTableDuoiAo(duoiAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm dưới áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoTayAo.isSelected()) {
                TayAo tayAo = getFormDataTayAo();
                if (tayAo != null) {
                    tayAoService.add(tayAo);
                    tayAos = tayAoService.getAll();
                    showDataTableTayAo(tayAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm tay áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoDangAo.isSelected()) {
                DangAo dangAo = getFormDataDangAo();
                if (dangAo != null) {
                    dangAoService.add(dangAo);
                    dangAos = dangAoService.getAll();
                    showDataTableDangAo(dangAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm dáng áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoChatLieu.isSelected()) {
                ChatLieu chatLieu = getFormDataChatLieu();
                if (chatLieu != null) {
                    chatLieuService.add(chatLieu);
                    chatLieus = chatLieuService.getAll();
                    showDataTableChatLieu(chatLieus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm chất liệu thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            }
        }

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa dữ liệu không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (rdoDanhMuc.isSelected()) {
                DanhMuc danhMuc = getFormDataDanhMuc();
                if (danhMuc != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    DanhMuc danhMuc1 = danhMucs.get(row);
                    danhMucService.delete(danhMuc1.getId() + "");
                    danhMucs = danhMucService.getAll();
                    showDataTableDanhMuc(danhMucs);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa danh mục thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoXuatXu.isSelected()) {
                XuatXu xuatXu = getFormDataXuatXu();
                if (xuatXu != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    XuatXu danhMuc1 = xuatXus.get(row);
                    xuatXuService.delete(danhMuc1.getId() + "");
                    xuatXus = xuatXuService.getAll();
                    showDataTableXuatXu(xuatXus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa xuất xứ thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoNSX.isSelected()) {
                NSX nsx = getFormDataNSX();
                if (nsx != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    NSX nsxz = nsxs.get(row);
                    nsxService.delete(nsxz.getId() + "");
                    nsxs = nsxService.getAll();
                    showDataTableNSX(nsxs);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa nhà sản xuất thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoMauSac.isSelected()) {
                MauSac mauSac = getFormDataMauSac();
                if (mauSac != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    MauSac mauSac1 = mausac.get(row);
                    mauSacService.delete(mauSac1.getId() + "");
                    mausac = mauSacService.getAll();
                    ShowDataTableMauSac(mausac);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa màu sắc thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoSize.isSelected()) {
                Size size = getFormDataSize();
                if (size != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    Size size1 = sizes.get(row);
                    sizeService.delete(size1.getId() + "");
                    sizes = sizeService.getAll();
                    showDataTableSize(sizes);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa size thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoThuongHieu.isSelected()) {
                ThuongHieu thuongHieu = getFormDataThuongHieu();
                if (thuongHieu != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    ThuongHieu thuongHieu1 = thuongHieus.get(row);
                    thuongHieuService.delete(thuongHieu1.getId() + "");
                    thuongHieus = thuongHieuService.getAll();
                    showDataTableThuongHieu(thuongHieus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thương hiệu thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoCoAo.isSelected()) {
                CoAo coAo = getFormDataCoAo();
                if (coAo != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    CoAo coAo1 = coAos.get(row);
                    coAoService.delete(coAo1.getId() + "");
                    coAos = coAoService.getAll();
                    showDataTableCoAo(coAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa cổ áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoDuoiAo.isSelected()) {
                DuoiAo duoiAo = getFormDataDuoiAo();
                if (duoiAo != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    DuoiAo duoiAo1 = this.duoiAos.get(row);
                    duoiAoService.delete(duoiAo1.getId() + "");
                    this.duoiAos = duoiAoService.getAll();
                    showDataTableDuoiAo(this.duoiAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa dưới áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoTayAo.isSelected()) {
                TayAo tayAo = getFormDataTayAo();
                if (tayAo != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    TayAo tayAo1 = tayAos.get(row);
                    tayAoService.delete(tayAo1.getId() + "");
                    tayAos = tayAoService.getAll();
                    showDataTableTayAo(tayAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa tay áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoDangAo.isSelected()) {
                DangAo dangAo = getFormDataDangAo();
                if (dangAo != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    DangAo dangAo1 = this.dangAos.get(row);
                    dangAoService.delete(dangAo1.getId() + "");
                    this.dangAos = dangAoService.getAll();
                    showDataTableDangAo(this.dangAos);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa dáng áo thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            } else if (rdoChatLieu.isSelected()) {
                ChatLieu chatLieu = getFormDataChatLieu();
                if (chatLieu != null) {
                    int row = tblThuocTinh.getSelectedRow();
                    ChatLieu chatLieu1 = chatLieus.get(row);
                    chatLieuService.delete(chatLieu1.getId() + "");
                    chatLieus = chatLieuService.getAll();
                    showDataTableChatLieu(chatLieus);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa chất liệu thành công");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin");
                }
            }
        }

    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoChatLieu;
    private javax.swing.JRadioButton rdoCoAo;
    private javax.swing.JRadioButton rdoDangAo;
    private javax.swing.JRadioButton rdoDanhMuc;
    private javax.swing.JRadioButton rdoDuoiAo;
    private javax.swing.JRadioButton rdoMauSac;
    private javax.swing.JRadioButton rdoNSX;
    private javax.swing.JRadioButton rdoSize;
    private javax.swing.JRadioButton rdoTayAo;
    private javax.swing.JRadioButton rdoThuongHieu;
    private javax.swing.JRadioButton rdoXuatXu;
    private swing.RoundPanel roundPanel2;
    private javax.swing.JTable tblThuocTinh;
    private textfield.TextField txtMaThuocTinh;
    private textfield.TextField txtTenThuocTinh;
    // End of variables declaration//GEN-END:variables
}
