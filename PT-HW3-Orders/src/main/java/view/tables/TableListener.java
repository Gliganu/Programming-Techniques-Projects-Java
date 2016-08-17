package view.tables;

import model.Order;

/**
 * Created by gliga on 3/31/2015.
 * table listener which will intercept all the events coming from a specific table
 */
public interface TableListener {

    public void deleteOrder(Order order);

}
