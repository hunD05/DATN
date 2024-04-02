/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package print.model;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author VHC
 */
public class ParameterReportPayment {

    private String maHD;
    private String ngayTao;
    private String tenKH;
    private String sdt;
    private String diaChi;
    private String tongTien;
    private InputStream qrcode;
    private List<FieldReportPayment> fields;

    public ParameterReportPayment() {
    }

    public ParameterReportPayment(String maHD, String ngayTao, String tenKH, String sdt, String diaChi, String tongTien, InputStream qrcode, List<FieldReportPayment> fields) {
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.tongTien = tongTien;
        this.qrcode = qrcode;
        this.fields = fields;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public InputStream getQrcode() {
        return qrcode;
    }

    public void setQrcode(InputStream qrcode) {
        this.qrcode = qrcode;
    }

    public List<FieldReportPayment> getFields() {
        return fields;
    }

    public void setFields(List<FieldReportPayment> fields) {
        this.fields = fields;
    }

    
}
