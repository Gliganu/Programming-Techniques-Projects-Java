package model;

/**
 * Created by gliga on 3/14/2015.
 */
public class Customer {

    private long arrivalTime;
    private long serviceTime;

    public Customer(long serviceTime) {
        this.serviceTime = serviceTime;
        this.arrivalTime = System.currentTimeMillis();
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (arrivalTime != customer.arrivalTime) return false;
        if (serviceTime != customer.serviceTime) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (arrivalTime ^ (arrivalTime >>> 32));
        result = 31 * result + (int) (serviceTime ^ (serviceTime >>> 32));
        return result;
    }
}
