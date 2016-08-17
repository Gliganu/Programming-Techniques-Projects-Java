package view;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Created by gliga on 3/15/2015.
 */
public class CustomerShape extends Ellipse2D.Double{

    private Color currentColor;


    public CustomerShape(double x, double y, double w, double h) {
        super(x, y, w, h);
        Random random = new Random();
        currentColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    /**
     * returns the random color which is given to every customer shape at its creation
     * @return
     */
    public Color getCurrentColor() {
        return currentColor;
    }

}
