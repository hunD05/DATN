/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.QRCode;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author leanb
 */
public class QRCodeResultWindow extends JFrame {
    public QRCodeResultWindow(String result) {
        setTitle("Kết quả quét mã QR");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Tạo label để hiển thị thông tin đã quét được
        JLabel label = new JLabel("Thông tin quét: " + result);

        // Đặt layout cho cửa sổ
        setLayout(new BorderLayout());

        // Thêm label vào cửa sổ
        add(label, BorderLayout.CENTER);

        // Thiết lập kích thước cửa sổ và hiển thị nó
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
