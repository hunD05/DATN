package form;

import backend.respository.DBConnect;
import backend.service.ChiTietSanPhamService;
import backend.service.ThongKeService;
import backend.viewmodel.SanPhamChiTietViewModel;
import card.ModelCard;
import com.raven.chart.ModelChart;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ThongKe extends javax.swing.JPanel {

    private DefaultTableModel dtm = new DefaultTableModel();
    private List<backend.entity.ThongKe> list = new ArrayList<>();
    private ThongKeService sr = new ThongKeService();
    private ChiTietSanPhamService chiTietSanPhamService = new ChiTietSanPhamService();

    public ThongKe() {
        initComponents();

        init();
        dtm = (DefaultTableModel) table3.getModel();
        list = sr.getAll();
        showDataTable(list);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, Calendar.JANUARY);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        startCalendar.set(Calendar.YEAR, LocalDate.now().getYear()); // Năm 1
        DateChooserStart.setCalendar(startCalendar);

// Set ngày cuối là ngày 31 tháng 12 năm 9999
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
        endCalendar.set(Calendar.DAY_OF_MONTH, 31);
        endCalendar.set(Calendar.YEAR, LocalDate.now().getYear()); // Năm 9999
        DateChooserEnd.setCalendar(endCalendar);
        chsYear.setYear(LocalDate.now().getYear());

// Set ngày bắt đầu là ngày hiện tại
    }

    private String formatCurrency(float value) {
        String formattedValue = String.format("%.0f", value); // Chuyển số thành chuỗi

        // Tạo một StringBuilder để xây dựng chuỗi kết quả
        StringBuilder result = new StringBuilder();

        // Thêm dấu chấm phân tách hàng nghìn mỗi 3 chữ số từ phải qua trái
        int count = 0;
        for (int i = formattedValue.length() - 1; i >= 0; i--) {
            result.insert(0, formattedValue.charAt(i)); // Thêm ký tự vào đầu chuỗi

            count++; // Đếm số lượng ký tự đã thêm vào

            // Nếu đã thêm vào đủ 3 ký tự và không phải ký tự cuối cùng, thêm dấu chấm
            if (count % 3 == 0 && i != 0) {
                result.insert(0, '.'); // Thêm dấu chấm vào trước ký tự đầu tiên
            }
        }

        // Thêm đơn vị tiền tệ (VND) vào cuối chuỗi
        result.append(" VND");

        return result.toString(); // Trả về chuỗi kết quả đã được định dạng
    }

    public void showDataTable(List<backend.entity.ThongKe> listTK) {
        dtm.setRowCount(0);
        int i = 0;
        for (backend.entity.ThongKe thongKe : listTK) {
            i++;
            dtm.addRow(new Object[]{i, thongKe.getMa(), thongKe.getNgayTao(), formatCurrency(thongKe.getTongTien())});
        }
    }

    private String formatGiaBan(BigDecimal giaBan) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
        System.out.println(giaBan);
        return formatter.format(giaBan);
    }

    private void init() {
        chart.addLegend("Tổng doanh thu", new Color(12, 84, 175), new Color(0, 108, 247));

        LocalDate startDate = LocalDate.of(2024, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.now();

        Map<LocalDate, BigDecimal> revenueByDate = sr.getTotalRevenueByDate(startDate, endDate);

        TreeMap<LocalDate, BigDecimal> sortedRevenueByDate = new TreeMap<>(revenueByDate);

        sortedRevenueByDate.forEach((date, revenue) -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = formatter.format(java.sql.Date.valueOf(date));
            chart.addData(new ModelChart(formattedDate, new double[]{revenue.doubleValue()}));
            System.out.println(revenue);
            chart.start();
        });
        
        chsYear.addPropertyChangeListener("year", evt -> {
            // Khi người dùng chọn một năm mới, lấy giá trị năm
            int selectedYear = chsYear.getYear();
            // Lọc dữ liệu và cập nhật biểu đồ
            filterDataByYear(selectedYear);
        });

        DateChooserEnd.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                filterDataByDateRange();
            }
        });

        card1.setData(new ModelCard(null, null, null, formatGiaBan(sr.calculateTotalRevenueFromPaidHoaDonChiTiet()), "Doanh thu"));
        card2.setData(new ModelCard(null, null, null, String.valueOf(sr.countPaidHoaDon()) + " Hóa đơn", "Số hóa đơn đã thanh toán"));
        card3.setData(new ModelCard(null, null, null, String.valueOf(sr.countUnpaidHoaDon() + sr.countUnpaidHoaDonDG() + sr.countUnppaidHoaDonCG()) + " Hóa đơn", "Số hóa đơn chưa thanh toán"));
        card4.setData(new ModelCard(null, null, null, String.valueOf(sr.countUniqueKhachHangInHoaDon() + " Khách hàng"), "Số khách hàng"));

        chartSanPhamHetHang.addLegend("Sản phẩm sắp hết hàng", new Color(220, 20, 60), new Color(255, 100, 100));

        List<SanPhamChiTietViewModel> sanPhamHetHang = chiTietSanPhamService.getSanPhamHetHang();

        // Thêm dữ liệu vào biểu đồ
        sanPhamHetHang.forEach(sp -> {
            chartSanPhamHetHang.addData(new ModelChart(sp.getTenSanPham(), new double[]{sp.getSoLuong()}));
        });

        chartSanPhamHetHang.start();

        List<SanPhamChiTietViewModel> topProducts = chiTietSanPhamService.getTopProducts();

