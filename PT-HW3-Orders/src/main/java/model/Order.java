package model;

import model.model.products.Product;

import java.util.Date;
import java.util.Random;

/**
 * Created by gliga on 3/29/2015.
 */
public class Order implements Comparable<Order> {

    private Customer customer;
    private Product product;
    private long creationDate;


    /**
     * Constructor with parameters
     * @param customer
     * @param product
     */
    public Order(Customer customer, Product product) {
        this.customer = customer;
        this.product = product;
        this.creationDate = System.currentTimeMillis();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (creationDate != order.creationDate) return false;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) return false;
        if (product != null ? !product.equals(order.product) : order.product != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (int) (creationDate ^ (creationDate >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", product=" + product +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public int compareTo(Order other) {

            if(this.hashCode() == other.hashCode()){
                return 0;
            } else if(this.hashCode() < other.hashCode()) {
                return -1;
            }

            return 1;
        }
}
