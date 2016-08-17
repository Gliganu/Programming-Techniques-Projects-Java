package view;

import model.Observer;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gliga on 3/15/2015.
 */
public class InfoPanel extends JPanel {

    private JLabel timePassedLabel;
    private JLabel averageTimeWaitingLabel;
    private JLabel currentNumberOfCustomersLabel;
    private JLabel customerSpeedLabel;
    private JLabel serviceSpeedLabel;
    private JButton addQueueButton;
    private JButton removeQueueButton;
    private JButton increseCustomerSpeedButton;
    private JButton decreaseCustomerSpeedButton;
    private JButton increaseServiceSpeedButton;
    private JButton decreaseServiceSpeedButton;

    private JPanel labelPanel;
    private JPanel buttonPanel;

    private int timePassed = 0;
    private int averageTimeForWaiting = 0;
    private int currentNumberCustomers = 0;
    private int totalNumberOfCustomersServed = 0;
    private int sumTimeWaited = 0;

    private Observer observer;
    private boolean timeRunning = true;

    private Map<Integer,Integer> chartMap = new HashMap<Integer, Integer>();
    private int simulationTotalTime;

    public InfoPanel() {
        buildGui();
    }

    /**
     * the most important method which builds and ties every gui element in the application to each other.
     */
    private void buildGui() {

        timePassedLabel = new JLabel("Total time passed: " + timePassed + " seconds");
        averageTimeWaitingLabel = new JLabel("Average time for waiting: " + averageTimeForWaiting + " seconds");
        currentNumberOfCustomersLabel = new JLabel("Customers: " + currentNumberCustomers);
        customerSpeedLabel = new JLabel("Customer addition speed: 0 ms");
        serviceSpeedLabel = new JLabel("Maximum service time: 0 ms");
        addQueueButton = new JButton("Queue number +");
        removeQueueButton = new JButton("Queue number -");
        increseCustomerSpeedButton = new JButton("Customer speed +");
        decreaseCustomerSpeedButton = new JButton("Customer speed -");
        increaseServiceSpeedButton = new JButton("Service speed +");
        decreaseServiceSpeedButton = new JButton("Service speed -");

        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));

        labelPanel.add(timePassedLabel);
        labelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        labelPanel.add(averageTimeWaitingLabel);
        labelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        labelPanel.add(currentNumberOfCustomersLabel);
        labelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        labelPanel.add(customerSpeedLabel);
        labelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        labelPanel.add(serviceSpeedLabel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 10, 5));

        buttonPanel.add(addQueueButton);
        buttonPanel.add(increseCustomerSpeedButton);
        buttonPanel.add(increaseServiceSpeedButton);

        buttonPanel.add(removeQueueButton);
        buttonPanel.add(decreaseCustomerSpeedButton);
        buttonPanel.add(decreaseServiceSpeedButton);


        addQueueButton.setAlignmentX(CENTER_ALIGNMENT);
        increseCustomerSpeedButton.setAlignmentX(CENTER_ALIGNMENT);
        removeQueueButton.setAlignmentX(CENTER_ALIGNMENT);
        decreaseCustomerSpeedButton.setAlignmentX(CENTER_ALIGNMENT);


        addQueueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (observer.isSimulationStarted()) {
                    observer.numberOfQueuesChanged(1);

                }
            }
        });

        removeQueueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (observer.isSimulationStarted()) {

                    observer.numberOfQueuesChanged(-1);
                }
            }
        });

        increseCustomerSpeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (observer.isSimulationStarted()) {
                    observer.changeAdditionSpeed(-100);

                }
            }
        });

        decreaseCustomerSpeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (observer.isSimulationStarted()) {

                    observer.changeAdditionSpeed(100);
                }
            }
        });

        increaseServiceSpeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (observer.isSimulationStarted()) {
                    observer.changeServiceSpeed(-500);

                }
            }
        });

        decreaseServiceSpeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (observer.isSimulationStarted()) {
                    observer.changeServiceSpeed(500);
                }
            }
        });

        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));

        add(labelPanel);
        add(buttonPanel);


    }


    /**
     * modifies the number of customers displaying to the user
     * @param modification
     */
    public void modifyNumberOfCustomers(int modification) {

        currentNumberCustomers += modification;

        if (currentNumberCustomers < 0) {
            currentNumberCustomers = 0;
        }

        if (modification < 0) {
            totalNumberOfCustomersServed++;
        }else{
            chartMap.put(timePassed,currentNumberCustomers);
        }

        currentNumberOfCustomersLabel.setText("Customers: " + currentNumberCustomers);

    }

    /**
     * adding time the last served customer waited in order to update the average time waited by everyone
     * @param waitedTime
     */
    public void addToAverageTimeForWaiting(int waitedTime) {

        sumTimeWaited += waitedTime;
        averageTimeForWaiting = sumTimeWaited / totalNumberOfCustomersServed;


        averageTimeWaitingLabel.setText("Average time for waiting: " + averageTimeForWaiting + " seconds");

    }

    /**
     * starts the simulation
     */
    public void beginSimulation() {

        simulationTotalTime = observer.getSimulationTotalTime();
        Thread timeThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (timeRunning) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(timePassed > simulationTotalTime){
                        observer.endSimulation();
                        timeRunning = false;
                        timePassedLabel.setText("Total time passed: " + (timePassed-1) + " seconds");
                        Thread.currentThread().interrupt();
                    }else{
                        timePassed++;
                         timePassedLabel.setText("Total time passed: " + timePassed + " seconds");
                    }

                }
            }
        });

        timeThread.start();


    }

    /**
     * updates the label which displays the current customer addition time
     * @param newSpeed
     */
    public void updateCustomerSpeedLabel(int newSpeed) {
        customerSpeedLabel.setText("Customer addition time: " + newSpeed + " ms");
    }

    /**
     * updates the label which displays the current customer service time
     * @param serviceSpeed
     */
    public void updateServiceSpeedLabel(int serviceSpeed) {
        serviceSpeedLabel.setText("Maximum service time: " + serviceSpeed + " ms");
    }

    /**
     * end the simulation
     */
    public void endSimulation() {
        timeRunning = false;
        setEndSimulationGraphics();
    }

    /**
     * sets the observer
     * @param observer
     */
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     * sets all the labels color to red to mark the fact that the simulation has ended
     */
    public void setEndSimulationGraphics(){

        timePassedLabel.setForeground(Color.red);
        averageTimeWaitingLabel.setForeground(Color.red);
        currentNumberOfCustomersLabel.setForeground(Color.red);
        customerSpeedLabel.setForeground(Color.red);
        serviceSpeedLabel.setForeground(Color.red);
        displayChart();

    }

    /**
     * displays the chart
     */
    private void displayChart() {
        final CustomerChart demo = new CustomerChart(chartMap);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }


}
