package raven.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import form.BanHang;
import form.ChiTietSanPham;
import form.Form_1;
import form.HoaDon;
import form.KhachHang;
import form.NhanVien;
import form.ViewPhieuGiamGia;
import form.SanPhamz;
import form.ThongKe;
import form.ThuocTinhSanPham;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import raven.application.Application;
import raven.application.form.other.FormDashboard;
import raven.application.form.other.FormInbox;
import raven.application.form.other.FormRead;
import raven.menu.Menu;
import raven.menu.MenuAction;

/**
 *
 * @author Raven
 */
public class MainForm extends JLayeredPane {

    private JButton closeButton;
    private Icon closeIcon;
    private Color closeButtonHoverColor;
    private Cursor handCursor;

    public MainForm() {
        init();
    }

    private void initCloseButton() {
        closeButtonHoverColor = new Color(255, 0, 0); // Màu đỏ
        handCursor = new Cursor(Cursor.HAND_CURSOR);

        // Gắn lắng nghe sự kiện di chuột vào và ra
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setCursor(handCursor);
                closeButton.setBackground(closeButtonHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setCursor(Cursor.getDefaultCursor());
                closeButton.setBackground(null); // Đặt lại màu nền về mặc định
            }
        });
        closeIcon = new FlatSVGIcon("raven/icon/svg/close.svg", 0.8f);
        closeButton = new JButton(closeIcon);
        closeButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");
        closeButton.addActionListener((ActionEvent e) -> {
            // Xử lý sự kiện khi nút đóng được nhấn
            System.exit(0); // Thay thế bằng hành động tương ứng với việc đóng ứng dụng của bạn
        });
        setLayer(closeButton, JLayeredPane.POPUP_LAYER);
        add(closeButton);
    }

    private void init() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
    setLayout(new MainFormLayout());
    menu = new Menu();
    panelBody = new JPanel(new BorderLayout());
    initMenuArrowIcon();
    menuButton.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$Menu.button.background;"
            + "arc:999;"
            + "focusWidth:0;"
            + "borderWidth:0");
    menuButton.addActionListener((ActionEvent e) -> {
        setMenuFull(!menu.isMenuFull());
    });
    initMenuEvent();
    setLayer(menuButton, JLayeredPane.POPUP_LAYER);
    add(menuButton);
    add(menu);
    add(panelBody);

    // Khởi tạo closeButton
    closeButton = new JButton("X");
    initCloseButton();

        // Thêm nút đóng
        JButton closeButton = new JButton("Đóng");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Đóng ứng dụng khi nút đóng được nhấn
            }
        });
        closeButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");

        // Thêm nút đóng vào panel
        add(closeButton);

        // Cập nhật layout để bao gồm nút đóng
        revalidate();
    }

    @Override
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("raven/icon/svg/" + icon, 0.8f));
    }

       private void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            // Application.mainForm.showForm(new DefaultForm("Form : " + index + " " + subIndex));
            if (index == 0) {
                Application.showForm(new Form_1());
            } else if (index == 1) {
                if (subIndex == 1) {
                    Application.showForm(new ChiTietSanPham());
                } else if (subIndex == 2) {
                    Application.showForm(new ThuocTinhSanPham());
                } else if (subIndex == 3) {
                    Application.showForm(new SanPhamz());
                } else {
                    action.cancel();
                }
            } else if (index == 2) {
                Application.showForm(new BanHang());
            } else if (index == 3) {
                Application.showForm(new HoaDon());
            } else if (index == 4) {
                Application.showForm(new ViewPhieuGiamGia());
            } else if (index == 5) {
                Application.showForm(new KhachHang());
            } else if (index == 6) {
                Application.showForm(new NhanVien());
                
            }else if (index == 7) {
                Application.showForm(new ThongKe());
                
            } 
            else if (index == 8) {
                Application.logout();
            } else {
                action.cancel();
            }
        });
    }

    private void setMenuFull(boolean full) {
        String icon;
        if (getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        } else {
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("raven/icon/svg/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }

    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;

    private class MainFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menubX;
                if (ltr) {
                    menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int bodyWidth = width - menuWidth - gap;
                int bodyHeight = height;
                int bodyx = ltr ? (x + menuWidth + gap) : x;
                int bodyy = y;
                panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);

                int closeButtonWidth = closeButton.getPreferredSize().width;
                int closeButtonHeight = closeButton.getPreferredSize().height;
                int closeButtonX = ltr ? (x + width - closeButtonWidth) : x;
                int closeButtonY = y;
                closeButton.setBounds(closeButtonX, closeButtonY, closeButtonWidth, closeButtonHeight);

            }
        }

    }
}
