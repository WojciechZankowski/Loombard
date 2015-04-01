package pl.zankowski.java.projekty.loombard.kontrolery;

import pl.zankowski.java.projekty.loombard.model.*;
import pl.zankowski.java.projekty.loombard.widoki.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
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
        ComputerParts cp = new ComputerParts("");
        HashMap<String, ComputerParts> list = cp.getList();
        view.getFields().get("Dyski").setText("");
        for(String key : list.keySet())
            view.getFields().get(key).setText(list.get(key).toString());
        for(HDD drive : Drives.getList())
            view.getFields().get("Dyski").setText(view.getFields().get("Dyski").getText()+drive.getDiskSize()+" / ");
    }

    private void fillDiskTabs() {
        ArrayList<HDD> disks = Drives.getList();
        int min = 100;
        for(HDD drive : disks) {
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
            view.addDiskCard(new String[]{drive.getModel(), drive.getDiskSize(), drive.getHours(), drive.getStatus()}, view.createStatusField(state, ""));
        }
        view.setDriveField(min, "");
    }

    private void updateBatteryField() {
        float usage = Battery.getUsage();
        int state = 10;
        if(usage > 85)
            state = 2;
        else if(usage > 70)
            state = 1;
        else if(usage == 0)
            state = 10;
        else
            state = 0;
        view.setBatteryField(state, ""+usage+"początkowej pojemności");
    }

}
