package pl.zankowski.java.projekty.loombard;

import pl.zankowski.java.projekty.loombard.controllers.Controller;
import pl.zankowski.java.projekty.loombard.model.Model;
import pl.zankowski.java.projekty.loombard.views.View;

/**
 * Created by Zano on 2015-03-25.
 */
public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.control();
    }

}
