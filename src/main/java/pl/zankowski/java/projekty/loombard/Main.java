package pl.zankowski.java.projekty.loombard;

import pl.zankowski.java.projekty.loombard.kontrolery.Controller;
import pl.zankowski.java.projekty.loombard.model.Model;
import pl.zankowski.java.projekty.loombard.widoki.View;

/**
 * Created by Zano on 2015-03-25.
 */
public class Main {

    public static void main(String[] args) {

        Model model = new Model();
        View view = new View("Loombard");
        Controller controller = new Controller(model, view);
        controller.control();
    }

}
