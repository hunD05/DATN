/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package print;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import print.model.ParameterReportPayment;

/**
 *
 * @author VHC
 */
public class ReportManager {

    private static ReportManager instance;

    private JasperReport reportPay;

    public static ReportManager getInstance() {
        if (instance == null) {
            instance = new ReportManager();
        }
        return instance;
    }

    private ReportManager() {
    }

    public void compileReport() throws JRException {
        reportPay = JasperCompileManager.compileReport(getClass().getResourceAsStream("/print/HoaDon.jrxml"));
    }

    public void printReportPayment(ParameterReportPayment data) throws JRException {
        Map<String, Object> para = new HashMap<>();
        para.put("mahd", data.getMaHD());
        para.put("ngaytao", data.getNgayTao());
        para.put("khachHang", data.getTenKH());
        para.put("sdt", data.getSdt());
        para.put("diachi", data.getDiaChi());
        para.put("tongtien", data.getTongTien());

        // Convert InputStream to BufferedImage
        try {
            BufferedImage qrCodeImage = ImageIO.read(data.getQrcode());
            para.put("qrcode", qrCodeImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
        JasperPrint print = JasperFillManager.fillReport(reportPay, para, dataSource);
        view(print);
    }

    private void view(JasperPrint print) throws JRException {
        JasperViewer.viewReport(print, false);
    }

    public void checkJRXMLPath() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/print/HoaDon.jrxml");
            if (inputStream != null) {
                System.out.println("JRXML path is valid.");
            } else {
                System.out.println("JRXML path is invalid. Check the path.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkCompilation() {
        try {
            compileReport();
            if (reportPay != null) {
                System.out.println("JRXML compilation successful.");
            } else {
                System.out.println("JRXML compilation failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkReportParameters(ParameterReportPayment data) {
        try {
            System.out.println("MaHD: " + data.getMaHD());
            System.out.println("Ngay tao: " + data.getNgayTao());
            System.out.println("Ten Khach Hang: " + data.getTenKH());
            System.out.println("SDT: " + data.getSdt());
            System.out.println("Dia chi: " + data.getDiaChi());
            System.out.println("Tong Tien: " + data.getTongTien());
            System.out.println("QR Code: " + data.getQrcode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
