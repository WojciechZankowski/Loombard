package pl.zankowski.java.projekty.loombard.kontrolery;

import pl.zankowski.java.projekty.loombard.model.*;
import pl.zankowski.java.projekty.loombard.widoki.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zano on 2015-03-25.
 */
public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void control() {
        System.out.println("WHAT");
        //JWindow window = makeWindow();
        //window.add(view);
        fillMainFields();
        fillDiskTabs();
        view.getDiskTab().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                view.getDiskTab().repaint();
                view.getDiskTab().invalidate();
                view.getDiskTab().validate();
                view.getHddPanel().repaint();
                view.getHddPanel().invalidate();
                view.getHddPanel().validate();
                for(JPanel panel : view.getTabs()) {
                    panel.repaint();
                    panel.revalidate();
                    panel.invalidate();
                }
            }
        });
    }

    private void fillMainFields() {
        ComputerParts cp = new ComputerParts("");
        HashMap<String, ComputerParts> list = cp.getList();
        for(String key : list.keySet())
            view.getMainInfo().get(key).setText(list.get(key).toString());
        for(HDD drive : Drives.getList())
            view.getMainInfo().get("Dyski").setText(view.getMainInfo().get("Dyski").getText()+drive.getDiskSize()+" / ");
    }

    private void fillDiskTabs() {
        ArrayList<HDD> disks = Drives.getList();
        int count = 1;
        for(HDD drive : disks) {
            view.addDiskTab("Dysk "+count++, new String[]{drive.getModel(), drive.getDiskSize(), drive.getHours(), drive.getStatus()});
        }
    }

    private JWindow makeWindow() {
        JWindow window = new JWindow();
        window.setSize(550, 500);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });

        MouseAdapter mouseHandler = new MouseAdapter() {

            private Point offset;

            protected boolean isWithinBorder(MouseEvent e) {
                Point p = e.getPoint();
                Component comp = e.getComponent();
                return p.x < 10 || p.y < 10 || p.x > comp.getWidth() - 10 || p.y > comp.getHeight()  - 10;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Component comp = e.getComponent();
                if (isWithinBorder(e)) {
                    System.out.println("Move");
                    comp.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                } else {
                    System.out.println("Default");
                    comp.setCursor(Cursor.getDefaultCursor());
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (offset != null) {
                    Point pos = e.getLocationOnScreen();

                    int x = pos.x - offset.x;
                    int y = pos.y - offset.y;

                    System.out.println(x + "x" + y);

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

        window.getContentPane().addMouseListener(mouseHandler);
        window.getContentPane().addMouseMotionListener(mouseHandler);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        return window;
    }



}
