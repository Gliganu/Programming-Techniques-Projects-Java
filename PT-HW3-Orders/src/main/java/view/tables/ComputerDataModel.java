package view.tables;

import model.Customer;
import model.Order;
import model.model.products.Computer;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by gliga on 3/31/2015.
 */

public class ComputerDataModel extends AbstractOrderTableModel {

    private String[] columnNames={"Customer Name","Customer Address","Price","Memory Capacity","Processor Speed","Is Desktop"};

    /**
     * Data model used for the computer product
     */
    public ComputerDataModel() {

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
                return Boolean.class;
            default:
                return null;
        }

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order =  orderList.get(rowIndex);
        Computer computer = (Computer) order.getProduct();
        Customer customer =  order.getCustomer();
        //  "Customer Name","Customer Address","Price","Memory Capacity","Processor Speed","Is Desktop"
        switch (columnIndex){
            case 0:
                return customer.getName();
            case 1:
                return customer.getAddress();
            case 2:
                return computer.getPrice();
            case 3:
                return computer.getMemoryCapacity();
            case 4:
                return computer.getProcessorSpeed();
            case 5:
                return computer.isDesktop();
        }
        return null;
    }

}