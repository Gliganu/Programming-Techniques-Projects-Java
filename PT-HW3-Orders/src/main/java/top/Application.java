package top;

import controller.OPDept;
import model.Order;
import org.apache.commons.lang3.RandomStringUtils;
import view.MainFrame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gliga on 3/29/2015.
 */
public class Application {

    /**
     * Top methods
     * @param args
     */
    public static void main(String[] args) {

        OPDept processingDepartment = new OPDept();
        MainFrame frame = new MainFrame(processingDepartment);

    }
}
