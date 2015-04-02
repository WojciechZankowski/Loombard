package pl.zankowski.java.projekty.loombard.model.OO;

/**
 * Created by Zano on 2015-04-02.
 */
public class Drive extends ComputerPart {

    private String size;
    private String sn;
    private String hours;
    private String status;

    public Drive(String name) {
        super(name);
    }

    public Drive(String name, String size, String sn) {
        super(name);
        this.size = size;
        this.sn = sn;
    }

    public String getSize() {
        return size;
    }

    public String getSn() {
        return sn;
    }

    public String getHours() {
        return hours;
    }

    public String getStatus() {
        return status;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
