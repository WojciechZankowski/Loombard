package pl.zankowski.java.projekty.loombard.model;

/**
 * Created by Zano on 2015-03-26.
 */
public class Motherboard extends ComputerParts {

    public Motherboard(String name) {
        super(name);
        list.put("Płyta główna", this);
    }

}
