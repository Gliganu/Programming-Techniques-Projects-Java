package view;

import model.Order;
import model.model.products.*;
import view.tables.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static model.model.products.Product.ProductEnum;
import static model.model.products.Product.ProductType;

/**
 * Created by gliga on 3/31/2015.
 */
public class SeeAllOrdersPanel extends JPanel {

    private JTable table;
    private AbstractOrderTableModel tableModel;
    private JPopupMenu popUpMenu;
    private TableListener tableListener;


    private JComboBox<ProductEnum> productComboBox;

    private Observer observer;
    private JScrollPane scrollPane;

    public SeeAllOrdersPanel(Observer observer) {

        this.observer = observer;
        configureGui();
    }

    private void configureGui() {


        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Check all customer orders");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));

        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        setLayout(new BorderLayout());

        productComboBox = new JComboBox<ProductEnum>();
        productComboBox.addItem(ProductEnum.Computer);
        productComboBox.addItem(ProductEnum.Closet);
        productComboBox.addItem(ProductEnum.Monitor);
        productComboBox.addItem(ProductEnum.Desk);


        productComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setTableModel((ProductEnum) productComboBox.getSelectedItem());

            }

        });

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        topPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        topPanel.add(titleLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        topPanel.add(productComboBox);
        topPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        add(topPanel, BorderLayout.NORTH);

        AbstractOrderTableModel model = new ComputerDataModel();
        model.setData(observer.getData(ProductEnum.Computer));

        configureTable(model);

    }

    private void setTableModel(ProductEnum productEnum) {

        AbstractOrderTableModel newModel = null;

        switch (productEnum) {

            case Closet:
                newModel = new ClosetDataModel();
                break;
            case Computer:
                newModel = new ComputerDataModel();
                break;
            case Desk:
                newModel = new DeskDataModel();
                break;
            case Monitor:
                newModel = new MonitorDataModel();
                break;
        }

        newModel.setData(observer.getData((ProductEnum) productComboBox.getSelectedItem()));

        table.setModel(newModel);
    }


    private void configureTable(AbstractOrderTableModel abstractTableModel) {

        tableModel = abstractTableModel;
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);


        popUpMenu = new JPopupMenu();


        table.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < 6; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JMenuItem fulfillOrder = new JMenuItem("Fulfill order");
        popUpMenu.add(fulfillOrder);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {

                int row = table.rowAtPoint(event.getPoint());

                table.getSelectionModel().setSelectionInterval(row, row);

                if (event.getButton() == MouseEvent.BUTTON3) {
                    popUpMenu.show(table, event.getX(), event.getY());
                }
            }
        });

        fulfillOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();

                List<Order> orderList = tableModel.getOrderList();

                Order currentOrder = orderList.get(row);

                if (tableListener != null) {

                    tableListener.deleteOrder(currentOrder);
                    refresh();

                }
            }
        });
    }


    public void setData(List<Order> orderList) {
        tableModel.setData(orderList);
    }

    public void refresh() {

        productComboBox.setSelectedItem(productComboBox.getSelectedItem());
        tableModel.fireTableDataChanged();

    }

    public void setTableListener(TableListener tableListener) {
        this.tableListener = tableListener;

    }

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }
}