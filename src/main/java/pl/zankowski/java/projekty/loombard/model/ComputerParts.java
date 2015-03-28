package pl.zankowski.java.projekty.loombard.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zano on 2015-03-26.
 */
public class ComputerParts {

    protected static HashMap<String, ComputerParts> list = new HashMap<String, ComputerParts>();

    private String name = "";

    public ComputerParts(String name) {
        this.name = name;
    }

    public void addPart(String key, ComputerParts cp) {
        list.put(key, cp);
    }

    public String getName() {
        return name;
    }

    public HashMap<String, ComputerParts> getList() {
        return list;
    }

    @Override
    public String toString() {
        return name;
    }
}
