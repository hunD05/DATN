/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.viewmodel;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author VHC
 */
public class LSHDViewModel {
    private int id;
    private String maNV;
    private LocalDateTime ngayCapNhat;
    private String hanhDong;

    public LSHDViewModel() {
    }

    public LSHDViewModel(int id, String maNV, LocalDateTime ngayCapNhat, String hanhDong) {
        this.id = id;
        this.maNV = maNV;
        this.ngayCapNhat = ngayCapNhat;
        this.hanhDong = hanhDong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    
}
