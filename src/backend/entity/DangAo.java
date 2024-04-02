/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.entity;

/**
 *
 * @author leanb
 */
import java.sql.Timestamp;

public class DangAo {
    private long id;
    private String maDangAo;
    private String tenDangAo;
    private boolean deleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;
    private String updatedBy;
    private int STT;

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }
    
    
    // Constructors
    public DangAo() {
    }

    public DangAo(String maDangAo, String tenDangAo) {
        this.maDangAo = maDangAo;
        this.tenDangAo = tenDangAo;
    }

    

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMaDangAo() {
        return maDangAo;
    }

    public void setMaDangAo(String maDangAo) {
        this.maDangAo = maDangAo;
    }

    public String getTenDangAo() {
        return tenDangAo;
    }

    public void setTenDangAo(String tenDangAo) {
        this.tenDangAo = tenDangAo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
