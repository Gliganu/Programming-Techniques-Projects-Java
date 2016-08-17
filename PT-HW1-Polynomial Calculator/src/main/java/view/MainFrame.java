package view;

import controller.Controller;
import model.OperationType;
import model.Polynomial;
import model.PolynomialUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gliga on 3/2/2015.
 */
/**
 * Main GUI class which handles all user interaction
 */
public class MainFrame extends JFrame {

    public static final String INTRODUCE_POLYNOMIAL_MESSAGE = "Introduce polynomial: ";
    public static final String INTRODUCE_VALUE_MESSAGE = "Introduce value: ";
    private static final java.lang.Object ACCEPTABLE_FORMAT_MESSAGE = "The format for entering polynomials is: coef1 * x ^ degree1 + coef2 * x ^ degree2 + ...";

    private JLabel firstTermLabel;
    private JLabel secondTermLabel;
    private JTextField firstTerm;
    private JTextField secondTerm;
    private JComboBox<OperationType> operationComboBox;
    private JTextArea resultTextArea;
    private JButton calculateButton;

    private Controller controller;
    private JButton generateValuesButton;
    private JScrollPane scrollPane;

    private JPanel mainPanel;


    public MainFrame() {
        controller = new Controller();
    }

    /**
     * Creasting the entire GUI
     */
    public void buildGui() {

        createComponents();
        createMenu();
        addListeners();
        layoutComponents();
        setVisible(true);
    }


    /**
     * Initializing the GUI components and setting their values
     */
    private void createComponents() {

        mainPanel = new JPanel();
        firstTermLabel = new JLabel(INTRODUCE_POLYNOMIAL_MESSAGE);
        secondTermLabel = new JLabel(INTRODUCE_POLYNOMIAL_MESSAGE);
        firstTerm = new JTextField(30);
        secondTerm = new JTextField(30);
        operationComboBox = new JComboBox<OperationType>(OperationType.values());
        resultTextArea = new JTextArea(3,30);
        calculateButton = new JButton("Calculate");

        generateValuesButton = new JButton("Generate");

        setTitle("Polynomial Calculator");
        setSize(550, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        resultTextArea.setEditable(false);

        scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(mainPanel,BorderLayout.CENTER);

        generateRandomPolynomials();


    }


    /**
     * Creates the top bar menu
     */
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem instructionsMenuItem = new JMenuItem("See Instructions");
        JMenuItem exitItem = new JMenuItem("Exit");

        helpMenu.add(instructionsMenuItem);
        fileMenu.add(exitItem);


        instructionsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(MainFrame.this,
                        ACCEPTABLE_FORMAT_MESSAGE,
                        "Useful Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });


        menuBar.add(fileMenu);
        menuBar.add(helpMenu);


        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(-1);
            }
        });


        setJMenuBar(menuBar);

    }

    /**
     * Generates two random polynomials in the input sequence. Mainly for testing & debugging purposes
     */
    private void generateRandomPolynomials() {

        Polynomial p1 = PolynomialUtils.getRandomPolynomial(3);
        Polynomial p2 = PolynomialUtils.getRandomPolynomial(3);

        firstTerm.setText(p1.toString());
        secondTerm.setText(p2.toString());
    }

    /**
     * Adds action listeners on all the buttons and the combo box
     */
    private void addListeners() {

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Polynomial polynomial = controller.doOperation(PolynomialUtils.getPolynomialFromText(firstTerm.getText()),
                            PolynomialUtils.getPolynomialFromText(secondTerm.getText()), (OperationType) operationComboBox.getSelectedItem());
                    resultTextArea.setText(polynomial.toString());
                } catch (IllegalArgumentException exception) {

                    JOptionPane.showMessageDialog(MainFrame.this,
                            exception.getMessage(),
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);

                }


            }
        });

        generateValuesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomPolynomials();

            }
        });

        operationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch ((OperationType)operationComboBox.getSelectedItem()){
                    case INTEGRATE:
                        setOnePolynomialInputMode();
                        break;
                    case DIFFERENTIATE:
                        setOnePolynomialInputMode();
                        break;
                    case CALCULATE_VALUE:
                        setOnePolynomialInputModeWithValue();
                        break;
                    case MULTIPLY_WITH_SCALAR:
                        setOnePolynomialInputModeWithValue();
                        break;
                    default:
                        setNormalMode();

                }
                
                
            }
        });


    }

    /**
     * Set the application state when the user has the option of introducing two polynomials
     */
    private void setNormalMode() {
        secondTermLabel.setText(INTRODUCE_POLYNOMIAL_MESSAGE);
        secondTerm.setVisible(true);
        secondTermLabel.setVisible(true);
    }

    /**
     * Set the application state when the user has the option of introducing one polynomial and one scalar value
     */
    private void setOnePolynomialInputModeWithValue() {
        secondTermLabel.setText(INTRODUCE_VALUE_MESSAGE);
        secondTerm.setVisible(true);
        secondTermLabel.setVisible(true);
    }

    /**
     * Set the application state when the user has the option of introducing one polynomial
     */
    private void setOnePolynomialInputMode() {
        secondTerm.setVisible(false);
        secondTermLabel.setVisible(false);
    }


    /**
     * Set the GUI elements into the frame and position them nicely so that the user has a nice experience
     */
    private void layoutComponents(){

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
        mainPanel.add(firstTermLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(firstTerm, gc);

        //Next Row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        mainPanel. add(secondTermLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(secondTerm, gc);

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
        mainPanel.add(operationComboBox, gc);

        //Next Row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        mainPanel. add(new JLabel("Result: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(scrollPane, gc);

        //Next Row

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        mainPanel.add(calculateButton, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        mainPanel. add(generateValuesButton, gc);



    }
}
