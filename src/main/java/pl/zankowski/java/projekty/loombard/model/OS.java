package pl.zankowski.java.projekty.loombard.model;

/**
 * Created by Zano on 2015-03-26.
 */
public class OS extends ComputerParts {

    public OS(String name) {
        super(name);
        list.put("System", this);
    }
}
