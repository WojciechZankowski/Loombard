package pl.zankowski.java.projekty.loombard.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;

/**
 * Created by Zano on 2015-03-29.
 */
public class Loading extends JFrame {

    public Loading() {
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

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        String AbsolutePath = new File("").getAbsolutePath();
        JLabel picLabel = new JLabel();
        picLabel.setBackground(new Color(0, 0, 0, 0));
        picLabel.setForeground(Color.WHITE);
        picLabel.setPreferredSize(new Dimension(250, 50));
        ImageIcon img = new ImageIcon(AbsolutePath+"\\logo.png");
        picLabel.setIcon(img);
        picLabel.setOpaque(true);
        panel1.add(picLabel);
        JLabel label = new JLabel("                             testowanie...");
        panel1.add(label);
        add(panel1);

        getContentPane().addMouseListener(mouseHandler);
        getContentPane().addMouseMotionListener(mouseHandler);
        setUndecorated(true);
        //setBackground(new Color(95, 161, 65));
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setSize(550, 200);
        //setShape(new RoundRectangle2D.Double(0, 0, 550, 500, 20, 20));
        //setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
