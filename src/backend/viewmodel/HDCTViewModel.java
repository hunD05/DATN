/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.viewmodel;

/**
 *
 * @author VHC
 */
public class HDCTViewModel {

    private int id;
    private int maSPCT;
    private String tenSP;
    private String mauSac;
    private String size;
    private String chatLieu;
    private String thuongHieu;
    private int soLuong;
    private double giaBan;

    public HDCTViewModel() {
    }

    public HDCTViewModel(int id, int maSPCT, String tenSP, String mauSac, String size, String chatLieu, String thuongHieu, int soLuong, double giaBan) {
        this.id = id;
        this.maSPCT = maSPCT;
        this.tenSP = tenSP;
        this.mauSac = mauSac;
        this.size = size;
        this.chatLieu = chatLieu;
        this.thuongHieu = thuongHieu;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(int maSPCT) {
        this.maSPCT = maSPCT;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

}
