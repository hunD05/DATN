/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package print.model;

/**
 *
 * @author VHC
 */
public class FieldReportPayment {

    private int stt;
    private String tensp;
    private String gia;
    private String soluong;
    private String tong;

    public FieldReportPayment() {
    }

    public FieldReportPayment(int stt, String tensp, String gia, String soluong, String tong) {
        this.stt = stt;
        this.tensp = tensp;
        this.gia = gia;
        this.soluong = soluong;
        this.tong = tong;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getTong() {
        return tong;
    }

    public void setTong(String tong) {
        this.tong = tong;
    }

    
}
