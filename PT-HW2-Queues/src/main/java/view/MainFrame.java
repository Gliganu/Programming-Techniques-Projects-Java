package view;

import controller.QueueController;
import model.Observer;
import model.QueueChangedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gliga on 3/15/2015.
 */
public class MainFrame extends JFrame implements Observer {

    private List<QueuePanel> queuePanelList;
    private QueueController controller;
    private JPanel mainPanel;
    private InfoPanel infoPanel;
    private int totalSimulationTime;

    public MainFrame(QueueController controller) {
        this.controller = controller;
        addMenu();
        buildGui();
    }

    /**
     * adds the top bar menu in the application. From here the user can select
     * multiple items which such as “Start Application”, “Stop Application”, “What If Analysis” or “Exit”
     */

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu simulationMenu = new JMenu("Simulation");

        final JMenuItem startSimulationItem = new JMenuItem("Start simulation");
        JMenuItem stopSimulationItem = new JMenuItem("Stop simulation");
        JMenuItem whatIfAnalysisItem = new JMenuItem("What if analysis");

        JMenuItem exitItem = new JMenuItem("Exit");

        simulationMenu.add(startSimulationItem);
        simulationMenu.add(stopSimulationItem);
        simulationMenu.add(whatIfAnalysisItem);
        fileMenu.add(exitItem);

        startSimulationItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    totalSimulationTime = Integer.parseInt(JOptionPane.showInputDialog("Enter simulation time in seconds: "));
                    if(totalSimulationTime<=0){
                        throw new NumberFormatException();
                    }
                }catch(NumberFormatException exception){
                    totalSimulationTime = Integer.parseInt(JOptionPane.showInputDialog("Not a valid input. Please re enter simulation time: "));
                    if(totalSimulationTime<=0){
                        throw new NumberFormatException();
                    }
                }

                controller.beginSimulation();
                infoPanel.beginSimulation();
                startSimulationItem.setEnabled(false);


            }
        });

        stopSimulationItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endSimulation();

            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(-1);
            }
        });

        whatIfAnalysisItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setUpWhatIfAnalysisFrame();
            }
        });



        menuBar.add(fileMenu);
        menuBar.add(simulationMenu);

        setJMenuBar(menuBar);
    }
    /**
     *constructs the gui
     */
    private void buildGui() {

        initializeComponents();

    }

    /**
     * builds the option pane which will interact with the user if he selects the
     * “What If Analysis” menu item. It holds text fields for all the inputs. It also has validation on those inputs
     */
    private void setUpWhatIfAnalysisFrame(){

        JTextField simulationIntervalField = new JTextField(5);
        JTextField numberOfQueuesField = new JTextField(5);
        JTextField serviceTimeField = new JTextField(5);

        JPanel myPanel = new JPanel();

        myPanel.setLayout(new BoxLayout(myPanel,BoxLayout.PAGE_AXIS));

        myPanel.add(new JLabel("Simulation Interval:"));
        myPanel.add(simulationIntervalField);
        myPanel.add(Box.createVerticalStrut(15)); // a spacer
        myPanel.add(new JLabel("Number of queues:"));
        myPanel.add(numberOfQueuesField);
        myPanel.add(Box.createVerticalStrut(15)); // a spacer
        myPanel.add(new JLabel("Service time:"));
        myPanel.add(serviceTimeField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter simulation parameters", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            try {
                int simInterval = Integer.parseInt(simulationIntervalField.getText());
                int serviceTime = Integer.parseInt(serviceTimeField.getText());
                int numberOfQueues = Integer.parseInt(numberOfQueuesField.getText());

                if(simInterval<=0 ||serviceTime<=0 ||numberOfQueues<=0 ){
                    throw new NumberFormatException();
                }

                int averageWaitingTime = controller.startWhatIfAnalysys(simInterval, serviceTime, numberOfQueues);

                JOptionPane.showMessageDialog(MainFrame.this,"The average waiting time is: " + averageWaitingTime +" s","Average waiting time",JOptionPane.INFORMATION_MESSAGE);


            }catch(NumberFormatException exception){
                JOptionPane.showMessageDialog(MainFrame.this,"Not a valid input. Please re enter simulation time: ","Invalid input",JOptionPane.ERROR_MESSAGE);

            }

        }

    }

    /**
     * the most important method which builds and ties every gui element in the application to each other.
     */
    private void initializeComponents() {

        queuePanelList = new ArrayList<QueuePanel>();
        FlowLayout flowLayout =new FlowLayout(FlowLayout.CENTER,10,10);
        mainPanel = new JPanel(flowLayout);
        infoPanel = new InfoPanel();

        infoPanel.setObserver(this);
        infoPanel.updateCustomerSpeedLabel(controller.getAdditionInterval());
        infoPanel.updateServiceSpeedLabel(controller.getServiceSpeed());


        mainPanel.setSize(800,800);

        int numberOfQueues = controller.getQueueList().size();
        for(int i=0;i<numberOfQueues;i++){
            QueuePanel queuePanel = new QueuePanel();
            queuePanel.setPreferredSize(new Dimension(mainPanel.getWidth() / (numberOfQueues + 1), mainPanel.getHeight()));
            queuePanelList.add(queuePanel);
            mainPanel.add(queuePanel);
        }

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(infoPanel,BorderLayout.NORTH);

        setSize(800, 800);
        setTitle("Queue Simulator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        setLocation(-1200,100);
        setVisible(true);

    }

    @Override
    public boolean isSimulationStarted(){
        return controller.isSimulationStarted();
    }

    @Override
    public int getSimulationTotalTime() {
        return totalSimulationTime;
    }

    @Override
    public void endSimulation() {
        controller.endSimulation();
        infoPanel.endSimulation();
    }

    @Override
    public void queueChanged(QueueChangedEvent event) {

        int queueIndex = event.getQueueIndex();
        int modification = event.getModification();
        int totalTimeWaitingForQueue = event.getTotalWaitingTimeForQueue();
        int timeCustomerWaited = event.getTimeCustomerWaited();

        queuePanelList.get(queueIndex).modifyNumberOfCustomers(modification);
        queuePanelList.get(queueIndex).setTotalTime(""+totalTimeWaitingForQueue);

        infoPanel.modifyNumberOfCustomers(modification);

        if(timeCustomerWaited!=0){
            infoPanel.addToAverageTimeForWaiting(timeCustomerWaited);
        }

    }

    @Override
    public void numberOfQueuesChanged(int difference) {

        if(difference == 1){
            controller.increaseNumberOfQueues();
            QueuePanel newQueuePanel = new QueuePanel();
            queuePanelList.add(newQueuePanel);
            mainPanel.add(newQueuePanel);
        }else{
            controller.decreaseNumberOfQueues();
            mainPanel.remove(queuePanelList.get(0));
            queuePanelList.remove(0);

        }

        int numberOfQueues = controller.getQueueList().size();
        for(QueuePanel queuePanel: queuePanelList){
            queuePanel.setPreferredSize(new Dimension(mainPanel.getWidth() / (numberOfQueues + 1), mainPanel.getHeight()));
            queuePanel.adjustCustomerShapesPosition();
        }
        repaint();

    }

    @Override
    public void changeAdditionSpeed(int difference) {
        if(controller.getAdditionInterval()== 100 && difference<0){
            return;
        }
        controller.setAdditionInterval(controller.getAdditionInterval()+difference);
        infoPanel.updateCustomerSpeedLabel(controller.getAdditionInterval());

    }

    @Override
    public void changeServiceSpeed(int difference) {
        if(controller.getServiceSpeed()== 500 && difference<0){
            return;
        }
        controller.setServiceSpeed(controller.getServiceSpeed() + difference);
        infoPanel.updateServiceSpeedLabel(controller.getServiceSpeed());
    }

}
