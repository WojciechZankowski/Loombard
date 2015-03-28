package pl.zankowski.java.projekty.loombard.model;

/**
 * Created by Zano on 2015-03-26.
 */
public class Ram extends ComputerParts {

    private String size;

    public Ram(String name) {
        super(name);
        if(list.get("Pamięć Ram") == null)
            list.put("Pamięć Ram", this);
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize(String  size) {
        return size;
    }

    @Override
    public String toString() {
        return size;
    }

}
