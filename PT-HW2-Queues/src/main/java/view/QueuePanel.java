package view;

import controller.QueueController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by gliga on 3/15/2015.
 */
public class QueuePanel extends JPanel {

    private int circleRadius = 20;
    private int topMargin = 30;
    private int circleGap = 30;
    private String totalTime = "0";


    private List<CustomerShape> customerShapeList;

    public QueuePanel() {
        Random random = new Random();
        setBackground(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        customerShapeList = new ArrayList<CustomerShape>();
    }

    /**
     * used to display the list of shapes it has as instance variable in a
     * straight line in its middle in order to create the impression of a queue
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(customerShapeList.size() == QueueController.QUEUE_CAPACITY){
            g2d.setColor(Color.RED);

            g2d.fillRect(0,0,getWidth(),getHeight());

        }

        g2d.setColor(Color.white);
        g2d.drawString(totalTime, getWidth() / 2, topMargin / 2);
        g2d.drawLine(0, topMargin - 2, getWidth(), topMargin - 2);


        for (CustomerShape customerShape : customerShapeList) {
            g2d.setColor(customerShape.getCurrentColor());
            g2d.fill(customerShape);
        }


    }


    /**
     * modifies the number of elements in the queue
     * @param modification
     */
    public void modifyNumberOfCustomers(int modification) {

        if (modification < 0 && customerShapeList.size() == 0) {
            return;
        }

        int circleX = (getWidth() - circleRadius) / 2;


        if (modification > 0) {
            customerShapeList.add(new CustomerShape(circleX, topMargin + customerShapeList.size() * circleGap, circleRadius, circleRadius));
        } else {
            customerShapeList.remove(0);

            for (CustomerShape customerShape : customerShapeList) {
                customerShape.y -= circleGap;
            }

        }

        repaint();
    }

    /**
     * the position of each shape is decided based on the total width of the panel, but as the number of panels
     * increase/decrease by the wishes of the user, so do their width. As a result,
     * every time the user add/removes a queue, all the position of the elements from the other queues need to be
     * recalculated
     */
    public void adjustCustomerShapesPosition() {
        for (CustomerShape customerShape : customerShapeList) {
            customerShape.x = (getWidth() - circleRadius) / 2;
        }
        repaint();
    }

    /**
     * every queue displays on top of it the total waiting time for that queue.
     * This method is used to set that time because the time thread is not in here, but in MainFrame
     * @param totalTime
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}

