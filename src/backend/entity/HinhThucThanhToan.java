/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.entity;

/**
 *
 * @author VHC
 */
public class HinhThucThanhToan {
    private int idHD;
    private int idHTTT;
    private double tienMat;
    private double tienTK;

    public HinhThucThanhToan() {
    }

    public HinhThucThanhToan(int idHD, int idHTTT, double tienMat, double tienTK) {
        this.idHD = idHD;
        this.idHTTT = idHTTT;
        this.tienMat = tienMat;
        this.tienTK = tienTK;
    }

    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public int getIdHTTT() {
        return idHTTT;
    }

    public void setIdHTTT(int idHTTT) {
        this.idHTTT = idHTTT;
    }

    public double getTienMat() {
        return tienMat;
    }

    public void setTienMat(double tienMat) {
        this.tienMat = tienMat;
    }

    public double getTienTK() {
        return tienTK;
    }

    public void setTienTK(double tienTK) {
        this.tienTK = tienTK;
    }
    
    
}
