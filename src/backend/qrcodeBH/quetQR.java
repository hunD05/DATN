/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.qrcodeBH;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import form.BanHang;
import form.ThemKhachHang;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author VHC
 */
public class quetQR {

    private static Webcam webcam;

    private static BanHang BanHangInstance; // Thêm biến tham chiếu

    public static void setBanHangInstance(BanHang banHang) {
        BanHangInstance = banHang;
    }

    public static void openWebcam() {
        JFrame webcamFrame = new JFrame("Webcam Viewer");
        webcamFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng JFrame khi cửa sổ được đóng
        webcamFrame.setSize(640, 480);

        JPanel panel = new JPanel(new BorderLayout());

        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getDefault();
        webcam.setViewSize(size);

        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setPreferredSize(size);

        panel.add(webcamPanel, BorderLayout.CENTER);

        webcamFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWebcam(); // Gọi phương thức đóng webcam khi cửa sổ được đóng
            }
        });

        webcamFrame.add(panel);

        webcamFrame.pack();
        webcamFrame.setLocationRelativeTo(null);
        webcamFrame.setVisible(true);

        webcam.open();

        Thread thread = new Thread(() -> {
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                if (webcam.isOpen()) {
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        MultiFormatReader reader = new MultiFormatReader();
                        try {
                            Result result = reader.decode(bitmap);
                            if (result != null) {
                                String qrCodeValue = result.getText();
                                closeWebcam();
                                handleQRCodeValue(qrCodeValue);

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } while (webcam.isOpen());
        });
        thread.start();
    }

    public static void closeWebcam() {
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
    }

    private static void handleQRCodeValue(String qrCodeValue) {
        // Hiển thị giá trị mã QR trên giao diện người dùng
        System.out.println("QR Code value: " + qrCodeValue);

        // Gửi giá trị mã QR sang lớp ThemKhachHang
// Gửi giá trị mã QR sang lớp BanHang
        if (BanHangInstance != null) {
            BanHangInstance.processQRCodeValue(qrCodeValue);
        }
    }

}
