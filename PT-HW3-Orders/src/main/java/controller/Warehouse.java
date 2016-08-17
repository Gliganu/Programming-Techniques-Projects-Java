package controller;

import model.Customer;
import model.Order;
import model.ProductStatistic;
import model.model.products.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

import static model.model.products.Product.ProductEnum;

/**
 * Created by gliga on 3/29/2015.
 */
public class Warehouse {

    private OrderNode headNode;
    private Map<ProductEnum, Integer> productStock;

    /**
     * Constructor
     */
    public Warehouse() {

        productStock = new HashMap<ProductEnum, Integer>();
        initializeStock();
        initializeOrders();
    }

    /**
     * Initialize the orders
     */
    private void initializeOrders() {

        Random random = new Random();

        for (ProductEnum productEnum : productStock.keySet()) {
            switch (productEnum) {

                case Closet:

                    for (int i = 0; i < 100; i++) {
                        Closet closet = new Closet(random.nextInt(500), random.nextInt(500), random.nextInt(500), random.nextInt(3) + 1);
                        Customer customer = new Customer(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
                        insertOrder(new Order(customer, closet));
                    }
                    break;

                case Computer:

                    for (int i = 0; i < 10; i++) {
                        Computer computer = new Computer(random.nextInt(500), random.nextInt(500), random.nextInt(500), random.nextBoolean());
                        Customer customer = new Customer(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
                        insertOrder(new Order(customer, computer));
                    }
                    break;


                case Desk:

                    for (int i = 0; i < 10; i++) {
                        Desk desk = new Desk(random.nextInt(500), random.nextInt(10));
                        Customer customer = new Customer(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
                        insertOrder(new Order(customer, desk));
                    }
                    break;


                case Monitor:

                    for (int i = 0; i < 10; i++) {
                        Monitor monitor = new Monitor(random.nextInt(500), random.nextInt(500), random.nextInt(10));
                        Customer customer = new Customer(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
                        insertOrder(new Order(customer, monitor));
                    }
                    break;
            }
        }


    }

    /**
     * Initialize the stocks
     */
    private void initializeStock() {

        Random random = new Random();
        productStock.put(ProductEnum.Closet, random.nextInt(100));
        productStock.put(ProductEnum.Computer, random.nextInt(100));
        productStock.put(ProductEnum.Desk, random.nextInt(100));
        productStock.put(ProductEnum.Monitor, random.nextInt(100));

    }

    /**
     * Insert an order
     * @param order
     */
    public void insertOrder(Order order) {
        OrderNode orderToBeInserted = new OrderNode(order);

        if (headNode == null) {
            headNode = orderToBeInserted;
            return;
        }

        boolean inserted = false;
        OrderNode currentOrderNode = headNode;

        while (!inserted) {
            if (orderToBeInserted.getOrder().hashCode() < currentOrderNode.getOrder().hashCode()) {

                if (currentOrderNode.getLeftOrderNode() == null) {
                    currentOrderNode.setLeftOrderNode(orderToBeInserted);
                    inserted = true;
                } else {
                    currentOrderNode = currentOrderNode.getLeftOrderNode();
                }

            } else {

                if (currentOrderNode.getRightOrderNode() == null) {
                    currentOrderNode.setRightOrderNode(orderToBeInserted);
                    inserted = true;
                } else {
                    currentOrderNode = currentOrderNode.getRightOrderNode();
                }

            }

        }

    }

    /**
     * Get orders by type
     * @param productEnum
     * @return
     */
    public List<Order> getOrders(ProductEnum productEnum) {

        if (productEnum == ProductEnum.Statistic) {
            return this.getProductStatistics();
        }

        return getOrdersRecursively(headNode, new ArrayList<Order>(), productEnum);
    }

    /**
     * Get orders recurvively
     * @param node
     * @param listSoFar
     * @param productEnum
     * @return
     */
    private List<Order> getOrdersRecursively(OrderNode node, List<Order> listSoFar, ProductEnum productEnum) {

        if (node == null) {
            return listSoFar;
        }

        if (node.getOrder().getProduct().getEnum() == productEnum) {
            listSoFar.add(node.getOrder());
        }

        if (node.getLeftOrderNode() != null) {
            listSoFar = getOrdersRecursively(node.getLeftOrderNode(), listSoFar, productEnum);

        }

        if (node.getRightOrderNode() != null) {
            listSoFar = getOrdersRecursively(node.getRightOrderNode(), listSoFar, productEnum);
        }


        return listSoFar;


    }

    /**
     * Get all orders
     * @return
     */
    public List<Order> getAllOrders() {

        return getAllOrdersRecursively(headNode, new ArrayList<Order>());
    }

    /**
     * Gets all the orders from the BST recursively
     * @param node
     * @param listSoFar
     * @return
     */
    public List<Order> getAllOrdersRecursively(OrderNode node, List<Order> listSoFar) {

        if (node == null) {
            return listSoFar;
        }

        listSoFar.add(node.getOrder());

        if (node.getLeftOrderNode() != null) {
            listSoFar = getAllOrdersRecursively(node.getLeftOrderNode(), listSoFar);

        }

        if (node.getRightOrderNode() != null) {
            listSoFar = getAllOrdersRecursively(node.getRightOrderNode(), listSoFar);
        }


        return listSoFar;


    }

    public void printTree() {
        printTreeRecursively(headNode, 0, "F");
    }

    private void printTreeRecursively(OrderNode node, int tabs, String type) {

        if (node == null) {
            return;
        }


        for (int i = 0; i < tabs; i++) {
            System.out.print(" -- ");
        }

        System.out.println(type + ":" + node.getOrder().hashCode());


        printTreeRecursively(node.getRightOrderNode(), tabs + 1, "R");
        printTreeRecursively(node.getLeftOrderNode(), tabs + 1, "L");


    }

    /**
     * Deletes an order
     * @param deletedOrder
     */
    public void deleteOrder(Order deletedOrder) {
        deleteOrderRecursively(headNode, deletedOrder);

    }

    /**
     * Deletes an order recursively
     * @param currentOrder
     * @param deletionOrder
     * @return
     */
    private OrderNode deleteOrderRecursively(OrderNode currentOrder, Order deletionOrder) {

        if (currentOrder == null)
            return null;

        int cmp = deletionOrder.compareTo(currentOrder.getOrder());

        if (cmp < 0) {
            currentOrder.setLeftOrderNode(deleteOrderRecursively(currentOrder.getLeftOrderNode(), deletionOrder));
        } else if (cmp > 0) {
            currentOrder.setRightOrderNode(deleteOrderRecursively(currentOrder.getRightOrderNode(), deletionOrder));
        } else {
            if (currentOrder.getRightOrderNode() == null)
                return currentOrder.getLeftOrderNode();
            if (currentOrder.getLeftOrderNode() == null)
                return currentOrder.getRightOrderNode();

            OrderNode t = currentOrder;
            currentOrder = getMin(t.getRightOrderNode());
            currentOrder.setRightOrderNode(deleteMin(t.getRightOrderNode()));
            currentOrder.setLeftOrderNode(t.getLeftOrderNode());

        }

        return currentOrder;
    }

    /**
     * Deletes the min
     * @param currentNode
     * @return
     */
    private OrderNode deleteMin(OrderNode currentNode) {
        if (currentNode.getLeftOrderNode() == null)
            return currentNode.getRightOrderNode();

        currentNode.setLeftOrderNode(deleteMin(currentNode.getLeftOrderNode()));

        return currentNode;
    }

    /**
     * REtrieves the min node
     * @param x
     * @return
     */
    private OrderNode getMin(OrderNode x) {
        if (x.getLeftOrderNode() == null)
            return x;
        else
            return getMin(x.getLeftOrderNode());
    }

    /**
     * Restocks the warehouse
     * @param productEnum
     */
    public void restock(ProductEnum productEnum) {

        Integer currentNumberOfItems = productStock.get(productEnum);

        if (currentNumberOfItems != 0) {
            productStock.put(productEnum, (int) (currentNumberOfItems * 1.5));
        }else{
            productStock.put(productEnum, 10);
        }

    }

    /**
     * REtrieves all the product statistics
     * @return
     */
    public List<Order> getProductStatistics() {

        List<Order> statisticList = new ArrayList<Order>();

        for (ProductEnum productEnum : productStock.keySet()) {
            ProductStatistic statistic1 = new ProductStatistic(productEnum, getOrders(productEnum).size(), productStock.get(productEnum));
            statisticList.add(new Order(null, statistic1));

        }

        return statisticList;
    }
}


class OrderNode {

    private Order order;
    private OrderNode leftOrderNode;
    private OrderNode rightOrderNode;


    /**
     * Constructor which takes an order which will be the value in the node
     * @param currentOrder
     */
    OrderNode(Order currentOrder) {
        this.order = currentOrder;
    }

    /**
     * check if the node is a leaft in the BST
     * @return
     */
    public boolean isLeaf() {
        return (leftOrderNode == null && rightOrderNode == null);
    }

    /**
     * Retrieve the order
     * @return
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Set the order
     * @param order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Retrieve the left node
     * @return
     */
    public OrderNode getLeftOrderNode() {
        return leftOrderNode;
    }

    /**
     * set the left node
     * @param leftOrderNode
     */
    public void setLeftOrderNode(OrderNode leftOrderNode) {
        this.leftOrderNode = leftOrderNode;
    }

    /**
     * Get the right order node
     * @return
     */
    public OrderNode getRightOrderNode() {
        return rightOrderNode;
    }

    /**
     * Set the right order node
     * @param rightOrderNode
     */
    public void setRightOrderNode(OrderNode rightOrderNode) {
        this.rightOrderNode = rightOrderNode;
    }

}
