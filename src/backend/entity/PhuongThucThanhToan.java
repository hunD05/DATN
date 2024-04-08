/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.entity;

/**
 *
 * @author VHC
 */
public class PhuongThucThanhToan {
    private int id;
    private String tenKieuThanhToan;

    public PhuongThucThanhToan() {
    }

    public PhuongThucThanhToan(int id, String tenKieuThanhToan) {
        this.id = id;
        this.tenKieuThanhToan = tenKieuThanhToan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKieuThanhToan() {
        return tenKieuThanhToan;
    }

    public void setTenKieuThanhToan(String tenKieuThanhToan) {
        this.tenKieuThanhToan = tenKieuThanhToan;
    }
   
}
