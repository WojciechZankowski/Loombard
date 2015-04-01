package pl.zankowski.java.projekty.loombard.widoki;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zano on 2015-03-25.
 */
public class View extends JFrame {

    private HashMap<String, JTextField> fields = new HashMap<String, JTextField>();
    private HashMap<String, JButton> buttons = new HashMap<String, JButton>();
    private JPanel cardsPanel;
    private JTextField driveField;
    private JTextField batteryField;
    private final static int width = 550;
    private final static int height = 530;
    private Color transparent = new Color(0,0,0,0);

    public View() {

        JPanel panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(qualityHints);
                Point2D center = new Point2D.Float(275, 280);
                float radius = 1800;
                float[] dist = {0.0f, 0.2f};
                Color[] colors = {new Color(0,148,67), new Color(4,92,46)};
                RadialGradientPaint p =
                        new RadialGradientPaint(center, radius, dist, colors);
                g2.setPaint(p);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
                g2.dispose();
            }
        };
        panel1.setMaximumSize(new Dimension(width, height));
        /////////////////////////////////////////


        panel1.add(createLogoPanel());
        panel1.add(createMenuPanel());
        panel1.add(Box.createRigidArea(new Dimension(0, 5)));
        panel1.add(createComputerHealthPanel());
        panel1.add(createInfoPanel());
        panel1.add(Box.createRigidArea(new Dimension(0, 5)));
        panel1.add(createHddPanel());
        //addDiskCard(new String[]{"", "", "", ""}, createJTextField(""));


        ////////////////////

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        //add(cardsPanel());
        add(panel1);

        getContentPane().addMouseListener(mouseHandler);
        getContentPane().addMouseMotionListener(mouseHandler);
        setUndecorated(true);
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setSize(width, height);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() {
        return null;
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = createJPanel(new GridBagLayout(), transparent, new Dimension(width - 20, 50), false);
        JButton exitButton = createCustomButton("X", Color.WHITE, new Dimension(40,15));

        buttons.put("exitButton", exitButton);

        /*
         * GridBagConstraints: gridx,gridy,gridwidth,gridheight,weightx,weighty,anchor,fill,insets,ipadx,ipady;
         */
        logoPanel.add(createPicLabel(transparent, new ImageIcon(getProjectAbolutePath() + "\\resources\\logo.png")),
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        logoPanel.add(exitButton,
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        return logoPanel;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = createJPanel(null, Color.WHITE, new Dimension(width, 25), true);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
        JButton readButton = createJButton("Wczytaj test", Color.WHITE);
        JButton testButton = createJButton("Przeprowadź test", Color.WHITE);
        readButton.setForeground(new Color(4, 92, 46));
        testButton.setForeground(new Color(4, 92, 46));
        buttons.put("readButton", readButton);
        buttons.put("testButton", testButton);
        menuPanel.add(readButton);
        menuPanel.add(testButton);
        return menuPanel;
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = createFormsPanel(createJPanel(null, transparent, new Dimension(width - 20, 200), false),
                new String[]{"System", "Model", "Procesor", "Pamięć Ram", "Karta graficzna", "Dyski"});
        infoPanel.setBorder(createColoredTitledBorder(BorderFactory.createTitledBorder("Opis komputera"), Color.WHITE));
        return infoPanel;
    }
    
    private JPanel createComputerHealthPanel() {
        JPanel healthPanel = createJPanel(new GridLayout(1, 4), transparent, new Dimension(width - 40, 100), false);

        JLabel driveLabel = createJLabel("Stan dysków:", transparent);
        driveLabel.setHorizontalAlignment(JLabel.CENTER);
        driveField = createJTextField("");
        JLabel batteryLabel = createJLabel("Stan baterii:", transparent);
        batteryLabel.setHorizontalAlignment(JLabel.CENTER);
        batteryField = createJTextField("");

        fields.put("driveField", driveField);
        fields.put("batteryField", batteryField);

        healthPanel.add(driveLabel);
        healthPanel.add(driveField);
        healthPanel.add(batteryLabel);
        healthPanel.add(batteryField);
        return healthPanel;
    }

    private TitledBorder createColoredTitledBorder(TitledBorder border, Color color) {
        border.setTitleColor(color);
        return border;
    }

    private JPanel createFormsPanel(JPanel panel, String[] labels) {
        GroupLayout layout = setGroupLayoutSettings(new GroupLayout(panel));
        GroupLayout.ParallelGroup parallelGroup = layout.createParallelGroup();
        GroupLayout.SequentialGroup sequentialGroup = layout.createSequentialGroup();
        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(parallelGroup));
        JLabel[] tempLabels = new JLabel[labels.length];
        layout.setVerticalGroup(sequentialGroup);
        for(int i = 0; i < labels.length; i++) {
            JTextField field = createJTextField("");
            tempLabels[i] = createJLabel(labels[i], transparent);
            tempLabels[i].setLabelFor(field);
            fields.put(labels[i], field);
            parallelGroup.addGroup(layout.createSequentialGroup().addComponent(tempLabels[i]).addComponent(field));
            sequentialGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(tempLabels[i]).addComponent(field));
            layout.linkSize(SwingConstants.HORIZONTAL, tempLabels[i], tempLabels[0]);
        }
        panel.setLayout(layout);
        return panel;
    }

    private JTextField createJTextField(String text) {
        JTextField field = new JTextField();
        field.setText(text);
        field.setEditable(false);
        return field;
    }

    private GroupLayout setGroupLayoutSettings(GroupLayout layout) {
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        return layout;
    }

    protected String getProjectAbolutePath() {
        return new File("").getAbsolutePath();
    }

    private JLabel createJLabel(String text, Color bgColor) {
        JLabel label = new JLabel(text);
        label.setBackground(bgColor);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JLabel createPicLabel(Color bgColor, ImageIcon img) {
        JLabel picLabel = createJLabel("", bgColor);
        picLabel.setIcon(img);
        return picLabel;
    }

    private JButton createCustomButton(String text, Color bgColor, Dimension prefSize) {
        JButton exitButton = createJButton(text, bgColor);
        exitButton.setOpaque(true);
        exitButton.setBorder(null);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(prefSize);
        exitButton.setForeground(new Color(95, 161, 65));
        return exitButton;
    }

    private JButton createJButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        return button;
    }

    private JPanel createJPanel(LayoutManager layout, Color bgColor, Boolean opaque) {
        return createJPanel(layout, bgColor, null, opaque);
    }

    private JPanel createJPanel(LayoutManager layout, Color bgColor, Dimension maxSize, Boolean opaque) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(bgColor);
        panel.setOpaque(opaque);
        if(maxSize != null)
            panel.setMaximumSize(maxSize);
        return panel;
    }

    private JPanel createHddPanel() {
        JPanel hddPanel = createJPanel(new GridBagLayout(), transparent, new Dimension(width - 40, 150), false);
        JButton previousButton = createCustomButton("<", Color.WHITE, new Dimension(20, 20));
        JButton nextButton = createCustomButton(">", Color.WHITE, new Dimension(20, 20));
        cardsPanel = createJPanel(new CardLayout(), transparent, new Dimension(width - 40, 150), false);

        buttons.put("previousButton", previousButton);
        buttons.put("nextButton", nextButton);

        hddPanel.add(previousButton,
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        hddPanel.add(nextButton,
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        hddPanel.add(cardsPanel,
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        return hddPanel;
    }

    public void addDiskCard(String[] params, JTextField statusField) {
        JPanel tabPanel = createJPanel(null, transparent, new Dimension(width - 20, 100), false);
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.X_AXIS));
        JPanel statusPanel = createJPanel(null, transparent, false);
        statusPanel.setLayout(new GridBagLayout());
        statusPanel.add(createJLabel("Stan dysku: ", Color.WHITE),
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        statusPanel.add(statusField,
                new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        String[] labels = new String[]{"Model: ", "Pojemność: ", "Czas użycia: "};
        JPanel formPanel = createFormsPanel(createJPanel(null, transparent, new Dimension(370, 100), false), labels);
        for(int i = 0; i < labels.length; i++)
            fields.get(labels[i]).setText(params[i]);
        formPanel.setPreferredSize(new Dimension(370, 100));
        tabPanel.add(statusPanel);
        tabPanel.add(formPanel);

        cardsPanel.add(tabPanel);
        cardsPanel.repaint();
        //cardsPanel.revalidate();
        //cardsPanel.invalidate();
    }

    public JTextField createStatusField(int state, String text) {
        return changeStatusFieldSettings(new JTextField(), state, text);
    }

    public JTextField changeStatusFieldSettings(JTextField field, int state, String text) {
        field.setEditable(false);
        field.setMaximumSize(new Dimension(100, 50));
        field.setPreferredSize(new Dimension(100, 50));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(null);
        switch (state) {
            case (0): field.setBackground(new Color(183,20,39));
                field.setText("zły "+text);
                break;
            case (1): field.setBackground(new Color(255,230,88));
                field.setText("uwaga "+text);
                break;
            case (2): field.setBackground(new Color(17,140,78));
                field.setText("dobry "+text);
                break;
            default: field.setBackground(Color.GRAY);
                field.setText("nieznany");
                break;
        }
        return field;
    }

    public void setDriveField(int state, String text) {
        changeStatusFieldSettings(driveField, state, text);
    }

    public void setBatteryField(int state, String text) {
        changeStatusFieldSettings(batteryField, state, text);
    }

    public JPanel getCardsPanel() {
        return cardsPanel;
    }

    public HashMap<String, JButton> getButtons() {
        return buttons;
    }

    public HashMap<String, JTextField> getFields() {
        return fields;
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
