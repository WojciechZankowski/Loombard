package pl.zankowski.java.projekty.loombard.model;

import java.util.ArrayList;

/**
 * Created by Zano on 2015-03-26.
 */
public class HDD {

    public static ArrayList<HDD> list = new ArrayList<HDD>();
    private String name;
    private String model;
    private String diskSize;
    private String status;
    private String hours;

    public HDD(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getDiskSize() {
        return diskSize;
    }

    public String getStatus() {
        return status;
    }

    public String getHours() {
        return hours;
    }
}
