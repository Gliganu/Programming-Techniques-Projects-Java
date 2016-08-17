package view.tables;

import model.Customer;
import model.Order;
import model.model.products.Closet;
import model.model.products.Computer;

import java.awt.*;

/**
 * Created by gliga on 4/1/2015.
 */
public class ClosetDataModel extends AbstractOrderTableModel {


    private String[] columnNames={"Customer Name","Customer Address","Price","Width","Height","Number of doors"};

    /**
     * DAta model used for the Closet product
     */
    public ClosetDataModel() {

    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return orderList.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            case 4:
                return Integer.class;
            case 5:
                return Integer.class;
            default:
                return null;
        }

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order =  orderList.get(rowIndex);
        Closet closet = (Closet) order.getProduct();
        Customer customer =  order.getCustomer();
        switch (columnIndex){
            case 0:
                return customer.getName();
            case 1:
                return customer.getAddress();
            case 2:
                return closet.getPrice();
            case 3:
                return closet.getWidth();
            case 4:
                return closet.getHeight();
            case 5:
                return closet.getNumberOfDoors();
        }
        return null;
    }

}
