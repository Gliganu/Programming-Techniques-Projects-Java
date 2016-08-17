package view;

import model.Customer;
import model.Order;
import model.model.products.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static model.model.products.Product.ProductEnum;

/**
 * Created by gliga on 3/30/2015.
 */
public class PlaceOrderPanel extends JPanel {

    private JTextField nameTextField;
    private JTextField addressTextField;
    private JComboBox<ProductEnum> productComboBox;
    private JButton nextButton;

    private JPanel mainPanel;
    private JPanel statusPanel;

    private JLabel statusLabel;

    private Observer observer;

    public PlaceOrderPanel(Observer observer) {

        this.observer = observer;
        buildGui();
    }

    private void buildGui() {

        nameTextField = new JTextField(20);
        addressTextField = new JTextField(20);
        statusLabel = new JLabel();
        mainPanel = new JPanel();
        statusPanel = new JPanel();
        productComboBox = new JComboBox();
        productComboBox.addItem(ProductEnum.Closet);
        productComboBox.addItem(ProductEnum.Computer);
        productComboBox.addItem(ProductEnum.Monitor);
        productComboBox.addItem(ProductEnum.Desk);
        nextButton = new JButton("Next");


        setLayout(new BorderLayout());

        add(mainPanel,BorderLayout.CENTER);
        add(statusPanel,BorderLayout.NORTH);
        //

        statusPanel.setLayout(new BoxLayout(statusPanel,BoxLayout.Y_AXIS));

        statusPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        statusPanel.add(statusLabel);

        statusLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);

