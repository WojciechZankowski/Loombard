package pl.zankowski.java.projekty.loombard.model;

/**
 * Created by Zano on 2015-03-28.
 */
public class Battery {

    private static float usage;

    public Battery(float usage) {
        this.usage = usage;
    }

    public static float getUsage() {
        return usage;
    }
}
