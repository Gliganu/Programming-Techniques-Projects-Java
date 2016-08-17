package model;


/**
 * Created by gliga on 3/15/2015.
 */
public class QueueChangedEvent {

    private int queueIndex;
    private int modification;
    private int totalWaitingTimeForQueue;
    private int timeCustomerWaited;

    public QueueChangedEvent(int queueIndex, int modification, int totalWaitingTime, int timeCustomerWaited) {
        this.queueIndex = queueIndex;
        this.modification = modification;
        this.totalWaitingTimeForQueue = totalWaitingTime;
        this.timeCustomerWaited = timeCustomerWaited;
    }

    /**
     * gets the total waiting time for that queue
     *
     * @return
     */
    public int getTotalWaitingTimeForQueue() {
        return totalWaitingTimeForQueue;
    }

    /**
     * returns the modified queue index
     *
     * @return
     */
    public int getQueueIndex() {
        return queueIndex;
    }

    /**
     * gets the modification on that queue
     *
     * @return
     */
    public int getModification() {
        return modification;
    }

    /**
     * gets the time the customer waited
     *
     * @return
     */
    public int getTimeCustomerWaited() {
        return timeCustomerWaited;
    }
}