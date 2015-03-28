package pl.zankowski.java.projekty.loombard.model;

/**
 * Created by Zano on 2015-03-26.
 */
public class CPU extends ComputerParts {

    private String sn;

    public CPU(String name) {
        super(name);

        list.put("Procesor", this);
    }

    public void setSN(String sn) {
        this.sn = sn;
    }

}
