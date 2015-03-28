package pl.zankowski.java.projekty.loombard.model;

import java.util.ArrayList;

/**
 * Created by Zano on 2015-03-28.
 */
public class Drives {

    private static ArrayList<HDD> drives = new ArrayList<HDD>();

    public static void addDrive(HDD hdd) {
        drives.add(hdd);
    }

    public static ArrayList<HDD> getList() {
        return drives;
    }

}
