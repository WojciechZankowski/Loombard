package pl.zankowski.java.projekty.loombard.model;

/**
 * Created by Zano on 2015-03-27.
 */
public class GraphicCard extends ComputerParts {

    public GraphicCard(String name) {
        super(name);
        list.put("Karta graficzna", this);
    }
}
