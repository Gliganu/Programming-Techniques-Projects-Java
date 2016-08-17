package view;

import model.Order;
import model.model.products.Product;
import view.tables.AbstractOrderTableModel;

import java.util.List;

import static model.model.products.Product.ProductEnum;

/**
 * Created by gliga on 3/31/2015.
 * Interface impplemented by the MainFrame in order to use the Observer pattern
 */
public interface Observer {

    public void sendOrder(Order order);

    public List<Order> getData(ProductEnum productEnum);

    public void restock(ProductEnum productEnum);

}
