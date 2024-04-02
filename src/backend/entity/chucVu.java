/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.entity;

/**
 *
 * @author Huu Hai
 */
public class chucVu {
    private String Id;
    private String tenCV;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public chucVu(String Id, String tenCV) {
        this.Id = Id;
        this.tenCV = tenCV;
    }

    public chucVu() {
    }
}
