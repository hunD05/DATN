/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.entity;

/**
 *
 * @author VHC
 */
public class HDCTEntity {

    private HoaDonEntity idHoaDon;
    private int soLuong;
    private int giaBan;

    public HDCTEntity() {
    }

    public HDCTEntity(HoaDonEntity idHoaDon, int soLuong, int giaBan) {
        this.idHoaDon = idHoaDon;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    public HoaDonEntity getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(HoaDonEntity idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    
    

}
