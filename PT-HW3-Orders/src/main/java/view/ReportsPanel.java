package view;

import model.Order;
import model.model.products.Product;
import view.tables.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.model.products.Product.ProductEnum;

/**
 * Created by gliga on 4/1/2015.
 */
public class ReportsPanel  extends JPanel{

    private JTable table;
    private AbstractOrderTableModel tableModel;
    private JPopupMenu  popUpMenu;
    private TableListener tableListener;

    private JLabel reportsLabel;

    private Observer observer;
    private JScrollPane scrollPane;

    public ReportsPanel(Observer observer) {

        this.observer = observer;
        configureGui();


    }

    private void configureGui() {


        JPanel comboBoxPanel = new JPanel();


        setLayout(new BorderLayout());

        reportsLabel = new JLabel("Warehouse Statistics");

        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));

        comboBoxPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        comboBoxPanel.add(reportsLabel);
        comboBoxPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        reportsLabel.setAlignmentX(CENTER_ALIGNMENT);
        reportsLabel.setFont(new Font("Verdana", Font.BOLD, 16));

        add(comboBoxPanel, BorderLayout.NORTH);

        AbstractOrderTableModel  model = new ProductStatisticDataModel();
        model.setData(observer.getData(ProductEnum.Statistic));

        configureTable(model);

    }



    private void configureTable(AbstractOrderTableModel abstractTableModel){

        tableModel = abstractTableModel;
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);


        popUpMenu = new JPopupMenu();


        table.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < 4; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JMenuItem restockItem = new JMenuItem("Restock");
        popUpMenu.add(restockItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {

                int row = table.rowAtPoint(event.getPoint());

                table.getSelectionModel().setSelectionInterval(row,row);

                if(event.getButton()== MouseEvent.BUTTON3){
                    popUpMenu.show(table, event.getX(),event.getY());
                }
            }
        });

        restockItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();

                ProductEnum selectedEnum = (ProductEnum) table.getValueAt(row, 0);
                observer.restock(selectedEnum);
                refresh();

            }
        });
    }



    public void setData(java.util.List<Order> orderList){
        tableModel.setData(orderList);
    }

    public void refresh() {
        tableModel.setData(observer.getData(ProductEnum.Statistic));
        tableModel.fireTableDataChanged();

    }

    public void tableListener(TableListener tableListener) {
        this.tableListener = tableListener;

    }

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }
}