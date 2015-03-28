package pl.zankowski.java.projekty.loombard.widoki;

import pl.zankowski.java.projekty.loombard.model.Battery;
import pl.zankowski.java.projekty.loombard.model.Drives;
import pl.zankowski.java.projekty.loombard.model.HDD;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zano on 2015-03-25.
 */
public class View extends JFrame {

    private HashMap<String, JTextField> mainInfo;
    private HashMap<String, JMenuItem> menuItem;
    private JTabbedPane diskTab;
    private ArrayList<JPanel> tabs = new ArrayList<JPanel>();

    public View(String name) {
        //super(name);

        //UIManager.put("TabbedPane.borderColor", new Color(0, 0, 0, 0));

        //UIManager.put("TabbedPane.background", new Color(0, 0, 0, 0));
        //UIManager.put("TabbedPane.selected", Color.WHITE);
        //UIManager.put("TabbedPane.contentOpaque", false);
        /*UIManager.put("TabbedPane.tabAreaBackground", new Color(95,161,65));
        UIManager.put("TabbedPane.borderHightlightColor", new Color(95,161,65));
        UIManager.put("TabbedPane.shadow", new Color(0, 0, 0, 0));
        UIManager.put("TabbedPane.darkShadow", new Color(0, 0, 0, 0));
        UIManager.put("TabbedPane.highlight", new Color(4,92,46));
        UIManager.put("TabbedPane.borderHightlightColor", new Color(0, 0, 0, 0));
        UIManager.put("TabbedPane.contentAreaColor ", new Color(0, 0, 0, 0));*/

        //UIManager.put("TabbedPane.light", new Color(0, 0, 0, 0));
        //add(mainInfo());
        //add(logoPanel());
        //add(healthPanel());
        //add(mainInfo());

        JPanel panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(qualityHints);
                Color color1 = new Color(4,92,46);
                Color color2 = new Color(0,148,67);
                Point2D center = new Point2D.Float(275, 280);
                float radius = 1800;
                float[] dist = {0.0f, 0.2f};
                Color[] colors = {new Color(0,148,67), new Color(4,92,46)};
                RadialGradientPaint p =
                        new RadialGradientPaint(center, radius, dist, colors);
                g2.setPaint(p);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
            }
        };
        panel1.setBackground(Color.BLACK);
        panel1.setMaximumSize(new Dimension(550,560));
        panel1.add(logoPanel());
        panel1.add(healthPanel());
        panel1.add(mainInfo());
        panel1.add(hddPanel());
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        //add(hddPanel());
        add(panel1);

        getContentPane().addMouseListener(mouseHandler);
        getContentPane().addMouseMotionListener(mouseHandler);
        setUndecorated(true);
        //setBackground(new Color(95, 161, 65));
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setSize(550, 600);
        //setShape(new RoundRectangle2D.Double(0, 0, 550, 500, 20, 20));
        //setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel logoPanel() {
        String AbsolutePath = new File("").getAbsolutePath();
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,130,0,0);
        temp.setBackground(new Color(0, 0, 0, 0));
        temp.setOpaque(true);
        temp.setMaximumSize(new Dimension(520,50));
        JLabel picLabel = new JLabel();
        picLabel.setBackground(new Color(0, 0, 0, 0));
        picLabel.setForeground(Color.WHITE);
        picLabel.setPreferredSize(new Dimension(250,50));
        ImageIcon img = new ImageIcon(AbsolutePath+"\\logo.png");
        picLabel.setIcon(img);
        picLabel.setOpaque(true);
        temp.add(picLabel, c);


        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.gridy = 0;
        c.gridx = 2;
        c.insets = new Insets(0,100,0,0);
        JButton button = new JButton("X");
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMinimumSize(new Dimension(50, 10));
        button.setPreferredSize(new Dimension(30, 15));
        button.setMaximumSize(new Dimension(50,10));
        button.setForeground(new Color(95, 161, 65));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        temp.add(button, c);
        return temp;
    }

    private JPanel mainInfo() {
        mainInfo = new HashMap<String, JTextField>();
        JPanel mainInfoPanel = new JPanel();
        TitledBorder border = BorderFactory.createTitledBorder("Opis komputera");
        border.setTitleColor(Color.WHITE);
        mainInfoPanel.setLayout(new BoxLayout(mainInfoPanel, BoxLayout.Y_AXIS));
        mainInfoPanel.setBackground(new Color(0, 0, 0, 0));
        mainInfoPanel.setMaximumSize(new Dimension(540, 250));
        mainInfoPanel.setPreferredSize(new Dimension(540,250));
        String[] labels = new String[]{"System", "Płyta główna", "Procesor", "Pamięć Ram", "Karta graficzna", "Dyski"};
        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(new Color(0, 0, 0, 0));
        labelPanel.setBackground(new Color(0, 0, 0, 0));
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.X_AXIS));
        temp.add(labelPanel, BorderLayout.WEST);
        temp.add(fieldPanel, BorderLayout.CENTER);
        for(int i = 0; i < labels.length; i++) {
            JTextField field = new JTextField();
            field.setEditable(false);
            field.setBackground(Color.WHITE);
            field.setColumns(37);
            JLabel label = new JLabel(labels[i], JLabel.RIGHT);
            //label.setForeground(Color.WHITE);
            label.setLabelFor(field);
            label.setBackground(new Color(0, 0, 0, 0));
            labelPanel.add(label);
            JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
            p.setBackground(new Color(0, 0, 0, 0));
            p.add(field);
            fieldPanel.add(p);
            temp.setMaximumSize(new Dimension(540, 300));
            temp.setPreferredSize(new Dimension(540, 300));
            temp.setBackground(new Color(0, 0, 0, 0));
            mainInfo.put(labels[i], field);
            mainInfoPanel.add(temp);
        }
        temp.setBorder(border);
        return mainInfoPanel;
    }

    private JPanel healthPanel() {
        JPanel healthPanel = new JPanel();
        TitledBorder border = BorderFactory.createTitledBorder("Stan komputera");
        border.setTitleColor(Color.WHITE);
        healthPanel.setLayout(new BoxLayout(healthPanel, BoxLayout.X_AXIS));
        JPanel right = new JPanel();
        right.setBackground(new Color(0, 0, 0, 0));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(new JLabel("Stan dysków: "));
        int u = 0;
        int z = 0;
        for(HDD hdd : Drives.getList()) {
            if(hdd.getStatus().equalsIgnoreCase(" uwaga"))
                u++;
            if(hdd.getStatus().equalsIgnoreCase(" zły"))
                z++;
        }
        if(z == 0 && u > 0)
            right.add(createStateField(" uwaga"));
        else if(z > 0)
            right.add(createStateField(" zły"));
        else
            right.add(createStateField(" dobry"));
        healthPanel.add(right);
        JPanel left = new JPanel();
        left.setBackground(new Color(0, 0, 0, 0));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(new JLabel("Zużycie baterii: "));
        float bat = Battery.getUsage();
        System.out.println(bat);
        if(bat >= 0.7)
            left.add(createStateField(" dobry"));
        else if(bat < 0.7 && bat > 0.4)
            left.add(createStateField(" uwaga"));
        else
            left.add(createStateField(" zły"));
        healthPanel.add(left);
        healthPanel.setBackground(new Color(0, 0, 0, 0));
        healthPanel.setBorder(border);
        healthPanel.setMaximumSize(new Dimension(540,100));
        return healthPanel;
    }

    private JPanel hddPanel;
    private JPanel hddPanel() {
        hddPanel = new JPanel();
        diskTab = new JTabbedPane();
        //diskTab.setOpaque(false);
        diskTab.setBackground(new Color(4,92,46));
        diskTab.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI(){
            protected void paintContentBorder(Graphics g,int tabPlacement,int selectedIndex){}
        });
        //diskTab.setBorder(null);
        //diskTab.setBackground(new Color(0, 0, 0, 0));
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(0, 0, 0, 0));
        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(0, 0, 0, 0));
        hddPanel.add(diskTab);
        hddPanel.setLayout(new BoxLayout(hddPanel, BoxLayout.X_AXIS));
        hddPanel.setBackground(new Color(0, 0, 0, 0));
        hddPanel.setMaximumSize(new Dimension(540, 150));
        return hddPanel;
    }

    public void addDiskTab(String tabName, String[] params) {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new FlowLayout());
        tempPanel.setBackground(new Color(0, 0, 0, 0));
        JPanel right = new JPanel();
        right.setBackground(new Color(0, 0, 0, 0));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(new JLabel("Stan dysku: "));
        right.add(createStateField(params[params.length - 1]));

        String[] labels = new String[]{"Model: ", "Pojemność: ", "Czas użycia: "};
        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(new Color(0, 0, 0, 0));
        labelPanel.setBackground(new Color(0, 0, 0, 0));
        fieldPanel.setMaximumSize(new Dimension(540, 150));
        labelPanel.setMaximumSize(new Dimension(540, 150));
        JPanel temp = new JPanel();
        temp.setLayout(new BorderLayout());
        temp.add(labelPanel, BorderLayout.WEST);
        temp.add(fieldPanel, BorderLayout.CENTER);
        for(int i = 0; i < labels.length; i++) {
            JTextField field = createJTextArea(params[i]);
            field.setEditable(false);
            field.setBackground(Color.WHITE);
            field.setColumns(20);
            JLabel label = new JLabel(labels[i], JLabel.RIGHT);
            //label.setForeground(Color.WHITE);
            label.setLabelFor(field);
            label.setBackground(new Color(0, 0, 0, 0));
            labelPanel.add(label);
            /*JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
            p.setBackground(new Color(0, 0, 0, 0));
            p.add(field);
            fieldPanel.add(p);*/
            fieldPanel.add(field);
            temp.setMaximumSize(new Dimension(540, 150));
            temp.setBackground(new Color(0, 0, 0, 0));
        }
        tempPanel.add(right);
        tempPanel.add(temp);

        tabs.add(tempPanel);
        tabs.add(right);
        tabs.add(temp);
        tabs.add(fieldPanel);
        tabs.add(labelPanel);
        diskTab.addTab(tabName, null, tempPanel, params[0]);
    }

    private JTextField createStateField(String text) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(150, 75));
        field.setPreferredSize(new Dimension(150, 75));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(null);
        switch (text) {
            case " dobry":
                field.setBackground(Color.GREEN);
                break;
            case " uwaga":
                field.setBackground(Color.YELLOW);
                break;
            case " zły":
                field.setBackground(Color.RED);
                break;
            default:
                break;
        }
        field.setText(text);
        field.setEditable(false);
        return field;
    }

    private JTextField createJTextArea(String text) {
        JTextField field = new JTextField();
        field.setText(text);
        field.setEditable(false);
        return field;
    }

    private JMenuBar menuBar() {
        menuItem = new HashMap<String, JMenuItem>();
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Plik");
        JMenu helpMenu = new JMenu("Pomoc");
        JMenuItem exitFileMenu = new JMenuItem("Wyjdź");
        fileMenu.add(exitFileMenu);
        menuItem.put("exitFileMenu", exitFileMenu);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    public HashMap<String, JTextField> getMainInfo() {
        return mainInfo;
    }

    public HashMap<String, JMenuItem> getMenuItem() {
        return menuItem;
    }

    public JTabbedPane getDiskTab() {
        return diskTab;
    }

    public JPanel getHddPanel() {
        return hddPanel;
    }

    public ArrayList<JPanel> getTabs() {
        return tabs;
    }

    MouseAdapter mouseHandler = new MouseAdapter() {

        private Point offset;

        protected boolean isWithinBorder(MouseEvent e) {
            Point p = e.getPoint();
            Component comp = e.getComponent();
            return p.x < 40 || p.y < 40 || p.x > comp.getWidth() - 10 || p.y > comp.getHeight()  - 10;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Component comp = e.getComponent();
            if (isWithinBorder(e)) {
                //System.out.println("Move");
                comp.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                //System.out.println("Default");
                comp.setCursor(Cursor.getDefaultCursor());
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (offset != null) {
                Point pos = e.getLocationOnScreen();

                int x = pos.x - offset.x;
                int y = pos.y - offset.y;

                //System.out.println(x + "x" + y);

                SwingUtilities.getWindowAncestor(e.getComponent()).setLocation(x, y);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (isWithinBorder(e)) {
                Point pos = e.getComponent().getLocationOnScreen();
                offset = new Point(e.getLocationOnScreen());
                offset.x -= pos.x;
                offset.y -= pos.y;
            }
        }

    };
}
