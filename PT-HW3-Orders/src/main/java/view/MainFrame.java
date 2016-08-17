package view;

import controller.OPDept;
import model.Order;
import view.tables.TableListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static model.model.products.Product.ProductEnum;

/**
 * Created by gliga on 3/29/2015.
 */
public class MainFrame extends JFrame implements Observer {

    private OPDept orderDepartment;
    private SeeAllOrdersPanel seeAllOrdersPanel;
    private ReportsPanel reportsPanel;

    boolean passwordIntroduced;

    /**
     * Constructor
     * @param orderDepartment
     */
    public MainFrame(OPDept orderDepartment) {
        super("Order Management System");

        this.orderDepartment = orderDepartment;

        buildGui();
        addMenu();

    }

    /**
     * Adds the menu to the frame
     */
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(exitItem);


        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(-1);
            }
        });


        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Constructs and binds the entire GUI
     */
    private void buildGui() {

        final JTabbedPane tabbedPane = new JTabbedPane();

        PlaceOrderPanel placeOrderPanel = new PlaceOrderPanel(this);
        seeAllOrdersPanel = new SeeAllOrdersPanel(this);
        reportsPanel = new ReportsPanel(this);

        seeAllOrdersPanel.setTableListener(new TableListener() {
            @Override
            public void deleteOrder(Order order) {

                orderDepartment.deleteOrder(order);
                seeAllOrdersPanel.refresh();
                reportsPanel.refresh();
            }

        });

        final JPanel adminPanel = new JPanel();

        final JPasswordField passwordField = new JPasswordField(10);

        adminPanel.setLayout(new GridLayout(1, 2, 10, 5));

        JLabel adminLabel = new JLabel("Enter admin password:");

        adminPanel.add(adminLabel);
        adminPanel.add(passwordField);


        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int selectedIndex = tabbedPane.getSelectedIndex();

                if (selectedIndex == 1 || selectedIndex == 2) {

                    if (!passwordIntroduced) {

                        tabbedPane.setSelectedIndex(0);

                        int result = JOptionPane.showConfirmDialog(MainFrame.this, adminPanel,
                                "Access restricted!", JOptionPane.OK_CANCEL_OPTION);

                        if (result == JOptionPane.OK_OPTION) {

                            if (!"admin".equals(String.valueOf(passwordField.getPassword()))) {
                                tabbedPane.setSelectedIndex(0);
                            }else{
                                passwordIntroduced = true;
                                tabbedPane.setSelectedIndex(selectedIndex);
                            }
                        }
                    }
                }
            }
        });

        tabbedPane.addTab("Place Order", placeOrderPanel);
        tabbedPane.addTab("See All Orders", seeAllOrdersPanel);
        tabbedPane.addTab("See reports", reportsPanel);


        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
        setSize(1000, 700);
//        setLocation(-1300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * Send order to the controller to add it to the warehouse
     * @param order
     */
    @Override
    public void sendOrder(Order order) {
        orderDepartment.insertOrder(order);
        seeAllOrdersPanel.refresh();

    }

    /**
     * Gets data regarding a specific product in order to populate the tables
     * @param productEnum
     * @return
     */
    @Override
    public List<Order> getData(ProductEnum productEnum) {

        return orderDepartment.getOrders(productEnum);

    }

    /**
     * Restocks
     * @param productEnum
     */
    @Override
    public void restock(ProductEnum productEnum) {
        orderDepartment.restock(productEnum);
    }
}
