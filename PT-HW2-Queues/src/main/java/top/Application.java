package top;

import controller.QueueController;
import view.MainFrame;

/**
 * Created by gliga on 3/14/2015.
 */
public class Application {

    /**
     * main entry point in the applicaiton
     * @param args
     */
    public static void main(String[] args) {


        QueueController controller = new QueueController(5);

        MainFrame frame = new MainFrame(controller);

        controller.addObserver(frame);


    }

}
