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
import com.google.zxing.NotFoundException;
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
import javax.swing.SwingUtilities;

/**
 *
 * @author VHC
 */
public class quetQR {

    private static Webcam webcam;
    private static codeQR qrCodeScan;
    private static JFrame webcamFrame;

    public interface codeQR {

        void onQRCodeScanned(int result);
    }

    public void setQRCodeListener(codeQR listener) {
        this.qrCodeScan = listener;
    }

    public static void openWebcam() {
        webcamFrame = new JFrame("Webcam Viewer");
        webcamFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                closeWebcam();
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
                                System.out.println("QR Code value: " + qrCodeValue);
                                if (qrCodeValue != null && qrCodeScan != null) {
                                    try {
                                        int intValue = Integer.parseInt(qrCodeValue);
                                        System.out.println("QR Code value (parsed): " + intValue); // Kiểm tra giá trị đã được parse đúng hay không
                                        qrCodeScan.onQRCodeScanned(intValue);
                                        System.out.println("Ở trong if: " + qrCodeValue);
                                        closeWebcam();
                                    } catch (NumberFormatException e) {
                                        System.err.println("Error parsing QR code value: " + qrCodeValue);
                                        e.printStackTrace();
                                    }
                                } else {
                                    System.out.println("qrCodeScan is null or qrCodeValue is null"); // In ra để kiểm tra xem tại sao if không được thực hiện
                                }
                            }
                        } catch (NotFoundException ex) {
                            // Không tìm thấy mã QR trong hình ảnh, tiếp tục quét
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

    // Phương thức để đóng cửa sổ webcam
    public void closeQRFrame() {
        if (webcamFrame != null) {
            webcamFrame.dispose();
        }
    }

}
