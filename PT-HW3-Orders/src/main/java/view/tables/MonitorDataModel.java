package view.tables;

import model.Customer;
import model.Order;
import model.model.products.Computer;
import model.model.products.Monitor;

/**
 * Created by gliga on 4/1/2015.
 */
public class MonitorDataModel extends AbstractOrderTableModel {

    private String[] columnNames={"Customer Name","Customer Address","Price","Refresh rate","Diagonal size"};

    /**
     * Data model used for monitor product
     */
    public MonitorDataModel() {

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
        return 5;
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

            default:
                return null;
        }

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order =  orderList.get(rowIndex);
        Monitor monitor = (Monitor) order.getProduct();
        Customer customer =  order.getCustomer();
        //  "Customer Name","Customer Address","Price","Memory Capacity","Processor Speed","Is Desktop"
        switch (columnIndex){
            case 0:
                return customer.getName();
            case 1:
                return customer.getAddress();
            case 2:
                return monitor.getPrice();
            case 3:
                return monitor.getRefreshRate();
            case 4:
                return monitor.getDiagonalSize();
        }
        return null;
    }


}
