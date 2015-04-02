package pl.zankowski.java.projekty.loombard.model.OO;

/**
 * Created by Zano on 2015-04-02.
 */
public class Battery extends ComputerPart {

    private float usage;

    public Battery(String name) {
        super(name);
    }

    public Battery(String name, float usage) {
        super(name);
        this.usage = usage;
    }

    public float getUsage() {
        return usage;
    }

    public void setUsage(float usage) {
        this.usage = usage;
    }
}
