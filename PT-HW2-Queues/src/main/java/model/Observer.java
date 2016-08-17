package model;

/**
 * Created by gliga on 3/15/2015.
 */
public interface Observer {

    /**
     * signals that a queue has changed. The Event passed contains all the necessary information about which queue changed and in what way
     * @param event
     */
    public void queueChanged(QueueChangedEvent event);

    /**
     * tell the observer that the number of queus has changed and by how much.
     * @param difference
     */
    public void numberOfQueuesChanged(int difference);

    /**
     * notifies that the user wants to change the rate at which customers at added to the queue.
     * @param difference
     */
    public void changeAdditionSpeed(int difference);

    /**
     * notifies that the user wants to change at which the customers are served
     * @param difference
     */
    public void changeServiceSpeed(int difference);

    /**
     * return the simulation state, whether it is currently running or not
     * @return
     */
    public boolean isSimulationStarted();

    /**
     * return the total simulation time
     * @return
     */
    public int getSimulationTotalTime();

    /**
     * end the simulation
     */
    public void endSimulation();
}
