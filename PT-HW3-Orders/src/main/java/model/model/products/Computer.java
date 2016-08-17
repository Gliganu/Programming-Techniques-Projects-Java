package model.model.products;

/**
 * Created by gliga on 3/29/2015.
 */
public class Computer extends Product {

    private int memoryCapacity;
    private int processorSpeed;
    private boolean desktop;

    /**
     * Simple constructor
     */
    public Computer() {
        super(ProductType.IT);
    }

    /**
     * Constructor with parameters
     * @param price
     * @param memoryCapacity
     * @param monitorRefreshRate
     * @param desktop
     */
    public Computer( int price, int memoryCapacity, int monitorRefreshRate, boolean desktop) {
        super(price,  ProductType.IT);
        this.memoryCapacity = memoryCapacity;
        this.processorSpeed = monitorRefreshRate;
        this.desktop = desktop;
    }

    public int getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(int memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public int getProcessorSpeed() {
        return processorSpeed;
    }

    public void setProcessorSpeed(int processorSpeed) {
        this.processorSpeed = processorSpeed;
    }

    public boolean isDesktop() {
        return desktop;
    }

    public void setDesktop(boolean desktop) {
        this.desktop = desktop;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "memoryCapacity=" + memoryCapacity +
                ", processorSpeed=" + processorSpeed +
                ", desktop=" + desktop +
                "price=" + price +
                ", type=" + type +
                '}';
    }

    @Override
    public ProductEnum getEnum() {
        return ProductEnum.Computer;
    }
}
