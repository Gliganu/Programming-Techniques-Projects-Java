package top;

import controller.Calculator;
import model.Polynomial;
import model.PolynomialUtils;
import view.MainFrame;

/**
 * Created by gliga on 3/1/2015.
 */

/**
 * Top class of the application which will start the app
 */
public class Main {

    public static void main(String[] args) {

        MainFrame main = new MainFrame();
        main.buildGui();

    }
}
