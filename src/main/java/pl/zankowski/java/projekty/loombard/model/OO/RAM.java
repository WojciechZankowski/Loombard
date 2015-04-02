package pl.zankowski.java.projekty.loombard.model.OO;

/**
 * Created by Zano on 2015-04-02.
 */
public class RAM extends ComputerPart {

    private Long size;

    public RAM(String name) {
        super(name);
    }

    public RAM(String name, Long size) {
        super(name);
        this.size = size;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
