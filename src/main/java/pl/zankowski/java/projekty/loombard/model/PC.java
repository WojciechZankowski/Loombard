package pl.zankowski.java.projekty.loombard.model;

import pl.zankowski.java.projekty.loombard.model.OO.*;
import pl.zankowski.java.projekty.loombard.model.OO.SystemOS;

import java.util.ArrayList;

/**
 * Created by Zano on 2015-04-02.
 */
public class PC {

    private Battery battery;
    private ArrayList<Drive> drives;
    private ArrayList<GPU> gpus;
    private Processor processor;
    private RAM ram;
    private SystemOS systemOS;
    private String model;

    public PC(String model, SystemOS systemOS, RAM ram, Processor processor, Battery baterry) {
        this.model = model;
        this.systemOS = systemOS;
        this.ram = ram;
        this.processor = processor;
        this.battery = baterry;
        drives = new ArrayList<Drive>();
        gpus = new ArrayList<GPU>();
    }

    public PC(String model) {
        this.model = model;
        drives = new ArrayList<Drive>();
        gpus = new ArrayList<GPU>();
    }

    public PC() {
        drives = new ArrayList<Drive>();
        gpus = new ArrayList<GPU>();
    }

    public Battery getBattery() {
        return battery;
    }

    public ArrayList<Drive> getDrives() {
        return drives;
    }

    public ArrayList<GPU> getGpus() {
        return gpus;
    }

    public Processor getProcessor() {
        return processor;
    }

    public RAM getRam() {
        return ram;
    }

    public SystemOS getSystemOS() {
        return systemOS;
    }

    public String getModel() {
        return model;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setRam(RAM ram) {
        this.ram = ram;
    }

    public void setSystemOS(SystemOS systemOS) {
        this.systemOS = systemOS;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void addDrive(Drive drive) {
        drives.add(drive);
    }

    public void addGPUS(GPU gpu) {
        gpus.add(gpu);
    }
}
