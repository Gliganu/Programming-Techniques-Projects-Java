package controller;

import model.Customer;
import model.CustomerArrayBlockingQueue;
import model.Observer;
import model.QueueChangedEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by gliga on 3/14/2015.
 */
public class QueueController {

    public static final int QUEUE_CAPACITY = 20;
    private int additionInterval = 500;
    private int serviceSpeed = 6000;

    private List<CustomerArrayBlockingQueue> queueList;
    private List<Observer> observerList;

    private boolean simulationStarted = false;

    private boolean comingFromAnotherLine = false;

    private PrintWriter writer;
    private SimpleDateFormat dateFormat;

    public QueueController(int numberOfQueues) {

        observerList = new ArrayList<Observer>();
        queueList = new ArrayList<CustomerArrayBlockingQueue>();

        try {
            writer = new PrintWriter(new File("log.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        for (int i = 0; i < numberOfQueues; i++) {
            queueList.add(new CustomerArrayBlockingQueue(QUEUE_CAPACITY));
        }


    }


    /**
     * starts the “provider thread” which as regular intervals will create a new customer, detect which queue has the smallest waiting time and put the customer in that queue
     */
    public void startAddingCustomers() {

        Random random = new Random();


        while (simulationStarted) {
            try {
                if (!comingFromAnotherLine) {
                    Thread.sleep(additionInterval);
                }

                Customer customer = new Customer(random.nextInt(serviceSpeed-400)+1000);

                //System.out.println("Created customer with service time " + customer.getServiceTime());
                writer.println(getCurrentDateAndTime() + "Created customer ( "+ customer.hashCode()+ " ) with service time " + customer.getServiceTime());
                writer.flush();
                CustomerArrayBlockingQueue minimalTimeQueue = null;

                long minimalWaitingTime = Long.MAX_VALUE;
                int index = 0;

                for (CustomerArrayBlockingQueue customerQueue : queueList) {

                    if (customerQueue.getTotalWaitingTime() < minimalWaitingTime) {
                        minimalTimeQueue = customerQueue;
                        minimalWaitingTime = customerQueue.getTotalWaitingTime();
                        index = queueList.indexOf(customerQueue);
                    }

                }

                if (minimalTimeQueue.size() != QUEUE_CAPACITY) {
                    minimalTimeQueue.add(customer);
                    int totalWaitingTimeForQueue = (int) (minimalWaitingTime / 1000);
                    updateObservers(queueList.indexOf(minimalTimeQueue), 1, totalWaitingTimeForQueue, 0);
                    System.out.println("Added customer ( "+ customer.hashCode()+ " )  to queue " + index);
                    writer.println(getCurrentDateAndTime() + "Added customer  ( "+ customer.hashCode()+ " )  to queue " + index);
                    writer.flush();

                    comingFromAnotherLine = false;
                } else {
                    System.out.println("Maximum size reached for queue " + index);
                    writer.println(getCurrentDateAndTime() + "Maximum size reached for queue " + index);
                    writer.flush();

                    updateObserversAutoAddQueue();

                    comingFromAnotherLine = true;
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * : for each queue, starts a “cosumer thread” which at regular intervals will remove the top of the queue, as if the customer has been served by the cashier
     * @param customerQueue
     */
    public void startProcessingCustomers(CustomerArrayBlockingQueue customerQueue) {

        long serviceTime;

        while (simulationStarted && queueList.contains(customerQueue)) {

            if (!customerQueue.isEmpty()) {

                Customer customer = customerQueue.peek();

                serviceTime = customer.getServiceTime();
                try {
                    Thread.sleep(serviceTime);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int timeCustomerWaited = (int) ((System.currentTimeMillis() - customer.getArrivalTime()) / 1000);

                customerQueue.poll();

                int totalWaitingTimeForQueue = (int) (customerQueue.getTotalWaitingTime() / 1000);

                if (queueList.contains(customerQueue)) {
                    updateObservers(queueList.indexOf(customerQueue), -1, totalWaitingTimeForQueue, timeCustomerWaited);

                    System.out.println("Removed customer ( "+ customer.hashCode()+ " )  from queue: " + queueList.indexOf(customerQueue));
                    writer.println(getCurrentDateAndTime() + "Removed customer  ( "+ customer.hashCode()+ " ) from queue " + queueList.indexOf(customerQueue));
                    writer.flush();

                }
            }


        }
    }

    /**
     * start the simulation
     */
    public void startSimulation() {

        Thread addingThread = new Thread(new Runnable() {
            @Override
            public void run() {

                startAddingCustomers();

            }
        });

        addingThread.start();

        for (final CustomerArrayBlockingQueue customerQueue : queueList) {

            Thread processingThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    startProcessingCustomers(customerQueue);

                }
            });

            processingThread.start();
        }

    }

    /**
     * increases the number of queues
     */
    public void increaseNumberOfQueues() {

        System.out.println("Added new queue");
        writer.println(getCurrentDateAndTime() +"Added new queue");
        writer.flush();

        final CustomerArrayBlockingQueue customerQueue = new CustomerArrayBlockingQueue(QUEUE_CAPACITY);
        queueList.add(customerQueue);

        Thread processingThread = new Thread(new Runnable() {
            @Override
            public void run() {

                startProcessingCustomers(customerQueue);

            }
        });

        processingThread.start();

    }

    /**
     * : decreases the number of queues
     */
    public void decreaseNumberOfQueues() {
        System.out.println("Removed a queue");
        writer.println(getCurrentDateAndTime() +"Removed a queue");
        writer.flush();
        queueList.remove(0);
    }

    /**
     * returns the queue list
     * @return
     */
    public List<CustomerArrayBlockingQueue> getQueueList() {
        return queueList;
    }

    /**
     * updates the observers with the modifications suffered by one queue, either by adding or removing customers. It does so by creating a QueueChangedEvent
     * @param queueIndex
     * @param modification
     * @param totalWaitingTimeForQueue
     * @param timeCustomerWaited
     */
    public synchronized void updateObservers(int queueIndex, int modification, int totalWaitingTimeForQueue, int timeCustomerWaited) {

        QueueChangedEvent event = new QueueChangedEvent(queueIndex, modification, totalWaitingTimeForQueue, timeCustomerWaited);

        for (Observer observer : observerList) {
            observer.queueChanged(event);
        }

    }

    /**
     * used by the auto increment queue number mechanism. It notifies the observer to increase the number of queus.
     */
    public void updateObserversAutoAddQueue(){
        for (Observer observer : observerList) {
            observer.numberOfQueuesChanged(1);
        }
    }

    /**
     * add an obverser
     * @param observer
     */
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    /**
     * return the customer addition interval
     * @return
     */
    public int getAdditionInterval() {
        return additionInterval;
    }

    /**
     * sets the customer addition interval
     * @param additionInterval
     */
    public void setAdditionInterval(int additionInterval) {

        if(additionInterval > this.additionInterval){
            System.out.println("Increased addition interval to " + additionInterval +" ms");
            writer.println(getCurrentDateAndTime()+"Increased addition interval to " + additionInterval+" ms");
        }else{
            System.out.println("Decreased addition interval to " + additionInterval+" ms");
            writer.println(getCurrentDateAndTime()+"Decreased addition interval to " + additionInterval+" ms");

        }
            writer.flush();

        this.additionInterval = additionInterval;
    }

    /**
     * begins the simulation
     */
    public void beginSimulation() {

        System.out.println("Begin simulation");
        writer.println(getCurrentDateAndTime()+ "Begin simulation");
        writer.flush();

        simulationStarted = true;
        startSimulation();
    }

    /**
     * end the simulation
     */
    public void endSimulation() {

        System.out.println("End simulation");
        writer.println(getCurrentDateAndTime()+ "End simulation");
        writer.flush();

        simulationStarted = false;
    }

    /**
     * returns the current date and time
     * @return
     */
    public String getCurrentDateAndTime() {
        return dateFormat.format(new Date(System.currentTimeMillis())) + " - ";
    }

    /**
     * returns the service speed
     * @return
     */
    public int getServiceSpeed() {
        return serviceSpeed;
    }

    /**
     * sets the service speed
     * @param serviceSpeed
     */
    public void setServiceSpeed(int serviceSpeed) {

        if(serviceSpeed > this.serviceSpeed){
            System.out.println("Increased service speed to " + serviceSpeed +" ms");

            writer.println(getCurrentDateAndTime()+ "Increased service speed to " + serviceSpeed+" ms");
        }else{
            System.out.println("Decreased service speed to " + serviceSpeed+" ms");
            writer.println(getCurrentDateAndTime()+ "Decreased service speed to " + serviceSpeed+" ms");

        }
        writer.flush();

        this.serviceSpeed = serviceSpeed;

    }

    /**
     * returns the state of the simulation
     * @return
     */
    public boolean isSimulationStarted() {
        return simulationStarted;
    }

    /**
     * starts the what if analysys
     * @param simInterval
     * @param serviceTime
     * @param numberOfQueues
     * @return
     */
    public int startWhatIfAnalysys(int simInterval, int serviceTime, int numberOfQueues) {

        int additionPeriod = serviceTime/2;

        int numberOfPplServed = simInterval/serviceTime * numberOfQueues;

        int timeWaitingPerClient = (serviceTime - additionPeriod) / numberOfQueues;

        int averageWaitingTime = timeWaitingPerClient * numberOfPplServed;


        return averageWaitingTime;
    }
}