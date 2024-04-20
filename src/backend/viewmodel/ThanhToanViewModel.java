/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.viewmodel;

/**
 *
 * @author VHC
 */
public class ThanhToanViewModel {

    private int idHD;
    private int idHTTT;
    private double tienMat;
    private double tienTK;
    private int idPTTT;
    private String tenKieuThanhToan;

    public ThanhToanViewModel() {
    }

    public ThanhToanViewModel(int idHD, int idHTTT, double tienMat, double tienTK, int idPTTT, String tenKieuThanhToan) {
        this.idHD = idHD;
        this.idHTTT = idHTTT;
        this.tienMat = tienMat;
        this.tienTK = tienTK;
        this.idPTTT = idPTTT;
        this.tenKieuThanhToan = tenKieuThanhToan;
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

    public int getIdPTTT() {
        return idPTTT;
    }

    public void setIdPTTT(int idPTTT) {
        this.idPTTT = idPTTT;
    }

    public String getTenKieuThanhToan() {
        return tenKieuThanhToan;
    }

    public void setTenKieuThanhToan(String tenKieuThanhToan) {
        this.tenKieuThanhToan = tenKieuThanhToan;
    }
    
    
}