        ///
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        //First Row
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        mainPanel.add(new JLabel("Enter Name: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        mainPanel. add(nameTextField, gc);

        //Next Row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        mainPanel.add(new JLabel("Enter your address"), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(addressTextField, gc);

        //Next Row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        mainPanel.add(new JLabel("Choose operation: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        mainPanel. add(productComboBox, gc);

        //Next Row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        mainPanel.add(nextButton, gc);


        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (nameTextField.getText().length() == 0 || addressTextField.getText().length() == 0) {
                    JOptionPane.showMessageDialog(PlaceOrderPanel.this, "Please insert data in the required fields", "Invalid input", JOptionPane.ERROR_MESSAGE);
                } else {
                    showDetailsPanel();
                }

            }
        });

    }

    private void showDetailsPanel() {

        ProductEnum productEnum = (ProductEnum) productComboBox.getSelectedItem();

        Product finalProduct = null;
        switch(productEnum){
            case Closet:
                finalProduct = new Closet();
                showClosetDetails((Closet) finalProduct);
                break;
            case Computer:
                finalProduct = new Computer();
                showComputerDetails((Computer)finalProduct);
                break;
            case Desk:
                finalProduct = new Desk();
                showDeskDetails((Desk) finalProduct);
                break;
            case Monitor:
                finalProduct = new Monitor();
                showMonitorDetails((Monitor) finalProduct);
                break;
        }


        Random random = new Random();
        finalProduct.setPrice(random.nextInt(100));


        Customer customer = new Customer(nameTextField.getText(),addressTextField.getText());

        Order order = new Order(customer,finalProduct);

        observer.sendOrder(order);

        statusLabel.setForeground(new Color(0,153,51));
        statusLabel.setText(productComboBox.getSelectedItem()+" ordered! ");

    }

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    private void showMonitorDetails(Monitor monitor) {

        JPanel detailPanel = new JPanel();


        JTextField refreshRateTextField = new JTextField(5);
        JTextField diagonalSizeTextField = new JTextField(5);

        detailPanel.setLayout(new GridLayout(2, 2, 10, 5));

        JLabel refreshRateLabel = new JLabel("Refresh rate:");
        detailPanel.add(refreshRateLabel);
        detailPanel.add(refreshRateTextField);

        JLabel diagonalSizeLabel = new JLabel("Diagonal Size:");
        detailPanel.add(diagonalSizeLabel);
        detailPanel.add(diagonalSizeTextField);


        int result = JOptionPane.showConfirmDialog(this, detailPanel,
                "Please enter order details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            try {
                int refreshRate = Integer.parseInt(refreshRateTextField.getText());
                int diagonalSize = Integer.parseInt(diagonalSizeTextField.getText());

                monitor.setRefreshRate(refreshRate);
                monitor.setDiagonalSize(diagonalSize);

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(PlaceOrderPanel.this, "Not a valid input. Please insert an integer", "Invalid input", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void showComputerDetails(Computer computer) {

        JPanel detailPanel = new JPanel();

        JTextField memoryCapacityTextField = new JTextField(5);
        JTextField processorSpeedTextField = new JTextField(5);
        JCheckBox desktopCheckBox = new JCheckBox();

        detailPanel.setLayout(new GridLayout(3, 2, 10, 5));

        JLabel desktopLabel = new JLabel("Is desktop:");

        detailPanel.add(desktopLabel);
        detailPanel.add(desktopCheckBox);

        JLabel memoryCapacityLabel = new JLabel("Memory Capacity:");
        detailPanel.add(memoryCapacityLabel);
        detailPanel.add(memoryCapacityTextField);

        JLabel processorSpeedLabel = new JLabel("Processor Speed:");
        detailPanel.add(processorSpeedLabel);
        detailPanel.add(processorSpeedTextField);


        int result = JOptionPane.showConfirmDialog(this, detailPanel,
                "Please enter order details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            try {
                int memoryCapacity = Integer.parseInt(memoryCapacityTextField.getText());
                int processorSpeed = Integer.parseInt(processorSpeedTextField.getText());
                boolean desktop = desktopCheckBox.isSelected();

                computer.setMemoryCapacity(memoryCapacity);
                computer.setProcessorSpeed(processorSpeed);
                computer.setDesktop(desktop);

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(PlaceOrderPanel.this, "Not a valid input. Please insert an integer", "Invalid input", JOptionPane.ERROR_MESSAGE);
            }

        }
    }


    private void showClosetDetails(Closet closet) {

        JPanel detailPanel = new JPanel();

        JTextField numberOfDoorsTextField = new JTextField(5);
        JTextField witdhTextField = new JTextField(5);
        JTextField heightTextField = new JTextField(5);
        detailPanel.setLayout(new GridLayout(3, 2, 10, 5));

        JLabel numberOfDoorsLabel = new JLabel("Number of doors:");
        detailPanel.add(numberOfDoorsLabel);
        detailPanel.add(numberOfDoorsTextField);

        JLabel widthLabel = new JLabel("Width:");
        detailPanel.add(widthLabel);
        detailPanel.add(witdhTextField);

        JLabel heightLabel = new JLabel("Height:");
        detailPanel.add(heightLabel);
        detailPanel.add(heightTextField);


        int result = JOptionPane.showConfirmDialog(this, detailPanel,
                "Please enter order details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            try {
                int width = Integer.parseInt(witdhTextField.getText());
                int height = Integer.parseInt(heightTextField.getText());
                int numberOfDoords = Integer.parseInt(numberOfDoorsTextField.getText());

                closet.setNumberOfDoors(numberOfDoords);
                closet.setWidth(width);
                closet.setHeight(height);

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(PlaceOrderPanel.this, "Not a valid input. Please insert an integer", "Invalid input", JOptionPane.ERROR_MESSAGE);

            }

        }

    }

    private void showDeskDetails(Desk desk) {

        JPanel detailPanel = new JPanel();

        JTextField drawerTextField = new JTextField(5);


        detailPanel.setLayout(new GridLayout(1, 2, 10, 5));

        JLabel colorLabel = new JLabel("Choose color:");
        colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);//0.0

        detailPanel.add(colorLabel);

        JLabel drawerLabel = new JLabel("Number of drawers:");
        drawerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);//0.0
        detailPanel.add(drawerLabel);
        detailPanel.add(drawerTextField);

        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);//0.0


        int result = JOptionPane.showConfirmDialog(this, detailPanel,
                "Please enter order details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            try {
                int numberOfDrawers = Integer.parseInt(drawerTextField.getText());
                desk.setNumberOfDrawers(numberOfDrawers);

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(PlaceOrderPanel.this, "Not a valid input. Please insert an integer", "Invalid input", JOptionPane.ERROR_MESSAGE);

            }

        }

    }


}