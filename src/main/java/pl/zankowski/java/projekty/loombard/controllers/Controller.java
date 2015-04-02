package pl.zankowski.java.projekty.loombard.controllers;

import pl.zankowski.java.projekty.loombard.model.*;
import pl.zankowski.java.projekty.loombard.model.OO.Battery;
import pl.zankowski.java.projekty.loombard.model.OO.Drive;
import pl.zankowski.java.projekty.loombard.model.OO.GPU;
import pl.zankowski.java.projekty.loombard.views.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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
        controlButtons();
    }

    private void controlButtons() {
        HashMap<String, JButton> buttons = view.getButtons();
        buttons.get("exitButton").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttons.get("previousButton").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) view.getCardsPanel().getLayout();
                cardLayout.previous(view.getCardsPanel());
            }
        });
        buttons.get("nextButton").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) view.getCardsPanel().getLayout();
                cardLayout.next(view.getCardsPanel());
            }
        });
        buttons.get("readButton").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser;
                if(!((chooser = openDialog("OUT File (.out)", "out")) == null)) {
                    try {
                        model.readOutput(chooser.getSelectedFile());
                        fillMainFields();
                        fillDiskTabs();
                        updateBatteryField();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        buttons.get("testButton").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.initTest();
                fillMainFields();
                fillDiskTabs();
                updateBatteryField();
            }
        });
    }

    private void fillMainFields() {
        PC pc = model.pc;
        String[] keys = new String[]{"System", "Model", "Procesor", "Pamięć Ram", "Karta graficzna", "Dyski"};
        for(String key : keys) {
            switch (key) {
                case ("System"): view.getFields().get(key).setText(pc.getSystemOS().getName());
                    break;
                case ("Model"): view.getFields().get(key).setText(pc.getModel());
                    break;
                case ("Procesor"): view.getFields().get(key).setText(pc.getProcessor().getName());
                    break;
                case ("Pamięć Ram"): view.getFields().get(key).setText(pc.getRam().getSize()+"MB RAM");
                    break;
                case ("Karta graficzna"):
                    String line = "";
                    for(GPU gpu : pc.getGpus()) {
                        line = line + gpu.getName() + " / ";
                    }
                    view.getFields().get(key).setText(line);
                    break;
                case ("Dyski"):
                    String line2 = "";
                    for(Drive drive : pc.getDrives()) {
                        line2 = line2 + drive.getSize() + " / ";
                    }
                    view.getFields().get(key).setText(line2);
                    break;
            }
        }
    }

    private void fillDiskTabs() {
        ArrayList<Drive> disks = model.pc.getDrives();
        int min = 100;
        for(Drive drive : disks) {
            int state = 10;
            switch(drive.getStatus()) {
                case (" zły"): state = 0;
                    break;
                case (" uwaga"): state = 1;
                    break;
                case (" dobry"): state = 2;
                    break;
            }
            if(min > state)
                min = state;
            view.addDiskCard(new String[]{drive.getName(), drive.getSize(), drive.getHours(), drive.getStatus()}, view.createStatusField(state, ""));
        }
        view.setDriveField(min, "");
    }

    private void updateBatteryField() {
        float usage = model.pc.getBattery().getUsage();
        int state = 10;
        if(usage > 85)
            state = 2;
        else if(usage > 70)
            state = 1;
        else if(usage == 0)
            state = 10;
        else
            state = 0;
        view.setBatteryField(state, "" + usage + "początkowej pojemności");
    }

    private JFileChooser openDialog(String description, String ext) {
        String path = new File("").getAbsolutePath();
        JFileChooser chooser = new JFileChooser(path+"\\Output");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(description, ext);
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser;
        }
        return null;
    }

}
