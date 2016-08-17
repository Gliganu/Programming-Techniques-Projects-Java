package model.model.products;

import java.awt.*;

/**
 * Created by gliga on 3/29/2015.
 */
public class Closet extends Product {

    private int width;
    private int height;
    private int numberOfDoors;

    /**
     * Simple constructor
     */
    public Closet() {
        super(ProductType.Furniture);
    }

    /**
     * Constructor with parameters
     * @param price
     * @param width
     * @param height
     * @param numberOfDoors
     */
    public Closet(int price, int width, int height, int numberOfDoors) {
        super(price, ProductType.Furniture);
        this.width = width;
        this.height = height;
        this.numberOfDoors = numberOfDoors;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String toString() {
        return "Closet{" +
                "width=" + width +
                ", height=" + height +
                ", numberOfDoors=" + numberOfDoors +
                "price=" + price +
                ", type=" + type +
                '}';
    }

    @Override
    public ProductEnum getEnum() {
        return ProductEnum.Closet;
    }
}
