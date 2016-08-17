package model;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by gliga on 3/14/2015.
 */
public class CustomerArrayBlockingQueue extends ArrayBlockingQueue<Customer>{

    public CustomerArrayBlockingQueue(int capacity) {
        super(capacity);
    }

    /**
     * : calculates the entire waiting time for all the customers in the queue
     * @return
     */
    public long getTotalWaitingTime(){

        long totalWaitingTime=0;

        for(Customer customer: this){
            totalWaitingTime+=customer.getServiceTime();
        }

        return totalWaitingTime;
    }

}
