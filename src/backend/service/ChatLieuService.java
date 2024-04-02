/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.service;

import backend.entity.ChatLieu;
import backend.entity.DuoiAo;
import backend.respository.ChatLieuRespository;
import backend.respository.DuoiAoRespository;
import java.util.List;

/**
 *
 * @author leanb
 */
public class ChatLieuService {
        ChatLieuRespository respository = new ChatLieuRespository();
    public List<ChatLieu> getAll(){
        return respository.getAll();
    }
    
    public boolean add(ChatLieu chiTietSanPham) {
        return respository.add(chiTietSanPham);
    }
    
    public boolean update(ChatLieu chatLieu, String id) {
        return respository.update(chatLieu, id);
    }
    
    public boolean delete(String id) {
        return respository.delete(id);
    }
}
