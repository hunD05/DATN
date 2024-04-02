/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.chucVu;
import backend.respository.chucVuRepository;
import java.util.List;

/**
 *
 * @author Huu Hai
 */
public class chucVuService {
    chucVuRepository rep = new chucVuRepository();
    
    public List<chucVu> getAll() {
        return rep.getAll();
        
    }
}
