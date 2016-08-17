package view.tables;

import model.Order;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Abstract table model which will serve as a template for all the other table models for all the tables in the app
 * Created by gliga on 4/1/2015.
 */
public class AbstractOrderTableModel extends AbstractTableModel {

    //list of items which will populate the tagble
    protected List<Order> orderList;

    public void setData(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