// Sắp xếp danh sách sản phẩm theo số lượng bán hàng giảm dần (hoặc theo tiêu chí bạn muốn)
        Collections.sort(topProducts, Comparator.comparingInt(SanPhamChiTietViewModel::getSoLuong).reversed());

        chartSanPhamBanChay.addLegend("Sản phẩm chạy", new Color(75, 0, 130), new Color(148, 0, 211));


// Thêm dữ liệu của các sản phẩm vào biểu đồ
        for (SanPhamChiTietViewModel product : topProducts) {
            chartSanPhamBanChay.addData(new ModelChart(product.getTenSanPham(), new double[]{product.getSoLuong()}));
        }

// Khởi chạy biểu đồ
        chartSanPhamBanChay.start();
    }

    private void filterDataByYear(int year) {
        LocalDate startDate = LocalDate.of(year, Month.JANUARY, 1); // Ngày bắt đầu năm được chọn
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, 31); // Ngày kết thúc năm được chọn

        // Lọc dữ liệu theo năm và cập nhật biểu đồ
        Map<LocalDate, BigDecimal> revenueByDate = sr.getTotalRevenueByDate(startDate, endDate);

        // Xóa dữ liệu cũ trên biểu đồ
        chart.clear();

        // Sử dụng TreeMap với Comparator mặc định để sắp xếp tăng dần theo key (ngày)
        TreeMap<LocalDate, BigDecimal> sortedRevenueByDate = new TreeMap<>(revenueByDate);

        // Duyệt qua từng ngày và tổng doanh thu tương ứng để thêm vào biểu đồ
        sortedRevenueByDate.forEach((date, revenue) -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = formatter.format(java.sql.Date.valueOf(date));
            chart.addData(new ModelChart(formattedDate, new double[]{revenue.doubleValue()}));
        });

        // Cập nhật biểu đồ
        chart.start();

    }

    private void filterDataByDateRange() {
        // Lấy ngày bắt đầu và ngày kết thúc từ các DateChooser
        LocalDate startDate = DateChooserStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = DateChooserEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Lọc dữ liệu dựa trên khoảng thời gian và cập nhật biểu đồ
        Map<LocalDate, BigDecimal> revenueByDate = sr.getTotalRevenueByDate(startDate, endDate);

        // Xóa dữ liệu cũ trên biểu đồ
        chart.clear();

        // Duyệt qua từng ngày và tổng doanh thu tương ứng để thêm vào biểu đồ
        revenueByDate.forEach((date, revenue) -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = formatter.format(java.sql.Date.valueOf(date));
            chart.addData(new ModelChart(formattedDate, new double[]{revenue.doubleValue()}));
        });

        // Cập nhật biểu đồ
        chart.start();
    }

    public Map<LocalDate, BigDecimal> getTotalRevenueByDate(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, BigDecimal> revenueByDate = new HashMap<>();

        // Lấy toàn bộ dữ liệu từ cơ sở dữ liệu
        Map<LocalDate, BigDecimal> allRevenueByDate = getAllRevenueByDate();

        // Lọc dữ liệu dựa trên khoảng thời gian đã chọn
        allRevenueByDate.forEach((date, revenue) -> {
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                revenueByDate.put(date, revenue);
            }
        });

        return revenueByDate;
    }

    private Map<LocalDate, BigDecimal> getAllRevenueByDate() {
        Map<LocalDate, BigDecimal> allRevenueByDate = new HashMap<>();

        try ( Connection con = DBConnect.getConnection();  Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery("SELECT NgayThanhToan, SUM(SoLuong * GiaBan) AS TotalRevenue FROM HoaDonChiTiet HDCT INNER JOIN HoaDon HD ON HDCT.IDHoaDon = HD.ID WHERE HD.TrangThai = N'Đã Thanh Toán' GROUP BY NgayThanhToan")) {
            while (rs.next()) {
                LocalDate date = rs.getDate("NgayThanhToan").toLocalDate();
                BigDecimal totalRevenue = rs.getBigDecimal("TotalRevenue");
                allRevenueByDate.put(date, totalRevenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allRevenueByDate;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        componentResizer1 = new javaswingdev.swing.titlebar.ComponentResizer();
        model_Menu1 = new com.raven.model.Model_Menu();
        modelCard1 = new card.ModelCard();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        roundPanel3 = new swing.RoundPanel();
        chart = new com.raven.chart.Chart();
        roundPanel5 = new swing.RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        DateChooserStart = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JSeparator();
        btnResetBoLoc = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        chsYear = new com.toedter.calendar.JYearChooser();
        jSeparator2 = new javax.swing.JSeparator();
        DateChooserEnd = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        chartSanPhamBanChay = new com.raven.chart.Chart();
        panelBorder1 = new com.raven.swing.PanelBorder();
        jScrollPane4 = new javax.swing.JScrollPane();
        table3 = new javaswingdev.swing.table.Table();
        chartSanPhamHetHang = new com.raven.chart.Chart();
        jLabel3 = new javax.swing.JLabel();
        roundPanel1 = new swing.RoundPanel();
        card1 = new card.Card();
        card4 = new card.Card();
        card2 = new card.Card();
        card3 = new card.Card();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setBackground(new java.awt.Color(222, 222, 222));

        chart.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Lọc theo khoảng thời gian");

        DateChooserStart.setDateFormatString("dd-MM-yyyy");
        DateChooserStart.setMaxSelectableDate(new java.util.Date(253370743261000L));
        DateChooserStart.setMinSelectableDate(new java.util.Date(-62135791139000L));

        btnResetBoLoc.setText("Reset biểu đồ");
        btnResetBoLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetBoLocActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Lọc theo năm");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        DateChooserEnd.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chsYear, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addComponent(DateChooserStart, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DateChooserEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResetBoLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator2)
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(chsYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnResetBoLoc)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DateChooserStart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DateChooserEnd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Sản phẩm bán chạy");

        chartSanPhamBanChay.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(chartSanPhamBanChay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartSanPhamBanChay, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày tạo", "Tổng tiền"
            }
        ));
        jScrollPane4.setViewportView(table3);

        chartSanPhamHetHang.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Sản phẩm sắp hết hàng");

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
                    .addComponent(chartSanPhamHetHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartSanPhamHetHang, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        roundPanel1.setBackground(new java.awt.Color(222, 222, 222));
        roundPanel1.setOpaque(true);
        roundPanel1.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        card1.setColor1(new java.awt.Color(102, 204, 0));
        card1.setColor2(new java.awt.Color(153, 204, 0));
        card1.setIcon(javaswingdev.GoogleMaterialDesignIcon.ATTACH_MONEY);
        card1.setInheritsPopupMenu(true);
        roundPanel1.add(card1);

        card4.setColor1(new java.awt.Color(51, 255, 204));
        card4.setColor2(new java.awt.Color(0, 153, 153));
        roundPanel1.add(card4);

        card2.setColor1(new java.awt.Color(255, 255, 153));
        card2.setColor2(new java.awt.Color(153, 153, 0));
        card2.setEnabled(false);
        card2.setIcon(javaswingdev.GoogleMaterialDesignIcon.KEYBOARD_TAB);
        roundPanel1.add(card2);

        card3.setColor1(new java.awt.Color(102, 102, 255));
        card3.setColor2(new java.awt.Color(0, 0, 255));
        roundPanel1.add(card3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetBoLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetBoLocActionPerformed
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, Calendar.JANUARY);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        startCalendar.set(Calendar.YEAR, LocalDate.now().getYear()); // Năm 1
        DateChooserStart.setCalendar(startCalendar);

// Set ngày cuối là ngày 31 tháng 12 năm 9999
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
        endCalendar.set(Calendar.DAY_OF_MONTH, 31);
        endCalendar.set(Calendar.YEAR, LocalDate.now().getYear()); // Năm 9999
        DateChooserEnd.setCalendar(endCalendar);
        chsYear.setYear(LocalDate.now().getYear());
    }//GEN-LAST:event_btnResetBoLocActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateChooserEnd;
    private com.toedter.calendar.JDateChooser DateChooserStart;
    private javax.swing.JButton btnResetBoLoc;
    private card.Card card1;
    private card.Card card2;
    private card.Card card3;
    private card.Card card4;
    private com.raven.chart.Chart chart;
    private com.raven.chart.Chart chartSanPhamBanChay;
    private com.raven.chart.Chart chartSanPhamHetHang;
    private com.toedter.calendar.JYearChooser chsYear;
    private javaswingdev.swing.titlebar.ComponentResizer componentResizer1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private card.ModelCard modelCard1;
    private com.raven.model.Model_Menu model_Menu1;
    private com.raven.swing.PanelBorder panelBorder1;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel3;
    private swing.RoundPanel roundPanel5;
    private javaswingdev.swing.table.Table table3;
    // End of variables declaration//GEN-END:variables
}
