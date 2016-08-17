package controller;

import model.Customer;
import model.Order;
import model.ProductStatistic;
import model.model.products.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.model.products.Product.ProductEnum;

/**
 * Created by gliga on 3/29/2015.
 */
public class OPDept {

    private Warehouse warehouse;

    /**
     * Constructor
     */
    public OPDept() {
        warehouse = new Warehouse();
    }


    /**
     * Retrieveing all orders from the warehouse
     * @return
     */
    public List<Order> getAllOrders() {
        return warehouse.getAllOrders();
    }

    /**
     * Inserting an order
     * @param order
     */
    public void insertOrder(Order order) {
        warehouse.insertOrder(order);
    }

    /**
     * Deleting an order
     * @param order
     */
    public void deleteOrder(Order order) {
        warehouse.deleteOrder(order);
    }

    /**
     * Getting a list of orders by product tppe
     * @param productEnum
     * @return
     */
    public List<Order> getOrders(ProductEnum productEnum) {

        return warehouse.getOrders(productEnum);

    }

    /**
     * Restocking the warehouse
     * @param productEnum
     */
    public void restock(ProductEnum productEnum) {
        warehouse.restock(productEnum);

    }
}
