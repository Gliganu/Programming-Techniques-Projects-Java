package view.tables;

import model.Customer;
import model.Order;
import model.model.products.Computer;
import model.model.products.Desk;

/**
 * Created by gliga on 4/1/2015.
 */
public class DeskDataModel extends AbstractOrderTableModel {
    private String[] columnNames={"Customer Name","Customer Address","Price","Number of Drawers"};

    /**
     * Table model used for desk product
     */
    public DeskDataModel() {

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
        return 4;
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

            default:
                return null;
        }

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order =  orderList.get(rowIndex);
        Desk desk = (Desk) order.getProduct();
        Customer customer =  order.getCustomer();
        switch (columnIndex){
            case 0:
                return customer.getName();
            case 1:
                return customer.getAddress();
            case 2:
                return desk.getPrice();
            case 3:
                return desk.getNumberOfDrawers();
        }
        return null;
    }

}
