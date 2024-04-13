/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.entity;

import java.util.Date;

/**
 *
 * @author leanb
 */
public class ThongKe {
    private String stt;
    private String ma;
    
    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
    private Date ngayTao;
    private float tongTien;

    public ThongKe() {
    }

    public ThongKe(String stt, String ma, Date ngayTao, float tongTien) {
        this.stt = stt;
        this.ma = ma;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
    }
}
